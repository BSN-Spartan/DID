// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.repository;

import java.math.BigInteger;

import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.request.ResetDid;
import com.reddate.did.protocol.response.CreateDidData;
import com.reddate.did.protocol.response.DidDocument;
import com.reddate.did.protocol.response.ResponseData;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.exception.DidException;
import com.reddate.did.service.DidService;
import com.reddate.did.service.impl.DidServiceImpl;

public class DidRepository {

  private DidService didService = new DidServiceImpl();

  public CreateDidData createDid(Boolean isStorageOnChain) {
    ResponseData<CreateDidData> didInfo = null;
    try {
      didInfo = didService.createDid(isStorageOnChain);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (didInfo == null || didInfo.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(didInfo.getErrorCode())) {
      return didInfo.getResult();
    } else {
      throw new DidException(didInfo.getErrorCode(), didInfo.getErrorEnMessage());
    }
  }

  public Boolean storeDidDocumentOnChain(DidDocument didDocument, PublicKey publicKey) {
    ResponseData<Boolean> chainDidDocumentWrapper = null;
    try {
      chainDidDocumentWrapper = didService.storeDidDocumentOnChain(didDocument, publicKey);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (chainDidDocumentWrapper == null || chainDidDocumentWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(chainDidDocumentWrapper.getErrorCode())) {
      return chainDidDocumentWrapper.getResult();
    } else {
      throw new DidException(
          chainDidDocumentWrapper.getErrorCode(), chainDidDocumentWrapper.getErrorEnMessage());
    }
  }

  public DidDocument getDidDocument(String did) {
    ResponseData<DidDocument> didDocumentWrapper = null;
    try {
      didDocumentWrapper = didService.getDidDocument(did);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (didDocumentWrapper == null || didDocumentWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(didDocumentWrapper.getErrorCode())) {
      return didDocumentWrapper.getResult();
    } else {
      throw new DidException(
          didDocumentWrapper.getErrorCode(), didDocumentWrapper.getErrorEnMessage());
    }
  }

  public Boolean verifyDidDocument(DidDocument didDocument, PublicKey publicKey) {
    ResponseData<Boolean> verifyDidDocumentWrapper = null;
    try {
      verifyDidDocumentWrapper = didService.verifyDidDocument(didDocument, publicKey);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (verifyDidDocumentWrapper == null || verifyDidDocumentWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.DID_DOCUMENT_VALIDATION_SUCCESS
        .getCode()
        .equals(verifyDidDocumentWrapper.getErrorCode())) {
      return verifyDidDocumentWrapper.getResult();
    } else {
      throw new DidException(
          verifyDidDocumentWrapper.getErrorCode(), verifyDidDocumentWrapper.getErrorEnMessage());
    }
  }

  public KeyPair resetDidAuth(ResetDid restDid) {
    ResponseData<KeyPair> keyPairWrapper = null;
    try {
      keyPairWrapper = didService.resetDidAuth(restDid);
    } catch (Exception e) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (keyPairWrapper == null || keyPairWrapper.getErrorCode() == null) {
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }

    if (ErrorCode.SUCCESS.getCode().equals(keyPairWrapper.getErrorCode())) {
      return keyPairWrapper.getResult();
    } else {
      throw new DidException(keyPairWrapper.getErrorCode(), keyPairWrapper.getErrorEnMessage());
    }
  }

  public BigInteger getBlockNumber() {
    ResponseData<BigInteger> result = null;
    try {
      result = didService.getBlockNumber();
    } catch (Exception e) {
      e.printStackTrace();
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }
    if (ErrorCode.SUCCESS.getCode().equals(result.getErrorCode())) {
      return result.getResult();
    } else {
      throw new DidException(result.getErrorCode(), result.getErrorEnMessage());
    }
  }

  public Integer getGroupId() {
    ResponseData<Integer> result = null;
    try {
      result = didService.getGroupId();
    } catch (Exception e) {
      e.printStackTrace();
      throw new DidException(
          ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getEnMessage());
    }
    if (ErrorCode.SUCCESS.getCode().equals(result.getErrorCode())) {
      return result.getResult();
    } else {
      throw new DidException(result.getErrorCode(), result.getErrorEnMessage());
    }
  }
}
