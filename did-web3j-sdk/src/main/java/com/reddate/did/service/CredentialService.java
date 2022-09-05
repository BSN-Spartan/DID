// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service;

import com.reddate.did.protocol.common.BaseCredential;
import com.reddate.did.protocol.common.Credential;
import com.reddate.did.protocol.common.PublicKey;
import com.reddate.did.protocol.request.CreateCredential;
import com.reddate.did.protocol.response.CredentialWrapper;
import com.reddate.did.protocol.response.Pages;
import com.reddate.did.protocol.response.ResponseData;

public interface CredentialService {

  ResponseData<CredentialWrapper> createCredential(CreateCredential createCredential);

  ResponseData<Boolean> verifyCredential(Credential credential, PublicKey publicKey);

  ResponseData<Pages<BaseCredential>> getRevokedCredList(Integer page, Integer size, String did);
}
