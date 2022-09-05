package com.reddate.did.service.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
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
public class TransparentUpgradeableProxy extends Contract {
  public static final String BINARY =
      "60806040526040516200126e3803806200126e833981810160405260608110156200002a5760006000fd5b81019080805190602001909291908051906020019092919080516040519392919084640100000000821115620000605760006000fd5b83820191506020820185811115620000785760006000fd5b8251866001820283011164010000000082111715620000975760006000fd5b8083526020830192505050908051906020019080838360005b83811015620000ce5780820151818401525b602081019050620000b0565b50505050905090810190601f168015620000fc5780820380516001836020036101000a031916815260200191505b506040526020015050505b82815b600160405180807f656970313936372e70726f78792e696d706c656d656e746174696f6e00000000815260200150601c019050604051809103902060001c0360001b600019167f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc60001b600019161415156200018257fe5b62000193826200024d60201b60201c565b600081511115620001b757620001b58282620002ec60201b620006331760201c565b505b5b5050600160405180807f656970313936372e70726f78792e61646d696e000000000000000000000000008152602001506013019050604051809103902060001c0360001b600019167fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d610360001b600019161415156200023257fe5b62000243826200032860201b60201c565b5b505050620005a5565b62000263816200035860201b6200066b1760201c565b1515620002bc576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526036815260200180620012126036913960400191505060405180910390fd5b60007f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc60001b9050818155505b50565b60606200031a8383604051806060016040528060278152602001620011eb602791396200037360201b60201c565b905062000322565b92915050565b60007fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d610360001b9050818155505b50565b60006000823b9050600081119150506200036e56505b919050565b606062000386846200035860201b60201c565b1515620003df576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526026815260200180620012486026913960400191505060405180910390fd5b600060608573ffffffffffffffffffffffffffffffffffffffff16856040518082805190602001908083835b6020831015156200043357805182525b6020820191506020810190506020830392506200040b565b6001836020036101000a038019825116818451168082178552505050505050905001915050600060405180830381855af49150503d806000811462000495576040519150601f19603f3d011682016040523d82523d6000602084013e6200049a565b606091505b5091509150620004b2828286620004c560201b60201c565b92505050620004be5650505b9392505050565b60608315620004dc578290506200059e566200059d565b600083511115620004f65782518084602001fd506200059c565b816040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825283818151815260200191508051906020019080838360005b83811015620005605780820151818401525b60208101905062000542565b50505050905090810190601f1680156200058e5780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b5b5b9392505050565b610c3680620005b56000396000f3fe60806040526004361061004e5760003560e01c80633659cfe6146100775780634f1ef286146100ca5780635c60da1b146101695780638f283970146101c1578063f851a4401461021457610065565b36610065575b61006261026c63ffffffff16565b5b005b5b61007461026c63ffffffff16565b5b005b3480156100845760006000fd5b506100c86004803603602081101561009c5760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610299565b005b610167600480360360408110156100e15760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019064010000000081111561011f5760006000fd5b8201836020820111156101325760006000fd5b803590602001918460018302840111640100000000831117156101555760006000fd5b90919293909091929390505050610302565b005b3480156101765760006000fd5b5061017f6103c2565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101ce5760006000fd5b50610212600480360360208110156101e65760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610430565b005b3480156102215760006000fd5b5061022a6105c5565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61027a61068563ffffffff16565b61029661028b61072a63ffffffff16565b61075d63ffffffff16565b5b565b6102a761079063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156102ef576102e9816107c363ffffffff16565b5b6102fe565b6102fd61026c63ffffffff16565b5b5b50565b61031061079063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156103ad57610352836107c363ffffffff16565b6103a68383838080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505061063363ffffffff16565b505b6103bc565b6103bb61026c63ffffffff16565b5b5b505050565b60006103d261079063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141561041d5761041361072a63ffffffff16565b905080505b61042c565b61042b61026c63ffffffff16565b5b5b90565b61043e61079063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156105b257600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141515156104f9576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252603a815260200180610b02603a913960400191505060405180910390fd5b7f7e644d79422f17c01e4894b5f4f588d331ebfa28653d42ae832dc59e38c9798f61052861079063ffffffff16565b82604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019250505060405180910390a16105ac8161081963ffffffff16565b5b6105c1565b6105c061026c63ffffffff16565b5b5b50565b60006105d561079063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156106205761061661079063ffffffff16565b905080505b61062f565b61062e61026c63ffffffff16565b5b5b90565b606061065e8383604051806060016040528060278152602001610b3c6027913961084963ffffffff16565b9050610665565b92915050565b60006000823b90506000811191505061068056505b919050565b61069361079063ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151515610719576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526042815260200180610bbf6042913960600191505060405180910390fd5b61072761099063ffffffff16565b5b565b600060007f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc60001b905080549150505b90565b36600060003760006000366000845af43d600060003e8060008114610785573d6000f361078a565b3d6000fd5b50505b50565b600060007fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d610360001b905080549150505b90565b6107d28161099363ffffffff16565b8073ffffffffffffffffffffffffffffffffffffffff167fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b60405160405180910390a25b50565b60007fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d610360001b9050818155505b50565b606061085a8461066b63ffffffff16565b15156108b1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526026815260200180610b996026913960400191505060405180910390fd5b600060608573ffffffffffffffffffffffffffffffffffffffff16856040518082805190602001908083835b60208310151561090357805182525b6020820191506020810190506020830392506108dd565b6001836020036101000a038019825116818451168082178552505050505050905001915050600060405180830381855af49150503d8060008114610963576040519150601f19603f3d011682016040523d82523d6000602084013e610968565b606091505b509150915061097e828286610a2963ffffffff16565b925050506109895650505b9392505050565b5b565b6109a28161066b63ffffffff16565b15156109f9576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526036815260200180610b636036913960400191505060405180910390fd5b60007f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc60001b9050818155505b50565b60608315610a3d57829050610afa56610af9565b600083511115610a555782518084602001fd50610af8565b816040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825283818151815260200191508051906020019080838360005b83811015610abd5780820151818401525b602081019050610aa1565b50505050905090810190601f168015610aea5780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b5b5b939250505056fe5472616e73706172656e745570677261646561626c6550726f78793a206e65772061646d696e20697320746865207a65726f2061646472657373416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c65645570677261646561626c6550726f78793a206e657720696d706c656d656e746174696f6e206973206e6f74206120636f6e7472616374416464726573733a2064656c65676174652063616c6c20746f206e6f6e2d636f6e74726163745472616e73706172656e745570677261646561626c6550726f78793a2061646d696e2063616e6e6f742066616c6c6261636b20746f2070726f787920746172676574a2646970667358221220a45e206fe56b3c38e7bef18680b25233635ebd90749427e0db9d2b5d4e8efdfc64736f6c634300060a0033416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c65645570677261646561626c6550726f78793a206e657720696d706c656d656e746174696f6e206973206e6f74206120636f6e7472616374416464726573733a2064656c65676174652063616c6c20746f206e6f6e2d636f6e7472616374";

