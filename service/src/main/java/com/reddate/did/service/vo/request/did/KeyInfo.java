package com.reddate.did.service.vo.request.did;

import java.io.Serializable;

public class KeyInfo implements Serializable{

	private String publicKey;
	
	private String type;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
