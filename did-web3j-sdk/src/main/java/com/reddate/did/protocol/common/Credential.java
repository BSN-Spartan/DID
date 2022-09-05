// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.common;

import java.io.Serializable;
import java.util.Map;

public class Credential extends BaseCredential implements Serializable {

  private String context; // version number

  private Long cptId; // Voucher template number

  private String type; // Reserve the types of certificates that can be issued

  private String issuerDid; // Did of the issuing party

  private String userDid; // Did of the user who applied for the credential

  private String expirationDate; // Expire date

  private String shortDesc; // Brief description of the voucher

  private String longDesc; // detailed description of the voucher

  private Map<String, Object> claim; // Declared data

  private Map<String, Object> proof; // Signature data structure

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public Long getCptId() {
    return cptId;
  }

  public void setCptId(Long cptId) {
    this.cptId = cptId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getIssuerDid() {
    return issuerDid;
  }

  public void setIssuerDid(String issuerDid) {
    this.issuerDid = issuerDid;
  }

  public String getUserDid() {
    return userDid;
  }

  public void setUserDid(String userDid) {
    this.userDid = userDid;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public String getShortDesc() {
    return shortDesc;
  }

  public void setShortDesc(String shortDesc) {
    this.shortDesc = shortDesc;
  }

  public String getLongDesc() {
    return longDesc;
  }

  public void setLongDesc(String longDesc) {
    this.longDesc = longDesc;
  }

  public Map<String, Object> getClaim() {
    return claim;
  }

  public void setClaim(Map<String, Object> claim) {
    this.claim = claim;
  }

  public Map<String, Object> getProof() {
    return proof;
  }

  public void setProof(Map<String, Object> proof) {
    this.proof = proof;
  }
}
