package com.reddate.did.did.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.reddate.did.constant.ErrorCode;
import com.reddate.did.protocol.common.Credential;
import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.request.CreateCredential;
import com.reddate.did.protocol.response.CredentialWrapper;
import com.reddate.did.util.CredentialUtils;
import com.reddate.did.util.commons.ECDSAUtils;

public class CredentialUtilsTest {
  @Test
  public void testIsCreateCredentialArgsValid() throws Exception {
    assertEquals(ErrorCode.PARAMETER_IS_EMPTY, CredentialUtils.isCreateCredentialArgsValid(null));
  }

  @Test
  public void testIsCreateCredentialArgsValid2() throws Exception {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(123L);
    assertEquals(
        ErrorCode.INVALID_DID, CredentialUtils.isCreateCredentialArgsValid(createCredential));
  }

  @Test
  public void testIsCreateCredentialArgsValid3() throws Exception {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setUserDid("User Did");
    createCredential.setCptId(123L);
    assertEquals(
        ErrorCode.INVALID_DID, CredentialUtils.isCreateCredentialArgsValid(createCredential));
  }

  @Test
  public void testIsCreateCredentialArgsValid4() throws Exception {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setUserDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    createCredential.setCptId(123L);
    createCredential.setIssuerDid("Issuer Did");
    assertEquals(
        ErrorCode.INVALID_DID, CredentialUtils.isCreateCredentialArgsValid(createCredential));
  }

  @Test
  public void testIsCreateCredentialArgsValid5() throws Exception {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setUserDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    createCredential.setCptId(123L);
    createCredential.setIssuerDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    assertEquals(
        ErrorCode.PARAMETER_IS_EMPTY,
        CredentialUtils.isCreateCredentialArgsValid(createCredential));
  }

  @Test
  public void testIsCreateCredentialArgsValid6() throws Exception {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setUserDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    createCredential.setCptId(123L);
    createCredential.setIssuerDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    createCredential.setClaim(new HashMap<>());
    assertEquals(
        ErrorCode.PARAMETER_IS_EMPTY,
        CredentialUtils.isCreateCredentialArgsValid(createCredential));
  }

  @Test
  public void testIsCreateCredentialArgsValid7() throws Exception {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setUserDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    createCredential.setCptId(123L);
    createCredential.setIssuerDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    Map<String, Object> claim = new HashMap<>();
    claim.put("test", "test");
    createCredential.setClaim(claim);
    KeyPair privateKey = new KeyPair();
    privateKey.setType(ECDSAUtils.type);
    privateKey.setPublicKey(
        "3411042173356754019773463267061677472969673694580781499143022094312348789299339570305441819287012040459157648511083108997557206395852634893851229402672341");
    privateKey.setPrivateKey(
        "101224078151474551219844497822474569775160888151198806712994905541367378215813");
    createCredential.setPrivateKey(privateKey);
    assertEquals(ErrorCode.SUCCESS, CredentialUtils.isCreateCredentialArgsValid(createCredential));
  }

  @Test
  public void testIsCredentialArgsValid() {
    assertEquals(
        ErrorCode.PARAMETER_IS_EMPTY, CredentialUtils.isCredentialArgsValid(new Credential()));
    assertEquals(ErrorCode.PARAMETER_IS_EMPTY, CredentialUtils.isCredentialArgsValid(null));
  }

  @Test
  public void testIsCredentialArgsValid2() {
    Credential credential = new Credential();
    credential.setCptId(123L);
    assertEquals(ErrorCode.INVALID_DID, CredentialUtils.isCredentialArgsValid(credential));
  }

  @Test
  public void testIsCredentialArgsValid3() {
    Credential credential = new Credential();
    credential.setUserDid("User Did");
    credential.setCptId(123L);
    assertEquals(ErrorCode.INVALID_DID, CredentialUtils.isCredentialArgsValid(credential));
  }

