package screen;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sit.clientlivecoding.R;

import View.HttpWraper;
import View.HttpWraper.OnHttpResponse;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AuthActivity extends Activity
{
	private class MyWebViewClient extends WebViewClient 
	{
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) 
	    {	    	
	    	Log.d("DEB", "url:"+url);
	    	Uri uri = Uri.parse(url);
	    	Log.d("DEB", "host:"+uri.getHost());
	    	if(uri.getHost().equals("localhost"))
	    	{
	    		uri = Uri.parse(url.replace("#", "?"));
	    		String tok = uri.getQueryParameter("access_token");
	    		if(tok.isEmpty())
	    		{
	    			((ViewGroup) webView.getParent()).removeView(webView);
	    			return true;
	    		}
	    		else
	    		{
	    			onAuth(tok);
	    		}
	    	}
	    		    	
	    	
	        view.loadUrl(url);
	        return true;
	    }
	}
	
	
	enum Language
	{
		RU,
		EN,
		DE
	}
	
	public static String token = "";
	public static Language language = Language.EN;
	private WebView webView = null;
	
	private String clientId = "9LwcNsX4dJPuSTWvCrBwErRX3j4ftldw17o2VkjV";
	
	private SharedPreferences pref;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth);		
		
		pref  = getSharedPreferences("Shared",MODE_PRIVATE);
		language = getLanguage(pref.getString("Language", ""));
		
		String tok = pref.getString("token", "");
		
		if(!tok.isEmpty())
		{
			onAuth(tok);
		}
		
		
	}
	
	@Override
	protected void onResume()
	{
		init();
		super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		if(webView != null)
		{
			((ViewGroup) webView.getParent()).removeView(webView);
		}
		else
		{
			super.onBackPressed();
		}
	}
	
	private void init()
	{		
		TextView desc_text = (TextView)findViewById(R.id.desc_text);
		if(language == Language.RU)
			desc_text.setText(R.string.desc_ru);
		else if(language == Language.EN)
			desc_text.setText(R.string.desc_en);
		else if(language == Language.DE)
			desc_text.setText(R.string.desc_de);
		
		Button auth_button = (Button)findViewById(R.id.auth_button);
		if(language == Language.RU)
			auth_button.setText(R.string.enter_ru);
		else if(language == Language.EN)
			auth_button.setText(R.string.enter_en);
		else if(language == Language.DE)
			auth_button.setText(R.string.enter_de);
		
		Button language_button = (Button)findViewById(R.id.language_button);
		if(language == Language.RU)
			language_button.setText(R.string.language_ru);
		else if(language == Language.EN)
			language_button.setText(R.string.language_en);
		else if(language == Language.DE)
			language_button.setText(R.string.language_de);
		
	}
	
	public void onAuth(String token)
	{
		AuthActivity.token = token;
		
		HttpWraper httpWraper = new HttpWraper();
		httpWraper.setOnHttpResponse(new OnHttpResponse()
		{
			@Override
			public void onRead(String response)
			{
				if(response.equals("{\"detail\":\"Authentication credentials were not provided.\"}"))
					return;
				Editor ed = pref.edit();
			    ed.putString("token", AuthActivity.token);
			    ed.commit();
				
			    Intent mainIntent = new Intent(AuthActivity.this, MainActivity.class);
				startActivity(mainIntent);
				finish();
			}
		});
		httpWraper.loadText("https://www.livecoding.tv:443/api/user/");
	}
	
	
	public void onClickEnter(View vuew)
	{
		createWebView(((RelativeLayout)findViewById(R.id.auth_layout)));
	}
	
	public void onClickLanguage(View vuew)
	{
		Intent intent = new Intent(AuthActivity.this, LanguageActivity.class);
		startActivity(intent);
	}
	
	public static Language getLanguage(String lang)
	{
		if(lang.equals("RU"))
			return Language.RU;
		else if(lang.equals("EN"))
			return Language.EN;
		else if(lang.equals("DE"))
			return Language.DE;
		return Language.EN;
	}
	
	
	private boolean createWebView(RelativeLayout layout)
	{
		webView = new WebView(this);
		layout.addView(webView);
		webView.setWebViewClient(new MyWebViewClient());
		webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13");
		
		String url = "https://www.livecoding.tv/o/authorize?client_id="+clientId+"&response_type=token";
		webView.loadUrl(url); 
		return true;
	}
}
