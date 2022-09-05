// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.request;

import java.io.Serializable;
import java.util.Map;

import com.reddate.did.protocol.common.KeyPair;

public class CreateCredential implements Serializable {

  private Long cptId; // Voucher template number

  private String issuerDid; // Did of the issuer of the certificate template

  private String userDid; // Did of the user who applied for the credential

  private String expirationDate; // Voucher expiration date

  private Map<String, Object>
      claim; // The claim data, the claim data need to correspond to the format of the cpt template

  private String type; // Voucher type: Proof

  private KeyPair privateKey; // Public and private key information, refer to Key Pair below

  private String shortDesc; // Brief description of the voucher

  private String longDesc; // Detailed description of the voucher

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

  public Map<String, Object> getClaim() {
    return claim;
  }

  public void setClaim(Map<String, Object> claim) {
    this.claim = claim;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public KeyPair getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(KeyPair privateKey) {
    this.privateKey = privateKey;
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
}
