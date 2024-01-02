package ObjectRep;

import BasicDatatypes.Matrix;
import BasicDatatypes.Vector;

import Window.Window;


public interface ObjectRep extends Iterable<Vector> {
    void applyTransformation(Matrix transform);

    int amountOfVertices();

    void draw(Window w);
    ObjectRep deepCopy();
}
