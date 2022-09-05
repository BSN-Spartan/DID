package com.reddate.did.did.protocol.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.reddate.did.protocol.common.DidInfo;
import com.reddate.did.protocol.common.PublicKey;
import org.junit.Test;

public class DidInfoTest {
  @Test
  public void testSetContext() {
    DidInfo didInfo = new DidInfo();
    didInfo.setContext("Context");
    assertEquals("Context", didInfo.getContext());
  }

  @Test
  public void testSetPublicKey() {
    DidInfo didInfo = new DidInfo();
    PublicKey publicKey = new PublicKey();
    didInfo.setPublicKey(publicKey);
    assertSame(publicKey, didInfo.getPublicKey());
  }

  @Test
  public void testSetAuthentication() {
    DidInfo didInfo = new DidInfo();
    String[] stringArray = new String[] {"foo", "foo", "foo"};
    didInfo.setAuthentication(stringArray);
    assertSame(stringArray, didInfo.getAuthentication());
  }

  @Test
  public void testSetRecovery() {
    DidInfo didInfo = new DidInfo();
    String[] stringArray = new String[] {"foo", "foo", "foo"};
    didInfo.setRecovery(stringArray);
    assertSame(stringArray, didInfo.getRecovery());
  }
}
