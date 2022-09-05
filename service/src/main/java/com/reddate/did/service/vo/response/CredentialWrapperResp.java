package com.reddate.did.service.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reddate.did.protocol.common.BaseCredential;

import java.io.Serializable;
import java.util.Map;

public class CredentialWrapperResp extends BaseCredential implements Serializable {
    private String context;
    private String id;
    private String type;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long cptId;
    private String issuerDid;
    private String userDid;
    private String expirationDate;
    private String created;
    private String shortDesc;
    private String longDesc;
    private Map<String, Object> claim;
    private Map<String, Object> proof;

    public CredentialWrapperResp() {
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCptId() {
        return this.cptId;
    }

    public void setCptId(Long cptId) {
        this.cptId = cptId;
    }

    public String getIssuerDid() {
        return this.issuerDid;
    }

    public void setIssuerDid(String issuerDid) {
        this.issuerDid = issuerDid;
    }

    public String getUserDid() {
        return this.userDid;
    }

    public void setUserDid(String userDid) {
        this.userDid = userDid;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getShortDesc() {
        return this.shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return this.longDesc;
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
        return this.proof;
    }

    public void setProof(Map<String, Object> proof) {
        this.proof = proof;
    }
}
