package appPackage;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class UIApplication extends BorderPane implements Observer {

	Scene scene;

	public UIApplication(Scene scene) {

		this.scene = scene;
		this.setStyle("-fx-background-color: #efefef;");
	}
	
	
	public void update(String query) {
		ArrayList<TVShow> searchList = TVShow.searchTVShows(query);
		UIListPane newListPane = new UIListPane(searchList, this, this.scene, Boolean.FALSE);
		this.setCenter(newListPane);
	}
	
	public void update(Boolean type){
		if (type == Boolean.TRUE){
			ArrayList<TVShow> list = TVShow.getPopularTVShows();
			UIListPane newListPane = new UIListPane(list, this, this.scene, Boolean.TRUE);
			this.setCenter(newListPane);
		}
		else {
			UIListPane newListPane = new UIListPane(User.getFavorite("airingTime"), this, this.scene, Boolean.TRUE);
			this.setCenter(newListPane);
		}

	}
	
	public void update(TVShow tVshow){
		UIFocusPane focusPane = new UIFocusPane(tVshow);
		this.setCenter(focusPane);
	}

}
