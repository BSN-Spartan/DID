package com.reddate.did.service.vo.request.did;

import java.io.Serializable;

import com.reddate.did.service.vo.request.BaseReq;

public class RestDocAuth implements Serializable{

	private Document didDoc;
	
	private String authPubKeySign;

	public Document getDidDoc() {
		return didDoc;
	}

	public void setDidDoc(Document didDoc) {
		this.didDoc = didDoc;
	}

	public String getAuthPubKeySign() {
		return authPubKeySign;
	}

	public void setAuthPubKeySign(String authPubKeySign) {
		this.authPubKeySign = authPubKeySign;
	}
	
	
	
}
