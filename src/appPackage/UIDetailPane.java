package appPackage;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class UIDetailPane extends VBox {
	public UIDetailPane(TVShow tvshow){	   
		// Adding title
	    Text title = new Text(tvshow.getName());
	    title.setFont(Font.font("Verdana", 40));
	   	VBox.setMargin(title, new Insets(15, 0, 0, 20));
	   	title.setWrappingWidth(420);
	   	this.getChildren().add(title);
	    
	   	// Adding description of the tvshow
	    Text description = new Text(tvshow.getOverview());
	    VBox.setMargin(description, new Insets(15, 0, 0, 20));
	    description.setWrappingWidth(420);
	    this.getChildren().add(description);
	    
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
	    this.getChildren().add(extra);

	    // Adding next episode date 
	    Text nextEpisode = new Text("The next episode will be available on " + tvshow.getNextAiringTime().toString());
	    nextEpisode.setFill(Color.RED);
	    VBox.setMargin(nextEpisode, new Insets(15, 0, 0, 20));
	    nextEpisode.setWrappingWidth(420);
	    this.getChildren().add(nextEpisode);
		
	    // Adding remove favorite button
		Button removeFavorite = new Button("Remove Favorite");
	   	VBox.setMargin(removeFavorite, new Insets(15, 0, 0, 20));
		this.getChildren().add(removeFavorite);
	}
}
