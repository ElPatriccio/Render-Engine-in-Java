package Vector;

import java.util.Iterator;

public class Vector implements Iterable<Float>{
    private final float[] v;

    public Vector(float ...values){
        v = values;
    }

    //requires dim > 0
    public Vector(int dim){
        v = new float[dim+1];
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

    public int dimension(){
        return v.length - 1;
    }

    public int size(){
        return v.length;
    }

    @Override
    public Iterator<Float> iterator() {
        return new VecIter(v, false);
    }

    public Iterator<Float> homogenizedIterator(){
        return new VecIter(v, true);
    }
}
