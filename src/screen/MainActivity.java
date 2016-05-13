package screen;

import screen.AuthActivity.Language;

import com.sit.clientlivecoding.R;

import Data.User;
import View.VideoListView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity
{	
	private ListView menu;
	
	User myUser;
	VideoListView videoList = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		videoList = (VideoListView)findViewById(R.id.body);
		videoList.setType(VideoListView.Type.ALL_STREAM);
		videoList.load();
		init();
	}
	
	
	private void createMenu(String previous, String profile, String myVideos,String my_channel,String my_follows,String my_followers)
	{
		String[] menu_items = { previous, profile, myVideos, my_channel, my_follows, my_followers};
		
		menu = (ListView)findViewById(R.id.menu);
		ArrayAdapter<String> adapter_menu = new ArrayAdapter<String>(this, R.layout.custom_list_item, menu_items);
		menu.setAdapter(adapter_menu);
		menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	onClickMenu(position);
		    }
		});
	}
	
	private void init()
	{		
		Button livestream_button = (Button)findViewById(R.id.livestream_button);
		if(AuthActivity.language == Language.RU)
			livestream_button.setText(R.string.Livestreams_ru);
		else if(AuthActivity.language == Language.EN)
			livestream_button.setText(R.string.Livestreams_en);
		else if(AuthActivity.language == Language.DE)
			livestream_button.setText(R.string.Livestreams_de);
		
		Button videos_button = (Button)findViewById(R.id.videos_button);
		if(AuthActivity.language == Language.RU)
			videos_button.setText(R.string.Videos_ru);
		else if(AuthActivity.language == Language.EN)
			videos_button.setText(R.string.Videos_en);
		else if(AuthActivity.language == Language.DE)
			videos_button.setText(R.string.Videos_de);
		
		if(AuthActivity.language == Language.RU)
			createMenu(getString(R.string.previous_ru), getString(R.string.profile_ru), getString(R.string.myVideos_ru),
					getString(R.string.my_channel_ru), getString(R.string.my_follows_ru), getString(R.string.my_followers_ru));
		else if(AuthActivity.language == Language.EN)
			createMenu(getString(R.string.previous_en), getString(R.string.profile_en), getString(R.string.myVideos_en),
					getString(R.string.my_channel_en), getString(R.string.my_follows_en), getString(R.string.my_followers_en));
		else if(AuthActivity.language == Language.DE)
			createMenu(getString(R.string.previous_de), getString(R.string.profile_de), getString(R.string.myVideos_de),
					getString(R.string.my_channel_de), getString(R.string.my_follows_de), getString(R.string.my_followers_de));
		
	}
	
	public void onClickMenu(int item)
	{
		switch (item) {
		case 0:
			menu.setVisibility(View.INVISIBLE);
			break;
		case 1:
			Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
			startActivity(intent);
			break;
		case 2:
			videoList.setType(VideoListView.Type.MY_VIDEOS);
			videoList.load();
			menu.setVisibility(View.INVISIBLE);
			break;
		case 3:
			User user = new User();
			user.setOnParse(
					new User.OnParse()
					{
						
						@Override
						public void onParse(User user)
						{
							Intent intent = new Intent(MainActivity.this, VideoActivity.class);
							intent.putExtra("url", "https://www.livecoding.tv:443/api/livestreams/"+user.slug+"/?format=json");
							startActivity(intent);
						}
					});
			user.load(null);
			menu.setVisibility(View.INVISIBLE);
			break;
		case 4:
			{
				Intent intentFollows = new Intent(MainActivity.this, FollowActivity.class);
				intentFollows.putExtra("type", 1);
				startActivity(intentFollows);
				menu.setVisibility(View.INVISIBLE);
			}
			break;
		case 5:
			{
				Intent intentFollowers = new Intent(MainActivity.this, FollowActivity.class);
				intentFollowers.putExtra("type", 2);
				startActivity(intentFollowers);
				menu.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			break;
		}
	}
	
	public void onClickLivestream(View view)
	{
		videoList.setType(VideoListView.Type.ALL_STREAM);
		videoList.load();
	}
	
	public void onClickVideos(View view)
	{
		videoList.setType(VideoListView.Type.ALL_VIDEOS);
		videoList.load();
	}
	
	public void onClickMenu(View view)
	{
		menu.setVisibility(View.VISIBLE);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_MENU) {
	    	if(menu.getVisibility()==View.VISIBLE)
			{
				menu.setVisibility(View.INVISIBLE);
			}
			else
			{
				menu.setVisibility(View.VISIBLE);
			}
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onBackPressed() {
		if(menu.getVisibility()==View.VISIBLE)
		{
			menu.setVisibility(View.INVISIBLE);
		}
		else
		{
			super.onBackPressed();
		}
	}
		
}
