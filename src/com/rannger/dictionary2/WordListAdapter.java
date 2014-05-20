package com.rannger.dictionary2;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;

public final class WordListAdapter extends ArrayAdapter<String> {
	
	private int mPageIndex=0;
	private String mKeyword="";
	private final static String HTTP_REQUEST_FINISH_TAG="HTTP_REQUEST_FINISH_TAG";
	private final ArrayList<AsyncHttpClient> requestList=new ArrayList<AsyncHttpClient>();
	
	public String getKeyWord()
	{
		return mKeyword;
	}
	
	public void setKeyWord(String keyword)
	{
		mKeyword=keyword;
	}
	
	public void cancelAll()
	{
		for(AsyncHttpClient client:requestList)
		{
			client.cancelRequests(getContext(), true);
		}
	}

	public WordListAdapter(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}
	
	public void loadMore()
	{
		try {
			loadDataFromNet();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadDataFromNet() throws MalformedURLException, UnsupportedEncodingException
	{
		String url=UrlFactory.share().wordListUrl(mPageIndex, getKeyWord());
		AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
		client.setTimeout(10000);
		 MySSLSocketFactory sf = SSLFactory.createSSLSocketFactory();  
         if(sf != null){  
            client.setSSLSocketFactory(sf);  
         }
         
        client.get(url, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	requestList.remove(this);
		        Log.v(HTTP_REQUEST_FINISH_TAG, response);
		        Gson gson = new Gson();
		        Map<String, Object> map = new HashMap<String, Object>();
		        map = (Map<String, Object>)gson.fromJson(response, map.getClass());
		        Double code=(Double)map.get("code");
		        if(1.0==code)
		        {
		        	 Map<String, Object> result=(Map<String, Object>)map.get("result");
		        	 double pageIndex=(Double)result.get("page");
		        	 mPageIndex=(int)pageIndex;
		        	 ArrayList<String> list=(ArrayList<String>)result.get("words");
		        	 addAll(list);
		        }
		        
				notifyDataSetChanged();
				
				Toast.makeText(getContext(), "加载成功",
					     Toast.LENGTH_SHORT).show();
		    }
		    @Override  
		    public void onFailure(java.lang.Throwable error)
		    {  
		    	requestList.remove(this);
		    	Log.v(HTTP_REQUEST_FINISH_TAG, error.toString());
				notifyDataSetChanged();
				
				Toast toast = Toast.makeText(getContext(),
					     error.toString(), Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
		    }  
		});
        requestList.add(client);
	}

}
