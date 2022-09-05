// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.protocol.response;

import java.io.Serializable;

public class CptBaseInfo implements Serializable {

  private Long cptId; // Voucher template number

  private Integer cptVersion; // Voucher template version

  public Long getCptId() {
    return cptId;
  }

  public void setCptId(Long cptId) {
    this.cptId = cptId;
  }

  public Integer getCptVersion() {
    return cptVersion;
  }

  public void setCptVersion(Integer cptVersion) {
    this.cptVersion = cptVersion;
  }
}
