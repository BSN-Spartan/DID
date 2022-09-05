// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSONArray;
import com.reddate.did.protocol.common.BaseDidDocument;
import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.common.Proof;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.request.ResetDid;
import com.reddate.did.protocol.response.CreateDidData;
import com.reddate.did.protocol.response.DidDocument;
import com.reddate.did.protocol.response.ResponseData;
import com.reddate.did.service.engine.DidContractEngine;
import com.reddate.did.constant.ContractCode;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.constant.ResultCode;
import com.reddate.did.service.DidService;
import com.reddate.did.util.DidUtils;
import com.reddate.did.util.KeyPairUtils;
import com.reddate.did.util.commons.ECDSAUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class DidServiceImpl implements DidService {

  private static final Logger logger = LoggerFactory.getLogger(DidServiceImpl.class);

  /**
   * Create did ( According to the parameters, you can choose whether to store on the chain )
   *
   */
  @Override
  public ResponseData<CreateDidData> createDid(Boolean isStorageOnChain) {
    try {
      // Generate two pairs of public and private keys
      KeyPair primaryKeyPair = ECDSAUtils.createKey();
      KeyPair alternateKeyPair = ECDSAUtils.createKey();
      if (StringUtils.isBlank(primaryKeyPair.getPublicKey())
          || StringUtils.isBlank(primaryKeyPair.getPrivateKey())
          || StringUtils.isBlank(alternateKeyPair.getPublicKey())
          || StringUtils.isBlank(alternateKeyPair.getPrivateKey())) {
        return new ResponseData<>(null, ErrorCode.CREATE_KEY_PAIR_FAIL);
      }

      // Assemble base did document
      BaseDidDocument baseDidDocument =
          DidUtils.generateBaseDidDocument(primaryKeyPair, alternateKeyPair);

      // Generate did identifier (encode the base did document after hashing twice)
      String didIdentifier = DidUtils.generateDidIdentifierByBaseDidDocument(baseDidDocument);
      if (StringUtils.isBlank(didIdentifier)) {
        return new ResponseData<>(null, ErrorCode.GENERATE_DID_FAIL);
      }

      // Generate did
      String did = DidUtils.generateDidByDidIdentifier(didIdentifier);
      if (StringUtils.isBlank(did)) {
        return new ResponseData<>(null, ErrorCode.CREATE_DID_FAIL);
      }

      // Assembling did documents (Except for the signature)
      DidDocument didDocument = DidUtils.generateDidDocument(primaryKeyPair, alternateKeyPair, did);
      if (Objects.isNull(didDocument)) {
        return new ResponseData<>(null, ErrorCode.GENERATE_DID_DOCUMENT_FAIL);
      }

      // Sign the assembled did document
      String signValue =
          ECDSAUtils.sign(JSONArray.toJSON(didDocument).toString(), primaryKeyPair.getPrivateKey(), primaryKeyPair.getPublicKey());
      if (StringUtils.isBlank(signValue)) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.SIGNATURE_FAIL, "didDocument "));
      }

      // Assembling sign
      Proof proof = new Proof();
      proof.setType(ECDSAUtils.type);
      proof.setCreator(did);
      proof.setSignatureValue(signValue);
      didDocument.setProof(proof);

      // Did document on the chain
      if (isStorageOnChain) {
        TimeInterval timer = DateUtil.timer();
        ContractCode contractCode = DidContractEngine.createDid(did, JSONArray.toJSON(didDocument).toString());
        if (!ContractCode.isSuccess(contractCode)) {
          return new ResponseData<>(null, null, contractCode);
        }
      }

      // Assembly return parameters
      CreateDidData createDidData = new CreateDidData();
      createDidData.setDid(did);
      createDidData.setAuthKeyInfo(primaryKeyPair);
      createDidData.setRecyKeyInfo(alternateKeyPair);
      if (!isStorageOnChain) {
        createDidData.setDidDocument(didDocument);
      }
      return new ResponseData<>(createDidData, ErrorCode.SUCCESS);
    } catch (TimeoutException e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.TRANSACTION_TIMEOUT);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Store the did document on the chain
   *
   * @param didDocument
   * @param publicKey
   */
  @Override
  public ResponseData<Boolean> storeDidDocumentOnChain(
      DidDocument didDocument, PublicKey publicKey) {
    try {
      // Verify the legitimacy of did document and public key
      ResponseData<Boolean> verifyDidDocumentResult =
          this.verifyDidDocument(didDocument, publicKey);
      if (!ErrorCode.DID_DOCUMENT_VALIDATION_SUCCESS
          .getCode()
          .equals(verifyDidDocumentResult.getErrorCode())) {
        return verifyDidDocumentResult;
      }

      // validate the create time and update time is same
      String createTime = didDocument.getCreated();
      String updateTime = didDocument.getUpdated();
      if (!createTime.trim().equals(updateTime.trim())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.DID_CREATE_UPDATE_NOT_SAME);
      }

      // Verify that the did document is on the chain
      ResponseData<DidDocument> getDIdDocumentResult = this.getDidDocument(didDocument.getDid());
      if (ErrorCode.isSuccess(getDIdDocumentResult.getErrorCode())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.DID_EXISTED);
      }

      // Did document on the chain
      ContractCode contractCode =
          DidContractEngine.createDid(
              didDocument.getDid(), JSONArray.toJSON(didDocument).toString());
      if (!ContractCode.isSuccess(contractCode)) {
        return new ResponseData<>(Boolean.FALSE, null, contractCode);
      }
      return new ResponseData<>(Boolean.TRUE, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(Boolean.FALSE, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Get did document according to did
   *
   * @param did
   */
  @Override
  public ResponseData<DidDocument> getDidDocument(String did) {
    try {
      // Validation of did parameter format
      if (StringUtils.isBlank(did)) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "did "));
      }
      if (!DidUtils.isDidValid(did)) {
        return new ResponseData<>(null, ErrorCode.INVALID_DID);
      }

      // Get did documents from the chain according to did
      Map<String, Object> resultMap = DidContractEngine.getDidDocument(did);
      if (!ContractCode.isSuccess(resultMap.get("code").toString())
          && Objects.isNull(resultMap.get("data"))) {
        return new ResponseData<>(null, ErrorCode.DID_NOT_EXIST);
      }
      DidDocument didDocument = (DidDocument) resultMap.get("data");
      return new ResponseData<>(didDocument, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Verify did document
   *
   * @param didDocument
   * @param publicKey
   */
  @Override
  public ResponseData<Boolean> verifyDidDocument(DidDocument didDocument, PublicKey publicKey) {
    try {
      // Verify that the did document is not empty
      ResultCode verifyDidDocumentErrorCode = DidUtils.verifyDidDocument(didDocument);
      if (!ErrorCode.isSuccess(verifyDidDocumentErrorCode.getCode())) {
        return new ResponseData<>(Boolean.FALSE, verifyDidDocumentErrorCode);
      }

      // check the did is match with the document, if the document is not updated.
      String createTime = didDocument.getCreated();
      String updateTime = didDocument.getUpdated();
      if (createTime.equals(updateTime)) {
        BaseDidDocument baseDidDocument = DidUtils.generateBaseDidDocument(didDocument);
        String didIdentifier = DidUtils.generateDidIdentifierByBaseDidDocument(baseDidDocument);
        String computedDid = DidUtils.generateDidByDidIdentifier(didIdentifier);
        String documentDid = didDocument.getDid();
        if (StringUtils.isBlank(computedDid) || !computedDid.equals(documentDid)) {
          ResultCode didValdiateResult = new ResultCode(ErrorCode.DID_DOCUMENT_NOT_MATCH);
          return new ResponseData<>(Boolean.FALSE, didValdiateResult);
        }
      }

      // Verify the validity of primary public key
      ResultCode verifyAuthenticationErrorCode =
          KeyPairUtils.isPublicKeyValid(didDocument.getAuthentication());
      if (!ErrorCode.isSuccess(verifyAuthenticationErrorCode.getCode())) {
        return new ResponseData<>(
            Boolean.FALSE, verifyAuthenticationErrorCode.editEnMessage("authentication "));
      }

      // Verify the validity of backup public key
      ResultCode verifyRecoveryErrorCode = KeyPairUtils.isPublicKeyValid(didDocument.getRecovery());
      if (!ErrorCode.isSuccess(verifyRecoveryErrorCode.getCode())) {
        return new ResponseData<>(
            Boolean.FALSE, verifyRecoveryErrorCode.editEnMessage("recovery "));
      }

      // Verify the validity of param public key
      ResultCode verifyPublicKeyErrorCode = KeyPairUtils.isPublicKeyValid(publicKey);
      if (!ErrorCode.isSuccess(verifyPublicKeyErrorCode.getCode())) {
        return new ResponseData<>(
            Boolean.FALSE, verifyPublicKeyErrorCode.editEnMessage("publicKey "));
      }

      // Compare whether the public key of the parameter and the main public key of the did document
      // on the chain are the same
      if (!DidUtils.comparePrimaryPublicKey(didDocument, publicKey.getPublicKey()).get()) {
        return new ResponseData<>(
            Boolean.FALSE, ErrorCode.PRIMARY_PUBLICKEY_DID_DOCUMENT_NOT_MATCH);
      }

      // Make a copy of the did document and verify the signature
      DidDocument copyDidDocument = new DidDocument();
      BeanUtil.copyProperties(didDocument, copyDidDocument);
      copyDidDocument.setProof(null);
      boolean verifyDocSign = false;
      try {
        verifyDocSign =
            ECDSAUtils.verify(
                JSONArray.toJSON(copyDidDocument).toString(),
                publicKey.getPublicKey(),
                didDocument.getProof().getSignatureValue());
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (!verifyDocSign) {
        return new ResponseData<>(
            Boolean.FALSE, new ResultCode(ErrorCode.SIGNATURE_VERIFICATION_FAIL, "didDocument "));
      }
      return new ResponseData<>(Boolean.TRUE, ErrorCode.DID_DOCUMENT_VALIDATION_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(Boolean.FALSE, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Reset master key based on standby key
   *
   * @param resetDid
   */
  @Override
  public ResponseData<KeyPair> resetDidAuth(ResetDid resetDid) {
    try {
      // Input parameter non null check
      if (Objects.isNull(resetDid)) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "resetDid "));
      }

      // Validation of did parameter format
      if (StringUtils.isBlank(resetDid.getDidDocument().getDid())) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "did "));
      }
      if (!DidUtils.isDidValid(resetDid.getDidDocument().getDid())) {
        return new ResponseData<>(null, ErrorCode.INVALID_DID);
      }

      boolean recoveryKeyVerify =
          ECDSAUtils.verify(
              resetDid.getDidDocument().getRecovery().getPublicKey(),
              resetDid.getDidDocument().getRecovery().getPublicKey(),
              resetDid.getAuthPublicKeyInfo().getPublicKeySign());
      if (!recoveryKeyVerify) {
        return new ResponseData<>(null, ErrorCode.VERIFY_PUBLIC_KEY_SIGN_ERR);
      }

      String did = resetDid.getDidDocument().getDid();

      // Get did documents from the chain according to did
      ResponseData<DidDocument> didDocumentResponseData = this.getDidDocument(did);
      if (Objects.isNull(didDocumentResponseData.getResult())
          || !ErrorCode.SUCCESS.getCode().equals(didDocumentResponseData.getErrorCode())) {
        return new ResponseData<>(null, ErrorCode.DID_NOT_EXIST);
      }

      ResponseData<Boolean> verifyDidDocumentResult =
          this.verifyDidDocument(
              resetDid.getDidDocument(), resetDid.getDidDocument().getAuthentication());
      if (!ErrorCode.DID_DOCUMENT_VALIDATION_SUCCESS
          .getCode()
          .equals(verifyDidDocumentResult.getErrorCode())) {
        return new ResponseData<>(
            null, ErrorCode.getTypeByErrorCode(verifyDidDocumentResult.getErrorCode()));
      }

      ResultCode validateUpdateAuthMatchResult =
          DidUtils.validateUpdateAuthMatch(
              didDocumentResponseData.getResult(), resetDid.getDidDocument());
      if (!ErrorCode.SUCCESS.getCode().equals(validateUpdateAuthMatchResult.getCode())) {
        return new ResponseData<>(null, validateUpdateAuthMatchResult);
      }

      // Update of did document on the chain
      ContractCode contractCode =
          DidContractEngine.updateDidAuth(
              did, JSONArray.toJSON(resetDid.getDidDocument()).toString());
      if (!ContractCode.isSuccess(contractCode)) {
        return new ResponseData<>(null, null, contractCode);
      }

      KeyPair primaryKeyPair = new KeyPair();
      primaryKeyPair.setPublicKey(resetDid.getDidDocument().getAuthentication().getPublicKey());
      primaryKeyPair.setType(resetDid.getDidDocument().getAuthentication().getType());
      // Return new primary key
      return new ResponseData<>(primaryKeyPair, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  @Override
  public ResponseData<BigInteger> getBlockNumber() {

    try {
      BigInteger blockNumber = DidContractEngine.getBlockNumber();
      return new ResponseData<>(blockNumber, ErrorCode.SUCCESS);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  @Override
  public ResponseData<Integer> getGroupId() {
    try {
      Integer groupId = DidContractEngine.getGroupId();
      return new ResponseData<>(groupId, ErrorCode.SUCCESS);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }
}
