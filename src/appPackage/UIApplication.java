package appPackage;

import java.util.ArrayList;

import javafx.scene.layout.BorderPane;

public class UIApplication extends BorderPane implements Observer {
	
	public void update(String query) {
		ArrayList<TVShow> searchList = TVShow.searchTVShows(query);
		UIListPane newListPane = new UIListPane(searchList);
		this.setCenter(newListPane);
	}

}
