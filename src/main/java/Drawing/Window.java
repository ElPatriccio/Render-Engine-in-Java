package Drawing;

import BasicDatatypes.Vector;
import ObjectRep.ObjectRep;
import ObjectRep.TriangleStrip;


import java.awt.*;

public interface Window {

    void setColor(Color color);
    void setLineWidth(int width);
    void drawPoint(float x, float y);

    void drawLine(float xStart, float yStart, float xEnd, float yEnd);

    void drawLine(Vector start, Vector end);

    default void drawObject(ObjectRep obj, boolean wireframe){
        obj.draw(this, wireframe);
    }

    void drawTriangleStrip(TriangleStrip strip, boolean wireframe);

    default void drawObject(ObjectRep obj){
        drawObject(obj, true);
    }

    void clear();

    void show();

    int width();
    int height();

}
