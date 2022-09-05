// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.config.cache;

import com.reddate.did.config.ContractAddress;
import com.reddate.did.config.DidConfig;

import java.util.concurrent.ConcurrentHashMap;

public class ConfigCache {

  public static final String CONFIG_KEY = "didConfigInfo";

  public static final String CONTRACT_ADDRESS = "contractAddress";

  private static final ConcurrentHashMap<String, DidConfig> configInfo = new ConcurrentHashMap<>();

  private static final ConcurrentHashMap<String, ContractAddress> addressInfo =
      new ConcurrentHashMap<>();

  public static void putConfig(DidConfig config) {
    configInfo.put(CONFIG_KEY, config);
  }

  public static DidConfig getConfig() {
    return configInfo.get(CONFIG_KEY);
  }

  public static void putAddress(ContractAddress config) {
    addressInfo.put(CONTRACT_ADDRESS, config);
  }

  public static ContractAddress getAddress() {

    return addressInfo.get(CONTRACT_ADDRESS);
  }
}
