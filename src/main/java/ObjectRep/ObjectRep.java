package ObjectRep;

import BasicDatatypes.Matrix;
import BasicDatatypes.Vector;

import Window.Window;


public interface ObjectRep extends Iterable<Vector> {

    Vector get(int index);

    void set(int index, Vector v);

    void applyTransformation(Matrix transform);

    ObjectRep createNewObjectWithTransform(Matrix transform);

    int amountOfVertices();

    void draw(Window w);
    ObjectRep deepCopy();
}
