package com.reddate.did.did.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import com.alibaba.fastjson.JSON;
import com.reddate.did.DIDClient;
import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.request.ResetDid;
import com.reddate.did.protocol.response.CreateDidData;
import com.reddate.did.protocol.response.DidDocument;
import com.reddate.did.protocol.response.ResponseData;
import com.reddate.did.service.impl.DidServiceImpl;
import com.reddate.did.util.commons.ECDSAUtils;

import org.junit.Test;

public class DidServiceImplTest {

  private CreateDidData createDidData;

  @Test
  public void testCreateDidOnChain() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    ResponseData<CreateDidData> actualCreateDidResult = didServiceImpl.createDid(true);
    assertEquals("success", actualCreateDidResult.getErrorEnMessage());
    assertEquals(0, actualCreateDidResult.getErrorCode().intValue());
  }

  @Test
  public void testCreateDidNotOnChain() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    ResponseData<CreateDidData> actualCreateDidResult = didServiceImpl.createDid(false);
    assertEquals("success", actualCreateDidResult.getErrorEnMessage());
    assertEquals(0, actualCreateDidResult.getErrorCode().intValue());
  }

  @Test
  public void testChainDidDocument() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    DidDocument didDocument = new DidDocument();
    ResponseData<Boolean> actualChainDidDocumentResult =
        didServiceImpl.storeDidDocumentOnChain(didDocument, new PublicKey());
    assertFalse(actualChainDidDocumentResult.getResult());
    assertEquals("public key is empty", actualChainDidDocumentResult.getErrorEnMessage());
    assertEquals(1009, actualChainDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testChainDidDocument2() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    ResponseData<Boolean> actualChainDidDocumentResult =
        didServiceImpl.storeDidDocumentOnChain(null, new PublicKey());
    assertFalse(actualChainDidDocumentResult.getResult());
    assertEquals("parameter is empty", actualChainDidDocumentResult.getErrorEnMessage());
    assertEquals(1001, actualChainDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testChainDidDocument3() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    ResponseData<Boolean> actualChainDidDocumentResult =
        didServiceImpl.storeDidDocumentOnChain(new DidDocument(), null);
    assertFalse(actualChainDidDocumentResult.getResult());
    assertEquals("public key is empty", actualChainDidDocumentResult.getErrorEnMessage());
    assertEquals(1009, actualChainDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testChainDidDocument4() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    DidDocument didDocument = new DidDocument();
    PublicKey publicKey = new PublicKey();
    publicKey.setPublicKey("0123456789ABCDEF");
    ResponseData<Boolean> actualChainDidDocumentResult =
        didServiceImpl.storeDidDocumentOnChain(didDocument, publicKey);
    assertFalse(actualChainDidDocumentResult.getResult());
    assertEquals("public key different", actualChainDidDocumentResult.getErrorEnMessage());
    assertEquals(1031, actualChainDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testChainDidDocument5() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    String json =
        "{\n"
            + "\t\t\"created\": \"2021-05-31 16:16:01\",\n"
            + "\t\t\"proof\": {\n"
            + "\t\t\t\"creator\": \"did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7\",\n"
            + "\t\t\t\"type\": \"Secp256k1\",\n"
            + "\t\t\t\"signatureValue\": \"nuqZaRIXMosZi2Ct372v/2fqjuaWRcDbH0DHoXudCO0H0EzMJup6Rt/0mgn2dfkyl9uScBbb6spFgR+FY2i1JBw=\"\n"
            + "\t\t},\n"
            + "\t\t\"recovery\": {\n"
            + "\t\t\t\"publicKey\": \"9264733601149973566780937989538349411767190493170094759771628788205941436219986642911333967865913988463210722831180443034761027871811639972755466176064047\",\n"
            + "\t\t\t\"type\": \"Secp256k1\"\n"
            + "\t\t},\n"
            + "\t\t\"updated\": \"2021-05-31 16:16:01\",\n"
            + "\t\t\"version\": \"1\",\n"
            + "\t\t\"did\": \"did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7\",\n"
            + "\t\t\"authentication\": {\n"
            + "\t\t\t\"publicKey\": \"12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114\",\n"
            + "\t\t\t\"type\": \"Secp256k1\"\n"
            + "\t\t}\n"
            + "\t}";
    DidDocument didDocument = JSON.parseObject(json, DidDocument.class);
    PublicKey publicKey = new PublicKey();
    /*publicKey.setType(ECDSAUtils.type);
    publicKey.setPublicKey("12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");*/
    ResponseData<Boolean> actualChainDidDocumentResult =
        didServiceImpl.storeDidDocumentOnChain(didDocument, publicKey);
    assertEquals(
        "already exists on the chain did document",
        actualChainDidDocumentResult.getErrorEnMessage());
    assertEquals(1055, actualChainDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testGetDidDocument() {
    ResponseData<DidDocument> actualDidDocument = (new DidServiceImpl()).getDidDocument("Did");
    assertNull(actualDidDocument.getResult());
    assertEquals("parameter illegal format", actualDidDocument.getErrorEnMessage());
    assertEquals(1002, actualDidDocument.getErrorCode().intValue());
  }

  @Test
  public void testGetDidDocument2() {
    ResponseData<DidDocument> actualDidDocument =
        (new DidServiceImpl()).getDidDocument("did:udpn:");
    assertNull(actualDidDocument.getResult());
    assertEquals("parameter illegal format", actualDidDocument.getErrorEnMessage());
    assertEquals(1002, actualDidDocument.getErrorCode().intValue());
  }

  @Test
  public void testGetDidDocument3() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    ResponseData<DidDocument> actualDidDocument =
        (new DidServiceImpl()).getDidDocument("did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7");
  }

  @Test
  public void testVerifyDidDocument() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    DidDocument didDocument = new DidDocument();
    ResponseData<Boolean> actualVerifyDidDocumentResult =
        didServiceImpl.verifyDidDocument(didDocument, new PublicKey());
    assertFalse(actualVerifyDidDocumentResult.getResult());
    assertEquals("public key is empty", actualVerifyDidDocumentResult.getErrorEnMessage());
    assertEquals(1009, actualVerifyDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testVerifyDidDocument2() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    ResponseData<Boolean> actualVerifyDidDocumentResult =
        didServiceImpl.verifyDidDocument(null, new PublicKey());
    assertFalse(actualVerifyDidDocumentResult.getResult());
    assertEquals("parameter is empty", actualVerifyDidDocumentResult.getErrorEnMessage());
    assertEquals(1001, actualVerifyDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testVerifyDidDocument3() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    ResponseData<Boolean> actualVerifyDidDocumentResult =
        didServiceImpl.verifyDidDocument(new DidDocument(), null);
    assertFalse(actualVerifyDidDocumentResult.getResult());
    assertEquals("public key is empty", actualVerifyDidDocumentResult.getErrorEnMessage());
    assertEquals(1009, actualVerifyDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testVerifyDidDocument5() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    DidDocument didDocument = new DidDocument();

    PublicKey publicKey = new PublicKey();
    publicKey.setPublicKey("0123456789ABCDEF");
    ResponseData<Boolean> actualVerifyDidDocumentResult =
        didServiceImpl.verifyDidDocument(didDocument, publicKey);
    assertFalse(actualVerifyDidDocumentResult.getResult());
    assertEquals("public key different", actualVerifyDidDocumentResult.getErrorEnMessage());
    assertEquals(1031, actualVerifyDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testVerifyDidDocument4() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    String json =
        "{\n"
            + "\t\t\"created\": \"2021-05-31 16:16:01\",\n"
            + "\t\t\"proof\": {\n"
            + "\t\t\t\"creator\": \"did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7\",\n"
            + "\t\t\t\"type\": \"Secp256k1\",\n"
            + "\t\t\t\"signatureValue\": \"nuqZaRIXMosZi2Ct372v/2fqjuaWRcDbH0DHoXudCO0H0EzMJup6Rt/0mgn2dfkyl9uScBbb6spFgR+FY2i1JBw\"\n"
            + "\t\t},\n"
            + "\t\t\"recovery\": {\n"
            + "\t\t\t\"publicKey\": \"9264733601149973566780937989538349411767190493170094759771628788205941436219986642911333967865913988463210722831180443034761027871811639972755466176064047\",\n"
            + "\t\t\t\"type\": \"Secp256k1\"\n"
            + "\t\t},\n"
            + "\t\t\"updated\": \"2021-05-31 16:16:01\",\n"
            + "\t\t\"version\": \"1\",\n"
            + "\t\t\"did\": \"did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7\",\n"
            + "\t\t\"authentication\": {\n"
            + "\t\t\t\"publicKey\": \"12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114\",\n"
            + "\t\t\t\"type\": \"Secp256k1\"\n"
            + "\t\t}\n"
            + "\t}";
    DidDocument didDocument = JSON.parseObject(json, DidDocument.class);
    PublicKey publicKey = new PublicKey();
    publicKey.setType(ECDSAUtils.type);
    publicKey.setPublicKey(
        "12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");
    ResponseData<Boolean> actualVerifyDidDocumentResult =
        didServiceImpl.verifyDidDocument(didDocument, publicKey);
    assertEquals(
        "did document validation success", actualVerifyDidDocumentResult.getErrorEnMessage());
    assertEquals(1054, actualVerifyDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testVerifyDidDocument6() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    String json =
        "{\n"
            + "\t\t\"created\": \"2021-05-31 16:16:01\",\n"
            + "\t\t\"proof\": {\n"
            + "\t\t\t\"creator\": \"aa\",\n"
            + "\t\t\t\"type\": \"aa\",\n"
            + "\t\t\t\"signatureValue\": \"nuqZaRIXMosZi2Ct372v/2fqjuaWRcDbH0DHoXudCO0H0EzMJup6Rt/0mgn2dfkyl9uScBbb6spFgR+FY2i1JBw\"\n"
            + "\t\t},\n"
            + "\t\t\"recovery\": {\n"
            + "\t\t\t\"publicKey\": \"9264733601149973566780937989538349411767190493170094759771628788205941436219986642911333967865913988463210722831180443034761027871811639972755466176064047\",\n"
            + "\t\t\t\"type\": \"Secp256k1\"\n"
            + "\t\t},\n"
            + "\t\t\"updated\": \"2021-05-31 16:16:01\",\n"
            + "\t\t\"version\": \"1\",\n"
            + "\t\t\"did\": \"did:udpn:2b9aNezzzEoivPRFMGYWAZjFDDk7\",\n"
            + "\t\t\"authentication\": {\n"
            + "\t\t\t\"publicKey\": \"12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114\",\n"
            + "\t\t\t\"type\": \"Secp256k1\"\n"
            + "\t\t}\n"
            + "\t}";
    DidDocument didDocument = JSON.parseObject(json, DidDocument.class);
    PublicKey publicKey = new PublicKey();
    publicKey.setType(ECDSAUtils.type);
    publicKey.setPublicKey(
        "12643727689395583394979568184128616516492937400878597880464365342708835202204927874414215983137241785142615526085767928082912223976891499877657952071799114");
    ResponseData<Boolean> actualVerifyDidDocumentResult =
        didServiceImpl.verifyDidDocument(didDocument, publicKey);
    assertEquals(
        "did document validation success", actualVerifyDidDocumentResult.getErrorEnMessage());
    assertEquals(1054, actualVerifyDidDocumentResult.getErrorCode().intValue());
  }

  @Test
  public void testResetDidAuth() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    ResponseData<KeyPair> actualResetDidAuthResult = didServiceImpl.resetDidAuth(new ResetDid());
    assertNull(actualResetDidAuthResult.getResult());
    assertEquals("invalid did", actualResetDidAuthResult.getErrorEnMessage());
    assertEquals(1035, actualResetDidAuthResult.getErrorCode().intValue());
  }

  @Test
  public void testResetDidAuth2() {
    ResponseData<KeyPair> actualResetDidAuthResult = (new DidServiceImpl()).resetDidAuth(null);
    assertNull(actualResetDidAuthResult.getResult());
    assertEquals("parameter is empty", actualResetDidAuthResult.getErrorEnMessage());
    assertEquals(1001, actualResetDidAuthResult.getErrorCode().intValue());
  }

  @Test
  public void testResetDidAuth3() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();

    ResetDid resetDid = new ResetDid();
    //        resetDid.setDid("Did");
    ResponseData<KeyPair> actualResetDidAuthResult = didServiceImpl.resetDidAuth(resetDid);
    assertNull(actualResetDidAuthResult.getResult());
    assertEquals("invalid did", actualResetDidAuthResult.getErrorEnMessage());
    assertEquals(1035, actualResetDidAuthResult.getErrorCode().intValue());
  }

  @Test
  public void testResetDidAuth4() {
    DidServiceImpl didServiceImpl = new DidServiceImpl();

    ResetDid resetDid = new ResetDid();
    //        resetDid.setDid("did:udpn:");
    ResponseData<KeyPair> actualResetDidAuthResult = didServiceImpl.resetDidAuth(resetDid);
    assertNull(actualResetDidAuthResult.getResult());
    assertEquals("invalid did", actualResetDidAuthResult.getErrorEnMessage());
    assertEquals(1035, actualResetDidAuthResult.getErrorCode().intValue());
  }

  @Test
  public void testResetDidAuth5() {
    DIDClient didClient = new DIDClient();
    didClient.init();
    DidServiceImpl didServiceImpl = new DidServiceImpl();
    ResetDid resetDid = new ResetDid();
    //        resetDid.setDid("did:udpn:2hpvEGY385VrEobXCojzSwyxmSfj");
    KeyPair keyPair = new KeyPair();
    keyPair.setType(ECDSAUtils.type);
    keyPair.setPublicKey(
        "12156923891569471103722266041056809002484787917907540712964614392244837098710089764144784078608071380730629780541985369374029290359107324769203846926972828");
    keyPair.setPrivateKey(
        "26770136929568551967195577749099807429609289029053927062452240099070488836336");
    //        resetDid.setRecoveryKey(keyPair);
    ResponseData<KeyPair> actualResetDidAuthResult = didServiceImpl.resetDidAuth(resetDid);
    assertEquals("success", actualResetDidAuthResult.getErrorEnMessage());
    assertEquals(0, actualResetDidAuthResult.getErrorCode().intValue());
  }
}
