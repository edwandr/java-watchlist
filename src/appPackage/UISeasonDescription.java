package appPackage;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Iterator;

public class UISeasonDescription extends BorderPane implements Observer{
    public UISeasonDescription(){
    }

    public void update (TVSeason season) {
        VBox description = new VBox();

        Iterator<TVEpisode> it = season.getEpisodes().iterator();

        while (it.hasNext()){
            TVEpisode episode = it.next();

            Text title = new Text(episode.getName());
            VBox.setMargin(title, new Insets(0, 0, 7, 0));
            title.setFont(Font.font("Verdana", 15));
            title.setWrappingWidth(420);

            Text content = new Text(episode.getOverview());
            VBox.setMargin(content, new Insets(0, 0, 20, 0));
            content.setWrappingWidth(420);

            description.getChildren().addAll(title, content);
        }

        VBox.setMargin(description, new Insets(10, 0, 20, 20));
        this.setCenter(description);
    }
}
