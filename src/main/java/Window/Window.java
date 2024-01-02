package Window;

import BasicDatatypes.Vector;
import ObjectRep.ObjectRep;
import codedraw.CodeDraw;

import java.awt.Color;

public class Window {
    private final CodeDraw window;
    private Color color;

    private final Vector middle;

    public Window(int width, int height){
        window = new CodeDraw(width, height);
        middle = new Vector(width/2f, height/2f);
        drawCS();
    }

    public Window(int sideLength){
        this(sideLength, sideLength);
    }

    private int convX(int x){
        return (int) middle.get(0) + x * 80;
    }

    private int convY(int y){
        return (int) middle.get(0) + y * -80;
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
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setPixel(int x, int y){
        window.setPixel(convX(x), convY(y), color);
    }

    public void drawObject(ObjectRep object){
        object.draw(this);
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
