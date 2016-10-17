package appPackage;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class UIFocusPane extends BorderPane {
	
	private TVShow focusedShow;
	
	public UIFocusPane(int TVShowId) {
		
		setFocusedShow(new TVShow(TVShowId));
	    
	    ImageView poster = new ImageView(SwingFXUtils.toFXImage(this.focusedShow.getPoster(), null));
	    poster.setPreserveRatio(true);
	    poster.setFitWidth(385);
	    BorderPane imageContainer = new BorderPane();
	    imageContainer.setPadding(new Insets(10, 10, 10, 10));
	    imageContainer.setCenter(poster);
	    
	    this.setRight(imageContainer);
	    
	    
	    UIDetailPane details = new UIDetailPane(this.focusedShow);
	    this.setLeft(details);
	}

	public TVShow getFocusedShow() {
		return focusedShow;
	}

	public void setFocusedShow(TVShow focusedShow) {
		this.focusedShow = focusedShow;
	}
}
