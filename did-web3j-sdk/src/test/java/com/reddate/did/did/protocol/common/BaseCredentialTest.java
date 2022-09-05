package com.reddate.did.did.protocol.common;

import static org.junit.Assert.assertEquals;

import com.reddate.did.protocol.common.BaseCredential;
import org.junit.Test;

public class BaseCredentialTest {
  @Test
  public void testSetId() {
    BaseCredential baseCredential = new BaseCredential();
    baseCredential.setId("42");
    assertEquals("42", baseCredential.getId());
  }

  @Test
  public void testSetCreated() {
    BaseCredential baseCredential = new BaseCredential();
    baseCredential.setCreated("Jan 1, 2020 8:00am GMT+0100");
    assertEquals("Jan 1, 2020 8:00am GMT+0100", baseCredential.getCreated());
  }
}
