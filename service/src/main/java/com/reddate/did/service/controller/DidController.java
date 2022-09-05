package com.reddate.did.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.reddate.did.DIDClient;
import com.reddate.did.command.DeployContract;
import com.reddate.did.config.ContractAddress;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.protocol.common.JsonSchema;
import com.reddate.did.protocol.common.Proof;
import com.reddate.did.protocol.common.*;
import com.reddate.did.protocol.request.CreateCredential;
import com.reddate.did.protocol.request.RegisterCpt;
import com.reddate.did.protocol.request.ResetDid;
import com.reddate.did.protocol.request.RevokeCredential;
import com.reddate.did.protocol.response.CptInfo;
import com.reddate.did.protocol.response.CredentialWrapper;
import com.reddate.did.protocol.response.*;
import com.reddate.did.sdk.protocol.request.RequestParam;
import com.reddate.did.sdk.protocol.request.*;
import com.reddate.did.sdk.protocol.response.BlockInfoResp;
import com.reddate.did.sdk.util.ECDSAUtils;
import com.reddate.did.sdk.util.Signatures;
import com.reddate.did.service.vo.request.did.DidDocSotreReq;
import com.reddate.did.service.vo.request.did.*;
import com.reddate.did.service.vo.response.ResultData;
import com.reddate.did.service.vo.response.did.ContractAddrResp;
import com.reddate.did.service.vo.response.did.ContractUpgradeResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@RestController
@RequestMapping("/did")
public class DidController  extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(DidController.class);
	
	private static DIDClient didClient;

	static {
		didClient = new DIDClient();
		didClient.init();
	}

	
	@GetMapping("/deploy")
	public ResponseEntity<ResultData<ContractAddrResp>> deployContract() throws Exception{

		ContractAddrResp contractAddrResp =  new ContractAddrResp();
		ContractAddress didContract = DeployContract.deployDidContract();
		contractAddrResp.setDid(didContract.getDidLogicContractAddress());
		contractAddrResp.setDidProxy(didContract.getDidProxyAdminAddress());
		contractAddrResp.setDidTrans(didContract.getDidContractAddress());

		ContractAddress issuerContract = DeployContract.deployIssuerContract();
		contractAddrResp.setIssuer(issuerContract.getIssuerLogicContractAddress());
		contractAddrResp.setIssuerProxy(issuerContract.getIssuerProxyAdminAddress());
		contractAddrResp.setIssuerTrans(issuerContract.getIssuerAddress());

		ContractAddress cptContract = DeployContract.deployCptContract();
		contractAddrResp.setCpt(cptContract.getCptLogicContractAddress());
		contractAddrResp.setCptProxy(cptContract.getCptProxyAdminAddress());
		contractAddrResp.setCptTrans(cptContract.getCptAddress());
		return success(contractAddrResp);
	}
	
	@GetMapping("/upgradeDid")
	public ResponseEntity<ResultData<ContractUpgradeResp>> upgradeDidContract() throws Exception{
		ContractUpgradeResp resp = new ContractUpgradeResp();
		resp.setSuccess(DeployContract.upgradeDidContract());
		return success(resp);
	}
	
	@GetMapping("/upgradeIssuer")
	public ResponseEntity<ResultData<ContractUpgradeResp>> upgradeIssuerContract() throws Exception{
		ContractUpgradeResp resp = new ContractUpgradeResp();
		resp.setSuccess(DeployContract.upgradeIssuerContract());
		return success(resp);
	}
	
	@GetMapping("/upgradeCpt")
	public ResponseEntity<ResultData<ContractUpgradeResp>> upgradeCptContract() throws Exception{
		ContractUpgradeResp resp = new ContractUpgradeResp();
		resp.setSuccess(DeployContract.upgradeCptContract());
		return success(resp);
	}
	
	@PostMapping("/putDoc")
	 public ResponseEntity<ResultData<Boolean>> putDidDocument(@RequestBody RequestParam<DidDocSotreReq> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/putDoc],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		Document document = vo.getData().getDidDoc();
		String validMsg = this.validDidDocument(document);
		if(validMsg != null) {
			return error(ErrorCode.PARAMETER_IS_EMPTY.getCode(),validMsg, Boolean.class);
		}
				
		DidDocument didDocument = this.cloneDidDocument(document);
		
		Boolean verifyResult = didClient.verifyDidDocument(didDocument, didDocument.getAuthentication());
		if(!verifyResult) {
			return error(ErrorCode.SIGNATURE_VERIFICATION_FAIL.getCode(),ErrorCode.SIGNATURE_VERIFICATION_FAIL.getEnMessage(), Boolean.class);
		}
		
		Boolean result = didClient.storeDidDocumentOnChain(didDocument, didDocument.getAuthentication());
		if(result) {
			return success(true);
		}else {
			return error(ErrorCode.STORE_DID_DOC_FAILED.getCode(),ErrorCode.STORE_DID_DOC_FAILED.getEnMessage(), Boolean.class);
		}
	 }
	
	
	@PostMapping("/verifyDoc")
	public ResponseEntity<ResultData<Boolean>> verifyDidDocument(@RequestBody RequestParam<VerifyDocumentReq> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/verifyDidDocument],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		Boolean didDocument = didClient.verifyDidDocument(vo.getData().getDidDoc(),vo.getData().getDidDoc().getAuthentication());
		return success(didDocument);
	}
	
	@PostMapping("/verifyDIdSign")
	public ResponseEntity<ResultData<Boolean>> verifyDidSign(@RequestBody RequestParam<VerifyDidReq> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/verifyDId],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		if(vo.getData().getDid() == null || vo.getData().getDid().trim().isEmpty()) {
			return error(ErrorCode.PARAMETER_IS_EMPTY.getCode(),"did "+ErrorCode.PARAMETER_IS_EMPTY.getEnMessage(), Boolean.class);
		}
		
		if(vo.getData().getDidSign() == null || vo.getData().getDidSign().trim().isEmpty()) {
			return error(ErrorCode.PARAMETER_IS_EMPTY.getCode(),"did sign "+ErrorCode.PARAMETER_IS_EMPTY.getEnMessage(), Boolean.class);
		}
		
		String publicKey = this.getPublicKeyByDid(vo.getData().getDid());
		if(publicKey == null || publicKey.trim().isEmpty()) {
			return error(ErrorCode.DID_NOT_EXIST.getCode(), ErrorCode.DID_NOT_EXIST.getEnMessage(), Boolean.class);
		}
		
		try {
			boolean verifyDidSign = ECDSAUtils.verify(vo.getData().getDid(), publicKey, vo.getData().getDidSign());
			return success(verifyDidSign);
		} catch (Exception e) {
			return error(ErrorCode.SIGNATURE_VERIFICATION_FAIL.getCode(), ErrorCode.SIGNATURE_VERIFICATION_FAIL.getEnMessage(), Boolean.class);
		}
	}
	
	
	@PostMapping("/getDoc")
	public ResponseEntity<ResultData<DidDocument>> getDidDocument(@RequestBody RequestParam<DidDocumentReq> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/getDidDocument],request param = ["+JSONObject.toJSONString(vo)+"]");
		}

		DidDocument didDocument = didClient.getDidDocument(vo.getData().getDid());
		return success(didDocument);
	}
	
	
	@PostMapping("/resetDidAuth")
	public ResponseEntity<ResultData<KeyPair>> resetDidAuth(@RequestBody RequestParam<RestDocAuth> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/resetDidAuth],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		String publicKey = this.getRecoveryPublicKeyByDid(vo.getDid());
		boolean verify = com.reddate.did.sdk.util.Signatures.get().setInfo(vo.getProjectId(),vo.getDid())
				.add("document", vo.getData().getDidDoc())
				.add("authPubKeySign", vo.getData().getAuthPubKeySign())
				.verify(publicKey, vo.getSign());
		
		if(!verify) {
			return error(ErrorCode.SIGNATURE_VERIFICATION_FAIL.getCode(),ErrorCode.SIGNATURE_VERIFICATION_FAIL.getEnMessage(), KeyPair.class);
		}	
		
		Document document = vo.getData().getDidDoc();
		String validMsg = this.validDidDocument(document);
		if(validMsg != null) {
			return error(ErrorCode.PARAMETER_IS_EMPTY.getCode(),validMsg, KeyPair.class);
		}
		
		DidDocument didDocument = this.cloneDidDocument(document);
		ResetDid restDid = new ResetDid();
		restDid.setDidDocument(didDocument);
		PublicKeyInfo authPublicKeyInfo = new PublicKeyInfo();
		authPublicKeyInfo.setPublicKey(document.getRecovery().getPublicKey());
		authPublicKeyInfo.setPublicKeySign(vo.getData().getAuthPubKeySign());
		authPublicKeyInfo.setType(document.getAuthentication().getType());
		restDid.setAuthPublicKeyInfo(authPublicKeyInfo);
		
		KeyPair keyPair = didClient.resetDidAuth(restDid);
		
		return success(keyPair);
	}
	

	@PostMapping("/registerAuthIssuer")
	public ResponseEntity<ResultData<Boolean>> registerAuthIssuer(@RequestBody RequestParam<RegisterAuthorityIssuerWrapper> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/registerAuthIssuer],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		String publicKey = this.getPublicKeyByDid(vo.getDid());
		
		boolean verify = com.reddate.did.sdk.util.Signatures.get().setInfo(vo.getProjectId(),vo.getData().getDid())
				.add("name", vo.getData().getName())
				.add("publicKeySign",vo.getData().getPublicKeySign())
				.verify(publicKey, vo.getSign());
		if(!verify) {
			return error(ErrorCode.SIGNATURE_VERIFICATION_FAIL.getCode(),ErrorCode.SIGNATURE_VERIFICATION_FAIL.getEnMessage(), Boolean.class);
		}		
		
		RegisterAuthorityIssuer issuer = new RegisterAuthorityIssuer();
		issuer.setDid(vo.getData().getDid());
		issuer.setName(vo.getData().getName());
		PublicKeyInfo publicKeyInfo = new PublicKeyInfo();
		publicKeyInfo.setPublicKey(publicKey);
		publicKeyInfo.setPublicKeySign(vo.getData().getPublicKeySign());
		issuer.setPublicKeyInfo(publicKeyInfo);
		Boolean registerResult= didClient.registerAuthIssuer(issuer);
		
		return success(registerResult);
	}
	
	
	@PostMapping("/queryAuthIssuerList")
	public ResponseEntity<ResultData<Pages<AuthorityIssuer>>> queryAuthIssuerList(@RequestBody RequestParam<AuthIssuerListWrapper> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/queryAuthIssuerList],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		if(vo.getData().getPage() == null) {
			return pageError(ErrorCode.PARAMETER_IS_EMPTY.getCode(),"page is empty", AuthorityIssuer.class);
		}
		
		if(vo.getData().getSize() == null) {
			return pageError(ErrorCode.PARAMETER_IS_EMPTY.getCode(),"page size is empty", AuthorityIssuer.class);
		}
		
		Pages<AuthorityIssuer> authIssueList  = didClient.queryAuthIssuerList(vo.getData().getPage(), vo.getData().getSize(), vo.getData().getDid());
	
		if(vo.getData().getDid() != null && !vo.getData().getDid().trim().isEmpty()) {
			if(authIssueList == null || authIssueList.getResult() == null || authIssueList.getResult().isEmpty()) {
				Pages<AuthorityIssuer> page0 = new Pages<>();
				page0.setPage(vo.getData().getPage());
				page0.setSize(vo.getData().getSize());
				page0.setTotalNum(0);
				page0.setTotalPage(0);
				page0.setResult(new ArrayList<AuthorityIssuer>());
				return success(page0);
			}
			
			Pages<AuthorityIssuer> page1 = new Pages<>();
			page1.setPage(vo.getData().getPage());
			page1.setSize(vo.getData().getSize());
			page1.setTotalNum(authIssueList.getResult().size());
			int totalPage = page1.getTotalNum() / vo.getData().getSize();
			if(page1.getTotalNum() * vo.getData().getSize() != page1.getTotalNum()) {
				totalPage = totalPage + 1;
			}		
			page1.setTotalPage(totalPage);		
			int start = (vo.getData().getPage() - 1) * vo.getData().getSize();		
			if(start + vo.getData().getSize() >= authIssueList.getResult().size()) {
				if(start >= authIssueList.getResult().size()) {
					page1.setResult(new ArrayList<AuthorityIssuer>());
				}else {
					List<AuthorityIssuer> tmplsit1 = authIssueList.getResult().subList(start, authIssueList.getResult().size());
					page1.setResult(tmplsit1);
				}
			}else {
				List<AuthorityIssuer> tmplsit1 = authIssueList.getResult().subList(start, start + vo.getData().getSize());
				page1.setResult(tmplsit1);
			}
			return success(page1);
		}
		
		return success(authIssueList);
	}
	
	
	@PostMapping("/registerCpt")
	public ResponseEntity<ResultData<CptBaseInfo>> registerCpt(@RequestBody RequestParam<RegisterCptWrapper> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/registerCpt],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		String publicKey = this.getPublicKeyByDid(vo.getDid());
		
		boolean verify = Signatures.get().setInfo(vo.getProjectId(),vo.getDid())
				.add("cptId", vo.getData().getCptId())
				.add("did", vo.getData().getDid())
				.add("jsonSchema",vo.getData().getCptJsonSchema())
				.add("title",vo.getData().getTitle())
				.add("description",vo.getData().getDescription())
				.add("type",vo.getData().getType())
				.add("proof",vo.getData().getProof())
				.add("created",vo.getData().getCreate())
				.add("updated",vo.getData().getUpdate())
				.verify(publicKey, vo.getSign());
		if(!verify) {
			return error(ErrorCode.SIGNATURE_VERIFICATION_FAIL.getCode(),ErrorCode.SIGNATURE_VERIFICATION_FAIL.getEnMessage(), CptBaseInfo.class);
		}
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setCptId(vo.getData().getCptId());
		registerCpt.setDid(vo.getData().getDid());
		Map<String, JsonSchema> cptJsonSchema  = new TreeMap<>();
		if(vo.getData().getCptJsonSchema() != null) {
			vo.getData().getCptJsonSchema().forEach((key,val) -> {
				JsonSchema jsonSchema = new JsonSchema();
				jsonSchema.setDescription(val.getDescription());
				jsonSchema.setRequired(val.getRequired());
				jsonSchema.setType(val.getType());
				cptJsonSchema.put(key, jsonSchema);
			});
		}
		registerCpt.setCptJsonSchema(cptJsonSchema);
		registerCpt.setTitle(vo.getData().getTitle());
		registerCpt.setDescription(vo.getData().getDescription());
		registerCpt.setType(vo.getData().getType());
		registerCpt.setCreate(vo.getData().getCreate());
		registerCpt.setUpdate(vo.getData().getUpdate());
		Proof proof = new Proof();
		proof.setCreator(vo.getData().getProof().getCreator());
		proof.setSignatureValue(vo.getData().getProof().getSignatureValue());
		proof.setType(vo.getData().getProof().getType());
		registerCpt.setProof(proof);
		
		CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);;
		
		return success(cptBaseInfo);
	}
	
	
	@PostMapping("/queryCptList")
	public ResponseEntity<ResultData<Pages<CptInfo>>> queryCptListByDid(@RequestBody RequestParam<QueryCptListWrapper> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/queryCptList],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		if(vo.getData().getPage() == null) {
			return pageError(ErrorCode.PARAMETER_IS_EMPTY.getCode(),"page is empty", CptInfo.class);
		}
		if(vo.getData().getSize() == null) {
			return pageError(ErrorCode.PARAMETER_IS_EMPTY.getCode(),"page size is empty", CptInfo.class);
		}
		
		Pages<CptInfo> cptPages = didClient.queryCptListByDid(vo.getData().getPage(), vo.getData().getSize(), vo.getData().getDid());
	
		return success(cptPages);
	}
	
	
	@PostMapping("/queryCptById")
	public ResponseEntity<ResultData<CptInfo>> queryCptById(@RequestBody RequestParam<QueryCptByIdWrapper> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/queryCptById],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		if(vo.getData().getCptId() == null) {
			return error(ErrorCode.PARAMETER_IS_EMPTY.getCode(),"cpt id is empty", CptInfo.class);
		}
		
		CptInfo cptInfo = didClient.queryCptById(vo.getData().getCptId());
		
		return success(cptInfo);
	}
	
	
	@PostMapping("/updateCpt")
	public ResponseEntity<ResultData<CptBaseInfo>> updateCpt(@RequestBody RequestParam<RegisterCptWrapper> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/updateCpt],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		String publicKey = this.getPublicKeyByDid(vo.getDid());
		
		boolean verify = Signatures.get().setInfo(vo.getProjectId(),vo.getDid())
				.add("cptId", vo.getData().getCptId())
				.add("did", vo.getData().getDid())
				.add("jsonSchema",vo.getData().getCptJsonSchema())
				.add("title",vo.getData().getTitle())
				.add("description",vo.getData().getDescription())
				.add("type",vo.getData().getType())
				.add("proof",vo.getData().getProof())
				.add("created",vo.getData().getCreate())
				.add("updated",vo.getData().getUpdate())
				.verify(publicKey, vo.getSign());
		if(!verify) {
			return error(ErrorCode.SIGNATURE_VERIFICATION_FAIL.getCode(),ErrorCode.SIGNATURE_VERIFICATION_FAIL.getEnMessage(), CptBaseInfo.class);
		}
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setCptId(vo.getData().getCptId());
		registerCpt.setDid(vo.getData().getDid());
		Map<String, JsonSchema> cptJsonSchema  = new TreeMap<>();
		if(vo.getData().getCptJsonSchema() != null) {
			vo.getData().getCptJsonSchema().forEach((key,val) -> {
				JsonSchema jsonSchema = new JsonSchema();
				jsonSchema.setDescription(val.getDescription());
				jsonSchema.setRequired(val.getRequired());
				jsonSchema.setType(val.getType());
				cptJsonSchema.put(key, jsonSchema);
			});
		}
		registerCpt.setCptJsonSchema(cptJsonSchema);
		registerCpt.setTitle(vo.getData().getTitle());
		registerCpt.setDescription(vo.getData().getDescription());
		registerCpt.setType(vo.getData().getType());
		registerCpt.setUpdate(vo.getData().getUpdate());
		Proof proof = new Proof();
		proof.setCreator(vo.getData().getProof().getCreator());
		proof.setSignatureValue(vo.getData().getProof().getSignatureValue());
		proof.setType(vo.getData().getProof().getType());
		registerCpt.setProof(proof);
		
		CptBaseInfo cptBaseInfo = didClient.updateCpt(registerCpt);
		
		return success(cptBaseInfo);
	}
	

	@PostMapping("/createCredential")
	public ResponseEntity<ResultData<CredentialWrapper>> createCredential(@RequestBody RequestParam<CreateCredentialReq> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/createCredential],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		String publicKey = this.getPublicKeyByDid(vo.getDid());
		
		boolean verify = Signatures.get().setInfo(vo.getProjectId(),vo.getDid())
				.add("cptId", vo.getData().getCptId())
				.add("userDid",vo.getData().getUserDid())
				.add("expirationDate",vo.getData().getExpirationDate())
				.add("claim",vo.getData().getClaim())
				.add("type",vo.getData().getType())
				.add("shortDesc", vo.getData().getShortDesc())
				.add("longDesc", vo.getData().getLongDesc())
				.verify(publicKey, vo.getSign());
		if(!verify) {
			return error(ErrorCode.SIGNATURE_VERIFICATION_FAIL.getCode(),ErrorCode.SIGNATURE_VERIFICATION_FAIL.getEnMessage(), CredentialWrapper.class);
		}
		
		CreateCredential createCredential = new CreateCredential();
		createCredential.setCptId(vo.getData().getCptId());
		createCredential.setIssuerDid(vo.getData().getIssuerDid());
		createCredential.setUserDid(vo.getData().getUserDid());
		createCredential.setExpirationDate(vo.getData().getExpirationDate());
		createCredential.setClaim(vo.getData().getClaim());
		createCredential.setType(vo.getData().getType());
		createCredential.setShortDesc(vo.getData().getShortDesc());
		createCredential.setLongDesc(vo.getData().getLongDesc());
		CredentialWrapper credential = didClient.createCredential(createCredential);
		return success(credential);
		
	}
	
	
	@PostMapping("/verifyCredential")
	public ResponseEntity<ResultData<Boolean>> verifyCredential(@RequestBody RequestParam<VerifyCredentialReq> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/verifyCredential],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		Credential createCredential = new Credential();
		BeanUtils.copyProperties(vo.getData().getCredentialWrapper(), createCredential);
		
		PublicKey publicKey = new PublicKey();
		publicKey.setType(vo.getData().getPublicKey().getType());
		publicKey.setPublicKey(vo.getData().getPublicKey().getPublicKey());
		
		Boolean credential = didClient.verifyCredential(createCredential,publicKey);
		return success(credential);
	}
	
	
	@PostMapping("/revokeCredential")
	public ResponseEntity<ResultData<Boolean>> revokeCredential(@RequestBody RequestParam<RevokCredentialReq> vo){

		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/revokeCredential],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		String publicKey = this.getPublicKeyByDid(vo.getDid());
		boolean verify = Signatures.get().setInfo(vo.getProjectId(),vo.getDid())
				.add("credId", vo.getData().getCredId())
				.add("cptId",vo.getData().getCptId())
				.add("did",vo.getData().getDid())
				.add("publicKeySign",vo.getData().getPublicKeySign())
				.add("revokeDate",vo.getData().getRevokeDate())
				.add("revokeSign",vo.getData().getRevokeSign())
				.verify(publicKey, vo.getSign());
		if(!verify) {
			return error(ErrorCode.SIGNATURE_VERIFICATION_FAIL.getCode(),ErrorCode.SIGNATURE_VERIFICATION_FAIL.getEnMessage(), Boolean.class);
		}
		if(!new BCryptPasswordEncoder().matches(vo.getData().getCptId().toString(), vo.getData().getCredId())){
			return error(ErrorCode.CRED_ID_NOT_MATCH_CPT_ID.getCode(),ErrorCode.CRED_ID_NOT_MATCH_CPT_ID.getEnMessage(), Boolean.class);
		}

		RevokeCredential cred =  new RevokeCredential();
		cred.setCredId(vo.getData().getCredId());
		cred.setCptId(vo.getData().getCptId());
		cred.setDid(vo.getData().getDid());
		cred.setRovekeDate(vo.getData().getRevokeDate());
		Proof roof = new Proof();
		roof.setCreator(vo.getData().getDid());
		roof.setSignatureValue(vo.getData().getRevokeSign());
		cred.setProof(roof);
		
		Boolean revokeResult = didClient.revokeCredential(cred);
		return success(revokeResult);
	}
	
	
	@PostMapping("/getRevokedCredList")
	public ResponseEntity<ResultData<Pages<BaseCredential>>> getRevokedCredList(@RequestBody RequestParam<QueryCredentialWrapper> vo){
		if(logger.isDebugEnabled()) {
			logger.debug("path=[/did/getRevokedCredList],request param = ["+JSONObject.toJSONString(vo)+"]");
		}
		
		Pages<BaseCredential> revokePages = didClient.getRevokedCredList(vo.getData().getPage(), vo.getData().getSize(), vo.getData().getDid());
		return success(revokePages);
	}
	

	private String getPublicKeyByDid(String did) {
		DidDocument didDocument = didClient.getDidDocument(did);
		if(didDocument == null) {
			return null;
		}
		return didDocument.getAuthentication().getPublicKey();
	}

	private String getRecoveryPublicKeyByDid(String did) {
		DidDocument didDocument = didClient.getDidDocument(did);
		if(didDocument == null) {
			return null;
		}
		return didDocument.getRecovery().getPublicKey();
	}
	
	private String validDidDocument(Document document) {
		if(document == null) {
			return "did document is empty";
		}
		if(document.getDid() == null || document.getDid().trim().isEmpty()) {
			return "did is empty";
		}
		if(document.getVersion() == null || document.getVersion().trim().isEmpty()) {
			return "version is empty";
		}
		if(document.getCreated() == null || document.getCreated().trim().isEmpty()) {
			return "create time is empty";
		}
		if(document.getUpdated() == null || document.getUpdated().trim().isEmpty()) {
			return "update time is empty";
		}
		if(document.getAuthentication() == null) {
			return "authentication key is empty";
		}
		if(document.getAuthentication().getPublicKey() == null || document.getAuthentication().getPublicKey().trim().isEmpty()) {
			return "authentication public key is empty";
		}
		if(document.getAuthentication().getType() == null || document.getAuthentication().getType().trim().isEmpty()) {
			return "authentication public key type is empty";
		}
		
		if(document.getRecovery() == null) {
			return "recovery key is empty";
		}
		if(document.getRecovery().getPublicKey() == null || document.getRecovery().getPublicKey().trim().isEmpty()) {
			return "recovery public key is empty";
		}
		if(document.getRecovery().getType() == null || document.getRecovery().getType().trim().isEmpty()) {
			return "recovery public key type is empty";
		}
		
		if(document.getProof() == null) {
			return "proof is empty";
		}
		
		if(document.getProof().getCreator() == null || document.getProof().getCreator().trim().isEmpty()) {
			return "proof creator is empty";
		}
		
		if(document.getProof().getSignatureValue() == null || document.getProof().getSignatureValue().trim().isEmpty()) {
			return "proof signal value is empty";
		}
		
		if(document.getProof().getType() == null || document.getProof().getType().trim().isEmpty()) {
			return "proof type is empty";
		}
		
		return null;
	}
	
	private DidDocument cloneDidDocument(Document document) {
		DidDocument didDocument = new DidDocument();
		didDocument.setDid(document.getDid());
		didDocument.setVersion(document.getVersion());
		didDocument.setCreated(document.getCreated());
		didDocument.setUpdated(document.getUpdated());
		PublicKey authentication = new PublicKey();
		authentication.setPublicKey(document.getAuthentication().getPublicKey());
		authentication.setType(document.getAuthentication().getType());
		didDocument.setAuthentication(authentication);
		PublicKey recovery = new PublicKey();
		recovery.setPublicKey(document.getRecovery().getPublicKey());
		recovery.setType(document.getRecovery().getType());
		didDocument.setRecovery(recovery);
		Proof proof = new Proof();
		proof.setCreator(document.getProof().getCreator());
		proof.setSignatureValue(document.getProof().getSignatureValue());
		proof.setType(document.getProof().getType());
		didDocument.setProof(proof);
		
		return didDocument;
	}
	
	@PostMapping("/getBlockInfo")
	public ResponseEntity<ResultData<BlockInfoResp>> getBlockInfo(@RequestBody RequestParam<DidDocSotreReq> vo){
		BigInteger blockNumber = didClient.getBlockNumber();
		Integer groupId = didClient.getGroupId();
		BlockInfoResp blockInfoResp = new BlockInfoResp();
		blockInfoResp.setBlockNumber(blockNumber.toString());
		blockInfoResp.setGroupId(String.valueOf(groupId));
		return success(blockInfoResp);
	}
	
	
}
