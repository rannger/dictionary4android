package com.rannger.dictionary2;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public final class UrlFactory {
	private static UrlFactory shareInstance=null;
	private URL baseUrl=null;
	
	public static UrlFactory share() throws MalformedURLException
	{
		if (null==shareInstance)
		{
			shareInstance=new UrlFactory(Urls.BASE_URL_STRING);
		}
		return shareInstance;
	}
	
	public UrlFactory(String spec) throws MalformedURLException
	{
		baseUrl=new URL(spec);
	}
	
	public String wordListUrl(int pageIndex,String word) throws MalformedURLException, UnsupportedEncodingException
	{
		String param=String.format(Urls.WORD_LIST_STRING, pageIndex, URLEncoder.encode(word,"UTF-8").replace("+", "%20"));
		URL retval=new URL(baseUrl,param);
		return retval.toString();
		
	}
	
	public String wordDescUrl(String word) throws MalformedURLException, UnsupportedEncodingException
	{
		String param=String.format(Urls.WORD_DESC_STRING,URLEncoder.encode(word,"UTF-8").replace("+", "%20"));
		URL retval=new URL(baseUrl,param);
		return retval.toString();
		
	}
	
	final class Urls
	{
		private final static String BASE_URL_STRING="https://dict-rst0aic.rhcloud.com";
		private final static String WORD_LIST_STRING="/word/list/%d/%s";
		private final static String WORD_DESC_STRING="/word/desc/%s";
		
	}

}
