package engine;

public class Vector {
    
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector plus(Vector current) {
        Vector z = new Vector(x,y);

        z.x = this.x + current.x;
        z.y = this.y + current.y;

        return z;
    }

    public Vector minus(Vector current) {
        Vector z = new Vector(x,y);
        
        z.x = this.x - current.x;
        z.y = this.y - current.y;
        return z;
    }

    public Vector times(double factor) {
        Vector z = new Vector(x,y);
        
        z.x = factor * this.x;
        z.y = factor * this.y;

        return z;
    }

    public double dot(Vector current) {
        double sum=0.0;

        sum = sum + (this.x*current.x);
        sum = sum + (this.y*current.y);

        return sum;
    }

    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    public double distanceTo(Vector current) {
        return this.minus(current).magnitude();
    }

    public Vector direction() {
        return this.times(1.0 / this.magnitude());
    }

    public Vector normalize(){
        Vector z = new Vector(x,y);

        z.x = this.x / this.magnitude();
        z.y = this.y / this.magnitude();

        return z;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('(' + this.x + ", " + this.y + ')');
        return s.toString();
    }
}

