package com.reddate.did.service.vo.response;


import com.reddate.did.protocol.common.BaseCredential;
import com.reddate.did.protocol.response.Pages;

import java.io.Serializable;

public class RevokeCredListWrapperResp implements Serializable {

    private Pages<BaseCredential> pages;

    public Pages<BaseCredential> getPages() {
        return pages;
    }

    public void setPages(Pages<BaseCredential> pages) {
        this.pages = pages;
    }
}
