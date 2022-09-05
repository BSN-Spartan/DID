package com.reddate.did.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import com.reddate.did.protocol.common.JsonSchema;
import com.reddate.did.protocol.request.RegisterCpt;
import org.junit.Test;

public class RegisterCptTest {
  @Test
  public void testSetCptId() {
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setCptId(123L);
    assertEquals(123L, registerCpt.getCptId().longValue());
  }

  @Test
  public void testSetDid() {
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDid("Did");
    assertEquals("Did", registerCpt.getDid());
  }

  @Test
  public void testSetCptJsonSchema() {
    RegisterCpt registerCpt = new RegisterCpt();
    HashMap<String, JsonSchema> stringJsonSchemaMap = new HashMap<String, JsonSchema>(1);
    registerCpt.setCptJsonSchema(stringJsonSchemaMap);
    assertSame(stringJsonSchemaMap, registerCpt.getCptJsonSchema());
  }

  @Test
  public void testSetTitle() {
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setTitle("Dr");
    assertEquals("Dr", registerCpt.getTitle());
  }

  @Test
  public void testSetDescription() {
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setDescription("The characteristics of someone or something");
    assertEquals("The characteristics of someone or something", registerCpt.getDescription());
  }

  @Test
  public void testSetType() {
    RegisterCpt registerCpt = new RegisterCpt();
    registerCpt.setType("Type");
    assertEquals("Type", registerCpt.getType());
  }

  //    @Test
  //    public void testSetPrivateKey() {
  //        RegisterCpt registerCpt = new RegisterCpt();
  //        KeyPair keyPair = new KeyPair();
  //        registerCpt.setPrivateKey(keyPair);
  //        assertSame(keyPair, registerCpt.getPrivateKey());
  //    }
}
