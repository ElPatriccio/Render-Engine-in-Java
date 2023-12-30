package Vector;

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

    //requires index >= 0 && index < dimension
    //ensures returns element at index = index
    public float get(int index){
        return v[index];
    }
    //requires index >= 0 && index < dimension
    //ensures sets element at index = index
    public void set(int index, float value){
        v[index] = value;
    }

    public float length(){
        float len = 0;
        for (float f : this){
            len+= f*f;
        }
        return (float) Math.sqrt(len);
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
