// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.common;

public class RegisterAuthorityIssuer extends AuthorityIssuer {

  private PublicKeyInfo publicKeyInfo;

  public PublicKeyInfo getPublicKeyInfo() {
    return publicKeyInfo;
  }

  public void setPublicKeyInfo(PublicKeyInfo publicKeyInfo) {
    this.publicKeyInfo = publicKeyInfo;
  }
}
