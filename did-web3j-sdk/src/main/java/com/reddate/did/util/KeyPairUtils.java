// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.util;

import com.reddate.did.constant.ErrorCode;
import com.reddate.did.constant.ResultCode;
import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.util.commons.ECDSAUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigInteger;
import java.util.Objects;

public class KeyPairUtils {

  /**
   * Verify the validity of publicKey object
   *
   * @param publicKey
   * @author shaopengfei
   */
  public static ResultCode isPublicKeyValid(PublicKey publicKey) throws Exception {
    if (Objects.isNull(publicKey)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY);
    }
    if (StringUtils.isBlank(publicKey.getType())) {
      return new ResultCode(ErrorCode.ENCRYPTION_TYPE_IS_EMPTY);
    }
    if (!ECDSAUtils.type.equals(publicKey.getType())) {
      return new ResultCode(ErrorCode.ENCRYPTION_TYPE_ILLEGAL);
    }
    if (StringUtils.isBlank(publicKey.getPublicKey())) {
      return new ResultCode(ErrorCode.PUBLIC_KEY_IS_EMPTY);
    }
    if (!isPublickKeyValid(publicKey.getPublicKey())) {
      return new ResultCode(ErrorCode.PUBLIC_KEY_ILLEGAL_FORMAT);
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Verify the validity of key object
   *
   * @param keyPair
   * @author shaopengfei
   */
  public static ResultCode isKeyPairValid(KeyPair keyPair) throws Exception {
    if (Objects.isNull(keyPair)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY);
    }
    if (StringUtils.isBlank(keyPair.getType())) {
      return new ResultCode(ErrorCode.ENCRYPTION_TYPE_IS_EMPTY);
    }
    if (!ECDSAUtils.type.equals(keyPair.getType())) {
      return new ResultCode(ErrorCode.ENCRYPTION_TYPE_ILLEGAL);
    }
    if (StringUtils.isBlank(keyPair.getPublicKey())) {
      return new ResultCode(ErrorCode.PUBLIC_KEY_IS_EMPTY);
    }
    if (!isPublickKeyValid(keyPair.getPublicKey())) {
      return new ResultCode(ErrorCode.PUBLIC_KEY_ILLEGAL_FORMAT);
    }
    if (StringUtils.isBlank(keyPair.getPrivateKey())) {
      return new ResultCode(ErrorCode.PRIVATE_KEY_IS_EMPTY);
    }
    if (!isPrivateKeyValid(keyPair.getPrivateKey())) {
      return new ResultCode(ErrorCode.PRIVATE_KEY_ILLEGAL_FORMAT);
    }
    if (!isKeyPairMatch(keyPair.getPrivateKey(), keyPair.getPublicKey())) {
      return new ResultCode(ErrorCode.PUBLIC_AND_PRIVATE_KEY_MISMATCH);
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Verify the validity of public key format
   *
   * @param publicKey
   * @author shaopengfei
   */
  public static boolean isPublickKeyValid(String publicKey) {
    return (StringUtils.isNotEmpty(publicKey)
        && StringUtils.isAlphanumeric(publicKey)
        && NumberUtils.isDigits(publicKey));
  }

  /**
   * Verify the validity of private key format
   *
   * @param privateKey
   * @author shaopengfei
   */
  public static boolean isPrivateKeyValid(String privateKey) {
    return (StringUtils.isNotEmpty(privateKey)
        && NumberUtils.isDigits(privateKey)
        && new BigInteger(privateKey).compareTo(BigInteger.ZERO) > 0);
  }

  /**
   * Verify whether the private key and public key are paired
   *
   * @param privateKey
   * @param publicKey
   * @author shaopengfei
   */
  public static boolean isKeyPairMatch(String privateKey, String publicKey) throws Exception {
    try {
      return false;
    } catch (Exception e) {
      throw new Exception("invalid privateKey");
    }
  }
}
