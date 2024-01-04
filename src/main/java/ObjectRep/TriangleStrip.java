package ObjectRep;

import BasicDatatypes.Matrix;
import BasicDatatypes.Vector;
import Drawing.WindowCD;
import Transformation.Transformation3D;
import Drawing.WindowJF;

import java.util.*;

public class TriangleStrip implements ObjectRep{

    private final List<Vector> vertices;

    public TriangleStrip(Collection<Vector> vs){
        vertices = new ArrayList<>(vs.size());
        vertices.addAll(vs);
    }

    public TriangleStrip(Vector ...vs){
        this(Arrays.asList(vs));
    }

    public TriangleStrip(){
        vertices = new ArrayList<>(8);
    }

    public static TriangleStrip square(float a){
        TriangleStrip square = new TriangleStrip();
        a /= 2;
        square.add(new Vector(-a, -a, 0));
        square.add(new Vector(-a, a, 0));
        square.add(new Vector(a, -a, 0));
        square.add(new Vector(a, a, 0));


        return square;
    }

    public static TriangleStrip circle(float radius, int vertices){
        TriangleStrip circle = new TriangleStrip();
        Vector begin = new Vector(-1, 0, 0);
        begin.scalarMult(radius);
        circle.add(begin);
        float phi = -3.145f/2;
        while(phi < 3.145f/2){
            float x = (float) (radius * Math.sin(phi));
            float y = (float) (radius * Math.cos(phi));
            circle.add(new Vector(x, y, 0));
            circle.add(new Vector(x, -y, 0));
            phi += 3.145f/((float)vertices / 2);
        }
        circle.add(Vector.scalarMult(new Vector(1, 0, 0), radius));

        return circle;
    }

    public static TriangleStrip triangle(float width, float height){
        TriangleStrip triangle = new TriangleStrip();
        triangle.add(new Vector(-width/2, -height/2, 0));
        triangle.add(new Vector(0, height/2, 0));
        triangle.add(new Vector(width/2, -height/2, 0));
        return triangle;
    }

    public static TriangleStrip cube(float len){
        TriangleStrip cube = new TriangleStrip();
        cube.add(new Vector(-1, 1, 1));
        cube.add(new Vector(1, 1, 1));
        cube.add(new Vector(-1, -1, 1));
        cube.add(new Vector(1, -1, 1));
        cube.add(new Vector(1, -1, -1));
        cube.add(new Vector(1, 1, 1));
        cube.add(new Vector(1, 1, -1));
        cube.add(new Vector(-1, 1, 1));
        cube.add(new Vector(-1, 1, -1));
        cube.add(new Vector(-1, -1, 1));
        cube.add(new Vector(-1, -1, -1));
        cube.add(new Vector(1, -1, -1));
        cube.add(new Vector(-1, 1, -1));
        cube.add(new Vector(1, 1, -1));

        cube.applyTransformation(Transformation3D.combineTransformation(Transformation3D.scale(len, len, len), Transformation3D.scale(0.5f, 0.5f, 0.5f)));

        return cube;
    }

    public static TriangleStrip triangle(float width){
        float c2 = width * width;
        float a2 = width*width/4;
        return TriangleStrip.triangle(width, (float) Math.sqrt(c2 - a2));
    }

    public void add(Vector v){
        vertices.add(v);
    }

    public int amountTriangles(){
        if(amountOfVertices() < 3){
            return 0;
        }
        return amountOfVertices() -2;
    }

    public Vector surfaceNormal(int triangle){
        Vector[] vs = getTriangle(triangle);
        return Vector.sub(vs[1], vs[0]).crossProduct(Vector.sub(vs[2], vs[0]));
    }

    @Override
    public Vector get(int index) {
        return vertices.get(index);
    }
    public Vector[] getTriangle(int index){
        return index % 2 == 0 ? new Vector[]{vertices.get(index), vertices.get(index + 1), vertices.get(index + 2)}
                              : new Vector[]{vertices.get(index + 1), vertices.get(index), vertices.get(index + 2)};
    }

    @Override
    public void set(int index, Vector v) {
        vertices.set(index, v);
    }

    @Override
    public void applyTransformation(Matrix transform) {
        vertices.replaceAll(transform::mult);
    }

    @Override
    public ObjectRep createNewObjectWithTransform(Matrix transform) {
        TriangleStrip result = (TriangleStrip) deepCopy();
        result.applyTransformation(transform);
        return result;
    }

    @Override
    public int amountOfVertices() {
        return vertices.size();
    }

    @Override
    public void draw(WindowCD w) {
        w.drawTriangleStrip(this);
    }

    @Override
    public void draw(WindowJF w){
        w.drawTriangleStrip(this);
    }

    @Override
    public ObjectRep deepCopy() {
        TriangleStrip copy = new TriangleStrip();
        for(Vector v : this){
            copy.add(v);
        }
        return copy;
    }

    @Override
    public Iterator<Vector> iterator() {
        return vertices.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriangleStrip vectors = (TriangleStrip) o;
        return Objects.equals(vertices, vectors.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }

    @Override
    public String toString() {
        return "TriangleStrip{" +
                "vertices=" + vertices +
                '}';
    }
}
