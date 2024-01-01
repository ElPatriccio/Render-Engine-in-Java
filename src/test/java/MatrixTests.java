import BasicDatatypes.Matrix;
import org.junit.jupiter.api.Test;
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

}
