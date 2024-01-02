import ObjectRep.TriangleStrip;
import Transformation.Transformation3D;
import Window.Window;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class WindowTest {

    public static void main(String[] args) {
        ShowWindowWithCoordinates();
        DrawPixel();
        DrawLine();
        Color();
        Square();
        Cube();
        ApplyTransformToCube();
    }
    private static void ShowWindowWithCoordinates(){
        Window w = new Window(400);
        w.show();
    }

    private static void DrawPixel(){
        Window w = new Window(400);
        w.setLineWidth(10);
        w.drawPoint(1, 1);
        w.show();
    }

    private static void DrawLine(){
        Window w = new Window(400);
        w.setLineWidth(3);
        w.drawLine(1, 1, 2, -2);
        w.show();
    }

    private static void Color(){
        Window w = new Window(600);
        w.setLineWidth(3);
        w.setColor(Color.GREEN);
        w.drawLine(2, 2, -1, -1);
        w.show();
    }

    private static void Square(){
        Window w = new Window(600);
        w.setLineWidth(2);
        w.setColor(Color.blue);
        w.drawObject(TriangleStrip.square(1));
        w.show();
    }

    private static void Cube(){
        Window w = new Window(600);
        w.setLineWidth(2);
        w.setColor(Color.blue);
        w.drawObject(TriangleStrip.cube(1));
        w.show();
    }

    private static void ApplyTransformToCube(){
        Window w = new Window(600);
        w.setLineWidth(2);
        w.setColor(Color.blue);
        TriangleStrip cube = TriangleStrip.cube(1);
        cube.applyTransformation(Transformation3D.rotateY(45));
        w.drawObject(cube);
        w.show();
    }
}
