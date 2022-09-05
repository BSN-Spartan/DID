package com.reddate.did;

import com.reddate.did.sdk.DidClient;

public class DidClientTestBase {

	public DidClient getDidClient() {

		String url = "";
			
		DidClient didClient = new DidClient(url);
		return didClient;
	}
	
	
}
