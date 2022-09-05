// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.util;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.reddate.did.constant.CurrencyCode;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.constant.ResultCode;
import com.reddate.did.protocol.common.BaseDidDocument;
import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.response.DidDocument;
import com.reddate.did.util.commons.ECDSAUtils;
import com.reddate.did.util.commons.RipeMDUtils;
import com.reddate.did.util.commons.Base58Utils;
import com.reddate.did.util.commons.SHA256Utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public final class DidUtils {

  /**
   * Generate base did document
   *
   * @param primaryKeyPair
   * @param alternateKeyPair
   * @author shaopengfei
   */
  public static BaseDidDocument generateBaseDidDocument(
          KeyPair primaryKeyPair, KeyPair alternateKeyPair) throws Exception {
    BaseDidDocument baseDidDocument = new BaseDidDocument();
    baseDidDocument.setContext(CurrencyCode.W3C_FORMAT_ADDRESS);
    PublicKey primaryPublicKey = new PublicKey();
    primaryPublicKey.setType(ECDSAUtils.type);
    primaryPublicKey.setPublicKey(primaryKeyPair.getPublicKey());
    baseDidDocument.setAuthentication(primaryPublicKey);
    PublicKey alternatePublicKey = new PublicKey();
    alternatePublicKey.setType(ECDSAUtils.type);
    alternatePublicKey.setPublicKey(alternateKeyPair.getPublicKey());
    baseDidDocument.setRecovery(alternatePublicKey);
    return baseDidDocument;
  }

  /**
   * Generate base did document from
   *
   * @param primaryKeyPair
   * @param alternateKeyPair
   * @author shaopengfei
   */
  public static BaseDidDocument generateBaseDidDocument(final DidDocument didDocument)
      throws Exception {
    BaseDidDocument baseDidDocument = new BaseDidDocument();
    baseDidDocument.setContext(CurrencyCode.W3C_FORMAT_ADDRESS);
    baseDidDocument.setAuthentication(didDocument.getAuthentication());
    baseDidDocument.setRecovery(didDocument.getRecovery());
    return baseDidDocument;
  }

  /**
   * Generate did identifier (encode the base did document after hashing twice)
   *
   * @param baseDidDocument
   * @author shaopengfei
   */
  public static String generateDidIdentifierByBaseDidDocument(BaseDidDocument baseDidDocument)
      throws Exception {
    String baseDidDocumentStr = JSONArray.toJSON(baseDidDocument).toString();
    // Coding base did with sha256
    byte[] firstHashBaseDidDocument = SHA256Utils.getSHA256(baseDidDocumentStr);
    // On this basis, ripemd160 coding is carried out
    byte[] secondHashBaseDidDocument = RipeMDUtils.encodeRipeMD160(firstHashBaseDidDocument);
    // Finally, base28 coding is carried out
    String afterEncodeBaseDidDocument = Base58Utils.encode(secondHashBaseDidDocument);
    return afterEncodeBaseDidDocument;
  }

  /**
   * Generate did
   *
   * @param didIdentifier
   * @author shaopengfei
   */
  public static String generateDidByDidIdentifier(String didIdentifier) throws Exception {
    StringBuffer didFormatSplicing = new StringBuffer();
    if (StringUtils.isNotBlank(didIdentifier)) {
      didFormatSplicing.append(CurrencyCode.DID_PREFIX);
      didFormatSplicing.append(CurrencyCode.DID_SEPARATOR);
      didFormatSplicing.append(CurrencyCode.DID_PROJECT_NAME);
      didFormatSplicing.append(CurrencyCode.DID_SEPARATOR);
      didFormatSplicing.append(didIdentifier);
    }
    return didFormatSplicing.toString();
  }

  /**
   * Generate did document
   *
   * @param primaryKeyPair
   * @param alternateKeyPair
   * @param did
   * @author shaopengfei
   */
  public static DidDocument generateDidDocument(
      KeyPair primaryKeyPair, KeyPair alternateKeyPair, String did) throws Exception {
    DidDocument didDocument = new DidDocument();
    didDocument.setDid(did);
    didDocument.setVersion(CurrencyCode.DEFAULT_VERSION);
    String now = DateUtil.now();
    didDocument.setCreated(now);
    didDocument.setUpdated(now);
    PublicKey primaryPublicKey = new PublicKey();
    primaryPublicKey.setType(ECDSAUtils.type);
    primaryPublicKey.setPublicKey(primaryKeyPair.getPublicKey());
    didDocument.setAuthentication(primaryPublicKey);
    PublicKey alternatePublicKey = new PublicKey();
    alternatePublicKey.setType(ECDSAUtils.type);
    alternatePublicKey.setPublicKey(alternateKeyPair.getPublicKey());
    didDocument.setRecovery(alternatePublicKey);
    return didDocument;
  }

  /**
   * Renew the public key and signature of the did document
   *
   * @param didDocument
   * @param publicKey
   * @param keyPair
   * @author shaopengfei
   */
  public static DidDocument renewDidDocument(
      DidDocument didDocument, String publicKey, KeyPair keyPair) {
    PublicKey primaryPublicKey = new PublicKey();
    primaryPublicKey.setType(ECDSAUtils.type);
    primaryPublicKey.setPublicKey(keyPair.getPublicKey());
    didDocument.setAuthentication(primaryPublicKey);
    PublicKey alternatePublicKey = new PublicKey();
    alternatePublicKey.setType(ECDSAUtils.type);
    alternatePublicKey.setPublicKey(publicKey);
    didDocument.setRecovery(alternatePublicKey);
    didDocument.setUpdated(DateUtil.now());
    didDocument.setProof(null);
    return didDocument;
  }

  /**
   * Verification of the legitimacy of the did format
   *
   * @param did
   * @author shaopengfei
   */
  public static boolean isDidValid(String did) {
    return StringUtils.isNotEmpty(did)
        && StringUtils.startsWith(did, CurrencyCode.DID_FORMAT_PREFIX)
        && isDidMemberSizeValid(did)
        && isValidDidIdentifier(convertDidIdentifier(did));
  }

  /**
   * Verification of the number of the did members
   *
   * @param did
   * @author shaopengfei
   */
  private static boolean isDidMemberSizeValid(String did) {
    String[] didFields = StringUtils.splitByWholeSeparator(did, CurrencyCode.DID_SEPARATOR);
    if (didFields.length == 3) {
      return true;
    }
    return false;
  }

  /**
   * Format verification of did identifier
   *
   * @param didIdentifier
   * @author shaopengfei
   */
  private static boolean isValidDidIdentifier(String didIdentifier) {
    if (StringUtils.isEmpty(didIdentifier)
        || !Pattern.compile(CurrencyCode.DID_IDENTIFIER_PATTERN).matcher(didIdentifier).matches()) {
      return false;
    }
    return true;
  }

  /**
   * Get did identifier
   *
   * @param did
   * @author shaopengfei
   */
  public static String convertDidIdentifier(String did) {
    if (StringUtils.isEmpty(did) || !StringUtils.contains(did, CurrencyCode.DID_FORMAT_PREFIX)) {
      return StringUtils.EMPTY;
    }
    String[] didFields = StringUtils.splitByWholeSeparator(did, CurrencyCode.DID_SEPARATOR);
    return didFields[didFields.length - 1];
  }

  /**
   * Verify the validity of did document
   *
   * @param didDocument
   * @author shaopengfei
   */
  public static ResultCode verifyDidDocument(DidDocument didDocument) {
    if (Objects.isNull(didDocument)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "didDocument ");
    }
    if (StringUtils.isBlank(didDocument.getDid())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "did ");
    }
    if (StringUtils.isBlank(didDocument.getVersion())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "version ");
    }
    if (StringUtils.isBlank(didDocument.getCreated())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "created ");
    }
    if (StringUtils.isBlank(didDocument.getUpdated())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "updated ");
    }
    if (Objects.isNull(didDocument.getAuthentication())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "authentication ");
    }
    if (Objects.isNull(didDocument.getRecovery())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "recovery ");
    }
    if (Objects.isNull(didDocument.getProof())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "proof ");
    }
    if (StringUtils.isBlank(didDocument.getProof().getType())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "type ");
    }
    if (StringUtils.isBlank(didDocument.getProof().getCreator())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "creator ");
    }
    if (StringUtils.isBlank(didDocument.getProof().getSignatureValue())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "signatureValue ");
    }
    if (!ECDSAUtils.type.equals(didDocument.getProof().getType())) {
      return new ResultCode(ErrorCode.ENCRYPTION_TYPE_ILLEGAL, "type ");
    }
    if (!didDocument.getDid().equals(didDocument.getProof().getCreator())) {
      return new ResultCode(ErrorCode.SIGNER_DID_NOT_MATCH);
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Compare whether the public key of the parameter and the main public key of the did document on
   * the chain are the same
   *
   * @param didDocument
   * @param publicKey
   * @author shaopengfei
   */
  public static AtomicBoolean comparePrimaryPublicKey(DidDocument didDocument, String publicKey) {
    AtomicBoolean publicKeyValid = new AtomicBoolean(false);
    if (!(Objects.isNull(didDocument) || StringUtils.isBlank(publicKey))) {
      if (!Objects.isNull(didDocument.getAuthentication())) {
        if (StringUtils.equals(didDocument.getAuthentication().getPublicKey(), publicKey)) {
          publicKeyValid.set(true);
        }
      }
    }
    return publicKeyValid;
  }

  /**
   * Compare whether the public key of the parameter and the standby public key of the did document
   * on the chain are the same
   *
   * @param didDocument
   * @param publicKey
   * @author shaopengfei
   */
  public static AtomicBoolean compareReservePublicKey(DidDocument didDocument, String publicKey) {
    AtomicBoolean publicKeyValid = new AtomicBoolean(false);
    if (!(Objects.isNull(didDocument) || StringUtils.isBlank(publicKey))) {
      if (!Objects.isNull(didDocument.getRecovery())) {
        if (StringUtils.equals(didDocument.getRecovery().getPublicKey(), publicKey)) {
          publicKeyValid.set(true);
        }
      }
    }
    return publicKeyValid;
  }

  public static ResultCode validateUpdateAuthMatch(
      DidDocument oldDocument, DidDocument newDocument) {
    if (!oldDocument.getDid().equals(newDocument.getProof().getCreator())) {
      return new ResultCode(ErrorCode.SIGNER_DID_NOT_MATCH);
    }

    if (!oldDocument.getVersion().equals(newDocument.getVersion())) {
      return new ResultCode(ErrorCode.DOC_VRESION_NOT_MATHCH);
    }

    if (!oldDocument.getCreated().equals(newDocument.getCreated())) {
      return new ResultCode(ErrorCode.DOC_CREATED_NOT_MATHCH);
    }

    if (!oldDocument
        .getRecovery()
        .getPublicKey()
        .equals(newDocument.getRecovery().getPublicKey())) {
      return new ResultCode(ErrorCode.DOC_RECOVERY_KEY_NOT_MATHCH);
    }

    if (!oldDocument.getRecovery().getType().equals(newDocument.getRecovery().getType())) {
      return new ResultCode(ErrorCode.DOC_RECOVERY_KEY_NOT_MATHCH);
    }

    return new ResultCode(ErrorCode.SUCCESS);
  }

  public static boolean StartWithDidPrefix(String str) {
    return (str != null && str.startsWith(CurrencyCode.DID_FORMAT_PREFIX));
  }
}
