package ObjectRep;

import BasicDatatypes.Matrix;
import BasicDatatypes.Vector;
import Drawing.Window;

import java.util.*;

public class ObjGroup implements ObjectRep{

    private final List<ObjectRep> obs;

    public ObjGroup(){
        obs = new ArrayList<>(8);
    }

    public ObjGroup(ObjectRep ...os){
        this();
        obs.addAll(Arrays.asList(os));
    }

    public ObjGroup(List<ObjectRep> obs){
        this.obs = obs;
    }
    private Vector findIndices(int index){
        int i = 0;
        int vertices = obs.get(i).amountOfVertices();
        while(index >= vertices){
            index -= vertices;
            vertices = obs.get(++i).amountOfVertices();
        }
        return new Vector(i, index);
    }
    @Override
    public Vector get(int index) {
        Vector indices = findIndices(index);
        return obs.get((int) indices.get(0)).get((int) indices.get(1));
    }

    @Override
    public void set(int index, Vector v) {
        Vector indices = findIndices(index);
        obs.get((int)indices.get(0)).set((int) indices.get(1), v);
    }

    @Override
    public void applyTransformation(Matrix transform) {
        for(ObjectRep o : obs){
            o.applyTransformation(transform);
        }
    }

    @Override
    public ObjectRep createNewObjectWithTransform(Matrix transform) {
        List<ObjectRep> newObs = new ArrayList<>(obs.size());
        for(ObjectRep o : obs){
            newObs.add(o.createNewObjectWithTransform(transform));
        }
        return new ObjGroup(newObs);
    }

    @Override
    public int amountOfVertices() {
        int sum = 0;
        for(ObjectRep o : obs){
            sum += o.amountOfVertices();
        }
        return sum;
    }

    @Override
    public void draw(Window w, boolean wireframe) {
        obs.forEach(a -> a.draw(w, wireframe));
    }

    @Override
    public ObjectRep deepCopy() {
        List<ObjectRep> newObs = new ArrayList<>(obs.size());
        for(ObjectRep o : obs){
            newObs.add(o.deepCopy());
        }
        return new ObjGroup(newObs);
    }

    @Override
    public Iterator<Vector> iterator() {
        return new ObjGroupIter(obs);
    }
}


class ObjGroupIter implements Iterator<Vector>{
    private final List<ObjectRep> obs;

    int object = 0;
    int index = 0;

    public ObjGroupIter(List<ObjectRep> obs){
        this.obs = obs;
    }

    @Override
    public boolean hasNext() {
        return object < obs.size();
    }

    @Override
    public Vector next() {
        Vector result = obs.get(object).get(index++);
        if(index >= obs.get(object).amountOfVertices()){
            index = 0;
            object++;
        }
        return result;
    }
}