package ObjectRep;

import BasicDatatypes.Matrix;
import BasicDatatypes.Vector;
import Window.Window;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TriangleStrip implements ObjectRep{

    private final List<Vector> vertices;

    public TriangleStrip(Collection<Vector> vs){
        vertices = new ArrayList<>(vs.size());
        vertices.addAll(vs);
    }

    public TriangleStrip(){
        vertices = new ArrayList<>(8);
    }

    public void addVertex(Vector v){
        vertices.add(v);
    }

    @Override
    public void applyTransformation(Matrix transform) {
        vertices.replaceAll(transform::mult);
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
        return null;
    }

    @Override
    public Iterator<Vector> iterator() {
        return vertices.iterator();
    }
}
