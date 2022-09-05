package com.reddate.did.service.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

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
public class DidContract extends Contract {
  public static final String BINARY =
      "60806040523480156100115760006000fd5b50610017565b6122e1806100266000396000f3fe60806040523480156100115760006000fd5b50600436106100985760003560e01c80638da5cb5b116100675780638da5cb5b1461034d578063ac521d0e14610397578063b0d8e369146105df578063e51af990146106ba578063f2fde38b1461090257610098565b80633d62d30e1461009e578063715018a6146101225780637ccb6a641461012c5780638129fc1c1461034357610098565b60006000fd5b6100a6610947565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100e75780820151818401525b6020810190506100cb565b50505050905090810190601f1680156101145780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61012a610b20565b005b6101ed600480360360208110156101435760006000fd5b81019080803590602001906401000000008111156101615760006000fd5b8201836020820111156101745760006000fd5b803590602001918460018302840111640100000000831117156101975760006000fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050909091929090919290505050610ca3565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156102365780820151818401525b60208101905061021a565b50505050905090810190601f1680156102635780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561029d5780820151818401525b602081019050610281565b50505050905090810190601f1680156102ca5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156103045780820151818401525b6020810190506102e8565b50505050905090810190601f1680156103315780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b61034b610e00565b005b610355610f1b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6104f6600480360360408110156103ae5760006000fd5b81019080803590602001906401000000008111156103cc5760006000fd5b8201836020820111156103df5760006000fd5b803590602001918460018302840111640100000000831117156104025760006000fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509090919290909192908035906020019064010000000081111561046a5760006000fd5b82018360208201111561047d5760006000fd5b803590602001918460018302840111640100000000831117156104a05760006000fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050909091929090919290505050610f4a565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b8381101561053b5780820151818401525b60208101905061051f565b50505050905090810190601f1680156105685780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b838110156105a25780820151818401525b602081019050610586565b50505050905090810190601f1680156105cf5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b6106a0600480360360208110156105f65760006000fd5b81019080803590602001906401000000008111156106145760006000fd5b8201836020820111156106275760006000fd5b8035906020019184600183028401116401000000008311171561064a5760006000fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050909091929090919290505050611372565b604051808215151515815260200191505060405180910390f35b610819600480360360408110156106d15760006000fd5b81019080803590602001906401000000008111156106ef5760006000fd5b8201836020820111156107025760006000fd5b803590602001918460018302840111640100000000831117156107255760006000fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509090919290909192908035906020019064010000000081111561078d5760006000fd5b8201836020820111156107a05760006000fd5b803590602001918460018302840111640100000000831117156107c35760006000fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505090909192909091929050505061141c565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b8381101561085e5780820151818401525b602081019050610842565b50505050905090810190601f16801561088b5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b838110156108c55780820151818401525b6020810190506108a9565b50505050905090810190601f1680156108f25780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b610945600480360360208110156109195760006000fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611843565b005b606060606040516024016040516020818303038152906040527f8129fc1c000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19166020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff838183161783525050505090507fbd7d81e39a13c2876502c5c2520fe0bc2494c940a8183f0fc6e0ff564935afff6040518060400160405280600481526020017f303030300000000000000000000000000000000000000000000000000000000081526020015082604051808060200180602001838103835285818151815260200191508051906020019080838360005b83811015610a705780820151818401525b602081019050610a54565b50505050905090810190601f168015610a9d5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b83811015610ad75780820151818401525b602081019050610abb565b50505050905090810190601f168015610b045780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a180915050610b1d56505b90565b610b2e611a4d63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16610b52610f1b63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16141515610be0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281526020015060200191505060405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff16603360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a36000603360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b565b6060606060606000610cba8561137263ffffffff16565b9050801515610d54576040518060400160405280600481526020017f32303131000000000000000000000000000000000000000000000000000000008152602001506040518060400160405280600d81526020017f646964206e6f74206578697374000000000000000000000000000000000000008152602001506040518060200160405280600081526020015093509350935050610df9565b60608590506060610d72826065600050611a5a90919063ffffffff16565b90506040518060400160405280600481526020017f3030303000000000000000000000000000000000000000000000000000000000815260200150816040518060400160405280600781526020017f737563636573730000000000000000000000000000000000000000000000000081526020015090955095509550505050610df9565050505b9193909250565b600060019054906101000a900460ff1680610e255750610e24611baa63ffffffff16565b5b80610e3d5750600060009054906101000a900460ff16155b1515610e94576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602e81526020018061227e602e913960400191505060405180910390fd5b6000600060019054906101000a900460ff161590508015610ee6576001600060016101000a81548160ff0219169083151502179055506001600060006101000a81548160ff0219169083151502179055505b610ef4611bc663ffffffff16565b5b8015610f17576000600060016101000a81548160ff0219169083151502179055505b505b565b6000603360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050610f47565b90565b60606060610f5c611a4d63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16610f80610f1b63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff1614151561100e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281526020015060200191505060405180910390fd5b600061101f8561137263ffffffff16565b90508015156111b5577f9af1b91de8cb6c51fb6a8211f6b04fc6c6c3642f77f3e417b9dd5f82f9e860a56040518060400160405280600481526020017f3230313100000000000000000000000000000000000000000000000000000000815260200150604051808060200180602001838103835284818151815260200191508051906020019080838360005b838110156110c75780820151818401525b6020810190506110ab565b50505050905090810190601f1680156110f45780820380516001836020036101000a031916815260200191505b508381038252600d8152602001807f646964206e6f7420657869737400000000000000000000000000000000000000815260200150602001935050505060405180910390a16040518060400160405280600481526020017f32303131000000000000000000000000000000000000000000000000000000008152602001506040518060400160405280600d81526020017f646964206e6f7420657869737400000000000000000000000000000000000000815260200150925092505061136a565b606085905060608590506111d882826065600050611cef9092919063ffffffff16565b7f9af1b91de8cb6c51fb6a8211f6b04fc6c6c3642f77f3e417b9dd5f82f9e860a56040518060400160405280600481526020017f3030303000000000000000000000000000000000000000000000000000000000815260200150604051808060200180602001838103835284818151815260200191508051906020019080838360005b838110156112775780820151818401525b60208101905061125b565b50505050905090810190601f1680156112a45780820380516001836020036101000a031916815260200191505b50838103825260078152602001807f7375636365737300000000000000000000000000000000000000000000000000815260200150602001935050505060405180910390a16040518060400160405280600481526020017f30303030000000000000000000000000000000000000000000000000000000008152602001506040518060400160405280600781526020017f73756363657373000000000000000000000000000000000000000000000000008152602001509450945050505061136a565050505b5b9250929050565b6000606082905060006065600050600001600050826040518082805190602001908083835b6020831015156113bd57805182525b602082019150602081019050602083039250611397565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600050549050600081141561140a57600092505050611417565b6001925050506114175650505b919050565b6060606061142e611a4d63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16611452610f1b63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff161415156114e0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281526020015060200191505060405180910390fd5b60006114f18561137263ffffffff16565b90508015611686577f12bc3c9c8a99ef08acd8f1191e57dc6246466d47e920eba9634c46bf19c9cbc96040518060400160405280600481526020017f3230313200000000000000000000000000000000000000000000000000000000815260200150604051808060200180602001838103835284818151815260200191508051906020019080838360005b838110156115985780820151818401525b60208101905061157c565b50505050905090810190601f1680156115c55780820380516001836020036101000a031916815260200191505b50838103825260118152602001807f64696420616c7265616479206578697374000000000000000000000000000000815260200150602001935050505060405180910390a16040518060400160405280600481526020017f32303132000000000000000000000000000000000000000000000000000000008152602001506040518060400160405280601181526020017f64696420616c7265616479206578697374000000000000000000000000000000815260200150925092505061183b565b606085905060608590506116a982826065600050611cef9092919063ffffffff16565b7f12bc3c9c8a99ef08acd8f1191e57dc6246466d47e920eba9634c46bf19c9cbc96040518060400160405280600481526020017f3030303000000000000000000000000000000000000000000000000000000000815260200150604051808060200180602001838103835284818151815260200191508051906020019080838360005b838110156117485780820151818401525b60208101905061172c565b50505050905090810190601f1680156117755780820380516001836020036101000a031916815260200191505b50838103825260078152602001807f7375636365737300000000000000000000000000000000000000000000000000815260200150602001935050505060405180910390a16040518060400160405280600481526020017f30303030000000000000000000000000000000000000000000000000000000008152602001506040518060400160405280600781526020017f73756363657373000000000000000000000000000000000000000000000000008152602001509450945050505061183b565050505b5b9250929050565b611851611a4d63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16611875610f1b63ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16141515611903576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281526020015060200191505060405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161415151561198b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260268152602001806122586026913960400191505060405180910390fd5b8073ffffffffffffffffffffffffffffffffffffffff16603360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a380603360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b50565b6000339050611a57565b90565b6060600083600001600050836040518082805190602001908083835b602083101515611a9c57805182525b602082019150602081019050602083039250611a76565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060005054905060608460020160005060018303815481101515611aee57fe5b906000526020600020900160005b508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611b915780601f10611b6657610100808354040283529160200191611b91565b820191906000526020600020905b815481529060010190602001808311611b7457829003601f168201915b505050505090508092505050611ba45650505b92915050565b6000611bbb30611ec463ffffffff16565b159050611bc3565b90565b600060019054906101000a900460ff1680611beb5750611bea611baa63ffffffff16565b5b80611c035750600060009054906101000a900460ff16155b1515611c5a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602e81526020018061227e602e913960400191505060405180910390fd5b6000600060019054906101000a900460ff161590508015611cac576001600060016101000a81548160ff0219169083151502179055506001600060006101000a81548160ff0219169083151502179055505b611cba611ede63ffffffff16565b611cc8611feb63ffffffff16565b5b8015611ceb576000600060016101000a81548160ff0219169083151502179055505b505b565b600083600001600050836040518082805190602001908083835b602083101515611d2f57805182525b602082019150602081019050602083039250611d09565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000505490506000811415611e81578360010160005083908060018154018082558091505060019003906000526020600020900160005b909190919091509080519060200190611db39291906121a7565b508360020160005082908060018154018082558091505060019003906000526020600020900160005b909190919091509080519060200190611df69291906121a7565b50836001016000508054905084600001600050846040518082805190602001908083835b602083101515611e4057805182525b602082019150602081019050602083039250611e1a565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600050819090905550611ebd565b818460020160005060018303815481101515611e9957fe5b906000526020600020900160005b509080519060200190611ebb9291906121a7565b505b505b505050565b60006000823b905060008111915050611ed956505b919050565b600060019054906101000a900460ff1680611f035750611f02611baa63ffffffff16565b5b80611f1b5750600060009054906101000a900460ff16155b1515611f72576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602e81526020018061227e602e913960400191505060405180910390fd5b6000600060019054906101000a900460ff161590508015611fc4576001600060016101000a81548160ff0219169083151502179055506001600060006101000a81548160ff0219169083151502179055505b5b8015611fe7576000600060016101000a81548160ff0219169083151502179055505b505b565b600060019054906101000a900460ff1680612010575061200f611baa63ffffffff16565b5b806120285750600060009054906101000a900460ff16155b151561207f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602e81526020018061227e602e913960400191505060405180910390fd5b6000600060019054906101000a900460ff1615905080156120d1576001600060016101000a81548160ff0219169083151502179055506001600060006101000a81548160ff0219169083151502179055505b60006120e1611a4d63ffffffff16565b905080603360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3505b80156121a3576000600060016101000a81548160ff0219169083151502179055505b505b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106121e857805160ff191683800117855561221b565b8280016001018555821561221b579182015b8281111561221a57825182600050909055916020019190600101906121fa565b5b509050612228919061222c565b5090565b6122549190612236565b808211156122505760008181506000905550600101612236565b5090565b9056fe4f776e61626c653a206e6577206f776e657220697320746865207a65726f2061646472657373496e697469616c697a61626c653a20636f6e747261637420697320616c726561647920696e697469616c697a6564a26469706673582212204fd7aa6f9b6acdbc08fa70978fb41c603c7671d038da19d746a91c9efbe8203364736f6c634300060a0033";

