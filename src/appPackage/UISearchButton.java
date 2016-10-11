package appPackage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;

public class UISearchButton extends Button {
	
	private List<Observer> observers = new ArrayList<Observer>();

	public UISearchButton(String title) {
		this.setText(title);
	}

	public void addObserver(Observer o){
		getObservers().add(o);
	}
	
	public void notifyObservers(String query){
		for (Observer observer : getObservers()) {
	         observer.update(query);
	      }
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}
}
