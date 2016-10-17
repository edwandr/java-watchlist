package appPackage;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;


public class UIListPane extends ScrollPane {
	public UIListPane(ArrayList<TVShow> tvshows, UIApplication application) {
		FlowPane list = new FlowPane();
		list.setPadding(new Insets(20, 20, 20, 20));
		list.setVgap(20);
		list.setHgap(20);
		list.setPrefWrapLength(300);
		list.setStyle("-fx-background-color: #efefef;");

		Iterator<TVShow> it = tvshows.iterator();
		
		while (it.hasNext()){
			try {
				TVShow show = it.next();
				UIDynamicImage poster = new UIDynamicImage(SwingFXUtils.toFXImage(show.getPoster(), null), show.getId());
				poster.addObserver(application);
		    	list.getChildren().addAll(poster);
		    	
		    	//Setting an action on the click of the poster
		    	poster.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		    	     @Override
		    	     public void handle(MouseEvent event) {
		    	    	 poster.notifyObservers(poster.getTVShowId());
		    	     }
		    	});
			}
			catch (NullPointerException e)
			{
				// TODO Mettre une image par défaut si le poster n'est pas trouvé
			}
		}
	    
	    list.setPrefSize(840, 600);
	    
	    this.setFitToWidth(true);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setContent(list);
	}
}
