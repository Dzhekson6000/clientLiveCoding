package screen;


import com.sit.clientlivecoding.R;

import Data.User;
import View.UserListView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class FollowActivity extends Activity
{
	UserListView userListView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fallow);
		
		userListView = (UserListView)findViewById(R.id.user_list_view);
		
		Intent intent = getIntent();
		int type = intent.getIntExtra("type", -1);
		if(type == 1)
		{
			userListView.setType(UserListView.Type.FALLOWS);
		}
		else if(type == 2)
		{
			userListView.setType(UserListView.Type.FALLOWERS);
		}
		else
		{
			return;
		}
		userListView.load();
	}
		
	public void onClickMenu(View view)
	{
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
