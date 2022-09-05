// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.util;

import java.util.Objects;

import com.reddate.did.constant.ErrorCode;
import com.reddate.did.constant.ResultCode;

public class PagesUtils {
	
	public static final int MIN_PAGE_SIZE = 1;
	
	public static final int MAX_PAGE_SIZE = 50;
	
  /**
   * Validity verification of pages entity
   *
   * @param page
   * @param size
   * @author shaopengfei
   */
  public static ResultCode verifyPages(Integer page, Integer size) {
    if (Objects.isNull(page)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "page ");
    }
    if (page < MIN_PAGE_SIZE) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of page is invalid");
    }
    
    if (Objects.isNull(size)) {
      return new ResultCode(ErrorCode.PARAMETER_IS_EMPTY, "size ");
    }
    if (size < MIN_PAGE_SIZE) {
      return new ResultCode(ErrorCode.PARAMETER_ILLEGAL_FORMAT.getCode(), "The format of size is invalid");
    }
    if (size > MAX_PAGE_SIZE) {
      return new ResultCode(ErrorCode.PARAMETER_TOO_LONG, "size ");
    }
    return new ResultCode(ErrorCode.SUCCESS);
  }
}
