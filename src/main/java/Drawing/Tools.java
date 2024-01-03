package Drawing;

import BasicDatatypes.Vector;
import ObjectRep.BoundingBox;

public class Tools {
    public static Vector barycentric(Vector a, Vector b, Vector c, Vector p){
        float v, w;

        Vector v0 = Vector.sub(b, a);
        Vector v1 = Vector.sub(c, a);
        Vector v2 = Vector.sub(p, a);

        float d00 = v0.dot(v0);
        float d01 = v0.dot(v1);
        float d11 = v1.dot(v1);
        float d20 = v2.dot(v0);
        float d21 = v2.dot(v1);

        float denominator = d00*d11 -d01 *d01;
        v = (d11 * d20 - d01 * d21) /denominator;
        w = (d00 * d21 -d01 *d20) / denominator;
        return new Vector(1f - v - w, v, w);
    }

    public static Vector barycentric(Vector a, Vector v0, Vector v1, Vector p, float d00, float d01, float d11, float denominator){
        float v, w;

        Vector v2 = Vector.sub(p, a);

        float d20 = v2.dot(v0);
        float d21 = v2.dot(v1);

        v = (d11 * d20 - d01 * d21) /denominator;
        w = (d00 * d21 -d01 *d20) / denominator;
        return new Vector(1f - v - w, v, w);
    }

    public static boolean insideTriangle(Vector barycentric){
        float sum = 0;
        for (float f : barycentric) {
            sum += f;
            if (f < 0){
                return false;
            }
        }

        float delta = 0.000001f;

        return sum > 1f - delta && sum < 1f + delta;
    }
}
