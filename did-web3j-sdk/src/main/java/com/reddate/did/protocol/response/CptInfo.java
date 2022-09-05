// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.response;

import java.io.Serializable;
import java.util.Map;

import com.reddate.did.protocol.common.JsonSchema;
import com.reddate.did.protocol.common.Proof;

public class CptInfo extends CptBaseInfo implements Serializable {

  private Map<String, JsonSchema> cptJsonSchema; // Field list of voucher template

  private String title; // A short description of the voucher template

  private String description; // Detailed description of the voucher template

  private String publisherDid; // Did of the user who created the voucher template

  private Proof proof; // //Signature data structure

  private String create; // Creation time

  private String update; // Update time

  public String getPublisherDid() {
    return publisherDid;
  }

  public void setPublisherDid(String publisherDid) {
    this.publisherDid = publisherDid;
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

  public Map<String, JsonSchema> getCptJsonSchema() {
    return cptJsonSchema;
  }

  public void setCptJsonSchema(Map<String, JsonSchema> cptJsonSchema) {
    this.cptJsonSchema = cptJsonSchema;
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
