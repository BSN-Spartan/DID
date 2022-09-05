package com.reddate.did.service;

import com.reddate.did.service.config.AppStartingEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DidServiceApplication {

    /*
     *
     * java -jar did.service-0.0.1.jar --encryptedPrivateKey=8d8c3659e0d6d645cabb6f2b47baeaed5f650bf126e19de858256e4b896393ae92f27282837e6a0a3cec8cecfa545ee7ad01fcbab9709f2174bf059b4f182e4924960bcdb272cc081fb17333f50382e7 --protectionKey=123456789
     *
     */
    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(DidServiceApplication.class);
        springApplication.addListeners(new AppStartingEventListener());
        springApplication.run(args);
    }

}
