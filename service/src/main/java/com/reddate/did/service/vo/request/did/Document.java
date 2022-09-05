package com.reddate.did.service.vo.request.did;

import java.io.Serializable;

public class Document implements Serializable{
	
	private String did;	
	
	private String version;	
	
	private String created;
	
	private String updated;
	
	private KeyInfo authentication;	
	
	private KeyInfo recovery;
	
	private Proof proof;

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

	public KeyInfo getAuthentication() {
		return authentication;
	}

	public void setAuthentication(KeyInfo authentication) {
		this.authentication = authentication;
	}

	public KeyInfo getRecovery() {
		return recovery;
	}

	public void setRecovery(KeyInfo recovery) {
		this.recovery = recovery;
	}

	public Proof getProof() {
		return proof;
	}

	public void setProof(Proof proof) {
		this.proof = proof;
	}
	
}
