package appPackage;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;


public class UIListPane extends ScrollPane {
	
	public UIListPane(ArrayList<TVShow> tvshows, UIApplication application, Scene scene, Boolean notification) {
		FlowPane list = new FlowPane();
		list.setPadding(new Insets(10, 10, 10, 10));
		list.setVgap(20);
		list.setHgap(20);
		list.setPrefWrapLength(300);
		list.setStyle("-fx-background-color: #efefef;");

		Iterator<TVShow> it = tvshows.iterator();
		
		while (it.hasNext()){
			TVShow show = it.next();
			UIDynamicImage poster = new UIDynamicImage(show);
			poster.setUpController();
			
			poster.addObserver(application);
			
			show.fetchPoster();
			poster.setPreserveRatio(true);
			poster.setFitWidth(185);


			BorderPane container = new BorderPane();

			if (notification==Boolean.TRUE && show.getNextEpisodeisSoon()==Boolean.TRUE) {
				container.setCenter(new UINotification(poster));
			}
			else{
				container.setCenter(poster);
			}
			Label title = new Label(show.getName());
			title.setMaxWidth(180);
			title.setAlignment(Pos.BASELINE_CENTER);
			container.setBottom(title);

			list.getChildren().addAll(container);


			//Setting an action on the click of the poster
			poster.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					poster.notifyObservers(poster.getTVShow());
				}
			});

			//Setting cursor as pointer when mouse enter a poster
			poster.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					scene.setCursor(Cursor.HAND);
				}
			});

			//Setting cursor back as normal when mouse exit a poster
			poster.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					scene.setCursor(Cursor.DEFAULT);
				}
			});
		}

	    list.setPrefSize(840, 600);

        this.setStyle("-fx-background-color:transparent;");
	    
	    this.setFitToWidth(true);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setContent(list);
	}
}
