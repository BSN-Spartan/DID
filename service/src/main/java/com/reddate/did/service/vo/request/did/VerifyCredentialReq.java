package com.reddate.did.service.vo.request.did;

import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.protocol.response.CredentialWrapper;

import java.io.Serializable;

public class VerifyCredentialReq implements Serializable {

    private CredentialWrapper credentialWrapper;

    private PublicKey publicKey;

    public CredentialWrapper getCredentialWrapper() {
        return credentialWrapper;
    }

    public void setCredentialWrapper(CredentialWrapper credentialWrapper) {
        this.credentialWrapper = credentialWrapper;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
