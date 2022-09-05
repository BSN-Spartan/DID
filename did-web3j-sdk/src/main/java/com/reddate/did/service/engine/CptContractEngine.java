// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service.engine;

import com.alibaba.fastjson.JSON;
import com.reddate.did.config.ContractAddress;
import com.reddate.did.config.DidConfig;
import com.reddate.did.config.cache.ConfigCache;
import com.reddate.did.constant.CurrencyCode;
import com.reddate.did.protocol.common.BaseCredential;
import com.reddate.did.protocol.request.RovekeInfo;
import com.reddate.did.protocol.response.CptInfo;
import com.reddate.did.constant.ContractCode;
import com.reddate.did.service.contract.CptContract;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.*;

public class CptContractEngine extends BaseEngine {

  private static final Logger logger = LoggerFactory.getLogger(DidContractEngine.class);

  private static CptContract cptContract = loadCptContract();


  private static CptContract loadCptContract() {

    DidConfig client = ConfigCache.getConfig();
    ContractAddress contractAddress = ConfigCache.getAddress();
    Web3j web3j = Web3j.build(new HttpService(client.getNodeUrl()));

    Credentials credentials = getCredentials(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY);
    TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, client.getChainId(),
            client.getReceiptRotationCount(), client.getReceiptWaitTime());
    StaticGasProvider staticGasProvider = new StaticGasProvider(client.getGasPrice(), client.getGasLimit());

    CptContract cptContract = CptContract.load(contractAddress.getCptAddress(), web3j, transactionManager, staticGasProvider);
    web3j.shutdown();
    return cptContract;
  }

  public static ContractCode registerCpt(String did, String cptId, String cpt) throws Exception {

    if (cptContract != null) {
      TransactionReceipt result = cptContract.registerCpt(did, cptId, cpt).send();
      List<CptContract.RegisterCptRetLogEventResponse> registerCptRetLogEvents = cptContract.getRegisterCptRetLogEvents(result);

      if (registerCptRetLogEvents != null && registerCptRetLogEvents.size() > 0 &&
              CHAIN_SUCCESS_CODE.equals(registerCptRetLogEvents.get(0).msgcode)) {
        return ContractCode.SUCCESS;
      } else {
        return ContractCode.getByCode(registerCptRetLogEvents.get(0).msgcode);
      }
    } else {
      return ContractCode.CONTRACT_ADDR_INVALID;
    }
  }

  public static Map<String, Object> getCptByCptId(String cptId) throws Exception {

    Map<String, Object> resultMap = new HashMap<>();
    if (cptContract != null) {
      Tuple3<String, String, String> result = cptContract.getCptByCptId(cptId).send();
      if (!Objects.isNull(result)) {
        String code = result.component1();

        resultMap.put("code", code);
        if (StringUtils.isNotBlank(result.component1()) && CHAIN_SUCCESS_CODE.equals(result.component1())) {
          String data = result.component3();
          resultMap.put("data", JSON.parseObject(data, CptInfo.class));
        }
      }
    }
    return resultMap;
  }

  public static Map<String, Object> getCptListByDid(String did, Integer page, Integer rows) throws Exception {

    Map<String, Object> resultMap = new HashMap<>();
    if (cptContract != null) {
      Tuple4<String, BigInteger, BigInteger, List<String>> result = cptContract.getCptListByDid(did, BigInteger.valueOf(page),
              BigInteger.valueOf(rows)).send();
      if (!Objects.isNull(result)) {

        resultMap.put("code", result.component1());
        resultMap.put("totalNum", result.component2().intValue());
        resultMap.put("totalPage", result.component3().intValue());
        if (StringUtils.isNotBlank(result.component1()) && CHAIN_SUCCESS_CODE.equals(result.component1())) {
          List<CptInfo> cptInfos = new ArrayList<>();
          List<String> data = result.component4();

          if (!data.isEmpty()) {
            data.forEach(
                item -> {
                  cptInfos.add(JSON.parseObject(item, CptInfo.class));
                });
          }
          resultMap.put("data", cptInfos);
        }
      }
    }
    return resultMap;
  }

  public static Boolean isCptExist(String cptId) throws Exception {

    if (cptContract != null) {
      return cptContract.isCptExist(cptId).send();
    }
    return false;
  }

  public static ContractCode updateCpt(String did, String cptId, String cpt) throws Exception {

    if (cptContract != null) {
      TransactionReceipt result = cptContract.updateCpt(did, cptId, cpt).send();
      List<CptContract.UpdateCptRetLogEventResponse> updateCptRetLogEvents = cptContract.getUpdateCptRetLogEvents(result);
      if (updateCptRetLogEvents != null && updateCptRetLogEvents.size() > 0 &&
              CHAIN_SUCCESS_CODE.equals(updateCptRetLogEvents.get(0).msgcode)) {

        return ContractCode.SUCCESS;
      } else {
        return ContractCode.getByCode(updateCptRetLogEvents.get(0).msgcode);
      }
    } else {
      return ContractCode.CONTRACT_ADDR_INVALID;
    }
  }

  public static boolean revokeCredential(String did, String credId, String info) throws Exception {
    if (cptContract != null) {
      TransactionReceipt result = cptContract.revokeCredential(did, credId, info).send();
      List<CptContract.RevokeCredentialRetLogEventResponse> revokeCredentialRetLogEvents =
          cptContract.getRevokeCredentialRetLogEvents(result);
      if (null != revokeCredentialRetLogEvents
          && revokeCredentialRetLogEvents.size() > 0
          && CHAIN_SUCCESS_CODE.equals(revokeCredentialRetLogEvents.get(0).msgcode)) {
        return true;
      }
    }
    return false;
  }

  public static Map<String, Object> getRevokedCred(String did, String credId) throws Exception {
    Map<String, Object> resultMap = new HashMap<>();
    if (cptContract != null) {
      Tuple3<String, String, String> result = cptContract.getRevokedCred(did, credId).send();
      if (!Objects.isNull(result)) {
        String code = result.component1();
        resultMap.put("code", code);
        if (StringUtils.isNotBlank(result.component1())) {
          String data = result.component3();
          resultMap.put("data", JSON.parseObject(data, RovekeInfo.class));
        }
      }
    }
    return resultMap;
  }

  public static Map<String, Object> getRevokedCredList(String did, Integer page, Integer rows)
      throws Exception {
    Map<String, Object> resultMap = new HashMap<>();
    if (cptContract != null) {
      Tuple5<String, String, BigInteger, BigInteger, List<String>> result =
          cptContract.getRevokedCredList(did, BigInteger.valueOf(page), BigInteger.valueOf(rows)).send();
      if (!Objects.isNull(result)) {
        resultMap.put("code", result.component1());
        resultMap.put("totalNum", result.component3().intValue());
        resultMap.put("totalPage", result.component4().intValue());
        if (StringUtils.isNotBlank(result.component1())
            && CHAIN_SUCCESS_CODE.equals(result.component1())) {
          List<BaseCredential> baseCredentials = new ArrayList<>();
          List<String> data = result.component5();
          if (null != data && !data.isEmpty()) {
            data.forEach(
                item -> {
                  BaseCredential baseCredential = new BaseCredential();
                  baseCredential.setId(item);
                  baseCredentials.add(baseCredential);
                });
          }
          resultMap.put("data", baseCredentials);
        }
      }
    }
    return resultMap;
  }
}
