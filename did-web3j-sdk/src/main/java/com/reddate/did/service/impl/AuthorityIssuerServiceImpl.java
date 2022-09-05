// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.reddate.did.constant.ContractCode;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.constant.ResultCode;
import com.reddate.did.protocol.common.AuthorityIssuer;
import com.reddate.did.protocol.common.Proof;
import com.reddate.did.protocol.common.RegisterAuthorityIssuer;
import com.reddate.did.protocol.request.RegisterCpt;
import com.reddate.did.protocol.request.RevokeCredential;
import com.reddate.did.protocol.request.RovekeInfo;
import com.reddate.did.protocol.response.*;
import com.reddate.did.service.AuthorityIssuerService;
import com.reddate.did.service.DidService;
import com.reddate.did.service.engine.AuthorityIssuerContractEngine;
import com.reddate.did.service.engine.BaseEngine;
import com.reddate.did.service.engine.CptContractEngine;
import com.reddate.did.util.CptUtils;
import com.reddate.did.util.CredentialUtils;
import com.reddate.did.util.DidUtils;
import com.reddate.did.util.PagesUtils;
import com.reddate.did.util.commons.ECDSAUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AuthorityIssuerServiceImpl implements AuthorityIssuerService {

  private DidService didService = new DidServiceImpl();

  /**
   * Register as the issuing party
   *
   * @param issuer
   */
  @Override
  public ResponseData<Boolean> registerAuthIssuer(RegisterAuthorityIssuer issuer) {
    try {
      // Verify the validity of registerAuthorityIssuer object
      ResultCode errorCode = CptUtils.verifyRegisterAuthorityIssuer(issuer);
      if (!ErrorCode.isSuccess(errorCode.getCode())) {
        return new ResponseData<>(Boolean.FALSE, errorCode);
      }

      // Get did documents from the chain according to issuer did
      ResponseData<DidDocument> issuerDidDocument = didService.getDidDocument(issuer.getDid());
      if (Objects.isNull(issuerDidDocument.getResult())
          || !ErrorCode.SUCCESS.getCode().equals(issuerDidDocument.getErrorCode())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.DID_NOT_EXIST);
      }

      // Verify whether the current did has registered the issuing party
      if (AuthorityIssuerContractEngine.isIssuer(issuer.getDid())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.ISSUER_EXISTED);
      }

      // Compare whether the public key of the parameter and the main public key of the did document
      // on the chain are the same
      DidDocument didDocument = issuerDidDocument.getResult();
      if (!DidUtils.comparePrimaryPublicKey(didDocument, issuer.getPublicKeyInfo().getPublicKey())
          .get()) {
        return new ResponseData<>(
            Boolean.FALSE, ErrorCode.PRIMARY_PUBLICKEY_DID_DOCUMENT_NOT_MATCH);
      }

      // Assemble the issuer information and store it on the chain
      AuthorityIssuer authorityIssuer = new AuthorityIssuer();
      authorityIssuer.setName(issuer.getName());
      authorityIssuer.setDid(issuer.getDid());
      if (!AuthorityIssuerContractEngine.createIssuer(
          issuer.getDid(), JSONArray.toJSON(authorityIssuer).toString())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.CREATE_ISSUER_FAIL);
      }
      return new ResponseData<>(Boolean.TRUE, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(Boolean.FALSE, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Query the list of issuers according to pagination information and did ( If did is blank, all
   * issuing parties will be queried )
   *
   * @param page
   * @param size
   * @param did
   */
  @Override
  public ResponseData<Pages<AuthorityIssuer>> queryAuthIssuerList(
      Integer page, Integer size, String did) {
    try {
      // Verify the validity of pages parameters
      ResultCode errorCode = PagesUtils.verifyPages(page, size);
      if (!ErrorCode.isSuccess(errorCode.getCode())) {
        return new ResponseData<>(null, errorCode);
      }
      Pages<AuthorityIssuer> pages = new Pages<>();
      if (StringUtils.isNotBlank(did)) {
        // Verify the validity of did
        if (!DidUtils.isDidValid(did)) {
          return new ResponseData<>(null, ErrorCode.INVALID_DID);
        }

        // Query the issuer on the chain according to did
        Map<String, Object> result = AuthorityIssuerContractEngine.queryAuthIssuerList(did);
        String code = result.get("code").toString();
        if (!ContractCode.isSuccess(code)) {
          return new ResponseData<>(null, ErrorCode.ISSUER_NOT_EXIST);
        }

        // Assembly return data
        List<AuthorityIssuer> authorityIssuerList = new ArrayList<>();
        AuthorityIssuer authIssuer = (AuthorityIssuer) result.get("data");
        authorityIssuerList.add(authIssuer);
        pages.setResult(authorityIssuerList);
        return new ResponseData<>(pages, ErrorCode.SUCCESS);
      } else {
        // Query the list of Issuers on the chain according to pages parameters
        Map<String, Object> result = AuthorityIssuerContractEngine.queryAuthIssuerList(page, size);
        String code = result.get("code").toString();
        if (!ContractCode.isSuccess(code)) {
          return new ResponseData<>(null, ErrorCode.NO_DATA_OF_ISSUER);
        }

        // Assembly return data
        List<AuthorityIssuer> authorityIssuerList = (List<AuthorityIssuer>) result.get("data");
        pages.setResult(authorityIssuerList);
        pages.setPage(page);
        pages.setSize(size);
        pages.setTotalNum((Integer) result.get("totalNum"));
        pages.setTotalPage((Integer) result.get("totalPage"));
        return new ResponseData<>(pages, ErrorCode.SUCCESS);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Register credentials template
   *
   * @param registerCpt
   */
  @Override
  public ResponseData<CptBaseInfo> registerCpt(RegisterCpt registerCpt) {
    try {
      // Validation of parameter validity
      ResultCode errorCode = CptUtils.verifyRegisterCpt(registerCpt);
      if (!ErrorCode.isSuccess(errorCode.getCode())) {
        return new ResponseData<>(null, errorCode);
      }

      // Query did document on the chain according to did
      ResponseData<DidDocument> response = didService.getDidDocument(registerCpt.getDid());
      if (!ErrorCode.SUCCESS.getCode().equals(response.getErrorCode())) {
        return new ResponseData<>(null, ErrorCode.DID_NOT_EXIST);
      }

      // Verify whether the current did has registered the issuing party
      if (!AuthorityIssuerContractEngine.isIssuer(registerCpt.getDid())) {
        return new ResponseData<>(null, ErrorCode.ISSUER_NOT_EXIST);
      }

      // Assembly needs CPT information on the chain
      CptInfo cptInfo = CptUtils.assemblyCptInfo(registerCpt);

      if (!registerCpt.getDid().equals(registerCpt.getProof().getCreator())) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.CPT_DID_NE_PROOF_CREATOR));
      }

      boolean proofResult =
          ECDSAUtils.verify(
              JSONArray.toJSON(cptInfo).toString(),
              response.getResult().getAuthentication().getPublicKey(),
              registerCpt.getProof().getSignatureValue());
      if (!proofResult) {
        return new ResponseData<>(
            null, new ResultCode(ErrorCode.SIGNATURE_FAIL, "cpt proof verify"));
      }

      // Add signature information
      Proof proof = new Proof();
      proof.setType(ECDSAUtils.type);
      proof.setCreator(cptInfo.getPublisherDid());
      proof.setSignatureValue(registerCpt.getProof().getSignatureValue());
      cptInfo.setProof(proof);

      // Chain up the assembled CPT entity
      ContractCode contractCode =
          CptContractEngine.registerCpt(
              registerCpt.getDid(), cptInfo.getCptId().toString(), JSONArray.toJSONString(cptInfo));
      if (!ContractCode.isSuccess(contractCode)) {
        return new ResponseData<>(null, null, contractCode);
      }

      // Assembly return information
      CptBaseInfo cptBaseInfo = new CptBaseInfo();
      cptBaseInfo.setCptId(cptInfo.getCptId());
      cptBaseInfo.setCptVersion(cptInfo.getCptVersion());
      return new ResponseData<>(cptBaseInfo, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Query the list of credential templates based on paging information and did
   *
   * @param page
   * @param size
   * @param did
   */
  @Override
  public ResponseData<Pages<CptInfo>> queryCptListByDid(Integer page, Integer size, String did) {
    Pages<CptInfo> pages = new Pages<>();
    try {
      // Verify the validity of pages parameters
      ResultCode errorCode = PagesUtils.verifyPages(page, size);
      if (!ErrorCode.isSuccess(errorCode.getCode())) {
        return new ResponseData<>(null, errorCode);
      }

      // Verify the validity of did
      if (StringUtils.isBlank(did)) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "did "));
      }
      if (!DidUtils.isDidValid(did)) {
        return new ResponseData<>(null, ErrorCode.INVALID_DID);
      }

      // Query CPT data on chain according to the did and pages param
      Map<String, Object> resultMap = CptContractEngine.getCptListByDid(did, page, size);
      String code = resultMap.get("code").toString();
      if (!ContractCode.isSuccess(code)) {
        return new ResponseData<>(null, ErrorCode.CPT_NOT_EXIST);
      }

      // Assembly return data
      List<CptInfo> result = (List) resultMap.get("data");
      pages.setPage(page);
      pages.setSize(size);
      pages.setTotalNum((Integer) resultMap.get("totalNum"));
      pages.setTotalPage((Integer) resultMap.get("totalPage"));
      pages.setResult(result);
      return new ResponseData<>(pages, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Query credential template information by cptId
   *
   * @param cptId
   */
  @Override
  public ResponseData<CptInfo> queryCptById(Long cptId) {
    try {
      if (Objects.isNull(cptId)) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "cptId "));
      }
      Map<String, Object> resultMap = CptContractEngine.getCptByCptId(cptId.toString());
      if (!ContractCode.isSuccess(resultMap.get("code").toString())) {
        return new ResponseData<>(null, ErrorCode.CPT_NOT_EXIST);
      }
      CptInfo cptInfo = (CptInfo) resultMap.get("data");
      if (Objects.isNull(cptInfo)) {
        return new ResponseData<>(null, ErrorCode.CPT_NOT_EXIST);
      }
      return new ResponseData<>(cptInfo, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Update credential template
   *
   * @param registerCpt
   */
  @Override
  public ResponseData<CptBaseInfo> updateCpt(RegisterCpt registerCpt) {
    try {
      // Validation of parameter validity
      ResultCode verifyRegisterCptErrorCode = CptUtils.verifyRegisterCpt(registerCpt);
      if (!ErrorCode.isSuccess(verifyRegisterCptErrorCode.getCode())) {
        return new ResponseData<>(null, verifyRegisterCptErrorCode);
      }

      // Verify cptid is not empty
      if (Objects.isNull(registerCpt.getCptId())) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "cptId "));
      }

      // Validation of did format
      if (!DidUtils.isDidValid(registerCpt.getDid())) {
        return new ResponseData<>(null, ErrorCode.DID_NOT_EXIST);
      }

      // Query did document on the chain according to did
      ResponseData<DidDocument> response = didService.getDidDocument(registerCpt.getDid());
      if (!ErrorCode.SUCCESS.getCode().equals(response.getErrorCode())) {
        return new ResponseData<>(null, ErrorCode.DID_NOT_EXIST);
      }

      // Verify whether the public key of the parameter is consistent with that of the did document
      DidDocument didDocument = response.getResult();

      // Verify the relationship between CPT and issuing
      if (!CptContractEngine.isCptExist(registerCpt.getCptId().toString())) {
        return new ResponseData<>(null, ErrorCode.CPT_NOT_EXIST);
      }
      Map<String, Object> resultMap =
          CptContractEngine.getCptByCptId(registerCpt.getCptId().toString());
      if (!ContractCode.isSuccess(resultMap.get("code").toString())
          || Objects.isNull(resultMap.get("data"))) {
        return new ResponseData<>(null, ErrorCode.CPT_NOT_EXIST);
      }
      CptInfo cptInfoChain = (CptInfo) resultMap.get("data");
      if (Objects.isNull(cptInfoChain)) {
        return new ResponseData<>(null, ErrorCode.CPT_NOT_EXIST);
      }
      if (!cptInfoChain.getPublisherDid().equals(registerCpt.getDid())) {
        return new ResponseData<>(null, ErrorCode.CPT_AND_ISSUER_CANNOT_MATCH);
      }

      // Assembly needs CPT information on the chain
      CptInfo cptInfo = CptUtils.resetCptInfo(registerCpt, cptInfoChain);
      cptInfo.setCptVersion(cptInfoChain.getCptVersion() + 1);
      cptInfo.setUpdate(registerCpt.getUpdate());

      if (!registerCpt.getDid().equals(registerCpt.getProof().getCreator())) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.CPT_DID_NE_PROOF_CREATOR));
      }

      boolean proofResult =
          ECDSAUtils.verify(
              JSONArray.toJSON(cptInfo).toString(),
              response.getResult().getAuthentication().getPublicKey(),
              registerCpt.getProof().getSignatureValue());
      if (!proofResult) {
        return new ResponseData<>(
            null, new ResultCode(ErrorCode.SIGNATURE_FAIL, "cpt proof verify"));
      }

      // Add signature information
      Proof proof = new Proof();
      proof.setType(ECDSAUtils.type);
      proof.setCreator(cptInfo.getPublisherDid());
      proof.setSignatureValue(registerCpt.getProof().getSignatureValue());
      cptInfo.setProof(proof);

      // Chain up the assembled CPT entity
      ContractCode contractCode =
          CptContractEngine.updateCpt(
              registerCpt.getDid(), cptInfo.getCptId().toString(), JSONArray.toJSONString(cptInfo));
      if (!ContractCode.isSuccess(contractCode)) {
        return new ResponseData<>(null, null, contractCode);
      }

      // Assembly return information
      CptBaseInfo cptBaseInfo = new CptBaseInfo();
      cptBaseInfo.setCptId(cptInfo.getCptId());
      cptBaseInfo.setCptVersion(cptInfo.getCptVersion());
      return new ResponseData<>(cptBaseInfo, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Revocation of credential
   *
   * @param cred
   */
  public ResponseData<Boolean> revokeCredential(RevokeCredential cred) {
    try {
      // Verify the validity of revocation certificate
      ResultCode errorCode = CredentialUtils.isRevokeCredentialValid(cred);
      if (!ErrorCode.isSuccess(errorCode.getCode())) {
        return new ResponseData<>(Boolean.FALSE, errorCode);
      }

      // Get did documents from the chain according to issuer did
      ResponseData<DidDocument> issuerDidDocument = didService.getDidDocument(cred.getDid());
      if (Objects.isNull(issuerDidDocument.getResult())
          || !ErrorCode.SUCCESS.getCode().equals(issuerDidDocument.getErrorCode())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.DID_NOT_EXIST);
      }

      StringBuffer signRawData = new StringBuffer();
      signRawData.append(cred.getDid()).append(cred.getRovekeDate()).append(cred.getCredId());
      boolean verifySign =
          ECDSAUtils.verify(
              signRawData.toString(),
              issuerDidDocument.getResult().getAuthentication().getPublicKey(),
              cred.getProof().getSignatureValue());
      if (!verifySign) {
        return new ResponseData<>(
            null, new ResultCode(ErrorCode.SIGNATURE_FAIL, "revoke credential "));
      }

      // Read cpt objects on chain according to cptId
      Map<String, Object> resultMap = CptContractEngine.getCptByCptId(cred.getCptId().toString());
      if (!ContractCode.isSuccess(resultMap.get("code").toString())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.CPT_NOT_EXIST);
      }
      CptInfo cptInfo = (CptInfo) resultMap.get("data");

      // Verify whether the issuing party is bound to cpt
      if (!cred.getDid().equals(cptInfo.getPublisherDid())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.CPT_AND_ISSUER_CANNOT_MATCH);
      }

      // Verify whether the credential is revoked
      Map<String, Object> result =
          CptContractEngine.getRevokedCred(cred.getDid(), cred.getCredId());
      if (BaseEngine.CHAIN_SUCCESS_CODE.equals(result.get("code"))) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.CREDENTIAL_REVOKED);
      }

      // Package revocation voucher information and store it on the chain
      RovekeInfo rovekeInfo = CredentialUtils.packageRovekeInfo(cred);
      // rovekeInfo.setProof(cred.getProof());
      if (!CptContractEngine.revokeCredential(
          cred.getDid(), cred.getCredId(), JSONArray.toJSON(rovekeInfo).toString())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.CREDENTIAL_REVOCATION_FAIL);
      }
      return new ResponseData<>(Boolean.TRUE, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(Boolean.FALSE, ErrorCode.UNKNOWN_ERROR);
    }
  }
}
