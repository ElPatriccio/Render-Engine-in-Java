package ObjectRep;

import BasicDatatypes.Vector;


public class BoundingBox {

    private float minx, maxx, miny, maxy, minz, maxz;
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

    public BoundingBox(float minx, float maxx, float miny, float maxy){
        this.minx = minx;
        this.maxx = maxx;
        this.miny = miny;
        this.maxy = maxy;
        this.minz = 0f;
        this.maxz = 0f;
    }

    public BoundingBox(float minx, float maxx, float miny, float maxy, float minz, float maxz){
        this(minx, maxx, miny, maxy);
        this.minz = minz;
        this.maxz = maxz;
    }

    public boolean inside2D(float x, float y){
        return x >= minx && x <= maxx && y >= miny && y <= maxy;
    }
    public boolean inside2D(Vector v){
        return inside2D(v.get(0), v.get(1));
    }

    public boolean inside3D(float x, float y, float z){
        return inside2D(x, y) && z >= minz && z <= maxz;
    }

    public boolean inside3D(Vector v){
        return inside3D(v.get(0), v.get(1), v.get(2));
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
