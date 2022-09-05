// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.common;

import java.io.Serializable;

public class Proof implements Serializable {

  private String type; // Encryption algorithm of signature data

  private String creator; // The did of the user who created the signature data

  private String signatureValue; // Signature value

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public String getSignatureValue() {
    return signatureValue;
  }

  public void setSignatureValue(String signatureValue) {
    this.signatureValue = signatureValue;
  }
}
