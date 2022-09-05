// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service.engine;

import com.alibaba.fastjson.JSON;
import com.reddate.did.config.ContractAddress;
import com.reddate.did.config.DidConfig;
import com.reddate.did.config.cache.ConfigCache;
import com.reddate.did.constant.CurrencyCode;
import com.reddate.did.protocol.response.DidDocument;
import com.reddate.did.service.contract.DidContract;
import com.reddate.did.constant.ContractCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DidContractEngine extends BaseEngine {

  private static final Logger logger = LoggerFactory.getLogger(DidContractEngine.class);

  private static DidContract didContract = loadDidContract();

  private static ContractAddress contractAddress = ConfigCache.getAddress();


  private static DidContract loadDidContract() {

    DidConfig client = ConfigCache.getConfig();
    ContractAddress contractAddress = ConfigCache.getAddress();
    Web3j web3j = Web3j.build(new HttpService(client.getNodeUrl()));

    Credentials credentials = getCredentials(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY);
    TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, client.getChainId(),
            client.getReceiptRotationCount(), client.getReceiptWaitTime());
    StaticGasProvider staticGasProvider = new StaticGasProvider(client.getGasPrice(), client.getGasLimit());

    DidContract didcontract = DidContract.load(contractAddress.getDidContractAddress(), web3j, transactionManager, staticGasProvider);
    web3j.shutdown();
    return didcontract;
  }

  public static ContractCode createDid(String did, String didDocument) throws Exception {

    Callable<ContractCode> call =
        () -> {
          TransactionReceipt result = didContract.createDid(did, didDocument).send();
          List<DidContract.CreateDidRetLogEventResponse> createDidRetLogEvents =
              didContract.getCreateDidRetLogEvents(result);
          return ContractCode.getByCode(createDidRetLogEvents.get(0).msgcode);
        };

    Future<ContractCode> future = null;
    try {
      future = executorService.submit(call);
      return future.get(contractAddress.getTimeOut(), TimeUnit.MILLISECONDS);

    } catch (TimeoutException e) {
      future.cancel(true);
      throw e;
    }
  }

  public static Map<String, Object> getDidDocument(String did) throws Exception {

    Map<String, Object> resultMap = new HashMap<>();
    if (didContract != null) {
      Callable<Map<String, Object>> call =
          () -> {
            Tuple3<String, String, String> result = didContract.getDocument(did).send();
            if (!Objects.isNull(result)) {
              String code = result.component1();
              resultMap.put("code", code);
              if (StringUtils.isNotBlank(result.component1())
                  && CHAIN_SUCCESS_CODE.equals(result.component1())) {
                String data = result.component3();
                resultMap.put("data", JSON.parseObject(data, DidDocument.class));
              }
            }
            return resultMap;
          };
      Future<Map<String, Object>> future = null;
      try {
        future = executorService.submit(call);
        return future.get(contractAddress.getTimeOut(), TimeUnit.MILLISECONDS);
      } catch (TimeoutException e) {
        future.cancel(true);
        throw e;
      }
    }
    return resultMap;
  }

  public static ContractCode updateDidAuth(String did, String didDocument) throws Exception {

    if (didContract != null) {
      TransactionReceipt result = didContract.updateDidAuth(did, didDocument).send();
      List<DidContract.UpdateDidAuthRetLogEventResponse> updateDidAuthRetLogEvents = didContract.getUpdateDidAuthRetLogEvents(result);
      return ContractCode.getByCode(updateDidAuthRetLogEvents.get(0).msgcode);
    }
    return ContractCode.CONTRACT_ADDR_INVALID;
  }

  public static BigInteger getBlockNumber() throws IOException {
    return null;
  }

  public static Integer getGroupId() throws IOException {
    return null;
  }
}
