package com.reddate.did.did.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.protocol.common.BaseDidDocument;
import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.response.DidDocument;
import com.reddate.did.util.DidUtils;
import com.reddate.did.util.commons.ECDSAUtils;

public class DidUtilsTest {
  @Test
  public void testGenerateBaseDidDocument() throws Exception {
    KeyPair primaryKeyPair = ECDSAUtils.createKey();
    BaseDidDocument actualGenerateBaseDidDocumentResult =
        DidUtils.generateBaseDidDocument(primaryKeyPair, ECDSAUtils.createKey());
    assertEquals("https://w3id.org/did/v1", actualGenerateBaseDidDocumentResult.getContext());
    assertEquals("Secp256k1", actualGenerateBaseDidDocumentResult.getRecovery().getType());
    assertEquals("Secp256k1", actualGenerateBaseDidDocumentResult.getAuthentication().getType());
  }

  @Test
  public void testGenerateDidIdentifierByBaseDidDocument() throws Exception {
    assertEquals(
        "sDPCuZvEYijqBK9UCqTTx5P6m8U",
        DidUtils.generateDidIdentifierByBaseDidDocument(new BaseDidDocument()));
  }

  @Test
  public void testGenerateDidIdentifierByBaseDidDocument2() throws Exception {
    KeyPair primaryKeyPair = ECDSAUtils.createKey();
    DidUtils.generateDidIdentifierByBaseDidDocument(
        DidUtils.generateBaseDidDocument(primaryKeyPair, ECDSAUtils.createKey()));
  }

  @Test
  public void testGenerateDidIdentifierByBaseDidDocument3() throws Exception {
    BaseDidDocument baseDidDocument = new BaseDidDocument();
    baseDidDocument.setRecovery(new PublicKey());
    assertEquals(
        "3ieVUGkaPM6J3HNQKRvUSp1VrHNb",
        DidUtils.generateDidIdentifierByBaseDidDocument(baseDidDocument));
  }

  @Test
  public void testGenerateDidByDidIdentifier() throws Exception {
    assertEquals("did:udpn:42", DidUtils.generateDidByDidIdentifier("42"));
    assertEquals("", DidUtils.generateDidByDidIdentifier(""));
  }

  @Test
  public void testGenerateDidDocument() throws Exception {
    KeyPair primaryKeyPair = ECDSAUtils.createKey();
    DidDocument actualGenerateDidDocumentResult =
        DidUtils.generateDidDocument(primaryKeyPair, ECDSAUtils.createKey(), "Did");
    assertEquals("1", actualGenerateDidDocumentResult.getVersion());
    assertEquals("Did", actualGenerateDidDocumentResult.getDid());
    assertEquals("Secp256k1", actualGenerateDidDocumentResult.getAuthentication().getType());
    assertEquals("Secp256k1", actualGenerateDidDocumentResult.getRecovery().getType());
  }

  @Test
  public void testRenewDidDocument() throws Exception {
    DidDocument didDocument = new DidDocument();
    DidDocument actualRenewDidDocumentResult =
        DidUtils.renewDidDocument(didDocument, "Public Key", ECDSAUtils.createKey());
    assertSame(didDocument, actualRenewDidDocumentResult);
    assertNull(actualRenewDidDocumentResult.getProof());
    PublicKey recovery = actualRenewDidDocumentResult.getRecovery();
    assertEquals("Public Key", recovery.getPublicKey());
    assertEquals("Secp256k1", actualRenewDidDocumentResult.getAuthentication().getType());
    assertEquals("Secp256k1", recovery.getType());
  }

  @Test
  public void testIsDidValid() {
    assertFalse(DidUtils.isDidValid("Did"));
    assertFalse(DidUtils.isDidValid("did:udpn:"));
    assertFalse(DidUtils.isDidValid(""));
  }

  @Test
  public void testConvertDidIdentifier() {
    assertEquals("", DidUtils.convertDidIdentifier("Did"));
    assertEquals("", DidUtils.convertDidIdentifier("did:udpn:"));
    assertEquals("", DidUtils.convertDidIdentifier(""));
  }

  @Test
  public void testComparePrimaryPublicKey() {

    DidUtils.comparePrimaryPublicKey(new DidDocument(), "Public Key");
  }

  @Test
  public void testComparePrimaryPublicKey2() throws Exception {
    KeyPair primaryKeyPair = ECDSAUtils.createKey();
    DidUtils.comparePrimaryPublicKey(
        DidUtils.generateDidDocument(primaryKeyPair, ECDSAUtils.createKey(), "Did"), "Public Key");
  }

  @Test
  public void testComparePrimaryPublicKey3() {

    DidUtils.comparePrimaryPublicKey(null, "Public Key");
  }

  @Test
  public void testComparePrimaryPublicKey4() {

    DidUtils.comparePrimaryPublicKey(new DidDocument(), "");
  }

  @Test
  public void testComparePrimaryPublicKey5() throws Exception {

    KeyPair createKeyResult = ECDSAUtils.createKey();
    createKeyResult.setPublicKey("Public Key");
    DidUtils.comparePrimaryPublicKey(
        DidUtils.generateDidDocument(createKeyResult, ECDSAUtils.createKey(), "Did"), "Public Key");
  }

  @Test
  public void testCompareReservePublicKey() {
    DidUtils.compareReservePublicKey(new DidDocument(), "Public Key");
  }

  @Test
  public void testCompareReservePublicKey2() throws Exception {

    KeyPair primaryKeyPair = ECDSAUtils.createKey();
    DidUtils.compareReservePublicKey(
        DidUtils.generateDidDocument(primaryKeyPair, ECDSAUtils.createKey(), "Did"), "Public Key");
  }

  @Test
  public void testCompareReservePublicKey3() {

    DidUtils.compareReservePublicKey(null, "Public Key");
  }

  @Test
  public void testCompareReservePublicKey4() {

    DidUtils.compareReservePublicKey(new DidDocument(), "");
  }

  @Test
  public void testCompareReservePublicKey5() throws Exception {

    KeyPair createKeyResult = ECDSAUtils.createKey();
    createKeyResult.setPublicKey("Public Key");
    DidUtils.compareReservePublicKey(
        DidUtils.generateDidDocument(ECDSAUtils.createKey(), createKeyResult, "Did"), "Public Key");
  }
}
