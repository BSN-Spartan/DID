package com.reddate.did.did.constant;

import static org.junit.Assert.assertEquals;

import com.reddate.did.config.cache.DidConfigConstant;
import com.reddate.did.constant.CurrencyCode;
import com.reddate.did.constant.ErrorCode;
import org.junit.Assert;
import org.junit.Test;

public class ErrorCodeTest {
  @Test
  public void testGetTypeByErrorCode() {
    new DidConfigConstant();
    new CurrencyCode();
    Assert.assertEquals(ErrorCode.UNKNOWN_ERROR, ErrorCode.getTypeByErrorCode(-1));
    assertEquals(ErrorCode.UNKNOWN_ERROR, ErrorCode.getTypeByErrorCode(1));
    assertEquals(ErrorCode.SUCCESS, ErrorCode.getTypeByErrorCode(0));
  }
}
