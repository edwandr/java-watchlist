package appPackage;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class UISearchBar extends HBox {
	public UISearchBar() {
		this.setPadding(new Insets(15, 12, 15, 12));
		this.setSpacing(10);
		this.setStyle("-fx-background-color: #efdecd;");

	    Label brand = new Label("Java-watchlist");
	    brand.setMinWidth(125);
	    
	    TextField textField = new TextField ();
	    textField.setMinWidth(500);

	    Button buttonCurrent = new Button("Search");
	    

	    this.getChildren().addAll(brand, textField, buttonCurrent);

	    this.setPrefWidth(1000);
	}
}
