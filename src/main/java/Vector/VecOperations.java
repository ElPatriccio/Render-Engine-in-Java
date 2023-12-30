package Vector;

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

    public static float mult(Vector v1, Vector v2){
        float result = 0;
        for (int i = 0; i < v1.dimension(); i++) {
            result += v1.get(i) * v2.get(i);
        }
        return result;
    }

    public static float dot(Vector v1, Vector v2){
        return mult(v1, v2);
    }
}
