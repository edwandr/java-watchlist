package appPackage;

import java.util.ArrayList;

import javafx.scene.layout.BorderPane;

public class UIApplication extends BorderPane implements Observer {
	
	public void update(String query) {
		ArrayList<TVShow> searchList = TVShow.searchTVShows(query);
		UIListPane newListPane = new UIListPane(searchList, this);
		this.setCenter(newListPane);
	}
	
	public void update(){
		ArrayList<TVShow> list = TVShow.getPopularTVShows();
		UIListPane newListPane = new UIListPane(list, this);
		this.setCenter(newListPane);
	}
	
	public void update(int tVshowId){
		UIFocusPane focusPane = new UIFocusPane(tVshowId);
		this.setCenter(focusPane);
	}

}
