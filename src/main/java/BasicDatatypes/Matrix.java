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

    public Matrix(int rows, int cols, float[] v){
        this.v = v;
        this.rows = rows;
        this.cols = cols;
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

    //ensures fills whole matrix with value
    public void fill(float value){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                set(i, j, value);
            }
        }
    }

    //ensures fills matrix with values, if there is more room, it starts at the beginning of values
    public void fillPattern(float ...values){
        int va = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
              set(i, j, values[va]);
              va = (va + 1) % values.length;
            }
        }
    }

    //ensures matrix is transposed
    public void transpose(){
        int tmp = rows;
        rows = cols;
        cols = tmp;
        transposed = !transposed;
    }

    public Matrix transposed(){
        Matrix result = this.deepCopy();
        result.transpose();
        return result;
    }

    //requires this.rows == this.cols
    //ensures returns determinant
    public float det(){
        return det(0);
    }

    //requires laplaceRow >= 0 < rows
    //         this.rows == this.cols
    //ensures returns determinant
    public float det(int laplaceRow){
        if(rows == 2){
            return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
        }
        else{
            float sum = 0;
            for (int j = 0; j < cols; j++) {
                if(get(laplaceRow, j) == 0){
                    continue;
                }
                Matrix smaller = new Matrix(rows-1);
                int row = 0;
                for (int k = 0; k < rows; k++) {
                    int col = 0;
                    if(k == laplaceRow){
                        continue;
                    }
                    for (int l = 0; l <cols; l++) {
                        if(l == j){
                            continue;
                        }
                        smaller.set(row, col++, get(k, l));
                    }
                    row++;
                }
                sum += (float) (Math.pow(-1.0, laplaceRow + j) * get(laplaceRow, j) * smaller.det());
            }
            return sum;
        }
    }

    //requires solution != null
    //ensures applies the gaussJordan algorithm
    //        returns null iff there is no solution
    private Matrix gaussJordan(Matrix solution) {

        Matrix copy = deepCopy();
        Matrix result = solution.deepCopy();

        for (int i = 0; i < rows; i++) {
            if(copy.get(i, i) == 0.0){
                return null;
            }
            for (int j = 0; j < cols; j++) {
                if(i != j){
                    float ratio = copy.get(j, i)/copy.get(i, i);
                    for (int k = 0; k < cols; k++) {
                        copy.set(j, k, copy.get(j, k) - ratio * copy.get(i, k));
                    }
                    for (int k = 0; k < solution.cols; k++) {
                        result.set(j, k, result.get(j, k) - ratio * result.get(i, k));
                    }
                }
            }
        }
        for (int j = 0; j < result.cols; j++) {
            for (int i = 0; i < rows; i++) {
                result.set(i, j, result.get(i, j) / copy.get(i, i));
            }
        }

        return result;
    }

    //requires this is square matrix
    //ensures returns inverse iff there is any else null
    public Matrix inverted(){
        return gaussJordan(Matrix.identityMatrix(rows));
    }

    public Matrix solveLinEq(Vector solution){
        return gaussJordan(solution.toMatrixDeep());
    }

    public int rows(){
        return this.rows;
    }

    //requires m has to be the equal dimension as this
    public void add(Matrix m){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                set(i, j, get(i, j) + m.get(i, j));
            }
        }
    }
    //requires m1 has to be the equal dimension as m2
    public static Matrix add(Matrix m1, Matrix m2){
        Matrix result = m1.deepCopy();
        result.add(m2);
        return result;
    }

    public void scalarMult(float scalar){
        for (int i = 0; i < size(); i++) {
            v[i] = v[i] * scalar;
        }
    }

    public static Matrix scalarMult(Matrix m, float scalar){
        Matrix result = m.deepCopy();
        result.scalarMult(scalar);
        return result;
    }

    //requires m has to be of equal dimension as this
    public void sub(Matrix m){
        add(Matrix.scalarMult(m, -1f));
    }

    public static Matrix sub(Matrix m1, Matrix m2){
        Matrix result = m1.deepCopy();
        result.sub(m2);
        return result;
    }

    public Matrix mult(Matrix m){

        Matrix result = new Matrix(rows, m.cols());
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < m.cols(); j++) {
                float sum = 0;
                for (int k = 0; k < cols(); k++) {
                    sum += get(i, k) * m.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    public Vector mult(Vector v){
        Matrix op = v.toMatrix();
        op.transpose();
        return new Vector(true, mult(op).v);
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

    public Matrix deepCopy(){
        Matrix copy = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copy.set(i, j, get(i, j));
            }
        }
        return copy;
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