  public static final String FUNC_CREATEDID = "createDid";

  public static final String FUNC_GETDOCUMENT = "getDocument";

  public static final String FUNC_GETINITIALIZEDATA = "getInitializeData";

  public static final String FUNC_INITIALIZE = "initialize";

  public static final String FUNC_ISDIDEXIST = "isDidExist";

  public static final String FUNC_OWNER = "owner";

  public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

  public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

  public static final String FUNC_UPDATEDIDAUTH = "updateDidAuth";

  public static final Event OWNERSHIPTRANSFERRED_EVENT =
      new Event(
          "OwnershipTransferred",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
  ;

  public static final Event CREATEDIDRETLOG_EVENT =
      new Event(
          "createDidRetLog",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
  ;

  public static final Event GETINITIALIZEDATALOG_EVENT =
      new Event(
          "getInitializeDataLog",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Utf8String>() {}, new TypeReference<DynamicBytes>() {}));
  ;

  public static final Event UPDATEDIDAUTHRETLOG_EVENT =
      new Event(
          "updateDidAuthRetLog",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
  ;

  @Deprecated
  protected DidContract(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected DidContract(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected DidContract(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected DidContract(
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

  public List<CreateDidRetLogEventResponse> getCreateDidRetLogEvents(
      TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(CREATEDIDRETLOG_EVENT, transactionReceipt);
    ArrayList<CreateDidRetLogEventResponse> responses =
        new ArrayList<CreateDidRetLogEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      CreateDidRetLogEventResponse typedResponse = new CreateDidRetLogEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.msgcode = (String) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<CreateDidRetLogEventResponse> createDidRetLogEventFlowable(EthFilter filter) {
    return web3j
        .ethLogFlowable(filter)
        .map(
            new Function<Log, CreateDidRetLogEventResponse>() {
              @Override
              public CreateDidRetLogEventResponse apply(Log log) {
                EventValuesWithLog eventValues =
                    extractEventParametersWithLog(CREATEDIDRETLOG_EVENT, log);
                CreateDidRetLogEventResponse typedResponse = new CreateDidRetLogEventResponse();
                typedResponse.log = log;
                typedResponse.msgcode =
                    (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<CreateDidRetLogEventResponse> createDidRetLogEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(CREATEDIDRETLOG_EVENT));
    return createDidRetLogEventFlowable(filter);
  }

  public List<GetInitializeDataLogEventResponse> getGetInitializeDataLogEvents(
      TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(GETINITIALIZEDATALOG_EVENT, transactionReceipt);
    ArrayList<GetInitializeDataLogEventResponse> responses =
        new ArrayList<GetInitializeDataLogEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      GetInitializeDataLogEventResponse typedResponse = new GetInitializeDataLogEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.msgcode = (String) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.msg = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<GetInitializeDataLogEventResponse> getInitializeDataLogEventFlowable(
      EthFilter filter) {
    return web3j
        .ethLogFlowable(filter)
        .map(
            new Function<Log, GetInitializeDataLogEventResponse>() {
              @Override
              public GetInitializeDataLogEventResponse apply(Log log) {
                EventValuesWithLog eventValues =
                    extractEventParametersWithLog(GETINITIALIZEDATALOG_EVENT, log);
                GetInitializeDataLogEventResponse typedResponse =
                    new GetInitializeDataLogEventResponse();
                typedResponse.log = log;
                typedResponse.msgcode =
                    (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<GetInitializeDataLogEventResponse> getInitializeDataLogEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(GETINITIALIZEDATALOG_EVENT));
    return getInitializeDataLogEventFlowable(filter);
  }

  public List<UpdateDidAuthRetLogEventResponse> getUpdateDidAuthRetLogEvents(
      TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(UPDATEDIDAUTHRETLOG_EVENT, transactionReceipt);
    ArrayList<UpdateDidAuthRetLogEventResponse> responses =
        new ArrayList<UpdateDidAuthRetLogEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      UpdateDidAuthRetLogEventResponse typedResponse = new UpdateDidAuthRetLogEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.msgcode = (String) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<UpdateDidAuthRetLogEventResponse> updateDidAuthRetLogEventFlowable(
      EthFilter filter) {
    return web3j
        .ethLogFlowable(filter)
        .map(
            new Function<Log, UpdateDidAuthRetLogEventResponse>() {
              @Override
              public UpdateDidAuthRetLogEventResponse apply(Log log) {
                EventValuesWithLog eventValues =
                    extractEventParametersWithLog(UPDATEDIDAUTHRETLOG_EVENT, log);
                UpdateDidAuthRetLogEventResponse typedResponse =
                    new UpdateDidAuthRetLogEventResponse();
                typedResponse.log = log;
                typedResponse.msgcode =
                    (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<UpdateDidAuthRetLogEventResponse> updateDidAuthRetLogEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(UPDATEDIDAUTHRETLOG_EVENT));
    return updateDidAuthRetLogEventFlowable(filter);
  }

  public RemoteFunctionCall<TransactionReceipt> createDid(String did, String didDocument) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_CREATEDID,
            Arrays.<Type>asList(
                new Utf8String(did),
                new Utf8String(didDocument)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<Tuple3<String, String, String>> getDocument(String did) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_GETDOCUMENT,
            Arrays.<Type>asList(new Utf8String(did)),
            Arrays.<TypeReference<?>>asList(
                new TypeReference<Utf8String>() {},
                new TypeReference<Utf8String>() {},
                new TypeReference<Utf8String>() {}));
    return new RemoteFunctionCall<Tuple3<String, String, String>>(
        function,
        new Callable<Tuple3<String, String, String>>() {
          @Override
          public Tuple3<String, String, String> call() throws Exception {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple3<String, String, String>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (String) results.get(2).getValue());
          }
        });
  }

  public RemoteFunctionCall<TransactionReceipt> getInitializeData() {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_GETINITIALIZEDATA,
            Arrays.<Type>asList(),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> initialize() {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_INITIALIZE, Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<Boolean> isDidExist(String did) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_ISDIDEXIST,
            Arrays.<Type>asList(new Utf8String(did)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

  public RemoteFunctionCall<TransactionReceipt> updateDidAuth(String did, String didDocument) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_UPDATEDIDAUTH,
            Arrays.<Type>asList(
                new Utf8String(did),
                new Utf8String(didDocument)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  @Deprecated
  public static DidContract load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new DidContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static DidContract load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new DidContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static DidContract load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new DidContract(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static DidContract load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new DidContract(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<DidContract> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(DidContract.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<DidContract> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(DidContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  public static RemoteCall<DidContract> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        DidContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<DidContract> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        DidContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }

  public static class OwnershipTransferredEventResponse extends BaseEventResponse {
    public String previousOwner;

    public String newOwner;
  }

  public static class CreateDidRetLogEventResponse extends BaseEventResponse {
    public String msgcode;

    public String msg;
  }

  public static class GetInitializeDataLogEventResponse extends BaseEventResponse {
    public String msgcode;

    public byte[] msg;
  }

  public static class UpdateDidAuthRetLogEventResponse extends BaseEventResponse {
    public String msgcode;

    public String msg;
  }
}
