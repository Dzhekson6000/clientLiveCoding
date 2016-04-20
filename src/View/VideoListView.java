package View;

import screen.VideoActivity;
import Data.ViewVideosItem;
import View.HttpWraper.OnHttpResponse;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class VideoListView extends ListView
{
	
	public enum Type { 
		ALL_STREAM,
		ALL_VIDEOS,
		MY_VIDEOS
		}
	
	private int limit=30;
	private int offset=0;
	
	private Type type = Type.ALL_STREAM;
	
	private VideoListAdapter adapter = null;
	
	public VideoListView(Context context) {
		super(context);
		init();
	}
	public VideoListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();	
	}
	public VideoListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();		
	}
	
	private void init()
	{
		adapter = new VideoListAdapter(getContext());
		setAdapter(adapter);
		
		setOnItemClickListener(
				new AdapterView.OnItemClickListener()
				{@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					{
						onClickItem(position);
					}});
	}
	
	public void onClickItem(int position)
	{
		ViewVideosItem vvi = (ViewVideosItem) adapter.getItem(position);
		Intent intent = new Intent(getContext(), VideoActivity.class);
		intent.putExtra("url", vvi.viewing_url);
		getContext().startActivity(intent);
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public boolean load()
	{		
		String url = null;
		if(type == VideoListView.Type.ALL_STREAM)
        {
			url = "https://www.livecoding.tv/api/livestreams/onair/?format=json&limit="+limit+"&offset="+offset;
        }
        else if(type == VideoListView.Type.ALL_VIDEOS)
		{
        	url = "https://www.livecoding.tv:443/api/videos/?format=json&limit="+limit+"&offset="+offset;
		}
        else if(type == VideoListView.Type.MY_VIDEOS)
        {
        	url = "https://www.livecoding.tv:443/api/user/videos/?format=json&limit="+limit+"&offset="+offset;
        }
        else
        {
        	return false;
        }
		
		HttpWraper httpWraper = new HttpWraper();
		httpWraper.setOnHttpResponse(new OnHttpResponse()
		{
			@Override
			public void onRead(String response)
			{
				adapter.parse(type, response);
				adapter.notifyDataSetChanged();
			}
		});
		httpWraper.loadText(url);
		return true;	                
	}
	
	
}
