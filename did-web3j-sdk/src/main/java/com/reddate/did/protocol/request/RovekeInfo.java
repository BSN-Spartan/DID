// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.request;

import java.io.Serializable;

import com.reddate.did.protocol.common.Proof;

public class RovekeInfo implements Serializable {

  private String rovekeDate;

  private String credId;

  private Proof proof;

  public String getRovekeDate() {
    return rovekeDate;
  }

  public void setRovekeDate(String rovekeDate) {
    this.rovekeDate = rovekeDate;
  }

  public String getCredId() {
    return credId;
  }

  public void setCredId(String credId) {
    this.credId = credId;
  }

  public Proof getProof() {
    return proof;
  }

  public void setProof(Proof proof) {
    this.proof = proof;
  }
}
