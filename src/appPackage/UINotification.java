package appPackage;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UINotification extends StackPane {
    public UINotification(UIDynamicImage poster)
    {

        Circle notif1 = new Circle();
        notif1.setRadius(12.0f);
        notif1.setFill(Color.RED);
        StackPane.setMargin(notif1, new Insets(0, 0, 262, 167));
        Circle notif2 = new Circle();
        notif2.setRadius(2.0f);
        notif2.setFill(Color.WHITE);
        StackPane.setMargin(notif2, new Insets(0, 0, 262, 167));

        this.getChildren().addAll(poster, notif1, notif2);
    }
}
