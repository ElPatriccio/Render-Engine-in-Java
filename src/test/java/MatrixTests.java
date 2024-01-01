import BasicDatatypes.Matrix;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTests {

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

}
