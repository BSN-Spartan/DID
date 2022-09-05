// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service;

import java.math.BigInteger;

import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.request.ResetDid;
import com.reddate.did.protocol.response.CreateDidData;
import com.reddate.did.protocol.response.DidDocument;
import com.reddate.did.protocol.response.ResponseData;

public interface DidService {

  ResponseData<CreateDidData> createDid(Boolean isStorageOnChain);

  ResponseData<Boolean> storeDidDocumentOnChain(DidDocument didDocument, PublicKey publicKey);

  ResponseData<DidDocument> getDidDocument(String did);

  ResponseData<Boolean> verifyDidDocument(DidDocument didDocument, PublicKey publicKey);

  ResponseData<KeyPair> resetDidAuth(ResetDid restDid);

  ResponseData<BigInteger> getBlockNumber();

  ResponseData<Integer> getGroupId();
}
