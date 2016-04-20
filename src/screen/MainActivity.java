package screen;

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
import android.widget.ListView;

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

		
		String[] menu_items = { getString(R.string.previous), getString(R.string.profile), getString(R.string.myVideos),
				getString(R.string.my_channel), getString(R.string.my_follows), getString(R.string.my_followers)};
		menu = (ListView)findViewById(R.id.menu);
		ArrayAdapter<String> adapter_menu = new ArrayAdapter<String>(this, R.layout.custom_list_item, menu_items);
		menu.setAdapter(adapter_menu);
		menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	onClickMenu(position);
		    }
		});
		
		
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
