// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import com.reddate.did.constant.CurrencyCode;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.constant.ResultCode;
import com.reddate.did.protocol.common.Credential;
import com.reddate.did.protocol.common.JsonSchema;
import com.reddate.did.protocol.common.Proof;
import com.reddate.did.protocol.request.CreateCredential;
import com.reddate.did.protocol.request.RevokeCredential;
import com.reddate.did.protocol.request.RovekeInfo;
import com.reddate.did.protocol.response.CptInfo;
import com.reddate.did.protocol.response.CredentialWrapper;
import com.reddate.did.util.commons.ECDSAUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public final class CredentialUtils {

  /**
   * Verify the validity of createCredential object
   *
   * @param args
   * @author shaopengfei
   */
  public static ResultCode isCreateCredentialArgsValid(CreateCredential args) throws Exception {
    if (Objects.isNull(args)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "args ");
    }
    if (Objects.isNull(args.getCptId())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "cptId ");
    }
    if (StringUtils.isBlank(args.getIssuerDid())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "issuerDid ");
    }
    if (!DidUtils.isDidValid(args.getIssuerDid())) {
      return new ResultCode(ErrorCode.INVALID_DID, "");
    }
    if (StringUtils.isBlank(args.getUserDid())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "userDid ");
    }
    if (!DidUtils.isDidValid(args.getUserDid())) {
      return new ResultCode(ErrorCode.INVALID_DID, "");
    }
    if (StringUtils.isBlank(args.getType())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "type ");
    }
    if (!CurrencyCode.REGISTRATION_VOUCHER_TEMPLATE_TYPE.equals(args.getType())) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of type is invalid");
    }
    if (StringUtils.isBlank(args.getExpirationDate())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "expirationDate ");
    }

    if (!isValidDataFormat(args.getExpirationDate())) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of expirationDate is invalid");
    }
    LocalDate expirationDate = null;
    try {
      expirationDate = LocalDate.parse(args.getExpirationDate());
    } catch (Exception e) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of expirationDate is invalid");
    }
    if (!LocalDate.now().isBefore(expirationDate)) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of expirationDate is invalid");
    }

    if (Objects.isNull(args.getClaim()) || args.getClaim().isEmpty()) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "claim ");
    }
    ResultCode claimValidErrorCode = verifyClaim(args.getClaim());
    if (!ErrorCode.isSuccess(claimValidErrorCode.getCode())) {
      return claimValidErrorCode;
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Verify the validity of credential object
   *
   * @param args
   * @author shaopengfei
   */
  public static ResultCode isCredentialArgsValid(Credential args) {
    if (Objects.isNull(args)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "Credential ");
    }
    if (StringUtils.isBlank(args.getContext())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "context ");
    }
    if (StringUtils.isBlank(args.getId())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "id ");
    }
    if (Objects.isNull(args.getCptId())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "cptId ");
    }
    if (StringUtils.isBlank(args.getIssuerDid())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "issuerDid ");
    }
    if (!DidUtils.isDidValid(args.getIssuerDid())) {
      return new ResultCode(ErrorCode.INVALID_DID, "issuerDid is ");
    }
    if (StringUtils.isBlank(args.getUserDid())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "userDid ");
    }
    if (!DidUtils.isDidValid(args.getUserDid())) {
      return new ResultCode(ErrorCode.INVALID_DID, "");
    }
    if (StringUtils.isBlank(args.getType())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "type ");
    }
    if (StringUtils.isBlank(args.getCreated())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "created ");
    }
    if (!CurrencyCode.REGISTRATION_VOUCHER_TEMPLATE_TYPE.equals(args.getType())) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of type is invalid");
    }
    if (StringUtils.isBlank(args.getExpirationDate())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "expirationDate ");
    }
    if (!isValidDataFormat(args.getExpirationDate())) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of expirationDate is invalid");
    }
    if (Objects.isNull(args.getClaim()) || args.getClaim().isEmpty()) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "claim ");
    }
    ResultCode claimValidErrorCode = verifyClaim(args.getClaim());
    if (!ErrorCode.isSuccess(claimValidErrorCode.getCode())) {
      return claimValidErrorCode;
    }
    ResultCode proofValidErrorCode = verifyProof(args.getProof(), args.getIssuerDid());
    if (!ErrorCode.isSuccess(proofValidErrorCode.getCode())) {
      return proofValidErrorCode;
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Verify the validity of revocation certificate
   *
   * @param cred
   * @author shaopengfei
   */
  public static ResultCode isRevokeCredentialValid(RevokeCredential cred) throws Exception {
    if (Objects.isNull(cred)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "RevokeCredential ");
    }
    if (StringUtils.isBlank(cred.getCredId())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "credId ");
    }
    if (Objects.isNull(cred.getCptId())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "cptId ");
    }
    if (StringUtils.isBlank(cred.getDid())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "did ");
    }
    if (!DidUtils.isDidValid(cred.getDid())) {
      return new ResultCode(ErrorCode.INVALID_DID);
    }
    
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Verify the validity of claim
   *
   * @param claim
   * @author shaopengfei
   */
  public static ResultCode verifyClaim(Map<String, Object> claim) {
    AtomicBoolean forEachResult = new AtomicBoolean(true);
    claim.forEach(
        (key, value) -> {
          if (Objects.isNull(value)) {
            forEachResult.set(false);
          }
        });
    if (!forEachResult.get()) {
      return new ResultCode(ErrorCode.DATA_IS_INCOMPLETE_OR_CONTAINS_EMPTY_ATTRIBUTES, "claim ");
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Verify the validity of proof
   *
   * @param proof
   * @param did
   * @author shaopengfei
   */
  public static ResultCode verifyProof(Map<String, Object> proof, String did) {
    if (Objects.isNull(proof) || proof.isEmpty() || Objects.isNull(proof.get("signatureValue"))) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "proof ");
    }
    if (Objects.isNull(proof.get("creator"))) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "creator ");
    }
    if (!proof.get("creator").equals(did)) {
      return new ResultCode(ErrorCode.SIGNER_DID_NOT_MATCH);
    }
    if (Objects.isNull(proof.get("type"))) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "type ");
    }
    if (!ECDSAUtils.type.equals(proof.get("type"))) {
      return new ResultCode(ErrorCode.ENCRYPTION_TYPE_ILLEGAL);
    }
    if (Objects.isNull(proof.get("signatureValue"))) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "signatureValue ");
    }
    if (proof.get("signatureValue").toString().length() != 87
        && proof.get("signatureValue").toString().length() != 88) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of signatureValue is invalid");
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Verify whether the credential is expired
   *
   * @param paramDate
   * @author shaopengfei
   */
  public static boolean verifyExpiration(String paramDate) {
    Date expirationDate = DateUtil.parse(paramDate);
    Date nowDate = DateUtil.parse(DateUtil.now());
    long difference = DateUtil.between(nowDate, expirationDate, DateUnit.SECOND, false);
    if (difference <= 0) {
      return false;
    }
    return true;
  }

  /**
   * Compare whether the key in the voucher and CPT template is consistent
   *
   * @param args
   * @param cptInfo
   * @author shaopengfei
   */
  public static boolean credentialComparisonCptTemplateClaim(
      CreateCredential args, CptInfo cptInfo) {
    AtomicBoolean result = new AtomicBoolean(true);
    Map<String, JsonSchema> jsonSchemaMap = cptInfo.getCptJsonSchema();
    Map<String, Object> argsClaim = args.getClaim();
    Map<String, Object> allNeedClains = new LinkedHashMap<String, Object>();

    jsonSchemaMap.forEach(
        (key, value) -> {
          Object clainItem = argsClaim.get(key);
          if (value.getRequired()) {
            if (clainItem == null || clainItem.toString().isEmpty()) {
              result.set(false);
            }
          }
          if (clainItem != null) {
            allNeedClains.put(key, clainItem);
          }
        });

    if (result.get()) {
      args.setClaim(allNeedClains);
    }

    return result.get();
  }

  /**
   * Gets the default credential context string
   *
   * @author shaopengfei
   */
  public static String getDefaultCredentialContext() {
    return CurrencyCode.DEFAULT_CREDENTIAL_CONTEXT;
  }

  /** Package credential info */
  public static CredentialWrapper packageCredentialInfo(CreateCredential createCredential)
      throws Exception {
    CredentialWrapper credentialWrapper = new CredentialWrapper();
    credentialWrapper.setContext(getDefaultCredentialContext());
    credentialWrapper.setId(new BCryptPasswordEncoder().encode(createCredential.getCptId().toString()));
    credentialWrapper.setType(createCredential.getType());
    credentialWrapper.setCptId(createCredential.getCptId());
    credentialWrapper.setIssuerDid(createCredential.getIssuerDid());
    credentialWrapper.setUserDid(createCredential.getUserDid());
    credentialWrapper.setExpirationDate(createCredential.getExpirationDate());
    credentialWrapper.setCreated(DateUtil.now());
    credentialWrapper.setClaim(createCredential.getClaim());
    credentialWrapper.setLongDesc(createCredential.getLongDesc());
    credentialWrapper.setShortDesc(createCredential.getShortDesc());
    return credentialWrapper;
  }

  public static void main(String[] args) {

    String cptId = "892205090947362333";
    String credId1 = new BCryptPasswordEncoder().encode(cptId);
    System.out.println("--------- credId1 = " + credId1);
    System.out.println("--------- credId1 verify = " + new BCryptPasswordEncoder().matches(cptId, credId1));

    String credId2 = new BCryptPasswordEncoder().encode(cptId);
    System.out.println("--------- credId2 = " + credId2);
    System.out.println("--------- credId2 verify = " + new BCryptPasswordEncoder().matches(cptId, credId2));

    String credId3 = new BCryptPasswordEncoder().encode(cptId);
    System.out.println("--------- credId3 = " + credId3);
    System.out.println("--------- credId3 verify = " + new BCryptPasswordEncoder().matches(cptId, credId3));

    System.out.println("--------- error verify = " + new BCryptPasswordEncoder().matches(cptId, "11111111"));
  }

  /** Package revocation credentials info */
  public static RovekeInfo packageRovekeInfo(RevokeCredential cred) throws Exception {
    RovekeInfo rovekeInfo = new RovekeInfo();
    rovekeInfo.setCredId(cred.getCredId());
    // rovekeInfo.setRovekeDate(DateUtil.now());
    rovekeInfo.setRovekeDate(cred.getRovekeDate());
    Proof proof = new Proof();
    proof.setType(ECDSAUtils.type);
    proof.setCreator(cred.getDid());
    proof.setSignatureValue(cred.getProof().getSignatureValue());
    rovekeInfo.setProof(proof);
    return rovekeInfo;
  }

  /**
   * Verify the validity of time string format
   *
   * @param dateValue
   * @author shaopengfei
   */
  private static boolean isValidDataFormat(String dateValue) {
    String regex = "^([0-9]{4})-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$";
    Pattern pattern = Pattern.compile(regex);
    return pattern.matcher((CharSequence) dateValue).matches();
  }
}
