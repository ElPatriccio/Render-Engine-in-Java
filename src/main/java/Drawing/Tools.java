package Drawing;

import BasicDatatypes.Vector;

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
}
