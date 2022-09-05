// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.command;

import com.reddate.did.DIDClient;
import com.reddate.did.config.ContractAddress;
import com.reddate.did.config.DidConfig;
import com.reddate.did.config.cache.ConfigCache;
import com.reddate.did.constant.CurrencyCode;
import com.reddate.did.service.contract.*;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DeployContract {

  public static void main(String[] args) throws Exception {

    System.out.println("Please Input You Want Deploy Contract : ");
    System.out.println("  1 - Did Contract");
    System.out.println("  2 - Auth Issuer Contract");
    System.out.println("  3 - Cpt Contract");
    System.out.println("  4 - All (1,2,3)");

    System.out.println("  5 - Upgrade Did Contract");
    System.out.println("  6 - Upgrade Auth Issuer Contract");
    System.out.println("  7 - Upgrade Cpt Contract");
    System.out.println("  8 - Upgrade All (5,6,7)");

    System.out.println("Please Input : ");
    Scanner scanner = new Scanner(System.in);
    int contractInd = scanner.nextInt();

    System.out.println("Begin try connect block chain, and deploy contract.");
    DIDClient didClient = new DIDClient();
    didClient.init();

    if (contractInd == 1) {
      deployDidContract();
    } else if (contractInd == 2) {
      deployIssuerContract();
    } else if (contractInd == 3) {
      deployCptContract();
    } else if (contractInd == 4) {
      deployDidContract();
      deployIssuerContract();
      deployCptContract();
    } else if (contractInd == 5) {
      upgradeDidContract();
    } else if (contractInd == 6) {
      upgradeIssuerContract();
    } else if (contractInd == 7) {
      upgradeCptContract();
    } else if (contractInd == 8) {
      upgradeDidContract();
      upgradeIssuerContract();
      upgradeCptContract();
    } else {
      System.out.println("You Selected Contract is incorrect.");
    }
  }

  public static ContractAddress deployDidContract() throws Exception {

    DidConfig client = ConfigCache.getConfig();
    ContractAddress contractAddress = ConfigCache.getAddress();
    System.out.println("--------- url = " + client.getNodeUrl());
    Web3j web3j = Web3j.build(new HttpService(client.getNodeUrl()));
    Credentials credentials = getCredentials(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY);
    StaticGasProvider staticGasProvider = new StaticGasProvider(client.getGasPrice(), client.getGasLimit());

//    deployDidContract1(client, web3j, credentials);

    TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, client.getChainId(),
            client.getReceiptRotationCount(), client.getReceiptWaitTime());
    DidContract didcontract = DidContract.deploy(web3j, transactionManager, staticGasProvider).send();
    String didContractAddress = didcontract.getContractAddress();
    System.out.println("did contract address:" + didContractAddress);
    contractAddress.setDidLogicContractAddress(didContractAddress);

    ProxyAdmin proxyAdmin = ProxyAdmin.deploy(web3j, transactionManager, staticGasProvider).send();
    String proxyAdminAddress = proxyAdmin.getContractAddress();
    System.out.println("did proxy admin address:" + proxyAdminAddress);
    contractAddress.setDidProxyAdminAddress(proxyAdminAddress);
    TransactionReceipt initializeTransactionReceipt = didcontract.getInitializeData().send();
    List<DidContract.GetInitializeDataLogEventResponse> logEventList = didcontract.getGetInitializeDataLogEvents(initializeTransactionReceipt);
    byte[] initializeData = logEventList.get(0).msg;

    TransparentUpgradeableProxy transParentProxyContract = TransparentUpgradeableProxy.deploy(
            web3j, transactionManager, staticGasProvider, didContractAddress, proxyAdminAddress, initializeData).send();
    String transParentProxyContractAddress = transParentProxyContract.getContractAddress();
    System.out.println("did transParentProxyContractAddress address:" + transParentProxyContractAddress);
    contractAddress.setDidContractAddress(transParentProxyContractAddress);

    web3j.shutdown();
    return contractAddress;
  }

  private static void deployDidContract1(DidConfig client, Web3j web3j, Credentials credentials) throws Exception {

    BigInteger nonce = getNonce(web3j, credentials.getAddress());
    RawTransaction transaction =
            RawTransaction.createContractTransaction(
                    nonce,
                    client.getGasPrice(),
                    client.getGasLimit(),
                    BigInteger.ZERO,
                    DidContract.BINARY);
    org.web3j.protocol.core.methods.response.EthSendTransaction transactionResponse =
            web3j.ethSendRawTransaction(
                            Numeric.toHexString(
                                    TransactionEncoder.signMessage(transaction, client.getChainId(), credentials)))
                    .sendAsync()
                    .get();

    String transactionHash = transactionResponse.getTransactionHash();
    System.out.println("----------1----------- " + transactionHash);
    TransactionReceipt transactionReceipt = waitForTransactionReceipt(web3j, transactionHash);
    System.out.println("------------------2------------------ " + transactionReceipt.getStatus());
  }

  private static TransactionReceipt waitForTransactionReceipt(Web3j web3j, String transactionHash) throws Exception {

    Optional<TransactionReceipt> transactionReceiptOptional =
            getTransactionReceipt(web3j, transactionHash, 15000, 40);

    if (!transactionReceiptOptional.isPresent()) {
      System.out.println("Transaction receipt not generated after " + 40 + " attempts");
    }

    return transactionReceiptOptional.get();
  }

  private static Optional<TransactionReceipt> getTransactionReceipt(Web3j web3j,
          String transactionHash, int sleepDuration, int attempts) throws Exception {

    Optional<TransactionReceipt> receiptOptional =
            sendTransactionReceiptRequest(web3j, transactionHash);
    for (int i = 0; i < attempts; i++) {
      if (!receiptOptional.isPresent()) {
        Thread.sleep(sleepDuration);
        receiptOptional = sendTransactionReceiptRequest(web3j, transactionHash);
      } else {
        break;
      }
    }

    return receiptOptional;
  }

  private static Optional<TransactionReceipt> sendTransactionReceiptRequest(Web3j web3j, String transactionHash)
          throws Exception {
    EthGetTransactionReceipt transactionReceipt =
            web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get();

    return transactionReceipt.getTransactionReceipt();
  }

  private static BigInteger getNonce(Web3j web3j, String address) throws Exception {
    EthGetTransactionCount ethGetTransactionCount =
            web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST)
                    .sendAsync()
                    .get();

    return ethGetTransactionCount.getTransactionCount();
  }

  public static ContractAddress deployIssuerContract() throws Exception {

    DidConfig client = ConfigCache.getConfig();
    ContractAddress contractAddress = ConfigCache.getAddress();
    Web3j web3j = Web3j.build(new HttpService(client.getNodeUrl()));
    Credentials credentials = getCredentials(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY);
    StaticGasProvider staticGasProvider = new StaticGasProvider(client.getGasPrice(), client.getGasLimit());

    TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, client.getChainId(),
            client.getReceiptRotationCount(), client.getReceiptWaitTime());
    IssuerContract issuerContract = IssuerContract.deploy(web3j, transactionManager, staticGasProvider).send();
    String issuerContractAddress = issuerContract.getContractAddress();
    System.out.println("issuerContractAddress is:" + issuerContractAddress);
    contractAddress.setIssuerLogicContractAddress(issuerContractAddress);

    ProxyAdmin proxyAdmin = ProxyAdmin.deploy(web3j, transactionManager, staticGasProvider).send();
    String proxyContractAddress = proxyAdmin.getContractAddress();
    System.out.println("issuer proxyContractAddress is:" + proxyContractAddress);
    contractAddress.setIssuerProxyAdminAddress(proxyContractAddress);
    TransactionReceipt initializeTransactionReceipt = issuerContract.getInitializeData().send();
    List<IssuerContract.GetInitializeDataLogEventResponse> logEventList = issuerContract.getGetInitializeDataLogEvents(initializeTransactionReceipt);
    byte[] initializeData = logEventList.get(0).msg;

    TransparentUpgradeableProxy transParentProxyContract = TransparentUpgradeableProxy.deploy(
            web3j, transactionManager, staticGasProvider, issuerContractAddress, proxyContractAddress, initializeData).send();
    String transParentProxyContractAddress = transParentProxyContract.getContractAddress();
    System.out.println("issuer transparentProxyContractAddress:" + transParentProxyContractAddress);
    contractAddress.setIssuerAddress(transParentProxyContractAddress);

    web3j.shutdown();
    return contractAddress;
  }

  public static ContractAddress deployCptContract() throws Exception {

    DidConfig client = ConfigCache.getConfig();
    ContractAddress contractAddress = ConfigCache.getAddress();
    Web3j web3j = Web3j.build(new HttpService(client.getNodeUrl()));
    Credentials credentials = getCredentials(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY);
    StaticGasProvider staticGasProvider = new StaticGasProvider(client.getGasPrice(), client.getGasLimit());

    TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, client.getChainId(),
            client.getReceiptRotationCount(), client.getReceiptWaitTime());
    CptContract cptContract = CptContract.deploy(web3j, transactionManager, staticGasProvider).send();
    String cptContractAddress = cptContract.getContractAddress();
    System.out.println("cptContractAddress is:" + cptContractAddress);
    contractAddress.setCptLogicContractAddress(cptContractAddress);

    ProxyAdmin proxyAdmin = ProxyAdmin.deploy(web3j, transactionManager, staticGasProvider).send();
    String proxyAdminAddress = proxyAdmin.getContractAddress();
    System.out.println("cpt proxyAdminAddress is:" + proxyAdminAddress);
    contractAddress.setCptProxyAdminAddress(proxyAdminAddress);
    TransactionReceipt initializeTransactionReceipt = cptContract.getInitializeData().send();
    List<CptContract.GetInitializeDataLogEventResponse> logEventList = cptContract.getGetInitializeDataLogEvents(initializeTransactionReceipt);
    byte[] initializeData = logEventList.get(0).msg;

    TransparentUpgradeableProxy transParentProxyContract = TransparentUpgradeableProxy.deploy(
            web3j, transactionManager, staticGasProvider, cptContractAddress, proxyAdminAddress, initializeData).send();
    String transParentProxyContractAddress = transParentProxyContract.getContractAddress();
    System.out.println("cpt transParentProxyContract:" + transParentProxyContractAddress);
    contractAddress.setCptAddress(transParentProxyContractAddress);

    web3j.shutdown();
    return contractAddress;
  }

  public static Boolean upgradeDidContract() throws Exception {

    DidConfig client = ConfigCache.getConfig();
    ContractAddress contractAddress = ConfigCache.getAddress();
    Web3j web3j = Web3j.build(new HttpService(client.getNodeUrl()));
    Credentials credentials = getCredentials(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY);
    StaticGasProvider staticGasProvider = new StaticGasProvider(client.getGasPrice(), client.getGasLimit());

    TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, client.getChainId(),
            client.getReceiptRotationCount(), client.getReceiptWaitTime());
    DidContract didcontract = DidContract.deploy(web3j, transactionManager, staticGasProvider).send();
    String didContractAddress = didcontract.getContractAddress();
    System.out.println("new did logic contract address:" + didContractAddress);

    ProxyAdmin proxyAdmin = ProxyAdmin.load(contractAddress.getDidProxyAdminAddress(), web3j, transactionManager, staticGasProvider);
    TransactionReceipt transactionReceipt = proxyAdmin.upgrade(contractAddress.getDidContractAddress(), didContractAddress).send();
    String txStatus = transactionReceipt.getStatus();
    System.out.println("Upgrade Did Contract Tx status is : " + txStatus);
    Boolean result = "0x1".equals(txStatus);

    if(result) {
      contractAddress.setDidLogicContractAddress(didContractAddress);
    }
    web3j.shutdown();
    return result;
  }

  public static Boolean upgradeIssuerContract() throws Exception {

    DidConfig client = ConfigCache.getConfig();
    ContractAddress contractAddress = ConfigCache.getAddress();
    Web3j web3j = Web3j.build(new HttpService(client.getNodeUrl()));
    Credentials credentials = getCredentials(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY);
    StaticGasProvider staticGasProvider = new StaticGasProvider(client.getGasPrice(), client.getGasLimit());

    TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, client.getChainId(),
            client.getReceiptRotationCount(), client.getReceiptWaitTime());
    IssuerContract issuerContract = IssuerContract.deploy(web3j, transactionManager, staticGasProvider).send();
    String issuerContractAddress = issuerContract.getContractAddress();
    System.out.println("new issuer logic contract address:" + issuerContractAddress);

    ProxyAdmin proxyAdmin = ProxyAdmin.load(contractAddress.getIssuerProxyAdminAddress(), web3j, transactionManager, staticGasProvider);
    TransactionReceipt transactionReceipt = proxyAdmin.upgrade(contractAddress.getIssuerAddress(), issuerContractAddress).send();
    String txStatus = transactionReceipt.getStatus();
    System.out.println("Upgrade issuer Contract Tx status is : " + txStatus);
    Boolean result = "0x1".equals(txStatus);

    if(result) {
      contractAddress.setIssuerLogicContractAddress(issuerContractAddress);
    }
    web3j.shutdown();
    return result;
  }

  public static Boolean upgradeCptContract() throws Exception {

    DidConfig client = ConfigCache.getConfig();
    ContractAddress contractAddress = ConfigCache.getAddress();
    Web3j web3j = Web3j.build(new HttpService(client.getNodeUrl()));
    Credentials credentials = getCredentials(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY);
    StaticGasProvider staticGasProvider = new StaticGasProvider(client.getGasPrice(), client.getGasLimit());

    TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, client.getChainId(),
            client.getReceiptRotationCount(), client.getReceiptWaitTime());
    CptContract cptContract = CptContract.deploy(web3j, transactionManager, staticGasProvider).send();
    String cptContractAddress = cptContract.getContractAddress();
    System.out.println("new cpt logic contract address:" + cptContractAddress);

    ProxyAdmin proxyAdmin = ProxyAdmin.load(contractAddress.getCptProxyAdminAddress(), web3j, transactionManager, staticGasProvider);
    TransactionReceipt transactionReceipt = proxyAdmin.upgrade(contractAddress.getCptAddress(), cptContractAddress).send();
    String txStatus = transactionReceipt.getStatus();
    System.out.println("Upgrade cpt Contract Tx status is : " + txStatus);
    Boolean result = "0x1".equals(txStatus);

    if(result) {
      contractAddress.setCptLogicContractAddress(cptContractAddress);
    }
    web3j.shutdown();
    return result;
  }

  /**
   * Generated the credentials information by the private key.
   *
   * @param inputPrivateKey the decimal private key String
   * @return return the credentials information object
   */
  public static Credentials getCredentials(String inputPrivateKey) {

    return Credentials.create(inputPrivateKey);
  }

}
