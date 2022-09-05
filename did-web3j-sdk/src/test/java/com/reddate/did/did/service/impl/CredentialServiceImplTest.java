package com.reddate.did.did.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.reddate.did.DIDClient;
import com.reddate.did.protocol.common.BaseCredential;
import com.reddate.did.protocol.common.Credential;
import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.request.CreateCredential;
import com.reddate.did.protocol.response.CredentialWrapper;
import com.reddate.did.protocol.response.Pages;
import com.reddate.did.protocol.response.ResponseData;
import com.reddate.did.service.impl.CredentialServiceImpl;
import com.reddate.did.util.commons.ECDSAUtils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CredentialServiceImplTest {
  @Test
  public void testCreateCredential() {
    CredentialServiceImpl credentialServiceImpl = new CredentialServiceImpl();
    ResponseData<CredentialWrapper> actualCreateCredentialResult =
        credentialServiceImpl.createCredential(new CreateCredential());
    assertNull(actualCreateCredentialResult.getResult());
    assertEquals("invalid cpt id", actualCreateCredentialResult.getErrorEnMessage());
    assertEquals(1037, actualCreateCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testCreateCredential2() {
    CredentialServiceImpl credentialServiceImpl = new CredentialServiceImpl();

    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(123L);
    createCredential.setUserDid("User Did");
    ResponseData<CredentialWrapper> actualCreateCredentialResult =
        credentialServiceImpl.createCredential(createCredential);
    assertNull(actualCreateCredentialResult.getResult());
    assertEquals("invalid did", actualCreateCredentialResult.getErrorEnMessage());
    assertEquals(1035, actualCreateCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testCreateCredential3() {
    CredentialServiceImpl credentialServiceImpl = new CredentialServiceImpl();

    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(123L);
    createCredential.setUserDid("did:udpn:");
    ResponseData<CredentialWrapper> actualCreateCredentialResult =
        credentialServiceImpl.createCredential(createCredential);
    assertNull(actualCreateCredentialResult.getResult());
    assertEquals("invalid did", actualCreateCredentialResult.getErrorEnMessage());
    assertEquals(1035, actualCreateCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testCreateCredential4() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    CredentialServiceImpl credentialServiceImpl = new CredentialServiceImpl();
    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(972106041627064723L);
    createCredential.setIssuerDid("did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7");
    createCredential.setUserDid("did:udpn:2hpvEGY385VrEobXCojzSwyxmSfj");
    createCredential.setExpirationDate("2021-07-31");
    Map<String, Object> claimMap = new HashMap<>();
    claimMap.put("name", "bob");
    createCredential.setClaim(claimMap);
    createCredential.setType("Proof");
    KeyPair keyPair = new KeyPair();
    keyPair.setType(ECDSAUtils.type);
    keyPair.setPublicKey(
        "12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");
    keyPair.setPrivateKey(
        "58994647064326635891406884561711756015554813284210528766755994688957384700750");
    createCredential.setPrivateKey(keyPair);
    ResponseData<CredentialWrapper> actualCreateCredentialResult =
        credentialServiceImpl.createCredential(createCredential);
    assertEquals("success", actualCreateCredentialResult.getErrorEnMessage());
    assertEquals(0, actualCreateCredentialResult.getErrorCode().intValue());
    System.out.println(JSONArray.toJSONString(actualCreateCredentialResult.getResult()));
  }

  @Test
  public void testVerifyCredential() {
    CredentialServiceImpl credentialServiceImpl = new CredentialServiceImpl();
    Credential credential = new Credential();
    ResponseData<Boolean> actualVerifyCredentialResult =
        credentialServiceImpl.verifyCredential(credential, new PublicKey());
    assertFalse(actualVerifyCredentialResult.getResult());
    assertEquals("public key is empty", actualVerifyCredentialResult.getErrorEnMessage());
    assertEquals(1009, actualVerifyCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testVerifyCredential2() {
    CredentialServiceImpl credentialServiceImpl = new CredentialServiceImpl();
    ResponseData<Boolean> actualVerifyCredentialResult =
        credentialServiceImpl.verifyCredential(new Credential(), null);
    assertFalse(actualVerifyCredentialResult.getResult());
    assertEquals("public key is empty", actualVerifyCredentialResult.getErrorEnMessage());
    assertEquals(1009, actualVerifyCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testVerifyCredential3() {
    CredentialServiceImpl credentialServiceImpl = new CredentialServiceImpl();
    Credential credential = new Credential();

    PublicKey publicKey = new PublicKey();
    publicKey.setPublicKey("0123456789ABCDEF");
    ResponseData<Boolean> actualVerifyCredentialResult =
        credentialServiceImpl.verifyCredential(credential, publicKey);
    assertFalse(actualVerifyCredentialResult.getResult());
    assertEquals("invalid cpt id", actualVerifyCredentialResult.getErrorEnMessage());
    assertEquals(1037, actualVerifyCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testVerifyCredential4() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    String json =
        "{\n"
            + "\t\"cptId\": 2105310514495880000,\n"
            + "\t\"created\": \"2021-05-31 17:21:29\",\n"
            + "\t\"issuerDid\": \"did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7\",\n"
            + "\t\"context\": \"https://www.w3.org/2018/credentials/v1\",\n"
            + "\t\"claim\": {\n"
            + "\t\t\"name\": \"bob\"\n"
            + "\t},\n"
            + "\t\"id\": \"1399294954446131200\",\n"
            + "\t\"proof\": {\n"
            + "\t\t\"type\": \"Secp256k1\",\n"
            + "\t\t\"creator\": \"did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7\",\n"
            + "\t\t\"signatureValue\": \"6ig5KdPK5698a7wWCtZTPvs3qZkUPLhEUzwufXZoTUpLuWQAI+ErmQ2mwe5aWBDb+hlW5h/5myUeKCFjjCNBWxw=\"\n"
            + "\t},\n"
            + "\t\"type\": \"Proof\",\n"
            + "\t\"userDid\": \"did:udpn:2hpvEGY385VrEobXCojzSwyxmSfj\",\n"
            + "\t\"expirationDate\": \"2021-06-30 11:11:11\"\n"
            + "}";
    CredentialServiceImpl credentialServiceImpl = new CredentialServiceImpl();
    Credential credential = JSON.parseObject(json, Credential.class);
    PublicKey publicKey = new PublicKey();
    publicKey.setType(ECDSAUtils.type);
    publicKey.setPublicKey(
        "12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");
    ResponseData<Boolean> actualVerifyCredentialResult =
        credentialServiceImpl.verifyCredential(credential, publicKey);
    assertEquals(
        "credentials validation success", actualVerifyCredentialResult.getErrorEnMessage());
    assertEquals(1051, actualVerifyCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testGetRevokedCredList() {
    ResponseData<Pages<BaseCredential>> actualRevokedCredList =
        (new CredentialServiceImpl()).getRevokedCredList(1, 1, "Did");
    assertNull(actualRevokedCredList.getResult());
    assertEquals("invalid did", actualRevokedCredList.getErrorEnMessage());
    assertEquals(1035, actualRevokedCredList.getErrorCode().intValue());
  }

  @Test
  public void testGetRevokedCredList2() {
    ResponseData<Pages<BaseCredential>> actualRevokedCredList =
        (new CredentialServiceImpl()).getRevokedCredList(1, 1, "did:udpn:");
    assertNull(actualRevokedCredList.getResult());
    assertEquals("invalid did", actualRevokedCredList.getErrorEnMessage());
    assertEquals(1035, actualRevokedCredList.getErrorCode().intValue());
  }

  @Test
  public void testGetRevokedCredList3() {
    ResponseData<Pages<BaseCredential>> actualRevokedCredList =
        (new CredentialServiceImpl()).getRevokedCredList(1, 1, "");
    assertNull(actualRevokedCredList.getResult());
    assertEquals("parameter is empty", actualRevokedCredList.getErrorEnMessage());
    assertEquals(1001, actualRevokedCredList.getErrorCode().intValue());
  }

  @Test
  public void testGetRevokedCredList4() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    ResponseData<Pages<BaseCredential>> actualRevokedCredList =
        (new CredentialServiceImpl())
            .getRevokedCredList(1, 4, "did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7");
    assertEquals("success", actualRevokedCredList.getErrorEnMessage());
    assertEquals(0, actualRevokedCredList.getErrorCode().intValue());
    System.out.println(JSONArray.toJSON(actualRevokedCredList.getResult()).toString());
  }
}
