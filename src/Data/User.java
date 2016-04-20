package Data;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sit.clientlivecoding.R;

import View.HttpWraper;
import View.HttpWraper.OnHttpResponse;

public class User
{
	
	public interface OnParse
	{
		void onParse(User user);
	}
	
	public String username;
	public String slug;
	public String country;
	public String city;
	public String favorite_programming;
	public String favorite_ide;
	public String favorite_coding_background_music;
	public String favorite_code;
	public int years_programming;
	public String want_learn;
	public String registration_date;
	
	OnParse onParse;
	
	public User()
	{
	}
	
	public boolean parse(String json)
	{
		JSONObject result = null;

        try {
        	result = new JSONObject(json);             
            
        	username  = result.getString("username");
        	slug = result.getString("slug");
        	country = result.getString("country");
        	city=result.getString("city");
        	favorite_programming=result.getString("favorite_programming");
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        onParse.onParse(this);
		return true;
	}
	
	public void setOnParse(OnParse onParse)
	{
		this.onParse = onParse;
	}
	
	public boolean viewPoll(View view)
	{
		TextView text1 = (TextView)view.findViewById(R.id.title);
		text1.setText(username);
		
		TextView text2 = (TextView)view.findViewById(R.id.textView2);
		if(city!="null")text2.setText(city);

		
		return true;
	}
	
	public boolean load(String slug)
	{
		String url = null;
		if(slug==null)
		{
			url = "https://www.livecoding.tv:443/api/user/?format=json";
		}
		else
		{
			url = "https://www.livecoding.tv:443/api/users/"+slug+"/?format=json";
			
		}
		
		HttpWraper httpWraper = new HttpWraper();
		httpWraper.setOnHttpResponse(new OnHttpResponse()
		{
			@Override
			public void onRead(String response)
			{
				parse(response);
			}
		});
		httpWraper.loadText(url);
		return true;
	}
}