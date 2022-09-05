// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.common;

public class BaseDidDocument {

  private String context; // Format standard

  private PublicKey authentication; // Authenticated public key

  private PublicKey recovery; // Standby public key

  public void setContext(String context) {
    this.context = context;
  }

  public String getContext() {
    return context;
  }

  public void setAuthentication(PublicKey authentication) {
    this.authentication = authentication;
  }

  public PublicKey getAuthentication() {
    return authentication;
  }

  public void setRecovery(PublicKey recovery) {
    this.recovery = recovery;
  }

  public PublicKey getRecovery() {
    return recovery;
  }
}
