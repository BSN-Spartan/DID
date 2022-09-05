package com.reddate.did.did.protocol.common;

import static org.junit.Assert.assertEquals;

import com.reddate.did.protocol.common.IssuerInfo;
import org.junit.Test;

public class IssuerInfoTest {
  @Test
  public void testSetOrgName() {
    IssuerInfo issuerInfo = new IssuerInfo();
    issuerInfo.setOrgName("Org Name");
    assertEquals("Org Name", issuerInfo.getOrgName());
  }

  @Test
  public void testSetIdType() {
    IssuerInfo issuerInfo = new IssuerInfo();
    issuerInfo.setIdType("Id Type");
    assertEquals("Id Type", issuerInfo.getIdType());
  }

  @Test
  public void testSetIdNum() {
    IssuerInfo issuerInfo = new IssuerInfo();
    issuerInfo.setIdNum("Id Num");
    assertEquals("Id Num", issuerInfo.getIdNum());
  }
}
