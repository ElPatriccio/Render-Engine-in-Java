package ObjectRep;

import BasicDatatypes.Matrix;
import BasicDatatypes.Vector;
import Window.Window;

import java.util.*;

public class TriangleStrip implements ObjectRep{

    private final List<Vector> vertices;

    public TriangleStrip(Collection<Vector> vs){
        vertices = new ArrayList<>(vs.size());
        vertices.addAll(vs);
    }

    public TriangleStrip(Vector ...vs){
        this(Arrays.asList(vs));
    }

    public TriangleStrip(){
        vertices = new ArrayList<>(8);
    }

    public void addVertex(Vector v){
        vertices.add(v);
    }

    @Override
    public Vector get(int index) {
        return vertices.get(index);
    }

    @Override
    public void set(int index, Vector v) {
        vertices.set(index, v);
    }

    @Override
    public void applyTransformation(Matrix transform) {
        vertices.replaceAll(transform::mult);
    }

    @Override
    public ObjectRep createNewObjectWithTransform(Matrix transform) {
        TriangleStrip result = (TriangleStrip) deepCopy();
        result.applyTransformation(transform);
        return result;
    }

    @Override
    public int amountOfVertices() {
        return vertices.size();
    }

    @Override
    public void draw(Window w) {

    }

    @Override
    public ObjectRep deepCopy() {
        TriangleStrip copy = new TriangleStrip();
        for(Vector v : this){
            copy.addVertex(v);
        }
        return copy;
    }

    @Override
    public Iterator<Vector> iterator() {
        return vertices.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriangleStrip vectors = (TriangleStrip) o;
        return Objects.equals(vertices, vectors.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }

    @Override
    public String toString() {
        return "TriangleStrip{" +
                "vertices=" + vertices +
                '}';
    }
}
