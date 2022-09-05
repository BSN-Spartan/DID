package com.reddate.did.service.vo.request;

import java.io.Serializable;

public class DeleteReq extends BaseReq implements Serializable{

	private String docId;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
	
}
