package com.reddate.did.service.vo.request.did;

import java.io.Serializable;

public class Proof implements Serializable {

	private String creator;
	
	private String type;
	
	private String signatureValue;

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSignatureValue() {
		return signatureValue;
	}

	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}
	

	
}
