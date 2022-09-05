// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did;

import com.reddate.did.config.DidConfig;
import com.reddate.did.config.cache.ConfigCache;
import com.reddate.did.config.cache.ConfigUtils;
import com.reddate.did.constant.ErrorCode;
import com.reddate.did.exception.DidException;
import com.reddate.did.protocol.common.*;
import com.reddate.did.protocol.request.CreateCredential;
import com.reddate.did.protocol.request.RegisterCpt;
import com.reddate.did.protocol.request.ResetDid;
import com.reddate.did.protocol.request.RevokeCredential;
import com.reddate.did.protocol.response.*;
import com.reddate.did.repository.CredentialRepository;
import com.reddate.did.repository.DidRepository;
import com.reddate.did.repository.IssuerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class DIDClient {

  private static final Logger logger = LoggerFactory.getLogger(DIDClient.class);

  private static final String CONFIG_FILE_NAME = "application.properties";

  private static DidRepository didRepository;

  private static IssuerRepository issuerRepository;

  private static CredentialRepository credentialRepository;

  /** Load DID component config information from the class path */
  public void init() {
    this.init(CONFIG_FILE_NAME);
  }

  /** Load DID component config information from the configure file */
  public void init(String configFileName) {
    DidConfig didConfig = null;
    try {
      didConfig = ConfigUtils.client(configFileName);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    if (didConfig == null) {
      logger.error(ErrorCode.DID_SDK_INIT_FAIL.getEnMessage());
      throw new DidException(
          ErrorCode.DID_SDK_INIT_FAIL.getCode(), ErrorCode.DID_SDK_INIT_FAIL.getEnMessage());
    }
    ConfigCache.putConfig(didConfig);
  }

  /** Perform the warehouse of business methods related to did */
  private DidRepository getDidRepository() {
    if (null == didRepository) didRepository = new DidRepository();
    return didRepository;
  }

  /** Perform the warehouse of business methods related to issuer */
  private IssuerRepository getIssuerRepository() {
    if (null == issuerRepository) issuerRepository = new IssuerRepository();
    return issuerRepository;
  }

  /** Perform the warehouse of business methods related to credential */
  private CredentialRepository getCredentialRepository() {
    if (null == credentialRepository) credentialRepository = new CredentialRepository();
    return credentialRepository;
  }

  /**
   * Register did and generate identity description document, and select whether it is on the chain
   * according to the parameters
   */
  public CreateDidData createDid(Boolean isStorageOnChain) {
    return getDidRepository().createDid(isStorageOnChain);
  }

  /** Store did documents that are not stored on the chain to the chain */
  public Boolean storeDidDocumentOnChain(DidDocument didDocument, PublicKey publicKey) {
    return getDidRepository().storeDidDocumentOnChain(didDocument, publicKey);
  }
  /** Verify the did identity is credible , and query the DID Document. */
  public DidDocument getDidDocument(String did) {
    return getDidRepository().getDidDocument(did);
  }

  /** Verify the validity of did document by means of signature verification */
  public Boolean verifyDidDocument(DidDocument didDocument, PublicKey publicKey) {
    return getDidRepository().verifyDidDocument(didDocument, publicKey);
  }

  /** Did users update their keys. */
  public KeyPair resetDidAuth(ResetDid restDid) {
    return getDidRepository().resetDidAuth(restDid);
  }

  /** Register did user to be a issuing party. */
  public Boolean registerAuthIssuer(RegisterAuthorityIssuer issuer) {
    return getIssuerRepository().registerAuthIssuer(issuer);
  }

  /**
   * If the input parameter did is empty, it means that all information of the issuing party is
   * returned in the form of a list. If the did not empty, it means that the information of the
   * issuing party is queried.
   */
  public Pages<AuthorityIssuer> queryAuthIssuerList(Integer page, Integer size, String did) {
    return getIssuerRepository().queryAuthIssuerList(page, size, did);
  }

  /** The issuing party registers the CPT template and uploads the CPT template. */
  public CptBaseInfo registerCpt(RegisterCpt registerCpt) {
    return getIssuerRepository().registerCpt(registerCpt);
  }

  /**
   * The interface returns information about the CPT template. Passing only did means to query all
   * CPT templates under the issuer; only passing cptId means to query the CPT template of the CPT
   * template number; if both did and cptId are empty, it means to query all CPT templates of all
   * issuers.
   */
  public Pages<CptInfo> queryCptListByDid(Integer page, Integer size, String did) {
    return getIssuerRepository().queryCptListByDid(page, size, did);
  }

  /**
   * The interface returns information about the CPT template. Passing only did means to query all
   * CPT templates under the issuer; only passing cptId means to query the CPT template of the CPT
   * template number; if both did and cptId are empty, it means to query all CPT templates of all
   * issuers.
   */
  public CptInfo queryCptById(Long cptId) {
    return getIssuerRepository().queryCptById(cptId);
  }

  /** The issuing party updates the content in the CPT template that it has already registered. */
  public CptBaseInfo updateCpt(RegisterCpt registerCpt) {
    return getIssuerRepository().updateCpt(registerCpt);
  }

  /** Create a Credential based on the incoming information and CPT template. */
  public CredentialWrapper createCredential(CreateCredential createCredential) {
    return getCredentialRepository().createCredential(createCredential);
  }

  /** Verify that the credentials are authentic and valid. */
  public Boolean verifyCredential(Credential credential, PublicKey publicKey) {
    return getCredentialRepository().verifyCredential(credential, publicKey);
  }

  /** Revoke the specified Credential. */
  public Boolean revokeCredential(RevokeCredential cred) {
    return getIssuerRepository().revokeCredential(cred);
  }
  
  /**
   * The did of the issuing party is required. If the request parameter does not have a Credential
   * number, query the list of revoked Credentials under the issuing party.
   */
  public Pages<BaseCredential> getRevokedCredList(Integer page, Integer size, String did) {
    return getCredentialRepository().getRevokedCredList(page, size, did);
  }

  public BigInteger getBlockNumber() {
    return getDidRepository().getBlockNumber();
  }

  public Integer getGroupId() {
    return getDidRepository().getGroupId();
  }
}
