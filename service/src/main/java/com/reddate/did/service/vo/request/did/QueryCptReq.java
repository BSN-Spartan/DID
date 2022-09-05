package com.reddate.did.service.vo.request.did;

import com.reddate.did.service.vo.request.BaseReq;

import java.io.Serializable;

public class QueryCptReq extends BaseReq implements Serializable{

	private Long cptId;

	private String token;

	private String key;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getCptId() {
		return cptId;
	}

	public void setCptId(Long cptId) {
		this.cptId = cptId;
	}
}
