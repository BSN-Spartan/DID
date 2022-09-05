package com.reddate.did.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.reddate.did.protocol.common.Proof;
import com.reddate.did.protocol.request.RovekeInfo;
import org.junit.Test;

public class RovekeInfoTest {
  @Test
  public void testSetRovekeDate() {
    RovekeInfo rovekeInfo = new RovekeInfo();
    rovekeInfo.setRovekeDate("2020-03-01");
    assertEquals("2020-03-01", rovekeInfo.getRovekeDate());
  }

  @Test
  public void testSetCredId() {
    RovekeInfo rovekeInfo = new RovekeInfo();
    rovekeInfo.setCredId("42");
    assertEquals("42", rovekeInfo.getCredId());
  }

  @Test
  public void testSetProof() {
    RovekeInfo rovekeInfo = new RovekeInfo();
    Proof proof = new Proof();
    rovekeInfo.setProof(proof);
    assertSame(proof, rovekeInfo.getProof());
  }
}
