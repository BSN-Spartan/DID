package com.reddate.did.service.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modify!</strong>
 *
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class ProxyAdmin extends Contract {
  public static final String BINARY =
      "60806040523480156100115760006000fd5b505b60006100236100c860201b60201c565b905080600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3505b6100d5565b60003390506100d2565b90565b610eb7806100e46000396000f3fe60806040526004361061007f5760003560e01c80639623609d1161004e5780639623609d146101fb57806399a88ec4146102fe578063f2fde38b14610371578063f3b7dead146103c45761007f565b8063204e1c7a14610085578063715018a6146101185780637eff275e146101305780638da5cb5b146101a35761007f565b60006000fd5b3480156100925760006000fd5b506100d6600480360360208110156100aa5760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610457565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101255760006000fd5b5061012e61052e565b005b34801561013d5760006000fd5b506101a1600480360360408110156101555760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506106b1565b005b3480156101b05760006000fd5b506101b9610810565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6102fc600480360360608110156102125760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001906401000000008111156102705760006000fd5b8201836020820111156102835760006000fd5b803590602001918460018302840111640100000000831117156102a65760006000fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505090909192909091929050505061083f565b005b34801561030b5760006000fd5b5061036f600480360360408110156103235760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610a0e565b005b34801561037e5760006000fd5b506103c2600480360360208110156103965760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610b6d565b005b3480156103d15760006000fd5b50610415600480360360208110156103e95760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610d77565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000600060608373ffffffffffffffffffffffffffffffffffffffff1660405180807f5c60da1b000000000000000000000000000000000000000000000000000000008152602001506004019050600060405180830381855afa9150503d80600081146104e0576040519150601f19603f3d011682016040523d82523d6000602084013e6104e5565b606091505b50915091508115156104f75760006000fd5b80806020019051602081101561050d5760006000fd5b8101908080519060200190929190505050925050506105295650505b919050565b61053c610e4e63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff1661056061081063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff161415156105ee576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281526020015060200191505060405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff16600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a36000600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b565b6106bf610e4e63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff166106e361081063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16141515610771576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281526020015060200191505060405180910390fd5b8173ffffffffffffffffffffffffffffffffffffffff16638f283970826040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050600060405180830381600087803b1580156107f15760006000fd5b505af1158015610806573d600060003e3d6000fd5b505050505b5b5050565b6000600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905061083c565b90565b61084d610e4e63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff1661087161081063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff161415156108ff576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281526020015060200191505060405180910390fd5b8273ffffffffffffffffffffffffffffffffffffffff16634f1ef2863484846040518463ffffffff1660e01b8152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b838110156109a05780820151818401525b602081019050610984565b50505050905090810190601f1680156109cd5780820380516001836020036101000a031916815260200191505b5093505050506000604051808303818588803b1580156109ed5760006000fd5b505af1158015610a02573d600060003e3d6000fd5b50505050505b5b505050565b610a1c610e4e63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16610a4061081063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16141515610ace576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281526020015060200191505060405180910390fd5b8173ffffffffffffffffffffffffffffffffffffffff16633659cfe6826040518263ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050600060405180830381600087803b158015610b4e5760006000fd5b505af1158015610b63573d600060003e3d6000fd5b505050505b5b5050565b610b7b610e4e63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16610b9f61081063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16141515610c2d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281526020015060200191505060405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151515610cb5576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526026815260200180610e5c6026913960400191505060405180910390fd5b8073ffffffffffffffffffffffffffffffffffffffff16600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a380600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b50565b6000600060608373ffffffffffffffffffffffffffffffffffffffff1660405180807ff851a440000000000000000000000000000000000000000000000000000000008152602001506004019050600060405180830381855afa9150503d8060008114610e00576040519150601f19603f3d011682016040523d82523d6000602084013e610e05565b606091505b5091509150811515610e175760006000fd5b808060200190516020811015610e2d5760006000fd5b810190808051906020019092919050505092505050610e495650505b919050565b6000339050610e58565b9056fe4f776e61626c653a206e6577206f776e657220697320746865207a65726f2061646472657373a26469706673582212201f7afe6bfff0ea620866c3baeb9d913190005988bce5c133f0616899fc3a43f264736f6c634300060a0033";

  public static final String FUNC_CHANGEPROXYADMIN = "changeProxyAdmin";

  public static final String FUNC_GETPROXYADMIN = "getProxyAdmin";

  public static final String FUNC_GETPROXYIMPLEMENTATION = "getProxyImplementation";

  public static final String FUNC_OWNER = "owner";

  public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

  public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

  public static final String FUNC_UPGRADE = "upgrade";

  public static final String FUNC_UPGRADEANDCALL = "upgradeAndCall";

  public static final Event OWNERSHIPTRANSFERRED_EVENT =
      new Event(
          "OwnershipTransferred",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
  ;

  @Deprecated
  protected ProxyAdmin(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected ProxyAdmin(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected ProxyAdmin(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected ProxyAdmin(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
      TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
    ArrayList<OwnershipTransferredEventResponse> responses =
        new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
      EthFilter filter) {
    return web3j
        .ethLogFlowable(filter)
        .map(
            new Function<Log, OwnershipTransferredEventResponse>() {
              @Override
              public OwnershipTransferredEventResponse apply(Log log) {
                EventValuesWithLog eventValues =
                    extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse =
                    new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner =
                    (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
    return ownershipTransferredEventFlowable(filter);
  }

  public RemoteFunctionCall<TransactionReceipt> changeProxyAdmin(String proxy, String newAdmin) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_CHANGEPROXYADMIN,
            Arrays.<Type>asList(
                new Address(160, proxy),
                new Address(160, newAdmin)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<String> getProxyAdmin(String proxy) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_GETPROXYADMIN,
            Arrays.<Type>asList(new Address(160, proxy)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<String> getProxyImplementation(String proxy) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_GETPROXYIMPLEMENTATION,
            Arrays.<Type>asList(new Address(160, proxy)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<String> owner() {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_OWNER,
            Arrays.<Type>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_RENOUNCEOWNERSHIP,
            Arrays.<Type>asList(),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_TRANSFEROWNERSHIP,
            Arrays.<Type>asList(new Address(160, newOwner)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> upgrade(String proxy, String implementation) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_UPGRADE,
            Arrays.<Type>asList(
                new Address(160, proxy),
                new Address(160, implementation)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> upgradeAndCall(
      String proxy, String implementation, byte[] data) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_UPGRADEANDCALL,
            Arrays.<Type>asList(
                new Address(160, proxy),
                new Address(160, implementation),
                new org.web3j.abi.datatypes.DynamicBytes(data)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  @Deprecated
  public static ProxyAdmin load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new ProxyAdmin(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static ProxyAdmin load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new ProxyAdmin(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static ProxyAdmin load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new ProxyAdmin(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static ProxyAdmin load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new ProxyAdmin(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<ProxyAdmin> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(ProxyAdmin.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<ProxyAdmin> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(ProxyAdmin.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  public static RemoteCall<ProxyAdmin> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        ProxyAdmin.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<ProxyAdmin> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        ProxyAdmin.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }

  public static class OwnershipTransferredEventResponse extends BaseEventResponse {
    public String previousOwner;

    public String newOwner;
  }
}
