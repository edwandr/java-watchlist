package appPackage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Hyperlink;

public class UIDynamicLink extends Hyperlink {
	private List<Observer> observers = new ArrayList<Observer>();

	public UIDynamicLink(String title) {
		this.setText(title);
	}

	public void addObserver(Observer o){
		getObservers().add(o);
	}
	
	public void notifyObservers(){
		for (Observer observer : getObservers()) {
	         observer.update();
	      }
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}
}
