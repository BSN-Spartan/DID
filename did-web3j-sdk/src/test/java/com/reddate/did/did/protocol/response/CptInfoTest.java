package com.reddate.did.did.protocol.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import com.reddate.did.protocol.common.JsonSchema;
import com.reddate.did.protocol.common.Proof;
import com.reddate.did.protocol.response.CptInfo;
import org.junit.Test;

public class CptInfoTest {
  @Test
  public void testSetPublisherDid() {
    CptInfo cptInfo = new CptInfo();
    cptInfo.setPublisherDid("Publisher Did");
    assertEquals("Publisher Did", cptInfo.getPublisherDid());
  }

  @Test
  public void testSetTitle() {
    CptInfo cptInfo = new CptInfo();
    cptInfo.setTitle("Dr");
    assertEquals("Dr", cptInfo.getTitle());
  }

  @Test
  public void testSetDescription() {
    CptInfo cptInfo = new CptInfo();
    cptInfo.setDescription("The characteristics of someone or something");
    assertEquals("The characteristics of someone or something", cptInfo.getDescription());
  }

  @Test
  public void testSetCptJsonSchema() {
    CptInfo cptInfo = new CptInfo();
    HashMap<String, JsonSchema> stringJsonSchemaMap = new HashMap<String, JsonSchema>(1);
    cptInfo.setCptJsonSchema(stringJsonSchemaMap);
    assertSame(stringJsonSchemaMap, cptInfo.getCptJsonSchema());
  }

  @Test
  public void testSetProof() {
    CptInfo cptInfo = new CptInfo();
    Proof proof = new Proof();
    cptInfo.setProof(proof);
    assertSame(proof, cptInfo.getProof());
  }

  @Test
  public void testSetCreate() {
    CptInfo cptInfo = new CptInfo();
    cptInfo.setCreate("Create");
    assertEquals("Create", cptInfo.getCreate());
  }

  @Test
  public void testSetUpdate() {
    CptInfo cptInfo = new CptInfo();
    cptInfo.setUpdate("2020-03-01");
    assertEquals("2020-03-01", cptInfo.getUpdate());
  }
}
