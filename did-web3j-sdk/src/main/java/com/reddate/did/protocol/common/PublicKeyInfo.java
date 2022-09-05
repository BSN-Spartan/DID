// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.common;

import java.io.Serializable;

public class PublicKeyInfo implements Serializable {

  private String publicKeySign;

  private String publicKey;

  private String type;


  public String getPublicKeySign() {
    return publicKeySign;
  }

  public void setPublicKeySign(String publicKeySign) {
    this.publicKeySign = publicKeySign;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
