package com.reddate.did.did.protocol.common;

import static org.junit.Assert.assertEquals;

import com.reddate.did.protocol.common.Proof;
import org.junit.Test;

public class ProofTest {
  @Test
  public void testSetType() {
    Proof proof = new Proof();
    proof.setType("Type");
    assertEquals("Type", proof.getType());
  }

  @Test
  public void testSetCreator() {
    Proof proof = new Proof();
    proof.setCreator("Creator");
    assertEquals("Creator", proof.getCreator());
  }

  @Test
  public void testSetSignatureValue() {
    Proof proof = new Proof();
    proof.setSignatureValue("42");
    assertEquals("42", proof.getSignatureValue());
  }
}
