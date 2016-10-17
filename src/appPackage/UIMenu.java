package appPackage;

import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

public class UIMenu extends VBox {
	public UIMenu() {
		this.setPadding(new Insets(10));
		this.setSpacing(8);
		this.setStyle("-fx-background-color: #ede8e3;");
		
	    Hyperlink options[] = new Hyperlink[] {
	            new Hyperlink("Featured shows"),
	            new Hyperlink("Favorites shows")};
	    
	    for (int i=0; i<2; i++) {
	        VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
	        this.getChildren().add(options[i]);
	    }
	    
	    this.setPrefHeight(600);
	}
}
