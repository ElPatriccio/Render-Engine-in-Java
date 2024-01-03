package ObjectRep;

import BasicDatatypes.Vector;


public class BoundingBox {

    private float minx, maxx, miny, maxy;
    public BoundingBox(Vector[] triangle){
        minx = Math.min(triangle[0].get(0), triangle[1].get(0));
        minx = Math.min(minx, triangle[2].get(0));
        maxx = Math.max(triangle[0].get(0), triangle[1].get(0));
        maxx = Math.max(maxx, triangle[2].get(0));

        miny = Math.min(triangle[0].get(1), triangle[1].get(1));
        miny = Math.min(miny, triangle[2].get(1));
        maxy = Math.max(triangle[0].get(1), triangle[1].get(1));
        maxy = Math.max(maxy, triangle[2].get(1));
    }

    public float minX(){
        return minx;
    }

    public float maxX(){
        return maxx;
    }

    public float minY(){
        return miny;
    }

    public float maxY(){
        return maxy;
    }
}
