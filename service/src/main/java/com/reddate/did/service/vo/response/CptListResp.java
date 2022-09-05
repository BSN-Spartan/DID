package com.reddate.did.service.vo.response;

import java.io.Serializable;

import com.reddate.did.protocol.response.CptInfo;
import com.reddate.did.protocol.response.Pages;

public class CptListResp implements Serializable {
    private Pages<CptInfo> pages;

    public Pages<CptInfo> getPages() {
        return pages;
    }

    public void setPages(Pages<CptInfo> pages) {
        this.pages = pages;
    }
}
