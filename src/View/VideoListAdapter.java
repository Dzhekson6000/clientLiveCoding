package View;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sit.clientlivecoding.R;

import Data.ViewVideosItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class VideoListAdapter extends BaseAdapter
{
	Context ctx;
	LayoutInflater inflater;
		
	ArrayList<ViewVideosItem> objects = new ArrayList<ViewVideosItem>();;
	
	public VideoListAdapter(Context context)
	{
		super();
		ctx = context;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	public boolean parse(VideoListView.Type type, String resultJson)
	{
		objects.clear();
		JSONObject dataJsonObj = null;

        try {
            dataJsonObj = new JSONObject(resultJson);
            
            JSONArray results = dataJsonObj.getJSONArray("results");
            
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                
                ViewVideosItem vvi = new ViewVideosItem();
                
                if(type == VideoListView.Type.ALL_STREAM)
                {
                	
					vvi.isVideos = false;
					vvi.slug=result.getString("user__slug");
					vvi.tags=result.getString("tags");
					vvi.is_live = result.getBoolean("is_live");
					vvi.viewers_live = result.getInt("viewers_live");
                }
                else if(type == VideoListView.Type.ALL_VIDEOS || type == VideoListView.Type.MY_VIDEOS)
				{
					vvi.isVideos = true;
					vvi.creation_time = result.getString("creation_time");
					vvi.duration = result.getInt("duration");
					vvi.viewers_overall = result.getInt("viewers_overall");
				}

                vvi.title=result.getString("title");
                vvi.viewing_url=result.getJSONArray("viewing_urls").getString(0);
                vvi.user=result.getString("user");
                vvi.description=result.getString("description");
                vvi.coding_category=result.getString("coding_category");
                vvi.difficulty=result.getString("difficulty");
                vvi.language=result.getString("language");
                
        		objects.add(vvi);
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

		return true;
	}
	
	@Override
	public int getCount()
	{
		return objects.size();
	}

	@Override
	public Object getItem(int position)
	{
		return objects.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if (view == null)
		{
			view = inflater.inflate(R.layout.item, parent, false);
		}
		
		objects.get(position).viewPoll(view);
		return view;
	}
}
