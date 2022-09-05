package com.reddate.did.service.vo.request;

import java.io.Serializable;

public class BaseReq implements Serializable{

	protected String address;
	
	protected String sign;

	protected String chainType;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getChainType() {
		return chainType;
	}

	public void setChainType(String chainType) {
		this.chainType = chainType;
	}
	
	
	
	
}
