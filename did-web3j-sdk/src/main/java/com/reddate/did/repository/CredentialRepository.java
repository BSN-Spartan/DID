// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.repository;

import com.reddate.did.exception.DidException;
import com.reddate.did.protocol.common.BaseCredential;
import com.reddate.did.protocol.common.Credential;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.request.CreateCredential;
import com.reddate.did.protocol.response.CredentialWrapper;
import com.reddate.did.protocol.response.Pages;
import com.reddate.did.protocol.response.ResponseData;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.service.CredentialService;
import com.reddate.did.service.impl.CredentialServiceImpl;

public class CredentialRepository {

  private CredentialService credentialService = new CredentialServiceImpl();

  public CredentialWrapper createCredential(CreateCredential createCredential) {
    ResponseData<CredentialWrapper> credentialWrapper = null;
    try {
      credentialWrapper = credentialService.createCredential(createCredential);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (credentialWrapper == null || credentialWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(credentialWrapper.getErrorCode())) {
      return credentialWrapper.getResult();
    } else {
      throw new DidException(
          credentialWrapper.getErrorCode(), credentialWrapper.getErrorEnMessage());
    }
  }

  public Boolean verifyCredential(Credential credential, PublicKey publicKey) {
    ResponseData<Boolean> verifyResultWrapper = null;
    try {
      verifyResultWrapper = credentialService.verifyCredential(credential, publicKey);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (verifyResultWrapper == null || verifyResultWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.CREDENTIAL_VALIDATION_SUCCESS
        .getCode()
        .equals(verifyResultWrapper.getErrorCode())) {
      return verifyResultWrapper.getResult();
    } else {
      throw new DidException(
          verifyResultWrapper.getErrorCode(), verifyResultWrapper.getErrorEnMessage());
    }
  }

  public Pages<BaseCredential> getRevokedCredList(Integer page, Integer size, String did) {
    ResponseData<Pages<BaseCredential>> baseCredentialWrapper = null;
    try {
      baseCredentialWrapper = credentialService.getRevokedCredList(page, size, did);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (baseCredentialWrapper == null || baseCredentialWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(baseCredentialWrapper.getErrorCode())) {
      return baseCredentialWrapper.getResult();
    } else {
      throw new DidException(
          baseCredentialWrapper.getErrorCode(), baseCredentialWrapper.getErrorEnMessage());
    }
  }
}
