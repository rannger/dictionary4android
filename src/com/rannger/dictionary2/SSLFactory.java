package com.rannger.dictionary2;

import java.security.KeyStore;
import com.loopj.android.http.MySSLSocketFactory;

public final class SSLFactory  {
	public static MySSLSocketFactory createSSLSocketFactory(){
		MySSLSocketFactory sf = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sf;
	}

}