  public static final String FUNC_ADMIN = "admin";

  public static final String FUNC_CHANGEADMIN = "changeAdmin";

  public static final String FUNC_IMPLEMENTATION = "implementation";

  public static final String FUNC_UPGRADETO = "upgradeTo";

  public static final String FUNC_UPGRADETOANDCALL = "upgradeToAndCall";

  public static final Event ADMINCHANGED_EVENT =
      new Event(
          "AdminChanged",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Address>() {}, new TypeReference<Address>() {}));
  ;

  public static final Event UPGRADED_EVENT =
      new Event("Upgraded", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
  ;

  @Deprecated
  protected TransparentUpgradeableProxy(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected TransparentUpgradeableProxy(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected TransparentUpgradeableProxy(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected TransparentUpgradeableProxy(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public List<AdminChangedEventResponse> getAdminChangedEvents(
      TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(ADMINCHANGED_EVENT, transactionReceipt);
    ArrayList<AdminChangedEventResponse> responses =
        new ArrayList<AdminChangedEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      AdminChangedEventResponse typedResponse = new AdminChangedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.previousAdmin = (String) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.newAdmin = (String) eventValues.getNonIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<AdminChangedEventResponse> adminChangedEventFlowable(EthFilter filter) {
    return web3j
        .ethLogFlowable(filter)
        .map(
            new Function<Log, AdminChangedEventResponse>() {
              @Override
              public AdminChangedEventResponse apply(Log log) {
                EventValuesWithLog eventValues =
                    extractEventParametersWithLog(ADMINCHANGED_EVENT, log);
                AdminChangedEventResponse typedResponse = new AdminChangedEventResponse();
                typedResponse.log = log;
                typedResponse.previousAdmin =
                    (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newAdmin =
                    (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<AdminChangedEventResponse> adminChangedEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(ADMINCHANGED_EVENT));
    return adminChangedEventFlowable(filter);
  }

  public List<UpgradedEventResponse> getUpgradedEvents(TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(UPGRADED_EVENT, transactionReceipt);
    ArrayList<UpgradedEventResponse> responses =
        new ArrayList<UpgradedEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      UpgradedEventResponse typedResponse = new UpgradedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<UpgradedEventResponse> upgradedEventFlowable(EthFilter filter) {
    return web3j
        .ethLogFlowable(filter)
        .map(
            new Function<Log, UpgradedEventResponse>() {
              @Override
              public UpgradedEventResponse apply(Log log) {
                EventValuesWithLog eventValues =
                    extractEventParametersWithLog(UPGRADED_EVENT, log);
                UpgradedEventResponse typedResponse = new UpgradedEventResponse();
                typedResponse.log = log;
                typedResponse.implementation =
                    (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<UpgradedEventResponse> upgradedEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(UPGRADED_EVENT));
    return upgradedEventFlowable(filter);
  }

  public RemoteFunctionCall<TransactionReceipt> admin() {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_ADMIN, Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> changeAdmin(String newAdmin) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_CHANGEADMIN,
            Arrays.<Type>asList(new Address(160, newAdmin)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> implementation() {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_IMPLEMENTATION, Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> upgradeTo(String newImplementation) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_UPGRADETO,
            Arrays.<Type>asList(new Address(160, newImplementation)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> upgradeToAndCall(
      String newImplementation, byte[] data) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_UPGRADETOANDCALL,
            Arrays.<Type>asList(
                new Address(160, newImplementation),
                new org.web3j.abi.datatypes.DynamicBytes(data)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  @Deprecated
  public static TransparentUpgradeableProxy load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new TransparentUpgradeableProxy(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static TransparentUpgradeableProxy load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new TransparentUpgradeableProxy(
        contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static TransparentUpgradeableProxy load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new TransparentUpgradeableProxy(
        contractAddress, web3j, credentials, contractGasProvider);
  }

  public static TransparentUpgradeableProxy load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new TransparentUpgradeableProxy(
        contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<TransparentUpgradeableProxy> deploy(
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider,
      String _logic,
      String admin_,
      byte[] _data) {
    String encodedConstructor =
        FunctionEncoder.encodeConstructor(
            Arrays.<Type>asList(
                new Address(160, _logic),
                new Address(160, admin_),
                new org.web3j.abi.datatypes.DynamicBytes(_data)));
    return deployRemoteCall(
        TransparentUpgradeableProxy.class,
        web3j,
        credentials,
        contractGasProvider,
        BINARY,
        encodedConstructor);
  }

  public static RemoteCall<TransparentUpgradeableProxy> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider,
      String _logic,
      String admin_,
      byte[] _data) {
    String encodedConstructor =
        FunctionEncoder.encodeConstructor(
            Arrays.<Type>asList(
                new Address(160, _logic),
                new Address(160, admin_),
                new org.web3j.abi.datatypes.DynamicBytes(_data)));
    return deployRemoteCall(
        TransparentUpgradeableProxy.class,
        web3j,
        transactionManager,
        contractGasProvider,
        BINARY,
        encodedConstructor);
  }

  @Deprecated
  public static RemoteCall<TransparentUpgradeableProxy> deploy(
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit,
      String _logic,
      String admin_,
      byte[] _data) {
    String encodedConstructor =
        FunctionEncoder.encodeConstructor(
            Arrays.<Type>asList(
                new Address(160, _logic),
                new Address(160, admin_),
                new org.web3j.abi.datatypes.DynamicBytes(_data)));
    return deployRemoteCall(
        TransparentUpgradeableProxy.class,
        web3j,
        credentials,
        gasPrice,
        gasLimit,
        BINARY,
        encodedConstructor);
  }

  @Deprecated
  public static RemoteCall<TransparentUpgradeableProxy> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit,
      String _logic,
      String admin_,
      byte[] _data) {
    String encodedConstructor =
        FunctionEncoder.encodeConstructor(
            Arrays.<Type>asList(
                new Address(160, _logic),
                new Address(160, admin_),
                new org.web3j.abi.datatypes.DynamicBytes(_data)));
    return deployRemoteCall(
        TransparentUpgradeableProxy.class,
        web3j,
        transactionManager,
        gasPrice,
        gasLimit,
        BINARY,
        encodedConstructor);
  }

  public static class AdminChangedEventResponse extends BaseEventResponse {
    public String previousAdmin;

    public String newAdmin;
  }

  public static class UpgradedEventResponse extends BaseEventResponse {
    public String implementation;
  }
}
