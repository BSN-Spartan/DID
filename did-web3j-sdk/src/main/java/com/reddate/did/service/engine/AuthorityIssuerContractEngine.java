// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service.engine;

import com.alibaba.fastjson.JSON;
import com.reddate.did.config.ContractAddress;
import com.reddate.did.config.DidConfig;
import com.reddate.did.config.cache.ConfigCache;
import com.reddate.did.constant.CurrencyCode;
import com.reddate.did.protocol.common.AuthorityIssuer;
import com.reddate.did.service.contract.IssuerContract;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.*;

public class AuthorityIssuerContractEngine extends BaseEngine {

  private static final Logger logger = LoggerFactory.getLogger(AuthorityIssuerContractEngine.class);

  private static IssuerContract issuerContract = loadIssuerContract();


  private static IssuerContract loadIssuerContract() {

    DidConfig client = ConfigCache.getConfig();
    ContractAddress contractAddress = ConfigCache.getAddress();
    Web3j web3j = Web3j.build(new HttpService(client.getNodeUrl()));

    Credentials credentials = getCredentials(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY);
    TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, client.getChainId(),
            client.getReceiptRotationCount(), client.getReceiptWaitTime());
    StaticGasProvider staticGasProvider = new StaticGasProvider(client.getGasPrice(), client.getGasLimit());

    IssuerContract issuerContract = IssuerContract.load(contractAddress.getIssuerAddress(), web3j, transactionManager, staticGasProvider);
    web3j.shutdown();
    return issuerContract;
  }

  public static boolean createIssuer(String did, String issuerInfo) throws Exception {

    if (issuerContract != null) {
      TransactionReceipt result = issuerContract.createIssuer(did, issuerInfo).send();
      List<IssuerContract.CreateIssuerRetLogEventResponse> createDidRetLogEvents =
          issuerContract.getCreateIssuerRetLogEvents(result);

      return (null != createDidRetLogEvents && createDidRetLogEvents.size() > 0 &&
              CHAIN_SUCCESS_CODE.equals(createDidRetLogEvents.get(0).msgcode));
    }
    return false;
  }

  public static Map<String, Object> queryAuthIssuerList(String did) throws Exception {

    Map<String, Object> resultMap = new HashMap<>();
    Tuple3<String, String, String> result = issuerContract.getIssuerByDid(did).send();
    if (!Objects.isNull(result)) {
      String code = result.component1();
      resultMap.put("code", code);

      if (StringUtils.isNotBlank(result.component1()) && CHAIN_SUCCESS_CODE.equals(result.component1())) {
        String data = result.component3();
        resultMap.put("data", JSON.parseObject(data, AuthorityIssuer.class));
      }
      return resultMap;
    }
    return null;
  }

  public static Map<String, Object> queryAuthIssuerList(Integer page, Integer rows) throws Exception {

    Map<String, Object> resultMap = new HashMap<>();
    List<AuthorityIssuer> resultList = new ArrayList<>();
    Tuple5<String, String, BigInteger, BigInteger, List<String>> result = issuerContract.getAllIssuerInfo(
            BigInteger.valueOf(page), BigInteger.valueOf(rows)).send();

    if (!Objects.isNull(result)) {
      resultMap.put("code", result.component1());
      resultMap.put("totalNum", result.component3().intValue());
      resultMap.put("totalPage", result.component4().intValue());
      if (StringUtils.isNotBlank(result.component1()) && CHAIN_SUCCESS_CODE.equals(result.component1())) {

        List<String> data = result.component5();
        for (String datum : data) {
          resultList.add(JSON.parseObject(datum, AuthorityIssuer.class));
        }
        resultMap.put("data", resultList);
      }
      return resultMap;
    }
    return null;
  }

  public static boolean isIssuer(String did) throws Exception {

    return issuerContract.isIssuerExist(did).send();
  }
}
