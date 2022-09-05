package com.reddate.did.service.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
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
import org.web3j.tuples.generated.Tuple5;
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
public class IssuerContract extends Contract {
  public static final String BINARY =
      "60806040523480156100115760006000fd5b50610017565b61214a806100266000396000f3fe60806040523480156100115760006000fd5b50600436106100985760003560e01c80638da5cb5b116100675780638da5cb5b14610101578063b0cf138f1461011f578063c76403a914610153578063d9287e5414610183578063f2fde38b146101b557610098565b80633d62d30e1461009e578063715018a6146100bc5780638129fc1c146100c65780638a57616e146100d057610098565b60006000fd5b6100a66101d1565b6040516100b39190611d1d565b60405180910390f35b6100c46102d8565b005b6100ce610425565b005b6100ea60048036038101906100e591906118f0565b61052a565b6040516100f8929190611d78565b60405180910390f35b6101096107d7565b6040516101169190611ce5565b60405180910390f35b61013960048036038101906101349190611961565b610806565b60405161014a959493929190611dfd565b60405180910390f35b61016d600480360381019061016891906118ac565b610aa1565b60405161017a9190611d01565b60405180910390f35b61019d600480360381019061019891906118ac565b610afd565b6040516101ac93929190611db0565b60405180910390f35b6101cf60048036038101906101ca9190611881565b610c5a565b005b606060606040516024016040516020818303038152906040527f8129fc1c000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19166020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff838183161783525050505090507fbd7d81e39a13c2876502c5c2520fe0bc2494c940a8183f0fc6e0ff564935afff6040518060400160405280600481526020017f3030303000000000000000000000000000000000000000000000000000000000815260200150826040516102c3929190611d40565b60405180910390a1809150506102d556505b90565b6102e6610e1863ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff1661030a6107d763ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16141515610362576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161035990611f14565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff16603360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a36000603360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b565b600060019054906101000a900460ff168061044a5750610449610e2563ffffffff16565b5b806104625750600060009054906101000a900460ff16155b15156104a3576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161049a90611ef3565b60405180910390fd5b6000600060019054906101000a900460ff1615905080156104f5576001600060016101000a81548160ff0219169083151502179055506001600060006101000a81548160ff0219169083151502179055505b610503610e4163ffffffff16565b5b8015610526576000600060016101000a81548160ff0219169083151502179055505b505b565b6060606061053c610e1863ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff166105606107d763ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff161415156105b8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105af90611f14565b60405180910390fd5b60006105c985610aa163ffffffff16565b905080156106bc577f13a1a2e79617720fb46b610845d8579eb443af29bed1b5794b092576e18755f96040518060400160405280600481526020017f32303232000000000000000000000000000000000000000000000000000000008152602001506040516106389190611e66565b60405180910390a16040518060400160405280600481526020017f32303232000000000000000000000000000000000000000000000000000000008152602001506040518060400160405280601481526020017f69737375657220616c726561647920657869737400000000000000000000000081526020015092509250506107cf565b606085905060608590506106df82826065600050610f549092919063ffffffff16565b7f13a1a2e79617720fb46b610845d8579eb443af29bed1b5794b092576e18755f96040518060400160405280600481526020017f30303030000000000000000000000000000000000000000000000000000000008152602001506040516107469190611e9c565b60405180910390a16040518060400160405280600481526020017f30303030000000000000000000000000000000000000000000000000000000008152602001506040518060400160405280600781526020017f7375636365737300000000000000000000000000000000000000000000000000815260200150945094505050506107cf565050505b5b9250929050565b6000603360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050610803565b90565b6060606060006000606060606065600050600201600050805480602002602001604051908101604052809291908181526020016000905b828210156108fe578382906000526020600020900160005b508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108ea5780601f106108bf576101008083540402835291602001916108ea565b820191906000526020600020905b8154815290600101906020018083116108cd57829003601f168201915b50505050508152602001906001019061083d565b5050505090506000815114156109eb576040518060400160405280600481526020017f323032310000000000000000000000000000000000000000000000000000000081526020015060006000600067ffffffffffffffff811180156109645760006000fd5b5060405190808252806020026020018201604052801561099857816020015b60608152602001906001900390816109835790505b506040518060400160405280601081526020017f697373756572206e6f74206578697374000000000000000000000000000000008152602001509291908292508191509550955095509550955050610a97565b600060006060610a028b8b8661108d63ffffffff16565b9250925092506040518060400160405280600481526020017f30303030000000000000000000000000000000000000000000000000000000008152602001508383836040518060400160405280600781526020017f73756363657373000000000000000000000000000000000000000000000000008152602001509291909850985098509850985050505050610a9756505050505b9295509295909350565b600060608290506000606560005060000160005082604051610ac39190611ccd565b90815260200160405180910390206000505490506000811415610aeb57600092505050610af8565b600192505050610af85650505b919050565b6060606060606000610b1485610aa163ffffffff16565b9050801515610bae576040518060400160405280600481526020017f32303231000000000000000000000000000000000000000000000000000000008152602001506040518060400160405280601081526020017f697373756572206e6f74206578697374000000000000000000000000000000008152602001506040518060200160405280600081526020015093509350935050610c53565b60608590506060610bcc82606560005061138f90919063ffffffff16565b90506040518060400160405280600481526020017f3030303000000000000000000000000000000000000000000000000000000000815260200150816040518060400160405280600781526020017f737563636573730000000000000000000000000000000000000000000000000081526020015090955095509550505050610c53565050505b9193909250565b610c68610e1863ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16610c8c6107d763ffffffff16565b73ffffffffffffffffffffffffffffffffffffffff16141515610ce4576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610cdb90611f14565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151515610d56576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d4d90611ed2565b60405180910390fd5b8073ffffffffffffffffffffffffffffffffffffffff16603360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a380603360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b50565b6000339050610e22565b90565b6000610e363061149163ffffffff16565b159050610e3e565b90565b600060019054906101000a900460ff1680610e665750610e65610e2563ffffffff16565b5b80610e7e5750600060009054906101000a900460ff16155b1515610ebf576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610eb690611ef3565b60405180910390fd5b6000600060019054906101000a900460ff161590508015610f11576001600060016101000a81548160ff0219169083151502179055506001600060006101000a81548160ff0219169083151502179055505b610f1f6114ab63ffffffff16565b610f2d6115a263ffffffff16565b5b8015610f50576000600060016101000a81548160ff0219169083151502179055505b505b565b60008360000160005083604051610f6b9190611ccd565b9081526020016040518091039020600050549050600081141561104a578360010160005083908060018154018082558091505060019003906000526020600020900160005b909190919091509080519060200190610fca929190611748565b508360020160005082908060018154018082558091505060019003906000526020600020900160005b90919091909150908051906020019061100d929190611748565b508360010160005080549050846000016000508460405161102e9190611ccd565b9081526020016040518091039020600050819090905550611086565b81846002016000506001830381548110151561106257fe5b906000526020600020900160005b509080519060200190611084929190611748565b505b505b505050565b60006000606060008663ffffffff161115156110aa576001955085505b60008563ffffffff161115156110c1576001945084505b60008560018803029050600085519050606060008863ffffffff16838115156110e657fe5b0490508863ffffffff1681028311156111025760018101905080505b808a63ffffffff16111561116f578281600067ffffffffffffffff8111801561112b5760006000fd5b5060405190808252806020026020018201604052801561115f57816020015b606081526020019060019003908161114a5790505b5096509650965050505050611386565b828463ffffffff1611156111dc578281600067ffffffffffffffff811180156111985760006000fd5b506040519080825280602002602001820160405280156111cc57816020015b60608152602001906001900390816111b75790505b5096509650965050505050611386565b60018a63ffffffff161480156111f75750828963ffffffff16115b15611252578267ffffffffffffffff811180156112145760006000fd5b5060405190808252806020026020018201604052801561124857816020015b60608152602001906001900390816112335790505b509150815061131a565b88840163ffffffff16831015156112bf578863ffffffff1667ffffffffffffffff811180156112815760006000fd5b506040519080825280602002602001820160405280156112b557816020015b60608152602001906001900390816112a05790505b5091508150611319565b8363ffffffff16830367ffffffffffffffff811180156112df5760006000fd5b5060405190808252806020026020018201604052801561131357816020015b60608152602001906001900390816112fe5790505b50915081505b5b6000600090505b825181101561136f5788818663ffffffff160181518110151561134057fe5b6020026020010151838281518110151561135657fe5b60200260200101819052505b8080600101915050611321565b508281839650965096505050505061138656505050505b93509350939050565b6060600083600001600050836040516113a89190611ccd565b9081526020016040518091039020600050549050606084600201600050600183038154811015156113d557fe5b906000526020600020900160005b508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156114785780601f1061144d57610100808354040283529160200191611478565b820191906000526020600020905b81548152906001019060200180831161145b57829003601f168201915b50505050509050809250505061148b5650505b92915050565b60006000823b9050600081119150506114a656505b919050565b600060019054906101000a900460ff16806114d057506114cf610e2563ffffffff16565b5b806114e85750600060009054906101000a900460ff16155b1515611529576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161152090611ef3565b60405180910390fd5b6000600060019054906101000a900460ff16159050801561157b576001600060016101000a81548160ff0219169083151502179055506001600060006101000a81548160ff0219169083151502179055505b5b801561159e576000600060016101000a81548160ff0219169083151502179055505b505b565b600060019054906101000a900460ff16806115c757506115c6610e2563ffffffff16565b5b806115df5750600060009054906101000a900460ff16155b1515611620576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161161790611ef3565b60405180910390fd5b6000600060019054906101000a900460ff161590508015611672576001600060016101000a81548160ff0219169083151502179055506001600060006101000a81548160ff0219169083151502179055505b6000611682610e1863ffffffff16565b905080603360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3505b8015611744576000600060016101000a81548160ff0219169083151502179055505b505b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061178957805160ff19168380011785556117bc565b828001600101855582156117bc579182015b828111156117bb578251826000509090559160200191906001019061179b565b5b5090506117c991906117cd565b5090565b6117f591906117d7565b808211156117f157600081815060009055506001016117d7565b5090565b9056612113565b60008135905061180b816120dd565b5b92915050565b600082601f83011215156118265760006000fd5b813561183961183482611f64565b611f35565b915080825260208301602083018583830111156118565760006000fd5b611861838284612086565b5050505b92915050565b60008135905061187a816120f8565b5b92915050565b6000602082840312156118945760006000fd5b60006118a2848285016117fc565b9150505b92915050565b6000602082840312156118bf5760006000fd5b600082013567ffffffffffffffff8111156118da5760006000fd5b6118e684828501611812565b9150505b92915050565b60006000604083850312156119055760006000fd5b600083013567ffffffffffffffff8111156119205760006000fd5b61192c85828601611812565b925050602083013567ffffffffffffffff81111561194a5760006000fd5b61195685828601611812565b9150505b9250929050565b60006000604083850312156119765760006000fd5b60006119848582860161186b565b92505060206119958582860161186b565b9150505b9250929050565b60006119ac8383611ab8565b90505b92915050565b6119be81612029565b82525b5050565b60006119d082611fa3565b6119da8185611fd5565b9350836020820285016119ec85611f92565b8060005b85811015611a295784840389528151611a0985826119a0565b9450611a1483611fc7565b925060208a019950505b6001810190506119f0565b5082975087955050505050505b92915050565b611a458161203c565b82525b5050565b6000611a5782611faf565b611a618185611fe7565b9350611a71818560208601612096565b611a7a816120cb565b84019150505b92915050565b6000611a9182611faf565b611a9b8185611ff9565b9350611aab818560208601612096565b8084019150505b92915050565b6000611ac382611fbb565b611acd8185612005565b9350611add818560208601612096565b611ae6816120cb565b84019150505b92915050565b6000611afd82611fbb565b611b078185612017565b9350611b17818560208601612096565b611b20816120cb565b84019150505b92915050565b6000611b39602683612017565b91507f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160008301527f646472657373000000000000000000000000000000000000000000000000000060208301526040820190505b919050565b6000611ba0601483612017565b91507f69737375657220616c726561647920657869737400000000000000000000000060008301526020820190505b919050565b6000611be1600783612017565b91507f737563636573730000000000000000000000000000000000000000000000000060008301526020820190505b919050565b6000611c22602e83612017565b91507f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160008301527f647920696e697469616c697a656400000000000000000000000000000000000060208301526040820190505b919050565b6000611c89602083612017565b91507f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657260008301526020820190505b919050565b611cc68161206a565b82525b5050565b6000611cd98284611a86565b91508190505b92915050565b6000602082019050611cfa60008301846119b5565b5b92915050565b6000602082019050611d166000830184611a3c565b5b92915050565b60006020820190508181036000830152611d378184611a4c565b90505b92915050565b60006040820190508181036000830152611d5a8185611af2565b90508181036020830152611d6e8184611a4c565b90505b9392505050565b60006040820190508181036000830152611d928185611af2565b90508181036020830152611da68184611af2565b90505b9392505050565b60006060820190508181036000830152611dca8186611af2565b90508181036020830152611dde8185611af2565b90508181036040830152611df28184611af2565b90505b949350505050565b600060a0820190508181036000830152611e178188611af2565b90508181036020830152611e2b8187611af2565b9050611e3a6040830186611cbd565b611e476060830185611cbd565b8181036080830152611e5981846119c5565b90505b9695505050505050565b60006040820190508181036000830152611e808184611af2565b90508181036020830152611e9381611b93565b90505b92915050565b60006040820190508181036000830152611eb68184611af2565b90508181036020830152611ec981611bd4565b90505b92915050565b60006020820190508181036000830152611eeb81611b2c565b90505b919050565b60006020820190508181036000830152611f0c81611c15565b90505b919050565b60006020820190508181036000830152611f2d81611c7c565b90505b919050565b6000604051905081810181811067ffffffffffffffff82111715611f595760006000fd5b80604052505b919050565b600067ffffffffffffffff821115611f7c5760006000fd5b601f19601f83011690506020810190505b919050565b60008190506020820190505b919050565b6000815190505b919050565b6000815190505b919050565b6000815190505b919050565b60006020820190505b919050565b60008282526020820190505b92915050565b60008282526020820190505b92915050565b60008190505b92915050565b60008282526020820190505b92915050565b60008282526020820190505b92915050565b600061203482612049565b90505b919050565b600081151590505b919050565b600073ffffffffffffffffffffffffffffffffffffffff821690505b919050565b60008190505b919050565b600063ffffffff821690505b919050565b828183376000838301525b505050565b60005b838110156120b55780820151818401525b602081019050612099565b838111156120c4576000848401525b505b505050565b6000601f19601f83011690505b919050565b6120e681612029565b811415156120f45760006000fd5b5b50565b61210181612075565b8114151561210f5760006000fd5b5b50565bfea2646970667358221220e32b90c41b300d1d73d791c2d827df5e70198692fdb94904791360556c1c7af764736f6c634300060a0033";

