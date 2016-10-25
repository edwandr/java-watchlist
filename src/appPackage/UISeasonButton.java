package appPackage;


import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class UISeasonButton extends Button {
    private List<Observer> observers = new ArrayList<Observer>();

    public UISeasonButton(String title) {
        this.setText(title);
    }

    public void addObserver(Observer o){
        getObservers().add(o);
    }

    public void notifyObservers(TVSeason season){
        for (Observer observer : getObservers()) {
            observer.update(season);
        }
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

}
