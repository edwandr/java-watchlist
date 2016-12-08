package appPackage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UIDynamicImage extends ImageView{
	private List<Observer> observers = new ArrayList<Observer>();
	private TVShow tVShow;
	
	static Image defaultImage = new Image("loading.gif");
	
	//Should the UIDynamicImage create its Controller on its own ??
	
    /**
     * Allocates a new UIDynamicImage with default "Unavailable Image" image
     *
     * @param tvShow reference used for observers
     * @see setUpController Ensures the image will change if it is loaded after initialization. 
     * 
     */
	public UIDynamicImage(TVShow tVShow){
		super(defaultImage);
		this.tVShow=tVShow;
	}
	
    /**
     * Allocates a controller to observe the TVShow and update the image when needed.
     */
	public void setUpController(){
		//Setup your controller to observe any changes made to the show
		//Note: This is outside the constructor to keep it streamlined
		UIDynamicImageController controller = new UIDynamicImageController(this);
		this.tVShow.addObserver(controller);
		controller.update(this.tVShow);
	}
	
	public void addObserver(Observer o){
		getObservers().add(o);
	}
	
	public void notifyObservers(TVShow tVShow){
		for (Observer observer : getObservers()) {
	         observer.update(tVShow);
	      }
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public TVShow getTVShow() {
		return tVShow;
	}

	public void setTVShow(TVShow ptVShow) {
		tVShow = ptVShow;
	}
}
