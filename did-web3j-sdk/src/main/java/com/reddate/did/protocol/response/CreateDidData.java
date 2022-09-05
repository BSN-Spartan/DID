// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.response;

import java.io.Serializable;

import com.reddate.did.protocol.common.KeyPair;

public class CreateDidData implements Serializable {

  private String did; // Did created

  private KeyPair authKeyInfo; // Master public private key information

  private KeyPair recyKeyInfo; // Spare public private key information

  private DidDocument
      didDocument; // Not on the chain, the did document information will be returned

  public String getDid() {
    return did;
  }

  public void setDid(String did) {
    this.did = did;
  }

  public KeyPair getAuthKeyInfo() {
    return authKeyInfo;
  }

  public void setAuthKeyInfo(KeyPair authKeyInfo) {
    this.authKeyInfo = authKeyInfo;
  }

  public KeyPair getRecyKeyInfo() {
    return recyKeyInfo;
  }

  public void setRecyKeyInfo(KeyPair recyKeyInfo) {
    this.recyKeyInfo = recyKeyInfo;
  }

  public DidDocument getDidDocument() {
    return didDocument;
  }

  public void setDidDocument(DidDocument didDocument) {
    this.didDocument = didDocument;
  }
}
