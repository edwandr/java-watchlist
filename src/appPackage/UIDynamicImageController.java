package appPackage;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class UIDynamicImageController implements Observer{
	private UIDynamicImage imageView;
	
	public UIDynamicImageController(UIDynamicImage imageView){
		this.imageView=imageView;
	}
	
	public void update(TVShow tvShow){
		Image image;
		
		if(tvShow.getBigPoster()!=null){
			image = tvShow.getBigPoster();
		}
		else if(tvShow.getPoster()!=null){
			image = tvShow.getPoster();
		}
		else{
			image = UIDynamicImage.defaultImage;
		}
		
		Task<Void> task = new Task<Void>() {
		    @Override protected Void call() throws Exception {
				imageView.setImage(image);
				return null;
		    }
		};
		
		Platform.runLater(task);
		
	}
	
}
