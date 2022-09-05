// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.request;

import java.io.Serializable;

import com.reddate.did.protocol.response.DidDocument;
import com.reddate.did.protocol.common.PublicKeyInfo;

public class ResetDid implements Serializable {

  private DidDocument didDocument;

  private PublicKeyInfo authPublicKeyInfo;


  public DidDocument getDidDocument() {
    return didDocument;
  }

  public void setDidDocument(DidDocument didDocument) {
    this.didDocument = didDocument;
  }

  public PublicKeyInfo getAuthPublicKeyInfo() {
    return authPublicKeyInfo;
  }

  public void setAuthPublicKeyInfo(PublicKeyInfo authPublicKeyInfo) {
    this.authPublicKeyInfo = authPublicKeyInfo;
  }
}
