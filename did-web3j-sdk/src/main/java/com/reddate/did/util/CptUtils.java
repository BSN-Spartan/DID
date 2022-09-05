// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.util;

import cn.hutool.core.date.DateUtil;
import com.reddate.did.constant.CurrencyCode;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.constant.ResultCode;
import com.reddate.did.protocol.common.JsonSchema;
import com.reddate.did.protocol.common.RegisterAuthorityIssuer;
import com.reddate.did.protocol.request.RegisterCpt;
import com.reddate.did.protocol.response.CptInfo;
import com.reddate.did.util.commons.ECDSAUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class CptUtils {

  /**
   * verification of registered issuer entity
   *
   * @param registerAuthorityIssuer
   * @return
   */
  public static ResultCode verifyRegisterAuthorityIssuer(RegisterAuthorityIssuer registerAuthorityIssuer) throws Exception {

    if (Objects.isNull(registerAuthorityIssuer)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "registerAuthorityIssuer ");
    }
    if (StringUtils.isBlank(registerAuthorityIssuer.getDid())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "did ");
    }
    if (StringUtils.isBlank(registerAuthorityIssuer.getName())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "name ");
    }
    if (Objects.isNull(registerAuthorityIssuer.getPublicKeyInfo())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "public keyInfo ");
    }
    if (!DidUtils.isDidValid(registerAuthorityIssuer.getDid())) {
      return new ResultCode(ErrorCode.INVALID_DID);
    }
    if (!isValidAuthorityIssuerBytes32Param(registerAuthorityIssuer.getName())) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of name is invalid");
    }

    boolean verfyResult =
            ECDSAUtils.verify(
            registerAuthorityIssuer.getPublicKeyInfo().getPublicKey(),
            registerAuthorityIssuer.getPublicKeyInfo().getPublicKey(),
            registerAuthorityIssuer.getPublicKeyInfo().getPublicKeySign());
    if (!verfyResult) {
      return new ResultCode(ErrorCode.VERIFY_PUBLIC_KEY_SIGN_ERR);
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * verification of registered cpt entity
   *
   * @param registerCpt
   * @author shaopengfei
   */
  public static ResultCode verifyRegisterCpt(RegisterCpt registerCpt) throws Exception {

    ResultCode verifyRegisterCptEmptyErrorCode = verifyRegisterCptEmpty(registerCpt);
    if (!ErrorCode.isSuccess(verifyRegisterCptEmptyErrorCode.getCode())) {
      return verifyRegisterCptEmptyErrorCode;
    }

    ResultCode verifyCptJsonSchemaErrorCode = verifyCptJsonSchema(registerCpt.getCptJsonSchema());
    if (!ErrorCode.isSuccess(verifyCptJsonSchemaErrorCode.getCode())) {
      return verifyCptJsonSchemaErrorCode;
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Non empty verification of registered cpt entity
   *
   * @param registerCpt
   * @author shaopengfei
   */
  public static ResultCode verifyRegisterCptEmpty(RegisterCpt registerCpt) throws Exception {

    if (Objects.isNull(registerCpt)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "registerCpt ");
    }
    if (StringUtils.isBlank(registerCpt.getDid())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "did ");
    }
    if (!DidUtils.isDidValid(registerCpt.getDid())) {
      return new ResultCode(ErrorCode.INVALID_DID);
    }
    if (Objects.isNull(registerCpt.getCptJsonSchema()) || registerCpt.getCptJsonSchema().isEmpty()) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "cptJsonSchema ");
    }
    if (StringUtils.isBlank(registerCpt.getTitle())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "title ");
    }
    if (StringUtils.isBlank(registerCpt.getDescription())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "description ");
    }
    if (StringUtils.isBlank(registerCpt.getType())) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "type ");
    }
    if (!CurrencyCode.REGISTRATION_VOUCHER_TEMPLATE_TYPE.equals(registerCpt.getType())) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of type is invalid");
    }
    
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Validity verification of cptJsonSchema entity
   *
   * @param cptJsonSchema
   * @author shaopengfei
   */
  public static ResultCode verifyCptJsonSchema(Map<String, JsonSchema> cptJsonSchema) {
    AtomicBoolean forEachResult = new AtomicBoolean(true);
    cptJsonSchema.forEach(
        (key, value) -> {
          if (StringUtils.isBlank(value.getType())
              || StringUtils.isBlank(value.getDescription())
              || Objects.isNull(value.getRequired())) {
            forEachResult.set(false);
          }
        });
    if (!forEachResult.get()) {
      return new ResultCode(
          ErrorCode.DATA_IS_INCOMPLETE_OR_CONTAINS_EMPTY_ATTRIBUTES, "cptJsonSchema ");
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }

  /**
   * Assembly needs CPT information on the chain
   *
   * @param registerCpt
   * @author shaopengfei
   */
  public static CptInfo assemblyCptInfo(RegisterCpt registerCpt) {

    CptInfo cptInfo = new CptInfo();
    cptInfo.setPublisherDid(registerCpt.getDid());
    cptInfo.setTitle(registerCpt.getTitle());
    cptInfo.setDescription(registerCpt.getDescription());
    cptInfo.setCptJsonSchema(registerCpt.getCptJsonSchema());

    cptInfo.setCreate(registerCpt.getCreate());
    cptInfo.setUpdate(registerCpt.getUpdate());
    cptInfo.setCptId(registerCpt.getCptId());
    cptInfo.setCptVersion(1);
    return cptInfo;
  }

  /**
   * Reset needs CPT information on the chain
   *
   * @param registerCpt
   * @author shaopengfei
   */
  public static CptInfo resetCptInfo(RegisterCpt registerCpt, CptInfo cptInfoChain) {
    CptInfo cptInfo = new CptInfo();
    cptInfo.setPublisherDid(cptInfoChain.getPublisherDid());
    cptInfo.setTitle(registerCpt.getTitle());
    cptInfo.setDescription(registerCpt.getDescription());
    cptInfo.setCptJsonSchema(registerCpt.getCptJsonSchema());
    cptInfo.setCreate(cptInfoChain.getCreate());
    cptInfo.setUpdate(DateUtil.now());
    cptInfo.setCptId(cptInfoChain.getCptId());
    cptInfo.setCptVersion(cptInfoChain.getCptVersion() + 1);
    return cptInfo;
  }

  /**
   * Verify that the name of the issuing party is legal
   *
   * @param name
   * @author shaopengfei
   */
  public static boolean isValidAuthorityIssuerBytes32Param(String name) {
    return !StringUtils.isEmpty(name)
        && name.getBytes(StandardCharsets.UTF_8).length
            < CurrencyCode.MAX_AUTHORITY_ISSUER_NAME_LENGTH
        && !StringUtils.isWhitespace(name);
  }
}
