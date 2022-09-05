// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.request;

import java.io.Serializable;

import com.reddate.did.protocol.common.Proof;

public class RevokeCredential implements Serializable {

  private String credId; // Document number that needs to be revoked

  private Long cptId; // Voucher template number

  private String did; // Did of the issuing party

  private String rovekeDate;

  private Proof proof;

  public String getCredId() {
    return credId;
  }

  public void setCredId(String credId) {
    this.credId = credId;
  }

  public Long getCptId() {
    return cptId;
  }

  public void setCptId(Long cptId) {
    this.cptId = cptId;
  }

  public String getDid() {
    return did;
  }

  public void setDid(String did) {
    this.did = did;
  }

  public Proof getProof() {
    return proof;
  }

  public void setProof(Proof proof) {
    this.proof = proof;
  }

  public String getRovekeDate() {
    return rovekeDate;
  }

  public void setRovekeDate(String rovekeDate) {
    this.rovekeDate = rovekeDate;
  }
}
