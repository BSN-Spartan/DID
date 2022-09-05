// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service.engine;

import org.web3j.crypto.Credentials;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseEngine {

  /** Chain success code */
  public static final String CHAIN_SUCCESS_CODE = "0000";

  /** Timeout processing thread pool */
  public static ExecutorService executorService;

  static {
    executorService = Executors.newCachedThreadPool();
  }

  protected static String blockChainType;


  public static Credentials getCredentials(String inputPrivateKey) {

    return Credentials.create(inputPrivateKey);
  }

}
