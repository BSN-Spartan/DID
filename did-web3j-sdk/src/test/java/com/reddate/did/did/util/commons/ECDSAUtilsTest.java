package com.reddate.did.did.util.commons;

import static org.junit.Assert.assertEquals;

import com.reddate.did.util.commons.ECDSAUtils;
import org.junit.Assert;
import org.junit.Test;

public class ECDSAUtilsTest {
  @Test
  public void testCreateKey() throws Exception {
    Assert.assertEquals(ECDSAUtils.type, ECDSAUtils.createKey().getType());
  }
}
