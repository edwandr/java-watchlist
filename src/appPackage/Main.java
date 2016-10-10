package appPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage; 


public class Main extends Application{
	
	public static String apiKey = "0ad8c862866c0f99ff7ea5a58309fc13";
	
	public static void main(String... args) { 
		//TVShow simpsons = new TVShow(Integer.parseInt("456"));
		//System.out.println(simpsons.toString());
		Application.launch(args); 
	} 
	
	@Override 
	public void start(Stage primaryStage) throws Exception { 
		primaryStage.setTitle("Java-watchlist");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        
        BorderPane application = new BorderPane();
        root.getChildren().add(application);
        
        HBox searchbar = addSearchBar();
        application.setTop(searchbar);
        
        VBox menu = addMenu();
        application.setLeft(menu);

        primaryStage.setScene(scene);
        primaryStage.show();
	} 
	
	public HBox addSearchBar() {
	    HBox searchbar = new HBox();
	    searchbar.setPadding(new Insets(15, 12, 15, 12));
	    searchbar.setSpacing(10);
	    searchbar.setStyle("-fx-background-color: #efdecd;");

	    Label brand = new Label("Java-watchlist");
	    brand.setMinWidth(125);
	    
	    TextField textField = new TextField ();
	    textField.setMinWidth(500);

	    Button buttonCurrent = new Button("Search");
	    

	    searchbar.getChildren().addAll(brand, textField, buttonCurrent);

	    searchbar.setPrefWidth(1000);

	    return searchbar;
	}
	
	public VBox addMenu() {
		VBox menu = new VBox();
		menu.setPadding(new Insets(10));
		menu.setSpacing(8);
	    menu.setStyle("-fx-background-color: #ede8e3;");
		
	    Hyperlink options[] = new Hyperlink[] {
	            new Hyperlink("Featured shows"),
	            new Hyperlink("Favorites shows")};
	    
	    for (int i=0; i<2; i++) {
	        VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
	        menu.getChildren().add(options[i]);
	    }
	    
	    menu.setPrefHeight(600);
		
		return menu;
	}
 
	public static JSONObject getJSONAtURL(String myURL) {
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
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
			throw new RuntimeException("Exception while calling URL:" + myURL, e);
		}
 
		return new JSONObject(sb.toString());
	}
}
