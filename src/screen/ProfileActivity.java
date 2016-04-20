package screen;


import com.sit.clientlivecoding.R;

import Data.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends Activity
{
	User user = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Intent intent = getIntent();
		String slug = intent.getStringExtra("slug");
		
		User user = new User();
		user.setOnParse(new User.OnParse()
		{
			
			@Override
			public void onParse(User user)
			{
				ProfileActivity.this.user = user;
				update();
			}
		});
		user.load(slug);
		
		
	}
	
	public void update()
	{
		TextView username = (TextView)findViewById(R.id.title);
		username.setText(user.username);
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
