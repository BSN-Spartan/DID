// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.request;

import java.io.Serializable;
import java.util.Map;

import com.reddate.did.protocol.common.JsonSchema;
import com.reddate.did.protocol.common.Proof;

public class RegisterCpt implements Serializable {

  private String did; // Did of the issuer of the voucher template

  private Map<String, JsonSchema> cptJsonSchema; // Map type Json Schema information

  private String title; // Voucher template title

  private String description; // Detailed description of the voucher template

  private String type; // Document Type: Proof

  private Proof proof;

  private Long cptId;

  private String create; // Creation time

  private String update; // Update time


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

  public Map<String, JsonSchema> getCptJsonSchema() {
    return cptJsonSchema;
  }

  public void setCptJsonSchema(Map<String, JsonSchema> cptJsonSchema) {
    this.cptJsonSchema = cptJsonSchema;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Proof getProof() {
    return proof;
  }

  public void setProof(Proof proof) {
    this.proof = proof;
  }

  public String getCreate() {
    return create;
  }

  public void setCreate(String create) {
    this.create = create;
  }

  public String getUpdate() {
    return update;
  }

  public void setUpdate(String update) {
    this.update = update;
  }
}
