import Vector.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class VectorTests {
    private static final float floatErrorDelta = 0.001f;
    @Test
    void ReturnCorrectElement(){
        assertEquals(2, new Vector(1, 2).get(1));
    }

    @Test
    void SetCorrectElement(){
        Vector v = new Vector(1, 2, 3, 4);
        v.set(2, -3.4f);
        assertEquals(-3.4f, v.get(2));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void Dimension(int dim){
        Vector v = new Vector(dim);
        assertEquals(dim, v.dimension());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void Size(int dim){
        Vector v = new Vector(dim);
        assertEquals(dim+1, v.size());
    }

    @Test
    void Iterator(){
        int i = 0;
        Vector v = new Vector(1, 2, 3);
        for (float f : v) {
            i++;
        }
        assertEquals(v.dimension(), i);
    }

    @Test
    void HomogenizedIter(){
        int i = 0;
        Vector v = new Vector(-1, 2, 23);
        Iterator<Float> iter = v.homogenizedIterator();
        while (iter.hasNext()){
            i++;
            iter.next();
        }
        assertEquals(v.size(), i);
    }

    @Test
    void Length(){
        Vector v = new Vector(4, 2, 4);
        assertEquals(6, v.length());
    }

    @Test
    void Addition(){
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 3);

        Vector result = VecOperations.add(v1, v2);

        assertEquals(new Vector(2, 4, 6), result);
    }

    @Test
    void ScalarMult(){
        Vector v = new Vector(2, 4, -3);
        assertEquals(new Vector(-2, -4, 3), VecOperations.scalarMult(v, -1f));
        assertEquals(new Vector(4, 8, -6), VecOperations.scalarMult(v, 2f));
    }

    @Test
    void Subtraction(){
        Vector v1 = new Vector(-3, 2, 1);
        Vector v2 = new Vector(-3, 2, 1);
        assertEquals(new Vector(0, 0, 0), VecOperations.sub(v1, v2));
    }

    @Test
    void Multiplication(){
        Vector v1 = new Vector(2, 3, 4);
        Vector v2 = new Vector(1, 5, 3);

        assertEquals(29, VecOperations.mult(v1, v2));
        assertEquals(29, VecOperations.dot(v1, v2));
    }

    @Test
    void CrossProduct(){
        Vector v1 = new Vector(2, 3, 9);
        Vector v2 = new Vector(4, 5, 1);
        assertEquals(new Vector(-42, 34, -2), VecOperations.crossProduct(v1, v2));
    }

    @Test
    void Angle(){
        Vector v1 = new Vector(0, 1, 0);
        Vector v2 = new Vector(1, 0, 0);
        assertEquals(Math.PI/2, VecOperations.angleInRadians(v1, v2), floatErrorDelta);

        v1 = new Vector(1, 0, 0);
        v2 = new Vector(-1, 0, 0);
        assertEquals(Math.PI, VecOperations.angleInRadians(v1, v2), floatErrorDelta);

        v1 = new Vector(1, 1, 0);
        v2 = new Vector(1, 0, 0);
        assertEquals(Math.PI/4, VecOperations.angleInRadians(v1, v2), floatErrorDelta);
    }

    @Test
    void correctConversionToDegrees(){
        Vector v1 = new Vector(0, 1, 0);
        Vector v2 = new Vector(1, 0, 0);
        assertEquals(90f, VecOperations.angleInDegrees(v1, v2), floatErrorDelta);

        v1 = new Vector(1, 0, 0);
        v2 = new Vector(-1, 0, 0);
        assertEquals(180, VecOperations.angleInDegrees(v1, v2), floatErrorDelta);

        v1 = new Vector(1, 1, 0);
        v2 = new Vector(1, 0, 0);
        assertEquals(45, VecOperations.angleInDegrees(v1, v2), floatErrorDelta);
    }

}
