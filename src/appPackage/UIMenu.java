package appPackage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Iterator;

public class UIMenu extends VBox {
	public UIMenu(ArrayList<UIDynamicLink> links) {
		this.setPadding(new Insets(10));
		this.setSpacing(8);
		this.setStyle("-fx-background-color: #ede8e3;");

		Iterator<UIDynamicLink> it = links.iterator();
		while (it.hasNext()){
			UIDynamicLink link = it.next();
			VBox.setMargin(link, new Insets(0, 0, 0, 8));
			this.getChildren().add(link);

			//Setting an action for the Submit button
			link.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {
					link.notifyObservers(link.getType());
				}
			});
		}

	    this.setPrefHeight(600);
	}
}
