package appPackage;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class UISearchBar extends HBox {
	String query;
	
	public UISearchBar(UISearchButton submit) {
		this.setPadding(new Insets(15, 12, 15, 12));
		this.setSpacing(10);
		this.setStyle("-fx-background-color: #efdecd;");

		Image logo = new Image("java_watchlist_favicon.png");
		ImageView brand = new ImageView();
		brand.setImage(logo);
		brand.setFitWidth(130);
		brand.setPreserveRatio(true);

	    TextField textField = new TextField ();
	    textField.setMinWidth(500);
	    textField.setPromptText("Search a TVShow...");
	    
	  	//Setting an action for the Submit button
	    submit.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	        public void handle(ActionEvent e) {
	            if ((textField.getText() != null && !textField.getText().isEmpty())) {
	                query = textField.getText();
	            } else {
	                //TODO Afficher une erreur si le champ est vide
	            }
	            
	    		submit.notifyObservers(query);
	         }
	     });

	    this.getChildren().addAll(brand, textField, submit);
	    this.setPrefWidth(1000);
	}
}
