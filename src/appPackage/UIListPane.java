package appPackage;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;


public class UIListPane extends ScrollPane {
	public UIListPane(TVShow[] tvshows) {
		FlowPane list = new FlowPane();
		list.setPadding(new Insets(20, 20, 20, 20));
		list.setVgap(20);
		list.setHgap(20);
		list.setPrefWrapLength(300);
		list.setStyle("-fx-background-color: #efefef;");

	    for (int i=0; i < tvshows.length; i++) {
	    	list.getChildren().addAll( new ImageView(SwingFXUtils.toFXImage(tvshows[i].poster, null)));
	    }
	    
	    list.setPrefSize(840, 600);
	    
	    this.setFitToWidth(true);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setContent(list);
	}
}
