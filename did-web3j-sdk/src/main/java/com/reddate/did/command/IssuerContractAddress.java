// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.command;

public class IssuerContractAddress {
  private String issuer;

  private String issuerProxy;

  private String issuerTrans;

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public String getIssuerProxy() {
    return issuerProxy;
  }

  public void setIssuerProxy(String issuerProxy) {
    this.issuerProxy = issuerProxy;
  }

  public String getIssuerTrans() {
    return issuerTrans;
  }

  public void setIssuerTrans(String issuerTrans) {
    this.issuerTrans = issuerTrans;
  }
}
