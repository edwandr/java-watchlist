package appPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application{

	public static String apiKey = "0ad8c862866c0f99ff7ea5a58309fc13";

	public static void main(String... args) {
		//
		//System.out.println(simpsons.toString());
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JavaWatchlist");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(680);
        primaryStage.setResizable(false);

		User user = new User();

        UIApplication application = new UIApplication(scene, user);
		root.getChildren().add(application);

		ArrayList<UIDynamicLink> links = new ArrayList<>();
		UIDynamicLink shows = new UIDynamicLink("Featured shows", "featured");
        shows.addObserver(application);
		links.add(shows);
		UIDynamicLink favorites = new UIDynamicLink("Favorite shows", "favorites");
		favorites.addObserver(application);
		links.add(favorites);

		UIMenu menu = new UIMenu(links);
        application.setLeft(menu);

		ArrayList<TVShow> tvshows = TVShow.getPopularTVShows();
        UIListPane listPane = new UIListPane(tvshows, application, scene, Boolean.FALSE);
        application.setCenter(listPane);
                
        UISearchButton search = new UISearchButton("Search");
        search.addObserver(application);
        UISearchBar searchbar = new UISearchBar(search);
        application.setTop(searchbar);

        primaryStage.setScene(scene);
        primaryStage.show();
        
        
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
