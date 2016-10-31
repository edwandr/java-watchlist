package appPackage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class UIDetailPane extends ScrollPane {
	public UIDetailPane(TVShow tvshow, UIFavoriteButton favButton){
		VBox details = new VBox();
		// Adding title
	    Text title = new Text(tvshow.getName());
	    title.setFont(Font.font("Verdana", 40));
	   	VBox.setMargin(title, new Insets(15, 0, 0, 20));
	   	title.setWrappingWidth(420);
		details.getChildren().add(title);
	    
	   	// Adding description of the tvshow
	    Text description = new Text(tvshow.getOverview());
	    VBox.setMargin(description, new Insets(15, 0, 0, 20));
	    description.setWrappingWidth(420);
		details.getChildren().add(description);
	    
	    // Adding creator and genres with bold style
	    Text t1 = new Text("Created by : ");
	    t1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	    Text author = new Text(tvshow.getCreator() + ".");
	    Text t2 = new Text(" Genres : ");
	    t2.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	    Text genre = new Text(tvshow.getGenre());
	    
	    TextFlow extra = new TextFlow();
	    extra.getChildren().addAll(t1, author, t2, genre);
	    VBox.setMargin(extra, new Insets(15, 0, 0, 20));
	    extra.setPrefWidth(420);
		details.getChildren().add(extra);

	    // Adding next episode date 
	    Text nextEpisode = new Text("The next episode will be available episode will be available " + tvshow.getNextAiringTime().toString());
	    nextEpisode.setFill(Color.RED);
	    VBox.setMargin(nextEpisode, new Insets(15, 0, 0, 20));
	    nextEpisode.setWrappingWidth(420);
		details.getChildren().add(nextEpisode);
		
	    // Adding favorite button
		VBox.setMargin(favButton, new Insets(15, 0, 20, 20));
		details.getChildren().add(favButton);

		// Adding Episodes and Seasons
		Text seasonTitle = new Text("Seasons");
		seasonTitle.setFont(Font.font("Verdana", 18));
		VBox.setMargin(seasonTitle, new Insets(15, 0, 5, 20));
		details.getChildren().add(seasonTitle);

		FlowPane seasonContainer = new FlowPane();
		seasonContainer.setMaxWidth(420);
		UISeasonDescription desc = new UISeasonDescription();


		for (int i = 1; i<tvshow.getNbSeasons()+1; i++)
		{
			int index = i;
			UISeasonButton seasButton = new UISeasonButton(String.valueOf(i));
			seasonContainer.getChildren().addAll(seasButton);
			FlowPane.setMargin(seasButton, new Insets(0, 7, 5, 0));
			seasButton.addObserver(desc);

			//Setting an action for the season button
			seasButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {
					seasButton.notifyObservers(new TVSeason(tvshow.getId(), index));
				}
			});
		}
		VBox.setMargin(seasonContainer, new Insets(15, 0, 20, 20));
		details.getChildren().add(seasonContainer);

		VBox.setMargin(desc, new Insets(15, 0, 20, 20));
		details.getChildren().add(desc);

		details.setPrefSize(461, 600);

		this.setStyle("-fx-background-color:transparent;");
		this.setFitToWidth(true);
		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.setContent(details);


		//Setting an action for the favorite button
		favButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (favButton.getType() == Boolean.TRUE){
					User.addFavorite(tvshow.getId());
					favButton.notifyObservers(favButton.getType());
				}
				else {
					User.removeFavorite(tvshow.getId());
					favButton.notifyObservers(favButton.getType());
				}
			}
		});
	}
}
