package com.rannger.dictionary2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.os.Build;

public class WordListActivity extends Activity {
	public final static String KEY_WORD="KEY_WORD";
	private final static String HTTP_REQUEST_FLAG="HTTP_REQUEST_FLAG";
	private WordListAdapter adapter=null;
	
	public WordListAdapter getAdapter()
	{
		return adapter;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_list);
		ArrayList<String> arrayList=new ArrayList<String>();
		adapter=new WordListAdapter(this,android.R.layout.simple_list_item_1,arrayList);
		Intent intent=getIntent();
		adapter.setKeyWord(intent.getStringExtra(KEY_WORD));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_list, menu); 
		SearchManager searchManager =
		           (SearchManager) getSystemService(this.SEARCH_SERVICE);
		final MenuItem searchMenuItem=menu.findItem(R.id.action_search);
		searchMenuItem.collapseActionView();
		final SearchView searchView=(SearchView) searchMenuItem.getActionView();
		searchView.setIconifiedByDefault(false);
		
	    searchView.setSearchableInfo(
	            searchManager.getSearchableInfo(getComponentName()));
	    searchView.setOnQueryTextListener(new OnQueryTextListener(){

			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				searchMenuItem.collapseActionView();
				Intent intent=new Intent(getBaseContext(),WordDetailActivity.class);
				intent.putExtra(WordDetailActivity.KEY_WORD, arg0);
				startActivity(intent);
				return true;
			}
	    	
	    });
	    searchView.setOnQueryTextFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus)
				{
					searchMenuItem.collapseActionView();
	                searchView.setQuery("", false);
				}
			}
	    	
	    });
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
		else if(id == android.R.id.home)
		{
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy ()
	{
		super.onDestroy();
		adapter.cancelAll();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends ListFragment implements View.OnClickListener
	{

		public PlaceholderFragment() {
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			WordListActivity activity=(WordListActivity)getActivity();
			activity.getAdapter().loadMore();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_word_list,
					container, false);
			View loadMoreView=inflater.inflate(R.layout.list_load_more_footer_view,null,false);
			WordListActivity activity=(WordListActivity)getActivity();
			Button btn=(Button) loadMoreView.findViewById(R.id.load_more);
			btn.setOnClickListener(this);
			ListView listView=(ListView) rootView.findViewById(android.R.id.list);
			listView.addFooterView(loadMoreView);
			setListAdapter(activity.getAdapter());
			
			return rootView;
		}
		
		@Override
		public void onListItemClick (ListView l, View v, int position, long id)
		{
			Intent intent=new Intent(getActivity(),WordDetailActivity.class);
			intent.putExtra(WordDetailActivity.KEY_WORD,(String)getListAdapter().getItem(position));
			getActivity().startActivity(intent);
		}


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			WordListActivity activity=(WordListActivity)getActivity();
			activity.getAdapter().loadMore();
		}
	}

}
