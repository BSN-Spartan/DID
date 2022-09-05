package com.reddate.did.service.vo.request;

import java.io.Serializable;

import com.reddate.did.protocol.common.Credential;
import com.reddate.did.protocol.common.PublicKey;

public class VerifyCredentialReq implements Serializable{

	private Credential credential;
	
	private PublicKey publicKey;

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	
	
	
}
