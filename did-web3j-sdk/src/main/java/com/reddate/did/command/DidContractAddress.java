// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.command;

public class DidContractAddress {
  private String did;

  private String didProxy;

  private String didTrans;

  public String getDid() {
    return did;
  }

  public void setDid(String did) {
    this.did = did;
  }

  public String getDidProxy() {
    return didProxy;
  }

  public void setDidProxy(String didProxy) {
    this.didProxy = didProxy;
  }

  public String getDidTrans() {
    return didTrans;
  }

  public void setDidTrans(String didTrans) {
    this.didTrans = didTrans;
  }
}
