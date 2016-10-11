package appPackage;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;


public class UIListPane extends ScrollPane {
	public UIListPane(ArrayList<TVShow> tvshows) {
		FlowPane list = new FlowPane();
		list.setPadding(new Insets(20, 20, 20, 20));
		list.setVgap(20);
		list.setHgap(20);
		list.setPrefWrapLength(300);
		list.setStyle("-fx-background-color: #efefef;");

		Iterator<TVShow> it = tvshows.iterator();
		
		while (it.hasNext()){
			try {
		    	list.getChildren().addAll( new ImageView(SwingFXUtils.toFXImage(it.next().getPoster(), null)));
			}
			catch (NullPointerException e)
			{
				// TODO Mettre une image par défaut si le poster n'est pas trouvé
			}
		}
	    
	    list.setPrefSize(840, 600);
	    
	    this.setFitToWidth(true);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setContent(list);
	}
}
