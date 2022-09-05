package com.reddate.did.did.protocol.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import com.reddate.did.protocol.common.Credential;
import org.junit.Test;

public class CredentialTest {
  @Test
  public void testSetContext() {
    Credential credential = new Credential();
    credential.setContext("Context");
    assertEquals("Context", credential.getContext());
  }

  @Test
  public void testSetCptId() {
    Credential credential = new Credential();
    credential.setCptId(123L);
    assertEquals(123L, credential.getCptId().longValue());
  }

  @Test
  public void testSetType() {
    Credential credential = new Credential();
    credential.setType("Type");
    assertEquals("Type", credential.getType());
  }

  @Test
  public void testSetIssuerDid() {
    Credential credential = new Credential();
    credential.setIssuerDid("Issuer Did");
    assertEquals("Issuer Did", credential.getIssuerDid());
  }

  @Test
  public void testSetUserDid() {
    Credential credential = new Credential();
    credential.setUserDid("User Did");
    assertEquals("User Did", credential.getUserDid());
  }

  @Test
  public void testSetExpirationDate() {
    Credential credential = new Credential();
    credential.setExpirationDate("2020-03-01");
    assertEquals("2020-03-01", credential.getExpirationDate());
  }

  @Test
  public void testSetShortDesc() {
    Credential credential = new Credential();
    credential.setShortDesc("Short Desc");
    assertEquals("Short Desc", credential.getShortDesc());
  }

  @Test
  public void testSetLongDesc() {
    Credential credential = new Credential();
    credential.setLongDesc("Long Desc");
    assertEquals("Long Desc", credential.getLongDesc());
  }

  @Test
  public void testSetClaim() {
    Credential credential = new Credential();
    HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
    credential.setClaim(stringObjectMap);
    assertSame(stringObjectMap, credential.getClaim());
  }

  @Test
  public void testSetProof() {
    Credential credential = new Credential();
    HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
    credential.setProof(stringObjectMap);
    assertSame(stringObjectMap, credential.getProof());
  }
}
