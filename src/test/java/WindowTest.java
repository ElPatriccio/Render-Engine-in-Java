import BasicDatatypes.Matrix;
import BasicDatatypes.Vector;
import Drawing.Tools;
import ObjectRep.ObjectRep;
import ObjectRep.TriangleStrip;
import Transformation.Transformation3D;
import Drawing.WindowCD;

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
        FloodFill();
        BarycentricFill();
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

    private static void FloodFill(){
        WindowCD w = new WindowCD(600);
        w.setLineWidth(2);
        w.setColor(Color.blue);
        TriangleStrip cube = TriangleStrip.cube(2);
        w.clear(false);
        w.drawObject(cube);
        w.floodFill(new Vector(0.2f, 0.5f));
        w.show();
    }

    private static void BarycentricFill(){
        WindowCD w = new WindowCD(600);
        w.setLineWidth(2);
        w.setColor(Color.blue);
        TriangleStrip cube = TriangleStrip.cube(2);
        Color[] colors = new Color[]{Color.ORANGE, Color.ORANGE, Color.YELLOW, Color.RED, Color.RED, Color.WHITE, Color.WHITE, Color.GREEN, Color.GREEN, Color.YELLOW, Color.BLUE, Color.BLUE};

        try {
            while (true){
                int i = 0;
                w.clear();
                for (int j = 0; j < cube.amountTriangles(); j++) {
                    Vector[] vs = cube.getTriangle(j);
                    w.setColor(colors[i++]);
                    i %= colors.length;
                    if(cube.surfaceNormal(j).get(2) > 0 - 0.001f){
                        continue;
                    }
                    w.fillTriangleBaryCentric(vs[0], vs[1], vs[2]);
                }
                w.setColor(Color.black);
                w.drawObject(cube);
                w.show();
                cube.applyTransformation(Transformation3D.combineTransformation(
                        Transformation3D.rotateY(0.3f),
                        Transformation3D.rotateX(0.03f),
                        Transformation3D.rotateZ(0.15f)
                ));
            }
        } catch (Exception e) {
            if(e.getMessage().contains("This CodeDraw")){
                System.out.println("Window closed!");
            }
            else{
                throw e;
            }
        }
    }
}
