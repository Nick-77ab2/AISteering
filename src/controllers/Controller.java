package controllers;

import engine.Car;
import engine.Game;
import engine.Vector;

/**
 *
 * @author santi
 */
public abstract class Controller {
    /*
        commands is an array with three components:
        - the desired "STEER" (-1 to +1)
        - the desired "THROTTLE" (0 to +1)
        - the deired "BRAKE" (0 to +1)
    */
    public static final int VARIABLE_STEERING = 0;
    public static final int VARIABLE_THROTTLE = 1;
    public static final int VARIABLE_BRAKE = 2;

    public static final int MAX_ACCELERATION = 5;
    
    public abstract void update(Car subject, Game game, double delta_t, double controlVariables[]);

    //Set an overarching control method that all scenarios use.
    public void carControl(Car subject, Vector target, double controlVariables[]){
        
        //current angle of acceleration as Vector
        Vector current = new Vector(Math.cos(subject.getAngle()), Math.sin(subject.getAngle()));
        //Current location
        Vector steer = new Vector((current.y * -1), current.x);
        if(steer.dot(target) > 0){
            controlVariables[VARIABLE_STEERING] = steer.dot(target) / 2;
        }
        else if(steer.dot(target) < 0){
            controlVariables[VARIABLE_STEERING] = steer.dot(target) / 2;
        }
        if(current.dot(target)>0){
            controlVariables[VARIABLE_THROTTLE] = 1;
        }
        else if(current.dot(target) < -1){
            controlVariables[VARIABLE_BRAKE] = 1;
        }

    }
}
