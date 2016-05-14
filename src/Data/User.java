package Data;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
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
	public String avatar;
	public String viewing_key;
	
	OnParse onParse;
	
	public User()
	{
	}
	
	public boolean parse(String json, String viewkeyJson)
	{
		
		JSONObject result = null;
        try {
        	result = new JSONObject(viewkeyJson);             
            
        	viewing_key  = result.getString("viewing_key");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
		return parse(json);
	}
	
	public boolean parse(String json)
	{
		JSONObject result = null;

        try {
        	result = new JSONObject(json);             
            
        	username  = result.getString("username");
        	slug = result.getString("slug");
        	avatar=result.getString("avatar");
        	country = result.getString("country");
        	city=result.getString("city");
        	favorite_programming=result.getString("favorite_programming");
            
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
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
		
		TextView text2 = (TextView)view.findViewById(R.id.categoria);
		if(city!="null")text2.setText(city);

		ImageView avatar = (ImageView)view.findViewById(R.id.avatar);
		
		if(!avatar.equals("null"))
		{
			ViewVideosItem.fetchImage(this.avatar, avatar, R.drawable.user);
		} else 
		{
			avatar.setImageResource(R.drawable.user);
		}
		
		return true;
	}
	
	public boolean load(final String slug)
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
			public void onRead(final String response)
			{
				if(slug == null)
				{
					HttpWraper httpWraper = new HttpWraper();
					httpWraper.setOnHttpResponse(new OnHttpResponse()
					{
						@Override
						public void onRead(String response2)
						{
							parse(response, response2);
						}
					});
					httpWraper.loadText("https://www.livecoding.tv:443/api/user/viewing_key/");
				}else
				{
					parse(response);
				}
			}
		});
		httpWraper.loadText(url);
		return true;
	}
}
