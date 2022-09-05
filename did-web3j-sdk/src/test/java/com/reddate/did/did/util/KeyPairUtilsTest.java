package com.reddate.did.did.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.reddate.did.constant.ErrorCode;
import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.util.KeyPairUtils;
import com.reddate.did.util.commons.ECDSAUtils;

public class KeyPairUtilsTest {
  @Test
  public void testIsKeyPairValid() throws Exception {
    assertEquals(ErrorCode.SUCCESS, KeyPairUtils.isKeyPairValid(ECDSAUtils.createKey()));
    assertEquals(ErrorCode.PUBLIC_KEY_IS_EMPTY, KeyPairUtils.isKeyPairValid(new KeyPair()));
    assertEquals(ErrorCode.PARAMETER_IS_EMPTY, KeyPairUtils.isKeyPairValid(null));
  }

  @Test
  public void testIsKeyPairValid2() throws Exception {
    KeyPair createKeyResult = ECDSAUtils.createKey();
    createKeyResult.setPublicKey("Public Key");
    assertEquals(ErrorCode.PUBLIC_KEY_ILLEGAL_FORMAT, KeyPairUtils.isKeyPairValid(createKeyResult));
  }

  @Test
  public void testIsKeyPairValid3() throws Exception {
    KeyPair createKeyResult = ECDSAUtils.createKey();
    createKeyResult.setPrivateKey("Private Key");
    assertEquals(
        ErrorCode.PRIVATE_KEY_ILLEGAL_FORMAT, KeyPairUtils.isKeyPairValid(createKeyResult));
  }

  @Test
  public void testIsKeyPairValid4() throws Exception {
    KeyPair createKeyResult = ECDSAUtils.createKey();
    createKeyResult.setPublicKey("42");
    assertEquals(
        ErrorCode.PUBLIC_AND_PRIVATE_KEY_MISMATCH, KeyPairUtils.isKeyPairValid(createKeyResult));
  }

  @Test
  public void testIsKeyPairValid5() throws Exception {
    KeyPair createKeyResult = ECDSAUtils.createKey();
    createKeyResult.setPrivateKey("");
    assertEquals(ErrorCode.PRIVATE_KEY_IS_EMPTY, KeyPairUtils.isKeyPairValid(createKeyResult));
  }

  @Test
  public void testIsPublickKeyValid() {
    assertFalse(KeyPairUtils.isPublickKeyValid("Public Key"));
    assertFalse(KeyPairUtils.isPublickKeyValid("foo"));
    assertTrue(KeyPairUtils.isPublickKeyValid("42"));
    assertFalse(KeyPairUtils.isPublickKeyValid(""));
  }

  @Test
  public void testIsPrivateKeyValid() {
    assertFalse(KeyPairUtils.isPrivateKeyValid("Private Key"));
    assertTrue(KeyPairUtils.isPrivateKeyValid("42"));
    assertFalse(KeyPairUtils.isPrivateKeyValid(""));
  }

  @Test
  public void testIsKeyPairMatch() throws Exception {
    assertThrows(Exception.class, () -> KeyPairUtils.isKeyPairMatch("Private Key", "Public Key"));
    assertFalse(KeyPairUtils.isKeyPairMatch("42", "Public Key"));
  }
}
