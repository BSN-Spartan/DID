package com.reddate.did.did.protocol.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.response.CreateDidData;
import com.reddate.did.protocol.response.DidDocument;
import org.junit.Test;

public class CreateDidDataTest {
  @Test
  public void testSetDid() {
    CreateDidData createDidData = new CreateDidData();
    createDidData.setDid("Did");
    assertEquals("Did", createDidData.getDid());
  }

  @Test
  public void testSetAuthKeyInfo() {
    CreateDidData createDidData = new CreateDidData();
    KeyPair keyPair = new KeyPair();
    createDidData.setAuthKeyInfo(keyPair);
    assertSame(keyPair, createDidData.getAuthKeyInfo());
  }

  @Test
  public void testSetRecyKeyInfo() {
    CreateDidData createDidData = new CreateDidData();
    KeyPair keyPair = new KeyPair();
    createDidData.setRecyKeyInfo(keyPair);
    assertSame(keyPair, createDidData.getRecyKeyInfo());
  }

  @Test
  public void testSetDidDocument() {
    CreateDidData createDidData = new CreateDidData();
    DidDocument didDocument = new DidDocument();
    createDidData.setDidDocument(didDocument);
    assertSame(didDocument, createDidData.getDidDocument());
  }
}
