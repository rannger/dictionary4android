package com.rannger.dictionary2;

import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public final class WordDetailAdapter extends ArrayAdapter<Map> {
	
	public WordDetailAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
	}

	public WordDetailAdapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	public WordDetailAdapter(Context context, int resource, Map[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	public WordDetailAdapter(Context context, int resource, List<Map> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	public WordDetailAdapter(Context context, int resource,
			int textViewResourceId, Map[] objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	public WordDetailAdapter(Context context, int resource,
			int textViewResourceId, List<Map> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("CutPasteId")
	public View getView (int position, View convertView, ViewGroup parent)
	{
		View retView=convertView;
		if (null==retView)
		{
			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			retView=layoutInflater.inflate(R.layout.word_detal_cell, parent,false);
			TextView tv=(TextView) retView.findViewById(R.id.word_detail_title);
			tv.setTypeface(FontManager.getFont(getContext(), FontManager.GLYPHICONS_HALFLINGS_REGULAR));
			tv=(TextView) retView.findViewById(R.id.word_detail_text);
			tv.setTypeface(FontManager.getFont(getContext(), FontManager.EUPHEMIA_UCAS_REGULAR));
		}
		TextView valueView=(TextView) retView.findViewById(R.id.word_detail_text);
		TextView titleView=(TextView) retView.findViewById(R.id.word_detail_title);
		Map<String,String> item=getItem(position);
		titleView.setText(item.get("title")+":");
		valueView.setText(item.get("value"));
		return retView;

	}

}
