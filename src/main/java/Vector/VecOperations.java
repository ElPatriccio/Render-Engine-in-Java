package Vector;

//binary operations with vectors
public class VecOperations {

    //requires v1.dimension() == v2.dimension()
    //ensures returns result of vector addition as a new vector
    public static Vector add(Vector v1, Vector v2){
        Vector result = new Vector(v1.dimension());
        for (int i = 0; i < v1.dimension(); i++) {
            result.set(i, v1.get(i) + v2.get(i));
        }
        return result;
    }

    //requires v != null
    //ensures returns result of scalar multiplication as a new vector
    public static Vector scalarMult(Vector v, float scalar){
        Vector result = new Vector(v.dimension());
        for(int i = 0; i < v.dimension(); i++){
            result.set(i, v.get(i) * scalar);
        }
        return result;
    }

    //requires v1.dimension() == v2.dimension()
    //ensures returns result of vector subtraction as a new vector
    public static Vector sub(Vector v1, Vector v2){
        return add(v1, scalarMult(v2, -1f));
    }

    //requires v1.dimension() == v2.dimension()
    //ensures returns result of vector multiplication as a new vector
    public static float mult(Vector v1, Vector v2){
        float result = 0;
        for (int i = 0; i < v1.dimension(); i++) {
            result += v1.get(i) * v2.get(i);
        }
        return result;
    }

    //requires v1.dimension() == v2.dimension()
    //ensures returns result of vector multiplication as a new vector
    //NOTE: just an alias of mult()
    public static float dot(Vector v1, Vector v2){
        return mult(v1, v2);
    }

    //requires v1.dimension == 3 && v2.dimension == 3
    //ensures returns result of vector cross product as a new vector
    public static Vector crossProduct(Vector v1, Vector v2){
        Vector cross = new Vector(3);
        cross.set(0, v1.get(1) * v2.get(2) - v1.get(2) * v2.get(1));
        cross.set(1, v1.get(2) * v2.get(0) - v1.get(0) * v2.get(2));
        cross.set(2, v1.get(0) * v2.get(1) - v1.get(1) * v2.get(0));
        return cross;
    }

    //requires v1.dimension() == v2.dimension()
    //ensures returns the angle between two vectors in radians
    public static float angleInRadians(Vector v1, Vector v2){
        return (float) Math.acos(VecOperations.mult(v1, v2) / (v1.length() * v2.length()));
    }

    //requires v1.dimension() == v2.dimension()
    //ensures returns the angle between two vectors in degrees
    public static float angleInDegrees(Vector v1, Vector v2){
        return (float) (angleInRadians(v1, v2) * 180f/Math.PI);
    }
}
