package com.reddate.did.service.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

public class CptBaseInfoResp implements Serializable {
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long cptId;
    private Integer cptVersion;

    public CptBaseInfoResp() {
    }

    public Long getCptId() {
        return this.cptId;
    }

    public void setCptId(Long cptId) {
        this.cptId = cptId;
    }

    public Integer getCptVersion() {
        return this.cptVersion;
    }

    public void setCptVersion(Integer cptVersion) {
        this.cptVersion = cptVersion;
    }
}
