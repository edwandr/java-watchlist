package appPackage;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class UIApplication extends BorderPane implements Observer {

	Scene scene;

	public UIApplication(Scene scene) {
		this.scene = scene;
	}

	public void update(String query) {
		ArrayList<TVShow> searchList = TVShow.searchTVShows(query);
		UIListPane newListPane = new UIListPane(searchList, this, this.scene);
		this.setCenter(newListPane);
	}
	
	public void update(){
		ArrayList<TVShow> list = TVShow.getPopularTVShows();
		UIListPane newListPane = new UIListPane(list, this, this.scene);
		this.setCenter(newListPane);
	}
	
	public void update(int tVshowId){
		UIFocusPane focusPane = new UIFocusPane(tVshowId);
		this.setCenter(focusPane);
	}

}
