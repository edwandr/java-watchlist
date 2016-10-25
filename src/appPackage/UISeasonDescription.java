package appPackage;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class UISeasonDescription extends BorderPane implements Observer{
    public UISeasonDescription(){
    }

    public void update (TVSeason season) {
        VBox description = new VBox();
        description.getChildren().addAll(new Label(season.getName()));
        VBox.setMargin(description, new Insets(15, 0, 20, 20));
        this.setCenter(description);
    }
}
