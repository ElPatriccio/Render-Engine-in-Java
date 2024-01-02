import BasicDatatypes.Matrix;
import BasicDatatypes.Vector;
import Transformation.Transformation3D;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTests {

    private static final float floatErrorDelta = 0.001f;

    @Test
    void GetSetElement(){
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 2f);
        m.set(1, 1, 3f);

        assertEquals(2f, m.get(0, 0));
        assertEquals(3f, m.get(1, 1));

    }

    @Test
    void ToString(){
        Matrix m = new Matrix(2);
        m.set(0, 0, 1f);
        m.set(0, 1, 2f);
        m.set(1, 0, 3f);
        m.set(1, 1, 4f);

        assertEquals("[1.0, 2.0]\n[3.0, 4.0]\n", m.toString());
    }

    @Test
    void Transpose(){
        Matrix m = new Matrix(1, 2);
        m.set(0, 0, 1f);
        m.set(0, 1, 2f);

        assertEquals(1f, m.get(0, 0));
        assertEquals(2f, m.get(0, 1));

        m.transpose();

        assertEquals(1f, m.get(0, 0));
        assertEquals(2f, m.get(1, 0));

        m.transpose();

        assertEquals(2f, m.get(0, 1));

    }

    @Test
    void Iterator(){
        Matrix m = new Matrix(2);
        m.set(0, 0, 1f);
        m.set(0, 1, 2f);
        m.set(1, 0, 3f);
        m.set(1, 1, 4f);

        Iterator<Float> iter = m.iterator();

        assertTrue(iter.hasNext());
        assertEquals(1f, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(2f, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(3f, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(4f, iter.next());
        assertFalse(iter.hasNext());

        m.transpose();

        iter = m.iterator();

        assertTrue(iter.hasNext());
        assertEquals(1f, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(3f, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(2f, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(4f, iter.next());
        assertFalse(iter.hasNext());

    }

    @Test
    void IdentityMat(){
        Matrix m = new Matrix(2);
        m.set(0, 0, 1);
        m.set(0, 1, 0);
        m.set(1, 0, 0);
        m.set(1, 1, 1);

        assertEquals(m, Matrix.identityMatrix(2));
    }

    @Test
    void DeepCopy(){
        Matrix m = new Matrix(2);
        m.set(0, 0, 1);
        m.set(0, 1, 0);
        m.set(1, 0, 0);
        m.set(1, 1, 1);

        Matrix m2 = m.deepCopy();

        assertNotSame(m, m2);
        assertEquals(m, m2);
    }

    @Test
    void Determinant(){
        Matrix m = new Matrix(3);
        m.set(0, 0, 2);
        m.set(0, 1, 3);
        m.set(0, 2, 2);
        m.set(1, 0, 6);
        m.set(1, 1, 6);
        m.set(1, 2, 7);
        m.set(2, 0, 3);
        m.set(2, 1, 7);
        m.set(2, 2, 3);

        assertEquals(-5f, m.det());
    }

    @Test
    void FillPattern(){
        Matrix m = new Matrix(3);
        m.fillPattern(1, 2, 3, 4, 5, 6, 7, 8);
        Matrix expected = new Matrix(3);

        expected.set(0, 0, 1);
        expected.set(0, 1, 2);
        expected.set(0, 2, 3);
        expected.set(1, 0, 4);
        expected.set(1, 1, 5);
        expected.set(1, 2, 6);
        expected.set(2, 0, 7);
        expected.set(2, 1, 8);
        expected.set(2, 2, 1);

        assertEquals(expected, m);
    }

    @Test
    void Inverse(){
        Matrix m = new Matrix(3);
        m.fillPattern(2, 5, 7, 6, 2, 7, 7, 2, 2);

        Matrix inverse = m.inverted();

        assertEquals(-10f/151f, inverse.get(0, 0), floatErrorDelta);
        assertEquals(4f/151f, inverse.get(0, 1), floatErrorDelta);
        assertEquals(21f/151f, inverse.get(0, 2), floatErrorDelta);
        assertEquals(37f/151f, inverse.get(1, 0), floatErrorDelta);
        assertEquals(-45f/151f, inverse.get(1, 1), floatErrorDelta);
        assertEquals(28f/151f, inverse.get(1, 2), floatErrorDelta);
        assertEquals(-2f/151f, inverse.get(2, 0), floatErrorDelta);
        assertEquals(31f/151f, inverse.get(2, 1), floatErrorDelta);
        assertEquals(-26f/151f, inverse.get(2, 2), floatErrorDelta);
    }

    @Test
    void Addition(){
        Matrix m = new Matrix(3);
        m.fill(1f);
        Matrix result = new Matrix(3);
        result.fill(2f);
        m.add(m);
        assertEquals(result, m);
    }

    @Test
    void ScalarMult(){
        Matrix m = new Matrix(2);
        m.fill(3f);
        Matrix result = new Matrix(2);
        result.fill(6f);

        m.scalarMult(2f);

        assertEquals(result, m);
    }

    @Test
    void Subtraction(){
        Matrix m = new Matrix(3);
        m.fill(3f);

        Matrix m2 = new Matrix(3);
        m2.fill(2f);

        Matrix result = new Matrix(3);
        result.fill(1f);

        m.sub(m2);

        assertEquals(result, m);
    }

    @Test
    void SubtractionSideEffect(){
        Matrix m = new Matrix(3);
        m.fill(3f);

        Matrix m2 = new Matrix(3);
        m2.fill(2f);

        Matrix result = new Matrix(3);
        result.fill(2f);

        m.sub(m2);

        assertEquals(result, m2);
    }

    @Test
    void Multiplication(){
        Matrix m1 = new Matrix(3);
        m1.fillPattern(5, 4, 4, 0, 2, 3, 7, 5, 1);
        Matrix m2 = new Matrix(3);
        m2.fillPattern(5, 7, 1, 7, 2, 3, 6, 3, 0);

        Matrix expected = new Matrix(3);
        expected.fillPattern(77, 55, 17, 32, 13, 6, 76, 62, 22);
        assertEquals(expected, m1.mult(m2));
    }
    @Test
    void MultiplicationDifferentDim(){
        Matrix m1 = new Matrix(2, 3);
        Matrix m2 = new Matrix(3);

        m1.fillPattern(5, 4, 3, 0, 2, 1);
        m2.fillPattern(1, 2, 5, 3, 4, 5, 5, 6, 3);

        Matrix expected = new Matrix(2, 3);
        expected.fillPattern(32, 44, 54, 11, 14, 13);

        assertEquals(expected, m1.mult(m2));
    }

    @Test
    void MultiplicationSideEffects(){
        Matrix m1 = new Matrix(2, 3);
        Matrix m2 = new Matrix(3);

        m1.fillPattern(5, 4, 3, 0, 2, 1);
        m2.fillPattern(1, 2, 5, 3, 4, 5, 5, 6, 3);

        Matrix mc1 = m1.deepCopy();
        Matrix mc2 = m2.deepCopy();

        Matrix expected = new Matrix(2, 3);
        expected.fillPattern(32, 44, 54, 11, 14, 13);
        m1.mult(m2);

        assertEquals(mc1, m1);
        assertEquals(mc2, m2);
    }

    @Test
    void MultiplicationWithVector(){
        Matrix m = Transformation3D.translate(1, 0, 0);
        Vector v = new Vector(2, 4, 5);

        assertEquals(new Vector(3, 4, 5), m.mult(v));

    }
}