  @Test
  public void testIsCredentialArgsValid4() {
    Credential credential = new Credential();
    credential.setUserDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    credential.setCptId(123L);
    credential.setIssuerDid("Issuer Did");
    assertEquals(ErrorCode.INVALID_DID, CredentialUtils.isCredentialArgsValid(credential));
  }

  @Test
  public void testIsCredentialArgsValid5() {
    Credential credential = new Credential();
    credential.setUserDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    credential.setCptId(123L);
    credential.setIssuerDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    assertEquals(ErrorCode.PARAMETER_IS_EMPTY, CredentialUtils.isCredentialArgsValid(credential));
  }

  @Test
  public void testIsCredentialArgsValid6() {
    Credential credential = new Credential();
    credential.setUserDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    credential.setCptId(123L);
    credential.setIssuerDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    Map<String, Object> claim = new HashMap<>();
    claim.put("test", "test");
    credential.setClaim(claim);
    assertEquals(ErrorCode.PARAMETER_IS_EMPTY, CredentialUtils.isCredentialArgsValid(credential));
  }

  @Test
  public void testIsCredentialArgsValid7() {
    Credential credential = new Credential();
    credential.setUserDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    credential.setCptId(123L);
    credential.setIssuerDid("did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1C");
    Map<String, Object> claim = new HashMap<>();
    claim.put("test", "test");
    credential.setClaim(claim);
    Map<String, Object> proof = new HashMap<>();
    proof.put("test", "test");
    credential.setProof(proof);
    assertEquals(
        ErrorCode.SIGNATURE_VERIFICATION_FAIL, CredentialUtils.isCredentialArgsValid(credential));
  }

  @Test
  public void testPackageCredentialInfo() throws Exception {
    assertThrows(
        Exception.class, () -> CredentialUtils.packageCredentialInfo(new CreateCredential()));
    assertThrows(Exception.class, () -> CredentialUtils.packageCredentialInfo(null));
  }

  @Test
  public void testPackageCredentialInfo2() throws Exception {
    CreateCredential createCredential = new CreateCredential();
    createCredential.setClaim(new HashMap<String, Object>(1));
    assertThrows(Exception.class, () -> CredentialUtils.packageCredentialInfo(createCredential));
  }

  @Test
  public void testPackageCredentialInfo3() throws Exception {
    HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
    stringObjectMap.put("https://www.w3.org/2018/credentials/v1", "42");

    CreateCredential createCredential = new CreateCredential();
    createCredential.setClaim(stringObjectMap);
    assertThrows(Exception.class, () -> CredentialUtils.packageCredentialInfo(createCredential));
  }

  @Test
  public void testPackageCredentialInfo4() throws Exception {
    HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
    stringObjectMap.put("https://www.w3.org/2018/credentials/v1", "42");

    CreateCredential createCredential = new CreateCredential();
    createCredential.setPrivateKey(ECDSAUtils.createKey());
    createCredential.setClaim(stringObjectMap);
    CredentialWrapper actualPackageCredentialInfoResult =
        CredentialUtils.packageCredentialInfo(createCredential);
    assertSame(stringObjectMap, actualPackageCredentialInfoResult.getClaim());
    assertNull(actualPackageCredentialInfoResult.getUserDid());
    assertNull(actualPackageCredentialInfoResult.getType());
    assertEquals(
        "https://www.w3.org/2018/credentials/v1", actualPackageCredentialInfoResult.getContext());
    assertNull(actualPackageCredentialInfoResult.getIssuerDid());
    assertNull(actualPackageCredentialInfoResult.getExpirationDate());
    assertEquals(3, actualPackageCredentialInfoResult.getProof().size());
    assertNull(actualPackageCredentialInfoResult.getCptId());
  }

  @Test
  public void testPackageCredentialInfo5() throws Exception {
    HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
    stringObjectMap.put("https://www.w3.org/2018/credentials/v1", "42");

    CreateCredential createCredential = new CreateCredential();
    createCredential.setPrivateKey(new KeyPair());
    createCredential.setClaim(stringObjectMap);
    assertThrows(Exception.class, () -> CredentialUtils.packageCredentialInfo(createCredential));
  }
}
