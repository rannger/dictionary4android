package com.rannger.dictionary2;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public final class FontManager {
	public final static String GLYPHICONS_HALFLINGS_REGULAR="fonts/glyphicons-halflings-regular.ttf";
	public final static String EUPHEMIA_UCAS_REGULAR="fonts/Euphemia-UCAS-Regular.ttf";
	public final static String EUPHEMIA_UCAS_BOLD="fonts/Euphemia-UCAS-Bold.ttf";
	
	public static Typeface getFont(Context context,String fontName)
	{
		AssetManager mgr=context.getAssets();
		Typeface tf=Typeface.createFromAsset(mgr, fontName);
		return tf;
	}
}
