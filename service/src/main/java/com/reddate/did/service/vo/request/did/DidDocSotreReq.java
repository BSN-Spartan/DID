package com.reddate.did.service.vo.request.did;

import java.io.Serializable;

import com.reddate.did.service.vo.request.BaseReq;

public class DidDocSotreReq implements Serializable{

	private Document didDoc;
	

	public Document getDidDoc() {
		return didDoc;
	}

	public void setDidDoc(Document didDoc) {
		this.didDoc = didDoc;
	}
	
}
