package com.reddate.did.service.config;

import javax.servlet.http.HttpServletResponse;

import com.reddate.did.sdk.constant.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reddate.did.exception.DidException;
import com.reddate.did.service.vo.response.ResultData;


@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseBody    
	@ExceptionHandler(Exception.class)    
	public ResponseEntity<ResultData<Object>> globalException(HttpServletResponse response,Exception ex){        
		ResultData<Object> result = new ResultData<Object>();
		
		if(ex instanceof DidException) {
			DidException didException = (DidException)ex;
			result.setCode(didException.getCode());
			result.setMsg(didException.getMessage());
		}else {
			result.setCode(ErrorMessage.UNKNOWN_ERROR.getCode());
			result.setMsg(ErrorMessage.UNKNOWN_ERROR.getMessage() + ex.getMessage());
		}
		
		logger.info("Process failed, the error message is: " + result.getCode() + " - " + result.getMsg());
		
		return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin","*")
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
}
	
}



