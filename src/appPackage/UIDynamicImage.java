package appPackage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UIDynamicImage extends ImageView{
	private List<Observer> observers = new ArrayList<Observer>();
	private TVShow tVShow;

	public UIDynamicImage(Image poster, TVShow tVShow){
		this.setImage(poster);
		this.setTVShow(tVShow);
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

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public TVShow getTVShow() {
		return tVShow;
	}

	public void setTVShow(TVShow ptVShow) {
		tVShow = ptVShow;
	}
}
