package View;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

import com.sit.clientlivecoding.R;

import Data.User;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class UserListAdapter extends BaseAdapter
{
	Context ctx;
	LayoutInflater inflater;
		
	ArrayList<User> objects = new ArrayList<User>();;
	
	public UserListAdapter(Context context)
	{
		super();
		ctx = context;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	public boolean parse(String resultJson)
	{
		objects.clear();
		JSONArray results = null;

        try {
        	results = new JSONArray(resultJson);
                        
            for (int i = 0; i < results.length(); i++) {
                String slug = results.getJSONObject(i).getString("slug");
                
                User user = new User();
                user.setOnParse(
    					new User.OnParse()
    					{
    						@Override
    						public void onParse(User user)
    						{
    							objects.add(user);
    							notifyDataSetInvalidated();
    						}
    					});
    			user.load(slug);
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
			view = inflater.inflate(R.layout.user_list_item, parent, false);
		}
		
		objects.get(position).viewPoll(view);
		return view;
	}
}
