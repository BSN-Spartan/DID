package com.reddate.did.did.protocol.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.reddate.did.protocol.common.ClaimInfo;
import org.junit.Test;

public class ClaimInfoTest {
  @Test
  public void testSetName() {
    ClaimInfo claimInfo = new ClaimInfo();
    claimInfo.setName("Name");
    assertEquals("Name", claimInfo.getName());
  }

  @Test
  public void testSetType() {
    ClaimInfo claimInfo = new ClaimInfo();
    claimInfo.setType("Type");
    assertEquals("Type", claimInfo.getType());
  }

  @Test
  public void testSetDesc() {
    ClaimInfo claimInfo = new ClaimInfo();
    claimInfo.setDesc("The characteristics of someone or something");
    assertEquals("The characteristics of someone or something", claimInfo.getDesc());
  }

  @Test
  public void testSetRequired() {
    ClaimInfo claimInfo = new ClaimInfo();
    claimInfo.setRequired(true);
    assertTrue(claimInfo.getRequired());
  }
}
