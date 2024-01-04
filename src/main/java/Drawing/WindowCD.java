package Drawing;

import BasicDatatypes.Vector;
import ObjectRep.*;
import codedraw.CodeDraw;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

public class WindowCD implements Window{
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
        return (int) (middle.get(1) + y * -80f);
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
        window.setColor(color);
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

    public void floodFill(Vector seed) {
        int oldLineWidth = lineWidth;
        lineWidth = 1;
        int x = convX(seed.get(0));
        int y = convY(seed.get(1));
        Color oldColor = window.getPixel(x, y);
        Queue<Vector> queue = new LinkedList<>();
        queue.add(new Vector(x, y));

        while (!queue.isEmpty()) {
            Vector curr = queue.poll();
            int currX = (int) curr.get(0);
            int currY = (int) curr.get(1);

            if (currX >= 0 && currX < window.getWidth() && currY >= 0 && currY < window.getHeight()) {
                if (window.getPixel(currX, currY).getRGB() == oldColor.getRGB()) {
                    dP(currX, currY);

                    queue.add(new Vector(currX, currY - 1));
                    queue.add(new Vector(currX + 1, currY));
                    queue.add(new Vector(currX, currY + 1));
                    queue.add(new Vector(currX - 1, currY));
                }
            }
        }
        lineWidth = oldLineWidth;
    }

    private void threadHelper(Vector aWindow, Vector v0, Vector v1, float d00, float d01, float d11, float denominator, BoundingBox b){
        for (int i = (int) b.minX(); i < b.maxX(); i++) {
            for (int j = (int)b.minY(); j < b.maxY(); j++) {
                if(Tools.insideTriangle(Tools.barycentric(
                        aWindow, v0, v1, new Vector(i, j), d00, d01, d11, denominator)
                )){
                    window.setPixel(i, j, color);
                }
            }
        }
    }
    public void fillTriangleBaryCentric(Vector a, Vector b, Vector c){
        Vector aWindow = new Vector(convX(a.get(0)), convY(a.get(1)));
        Vector bWindow = new Vector(convX(b.get(0)), convY(b.get(1)));
        Vector cWindow = new Vector(convX(c.get(0)), convY(c.get(1)));

        BoundingBox bounds = new BoundingBox(new Vector[]{aWindow, bWindow, cWindow});
        Vector v0 = Vector.sub(bWindow, aWindow);
        Vector v1 = Vector.sub(cWindow, aWindow);

        float d00 = v0.dot(v0);
        float d01 = v0.dot(v1);
        float d11 = v1.dot(v1);

        float denominator = d00*d11 - d01 * d01;

        float midX = bounds.maxX() - bounds.minX();
        float midY = bounds.maxY() - bounds.minX();

//        BoundingBox topLeft = new BoundingBox(bounds.minX(), midX, midY, bounds.maxY());
//        BoundingBox bottomLeft = new BoundingBox(bounds.minX(), midX, bounds.minY(), midY);
//        BoundingBox topRight = new BoundingBox(midX, bounds.maxX(), midY, bounds.maxY());
//        BoundingBox bottomRight = new BoundingBox(midX, bounds.maxX(), bounds.minY(), midY);

//        Thread t1, t2, t3, t4;
//        t1 = new Thread(() -> threadHelper(aWindow, v0, v1, d00, d01, d11, denominator, topLeft));
//        t2 = new Thread(() -> threadHelper(aWindow, v0, v1, d00, d01, d11, denominator, bottomLeft));
//        t3 = new Thread(() -> threadHelper(aWindow, v0, v1, d00, d01, d11, denominator, topRight));
//        t4 = new Thread(() -> threadHelper(aWindow, v0, v1, d00, d01, d11, denominator, bottomRight));
//
//        t1.start();
//        t2.start();
//        t3.start();
//        t4.start();

        threadHelper(aWindow, v0, v1, d00, d01, d11, denominator, bounds);



//        try {
//            t1.join();
//            t2.join();
//            t3.join();
//            t4.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void drawTriangleStrip(TriangleStrip strip, boolean wireframe){
        if(strip.amountTriangles() == 0){
            return;
        }
        Vector v1, v2, v3;
        boolean prevDrawn = false;
        for (int i = 0; i < strip.amountTriangles(); i++) {
            if(!wireframe && !(strip.surfaceNormal(i).get(2) < 0 + 0.0001f)){
                prevDrawn = false;
                continue;
            }
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
            if(!prevDrawn){
                drawLine(v1, v2);
                prevDrawn = true;
            }
        }
    }
    public void clear(boolean showCs){
        window.clear();
        if(showCs) drawCS();
    }

    public void clear(){
        clear(true);
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
