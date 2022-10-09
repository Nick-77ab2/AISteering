/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.Vector;

/**
 *
 @author Pelletier
 */
public class SeekController extends Controller {
    //Create global target var for use in other methods
    private GameObject theTarget;
    
    public SeekController(GameObject target){
        theTarget=target;
    }

    public Vector seek(Car car){
        Vector currentCar = new Vector(car.getX(),car.getY());

        Vector currentCarCopy = new Vector(car.getX(),car.getY());
        Vector targetCar = new Vector(theTarget.getX(), theTarget.getY());

        currentCar = targetCar.minus(currentCarCopy);
        currentCar = currentCar.normalize();
        currentCar = currentCar.times(MAX_ACCELERATION);

        return currentCar;

    }

    public void update(Car car, Game game, double delta_t, double[] controlVariables) {
        carControl(car, seek(car), controlVariables);
    }
    
}
