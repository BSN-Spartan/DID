// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.command;

import com.reddate.did.protocol.common.KeyPair;
import com.reddate.did.util.commons.ECDSAUtils;

public class DidSdkPrivateKeyGenerate {

  public static KeyPair getKey() throws Exception {
    return ECDSAUtils.createKey();
  }

  public static void main(String[] args) throws Exception {
    System.out.println("---------->>>>>generate private key: " + getKey().getPrivateKey());
  }
}
