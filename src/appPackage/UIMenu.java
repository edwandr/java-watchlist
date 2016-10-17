package appPackage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

public class UIMenu extends VBox {
	public UIMenu(UIDynamicLink shows) {
		this.setPadding(new Insets(10));
		this.setSpacing(8);
		this.setStyle("-fx-background-color: #ede8e3;");
		
		VBox.setMargin(shows, new Insets(0, 0, 0, 8));
        this.getChildren().add(shows);
        
      //Setting an action for the Submit button
        shows.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	        public void handle(ActionEvent e) {           
	    		shows.notifyObservers();
	         }
	     });
        
        Hyperlink favorites = new Hyperlink("Favorites shows");
        VBox.setMargin(favorites, new Insets(0, 0, 0, 8));
        this.getChildren().add(favorites);
	    
	    this.setPrefHeight(600);
	}
}
