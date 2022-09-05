package com.reddate.did.sdk;

import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.req.*;
import com.reddate.did.sdk.param.resp.DidDataWrapper;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.request.CptInfo;
import com.reddate.did.sdk.protocol.request.CredentialWrapper;
import com.reddate.did.sdk.protocol.response.AuthorityIssuer;
import com.reddate.did.sdk.protocol.response.*;
import com.reddate.did.sdk.service.AuthIssuerService;
import com.reddate.did.sdk.service.CredentialService;
import com.reddate.did.sdk.service.DidService;

import java.util.List;

/**
 * Did SDK main class, all the did service can be called by this class method.
 * 
 * Before call did service, you need create did client instance.
 */
public class DidClient {

	/**
	 * Did module service logic implement class
	 * 
	 */
	private DidService didService;

	/**
	 * Authenticate module service logic implement class
	 * 
	 */
	private AuthIssuerService authIssuerService;

	/**
	 * Credential module service logic implement class
	 * 
	 */
	private CredentialService credentialService;


	/**
	 * Did client construct for connect to the did service
	 *
	 * @param url       did service URL
	 */
	public DidClient(String url) {
		this(url, "", "", CryptoType.ECDSA);
	}

	/**
	 * Did client construct for connect to the did service
	 * 
	 * @param url       did service URL
	 * @param projectId The project Id of assign
	 * @param token     The Token of assign
	 */
	public DidClient(String url, String projectId, String token) {
		this(url, projectId, token,CryptoType.ECDSA);
	}
	
	/**
	 * Did client construct for connect to the self-deployed did service 
	 * 
	 * @param url       did service URL
	 * @param projectId The project Id 
	 * @param token     The Token 
	 * @param hubCryptoType hub's crypto type
	 */
	public DidClient(String url, String projectId, String token,CryptoType hubCryptoType) {

		didService = new DidService(url, projectId, token,hubCryptoType);
		authIssuerService = new AuthIssuerService(url, projectId, token, hubCryptoType);
		credentialService = new CredentialService(url, projectId, token, hubCryptoType);
	}
	
	/**
	 * Create did document and store this document on block chain if choose store on
	 * block chain.
	 * 
	 * @param isStorageOnChain Store generated did document store on block chain
	 * @return The did Identifier, generated did document and key pair.
	 */
	public DidDataWrapper createDid(Boolean isStorageOnChain) {

		ResultData<DidDataWrapper> genDidResult = null;
		try {
			genDidResult = didService.generateDid(isStorageOnChain);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}

		if (!genDidResult.isSuccess()) {
			throw new DidException(genDidResult.getCode(), genDidResult.getMsg());
		}
		return genDidResult.getData();
	}

