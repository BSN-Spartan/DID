package com.reddate.did.did.protocol.response;

import static org.junit.Assert.assertEquals;

import com.reddate.did.protocol.response.CptBaseInfo;
import org.junit.Test;

public class CptBaseInfoTest {
  @Test
  public void testSetCptId() {
    CptBaseInfo cptBaseInfo = new CptBaseInfo();
    cptBaseInfo.setCptId(123L);
    assertEquals(123L, cptBaseInfo.getCptId().longValue());
  }

  @Test
  public void testSetCptVersion() {
    CptBaseInfo cptBaseInfo = new CptBaseInfo();
    cptBaseInfo.setCptVersion(1);
    assertEquals(1, cptBaseInfo.getCptVersion().intValue());
  }
}
