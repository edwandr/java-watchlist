import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage; 
  
public class Main extends Application { 
  
	@Override 
	public void start(Stage primaryStage) throws Exception { 
		primaryStage.setTitle("Java-watchlist");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        
        BorderPane application = new BorderPane();
        root.getChildren().add(application);
        
        HBox searchbar = addSearchBar();
        application.setTop(searchbar);
        
        VBox menu = addMenu();
        application.setLeft(menu);

        primaryStage.setScene(scene);
        primaryStage.show();
	} 
	
	public HBox addSearchBar() {
	    HBox searchbar = new HBox();
	    searchbar.setPadding(new Insets(15, 12, 15, 12));
	    searchbar.setSpacing(10);
	    searchbar.setStyle("-fx-background-color: #efdecd;");

	    Label brand = new Label("Java-watchlist");
	    brand.setMinWidth(125);
	    
	    TextField textField = new TextField ();
	    textField.setMinWidth(500);

	    Button buttonCurrent = new Button("Search");
	    

	    searchbar.getChildren().addAll(brand, textField, buttonCurrent);

	    searchbar.setPrefWidth(1000);

	    return searchbar;
	}
	
	public VBox addMenu() {
		VBox menu = new VBox();
		menu.setPadding(new Insets(10));
		menu.setSpacing(8);
	    menu.setStyle("-fx-background-color: #ede8e3;");
		
	    Hyperlink options[] = new Hyperlink[] {
	            new Hyperlink("Featured shows"),
	            new Hyperlink("Favorites shows")};
	    
	    for (int i=0; i<2; i++) {
	        VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
	        menu.getChildren().add(options[i]);
	    }
	    
	    menu.setPrefHeight(600);
		
		return menu;
	}
	  
	public static void main(String... args) { 
		Application.launch(args); 
	} 
}
