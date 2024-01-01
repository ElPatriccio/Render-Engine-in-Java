package BasicDatatypes;

import java.util.Arrays;
import java.util.Iterator;

public class Vector implements Iterable<Float>{
    private final float[] v;
    public Vector(float ...values){
        v = new float[values.length+1];
        for (int i = 0; i < dimension(); i++) {
            set(i, values[i]);
        }
        set(v.length-1, 1);
    }

    //requires dim > 0
    public Vector(int dim){
        v = new float[dim+1];
        for (int i = 0; i < dim; i++) {
            v[i] = 0;
        }
        v[dim] = 1;
    }

    //requires index >= 0 && index < size
    //ensures returns element at index = index
    public float get(int index){
        return v[index];
    }

    //requires index >= 0 && index < size
    //ensures sets element at index = index
    public void set(int index, float value){
        v[index] = value;
    }

    //ensures fills the vector with value (except the homogenous component)
    public void fill(float value) {
        for (int i = 0; i < dimension(); i++) {
            v[i] = value;
        }
    }

    //ensures length of vector is returned
    public float length(){
        float len = 0;
        for (float f : this){
            len+= f*f;
        }
        return (float) Math.sqrt(len);
    }

    //ensures normalizes the vector
    public void normalize(){
       scalarMult(1f/length());
    }


    public void dehomogenize(){
        float scalar = 1f/v[size()-1];
        if(scalar == 1f){
            return;
        }
        v[size()-1] = 1f;
        for (int i = 0; i < dimension(); i++) {
            v[i] = v[i]*scalar;
        }
    }
    //requires dimension() == v.dimension()
    //ensures result of vector addition
    public void add(Vector v){
        for (int i = 0; i < dimension(); i++) {
            set(i, get(i) + v.get(i));
        }
    }

    //requires v != null
    //ensures scalar multiplication
    public void scalarMult(float scalar){
        for(int i = 0; i < dimension(); i++){
            set(i, get(i) * scalar);
        }
    }

    //requires v1.dimension() == v2.dimension()
    //ensures result of vector subtraction
    public void sub(Vector v){
        v.scalarMult(-1f);
        add(v);
    }

    //requires dimension() == v.dimension()
    //ensures vector multiplication
    public float mult(Vector v){
        float result = 0;
        for (int i = 0; i < dimension(); i++) {
            result += get(i) * v.get(i);
        }
        return result;
    }

    //requires dimension() == v.dimension()
    //ensures vector multiplication
    //NOTE: just an alias of mult()
    public float dot(Vector v){
        return mult(v);
    }

    //requires dimension == 3 && v.dimension == 3
    //ensures returns result of vector cross product as a new vector
    public Vector crossProduct(Vector v2){
        Vector cross = new Vector(3);
        cross.set(0, get(1) * v2.get(2) - get(2) * v2.get(1));
        cross.set(1, get(2) * v2.get(0) - get(0) * v2.get(2));
        cross.set(2, get(0) * v2.get(1) - get(1) * v2.get(0));
        return cross;
    }

    //requires dimension() == v.dimension()
    //ensures returns the angle between two vectors in radians
    public float angleInRadians(Vector v){
        return (float) Math.acos(mult(v) / (length() * v.length()));
    }

    //requires dimension() == v.dimension()
    //ensures returns the angle between two vectors in degrees
    public float angleInDegrees(Vector v){
        return (float) (angleInRadians(v) * 180f/Math.PI);
    }

    //ensures returns dimension of vector (without homogenous component)
    public int dimension(){
        return v.length - 1;
    }

    //ensures returns size of underlying array (dim of vector with homogenous component)
    public int size(){
        return v.length;
    }

    //ensures returns iterator over all elements except the last one
    @Override
    public Iterator<Float> iterator() {
        return new VecIter(v, false);
    }

    //ensures returns iterator over all elements (with homogenous component)
    public Iterator<Float> homogenizedIterator(){
        return new VecIter(v, true);
    }

    public Matrix toMatrix(){
        Matrix result = new Matrix(1, dimension());
        for (int i = 0; i < dimension(); i++) {
            result.set(0, i, get(i));
        }
        return result;
    }

    //ensures returned Vector is equal to this, but not identical
    public Vector deepCopy(){
        Vector copy = new Vector(dimension());
        for (int i = 0; i < dimension(); i++) {
            copy.set(i, get(i));
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector floats = (Vector) o;
        return Arrays.equals(v, floats.v);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(v);
    }

    @Override
    public String toString() {
        return Arrays.toString(v);
    }
}

class VecIter implements Iterator<Float> {
    private final float[] v;
    private int i = 0;
    private final int x;


    public VecIter(float[] v, boolean homogenized){
        this.v = v;
        x = homogenized ? 0 : 1;
    }

    @Override
    public boolean hasNext() {
        return i < v.length - x;
    }

    @Override
    public Float next() {
        return v[i++];
    }
}