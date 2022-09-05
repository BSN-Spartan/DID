
### Requirements
Latest version of Java 1.8 (minor version > 200)


### Build from source code

The build process needs to skip the unit test, command:

   ` mvn clean install -Dmaven.test.skip=true `


### Project integration SDK

Project integration SDK, please add other JAR files with dependencies:

```    
       <dependency>
           <groupId>com.reddate</groupId>
           <artifactId>did.sdk</artifactId>
           <version>${version}</version>
           <scope>system</scope>
           <systemPath>${basedir}/lib/did.sdk.${version}.jar</systemPath>
        </dependency>
        <dependency>
           <groupId>org.apache.directory.studio</groupId>
           <artifactId>org.apache.commons.codec</artifactId>
           <version>1.8</version>
        </dependency>
        <dependency>
           <groupId>org.apache.commons</groupId>
           <artifactId>commons-lang3</artifactId>
           <version>3.5</version>
        </dependency>
        <dependency>
           <groupId>com.alibaba</groupId>
           <artifactId>fastjson</artifactId>
           <version>1.2.83</version>
        </dependency>
        <dependency>
           <groupId>org.bouncycastle</groupId>
           <artifactId>bcprov-jdk15on</artifactId>
           <version>1.68</version>
        </dependency>
        <dependency>
           <groupId>cn.hutool</groupId>
           <artifactId>hutool-all</artifactId>
           <version>5.6.3</version>
        </dependency>
        <dependency>
           <groupId>org.fisco-bcos.java-sdk</groupId>
           <artifactId>java-sdk</artifactId>
           <version>2.7.0</version>
        </dependency>
        <dependency>
           <groupId>com.squareup.okhttp3</groupId>
           <artifactId>okhttp</artifactId>
           <version>4.9.0</version>
        </dependency>
        <dependency>
           <groupId>com.squareup.okhttp3</groupId>
           <artifactId>logging-interceptor</artifactId>
           <version>4.9.0</version>
        </dependency>
        <dependency>
           <groupId>junit</groupId>
           <artifactId>junit</artifactId>
           <version>4.13.2</version>
           <scope>test</scope>
        </dependency>
        <dependency>
           <groupId>org.web3j</groupId>
           <artifactId>core</artifactId>
           <version>4.8.4</version>
        </dependency>
        <dependency>
           <groupId>org.web3j</groupId>
           <artifactId>crypto</artifactId>
           <version>4.8.4</version>
        </dependency>
        <dependency>
           <groupId>org.bitcoinj</groupId>
           <artifactId>bitcoinj-core</artifactId>
           <version>0.15.10</version>
        </dependency>
        <dependency>
           <groupId>junit</groupId>
           <artifactId>junit</artifactId>
           <version>4.13.2</version>
           <scope>test</scope>
        </dependency>
        <dependency>
           <groupId>com.google.guava</groupId>
           <artifactId>guava</artifactId>
           <version>29.0-jre</version>
           <scope>compile</scope>
        </dependency>
```


### How to use


```java
package com.reddate.did;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.reddate.did.sdk.DidClient;
import com.reddate.did.sdk.param.req.DidSign;
import com.reddate.did.sdk.param.req.ResetDidAuth;
import com.reddate.did.sdk.param.resp.DidDataWrapper;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.util.ECDSAUtils;

import static org.junit.Assert.*;

public class DidClientTest {
	
	private DidClient getDidClient() {
		DidClient didClient = new DidClient();
		return didClient;
	}
	
	@Test   
	public void generateDidtest() {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid();
		assertNotNull(didDataWrapper);
		assertNotNull(didDataWrapper.getDid());
		assertNotNull(didDataWrapper.getDocument());
		assertNotNull(didDataWrapper.getAuthKeyInfo());
		assertNotNull(didDataWrapper.getRecyKeyInfo());
	} 
	
	@Test   
	public void resetDidAuthTest() throws InterruptedException {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid();
		
		ResetDidAuth restDidAuth = new ResetDidAuth();
		restDidAuth.setDid(didDataWrapper.getDid());
		KeyPair resetDidAuthKey = new KeyPair();
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
		DidDataWrapper didDataWrapper = didClient.createDid();
		
		ResetDidAuth restDidAuth = new ResetDidAuth();
		restDidAuth.setDid(didDataWrapper.getDid());
		restDidAuth.setPrimaryKeyPair(ECDSAUtils.createKey());
		KeyPair resetDidAuthKey = new KeyPair();
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
	public void verifyDIdSign() throws Exception {
		DidClient didClient = this.getDidClient();
		DidDataWrapper didDataWrapper = didClient.createDid();
		
		DidSign didSign = new DidSign();
		didSign.setDid(didDataWrapper.getDid());
		didSign.setDidSign(didDataWrapper.getDidSign());
		
		Boolean verify = didClient.verifyDIdSign(didSign);
		
		assertTrue(verify);
	} 
	
}

```

### DID signature
The signature of DID, uses the Secp256k1 algorithm.

```java
/**
 * @param cryptoType Enumeration value of the encryption algorithm
 * @param message Summary data to be signed
 * @param privateKey The private key
 */
public static String sign(CryptoType cryptoType, String message, String privateKey) {

    try {
        String publicKey = getPublicKey(cryptoType, privateKey);
        Sign.SignatureData signatureData = sign(message.getBytes(), new ECKeyPair(new BigInteger(privateKey), new BigInteger(publicKey)));
        return secp256k1SigBase64Serialization(signatureData);

        } catch (Exception e) {
        e.printStackTrace();
        throw new HubException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
    }
}
```


### Default global configuration
All request methods have the following global configuration:

```java
public class DidClientTestBase {

    public DidClient getDidClient() {

        String url = "https://didservice.bsn.foundation";
        DidClient didClient = new DidClient(url);
        
        return didClient;
    }
}

RequestBody requestBody = RequestBody.create(JSONObject.toJSONString(requestParam), JSON);
Request request = new Request.Builder()
        .url(url)
        .post(requestBody)
        .header("Connection", "close").build();
```


### Configure timeout

Configure timeout. The default connection timeout is 20 seconds, and the default read timeout is 60 seconds.

```java
OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build();
```


