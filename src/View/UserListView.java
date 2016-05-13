package View;

import screen.ProfileActivity;
import Data.User;
import View.HttpWraper.OnHttpResponse;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class UserListView extends ListView
{
	
	public enum Type { 
		FALLOWS,
		FALLOWERS,
		}
	
	private Type type = Type.FALLOWS;
	
	private UserListAdapter adapter = null;
	
	public UserListView(Context context) {
		super(context);
		init();
	}
	public UserListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();	
	}
	public UserListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();		
	}
	
	private void init()
	{
		adapter = new UserListAdapter(getContext());
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
		Intent intent = new Intent(getContext(), ProfileActivity.class);
		intent.putExtra("slug", ((User)adapter.getItem(position)).slug);
		getContext().startActivity(intent);
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public boolean load()
	{		
		String url = null;
		if(type == UserListView.Type.FALLOWS)
        {
			url = "https://www.livecoding.tv:443/api/user/follows/?format=json";
        }
        else if(type == UserListView.Type.FALLOWERS)
		{
        	url = "https://www.livecoding.tv:443/api/user/followers/?format=json";
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
				adapter.parse(response);
				adapter.notifyDataSetChanged();
			}
		});
		httpWraper.loadText(url);
		return true;	                
	}
	
	
}
