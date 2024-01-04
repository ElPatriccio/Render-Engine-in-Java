import BasicDatatypes.Vector;
import ObjectRep.TriangleStrip;
import Transformation.Transformation3D;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TriangleStripTest {
    @Test
    void Basic(){
        Vector v1 = new Vector(1, 2, 0);
        Vector v2 = new Vector(3, 1, 0);
        Vector v3 = new Vector(6, 4, 0);

        TriangleStrip strip = new TriangleStrip(v1, v2, v3);

        assertEquals(3, strip.amountOfVertices());
        int i = 0;
        for (Vector ignored : strip){
            i++;
        }
        assertEquals(3, i);

        Iterator<Vector> iter = strip.iterator();
        assertTrue(iter.hasNext());
        assertEquals(v1, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(v2, iter.next());
    }

    @Test
    void ApplyTransform(){
        Vector v1 = new Vector(1, 2, 0);
        Vector v2 = new Vector(3, 1, 0);
        Vector v3 = new Vector(6, 4, 0);

        TriangleStrip strip = new TriangleStrip(v1, v2, v3);

        strip.applyTransformation(Transformation3D.translate(3, 0, 0));

        Iterator<Vector> iter = strip.iterator();
        assertTrue(iter.hasNext());
        assertEquals(new Vector(4, 2, 0), iter.next());
        assertTrue(iter.hasNext());
        assertEquals(new Vector(6, 1, 0), iter.next());
        assertTrue(iter.hasNext());
        assertEquals(new Vector(9, 4, 0), iter.next());
    }

    @Test
    void NewObject(){
        Vector v1 = new Vector(1, 2, 0);
        Vector v2 = new Vector(3, 1, 0);
        Vector v3 = new Vector(6, 4, 0);

        TriangleStrip strip = new TriangleStrip(v1, v2, v3);

        TriangleStrip result = (TriangleStrip) strip.createNewObjectWithTransform(Transformation3D.translate(3, 1, 0));

        assertNotEquals(strip, result);
    }

    @Test
    void SurfaceNormal(){
        TriangleStrip cube = TriangleStrip.cube(2);
        cube.applyTransformation(Transformation3D.rotateY(90));
        assertEquals(0f, cube.surfaceNormal(0).get(2), 0.001f);

        cube.applyTransformation(Transformation3D.rotateY(-45));

        assertTrue(cube.surfaceNormal(0).get(2) < 0);

        cube.applyTransformation(Transformation3D.rotateY(60));

        assertTrue(cube.surfaceNormal(0).get(2) > 0);
    }

    @Test
    void Visibility(){
        TriangleStrip cube = TriangleStrip.cube(2);

        for (int i = 0; i < cube.amountTriangles(); i++) {
            Vector[] vs = cube.getTriangle(i);
            float z = vs[0].crossProduct(vs[1]).get(2);
            if(i == 0 || i == 1){
                assertTrue(z < 0 + 0.00001f);
            }
            else{
                assertTrue(z >= 0 - 0.00001f);
            }
        }

        cube.applyTransformation(Transformation3D.rotateY(45));


        for (int i = 0; i < cube.amountTriangles(); i++) {
            float z = cube.surfaceNormal(i).get(2);
            if(i == 0 || i == 1 || i == 7 || i == 8){

                assertTrue(z < 0 + 0.00001f);
            }
            else{
                assertTrue(z >= 0 - 0.00001f);
            }
        }

    }
}
