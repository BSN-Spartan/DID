package com.reddate.did.did.protocol.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.reddate.did.protocol.common.Proof;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.response.DidDocument;
import org.junit.Test;

public class DidDocumentTest {
  @Test
  public void testSetDid() {
    DidDocument didDocument = new DidDocument();
    didDocument.setDid("Did");
    assertEquals("Did", didDocument.getDid());
  }

  @Test
  public void testSetVersion() {
    DidDocument didDocument = new DidDocument();
    didDocument.setVersion("1.0.2");
    assertEquals("1.0.2", didDocument.getVersion());
  }

  @Test
  public void testSetCreated() {
    DidDocument didDocument = new DidDocument();
    didDocument.setCreated("Jan 1, 2020 8:00am GMT+0100");
    assertEquals("Jan 1, 2020 8:00am GMT+0100", didDocument.getCreated());
  }

  @Test
  public void testSetUpdated() {
    DidDocument didDocument = new DidDocument();
    didDocument.setUpdated("2020-03-01");
    assertEquals("2020-03-01", didDocument.getUpdated());
  }

  @Test
  public void testSetAuthentication() {
    DidDocument didDocument = new DidDocument();
    PublicKey publicKey = new PublicKey();
    didDocument.setAuthentication(publicKey);
    assertSame(publicKey, didDocument.getAuthentication());
  }

  @Test
  public void testSetRecovery() {
    DidDocument didDocument = new DidDocument();
    PublicKey publicKey = new PublicKey();
    didDocument.setRecovery(publicKey);
    assertSame(publicKey, didDocument.getRecovery());
  }

  @Test
  public void testSetProof() {
    DidDocument didDocument = new DidDocument();
    Proof proof = new Proof();
    didDocument.setProof(proof);
    assertSame(proof, didDocument.getProof());
  }
}
