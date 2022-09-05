package com.reddate.did.did.service.impl;

import com.reddate.did.DIDClient;
import com.reddate.did.protocol.common.AuthorityIssuer;
import com.reddate.did.protocol.common.JsonSchema;
import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.common.RegisterAuthorityIssuer;
import com.reddate.did.protocol.request.RegisterCpt;
import com.reddate.did.protocol.request.RevokeCredential;
import com.reddate.did.protocol.response.CptBaseInfo;
import com.reddate.did.protocol.response.CptInfo;
import com.reddate.did.protocol.response.Pages;
import com.reddate.did.protocol.response.ResponseData;
import com.reddate.did.service.impl.AuthorityIssuerServiceImpl;
import com.reddate.did.util.commons.ECDSAUtils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AuthorityIssuerServiceImplTest {
  @Test
  public void testRegisterAuthIssuer() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(new RegisterAuthorityIssuer());
    assertEquals("parameter is empty", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("parameter is empty", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer10() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("Did");
    //        authorityIssuer.setKeyInfo(new KeyPair());
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1035, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer11() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("did:udpn:");
    //        authorityIssuer.setKeyInfo(new KeyPair());
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1035, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer2() {
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        (new AuthorityIssuerServiceImpl()).registerAuthIssuer(null);
    assertEquals("parameter is empty", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("parameter is empty", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer3() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    //        authorityIssuer.setKeyInfo(new KeyPair());
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1035, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer4() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("Did");
    //        authorityIssuer.setKeyInfo(new KeyPair());
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1035, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer5() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("did:udpn:");
    //        authorityIssuer.setKeyInfo(new KeyPair());
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1035, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer6() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7");
    authorityIssuer.setName("test");
    KeyPair keyPair = new KeyPair();
    keyPair.setType(ECDSAUtils.type);
    keyPair.setPublicKey(
        "12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");
    keyPair.setPrivateKey(
        "58994647064326635891406884561711756015554813284210528766755994688957384700750");
    //        authorityIssuer.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("create issuer fail", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertEquals("create issuer fail", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1038, actualRegisterAuthIssuerResult.getErrorCode().intValue());
    assertEquals("success", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(0, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer7() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(new RegisterAuthorityIssuer());
    assertEquals("parameter is empty", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("parameter is empty", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer8() {
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        (new AuthorityIssuerServiceImpl()).registerAuthIssuer(null);
    assertEquals("parameter is empty", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("parameter is empty", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer9() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    //        authorityIssuer.setKeyInfo(new KeyPair());
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertFalse(actualRegisterAuthIssuerResult.getResult());
    assertEquals("invalid did", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1035, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer12() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("did:udpn:3d5TtjKuUTcfUvCdUuEERp4QWmiM");
    KeyPair keyPair = new KeyPair();
    keyPair.setType(ECDSAUtils.type);
    keyPair.setPublicKey(
        "10937593245606769798492586132869385140851734543713754057350260753393388859812088215124478791566501613895252764289857081026773678691057809543053032583490202");
    keyPair.setPrivateKey(
        "30308848302736829136366421019592481055057316206795517589670967456575530721272");
    //        authorityIssuer.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("parameter illegal format", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertEquals("parameter illegal format", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1002, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer13() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("did:udpn:3d5TtjKuUTcfUvCdUuEERp4QWmiM");
    authorityIssuer.setName("test");
    KeyPair keyPair = new KeyPair();
    keyPair.setPublicKey(
        "10937593245606769798492586132869385140851734543713754057350260753393388859812088215124478791566501613895252764289857081026773678691057809543053032583490202");
    keyPair.setPrivateKey(
        "30308848302736829136366421019592481055057316206795517589670967456575530721272");
    //        authorityIssuer.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("Encryption type is empty", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertEquals("Encryption type is empty", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1052, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterAuthIssuer14() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("did:udpn:3d5TtjKuUTcfUvCdUuEERp4QWmiM");
    authorityIssuer.setName("test");
    KeyPair keyPair = new KeyPair();
    keyPair.setType(ECDSAUtils.type);
    keyPair.setPublicKey("wdhkajhdkja");
    keyPair.setPrivateKey("kawhdkjawhkdja");
    //        authorityIssuer.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRegisterAuthIssuerResult =
        authorityIssuerServiceImpl.registerAuthIssuer(authorityIssuer);
    assertEquals("invalid key", actualRegisterAuthIssuerResult.getErrorChMessage());
    assertEquals("invalid key", actualRegisterAuthIssuerResult.getErrorEnMessage());
    assertEquals(1034, actualRegisterAuthIssuerResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList10() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, 10, "Did");
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1035, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList11() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(null, 10, "Did");
    assertEquals("illegal start index", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("illegal start index", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1044, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList12() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(-1, 10, "Did");
    assertEquals("illegal start index", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("illegal start index", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1044, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList13() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, 0, "Did");
    assertEquals("data number illegal", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("data number illegal", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1045, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList14() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, null, "Did");
    assertEquals("data number illegal", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("data number illegal", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1045, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList15() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, 10, "did:udpn:");
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1035, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList16() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, 10, "0000");
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1035, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList2() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(-1, 10, "Did");
    assertEquals("illegal start index", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("illegal start index", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1044, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList3() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(null, 10, "Did");
    assertEquals("illegal start index", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("illegal start index", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1044, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList4() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, 0, "Did");
    assertEquals("data number illegal", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("data number illegal", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1045, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList5() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, null, "Did");
    assertEquals("data number illegal", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("data number illegal", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1045, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList6() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, 10, "did:udpn:");
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1035, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList7() {
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, 10, "0000");
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertNull(actualQueryAuthIssuerListResult.getResult());
    assertEquals("invalid did", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1035, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList8() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl())
            .queryAuthIssuerList(1, 2, "did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7");
    assertEquals("success", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertEquals("success", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(0, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList9() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl()).queryAuthIssuerList(1, 10, null);
    assertEquals("success", actualQueryAuthIssuerListResult.getErrorChMessage());
    assertEquals("success", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(0, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testQueryAuthIssuerList17() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    ResponseData<Pages<AuthorityIssuer>> actualQueryAuthIssuerListResult =
        (new AuthorityIssuerServiceImpl())
            .queryAuthIssuerList(1, 10, "did:udpn:2JrytgQsafauZXuVPx9vmEkSkF1D");
    assertEquals(
        "Failed to query the issuing party", actualQueryAuthIssuerListResult.getErrorEnMessage());
    assertEquals(1053, actualQueryAuthIssuerListResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterCpt() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        authorityIssuerServiceImpl.registerCpt(new RegisterCpt());
    assertNull(actualRegisterCptResult.getResult());
    assertEquals("parameter is empty", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterCptResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterCpt2() {
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        (new AuthorityIssuerServiceImpl()).registerCpt(null);
    assertNull(actualRegisterCptResult.getResult());
    assertEquals("parameter is empty", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterCptResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterCpt3() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        authorityIssuerServiceImpl.registerCpt(registerCpt);
    assertEquals("parameter is empty", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterCptResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterCpt4() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("did:udpn:");
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        authorityIssuerServiceImpl.registerCpt(registerCpt);
    assertEquals("parameter is empty", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterCptResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterCpt5() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7");
    Map<String, JsonSchema> jsonSchemaMap = new HashMap<>();
    JsonSchema jsonSchema1 = new JsonSchema();
    jsonSchema1.setType("string");
    jsonSchema1.setDescription("this is a name");
    jsonSchema1.setRequired(true);
    jsonSchemaMap.put("name", jsonSchema1);
    registerCpt.setCptJsonSchema(jsonSchemaMap);
    registerCpt.setTitle("test cpt");
    registerCpt.setDescription("this is a test cpt");
    registerCpt.setType("Proof");
    KeyPair keyPair = new KeyPair();
    keyPair.setType(ECDSAUtils.type);
    keyPair.setPublicKey(
        "12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");
    keyPair.setPrivateKey(
        "58994647064326635891406884561711756015554813284210528766755994688957384700750");
    //        registerCpt.setPrivateKey(keyPair);
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        authorityIssuerServiceImpl.registerCpt(registerCpt);
    assertEquals("success", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(0, actualRegisterCptResult.getErrorCode().intValue());
    System.out.println(actualRegisterCptResult.getResult().getCptId());
  }

  @Test
  public void testRegisterCpt15() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7");
    Map<String, JsonSchema> jsonSchemaMap = new HashMap<>();
    JsonSchema jsonSchema1 = new JsonSchema();
    jsonSchema1.setType("string");
    jsonSchema1.setDescription("this is a name");
    jsonSchema1.setRequired(true);
    jsonSchemaMap.put("name", jsonSchema1);
    registerCpt.setCptJsonSchema(jsonSchemaMap);
    registerCpt.setTitle("test cpt");
    registerCpt.setDescription("this is a test cpt");
    registerCpt.setType("Proof");
    KeyPair keyPair = new KeyPair();
    keyPair.setType(ECDSAUtils.type);
    keyPair.setPublicKey(
        "12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");
    keyPair.setPrivateKey(
        "58994647064326635891406884561711756015554813284210528766755994688957384700750");
    //        registerCpt.setPrivateKey(keyPair);
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        authorityIssuerServiceImpl.registerCpt(registerCpt);
    assertEquals("success", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(0, actualRegisterCptResult.getErrorCode().intValue());
    System.out.println(actualRegisterCptResult.getResult().getCptId());
  }

  @Test
  public void testRegisterCpt6() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        authorityIssuerServiceImpl.registerCpt(new RegisterCpt());
    assertNull(actualRegisterCptResult.getResult());
    assertEquals("parameter is empty", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterCptResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterCpt7() {
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        (new AuthorityIssuerServiceImpl()).registerCpt(null);
    assertNull(actualRegisterCptResult.getResult());
    assertEquals("parameter is empty", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterCptResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterCpt8() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        authorityIssuerServiceImpl.registerCpt(registerCpt);
    assertNull(actualRegisterCptResult.getResult());
    assertEquals("parameter is empty", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterCptResult.getErrorCode().intValue());
  }

  @Test
  public void testRegisterCpt9() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("did:udpn:");
    ResponseData<CptBaseInfo> actualRegisterCptResult =
        authorityIssuerServiceImpl.registerCpt(registerCpt);
    assertNull(actualRegisterCptResult.getResult());
    assertEquals("parameter is empty", actualRegisterCptResult.getErrorEnMessage());
    assertEquals(1001, actualRegisterCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        authorityIssuerServiceImpl.updateCpt(new RegisterCpt());
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("invalid did", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1035, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt10() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        authorityIssuerServiceImpl.updateCpt(registerCpt);
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("invalid did", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1035, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt11() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        authorityIssuerServiceImpl.updateCpt(new RegisterCpt());
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("invalid did", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1035, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt12() {
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        (new AuthorityIssuerServiceImpl()).updateCpt(null);
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("parameter is empty", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1001, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt13() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        authorityIssuerServiceImpl.updateCpt(registerCpt);
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("invalid did", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1035, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt2() {
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        (new AuthorityIssuerServiceImpl()).updateCpt(null);
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("parameter is empty", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1001, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt3() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        authorityIssuerServiceImpl.updateCpt(registerCpt);
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("invalid did", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1035, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt4() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setCptId(932106041638260255L);
    registerCpt.setDid("did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7");
    Map<String, JsonSchema> jsonSchemaMap = new HashMap<>();
    JsonSchema jsonSchema1 = new JsonSchema();
    jsonSchema1.setType("string");
    jsonSchema1.setDescription("this is a name");
    jsonSchema1.setRequired(true);
    jsonSchemaMap.put("name", jsonSchema1);
    registerCpt.setCptJsonSchema(jsonSchemaMap);
    registerCpt.setTitle("test cpt");
    registerCpt.setDescription("this is a test cpt");
    registerCpt.setType("Proof");
    KeyPair keyPair = new KeyPair();
    keyPair.setType(ECDSAUtils.type);
    keyPair.setPublicKey(
        "12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");
    keyPair.setPrivateKey(
        "58994647064326635891406884561711756015554813284210528766755994688957384700750");
    //        registerCpt.setPrivateKey(keyPair);
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        authorityIssuerServiceImpl.updateCpt(registerCpt);
    assertEquals("success", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(0, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt5() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        authorityIssuerServiceImpl.updateCpt(new RegisterCpt());
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("invalid did", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1035, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt6() {
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        (new AuthorityIssuerServiceImpl()).updateCpt(null);
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("parameter is empty", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1001, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt7() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        authorityIssuerServiceImpl.updateCpt(registerCpt);
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("invalid did", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1035, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt8() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        authorityIssuerServiceImpl.updateCpt(new RegisterCpt());
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("invalid did", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1035, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testUpdateCpt9() {
    ResponseData<CptBaseInfo> actualUpdateCptResult =
        (new AuthorityIssuerServiceImpl()).updateCpt(null);
    assertNull(actualUpdateCptResult.getResult());
    assertEquals("parameter is empty", actualUpdateCptResult.getErrorEnMessage());
    assertEquals(1001, actualUpdateCptResult.getErrorCode().intValue());
  }

  @Test
  public void testGetCptListByDid() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<Pages<CptInfo>> actualGetCptListByDidResult =
        authorityIssuerServiceImpl.queryCptListByDid(
            1, 12, "did:udpn:3y53TZ28vscJ3SAEBhimG3ZTtgwh");
    assertEquals("success", actualGetCptListByDidResult.getErrorEnMessage());
    assertEquals(0, actualGetCptListByDidResult.getErrorCode().intValue());
  }

  @Test
  public void testGetCptById() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<CptInfo> actualGetCptListByDidResult =
        authorityIssuerServiceImpl.queryCptById(2105280305216230000L);
    assertEquals("success", actualGetCptListByDidResult.getErrorEnMessage());
    assertEquals(0, actualGetCptListByDidResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(new RevokeCredential());
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("private key is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1011, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential10() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    KeyPair keyPair = new KeyPair();
    keyPair.setPrivateKey("Private Key");

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("document is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1016, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential11() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    KeyPair keyPair = new KeyPair();
    keyPair.setPrivateKey("42");

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals(
        "public and private key mismatch", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1008, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential12() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(new RevokeCredential());
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("private key is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1011, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential13() {
    ResponseData<Boolean> actualRevokeCredentialResult =
        (new AuthorityIssuerServiceImpl()).revokeCredential(null);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("parameter is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1001, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential14() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(new KeyPair());
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("document is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1016, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential15() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    KeyPair keyPair = new KeyPair();
    keyPair.setPrivateKey("Private Key");

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("document is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1016, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential16() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    KeyPair keyPair = new KeyPair();
    keyPair.setPrivateKey("42");

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals(
        "public and private key mismatch", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1008, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential17() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(new RevokeCredential());
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("private key is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1011, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential18() {
    ResponseData<Boolean> actualRevokeCredentialResult =
        (new AuthorityIssuerServiceImpl()).revokeCredential(null);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("parameter is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1001, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential19() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(new KeyPair());
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("document is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1016, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential2() {
    ResponseData<Boolean> actualRevokeCredentialResult =
        (new AuthorityIssuerServiceImpl()).revokeCredential(null);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("parameter is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1001, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential20() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    KeyPair keyPair = new KeyPair();
    keyPair.setPrivateKey("Private Key");

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("document is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1016, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential21() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    KeyPair keyPair = new KeyPair();
    keyPair.setPrivateKey("42");

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals(
        "public and private key mismatch", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1008, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential3() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(new KeyPair());
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("document is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1016, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential4() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    KeyPair keyPair = new KeyPair();
    keyPair.setPrivateKey("Private Key");

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("document is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1016, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential5() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();

    KeyPair keyPair = new KeyPair();
    keyPair.setPrivateKey("42");

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals(
        "public and private key mismatch", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1008, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential6() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    RevokeCredential revokeCredential = new RevokeCredential();
    revokeCredential.setCredId("1402813761399689216");
    revokeCredential.setCptId(972106041627064723L);
    revokeCredential.setDid("did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7");
    KeyPair keyPair = new KeyPair();
    keyPair.setType(ECDSAUtils.type);
    keyPair.setPublicKey(
        "12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");
    keyPair.setPrivateKey(
        "58994647064326635891406884561711756015554813284210528766755994688957384700750");
    //        revokeCredential.setKeyInfo(keyPair);
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(revokeCredential);
    assertEquals("success", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(0, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential7() {
    AuthorityIssuerServiceImpl authorityIssuerServiceImpl = new AuthorityIssuerServiceImpl();
    ResponseData<Boolean> actualRevokeCredentialResult =
        authorityIssuerServiceImpl.revokeCredential(new RevokeCredential());
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("private key is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1011, actualRevokeCredentialResult.getErrorCode().intValue());
  }

  @Test
  public void testRevokeCredential8() {
    ResponseData<Boolean> actualRevokeCredentialResult =
        (new AuthorityIssuerServiceImpl()).revokeCredential(null);
    assertFalse(actualRevokeCredentialResult.getResult());
    assertEquals("parameter is empty", actualRevokeCredentialResult.getErrorEnMessage());
    assertEquals(1001, actualRevokeCredentialResult.getErrorCode().intValue());
  }

}
