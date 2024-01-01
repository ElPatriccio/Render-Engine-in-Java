package BasicDatatypes;

import java.util.Arrays;
import java.util.Iterator;

public class Matrix implements Iterable<Float>{

    private final float[] v;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols){
        v = new float[rows*cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(int squaredDimension){
        this(squaredDimension, squaredDimension);
    }

    //requires row < this.rows && col < this.cols
    //ensures returns correct element
    public float get(int row, int col){
        return v[row * cols + col];
    }

    //requires row < this.rows && col < this.cols
    //ensures sets correct element to value
    public void set(int row, int col, float value){
        v[row * cols + col] = value;
    }

    public int size(){
        return v.length;
    }

    @Override
    public Iterator<Float> iterator() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            builder.append("[");
            for (int j = 0; j < cols; j++) {
                builder.append(get(i, j));
                if(j != cols-1){
                    builder.append(", ");
                }
            }
            builder.append("]\n");
        }
        return builder.toString();
    }
}
