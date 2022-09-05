package com.reddate.did.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.request.CreateCredential;
import org.junit.Test;

public class CreateCredentialTest {
  @Test
  public void testSetCptId() {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(123L);
    assertEquals(123L, createCredential.getCptId().longValue());
  }

  @Test
  public void testSetIssuerDid() {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setIssuerDid("Issuer Did");
    assertEquals("Issuer Did", createCredential.getIssuerDid());
  }

  @Test
  public void testSetUserDid() {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setUserDid("User Did");
    assertEquals("User Did", createCredential.getUserDid());
  }

  @Test
  public void testSetExpirationDate() {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setExpirationDate("2020-03-01");
    assertEquals("2020-03-01", createCredential.getExpirationDate());
  }

  @Test
  public void testSetClaim() {
    CreateCredential createCredential = new CreateCredential();
    HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
    createCredential.setClaim(stringObjectMap);
    assertSame(stringObjectMap, createCredential.getClaim());
  }

  @Test
  public void testSetType() {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setType("Type");
    assertEquals("Type", createCredential.getType());
  }

  @Test
  public void testSetPrivateKey() {
    CreateCredential createCredential = new CreateCredential();
    KeyPair keyPair = new KeyPair();
    createCredential.setPrivateKey(keyPair);
    assertSame(keyPair, createCredential.getPrivateKey());
  }
}
