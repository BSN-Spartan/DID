package com.reddate.did.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DidSdkPrivateKeyGenerateTest {
  @Test
  public void testGetKey() throws Exception {
    new DidSdkPrivateKeyGenerate();
    assertEquals("Secp256k1", DidSdkPrivateKeyGenerate.getKey().getType());
  }
}
