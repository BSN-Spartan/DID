// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.common;

import java.io.Serializable;

public class DidInfo implements Serializable {

  private String context; // did Specification

  private PublicKey publicKey; // Public key list

  private String[]
      authentication; // The master public key list of did, storing the id in the public key

  private String[] recovery; // List of did's alternate public keys,

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public PublicKey getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
  }

  public String[] getAuthentication() {
    return authentication;
  }

  public void setAuthentication(String[] authentication) {
    this.authentication = authentication;
  }

  public String[] getRecovery() {
    return recovery;
  }

  public void setRecovery(String[] recovery) {
    this.recovery = recovery;
  }
}
