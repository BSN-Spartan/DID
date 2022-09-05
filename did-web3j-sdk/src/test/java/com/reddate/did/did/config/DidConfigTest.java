package com.reddate.did.did.config;

import static org.junit.Assert.assertEquals;

import com.reddate.did.config.DidConfig;
import org.junit.Test;

public class DidConfigTest {
  @Test
  public void testSetType() {
    DidConfig didConfig = new DidConfig();
    didConfig.setType("Type");
    assertEquals("Type", didConfig.getType());
  }
}
