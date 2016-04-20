package screen;


import com.sit.clientlivecoding.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class AuthActivity extends Activity {
	
	public static String token;
	private String clientId = "";
	private String clientSecret = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth);
		
		//createWebView(((LinearLayout)findViewById(R.id.main_layout)));
		token = "";
		
		if(!token.isEmpty())
		{
			Intent mainIntent = new Intent(AuthActivity.this, MainActivity.class);
			mainIntent.putExtra("token", token);
			startActivity(mainIntent);
			finish();
		}
		
	}
	
	
	public void onClickAuth(View view)
	{
		
	}
	
	private boolean createWebView(LinearLayout view)
	{
		//WebView webView = new WebView(this);
		//view.addView(webView);
		//webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13");
		//webView.getSettings().setJavaScriptEnabled(true);
		
		//String url = "https://www.livecoding.tv/o/authorize?client_id="+clientId+"&response_type=token&state=123";
		//webView.loadUrl(url); 
		return true;
	}
}
