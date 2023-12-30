import Vector.Vector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class VectorTests {
    @Test
    void ReturnCorrectElement(){
        assertEquals(2, new Vector(1, 2).get(1));
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

}
