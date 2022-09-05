package com.reddate.did.did.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.reddate.did.exception.DidException;

public class DidExceptionTest {
  @Test
  public void testConstructor() {
    DidException actualDidException = new DidException(1, "An error occurred");
    assertNull(actualDidException.getCause());
    assertEquals(
        "com.red.udpn.did.exception.DidException: An error occurred",
        actualDidException.toString());
    assertEquals(0, actualDidException.getSuppressed().length);
    assertEquals("An error occurred", actualDidException.getMessage());
    assertEquals("An error occurred", actualDidException.getLocalizedMessage());
  }
}
