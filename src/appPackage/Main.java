package appPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;


public class Main {
	
	public static String apiKey = "0ad8c862866c0f99ff7ea5a58309fc13";
	
	public static void main(String[] args) {
		ArrayList<TVShow> list = TVShow.searchTVShows("new");
		Iterator<TVShow> it = list.iterator();
		
		while(it.hasNext()) System.out.println(it.next().toString());
		
	}
	
	public static JSONObject getJSONAtURL(String myURL) throws JSONException {
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				//Timeout at 5 seconds
				urlConn.setReadTimeout(5 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			
		}
		
		try{
			return new JSONObject(sb.toString());
		}
		catch(JSONException e){
			throw new JSONException("Bad JSON");
		}
	}
	
	
	
}
