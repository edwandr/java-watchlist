package appPackage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UIDynamicImage extends ImageView{
	private List<Observer> observers = new ArrayList<Observer>();
	private int TVShowId;

	public UIDynamicImage(Image poster, int tVShowId){
		this.setImage(poster);
		this.setTVShowId(tVShowId);
	}
	
	public void addObserver(Observer o){
		getObservers().add(o);
	}
	
	public void notifyObservers(int tVShowId){
		for (Observer observer : getObservers()) {
	         observer.update(tVShowId);
	      }
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public int getTVShowId() {
		return TVShowId;
	}

	public void setTVShowId(int tVShowId) {
		TVShowId = tVShowId;
	}
}
