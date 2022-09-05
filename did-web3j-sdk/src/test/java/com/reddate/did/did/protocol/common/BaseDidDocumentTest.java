package com.reddate.did.did.protocol.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.reddate.did.protocol.common.BaseDidDocument;
import com.reddate.did.protocol.common.PublicKey;
import org.junit.Test;

public class BaseDidDocumentTest {
  @Test
  public void testSetContext() {
    BaseDidDocument baseDidDocument = new BaseDidDocument();
    baseDidDocument.setContext("Context");
    assertEquals("Context", baseDidDocument.getContext());
  }

  @Test
  public void testSetAuthentication() {
    BaseDidDocument baseDidDocument = new BaseDidDocument();
    PublicKey publicKey = new PublicKey();
    baseDidDocument.setAuthentication(publicKey);
    assertSame(publicKey, baseDidDocument.getAuthentication());
  }

  @Test
  public void testSetRecovery() {
    BaseDidDocument baseDidDocument = new BaseDidDocument();
    PublicKey publicKey = new PublicKey();
    baseDidDocument.setRecovery(publicKey);
    assertSame(publicKey, baseDidDocument.getRecovery());
  }
}
