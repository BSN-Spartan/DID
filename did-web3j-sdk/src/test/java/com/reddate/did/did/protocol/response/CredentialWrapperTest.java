package com.reddate.did.did.protocol.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import com.reddate.did.protocol.response.CredentialWrapper;
import org.junit.Test;

public class CredentialWrapperTest {
  @Test
  public void testSetContext() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setContext("Context");
    assertEquals("Context", credentialWrapper.getContext());
  }

  @Test
  public void testSetId() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setId("42");
    assertEquals("42", credentialWrapper.getId());
  }

  @Test
  public void testSetType() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setType("Type");
    assertEquals("Type", credentialWrapper.getType());
  }

  @Test
  public void testSetCptId() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setCptId(123L);
    assertEquals(123L, credentialWrapper.getCptId().longValue());
  }

  @Test
  public void testSetIssuerDid() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setIssuerDid("Issuer Did");
    assertEquals("Issuer Did", credentialWrapper.getIssuerDid());
  }

  @Test
  public void testSetUserDid() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setUserDid("User Did");
    assertEquals("User Did", credentialWrapper.getUserDid());
  }

  @Test
  public void testSetExpirationDate() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setExpirationDate("2020-03-01");
    assertEquals("2020-03-01", credentialWrapper.getExpirationDate());
  }

  @Test
  public void testSetCreated() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setCreated("Jan 1, 2020 8:00am GMT+0100");
    assertEquals("Jan 1, 2020 8:00am GMT+0100", credentialWrapper.getCreated());
  }

  @Test
  public void testSetShortDesc() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setShortDesc("Short Desc");
    assertEquals("Short Desc", credentialWrapper.getShortDesc());
  }

  @Test
  public void testSetLongDesc() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setLongDesc("Long Desc");
    assertEquals("Long Desc", credentialWrapper.getLongDesc());
  }

  @Test
  public void testSetClaim() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
    credentialWrapper.setClaim(stringObjectMap);
    assertSame(stringObjectMap, credentialWrapper.getClaim());
  }

  @Test
  public void testSetProof() {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
    credentialWrapper.setProof(stringObjectMap);
    assertSame(stringObjectMap, credentialWrapper.getProof());
  }
}
