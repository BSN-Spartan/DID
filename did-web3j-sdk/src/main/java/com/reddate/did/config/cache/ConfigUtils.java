// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.config.cache;

import com.reddate.did.DIDClient;
import com.reddate.did.config.ContractAddress;
import com.reddate.did.config.DidConfig;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.exception.DidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Properties;

public class ConfigUtils {

  private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);


  public static DidConfig client(String configFileName) {

    DidConfig didConfig = new DidConfig();
    Properties properties = new Properties();
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(configFileName);
      logger.info("load config file from folder: {}", configFileName);
    } catch (Exception e1) {
      logger.warn(e1.getMessage());
    }
    if (inputStream == null) {
      inputStream = DIDClient.class.getClassLoader().getResourceAsStream(configFileName);
      logger.info("load config file from class path: {}", configFileName);
    }
    try {
      properties.load(inputStream);
    } catch (IOException e) {
      logger.error(ErrorCode.CONFIG_FILE_NOT_FOUND.getEnMessage(), e);
      throw new DidException(
              ErrorCode.CONFIG_FILE_NOT_FOUND.getCode(),
              ErrorCode.CONFIG_FILE_NOT_FOUND.getEnMessage());
    }
    logger.info("Begin intial DID sdk,load the config Info is: " + properties.toString());

    String didType = properties.getProperty(DidConfigConstant.DID_TYPE);
    String nodeUrl = properties.getProperty(DidConfigConstant.WEB3J_NODE_URL);
    if (nodeUrl == null || nodeUrl.trim().isEmpty()) {
      logger.error(ErrorCode.PARAMETER_IS_EMPTY.getEnMessage());
      throw new DidException(
              ErrorCode.PARAMETER_IS_EMPTY.getCode(), ErrorCode.PARAMETER_IS_EMPTY.getEnMessage());
    }

    BigInteger gasPrice = new BigInteger(properties.getProperty(DidConfigConstant.WEB3J_GAS_PRICE));
    BigInteger gasLimit = new BigInteger(properties.getProperty(DidConfigConstant.WEB3J_GAS_LIMIT));
    Integer receiptRotationCount = Integer.valueOf(properties.getProperty(DidConfigConstant.RECEIPT_ROTATION_COUNT));
    Long receiptWaitTime = Long.valueOf(properties.getProperty(DidConfigConstant.RECEIPT_WAIT_TIME));
    Long chainId = Long.valueOf(properties.getProperty(DidConfigConstant.WEB3J_CHAIN_ID));

    ContractAddress contractAddress = new ContractAddress();
    contractAddress.setDidContractAddress(properties.getProperty(DidConfigConstant.DID_TRANSPARENTPROXYCONTRACT));
    contractAddress.setIssuerAddress(properties.getProperty(DidConfigConstant.ISSUER_TRANSPARENTPROXYCONTRACT));
    contractAddress.setCptAddress(properties.getProperty(DidConfigConstant.CPT_TRANSPARENTPROXYCONTRACT));
    contractAddress.setDidProxyAdminAddress(properties.getProperty(DidConfigConstant.DID_ROXYCON_ADMIN_TRACT));
    contractAddress.setIssuerProxyAdminAddress(properties.getProperty(DidConfigConstant.ISSUER_ROXY_ADMIN_CONTRACT));

    contractAddress.setCptProxyAdminAddress(properties.getProperty(DidConfigConstant.CPT_PROXY_ADMIN_CONTRACT));
    contractAddress.setDidLogicContractAddress(properties.getProperty(DidConfigConstant.DID_LOGIC_CONTRACT));
    contractAddress.setIssuerLogicContractAddress(properties.getProperty(DidConfigConstant.ISSUER_LOGIC_CONTRACT));
    contractAddress.setCptLogicContractAddress(properties.getProperty(DidConfigConstant.CPT_LOGIC_CONTRACT));
    contractAddress.setTimeOut(Integer.valueOf(properties.getProperty(DidConfigConstant.DID_TIME_OUT)));
    ConfigCache.putAddress(contractAddress);

    didConfig.setType(didType);
    didConfig.setNodeUrl(nodeUrl);
    didConfig.setGasPrice(gasPrice);
    didConfig.setGasLimit(gasLimit);
    didConfig.setReceiptRotationCount(receiptRotationCount);

    didConfig.setReceiptWaitTime(receiptWaitTime);
    didConfig.setChainId(chainId);
    return didConfig;
  }

}
