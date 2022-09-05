// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.response;

import java.io.Serializable;
import java.util.Map;

import com.reddate.did.protocol.common.BaseCredential;

public class CredentialWrapper extends BaseCredential implements Serializable {

  private String context; // VERSION NUMBER

  private String id; // Certificate Number

  private String type; // DOCUMENT TYPE: Proof

  private Long cptId; // cpt SERIAL NUMBER

  private String issuerDid; // Voucher issuer did

  private String userDid; // Of the user requesting the credential did

  private String expirationDate; // Expiration log

  private String created; // Creation time

  private String shortDesc; // Brief description of the voucher

  private String longDesc; // Detailed description of the voucher

  private Map<String, Object> claim; // Declared data

  private Map<String, Object> proof; // Signature data structure

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getCptId() {
    return cptId;
  }

  public void setCptId(Long cptId) {
    this.cptId = cptId;
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

  @Override
  public String getCreated() {
    return created;
  }

  @Override
  public void setCreated(String created) {
    this.created = created;
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
    return this.claim;
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
