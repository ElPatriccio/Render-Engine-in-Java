import BasicDatatypes.Matrix;
import BasicDatatypes.Vector;
import ObjectRep.ObjectRep;
import ObjectRep.TriangleStrip;
import Transformation.Transformation3D;
import Window.WindowCD;

import java.awt.*;

public class WindowTest {

    private static void AnimationFrame(WindowCD w, ObjectRep[] obs, Matrix[] transforms){
        try {
            while(true){
                w.clear();
                for (int i = 0; i < obs.length; i++) {
                    obs[i].applyTransformation(transforms[i % transforms.length]);
                    w.drawObject(obs[i]);
                }
                w.show();
            }

        }catch (RuntimeException e){
            if(e.getMessage().contains("This CodeDraw")){
                System.out.println("WindowCD closed!");
            }else{
                throw e;
            }
        }

    }

    public static void main(String[] args) {
//        ShowWindowWithCoordinates();
//        DrawPixel();
//        DrawLine();
//        Color();
//        Square();
//        Cube();
//        ApplyTransformToCube();
//        CubeAnimation();
        Fill();
    }
    private static void ShowWindowWithCoordinates(){
        WindowCD w = new WindowCD(400);
        w.show();
    }

    private static void DrawPixel(){
        WindowCD w = new WindowCD(400);
        w.setLineWidth(10);
        w.drawPoint(1, 1);
        w.show();
    }

    private static void DrawLine(){
        WindowCD w = new WindowCD(400);
        w.setLineWidth(3);
        w.drawLine(1, 1, 2, -2);
        w.show();
    }

    private static void Color(){
        WindowCD w = new WindowCD(600);
        w.setLineWidth(3);
        w.setColor(Color.GREEN);
        w.drawLine(2, 2, -1, -1);
        w.show();
    }

    private static void Square(){
        WindowCD w = new WindowCD(600);
        w.setLineWidth(2);
        w.setColor(Color.blue);
        w.drawObject(TriangleStrip.square(1));
        w.show();
    }

    private static void Cube(){
        WindowCD w = new WindowCD(600);
        w.setLineWidth(2);
        w.setColor(Color.blue);
        w.drawObject(TriangleStrip.cube(1));
        w.show();
    }

    private static void ApplyTransformToCube(){
        WindowCD w = new WindowCD(600);
        w.setLineWidth(2);
        w.setColor(Color.blue);
        TriangleStrip cube = TriangleStrip.cube(2);
        cube.applyTransformation(Transformation3D.rotateY(45f));
        cube.applyTransformation(Transformation3D.rotateX(10f));
        w.drawObject(cube);
        w.show();
    }

    private static void CubeAnimation(){
        WindowCD w = new WindowCD(600);
        w.setLineWidth(2);
        w.setColor(Color.blue);
        TriangleStrip cube = TriangleStrip.cube(2);

        AnimationFrame(w,
                new ObjectRep[]{cube},
                new Matrix[]{Transformation3D.combineTransformation(
                    Transformation3D.rotateX(0.01f),
                    Transformation3D.rotateY(0.1f)
                )}
        );
    }
}
