package co.tashawych.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpRequest {
	
	public static String GET(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		String response = "";
		
		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			response = httpclient.execute(request, handler);
			Log.d("GET response", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		httpclient.getConnectionManager().shutdown();
		return response;
	}
	
	public static String POST(String url, StringEntity entity) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		String str_response = "";
		
		request.setEntity(entity);
//		request.setHeader("Accept", "application/json");
//		request.setHeader("Content-type", "application/json");
		
		try {
			HttpResponse response = httpclient.execute(request);
			InputStreamReader isReader = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
			BufferedReader reader = new BufferedReader(isReader);
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line);
			}
			str_response = builder.toString();
			Log.d("POST response", str_response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		httpclient.getConnectionManager().shutdown();
		return str_response;
	}

}
