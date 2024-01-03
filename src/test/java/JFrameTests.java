import ObjectRep.TriangleStrip;
import Transformation.Transformation3D;
import Drawing.WindowJF;
public class JFrameTests {
    public static void main(String[] args) {
        WindowJF w = new WindowJF(1920, 1080);

        TriangleStrip cube = TriangleStrip.cube(2);
        while (true){
            w.clearImage();
            cube.applyTransformation(Transformation3D.combineTransformation(
                    Transformation3D.rotateX(0.01f),
                    Transformation3D.rotateY(0.1f)
            ));
            w.drawTriangleStrip(cube);
            w.show();
        }

    }
}
