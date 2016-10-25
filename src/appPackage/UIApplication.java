package appPackage;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class UIApplication extends BorderPane implements Observer {

	Scene scene;
	User user;

	public UIApplication(Scene scene, User user) {
		this.scene = scene;
		this.user = user;
		// Récupération de toutes les données utilisateur à l'initialisation de l'application
		this.user.loadUser();
	}

	public void update(String query) {
		ArrayList<TVShow> searchList = TVShow.searchTVShows(query);
		UIListPane newListPane = new UIListPane(searchList, this, this.scene);
		this.setCenter(newListPane);
	}
	
	public void update(Boolean type){
		if (type == Boolean.TRUE){
			ArrayList<TVShow> list = TVShow.getPopularTVShows();
			UIListPane newListPane = new UIListPane(list, this, this.scene);
			this.setCenter(newListPane);
		}
		else {
			UIListPane newListPane = new UIListPane(User.favorite, this, this.scene);
			this.setCenter(newListPane);
		}

	}
	
	public void update(int tVshowId){
		UIFocusPane focusPane = new UIFocusPane(tVshowId);
		this.setCenter(focusPane);
	}

}
