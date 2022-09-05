package com.reddate.did.did;

import com.alibaba.fastjson.JSONObject;
import com.reddate.did.DIDClient;
import com.reddate.did.protocol.response.CreateDidData;

public class DidSdkTest {

  public static void main(String[] args) {

    DIDClient didClient = new DIDClient();
    didClient.init();

    System.out.println("========================");
    CreateDidData didData = didClient.createDid(true);
    System.out.println("========================");
    System.out.println("create result = " + JSONObject.toJSONString(didData));

  }
}
