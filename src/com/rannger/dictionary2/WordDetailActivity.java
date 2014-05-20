package com.rannger.dictionary2;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class WordDetailActivity extends Activity {
	public final static String KEY_WORD="KEY_WORD";
	private final static String HTTP_REQUEST_FINISH_TAG="HTTP_REQUEST_FINISH_TAG";
	private String mKeyword="";
	private WordDetailAdapter mAdapter=null;
	
	public void setKeyWord(String keyword)
	{
		mKeyword=keyword;
	}
	
	public String getKeyWord()
	{
		return mKeyword;
	}
	
	public WordDetailAdapter getAdapter()
	{
		return mAdapter;
	}
	
	
	private void loadDataFromNetwork() throws MalformedURLException, UnsupportedEncodingException
	{
		String url=UrlFactory.share().wordDescUrl(mKeyword);
		AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
		client.setTimeout(10000);
		 MySSLSocketFactory sf = SSLFactory.createSSLSocketFactory();  
         if(sf != null){  
            client.setSSLSocketFactory(sf);  
         }
         
         client.get(url, new AsyncHttpResponseHandler() {
 		    @Override
 		    public void onSuccess(String response) {
 		        Log.v(HTTP_REQUEST_FINISH_TAG, response);
 		        Gson gson = new Gson();
 		        Map<String, Object> map = new HashMap<String, Object>();
 		        map = (Map<String, Object>)gson.fromJson(response, map.getClass());
 		        Double code=(Double)map.get("code");
 		        if(1.0==code)
 		        {
 		        	Map<String, Object> result=(Map<String, Object>)map.get("result");
 		        	ArrayList< Map<String,String> > wordDesc=(ArrayList)result.get("worddesc");
 		        	for (Map<String,String> wordDescItem : wordDesc)
 		        	{
 		        		for (Map.Entry<String, String> entry : wordDescItem.entrySet())
 		        		{
 		        			HashMap<String,String> item=new HashMap<String,String>();
 		        			item.put("title", entry.getKey().toString());
 		        			item.put("value", entry.getValue().toString());
 		        			mAdapter.add(item);
 		        		}
 		        	}
 		        	mAdapter.notifyDataSetChanged();
 	 				Toast.makeText(WordDetailActivity.this, "加载成功",
 	 					     Toast.LENGTH_SHORT).show();
 		        }
 		    }
 		    @Override  
 		    public void onFailure(java.lang.Throwable error)
 		    {  
 		    	Log.v(HTTP_REQUEST_FINISH_TAG, error.toString());
 				
 				Toast toast = Toast.makeText(WordDetailActivity.this,
 					     error.toString(), Toast.LENGTH_LONG);
 				toast.setGravity(Gravity.CENTER, 0, 0);
 				toast.show();
 		    }  
 		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<Map> array=new ArrayList<Map>();
		mAdapter=new WordDetailAdapter(this,android.R.layout.simple_expandable_list_item_1,array);
		setContentView(R.layout.activity_word_detail);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setKeyWord(getIntent().getStringExtra(KEY_WORD));
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		try {
			loadDataFromNetwork();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		else if (id == android.R.id.home)
		{
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_word_detail,
					container, false);
			TextView tv=(TextView) rootView.findViewById(R.id.word_title);
			ListView lv=(ListView) rootView.findViewById(android.R.id.list);
			
			WordDetailActivity activity=(WordDetailActivity)getActivity();
			tv.setText(activity.getKeyWord());
			tv.setTypeface(FontManager.getFont(activity, FontManager.EUPHEMIA_UCAS_BOLD));
			lv.setAdapter(activity.getAdapter());
			return rootView;
		}
	}

}
