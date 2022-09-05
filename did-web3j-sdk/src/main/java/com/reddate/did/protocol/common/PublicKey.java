// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.common;

import java.io.Serializable;

public class PublicKey implements Serializable {

  private String type; // encryption algorithm

  private String publicKey; // Public key value

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }
}
