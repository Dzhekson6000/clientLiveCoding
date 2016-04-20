package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import screen.AuthActivity;

import android.os.Handler;
import android.os.Message;



public class HttpWraper
{
	public interface OnHttpResponse
	{
		void onRead(String response);
	}
	
	OnHttpResponse onHttpResponse;
	
	public HttpWraper()
	{
		
	}
	
	public void loadText(final String path)
	{
		 final Handler handler = new Handler()
         {
         	@Override
         	public void handleMessage(Message message)
         	{
         		final String response = (String) message.obj;
         		onHttpResponse.onRead(response);
         	}
         };
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(path);
					
					HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			        connect.setReadTimeout(10000);
			        connect.setConnectTimeout(15000);
			        connect.setRequestMethod("GET");
			        connect.addRequestProperty("Authorization", "Bearer "+AuthActivity.token);
			        connect.setDoInput(true);
			        connect.connect();
					
			        if(connect.getResponseCode() != 200)return;
			        
			        InputStream inputStream = connect.getInputStream();
	                StringBuffer buffer = new StringBuffer();
	 
	                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	 
	                String line;
	                while ((line = reader.readLine()) != null)
	                {
	                    buffer.append(line);
	                }
	                
	                if(onHttpResponse==null)return;
	                
	               
          	        if ( buffer != null)
          	        {
          	        	final Message message = handler.obtainMessage(1, buffer.toString());
          	        	handler.sendMessage(message);
          	        }   
					
				} catch (MalformedURLException e)
				{
					e.printStackTrace();
				} catch (ProtocolException e)
				{
					e.printStackTrace();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void setOnHttpResponse(OnHttpResponse onHttpResponse)
	{
		this.onHttpResponse = onHttpResponse;
	}
}
