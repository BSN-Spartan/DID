package com.reddate.did.service.vo.request;

import java.io.Serializable;

public class CreateDidReq implements Serializable{

	private boolean onChain;

	public boolean isOnChain() {
		return onChain;
	}

	public void setOnChain(boolean onChain) {
		this.onChain = onChain;
	}

}
