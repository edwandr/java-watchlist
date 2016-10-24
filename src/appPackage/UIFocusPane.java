package appPackage;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class UIFocusPane extends BorderPane implements Observer {
	
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

		if (User.isInFavorite(TVShowId)){
			UIFavoriteButton favButton = new UIFavoriteButton("Remove Favorite", Boolean.FALSE);
			favButton.addObserver(this);
			UIDetailPane details = new UIDetailPane(this.focusedShow, favButton);
			this.setLeft(details);
		}
		else {
			UIFavoriteButton favButton = new UIFavoriteButton("Add Favorite", Boolean.TRUE);
			favButton.addObserver(this);
			UIDetailPane details = new UIDetailPane(this.focusedShow, favButton);
			this.setLeft(details);
		}

	}

	public TVShow getFocusedShow() {
		return focusedShow;
	}

	public void setFocusedShow(TVShow focusedShow) {
		this.focusedShow = focusedShow;
	}

	public void update(Boolean type){
		if (type == Boolean.TRUE)
		{
			UIFavoriteButton favButton = new UIFavoriteButton("Remove Favorite", Boolean.FALSE);
			favButton.addObserver(this);
			UIDetailPane newDetails = new UIDetailPane(this.focusedShow, favButton);
			this.setLeft(newDetails);
		}
		else {
			UIFavoriteButton favButton = new UIFavoriteButton("Add Favorite", Boolean.TRUE);
			favButton.addObserver(this);
			UIDetailPane newDetails = new UIDetailPane(this.focusedShow, favButton);
			this.setLeft(newDetails);
		}
	}
}
