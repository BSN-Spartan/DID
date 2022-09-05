package com.reddate.did.service.vo.request.did;

import com.reddate.did.protocol.response.DidDocument;

public class VerifyDocumentReq {

    private DidDocument didDoc;

    public DidDocument getDidDoc() {
        return didDoc;
    }

    public void setDidDoc(DidDocument didDoc) {
        this.didDoc = didDoc;
    }

}
