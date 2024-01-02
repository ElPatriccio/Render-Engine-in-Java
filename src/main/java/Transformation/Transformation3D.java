package Transformation;

import BasicDatatypes.Matrix;

public class Transformation3D {

    public static Matrix translate(float x, float y, float z){
        Matrix translate = Matrix.identityMatrix(4);
        translate.set(0, 3, x);
        translate.set(1, 3, y);
        translate.set(2, 3, z);
        return translate;
    }

    public static Matrix scale(float x, float y, float z){
        Matrix scale = new Matrix(4);
        scale.set(0, 0, x);
        scale.set(1, 1, y);
        scale.set(2, 2, z);
        scale.set(3, 3, 1f);
        return scale;
    }

    public static Matrix rotateX(float angle){
        float radians = angle * (float)Math.PI/180f;
        Matrix rotate = Matrix.identityMatrix(4);
        rotate.set(1, 1, (float) Math.cos(radians));
        rotate.set(1, 2, (float) -Math.sin(radians));
        rotate.set(2, 1, (float) Math.sin(radians));
        rotate.set(2, 2, (float) Math.cos(radians));
        return rotate;
    }

    public static Matrix rotateY(float angle){
        float radians = angle * (float) Math.PI/180f;
        Matrix rotate = Matrix.identityMatrix(4);
        rotate.set(0, 0, (float) Math.cos(radians));
        rotate.set(0, 2, (float) Math.sin(radians));
        rotate.set(2, 0, (float) -Math.sin(radians));
        rotate.set(2, 2, (float) Math.cos(radians));
        return rotate;
    }

    public static Matrix rotateZ(float angle){
        float radians = angle * (float) Math.PI/180f;
        Matrix rotate = Matrix.identityMatrix(4);
        rotate.set(0, 0, (float) Math.cos(radians));
        rotate.set(0, 1, (float) -Math.sin(radians));
        rotate.set(1, 0, (float) Math.sin(radians));
        rotate.set(1, 1, (float) Math.cos(radians));
        return rotate;
    }

    public static Matrix combineTransformation(Matrix ...matrices){
        Matrix result = matrices[0];
        for (int i = 1; i < matrices.length; i++) {
            result = result.mult(matrices[i]);
        }
        return result;
    }
}
