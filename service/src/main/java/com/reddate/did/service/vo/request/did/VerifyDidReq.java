package com.reddate.did.service.vo.request.did;



public class VerifyDidReq {

    private String did;
    
    private String didSign;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getDidSign() {
		return didSign;
	}

	public void setDidSign(String didSign) {
		this.didSign = didSign;
	}
	
}
