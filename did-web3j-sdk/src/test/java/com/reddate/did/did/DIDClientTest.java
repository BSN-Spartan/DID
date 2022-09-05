package com.reddate.did.did;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.reddate.did.DIDClient;
import com.reddate.did.exception.DidException;
import com.reddate.did.protocol.common.*;
import com.reddate.did.protocol.request.CreateCredential;
import com.reddate.did.protocol.request.RegisterCpt;
import com.reddate.did.protocol.request.ResetDid;
import com.reddate.did.protocol.request.RevokeCredential;
import com.reddate.did.protocol.response.*;
import com.reddate.did.util.CptUtils;
import com.reddate.did.util.DidUtils;
import com.reddate.did.util.GenerateCptIdUtils;
import com.reddate.did.util.commons.ECDSAUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class DIDClientTest {
  @Test
  public void testInit() {
    assertThrows(DidException.class, () -> (new DIDClient()).init("foo.txt"));
    assertThrows(DidException.class, () -> (new DIDClient()).init("foo.txt"));
  }

  @Test
  public void testInit2() {

    (new DIDClient()).init("application.properties");
  }

  @Test
  public void testInit3() {

    (new DIDClient()).init("application.properties");
  }

  @Test
  public void testCreateDid() {

    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData didData = didClient.createDid(true);
    System.out.println("-----------testCreateDid----------- didData = " + JSONArray.toJSONString(didData));
  }

  @Test
  public void testCreateDid2() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    DidDocument didDocument = didClient.getDidDocument("did:bsn:4Xf3qovtZc9uy1AQVLoCsFw3bxGf");
    System.out.println(JSONArray.toJSONString(didDocument));
  }

  @Test
  public void testChainDidDocument() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData = didClient.createDid(false);
    Boolean result = didClient.storeDidDocumentOnChain( createDidData.getDidDocument(), createDidData.getDidDocument().getAuthentication());
    System.out.println("------------------- testChainDidDocument result = " + result);
  }

  @Test
  public void testChainDidDocument2() throws Exception {

    DIDClient didClient = new DIDClient();
    KeyPair primaryKeyPair = ECDSAUtils.createKey();
    DidDocument didDocument =
        DidUtils.generateDidDocument(primaryKeyPair, ECDSAUtils.createKey(), "Did");
    didClient.storeDidDocumentOnChain(didDocument, new PublicKey());
  }

  @Test
  public void testChainDidDocument3() {

    DIDClient didClient = new DIDClient();
    DidDocument didDocument = new DidDocument();
    didClient.storeDidDocumentOnChain(didDocument, new PublicKey());
  }

  @Test
  public void testChainDidDocument4() throws Exception {

    DIDClient didClient = new DIDClient();
    KeyPair primaryKeyPair = ECDSAUtils.createKey();
    DidDocument didDocument =
        DidUtils.generateDidDocument(primaryKeyPair, ECDSAUtils.createKey(), "Did");
    didClient.storeDidDocumentOnChain(didDocument, new PublicKey());
  }

  @Test
  public void testGetDidDocument() {
    assertNull((new DIDClient()).getDidDocument("Did"));
    assertNull((new DIDClient()).getDidDocument("did:udpn:"));
    assertNull((new DIDClient()).getDidDocument("Did"));
    assertNull((new DIDClient()).getDidDocument("did:udpn:"));
  }

  @Test
  public void testGetDidDocument2() {

    DIDClient didClient = new DIDClient();
    didClient.init();
    DidDocument didDocument = didClient.getDidDocument(didClient.createDid(true).getDid());
    System.out.println(JSONObject.toJSONString(didDocument));
  }

  @Test
  public void testGetDidDocument3() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    DidDocument didDocument = didClient.getDidDocument(null);
  }

  @Test
  public void testGetDidDocument4() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    DidDocument didDocument = didClient.getDidDocument("123");
  }

  @Test
  public void testVerifyDidDocument() {

    DIDClient didClient = new DIDClient();
    DidDocument didDocument = new DidDocument();
    didClient.verifyDidDocument(didDocument, new PublicKey());
  }

  @Test
  public void testVerifyDidDocument2() {

    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData = didClient.createDid(false);
    Boolean result = didClient.verifyDidDocument(createDidData.getDidDocument(), createDidData.getDidDocument().getAuthentication());
    System.out.println("----------testVerifyDidDocument2-------- result = " + result);
  }

  @Test
  public void testResetDidAuth() {

    DIDClient didClient = new DIDClient();
    didClient.resetDidAuth(new ResetDid());
  }

  @Test
  public void testResetDidAuth2() {

    DIDClient didClient = new DIDClient();

    ResetDid resetDid = new ResetDid();
    //        resetDid.setDid("Did");
    didClient.resetDidAuth(resetDid);
  }

  @Test
  public void testResetDidAuth3() {

    DIDClient didClient = new DIDClient();

    ResetDid resetDid = new ResetDid();
    didClient.resetDidAuth(resetDid);
  }

  @Test
  public void testResetDidAuth4() {

    DIDClient didClient = new DIDClient();
    didClient.resetDidAuth(new ResetDid());
  }

  @Test
  public void testResetDidAuth5() {

    DIDClient didClient = new DIDClient();

    ResetDid resetDid = new ResetDid();
    //        resetDid.setDid("Did");
    didClient.resetDidAuth(resetDid);
  }

  @Test
  public void testResetDidAuth6() {
    DIDClient didClient = new DIDClient();

    ResetDid resetDid = new ResetDid();
    //        resetDid.setDid("did:udpn:");
    didClient.resetDidAuth(resetDid);
  }

  @Test
  public void testResetDidAuth7() throws Exception {
    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData = didClient.createDid(true);
    System.out.println("----------testResetDidAuth7---------- authKeyInfo = " + JSONObject.toJSONString(createDidData.getAuthKeyInfo()));

    ResetDid resetDid = new ResetDid();
    DidDocument document = didClient.getDidDocument(createDidData.getDid());
    resetDid.setDidDocument(document);
    PublicKeyInfo authPublicKeyInfo = new PublicKeyInfo();
    authPublicKeyInfo.setPublicKey(document.getRecovery().getPublicKey());

    String authPublicKeySign = ECDSAUtils.sign(createDidData.getRecyKeyInfo().getPublicKey(), createDidData.getRecyKeyInfo().getPrivateKey(),
            createDidData.getRecyKeyInfo().getPublicKey());
    authPublicKeyInfo.setPublicKeySign(authPublicKeySign);
    authPublicKeyInfo.setType(document.getAuthentication().getType());
    resetDid.setAuthPublicKeyInfo(authPublicKeyInfo);

    KeyPair keyPair = didClient.resetDidAuth(resetDid);
    System.out.println("----------testResetDidAuth7---------- keyPair = " + JSONObject.toJSONString(keyPair));
  }

  @Test
  public void testRegisterAuthIssuer() {

    DIDClient didClient = new DIDClient();
    didClient.registerAuthIssuer(new RegisterAuthorityIssuer());
  }

  @Test
  public void testRegisterAuthIssuer2() throws Exception {

    DIDClient didClient = new DIDClient();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("Did");
    didClient.registerAuthIssuer(authorityIssuer);
  }

  @Test
  public void testRegisterAuthIssuer3() throws Exception {

    DIDClient didClient = new DIDClient();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("did:udpn:");
    didClient.registerAuthIssuer(authorityIssuer);
  }

  @Test
  public void testRegisterAuthIssuer4() {

    DIDClient didClient = new DIDClient();
    didClient.registerAuthIssuer(new RegisterAuthorityIssuer());
  }

  @Test
  public void testRegisterAuthIssuer5() throws Exception {

    DIDClient didClient = new DIDClient();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("Did");
    didClient.registerAuthIssuer(authorityIssuer);
  }

  @Test
  public void testRegisterAuthIssuer6() throws Exception {

    DIDClient didClient = new DIDClient();

    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid("did:udpn:");
    didClient.registerAuthIssuer(authorityIssuer);
  }

  @Test
  public void testRegisterAuthIssuer7() throws Exception {

    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData = didClient.createDid(true);
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();

    authorityIssuer.setDid(createDidData.getDid());
    authorityIssuer.setName("test");
    DidDocument document = didClient.getDidDocument(createDidData.getDid());
    PublicKeyInfo authPublicKeyInfo = new PublicKeyInfo();
    authPublicKeyInfo.setPublicKey(document.getAuthentication().getPublicKey());

    String authPublicKeySign = ECDSAUtils.sign(createDidData.getAuthKeyInfo().getPublicKey(), createDidData.getAuthKeyInfo().getPrivateKey(),
            createDidData.getAuthKeyInfo().getPublicKey());
    authPublicKeyInfo.setPublicKeySign(authPublicKeySign);
    authPublicKeyInfo.setType(document.getAuthentication().getType());

    authorityIssuer.setPublicKeyInfo(authPublicKeyInfo);
    Boolean result = didClient.registerAuthIssuer(authorityIssuer);
    System.out.println("---------testRegisterAuthIssuer7------------ result = " + result);
  }

  @Test
  public void testQueryAuthIssuerList() {

    (new DIDClient()).queryAuthIssuerList(1, 10, "Did");
  }

  @Test
  public void testQueryAuthIssuerList2() {

    (new DIDClient()).queryAuthIssuerList(0, 10, "Did");
  }

  @Test
  public void testQueryAuthIssuerList3() {

    (new DIDClient()).queryAuthIssuerList(1, 10, "did:udpn:");
  }

  @Test
  public void testQueryAuthIssuerList4() {
    (new DIDClient()).queryAuthIssuerList(1, 10, "Did");
  }

  @Test
  public void testQueryAuthIssuerList5() {

    (new DIDClient()).queryAuthIssuerList(0, 10, "Did");
  }

  @Test
  public void testQueryAuthIssuerList6() {

    DIDClient didClient = new DIDClient();
    didClient.init();
    Pages<AuthorityIssuer> pages = didClient.queryAuthIssuerList(1, 10, "did:bsn:3z7Dz75774ji213sLzL6gTB6kKyS");
    System.out.println("-------------testQueryAuthIssuerList6----------- pages = " + JSONObject.toJSONString(pages));
  }

  @Test
  public void testQueryAuthIssuerList7() {

    DIDClient didClient = new DIDClient();
    didClient.init();
    Pages<AuthorityIssuer> pages = didClient.queryAuthIssuerList(1, 45, null);
    System.out.println("-------------testQueryAuthIssuerList7----------- pages = " + JSONObject.toJSONString(pages));
  }

  @Test
  public void testRegisterCpt() {

    DIDClient didClient = new DIDClient();
    didClient.registerCpt(new RegisterCpt());
  }

  @Test
  public void testRegisterCpt2() {

    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    didClient.registerCpt(registerCpt);
  }

  @Test
  public void testRegisterCpt3() {

    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("did:udpn:");
    didClient.registerCpt(registerCpt);
  }

  @Test
  public void testRegisterCpt4() {

    DIDClient didClient = new DIDClient();
    didClient.registerCpt(new RegisterCpt());
  }

  @Test
  public void testRegisterCpt5() {

    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    didClient.registerCpt(registerCpt);
  }

  @Test
  public void testRegisterCpt6() throws Exception {

    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData = didClient.createDid(true);
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();

    authorityIssuer.setDid(createDidData.getDid());
    authorityIssuer.setName("test");
    DidDocument document = didClient.getDidDocument(createDidData.getDid());
    PublicKeyInfo authPublicKeyInfo = new PublicKeyInfo();
    authPublicKeyInfo.setPublicKey(document.getAuthentication().getPublicKey());

    String authPublicKeySign = ECDSAUtils.sign(createDidData.getAuthKeyInfo().getPublicKey(), createDidData.getAuthKeyInfo().getPrivateKey(),
            createDidData.getAuthKeyInfo().getPublicKey());
    authPublicKeyInfo.setPublicKeySign(authPublicKeySign);
    authPublicKeyInfo.setType(document.getAuthentication().getType());

    authorityIssuer.setPublicKeyInfo(authPublicKeyInfo);
    Boolean result = didClient.registerAuthIssuer(authorityIssuer);
    System.out.println("---------testRegisterCpt6------------ result = " + result);

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid(createDidData.getDid());
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

    String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    registerCpt.setCreate(nowStr);
    registerCpt.setUpdate(nowStr);
    registerCpt.setCptId(GenerateCptIdUtils.getId());

    CptInfo cptInfo = CptUtils.assemblyCptInfo(registerCpt);
    String signValue = ECDSAUtils.sign(JSONArray.toJSON(cptInfo).toString(), createDidData.getAuthKeyInfo().getPrivateKey(),
            createDidData.getAuthKeyInfo().getPublicKey());
    Proof proof = new Proof();
    proof.setCreator(registerCpt.getDid());
    proof.setSignatureValue(signValue);
    proof.setType("Secp256k1");
    registerCpt.setProof(proof);

    CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
    System.out.println("---------testRegisterCpt6------------ cptBaseInfo = " + JSONObject.toJSONString(cptBaseInfo));
  }

  @Test
  public void testQueryCptListByDid() throws Exception {

    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData = didClient.createDid(true);
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();

    authorityIssuer.setDid(createDidData.getDid());
    authorityIssuer.setName("test");
    DidDocument document = didClient.getDidDocument(createDidData.getDid());
    PublicKeyInfo authPublicKeyInfo = new PublicKeyInfo();
    authPublicKeyInfo.setPublicKey(document.getAuthentication().getPublicKey());

    String authPublicKeySign = ECDSAUtils.sign(createDidData.getAuthKeyInfo().getPublicKey(), createDidData.getAuthKeyInfo().getPrivateKey(),
            createDidData.getAuthKeyInfo().getPublicKey());
    authPublicKeyInfo.setPublicKeySign(authPublicKeySign);
    authPublicKeyInfo.setType(document.getAuthentication().getType());

    authorityIssuer.setPublicKeyInfo(authPublicKeyInfo);
    Boolean result = didClient.registerAuthIssuer(authorityIssuer);
    System.out.println("---------testRegisterCpt6------------ result = " + result);

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid(createDidData.getDid());
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

    String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    registerCpt.setCreate(nowStr);
    registerCpt.setUpdate(nowStr);
    registerCpt.setCptId(GenerateCptIdUtils.getId());

    CptInfo cptInfo = CptUtils.assemblyCptInfo(registerCpt);
    String signValue = ECDSAUtils.sign(JSONArray.toJSON(cptInfo).toString(), createDidData.getAuthKeyInfo().getPrivateKey(),
            createDidData.getAuthKeyInfo().getPublicKey());
    Proof proof = new Proof();
    proof.setCreator(registerCpt.getDid());
    proof.setSignatureValue(signValue);
    proof.setType("Secp256k1");
    registerCpt.setProof(proof);

    CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
    System.out.println("---------testRegisterCpt6------------ cptBaseInfo = " + JSONObject.toJSONString(cptBaseInfo));

    RegisterCpt registerCpt1 = new RegisterCpt();
    registerCpt1.setDid(createDidData.getDid());
    registerCpt1.setCptJsonSchema(jsonSchemaMap);
    registerCpt1.setTitle("test cpt");
    registerCpt1.setDescription("this is a test cpt");
    registerCpt1.setType("Proof");

    registerCpt1.setCreate(nowStr);
    registerCpt1.setUpdate(nowStr);
    registerCpt1.setCptId(cptBaseInfo.getCptId());

    CptInfo cptInfo11 = CptUtils.assemblyCptInfo(registerCpt1);
    cptInfo11.setCptVersion(cptInfo11.getCptVersion() + 1);
    String signValue1 = ECDSAUtils.sign(JSONArray.toJSON(cptInfo11).toString(), createDidData.getAuthKeyInfo().getPrivateKey(),
            createDidData.getAuthKeyInfo().getPublicKey());
    Proof proof1 = new Proof();
    proof1.setCreator(registerCpt.getDid());
    proof1.setSignatureValue(signValue1);
    proof1.setType("Secp256k1");
    registerCpt1.setProof(proof1);
    CptBaseInfo cptBaseInfo1 = didClient.updateCpt(registerCpt1);
    System.out.println("---------testQueryCptListByDid------------ cptBaseInfo1 = " + JSONObject.toJSONString(cptBaseInfo1));

    CptInfo cptInfo1 = didClient.queryCptById(cptBaseInfo.getCptId());
    System.out.println("---------testQueryCptListByDid------------ cptInfo1 = " + JSONObject.toJSONString(cptInfo1));

    Pages<CptInfo> cptInfoPages = didClient.queryCptListByDid(1, 10, registerCpt.getDid());
    System.out.println("---------testQueryCptListByDid------------ cptInfoPages = " + JSONObject.toJSONString(cptInfoPages));
  }

  @Test
  public void testUpdateCpt() {
    DIDClient didClient = new DIDClient();
    assertNull(didClient.updateCpt(new RegisterCpt()));
  }

  @Test
  public void testUpdateCpt10() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData = didClient.createDid(true);
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid(createDidData.getDid());
    authorityIssuer.setName("test");
    //        authorityIssuer.setKeyInfo(createDidData.getAuthKeyInfo());
    didClient.registerAuthIssuer(authorityIssuer);
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid(createDidData.getDid());
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
    //        registerCpt.setPrivateKey(createDidData.getAuthKeyInfo());
    CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
    registerCpt.setCptId(cptBaseInfo.getCptId());
    didClient.updateCpt(registerCpt);
  }

  @Test
  public void testUpdateCpt11() {
    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDescription("The characteristics of someone or something");
    registerCpt.setTitle("Dr");
    registerCpt.setDid("Did");
    assertNull(didClient.updateCpt(registerCpt));
  }

  @Test
  public void testUpdateCpt12() {
    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setType("Type");
    registerCpt.setDescription("The characteristics of someone or something");
    registerCpt.setTitle("Dr");
    registerCpt.setDid("Did");
    assertNull(didClient.updateCpt(registerCpt));
  }

  @Test
  public void testUpdateCpt2() throws Exception {
    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    //        registerCpt.setPrivateKey(ECDSAUtils.createKey());
    assertNull(didClient.updateCpt(registerCpt));
  }

  @Test
  public void testUpdateCpt3() {
    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    assertNull(didClient.updateCpt(registerCpt));
  }

  @Test
  public void testUpdateCpt4() {
    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setTitle("Dr");
    registerCpt.setDid("Did");
    assertNull(didClient.updateCpt(registerCpt));
  }

  @Test
  public void testUpdateCpt5() {
    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDescription("The characteristics of someone or something");
    registerCpt.setTitle("Dr");
    registerCpt.setDid("Did");
    assertNull(didClient.updateCpt(registerCpt));
  }

  @Test
  public void testUpdateCpt6() {
    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setType("Type");
    registerCpt.setDescription("The characteristics of someone or something");
    registerCpt.setTitle("Dr");
    registerCpt.setDid("Did");
    assertNull(didClient.updateCpt(registerCpt));
  }

  @Test
  public void testUpdateCpt7() {
    DIDClient didClient = new DIDClient();
    assertNull(didClient.updateCpt(new RegisterCpt()));
  }

  @Test
  public void testUpdateCpt8() throws Exception {
    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    //        registerCpt.setPrivateKey(ECDSAUtils.createKey());
    assertNull(didClient.updateCpt(registerCpt));
  }

  @Test
  public void testUpdateCpt9() {
    DIDClient didClient = new DIDClient();

    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    assertNull(didClient.updateCpt(registerCpt));
  }

  @Test
  public void testRevokeCredential() {

    DIDClient didClient = new DIDClient();
    didClient.revokeCredential(new RevokeCredential());
  }

  @Test
  public void testRevokeCredential2() throws Exception {

    DIDClient didClient = new DIDClient();

    RevokeCredential revokeCredential = new RevokeCredential();
    //        revokeCredential.setKeyInfo(ECDSAUtils.createKey());
    didClient.revokeCredential(revokeCredential);
  }

  @Test
  public void testRevokeCredential3() {

    DIDClient didClient = new DIDClient();
    didClient.revokeCredential(new RevokeCredential());
  }

  @Test
  public void testRevokeCredential4() throws Exception {
    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData1 = didClient.createDid(true);
    CreateDidData createDidData2 = didClient.createDid(true);
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid(createDidData1.getDid());
    authorityIssuer.setName("test");
    //        authorityIssuer.setKeyInfo(createDidData1.getAuthKeyInfo());
    didClient.registerAuthIssuer(authorityIssuer);
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid(createDidData1.getDid());
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
    //        registerCpt.setPrivateKey(createDidData1.getAuthKeyInfo());
    CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(cptBaseInfo.getCptId());
    createCredential.setIssuerDid(createDidData1.getDid());
    createCredential.setUserDid(createDidData2.getDid());
    createCredential.setExpirationDate("2021-05-30 11:11:11");
    Map<String, Object> claimMap = new HashMap<>();
    claimMap.put("name", "bob");
    createCredential.setClaim(claimMap);
    createCredential.setType("Proof");
    createCredential.setPrivateKey(createDidData1.getAuthKeyInfo());
    CredentialWrapper credentialWrapper = didClient.createCredential(createCredential);
    RevokeCredential revokeCredential = new RevokeCredential();
    revokeCredential.setCredId(credentialWrapper.getId());
    revokeCredential.setCptId(credentialWrapper.getCptId());
    revokeCredential.setDid(createDidData1.getDid());
    //        revokeCredential.setKeyInfo(createDidData1.getAuthKeyInfo());
    didClient.revokeCredential(revokeCredential);
  }

  @Test
  public void testCreateCredential() {

    DIDClient didClient = new DIDClient();
    didClient.createCredential(new CreateCredential());
  }

  @Test
  public void testCreateCredential2() {
    (new DIDClient()).createCredential(null);
  }

  @Test
  public void testCreateCredential3() {

    DIDClient didClient = new DIDClient();

    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(123L);
    createCredential.setUserDid("User Did");
    didClient.createCredential(createCredential);
  }

  @Test
  public void testCreateCredential4() {

    DIDClient didClient = new DIDClient();

    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(123L);
    createCredential.setUserDid("did:udpn:");
    didClient.createCredential(createCredential);
  }

  @Test
  public void testCreateCredential5() {

    DIDClient didClient = new DIDClient();
    didClient.createCredential(new CreateCredential());
  }

  @Test
  public void testCreateCredential6() {

    (new DIDClient()).createCredential(null);
  }

  @Test
  public void testCreateCredential7() {

    DIDClient didClient = new DIDClient();

    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(123L);
    createCredential.setUserDid("User Did");
    didClient.createCredential(createCredential);
  }

  @Test
  public void testCreateCredential8() {

    DIDClient didClient = new DIDClient();

    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(123L);
    createCredential.setUserDid("did:udpn:");
    didClient.createCredential(createCredential);
  }

  @Test
  public void testCreateCredential9() throws Exception {
    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData1 = didClient.createDid(true);
    CreateDidData createDidData2 = didClient.createDid(true);
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid(createDidData1.getDid());
    authorityIssuer.setName("test");

    DidDocument document = didClient.getDidDocument(createDidData1.getDid());
    PublicKeyInfo authPublicKeyInfo = new PublicKeyInfo();
    authPublicKeyInfo.setPublicKey(document.getAuthentication().getPublicKey());

    String authPublicKeySign = ECDSAUtils.sign(createDidData1.getAuthKeyInfo().getPublicKey(), createDidData1.getAuthKeyInfo().getPrivateKey(),
            createDidData1.getAuthKeyInfo().getPublicKey());
    authPublicKeyInfo.setPublicKeySign(authPublicKeySign);
    authPublicKeyInfo.setType(document.getAuthentication().getType());
    authorityIssuer.setPublicKeyInfo(authPublicKeyInfo);
    didClient.registerAuthIssuer(authorityIssuer);
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid(createDidData1.getDid());
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
    String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    registerCpt.setCreate(nowStr);
    registerCpt.setUpdate(nowStr);
    registerCpt.setCptId(GenerateCptIdUtils.getId());
    //registerCpt.setPrivateKey(createDidData1.getAuthKeyInfo());
    CptInfo cptInfo = CptUtils.assemblyCptInfo(registerCpt);
    String signValue = ECDSAUtils.sign(JSONArray.toJSON(cptInfo).toString(), createDidData1.getAuthKeyInfo().getPrivateKey(),
            createDidData1.getAuthKeyInfo().getPublicKey());
    Proof proof = new Proof();
    proof.setCreator(registerCpt.getDid());
    proof.setSignatureValue(signValue);
    proof.setType("Secp256k1");
    registerCpt.setProof(proof);
    CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(cptBaseInfo.getCptId());
    createCredential.setIssuerDid(createDidData1.getDid());
    createCredential.setUserDid(createDidData2.getDid());
    createCredential.setExpirationDate("2022-08-30");
    Map<String, Object> claimMap = new HashMap<>();
    claimMap.put("name", "bob");
    createCredential.setClaim(claimMap);
    createCredential.setType("Proof");
    createCredential.setPrivateKey(createDidData1.getAuthKeyInfo());
    CredentialWrapper credential = didClient.createCredential(createCredential);
    System.out.println();
    System.out.println(credential.getId());
    System.out.println(createCredential.getCptId());
    System.out.println(createDidData2.getDid());
  }

  @Test
  public void testRevokedCredential() throws Exception {
    DIDClient didClient = new DIDClient();
    didClient.init();
    //创建did
    CreateDidData createDidData1 = didClient.createDid(true);
    CreateDidData createDidData2 = didClient.createDid(true);
    //注册发证方
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid(createDidData1.getDid());
    authorityIssuer.setName("test");
    DidDocument document = didClient.getDidDocument(createDidData1.getDid());
    PublicKeyInfo authPublicKeyInfo = new PublicKeyInfo();
    authPublicKeyInfo.setPublicKey(document.getAuthentication().getPublicKey());
    String authPublicKeySign = ECDSAUtils.sign(createDidData1.getAuthKeyInfo().getPublicKey(), createDidData1.getAuthKeyInfo().getPrivateKey(),
            createDidData1.getAuthKeyInfo().getPublicKey());
    authPublicKeyInfo.setPublicKeySign(authPublicKeySign);
    authPublicKeyInfo.setType(document.getAuthentication().getType());
    authorityIssuer.setPublicKeyInfo(authPublicKeyInfo);
    didClient.registerAuthIssuer(authorityIssuer);

    //注册凭证模板
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid(createDidData1.getDid());
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
    String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    registerCpt.setCreate(nowStr);
    registerCpt.setUpdate(nowStr);
    registerCpt.setCptId(GenerateCptIdUtils.getId());
    CptInfo cptInfo = CptUtils.assemblyCptInfo(registerCpt);
    String signValue = ECDSAUtils.sign(JSONArray.toJSON(cptInfo).toString(), createDidData1.getAuthKeyInfo().getPrivateKey(),
            createDidData1.getAuthKeyInfo().getPublicKey());
    Proof proof = new Proof();
    proof.setCreator(registerCpt.getDid());
    proof.setSignatureValue(signValue);
    proof.setType("Secp256k1");
    registerCpt.setProof(proof);
    CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);

    //签发凭证
    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(cptBaseInfo.getCptId());
    createCredential.setIssuerDid(createDidData1.getDid());
    createCredential.setUserDid(createDidData2.getDid());
    createCredential.setExpirationDate("2022-08-30");
    Map<String, Object> claimMap = new HashMap<>();
    claimMap.put("name", "bob");
    createCredential.setClaim(claimMap);
    createCredential.setType("Proof");
    createCredential.setPrivateKey(createDidData1.getAuthKeyInfo());
    CredentialWrapper credential = didClient.createCredential(createCredential);

    //吊销凭证
    RevokeCredential revokeCredential = new RevokeCredential();
    revokeCredential.setDid(createDidData1.getDid());
    revokeCredential.setRovekeDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    revokeCredential.setCredId(credential.getId());
    StringBuffer stringBuffer = new StringBuffer();
    String append = stringBuffer.append(revokeCredential.getDid())
            .append(revokeCredential.getRovekeDate())
            .append(revokeCredential.getCredId())
            .toString();
    String signValue1 = ECDSAUtils.sign(append, createDidData1.getAuthKeyInfo().getPrivateKey(),
            createDidData1.getAuthKeyInfo().getPublicKey());
    Proof proof1 = new Proof();
    proof1.setCreator(createDidData1.getDid());
    proof1.setSignatureValue(signValue1);
    proof1.setType("Secp256k1");
    revokeCredential.setProof(proof1);
    revokeCredential.setCptId(credential.getCptId());

    Boolean aBoolean = didClient.revokeCredential(revokeCredential);
    System.out.println(aBoolean);

    //查询吊销列表
    Pages<BaseCredential> revokedCredList = didClient.getRevokedCredList(1, 2, createDidData1.getDid());
    for (BaseCredential credential1 : revokedCredList.getResult()){
      System.out.println("----------------------- testRevokedCredential = " + JSONObject.toJSONString(credential1));
    }
  }

  @Test
  public void testVerifyCredential() {

    DIDClient didClient = new DIDClient();
    Credential credential = new Credential();
    didClient.verifyCredential(credential, new PublicKey());
  }

  @Test
  public void testVerifyCredential2() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData1 = didClient.createDid(true);
    CreateDidData createDidData2 = didClient.createDid(true);
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid(createDidData1.getDid());
    authorityIssuer.setName("test");
    didClient.registerAuthIssuer(authorityIssuer);
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid(createDidData1.getDid());
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
    CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(cptBaseInfo.getCptId());
    createCredential.setIssuerDid(createDidData1.getDid());
    createCredential.setUserDid(createDidData2.getDid());
    createCredential.setExpirationDate("2021-05-30 11:11:11");
    Map<String, Object> claimMap = new HashMap<>();
    claimMap.put("name", "bob");
    createCredential.setClaim(claimMap);
    createCredential.setType("Proof");
    createCredential.setPrivateKey(createDidData1.getAuthKeyInfo());
    CredentialWrapper credentialWrapper = didClient.createCredential(createCredential);
    Credential credential = new Credential();
    BeanUtil.copyProperties(credentialWrapper, credential);
    PublicKey publicKey = new PublicKey();
    publicKey.setType(ECDSAUtils.type);
    publicKey.setPublicKey(createDidData1.getAuthKeyInfo().getPublicKey());
    didClient.verifyCredential(credential, publicKey);
  }

  @Test
  public void testGetRevokedCredList() {

    (new DIDClient()).getRevokedCredList(1, 10, "Did");
  }

  @Test
  public void testGetRevokedCredList2() {

    (new DIDClient()).getRevokedCredList(1, 10, "did:udpn:");
  }

  @Test
  public void testGetRevokedCredList3() {

    (new DIDClient()).getRevokedCredList(1, 10, "Did");
  }

  @Test
  public void testGetRevokedCredList4() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    CreateDidData createDidData1 = didClient.createDid(true);
    CreateDidData createDidData2 = didClient.createDid(true);
    RegisterAuthorityIssuer authorityIssuer = new RegisterAuthorityIssuer();
    authorityIssuer.setDid(createDidData1.getDid());
    authorityIssuer.setName("test");
    didClient.registerAuthIssuer(authorityIssuer);
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid(createDidData1.getDid());
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
    CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
    CreateCredential createCredential = new CreateCredential();
    createCredential.setCptId(cptBaseInfo.getCptId());
    createCredential.setIssuerDid(createDidData1.getDid());
    createCredential.setUserDid(createDidData2.getDid());
    createCredential.setExpirationDate("2021-05-30 11:11:11");
    Map<String, Object> claimMap = new HashMap<>();
    claimMap.put("name", "bob");
    createCredential.setClaim(claimMap);
    createCredential.setType("Proof");
    createCredential.setPrivateKey(createDidData1.getAuthKeyInfo());
    CredentialWrapper credentialWrapper = didClient.createCredential(createCredential);
    RevokeCredential revokeCredential = new RevokeCredential();
    revokeCredential.setCredId(credentialWrapper.getId());
    revokeCredential.setCptId(credentialWrapper.getCptId());
    revokeCredential.setDid(createDidData1.getDid());
    didClient.revokeCredential(revokeCredential);
    didClient.getRevokedCredList(1, 2, createDidData1.getDid());
  }

}
