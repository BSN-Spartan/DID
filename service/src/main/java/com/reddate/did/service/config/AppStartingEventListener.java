// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.service.config;


import com.reddate.did.constant.CurrencyCode;
import com.reddate.did.util.AesCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

public class AppStartingEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(AppStartingEventListener.class);

  public static final String ENCRYPTED_PRIVATE_KEY = "encryptedPrivateKey";

  public static final String PROTECTION_KEY = "protectionKey";


  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

    String encryptedPrivateKey = parseArg(event.getArgs(), ENCRYPTED_PRIVATE_KEY);
    String protectionKey = parseArg(event.getArgs(), PROTECTION_KEY);
    calcAccPrivateKey(encryptedPrivateKey, protectionKey);
  }

  private String parseArg(String[] args, String argName) {

    String argument = null;
    if (args != null && args.length > 0) {
      for (int i = 0; i < args.length; i++) {
        String arg = args[i];

        if (arg != null) {
          arg = arg.trim();
          if (arg.contains(argName)) {
            String[] tmp = arg.split("=");

            if (tmp.length != 2) {
              throw new RuntimeException("The format of the argument '" + argName + "' is incorrect");
            }
            if (tmp[1] == null || tmp[1].trim().isEmpty()) {
              throw new RuntimeException("The format of the argument '" + argName + "' is incorrect");
            }
            argument = tmp[1].trim();
            break;
          }
        }
      }
    }
    return argument;
  }

  private void calcAccPrivateKey(String encryptedPrivateKey, String protectionKey) {

    logger.info("------ encryptedPrivateKey = {}, protectionKey = {}", encryptedPrivateKey, protectionKey);
    try {
      String key = DigestUtils.md5Hex(protectionKey).substring(0, 16);
      CurrencyCode.CONTRACT_ACC_PRIVATE_KEY = AesCode.decrypt(key, encryptedPrivateKey);

      if(StringUtils.isBlank(CurrencyCode.CONTRACT_ACC_PRIVATE_KEY)){
        logger.error("--------- CONTRACT_ACC_PRIVATE_KEY is empty ");
        System.exit(1);
      }

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("--------- calcAccPrivateKey e = ", e);
      System.exit(1);
    }


  }
}
