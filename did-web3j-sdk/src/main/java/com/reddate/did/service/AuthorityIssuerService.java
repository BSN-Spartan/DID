// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service;

import com.reddate.did.protocol.common.AuthorityIssuer;
import com.reddate.did.protocol.common.RegisterAuthorityIssuer;
import com.reddate.did.protocol.request.RegisterCpt;
import com.reddate.did.protocol.request.RevokeCredential;
import com.reddate.did.protocol.response.CptBaseInfo;
import com.reddate.did.protocol.response.CptInfo;
import com.reddate.did.protocol.response.Pages;
import com.reddate.did.protocol.response.ResponseData;

public interface AuthorityIssuerService {

  ResponseData<Boolean> registerAuthIssuer(RegisterAuthorityIssuer issuer);

  ResponseData<Pages<AuthorityIssuer>> queryAuthIssuerList(Integer page, Integer size, String did);

  ResponseData<CptBaseInfo> registerCpt(RegisterCpt registerCpt);

  ResponseData<Pages<CptInfo>> queryCptListByDid(Integer page, Integer size, String did);

  ResponseData<CptInfo> queryCptById(Long cptId);

  ResponseData<CptBaseInfo> updateCpt(RegisterCpt registerCpt);

  ResponseData<Boolean> revokeCredential(RevokeCredential cred);
}
