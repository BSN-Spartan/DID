package com.reddate.did.service.vo.response.did;

import java.io.Serializable;

public class ContractAddrResp implements Serializable{

	private String did;

	private String didProxy;

	private String didTrans;

	private String cpt;

	private String cptProxy;

	private String cptTrans;

	private String issuer;

	private String issuerProxy;

	private String issuerTrans;

	public String getDidProxy() {
		return didProxy;
	}

	public void setDidProxy(String didProxy) {
		this.didProxy = didProxy;
	}

	public String getDidTrans() {
		return didTrans;
	}

	public void setDidTrans(String didTrans) {
		this.didTrans = didTrans;
	}

	public String getCptProxy() {
		return cptProxy;
	}

	public void setCptProxy(String cptProxy) {
		this.cptProxy = cptProxy;
	}

	public String getCptTrans() {
		return cptTrans;
	}

	public void setCptTrans(String cptTrans) {
		this.cptTrans = cptTrans;
	}

	public String getIssuerProxy() {
		return issuerProxy;
	}

	public void setIssuerProxy(String issuerProxy) {
		this.issuerProxy = issuerProxy;
	}

	public String getIssuerTrans() {
		return issuerTrans;
	}

	public void setIssuerTrans(String issuerTrans) {
		this.issuerTrans = issuerTrans;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getCpt() {
		return cpt;
	}

	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	
	
}
