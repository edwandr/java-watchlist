package appPackage;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
			try {
				TVShow show = it.next();
				UIDynamicImage poster = new UIDynamicImage(SwingFXUtils.toFXImage(show.getPoster(), null), show);
				poster.addObserver(application);


				BorderPane container = new BorderPane();

				if (notification == Boolean.TRUE) {
					if (show.getNextEpisodeisSoon() == Boolean.TRUE)
					{
						UINotification notifiedPoster = new UINotification(poster);
						container.setCenter(notifiedPoster);
					}
					else
					{
						container.setCenter(poster);
					}
				}
				else
				{
					container.setCenter(poster);
				}
				Label title = new Label(show.getName());
				title.setMaxWidth(180);
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
			catch (NullPointerException e)
			{
				// TODO Mettre une image par défaut si le poster n'est pas trouvé
			}
		}
	    
	    list.setPrefSize(840, 600);

        this.setStyle("-fx-background-color:transparent;");
	    
	    this.setFitToWidth(true);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setContent(list);
	}
}
