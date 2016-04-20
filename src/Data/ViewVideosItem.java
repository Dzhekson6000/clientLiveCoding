package Data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.sit.clientlivecoding.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewVideosItem
{
	public String slug;
	public String user;
	public String title;
	public String description;
	public String coding_category;
	public String difficulty;
	public String language;
	public String tags;
	public boolean is_live;
	public int viewers_live;
	public String viewing_url;
	public String creation_time;
	public int duration;
	public String region;
	public int viewers_overall;
	public String product_type;
	
	public boolean isVideos;
	
	public static Map<String, Bitmap> mapImage = new HashMap<String, Bitmap>();
	
	public ViewVideosItem()
	{
	}
	
	public boolean viewPoll(View view)
	{
		TextView text1 = (TextView)view.findViewById(R.id.title);
		text1.setText(title);
		
		TextView text2 = (TextView)view.findViewById(R.id.textView2);
		text2.setText(description);
		
		ImageView imageView  = (ImageView)view.findViewById(R.id.ImageView1);
		
		if(!isVideos)
		{
			fetchImage("https://www.livecoding.tv/video/livestream/" + slug + "/thumbnail_250_140/", imageView);
		}
		else
		{
			imageView.setImageResource(R.drawable.logo_top);
		}
		
		return true;
	}
	
	
	public static void fetchImage(final String iUrl, final ImageView iView) {
	    if ( iUrl == null || iView == null )
	      return;
	 
	    final Handler handler = new Handler() {
	      @Override
	      public void handleMessage(Message message) {
	        final Bitmap image = (Bitmap) message.obj;
	        iView.setImageBitmap(image);
	      }
	    };
	 
	    final Thread thread = new Thread() {
	      @Override
	      public void run() {
	        final Bitmap image = downloadImage(iUrl);
	        if ( image != null ) {
	          final Message message = handler.obtainMessage(1, image);
	          handler.sendMessage(message);
	        }
	      }
	    };
	    iView.setImageResource(R.drawable.logo_top);
	    thread.setPriority(3);
	    thread.start();
	  }
	
	public static Bitmap downloadImage(String iUrl) {
		Bitmap bitmap = mapImage.get(iUrl);
		if(bitmap!=null)return bitmap;
		
	    HttpURLConnection conn = null;
	    BufferedInputStream buf_stream = null;
	    try {
	      conn = (HttpURLConnection) new URL(iUrl).openConnection();
	      conn.setDoInput(true);
	      conn.setRequestProperty("Connection", "Keep-Alive");
	      conn.connect();
	      buf_stream = new BufferedInputStream(conn.getInputStream(), 8192);
	      bitmap = BitmapFactory.decodeStream(buf_stream);
	      buf_stream.close();
	      conn.disconnect();
	      buf_stream = null;
	      conn = null;
	    } catch (Exception ex) {
	      return null;
	    } finally {
	      if ( buf_stream != null )
	        try { buf_stream.close(); } catch (IOException ex) {}
	      if ( conn != null )
	        conn.disconnect();
	    }
	    
	    mapImage.put(iUrl, bitmap);
	    
	    return bitmap;
	  }
	
	
}
