package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.RotatedRectangle;
import engine.Vector;

/**
 *
 @author Pelletier
 */
public class WallAvoidController extends Controller {
    private GameObject theTarget;

    public WallAvoidController(GameObject target){
        theTarget = target;
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

    public Vector wallAvoid(Car car, double x, double y){
        Vector currentCar = new Vector(car.getX(),car.getY());
        Vector currentCarCopy = new Vector(car.getX(),car.getY());
        Vector targetCar = new Vector(x, y);

        currentCar = targetCar.minus(currentCarCopy);
        currentCar = currentCar.normalize();
        currentCar = currentCar.times(MAX_ACCELERATION);

        return currentCar;

    }

    public boolean collisionChecker(Car car, Game game, double angle, double distance){
        double x = car.getX() + Math.cos(angle) * distance;
        double y = car.getY() + Math.sin(angle) * distance;
        double BOX_HEIGHT = 16;
        double BOX_WIDTH = 16; 
        RotatedRectangle r = new RotatedRectangle(x, y, BOX_WIDTH, BOX_HEIGHT, angle);
        GameObject collision = game.collision(r);
        if(collision !=null && collision.getClass() != Car.class){
            return true;
        }
        return false;

    }

    
    public void update(Car car, Game game, double delta_t, double[] controlVariables) {
        double rayLength = 45;
        double moveDistance = rayLength;

        boolean left = false;
        boolean front = false;
        boolean right = false;

        for(int i = 1; i < rayLength; i ++) {
		    left = collisionChecker(car, game, car.getAngle() - Math.PI/4, i);
		    if(left) {
		    	moveDistance = i;
		    	break;
		    }
	    }
	    
	    for(int i = 1; i < rayLength; i ++) {
		    front = collisionChecker(car, game, car.getAngle(), i);
		    if(front) {
		    	moveDistance = i;
		    	break;
		    }
	    }
	    
	    for(int i = 1; i < rayLength; i ++) {
		    right = collisionChecker(car, game, car.getAngle() + Math.PI/4, i);
		    if(right) {
		    	moveDistance = i;
		    	break;
		    }
	    }

        if(left) {
	    	double x = car.getX() + Math.cos(car.getAngle() + Math.PI/4) * moveDistance;
			double y = car.getY() + Math.sin(car.getAngle() + Math.PI/4) * moveDistance;
            Vector turn=wallAvoid(car,x,y);
			carControl(car, turn, controlVariables);
	    } 
        else if(front) {
	    	double x = car.getX() + Math.cos(car.getAngle()) * -moveDistance;
			double y = car.getY() + Math.sin(car.getAngle()) * -moveDistance;
            Vector turn=wallAvoid(car,x,y);
			carControl(car, turn, controlVariables);
	    } 
        else if(right) {
	    	double x = car.getX() + Math.cos(car.getAngle() - Math.PI/4) * moveDistance;
			double y = car.getY() + Math.sin(car.getAngle() - Math.PI/4) * moveDistance;
            Vector turn=wallAvoid(car,x,y);
			carControl(car, turn, controlVariables);
	    } 
        else {
            Vector seekNormal=seek(car);
	    	carControl(car, seekNormal, controlVariables);
        }
    }

}
