package appPackage;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class UIFocusPane extends BorderPane implements Observer {
	
	private TVShow focusedShow;
	
	public UIFocusPane(TVShow tVShow) {
		
		setFocusedShow(tVShow);
	    
	    UIDynamicImage poster = new UIDynamicImage(tVShow);
		poster.setPreserveRatio(true);
	    poster.setFitWidth(370);
		BorderPane imageContainer = new BorderPane();
	    imageContainer.setPadding(new Insets(10, 10, 10, 10));
	    imageContainer.setCenter(poster);
	    
	    this.setRight(imageContainer);
	    
	    poster.setUpController();
	    tVShow.fetchBigPoster();

		if (User.isInFavorite(tVShow.getId())){
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
