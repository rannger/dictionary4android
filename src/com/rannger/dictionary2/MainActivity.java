package com.rannger.dictionary2;


import android.app.Activity;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends Activity
{

	private static final String HTTP_REQUEST_FINISH_TAG="HTTP_REQUEST_FINISH_TAG";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
				Intent intent=new Intent(MainActivity.this,WordListActivity.class);
				intent.putExtra(WordListActivity.KEY_WORD, arg0);
				MainActivity.this.startActivity(intent);
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
		if (id == R.id.action_search) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends ListFragment {
		private ArrayAdapter<String> mAdapter=null;
		
		public PlaceholderFragment() {
		}
		
		@Override
		public void onCreate (Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			String[] letter=new String[26];
			char begin='A';
			for(int index=0;index<26;index++)
			{
				letter[index]=Character.toString(((char)(begin+index)));
			}
			mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,letter);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			setListAdapter(mAdapter);
			return rootView;
		}
		
		@Override
		public void onListItemClick (ListView l, View v, int position, long id)
		{
			Intent intent=new Intent(getActivity(),WordListActivity.class);
			intent.putExtra(WordListActivity.KEY_WORD, mAdapter.getItem(position));
			getActivity().startActivity(intent);
		}
	}


}
