package screen;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import com.sit.clientlivecoding.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class VideoActivity extends Activity
{
	private String url = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
	        return;
		
		setContentView(R.layout.video);
		
		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		url+="?t=35ABD19D8E214C95BA31C46F114600A9";

		MediaController mc = new MediaController(this);
		
		
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		videoView.setVideoPath(url);
		videoView.setMediaController(mc);
		mc.setAnchorView(videoView);
		videoView.requestFocus();
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
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
