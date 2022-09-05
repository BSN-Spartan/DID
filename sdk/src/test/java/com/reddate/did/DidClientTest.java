package com.reddate.did;

import com.alibaba.fastjson.JSONObject;
import com.reddate.did.sdk.DidClient;
import com.reddate.did.sdk.param.req.*;
import com.reddate.did.sdk.param.resp.DidDataWrapper;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.request.CptInfo;
import com.reddate.did.sdk.protocol.request.CredentialWrapper;
import com.reddate.did.sdk.protocol.request.JsonSchema;
import com.reddate.did.sdk.protocol.response.AuthorityIssuer;
import com.reddate.did.sdk.protocol.response.BaseCredential;
import com.reddate.did.sdk.protocol.response.CptBaseInfo;
import com.reddate.did.sdk.protocol.response.Pages;
import com.reddate.did.sdk.util.ECDSAUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DidClientTest extends DidClientTestBase {

	
	@Test   
	public void generateDidTest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(false);
		System.out.println("=================="+JSONObject.toJSONString(didDataWrapper));
		assertNotNull(didDataWrapper);
		assertNotNull(didDataWrapper.getDid());
		assertNotNull(didDataWrapper.getDocument());
		assertNotNull(didDataWrapper.getAuthKeyInfo());
		assertNotNull(didDataWrapper.getRecyKeyInfo());
	} 
	
	@Test   
	public void generateDidtest2() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		System.out.println("=================="+JSONObject.toJSONString(didDataWrapper));
		assertNotNull(didDataWrapper);
		assertNotNull(didDataWrapper.getDid());
		assertNotNull(didDataWrapper.getDocument());
		assertNotNull(didDataWrapper.getAuthKeyInfo());
		assertNotNull(didDataWrapper.getRecyKeyInfo());
	} 
	
	@Test   
	public void verifyDidDocumentTest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(false);
		
		DidDocument didDocument = new DidDocument();
		didDocument.setDid(didDataWrapper.getDocument().getDid());
		didDocument.setVersion(didDataWrapper.getDocument().getVersion());
		didDocument.setCreated(didDataWrapper.getDocument().getCreated());
		didDocument.setUpdated(didDataWrapper.getDocument().getUpdated());
		PublicKey authentication = new PublicKey();
		authentication.setPublicKey(didDataWrapper.getDocument().getAuthentication().getPublicKey());
		authentication.setType(didDataWrapper.getDocument().getAuthentication().getType());
		didDocument.setAuthentication(authentication);
		PublicKey recovery = new PublicKey();
		recovery.setPublicKey(didDataWrapper.getDocument().getRecovery().getPublicKey());
		recovery.setType(didDataWrapper.getDocument().getRecovery().getType());
		didDocument.setRecovery(recovery);
		Proof proof = new Proof();
		proof.setCreator(didDataWrapper.getDocument().getProof().getCreator());
		proof.setSignatureValue(didDataWrapper.getDocument().getProof().getSignatureValue());
		proof.setType(didDataWrapper.getDocument().getProof().getType());
		didDocument.setProof(proof);
		
		
		Boolean verifyResult = didClient.verifyDidDocument(didDocument);
		
		assertTrue(verifyResult);
	} 
	
	@Test   
	public void storeDidDocumentOnChainTest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(false);
		
		DidDocument didDocument = new DidDocument();
		didDocument.setDid(didDataWrapper.getDocument().getDid());
		didDocument.setVersion(didDataWrapper.getDocument().getVersion());
		didDocument.setCreated(didDataWrapper.getDocument().getCreated());
		didDocument.setUpdated(didDataWrapper.getDocument().getUpdated());
		PublicKey authentication = new PublicKey();
		authentication.setPublicKey(didDataWrapper.getDocument().getAuthentication().getPublicKey());
		authentication.setType(didDataWrapper.getDocument().getAuthentication().getType());
		didDocument.setAuthentication(authentication);
		PublicKey recovery = new PublicKey();
		recovery.setPublicKey(didDataWrapper.getDocument().getRecovery().getPublicKey());
		recovery.setType(didDataWrapper.getDocument().getRecovery().getType());
		didDocument.setRecovery(recovery);
		Proof proof = new Proof();
		proof.setCreator(didDataWrapper.getDocument().getProof().getCreator());
		proof.setSignatureValue(didDataWrapper.getDocument().getProof().getSignatureValue());
		proof.setType(didDataWrapper.getDocument().getProof().getType());
		didDocument.setProof(proof);
		
		
		Boolean stortResult = didClient.storeDidDocumentOnChain(didDocument);
		
		assertTrue(stortResult);
	} 
	
	@Test   
	public void getDidDocument() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		DidDocument didDocument = didClient.getDidDocument(didDataWrapper.getDid());
		assertNotNull(didDocument);
		assertNotNull(didDocument.getDid());
		assertNotNull(didDocument.getVersion());
		assertNotNull(didDocument.getCreated());
		assertNotNull(didDocument.getAuthentication());
		assertNotNull(didDocument.getRecovery());
		assertNotNull(didDocument.getProof());
	} 
	
	
	@Test   
	public void resetDidAuthTest() throws InterruptedException {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		ResetDidAuth restDidAuth = new ResetDidAuth();
		restDidAuth.setDid(didDataWrapper.getDid());
		ResetDidAuthKey resetDidAuthKey = new ResetDidAuthKey();
		resetDidAuthKey.setPrivateKey(didDataWrapper.getRecyKeyInfo().getPrivateKey());
		resetDidAuthKey.setPublicKey(didDataWrapper.getRecyKeyInfo().getPublicKey());
		resetDidAuthKey.setType(didDataWrapper.getRecyKeyInfo().getType());
		restDidAuth.setRecoveryKey(resetDidAuthKey);
		
		Thread.currentThread().sleep(2000);
		
		KeyPair newKeyPair = didClient.resetDidAuth(restDidAuth);
		
		assertNotNull(newKeyPair);
		assertNotNull(newKeyPair.getPrivateKey());
		assertNotNull(newKeyPair.getPublicKey());
		assertNotNull(newKeyPair.getType());
	} 
	
	@Test   
	public void resetDidAuthTest2() throws Exception {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		ResetDidAuth restDidAuth = new ResetDidAuth();
		restDidAuth.setDid(didDataWrapper.getDid());
		restDidAuth.setPrimaryKeyPair(ECDSAUtils.createKey());
		ResetDidAuthKey resetDidAuthKey = new ResetDidAuthKey();
		resetDidAuthKey.setPrivateKey(didDataWrapper.getRecyKeyInfo().getPrivateKey());
		resetDidAuthKey.setPublicKey(didDataWrapper.getRecyKeyInfo().getPublicKey());
		resetDidAuthKey.setType(didDataWrapper.getRecyKeyInfo().getType());
		restDidAuth.setRecoveryKey(resetDidAuthKey);
		
		Thread.currentThread().sleep(2000);
		
		KeyPair newKeyPair = didClient.resetDidAuth(restDidAuth);
		
		assertNotNull(newKeyPair);
		assertNotNull(newKeyPair.getPrivateKey());
		assertNotNull(newKeyPair.getPublicKey());
		assertNotNull(newKeyPair.getType());
	} 
	
	
	@Test   
	public void registerAuthIssuerTest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		boolean registerresult = didClient.registerAuthIssuer(register);
		assertTrue(registerresult);
	} 
	
	@Test   
	public void queryAuthIssuerListTetst() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		try {
			boolean registerresult = didClient.registerAuthIssuer(register);
			assertTrue(registerresult);
		}catch (Exception e){
			assertEquals("query auth issue list failed:server process failed",e.getMessage());
		}

		
		AuthIssuer queryIssuer = new AuthIssuer();
		queryIssuer.setPage(1);
		queryIssuer.setSize(10);
		queryIssuer.setDid(didDataWrapper.getDid());
		Pages<AuthorityIssuer> issuerList = didClient.queryAuthIssuerList(queryIssuer);
		assertNotNull(issuerList);
		assertTrue(issuerList.getResult().size() > 0);

	} 
	
	
	@Test   
	public void queryAuthIssuerListTetst2() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		try {
			boolean registerresult = didClient.registerAuthIssuer(register);
			assertTrue(registerresult);
		}catch (Exception e){
			assertEquals("query auth issue list failed:server process failed",e.getMessage());
		}

		
		AuthIssuer queryIssuer = new AuthIssuer();
		queryIssuer.setPage(1);
		queryIssuer.setSize(10);
		Pages<AuthorityIssuer> issuerList = didClient.queryAuthIssuerList(queryIssuer);
		assertNotNull(issuerList);
		assertTrue(issuerList.getResult().size() > 0);
	} 
	
	
	@Test   
	public void registerCpt() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		boolean registerresult = didClient.registerAuthIssuer(register);
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setDid(didDataWrapper.getDid());
		registerCpt.setTitle("test cpt template 01");
		registerCpt.setDescription("test cpt tempalte 01 long descriptuion");
		registerCpt.setType("Proof");
		registerCpt.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		Map<String, JsonSchema> cptJsonSchemas = new HashMap<>();
		JsonSchema jsonSchema1 = new JsonSchema();
		jsonSchema1.setDescription("user name");
		jsonSchema1.setRequired(true);
		jsonSchema1.setType("String");
		cptJsonSchemas.put("name", jsonSchema1);
		JsonSchema jsonSchema2 = new JsonSchema();
		jsonSchema2.setDescription("user sex");
		jsonSchema2.setRequired(false);
		jsonSchema2.setType("String");
		cptJsonSchemas.put("sex", jsonSchema2);
		JsonSchema jsonSchema3 = new JsonSchema();
		jsonSchema3.setDescription("user age");
		jsonSchema3.setRequired(true);
		jsonSchema3.setType("Number");
		cptJsonSchemas.put("age", jsonSchema3);
		registerCpt.setCptJsonSchema(cptJsonSchemas);
		CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
		assertNotNull(cptBaseInfo);
	} 
	
	
	@Test   
	public void queryCptListByDid() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		boolean registerresult = didClient.registerAuthIssuer(register);
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setDid(didDataWrapper.getDid());
		registerCpt.setTitle("test cpt template 01");
		registerCpt.setDescription("test cpt tempalte 01 long descriptuion");
		registerCpt.setType("Proof");
		registerCpt.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		Map<String, JsonSchema> cptJsonSchemas = new HashMap<>();
		JsonSchema jsonSchema1 = new JsonSchema();
		jsonSchema1.setDescription("user name");
		jsonSchema1.setRequired(true);
		jsonSchema1.setType("String");
		cptJsonSchemas.put("name", jsonSchema1);
		JsonSchema jsonSchema2 = new JsonSchema();
		jsonSchema2.setDescription("user sex");
		jsonSchema2.setRequired(false);
		jsonSchema2.setType("String");
		cptJsonSchemas.put("sex", jsonSchema2);
		JsonSchema jsonSchema3 = new JsonSchema();
		jsonSchema3.setDescription("user age");
		jsonSchema3.setRequired(true);
		jsonSchema3.setType("Number");
		cptJsonSchemas.put("age", jsonSchema3);
		registerCpt.setCptJsonSchema(cptJsonSchemas);
		CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
		
		QueryCpt query = new QueryCpt();
		query.setDid(didDataWrapper.getDid());
		query.setPage(1);
		query.setSize(10);
		try {
			Pages<CptInfo> pages = didClient.queryCptListByDid(query);
			assertNotNull(pages);
		}catch (Exception e){
			assertEquals("query cptList failed:query cpt list by did failed:server process failed",e.getMessage());
		}

	} 
	
	
	@Test   
	public void queryCptByIdTest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		boolean registerresult = didClient.registerAuthIssuer(register);
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setDid(didDataWrapper.getDid());
		registerCpt.setTitle("test cpt template 01");
		registerCpt.setDescription("test cpt tempalte 01 long descriptuion");
		registerCpt.setType("Proof");
		registerCpt.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		Map<String, JsonSchema> cptJsonSchemas = new HashMap<>();
		JsonSchema jsonSchema1 = new JsonSchema();
		jsonSchema1.setDescription("user name");
		jsonSchema1.setRequired(true);
		jsonSchema1.setType("String");
		cptJsonSchemas.put("name", jsonSchema1);
		JsonSchema jsonSchema2 = new JsonSchema();
		jsonSchema2.setDescription("user sex");
		jsonSchema2.setRequired(false);
		jsonSchema2.setType("String");
		cptJsonSchemas.put("sex", jsonSchema2);
		JsonSchema jsonSchema3 = new JsonSchema();
		jsonSchema3.setDescription("user age");
		jsonSchema3.setRequired(true);
		jsonSchema3.setType("Number");
		cptJsonSchemas.put("age", jsonSchema3);
		registerCpt.setCptJsonSchema(cptJsonSchemas);
		CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
		
		
		CptInfo cptInfo = didClient.queryCptById(cptBaseInfo.getCptId());
		assertNotNull(cptInfo.getCptId());
		assertNotNull(cptInfo.getCptVersion());
		assertNotNull(cptInfo.getCptJsonSchema());
		assertNotNull(cptInfo.getCreate());
		assertNotNull(cptInfo.getUpdate());
		assertNotNull(cptInfo.getProof());
		assertNotNull(cptInfo.getTitle());
		assertNotNull(cptInfo.getDescription());
	} 
	
	
	@Test   
	public void updateCptTest() throws InterruptedException {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		boolean registerresult = didClient.registerAuthIssuer(register);
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setDid(didDataWrapper.getDid());
		registerCpt.setTitle("test cpt template 01");
		registerCpt.setDescription("test cpt tempalte 01 long descriptuion");
		registerCpt.setType("Proof");
		registerCpt.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		Map<String, JsonSchema> cptJsonSchemas = new HashMap<>();
		JsonSchema jsonSchema1 = new JsonSchema();
		jsonSchema1.setDescription("user name");
		jsonSchema1.setRequired(true);
		jsonSchema1.setType("String");
		cptJsonSchemas.put("name", jsonSchema1);
		JsonSchema jsonSchema2 = new JsonSchema();
		jsonSchema2.setDescription("user sex");
		jsonSchema2.setRequired(false);
		jsonSchema2.setType("String");
		cptJsonSchemas.put("sex", jsonSchema2);
		JsonSchema jsonSchema3 = new JsonSchema();
		jsonSchema3.setDescription("user age");
		jsonSchema3.setRequired(true);
		jsonSchema3.setType("Number");
		cptJsonSchemas.put("age", jsonSchema3);
		registerCpt.setCptJsonSchema(cptJsonSchemas);
		CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);

		
		Thread.currentThread().sleep(2000);
		
		JsonSchema jsonSchema4 = new JsonSchema();
		jsonSchema4.setDescription("user address");
		jsonSchema4.setRequired(true);
		jsonSchema4.setType("String");
		cptJsonSchemas.put("address", jsonSchema4);
		registerCpt.setCptId(cptBaseInfo.getCptId());
		
		CptBaseInfo cptBaseInfo2 = didClient.updateCpt(registerCpt);
		assertNotNull(cptBaseInfo2);
		assertNotNull(cptBaseInfo2.getCptId());
		assertNotNull(cptBaseInfo2.getCptVersion());
	} 
	
	
	
	@Test   
	public void createCredentialTest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		DidDataWrapper didDataWrapper2 = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		boolean registerresult = didClient.registerAuthIssuer(register);
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setDid(didDataWrapper.getDid());
		registerCpt.setTitle("test cpt template 01");
		registerCpt.setDescription("test cpt tempalte 01 long descriptuion");
		registerCpt.setType("Proof");
		registerCpt.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		Map<String, JsonSchema> cptJsonSchemas = new HashMap<>();
		JsonSchema jsonSchema1 = new JsonSchema();
		jsonSchema1.setDescription("user name");
		jsonSchema1.setRequired(true);
		jsonSchema1.setType("String");
		cptJsonSchemas.put("name", jsonSchema1);
		JsonSchema jsonSchema2 = new JsonSchema();
		jsonSchema2.setDescription("user sex");
		jsonSchema2.setRequired(false);
		jsonSchema2.setType("String");
		cptJsonSchemas.put("sex", jsonSchema2);
		JsonSchema jsonSchema3 = new JsonSchema();
		jsonSchema3.setDescription("user age");
		jsonSchema3.setRequired(true);
		jsonSchema3.setType("Number");
		cptJsonSchemas.put("age", jsonSchema3);
		registerCpt.setCptJsonSchema(cptJsonSchemas);
		CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
		
		
		CreateCredential createCredential = new CreateCredential();
		createCredential.setCptId(cptBaseInfo.getCptId());
		createCredential.setIssuerDid(didDataWrapper.getDid());
		createCredential.setUserDid(didDataWrapper2.getDid());
        createCredential.setExpirationDate("2025-03-17");
        Map<String,Object> clainMap = new HashMap<>();
        clainMap.put("name", "aa");
        clainMap.put("sex", "female");
        clainMap.put("age", 38);
        createCredential.setClaim(clainMap);
        createCredential.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		
        CredentialWrapper credentialWrapper = didClient.createCredential(createCredential);
		
		assertNotNull(credentialWrapper);
		assertNotNull(credentialWrapper.getClaim());
		assertNotNull(credentialWrapper.getCreated());
		assertNotNull(credentialWrapper.getProof());
		assertNotNull(credentialWrapper.getExpirationDate());
		assertTrue(credentialWrapper.getProof().size() > 0);
	} 
	
	
	@Test   
	public void verifyCredentialTest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		DidDataWrapper didDataWrapper2 = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		boolean registerresult = didClient.registerAuthIssuer(register);
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setDid(didDataWrapper.getDid());
		registerCpt.setTitle("test cpt template 01");
		registerCpt.setDescription("test cpt tempalte 01 long descriptuion");
		registerCpt.setType("Proof");
		registerCpt.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		Map<String, JsonSchema> cptJsonSchemas = new HashMap<>();
		JsonSchema jsonSchema1 = new JsonSchema();
		jsonSchema1.setDescription("user name");
		jsonSchema1.setRequired(true);
		jsonSchema1.setType("String");
		cptJsonSchemas.put("name", jsonSchema1);
		JsonSchema jsonSchema2 = new JsonSchema();
		jsonSchema2.setDescription("user sex");
		jsonSchema2.setRequired(false);
		jsonSchema2.setType("String");
		cptJsonSchemas.put("sex", jsonSchema2);
		JsonSchema jsonSchema3 = new JsonSchema();
		jsonSchema3.setDescription("user age");
		jsonSchema3.setRequired(true);
		jsonSchema3.setType("Number");
		cptJsonSchemas.put("age", jsonSchema3);
		registerCpt.setCptJsonSchema(cptJsonSchemas);
		CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
		
		
		CreateCredential createCredential = new CreateCredential();
		createCredential.setCptId(cptBaseInfo.getCptId());
		createCredential.setIssuerDid(didDataWrapper.getDid());
		createCredential.setUserDid(didDataWrapper2.getDid());
        createCredential.setExpirationDate("2025-03-17");
        Map<String,Object> clainMap = new HashMap<>();
        clainMap.put("name", "aa");
        clainMap.put("sex", "female");
        clainMap.put("age", 38);
        createCredential.setClaim(clainMap);
        createCredential.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		
        CredentialWrapper credentialWrapper = didClient.createCredential(createCredential);
		
        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey(didDataWrapper.getAuthKeyInfo().getPublicKey());
        publicKey1.setType(didDataWrapper.getAuthKeyInfo().getType());
        
        boolean verifyResult = didClient.verifyCredential(credentialWrapper, publicKey1);
        
		assertNotNull(verifyResult);
	} 
	
	
	@Test   
	public void revokeCredentialTest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		DidDataWrapper didDataWrapper2 = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		boolean registerresult = didClient.registerAuthIssuer(register);
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setDid(didDataWrapper.getDid());
		registerCpt.setTitle("test cpt template 01");
		registerCpt.setDescription("test cpt tempalte 01 long descriptuion");
		registerCpt.setType("Proof");
		registerCpt.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		Map<String, JsonSchema> cptJsonSchemas = new HashMap<>();
		JsonSchema jsonSchema1 = new JsonSchema();
		jsonSchema1.setDescription("user name");
		jsonSchema1.setRequired(true);
		jsonSchema1.setType("String");
		cptJsonSchemas.put("name", jsonSchema1);
		JsonSchema jsonSchema2 = new JsonSchema();
		jsonSchema2.setDescription("user sex");
		jsonSchema2.setRequired(false);
		jsonSchema2.setType("String");
		cptJsonSchemas.put("sex", jsonSchema2);
		JsonSchema jsonSchema3 = new JsonSchema();
		jsonSchema3.setDescription("user age");
		jsonSchema3.setRequired(true);
		jsonSchema3.setType("Number");
		cptJsonSchemas.put("age", jsonSchema3);
		registerCpt.setCptJsonSchema(cptJsonSchemas);
		CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
		
		
		CreateCredential createCredential = new CreateCredential();
		createCredential.setCptId(cptBaseInfo.getCptId());
		createCredential.setIssuerDid(didDataWrapper.getDid());
		createCredential.setUserDid(didDataWrapper2.getDid());
        createCredential.setExpirationDate("2025-03-17");
        Map<String,Object> clainMap = new HashMap<>();
        clainMap.put("name", "aa");
        clainMap.put("sex", "female");
        clainMap.put("age", 38);
        createCredential.setClaim(clainMap);
        createCredential.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		
        CredentialWrapper credentialWrapper = didClient.createCredential(createCredential);
		
        
        RevokeCredential cred = new RevokeCredential();
        cred.setCredId(credentialWrapper.getId());
        cred.setCptId(cptBaseInfo.getCptId());
        cred.setDid(didDataWrapper.getDid());
        cred.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
        boolean revokeResult = didClient.revokeCredential(cred);
        
		assertNotNull(revokeResult);
	} 
	
	
	@Test   
	public void getRevokedCredListTest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid(true);
		
		DidDataWrapper didDataWrapper2 = didClient.createDid(true);
		
		RegisterAuthorityIssuer register = new RegisterAuthorityIssuer();
		register.setDid(didDataWrapper.getDid());
		register.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		register.setName("xxxx company");
		boolean registerresult = didClient.registerAuthIssuer(register);
		
		RegisterCpt registerCpt = new RegisterCpt();
		registerCpt.setDid(didDataWrapper.getDid());
		registerCpt.setTitle("test cpt template 01");
		registerCpt.setDescription("test cpt tempalte 01 long descriptuion");
		registerCpt.setType("Proof");
		registerCpt.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		Map<String, JsonSchema> cptJsonSchemas = new HashMap<>();
		JsonSchema jsonSchema1 = new JsonSchema();
		jsonSchema1.setDescription("user name");
		jsonSchema1.setRequired(true);
		jsonSchema1.setType("String");
		cptJsonSchemas.put("name", jsonSchema1);
		JsonSchema jsonSchema2 = new JsonSchema();
		jsonSchema2.setDescription("user sex");
		jsonSchema2.setRequired(false);
		jsonSchema2.setType("String");
		cptJsonSchemas.put("sex", jsonSchema2);
		JsonSchema jsonSchema3 = new JsonSchema();
		jsonSchema3.setDescription("user age");
		jsonSchema3.setRequired(true);
		jsonSchema3.setType("Number");
		cptJsonSchemas.put("age", jsonSchema3);
		registerCpt.setCptJsonSchema(cptJsonSchemas);
		CptBaseInfo cptBaseInfo = didClient.registerCpt(registerCpt);
		
		
		CreateCredential createCredential = new CreateCredential();
		createCredential.setCptId(cptBaseInfo.getCptId());
		createCredential.setIssuerDid(didDataWrapper.getDid());
		createCredential.setUserDid(didDataWrapper2.getDid());
        createCredential.setExpirationDate("2025-03-17");
        Map<String,Object> clainMap = new HashMap<>();
        clainMap.put("name", "aa");
        clainMap.put("sex", "female");
        clainMap.put("age", 38);
        createCredential.setClaim(clainMap);
        createCredential.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
		
        CredentialWrapper credentialWrapper = didClient.createCredential(createCredential);
		
        
        RevokeCredential cred = new RevokeCredential();
        cred.setCredId(credentialWrapper.getId());
        cred.setCptId(cptBaseInfo.getCptId());
        cred.setDid(didDataWrapper.getDid());
        cred.setPrivateKey(didDataWrapper.getAuthKeyInfo().getPrivateKey());
        boolean revokeResult = didClient.revokeCredential(cred);
        
        QueryCredential queryCredential = new QueryCredential();
        queryCredential.setDid(didDataWrapper.getDid());
        queryCredential.setPage(1);
        queryCredential.setSize(10);
        Pages<BaseCredential>  pages = didClient.getRevokedCredList(queryCredential);
        
        assertNotNull(pages);
	}
	
	@Test
	public void generalKeyPairByMnemonic() {
		List<String> mnemList = Arrays.asList("this is one test input mnemonic the size of mnemonic should 16".split(" "));
		KeyPair keyPair = DidClient.generalKeyPairByMnemonic(mnemList);
		System.out.println("=================="+JSONObject.toJSONString(keyPair));
		assertNotNull(keyPair.getPrivateKey());
		assertNotNull(keyPair.getPublicKey());
		assertNotNull(keyPair.getType());
	}
	
}
