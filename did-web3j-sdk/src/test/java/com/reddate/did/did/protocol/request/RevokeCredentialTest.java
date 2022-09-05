package com.reddate.did.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.reddate.did.protocol.request.RevokeCredential;
import org.junit.Test;

public class RevokeCredentialTest {

  @Test
  public void testSetCredId() {
    RevokeCredential revokeCredential = new RevokeCredential();
    revokeCredential.setCredId("42");
    assertEquals("42", revokeCredential.getCredId());
  }

  @Test
  public void testSetCptId() {
    RevokeCredential revokeCredential = new RevokeCredential();
    revokeCredential.setCptId(123L);
    assertEquals(123L, revokeCredential.getCptId().longValue());
  }

  @Test
  public void testSetDid() {
    RevokeCredential revokeCredential = new RevokeCredential();
    revokeCredential.setDid("Did");
    assertEquals("Did", revokeCredential.getDid());
  }

}
