package Window;

import BasicDatatypes.Vector;
import ObjectRep.*;
import codedraw.CodeDraw;

import java.awt.Color;

public class WindowCD {
    private final CodeDraw window;
    private Color color = Color.BLACK;
    private int lineWidth = 1;

    private final Vector middle;

    public WindowCD(int width, int height){
        window = new CodeDraw(width, height);
        middle = new Vector(width/2f, height/2f);
        drawCS();
    }

    public WindowCD(int sideLength){
        this(sideLength, sideLength);
    }

    private int convX(float x){
        return (int) (middle.get(0) + x * 80f);
    }

    private int convY(float y){
        return (int) (middle.get(0) + y * -80f);
    }

    private void drawCS(){
        window.setColor(Color.BLACK);

        window.drawLine(0, middle.get(1), width(), middle.get(1));
        window.drawLine(middle.get(0), 0, middle.get(0), height());

        int x = width()/80;
        int y = height()/80;
        for (int i = -x; i <= x; i++) {
            if(i == 0){
                continue;
            }
            window.drawLine(middle.get(0) + (i * 80), middle.get(1)-5, middle.get(0) + (i * 80), middle.get(1)+5);
            window.drawText(middle.get(0)-5 +(i*80), middle.get(1)+10, "" + i);
        }

        for (int i = -y; i <= y; i++) {
            if(i == 0){
                continue;
            }
            window.drawLine(middle.get(0)-5, middle.get(1) - (i * 80), middle.get(0)+5, middle.get(1) - (i * 80));
            window.drawText(middle.get(0)+10, middle.get(1)-5 - (i*80), "" + i);
        }

        window.drawText(middle.get(0)+5, middle.get(1)+10, "0");
        window.setColor(color);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setLineWidth(int width){
        lineWidth = width;
    }

    private void dP(int x, int y){
        if(lineWidth == 1){
            window.setPixel(x, y, color);
        }

        for (int j = -(lineWidth/2); j < lineWidth/2; j++) {
            for (int k = -(lineWidth/2); k < lineWidth/2; k++) {
                window.setPixel(x + j, y + k, color);
            }

        }
    }

    public void drawPoint(float x, float y){
        dP(convX(x), convY(y));
    }

    private int sgn(int num){
        if(num < 0) return -1;
        else if(num > 0) return 1;
        return 0;
    }

    private void dL(int xStart, int yStart, int xEnd, int yEnd){
        int dX = xEnd - xStart;
        int dY = (yEnd - yStart);

        int xInc = sgn(dX);
        int yInc = sgn(dY);//* lineWidth;

        if(dX < 0){
            dX = -dX;
        }
        if(dY < 0){
            dY = -dY;
        }

        int dsx, dsy, psx, psy, dFastDir, dSlowDir;

        if(dX >= dY){
            dsx = xInc;
            dsy = yInc;
            psx = xInc;
            psy = 0;

            dFastDir = dX;
            dSlowDir = dY;
        }
        else{
            dsx = xInc;
            dsy = yInc;
            psx = 0;
            psy = yInc;
            dFastDir = dY;
            dSlowDir = dX;
        }

        int x = xStart;
        int y = yStart;
        dP(x, y);

        int p = dFastDir/2;
        for (int i = 0; i < dFastDir; i++) {
            p -= dSlowDir;
            if(p < 0){
                x+=dsx;
                y+=dsy;
                p += dFastDir;
            }
            else{
                x += psx;
                y += psy;
            }

            dP(x, y);
        }

    }

    public void drawLine(float xStart, float yStart, float xEnd, float yEnd){
        dL(convX(xStart), convY(yStart), convX(xEnd), convY(yEnd));


    }
    public void drawLine(Vector v1, Vector v2){
        drawLine(v1.get(0), v1.get(1), v2.get(0), v2.get(1));
    }


    public void drawObject(ObjectRep object){
        object.draw(this);
    }

    public void drawTriangleStrip(TriangleStrip strip){
        if(strip.amountTriangles() == 0){
            return;
        }
        Vector v1, v2, v3;
        v1 = strip.get(0);
        v2 = strip.get(1);
        v3 = strip.get(2);
        drawLine(v1, v2);
        drawLine(v2, v3);
        drawLine(v3, v1);

        for (int i = 1; i < strip.amountTriangles(); i++) {
            if(i % 2 == 0){
                v1 = strip.get(i);
                v2 = strip.get(i+1);
            }
            else {
                v1 = strip.get(i+1);
                v2 = strip.get(i);
            }
            v3 = strip.get(i+2);
            drawLine(v1, v3);
            drawLine(v2, v3);
        }
    }

    public void clear(){
        window.clear();
        drawCS();
    }
    public void show(){
        window.show();
    }

    public int width(){
        return window.getWidth();
    }

    public int height(){
        return window.getHeight();
    }
}
