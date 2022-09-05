package com.reddate.did.did.protocol.common;

import static org.junit.Assert.assertEquals;

import com.reddate.did.protocol.common.PublicKey;
import org.junit.Test;

public class PublicKeyTest {
  @Test
  public void testSetType() {
    PublicKey publicKey = new PublicKey();
    publicKey.setType("Type");
    assertEquals("Type", publicKey.getType());
  }

  @Test
  public void testSetPublicKeyHex() {
    PublicKey publicKey = new PublicKey();
    publicKey.setPublicKey("0123456789ABCDEF");
    assertEquals("0123456789ABCDEF", publicKey.getPublicKey());
  }
}
