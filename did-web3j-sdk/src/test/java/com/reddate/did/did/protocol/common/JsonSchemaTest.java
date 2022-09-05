package com.reddate.did.did.protocol.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.reddate.did.protocol.common.JsonSchema;
import org.junit.Test;

public class JsonSchemaTest {
  @Test
  public void testSetType() {
    JsonSchema jsonSchema = new JsonSchema();
    jsonSchema.setType("Type");
    assertEquals("Type", jsonSchema.getType());
  }

  @Test
  public void testSetDescription() {
    JsonSchema jsonSchema = new JsonSchema();
    jsonSchema.setDescription("The characteristics of someone or something");
    assertEquals("The characteristics of someone or something", jsonSchema.getDescription());
  }

  @Test
  public void testSetRequired() {
    JsonSchema jsonSchema = new JsonSchema();
    jsonSchema.setRequired(true);
    assertTrue(jsonSchema.getRequired());
  }
}
