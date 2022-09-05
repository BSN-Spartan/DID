// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.reddate.did.constant.ContractCode;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.constant.ResultCode;
import com.reddate.did.protocol.common.BaseCredential;
import com.reddate.did.protocol.common.Credential;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.request.CreateCredential;
import com.reddate.did.protocol.request.RovekeInfo;
import com.reddate.did.protocol.response.*;
import com.reddate.did.service.CredentialService;
import com.reddate.did.service.DidService;
import com.reddate.did.service.engine.AuthorityIssuerContractEngine;
import com.reddate.did.service.engine.BaseEngine;
import com.reddate.did.service.engine.CptContractEngine;
import com.reddate.did.util.CredentialUtils;
import com.reddate.did.util.DidUtils;
import com.reddate.did.util.KeyPairUtils;
import com.reddate.did.util.PagesUtils;
import com.reddate.did.util.commons.ECDSAUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CredentialServiceImpl implements CredentialService {

  private DidService didService = new DidServiceImpl();


  /**
   * Create credentials
   *
   * @param createCredential
   */
  @Override
  public ResponseData<CredentialWrapper> createCredential(CreateCredential createCredential) {
    try {
      // Verify the validity of createCredential object
      ResultCode errorCode = CredentialUtils.isCreateCredentialArgsValid(createCredential);
      if (!ErrorCode.isSuccess(errorCode.getCode())) {
        return new ResponseData<>(null, errorCode);
      }

      // Get did documents from the chain according to issuer did
      ResponseData<DidDocument> issuerDidDocument =
          didService.getDidDocument(createCredential.getIssuerDid());
      if (Objects.isNull(issuerDidDocument.getResult())
          || !ErrorCode.SUCCESS.getCode().equals(issuerDidDocument.getErrorCode())) {
        return new ResponseData<>(null, ErrorCode.DID_NOT_EXIST);
      }

      // Verify whether the current did has registered the issuing party
      if (!AuthorityIssuerContractEngine.isIssuer(createCredential.getIssuerDid())) {
        return new ResponseData<>(null, ErrorCode.ISSUER_NOT_EXIST);
      }

      // Get did documents from the chain according to user did
      ResponseData<DidDocument> userDidDocument =
          didService.getDidDocument(createCredential.getUserDid());
      if (Objects.isNull(userDidDocument.getResult())
          || !ErrorCode.SUCCESS.getCode().equals(userDidDocument.getErrorCode())) {
        return new ResponseData<>(null, ErrorCode.DID_NOT_EXIST);
      }

      // Read cpt objects on chain according to cptId
      Map<String, Object> resultMap =
          CptContractEngine.getCptByCptId(createCredential.getCptId().toString());
      if (!ContractCode.isSuccess(resultMap.get("code").toString())) {
        return new ResponseData<>(null, ErrorCode.CPT_NOT_EXIST);
      }
      CptInfo cptInfo = (CptInfo) resultMap.get("data");

      // Verify whether the issuing party is bound to cpt
      if (!createCredential.getIssuerDid().equals(cptInfo.getPublisherDid())) {
        return new ResponseData<>(null, ErrorCode.CPT_AND_ISSUER_CANNOT_MATCH);
      }

      // Verify whether the attributes in CPT and credentials are consistent
      if (!CredentialUtils.credentialComparisonCptTemplateClaim(createCredential, cptInfo)) {
        return new ResponseData<>(null, ErrorCode.CPT_AND_CREDENTIAL_CANNOT_MATCH);
      }

      // Package credential info
      CredentialWrapper credentialWrapper = CredentialUtils.packageCredentialInfo(createCredential);
      if (Objects.isNull(credentialWrapper)) {
        return new ResponseData<>(null, ErrorCode.CREATE_CREDENTIAL_FAIL);
      }

      if (credentialWrapper.getShortDesc() == null
          || credentialWrapper.getShortDesc().trim().isEmpty()) {
        credentialWrapper.setShortDesc(cptInfo.getTitle());
      }

      return new ResponseData<>(credentialWrapper, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Verify credentials
   *
   * @param credential
   * @param publicKey
   */
  @Override
  public ResponseData<Boolean> verifyCredential(Credential credential, PublicKey publicKey) {
    try {
      // Verify the validity and expiration of the credential object
      ResultCode credentialValidErrorCode = CredentialUtils.isCredentialArgsValid(credential);
      if (!ErrorCode.isSuccess(credentialValidErrorCode.getCode())) {
        return new ResponseData<>(Boolean.FALSE, credentialValidErrorCode);
      }

      // Verify the validity of publicKey object
      ResultCode publicKeyValidErrorCode = KeyPairUtils.isPublicKeyValid(publicKey);
      if (!ErrorCode.isSuccess(publicKeyValidErrorCode.getCode())) {
        return new ResponseData<>(Boolean.FALSE, publicKeyValidErrorCode.editEnMessage(""));
      }

      // Public key verification of contract, tamper proof
      Credential copyCredential = new Credential();
      BeanUtil.copyProperties(credential, copyCredential);
      copyCredential.setProof(null);
      if (!ECDSAUtils.verify(
          JSONArray.toJSON(copyCredential).toString(),
          publicKey.getPublicKey(),
          credential.getProof().get("signatureValue").toString())) {
        return new ResponseData<>(
            Boolean.FALSE, new ResultCode(ErrorCode.SIGNATURE_VERIFICATION_FAIL, "credential "));
      }

      // Verify whether the credential is expired
      if (!CredentialUtils.verifyExpiration(credential.getExpirationDate())) {
        return new ResponseData<>(Boolean.FALSE, ErrorCode.CREDENTIAL_EXPIRED);
      }

      // Verify whether the credential is revoked
      Map<String, Object> result =
          CptContractEngine.getRevokedCred(credential.getIssuerDid(), credential.getId());
      if (BaseEngine.CHAIN_SUCCESS_CODE.equals(result.get("code"))) {
        RovekeInfo rovekeInfo = (RovekeInfo) result.get("data");
        if (!Objects.isNull(rovekeInfo)) {
          StringBuffer message = new StringBuffer();
          message.append(credential.getIssuerDid());
          message.append(rovekeInfo.getRovekeDate());
          message.append(credential.getId());
          String signatureValue = rovekeInfo.getProof().getSignatureValue();
          if (ECDSAUtils.verify(message.toString(), publicKey.getPublicKey(), signatureValue)) {
            return new ResponseData<>(Boolean.FALSE, ErrorCode.CREDENTIAL_REVOKED);
          }
        }
      }
      return new ResponseData<>(Boolean.TRUE, ErrorCode.CREDENTIAL_VALIDATION_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(Boolean.FALSE, ErrorCode.UNKNOWN_ERROR);
    }
  }

  /**
   * Query the paging list of revocation credentials
   *
   * @param page
   * @param size
   * @param did
   */
  @Override
  public ResponseData<Pages<BaseCredential>> getRevokedCredList(
      Integer page, Integer size, String did) {
    try {
      // Verify the validity of pages parameters
      ResultCode errorCode = PagesUtils.verifyPages(page, size);
      if (!ErrorCode.isSuccess(errorCode.getCode())) {
        return new ResponseData<>(null, errorCode);
      }
      Pages<BaseCredential> pages = new Pages<>();

      // Verify the validity of did format
      if (StringUtils.isBlank(did)) {
        return new ResponseData<>(null, new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "did "));
      }
      if (!DidUtils.isDidValid(did)) {
        return new ResponseData<>(null, ErrorCode.INVALID_DID);
      }

      // Read revocation credentials list on chain
      Map<String, Object> resultMap = CptContractEngine.getRevokedCredList(did, page, size);
      if (!ContractCode.isSuccess(resultMap.get("code").toString())) {
        return new ResponseData<>(null, ErrorCode.NO_DATA_OF_REVOKE_CREDENTIAL);
      }
      List<BaseCredential> baseCredentialsList = (List<BaseCredential>) resultMap.get("data");
      pages.setPage(page);
      pages.setSize(size);
      pages.setTotalNum((Integer) resultMap.get("totalNum"));
      pages.setTotalPage((Integer) resultMap.get("totalPage"));
      pages.setResult(baseCredentialsList);
      return new ResponseData<>(pages, ErrorCode.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseData<>(null, ErrorCode.UNKNOWN_ERROR);
    }
  }
}