	public DidDataWrapper createDid(KeyPair primaryKeyPair, KeyPair alternateKeyPair, Boolean isStorageOnChain) {

		ResultData genDidResult = null;
		try {
			genDidResult = this.didService.generateDid(primaryKeyPair, alternateKeyPair, isStorageOnChain);
		} catch (DidException var6) {
			throw var6;
		} catch (Exception var7) {
			var7.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), var7.getMessage());
		}

		if (!genDidResult.isSuccess()) {
			throw new DidException(genDidResult.getCode(), genDidResult.getMsg());
		} else {
			return (DidDataWrapper)genDidResult.getData();
		}
	}

	/**
	 * Verify the did document format and sign is correct or not, this function will
	 * verify the document format and the document sign.
	 * 
	 * @param didDocument Did document content
	 * @return The verify result.
	 */
	public Boolean verifyDidDocument(DidDocument didDocument) {

		ResultData<Boolean> verifyResult = null;
		try {
			verifyResult = didService.verifyDidDocument(didDocument);
		} catch (DidException e) {
			throw e;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
		return verifyResult.getData();
	}

	/**
	 * Storing a generated did document On-chain. this function verify the document
	 * content first, then store the document On-chain.
	 * 
	 * 
	 * @param didDocument The did document content
	 * @return document On-chain result
	 */
	public Boolean storeDidDocumentOnChain(DidDocument didDocument) {

		try {
			didService.storeDidDocumentOnChain(didDocument);
		} catch (DidException e) {
			throw e;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
		return true;
	}

	/**
	 * Query the did document content on the block chain. The DID Document contains
	 * the user's DID identifier, generation time, update time, public key,
	 * encryption algorithm, signature information, etc.
	 * 
	 * @param did Did identify
	 * @return The did document detail information
	 */
	public DidDocument getDidDocument(String did) {

		ResultData<DidDocument> queryDidDocResult = null;
		try {
			queryDidDocResult = didService.getDidDocument(did);
		} catch (DidException e) {
			throw e;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
		return queryDidDocResult.getData();
	}

	/**
	 * The user completes the master public and private key update through the
	 * recovery public and private keys. After the key is updated, the user's DID
	 * Document will also be updated, but the DID identifier will not change.
	 * 
	 * @param restDidAuth Rest the did document key information.
	 * @return Return the reset main public key result
	 */
	public KeyPair resetDidAuth(ResetDidAuth restDidAuth) {

		ResultData<KeyPair> restAuth = null;
		try {
			restAuth = didService.resetDidAuth(restDidAuth);
		} catch (DidException e) {
			throw e;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
		return restAuth.getData();
	}
	
	/**
	 * 
	 * Verify the sign value of the did identify is correct or not  by the did identify related document's public key.
	 * 
	 *
	 * @param didSign the did identify and did identify's sign value
	 * @return Return the verify did identify result
	 */
	public Boolean verifyDIdSign(DidSign didSign) {

		ResultData<Boolean> verifyResult = null;
		try {
			verifyResult = didService.verifyDIdSign(didSign);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(),e.getMessage());
		}
		
		if(!verifyResult.isSuccess()) {
			throw new DidException(verifyResult.getCode(),verifyResult.getMsg());
		}
		return verifyResult.getData();
	}

	/**
	 * 
	 * Register a did to be a authority issuer, this function validate the did is on
	 * block chain, and validate it is not a authority issuer, and register it to be
	 * a issuer.
	 * 
	 * 
	 * @param register Register authority issuer information.
	 * @return Return the register result
	 */
	public boolean registerAuthIssuer(RegisterAuthorityIssuer register) {

		try {
			return authIssuerService.registerAuthIssuer(register);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}

	/**
	 * Query the registered authority issuer list form the block chain.
	 * 
	 * The issuer information is public on the chain, so anyone can find all the
	 * issuers, but the DID identifier of the issuer is not returned by default. At
	 * the same time, it also supports query through the DID identifier of the
	 * issuing party, and all the information on the chain of the issuing party can
	 * be found.
	 * 
	 * @param query The page info and did identify
	 * @return return the authority list
	 */
	public Pages<AuthorityIssuer> queryAuthIssuerList(AuthIssuer query) {

		try {
			return authIssuerService.queryAuthIssuerList(query);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}

	/**
	 * Register a CPT template for the authority issuer, this function validate the
	 * authority issuer and CPT template information first, then add this CPT
	 * template on the block chain.
	 * 
	 * 
	 * @param registerCpt Register CPT template information
	 * @return Return the CPT template Id,and CPT template version
	 */
	public CptBaseInfo registerCpt(RegisterCpt registerCpt) {

		try {
			return authIssuerService.registerCpt(registerCpt);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}

	/**
	 * Query all registered CPT templates under the issuer according to the DID
	 * identifier of the issuer.
	 * 
	 * @param query Page information and authority issuer
	 * @return Return the CPT template list
	 */
	public Pages<CptInfo> queryCptListByDid(QueryCpt query) {

		try {
			return authIssuerService.queryCptListByDid(query);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}

	/**
	 * Query CPT declaration information based on the CPT template ID.
	 * 
	 * @param cptId The CPT template Id
	 * @return Return the CPT template detail information
	 */
	public CptInfo queryCptById(Long cptId) {

		try {
			return authIssuerService.queryCptById(cptId);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}

	/**
	 * The issuer updates the declaration content in the CPT template that it has
	 * already registered.
	 * 
	 * @param registerCpt Update CPT template information
	 * @return Return the new CPT template Id and version
	 */
	public CptBaseInfo updateCpt(RegisterCpt registerCpt) {

		try {
			return authIssuerService.updateCpt(registerCpt);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}

	/**
	 * Generate and issue a new credential base on the CPT template and input
	 * information
	 * 
	 * The issuer issues a certificate for the user according to the content of the
	 * certificate application filled in by the user. The certificate needs the
	 * signature of the issuer, so the issuer needs to pass in the master private
	 * key to sign.
	 * 
	 * @param createCredential The credential field values
	 * @return Return a issued credential
	 */
	public CredentialWrapper createCredential(CreateCredential createCredential) {

		try {
			return credentialService.createCredential(createCredential);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}

	/**
	 * Verify that the credentials are reality and valid.
	 * 
	 * Through certificate verification, the authenticity of the certificate can be
	 * identified. Each certificate is signed by the issuing authentication private
	 * key, so as long as the signature is verified, it means that the certificate
	 * itself is true. On this basis, re-verify the validity period of the
	 * certificate and whether it has been revoked. If it passes, it means that the
	 * certificate is acceptable.
	 * 
	 * @param createCredential Credential detail information
	 * @param publicKey        Public key information
	 * @return Return the verify result
	 */
	public Boolean verifyCredential(CredentialWrapper createCredential, PublicKey publicKey) {

		try {
			return credentialService.verifyCredential(createCredential, publicKey);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}

	/**
	 * 
	 * revoke issued credential on block chain, add this credential to the revoke
	 * credential list on block chain.
	 * 
	 * 
	 * @param cred Want to revoke credential information
	 * @return Return the revoke credential result
	 */
	public Boolean revokeCredential(RevokeCredential cred) {

		try {
			return credentialService.revokeCredential(cred);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}

	/**
	 * Query the revoked credential and and the revocation time, can query by the
	 * crednential's issuer did, also can query by the credential Id
	 * 
	 * @param queryCredential Paging information and authority issuer did
	 * @return Return the credential List
	 */
	public Pages<BaseCredential> getRevokedCredList(QueryCredential queryCredential) {

		try {
			return credentialService.getRevokedCredList(queryCredential);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}
	}
	
	/**
	 * Generate public and private key through mnemonics. 
	 * The mnemonic should consist of 16 English words.
	 * 
	 * The same mnemonic will generate the same public and private key.
	 * 
	 * @param mnemList English words
	 * @return return public and private key
	 */
	public static KeyPair generalKeyPairByMnemonic(List<String> mnemList) {
		return DidService.generalKeyPairByMnemonic(mnemList);
	} 

	/**
	 * Query current block number and group id
	 * 
	 * @return return current block number and group id
	 */
	public BlockInfoResp getBLockInfo() {
		return didService.getBlockInfo();
	}
	
}
