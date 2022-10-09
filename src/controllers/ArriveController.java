package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.Vector;

/**
 *
 @author Pelletier
 */
public class ArriveController extends Controller {
    
    private GameObject theTarget;
    private double slowRadius;
    private double targetRadius;

    public ArriveController(GameObject target, double sRadius, double tRadius){
        theTarget = target;
        targetRadius = tRadius;
        slowRadius = sRadius;

    }

    public Vector arrive(Car car, double delta_time){
        //Basically just follows the algorithm given in the slides.
        double targetSpeed;
        //target velocity
        Vector tVelo = new Vector(car.getX(), car.getY());
        //distance
        Vector d = new Vector(car.getX(),car.getY());
        //final velocity
        Vector fVelo = new Vector(car.getX(),car.getY());
        //user location
        Vector user = new Vector(car.getX(),car.getY());
        //target loation
        Vector enemy = new Vector(theTarget.getX(), theTarget.getY());

        d = enemy.minus(user);

        double dist = d.magnitude();
        
        if(dist<targetRadius){
            return new Vector(0,0);
        }
        else if(dist>slowRadius){
            targetSpeed = car.getMaxSpeed();
        }
        else{
            targetSpeed = car.getMaxSpeed() * dist/slowRadius;
        }

        tVelo = d.normalize();
        tVelo = tVelo.times(targetSpeed);

        Vector userVelo = new Vector(Math.cos(car.getAngle()), Math.sin(car.getAngle()));
        userVelo = userVelo.normalize();
        userVelo = userVelo.times(car.getSpeed());

        fVelo = tVelo.minus(userVelo).times((1/delta_time));

        if(fVelo.magnitude() > MAX_ACCELERATION){
            fVelo = fVelo.normalize();
            fVelo = fVelo.times(MAX_ACCELERATION);
        }

        return fVelo;


    }

    public void update(Car car, Game game, double delta_t, double[] controlVariables) {
        carControl(car, arrive(car, delta_t), controlVariables);
    }
}
