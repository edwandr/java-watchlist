package appPackage;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Iterator;

public class UISeasonDescription extends BorderPane implements Observer{
    public UISeasonDescription(){
    }

    public void update (TVSeason season) {
        VBox description = new VBox();
        description.getChildren().addAll(new Label(season.getName()));

        Iterator<TVEpisode> it = season.getEpisodes().iterator();

        while (it.hasNext()){
            TVEpisode episode = it.next();
            description.getChildren().addAll(new Text(episode.getName()), new Text(episode.getOverview()));
        }
        VBox.setMargin(description, new Insets(15, 0, 20, 20));
        this.setCenter(description);
    }
}
