// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.repository;

import com.reddate.did.constant.ErrorCode;
import com.reddate.did.exception.DidException;
import com.reddate.did.protocol.common.AuthorityIssuer;
import com.reddate.did.protocol.common.RegisterAuthorityIssuer;
import com.reddate.did.protocol.request.RegisterCpt;
import com.reddate.did.protocol.request.RevokeCredential;
import com.reddate.did.protocol.response.CptBaseInfo;
import com.reddate.did.protocol.response.CptInfo;
import com.reddate.did.protocol.response.Pages;
import com.reddate.did.protocol.response.ResponseData;
import com.reddate.did.service.AuthorityIssuerService;
import com.reddate.did.service.impl.AuthorityIssuerServiceImpl;

public class IssuerRepository {

  private AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

  public Boolean registerAuthIssuer(RegisterAuthorityIssuer issuer) {
    ResponseData<Boolean> regsitRstWrapper = null;
    try {
      regsitRstWrapper = authorityIssuerService.registerAuthIssuer(issuer);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (regsitRstWrapper == null || regsitRstWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(regsitRstWrapper.getErrorCode())) {
      return regsitRstWrapper.getResult();
    } else {
      throw new DidException(regsitRstWrapper.getErrorCode(), regsitRstWrapper.getErrorEnMessage());
    }
  }

  public Pages<AuthorityIssuer> queryAuthIssuerList(Integer page, Integer size, String did) {
    ResponseData<Pages<AuthorityIssuer>> authListWrapper = null;
    try {
      authListWrapper = authorityIssuerService.queryAuthIssuerList(page, size, did);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (authListWrapper == null || authListWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(authListWrapper.getErrorCode())) {
      return authListWrapper.getResult();
    } else {
      throw new DidException(authListWrapper.getErrorCode(), authListWrapper.getErrorEnMessage());
    }
  }

  public CptBaseInfo registerCpt(RegisterCpt registerCpt) {
    ResponseData<CptBaseInfo> cptWrapper = null;
    try {
      cptWrapper = authorityIssuerService.registerCpt(registerCpt);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (cptWrapper == null || cptWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(cptWrapper.getErrorCode())) {
      return cptWrapper.getResult();
    } else {
      throw new DidException(cptWrapper.getErrorCode(), cptWrapper.getErrorEnMessage());
    }
  }

  public Pages<CptInfo> queryCptListByDid(Integer page, Integer size, String did) {
    ResponseData<Pages<CptInfo>> cptListWrapper = null;
    try {
      cptListWrapper = authorityIssuerService.queryCptListByDid(page, size, did);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (cptListWrapper == null || cptListWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(cptListWrapper.getErrorCode())) {
      return cptListWrapper.getResult();
    } else {
      throw new DidException(cptListWrapper.getErrorCode(), cptListWrapper.getErrorEnMessage());
    }
  }

  public CptInfo queryCptById(Long cptId) {
    ResponseData<CptInfo> cptListWrapper = null;
    try {
      cptListWrapper = authorityIssuerService.queryCptById(cptId);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (cptListWrapper == null || cptListWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(cptListWrapper.getErrorCode())) {
      return cptListWrapper.getResult();
    } else {
      throw new DidException(cptListWrapper.getErrorCode(), cptListWrapper.getErrorEnMessage());
    }
  }

  public CptBaseInfo updateCpt(RegisterCpt registerCpt) {
    ResponseData<CptBaseInfo> cptWrapper = null;
    try {
      cptWrapper = authorityIssuerService.updateCpt(registerCpt);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (cptWrapper == null || cptWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(cptWrapper.getErrorCode())) {
      return cptWrapper.getResult();
    } else {
      throw new DidException(cptWrapper.getErrorCode(), cptWrapper.getErrorEnMessage());
    }
  }

  public Boolean revokeCredential(RevokeCredential cred) {
    ResponseData<Boolean> revokeResult = null;
    try {
      revokeResult = authorityIssuerService.revokeCredential(cred);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (revokeResult == null || revokeResult.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(revokeResult.getErrorCode())) {
      return revokeResult.getResult();
    } else {
      throw new DidException(revokeResult.getErrorCode(), revokeResult.getErrorEnMessage());
    }
  }
}
