// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.response;

import java.io.Serializable;

import com.reddate.did.protocol.common.Proof;
import com.reddate.did.protocol.common.PublicKey;

public class DidDocument implements Serializable {

  private String did; // Did of the document owner

  private String version; // The version of the document

  private String created; // Created time of the document

  private String updated; // Update time of the document

  private PublicKey authentication; // Authenticated public key

  private PublicKey recovery; // Standby public key

  private Proof proof; // Signature data structure


  public String getDid() {
    return did;
  }

  public void setDid(String did) {
    this.did = did;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
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

  public Proof getProof() {
    return proof;
  }

  public void setProof(Proof proof) {
    this.proof = proof;
  }
}
