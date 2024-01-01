package BasicDatatypes;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class Matrix implements Iterable<Float>{

    private final float[] v;
    private int rows;
    private int cols;
    private boolean transposed = false;

    public Matrix(int rows, int cols){
        v = new float[rows*cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(int squaredDimension){
        this(squaredDimension, squaredDimension);
    }

    //requires dim >= 0
    //ensures returns identity matrix with dimension dim x dim
    public static Matrix identityMatrix(int dim){
        Matrix id = new Matrix(dim);
        for (int i = 0; i < dim; i++) {
            id.set(i, i, 1);
        }
        return id;
    }

    private int index(int row, int col){
        if(transposed){
            return col * cols + row;
        }
        return row * cols + col;
    }

    //requires row < this.rows && col < this.cols
    //ensures returns correct element
    public float get(int row, int col){
        return v[index(row, col)];
    }

    //requires row < this.rows && col < this.cols
    //ensures sets correct element to value
    public void set(int row, int col, float value){
       v[index(row, col)] = value;
    }

    //ensures matrix is transposed
    public void transpose(){
        int tmp = rows;
        rows = cols;
        cols = tmp;
        transposed = !transposed;
    }

    public int rows(){
        return this.rows;
    }

    public int cols(){
        return this.cols;
    }

    public int size(){
        return v.length;
    }

    @Override
    public Iterator<Float> iterator() {
        return new MatIter(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix floats = (Matrix) o;
        return rows == floats.rows && cols == floats.cols && transposed == floats.transposed && Arrays.equals(v, floats.v);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, cols, transposed);
        result = 31 * result + Arrays.hashCode(v);
        return result;
    }
}

class MatIter implements Iterator<Float>{

    private final Matrix m;
    private int i = 0;
    private int row = 0;
    private int col = 0;
    public MatIter(Matrix m){
       this.m = m;
    }

    @Override
    public boolean hasNext() {
        return i < m.size();
    }

    @Override
    public Float next() {
        float result = m.get(row, col++);
        if(col == m.cols()){
            col = 0;
            row++;
        }
        i++;
        return result;
    }
}