  public static final String FUNC_CREATEISSUER = "createIssuer";

  public static final String FUNC_GETALLISSUERINFO = "getAllIssuerInfo";

  public static final String FUNC_GETINITIALIZEDATA = "getInitializeData";

  public static final String FUNC_GETISSUERBYDID = "getIssuerByDid";

  public static final String FUNC_INITIALIZE = "initialize";

  public static final String FUNC_ISISSUEREXIST = "isIssuerExist";

  public static final String FUNC_OWNER = "owner";

  public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

  public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

  public static final Event OWNERSHIPTRANSFERRED_EVENT =
      new Event(
          "OwnershipTransferred",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
  ;

  public static final Event CREATEISSUERRETLOG_EVENT =
      new Event(
          "createIssuerRetLog",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
  ;

  public static final Event GETINITIALIZEDATALOG_EVENT =
      new Event(
          "getInitializeDataLog",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Utf8String>() {}, new TypeReference<DynamicBytes>() {}));
  ;

  @Deprecated
  protected IssuerContract(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected IssuerContract(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected IssuerContract(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected IssuerContract(
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

  public List<CreateIssuerRetLogEventResponse> getCreateIssuerRetLogEvents(
      TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(CREATEISSUERRETLOG_EVENT, transactionReceipt);
    ArrayList<CreateIssuerRetLogEventResponse> responses =
        new ArrayList<CreateIssuerRetLogEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      CreateIssuerRetLogEventResponse typedResponse = new CreateIssuerRetLogEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.msgcode = (String) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<CreateIssuerRetLogEventResponse> createIssuerRetLogEventFlowable(
      EthFilter filter) {
    return web3j
        .ethLogFlowable(filter)
        .map(
            new Function<Log, CreateIssuerRetLogEventResponse>() {
              @Override
              public CreateIssuerRetLogEventResponse apply(Log log) {
                EventValuesWithLog eventValues =
                    extractEventParametersWithLog(CREATEISSUERRETLOG_EVENT, log);
                CreateIssuerRetLogEventResponse typedResponse =
                    new CreateIssuerRetLogEventResponse();
                typedResponse.log = log;
                typedResponse.msgcode =
                    (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.msg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<CreateIssuerRetLogEventResponse> createIssuerRetLogEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(CREATEISSUERRETLOG_EVENT));
    return createIssuerRetLogEventFlowable(filter);
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

  public RemoteFunctionCall<TransactionReceipt> createIssuer(String did, String issuerInfo) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_CREATEISSUER,
            Arrays.<Type>asList(
                new Utf8String(did),
                new Utf8String(issuerInfo)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<Tuple5<String, String, BigInteger, BigInteger, List<String>>>
      getAllIssuerInfo(BigInteger page, BigInteger size) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_GETALLISSUERINFO,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.generated.Uint32(page),
                new org.web3j.abi.datatypes.generated.Uint32(size)),
            Arrays.<TypeReference<?>>asList(
                new TypeReference<Utf8String>() {},
                new TypeReference<Utf8String>() {},
                new TypeReference<Uint256>() {},
                new TypeReference<Uint256>() {},
                new TypeReference<DynamicArray<Utf8String>>() {}));
    return new RemoteFunctionCall<Tuple5<String, String, BigInteger, BigInteger, List<String>>>(
        function,
        new Callable<Tuple5<String, String, BigInteger, BigInteger, List<String>>>() {
          @Override
          public Tuple5<String, String, BigInteger, BigInteger, List<String>> call()
              throws Exception {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple5<String, String, BigInteger, BigInteger, List<String>>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (BigInteger) results.get(2).getValue(),
                (BigInteger) results.get(3).getValue(),
                convertToNative((List<Utf8String>) results.get(4).getValue()));
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

  public RemoteFunctionCall<Tuple3<String, String, String>> getIssuerByDid(String did) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_GETISSUERBYDID,
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

  public RemoteFunctionCall<TransactionReceipt> initialize() {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_INITIALIZE, Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<Boolean> isIssuerExist(String did) {
    final org.web3j.abi.datatypes.Function function =
        new org.web3j.abi.datatypes.Function(
            FUNC_ISISSUEREXIST,
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

  @Deprecated
  public static IssuerContract load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new IssuerContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static IssuerContract load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new IssuerContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static IssuerContract load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new IssuerContract(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static IssuerContract load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new IssuerContract(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<IssuerContract> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        IssuerContract.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<IssuerContract> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(
        IssuerContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  public static RemoteCall<IssuerContract> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        IssuerContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<IssuerContract> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        IssuerContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }

  public static class OwnershipTransferredEventResponse extends BaseEventResponse {
    public String previousOwner;

    public String newOwner;
  }

  public static class CreateIssuerRetLogEventResponse extends BaseEventResponse {
    public String msgcode;

    public String msg;
  }

  public static class GetInitializeDataLogEventResponse extends BaseEventResponse {
    public String msgcode;

    public byte[] msg;
  }
}
