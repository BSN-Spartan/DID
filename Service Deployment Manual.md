
### 1. Prerequisites
-  jdk1.8_200 or later
-  maven3.6.0


### 2. Change Configuration

The parameters below in `application.properties` file can be edited according to the actual deployment information:

- server.port
- did.type
- did.timeout
- web3j.receiptRotationCount
- web3j.receiptWaitTime


### 3. Build jar Package

Go to the root directory of the service project and execute the package command:

` mvn clean package -Dmaven.test.skip=true `

### 4. Deploy jar Package

If you need to deploy the DID contract and DID Service yourself, please note that:

- encryptedPrivateKey is the ciphertext of the deployed contract's private key.
- protectionKey is the key to encrypt the deployed contract's private key.

` java -jar did.service-0.0.1.jar --encryptedPrivateKey=07dd3c6c3a45d1d0e748d58481f07d17606f07448e807f3a3f2217da503fb57ca00c2c7467e165a041d1cc4b5e305f9c7891a94000f0da0916ae4975c65ef9e61d9e52537a409a9b2a02b07e64e6573a 
--protectionKey=b5e305f9c7891a94000f0da >>app.log 2>&1 `
