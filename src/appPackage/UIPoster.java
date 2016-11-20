package appPackage;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UIPoster extends StackPane {
    public UIPoster(UIDynamicImage poster, Boolean notification)
    {
    	
        Circle notif1 = new Circle();
        notif1.setRadius(12.0f);
        StackPane.setMargin(notif1, new Insets(0, 0, 275, 175));
        Circle notif2 = new Circle();
        notif2.setRadius(2.0f);

        if (notification == Boolean.TRUE){
            notif1.setFill(Color.RED);
            notif2.setFill(Color.WHITE);
        }
        else
        {
            notif1.setFill(Color.TRANSPARENT);
            notif2.setFill(Color.TRANSPARENT);
        }

        StackPane.setMargin(notif2, new Insets(0, 0, 275, 175));

        this.getChildren().addAll(poster, notif1, notif2);
        
    }
}
