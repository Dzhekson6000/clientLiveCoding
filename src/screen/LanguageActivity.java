package screen;

import screen.AuthActivity.Language;

import com.sit.clientlivecoding.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;

public class LanguageActivity extends Activity
{
	private SharedPreferences pref = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lang);
		
		pref  = getSharedPreferences("Shared",MODE_PRIVATE);
	}
	
	public void onClickRU(View vuew)
	{
		Editor ed = pref.edit();
	    ed.putString("Language", "RU");
	    ed.commit();
		AuthActivity.language = Language.RU;
		finish();
	}
	
	public void onClickEN(View vuew)
	{
		Editor ed = pref.edit();
	    ed.putString("Language", "EN");
	    ed.commit();
		AuthActivity.language = Language.EN;
		finish();
	}
	
	public void onClickDE(View vuew)
	{
		Editor ed = pref.edit();
	    ed.putString("Language", "DE");
	    ed.commit();
		AuthActivity.language = Language.DE;
		finish();
	}
}
