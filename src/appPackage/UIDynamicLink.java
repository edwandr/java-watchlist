package appPackage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Hyperlink;

public class UIDynamicLink extends Hyperlink {
	private List<Observer> observers = new ArrayList<Observer>();
	private Boolean type;

	public UIDynamicLink(String title, String type) {

		this.setText(title);

		if (type.equals("featured"))
		{
			this.type = Boolean.TRUE;
		}
		else if (type.equals("favorites"))
		{
			this.type = Boolean.FALSE;
		}
	}

	public void addObserver(Observer o){
		getObservers().add(o);
	}
	
	public void notifyObservers(Boolean type){
		for (Observer observer : getObservers()) {
	         observer.update(type);
	      }
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public Boolean getType()
	{
		return this.type;
	}

	public void setType(Boolean pType)
	{
		this.type = pType;
	}
}
