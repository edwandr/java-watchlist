package appPackage;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class UIFavoriteButton extends Button {

    private List<Observer> observers = new ArrayList<Observer>();
    private Boolean type;

    public UIFavoriteButton(String title, Boolean type) {
        this.setText(title);
        this.type = type;
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
