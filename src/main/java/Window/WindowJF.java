package Window;
import BasicDatatypes.Vector;
import ObjectRep.ObjectRep;
import ObjectRep.TriangleStrip;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
public class WindowJF {
    private BufferedImage image;
    private JFrame frame;

    public WindowJF(int width, int height){
        createWindow(width, height);
    }
    private void createWindow(int width, int height){
        frame = new JFrame("Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        image = new BufferedImage((int) (frame.getWidth()*0.8f), (int) (frame.getHeight()*0.6f), BufferedImage.TYPE_INT_ARGB);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        frame.add(label);
        JButton button = new JButton("Create Cube");
        button.addActionListener(e -> createCube());
        
        frame.add(button);

        frame.setVisible(true);
    }

    private void drawCS(){
        int width2 = image.getWidth()/2;
        int height2 = image.getHeight()/2;

        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.drawLine(width2, 0, width2, image.getHeight());
        g.drawLine(0, height2, image.getWidth(), height2);

        int x = image.getWidth()/80;
        int y = image.getHeight()/80;
        for (int i = -x; i <= x; i++) {
            if(i == 0){
                continue;
            }
            g.drawLine(width2 + (i * 80), height2-5, width2 + (i * 80), height2+5);
            g.drawString(String.valueOf(i), width2 + (i*80) - 3, height2 + 20);
        }

        for (int i = -y; i < y; i++) {
            if(i == 0){
                continue;
            }
            g.drawLine(width2-5, height2 - (i * 80), width2+5, height2 - (i * 80));
            g.drawString(String.valueOf(-i), width2 + 15, height2 + (i*80) + 5);
        }

        g.drawString("0", width2+15, height2+20);

       g.dispose();
    }

    public void clearImage(){
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        drawCS();
    }
    private int convX(float x){
        return (int) (image.getWidth()/2 + x * 80f);
    }

    private int convY(float y){
        return (int) (image.getHeight()/2 + y * -80f);
    }

    public void drawLine(Graphics g, Vector v1, Vector v2) {
        g.drawLine(convX(v1.get(0)), convY(v1.get(1)), convX(v2.get(0)), convY(v2.get(1)));
    }
    public void drawTriangleStrip(TriangleStrip strip){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        if(strip.amountTriangles() == 0){
            return;
        }
        Vector v1, v2, v3;
        v1 = strip.get(0);
        v2 = strip.get(1);
        v3 = strip.get(2);
        drawLine(g, v1, v2);
        drawLine(g, v2, v3);
        drawLine(g, v3, v1);

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
            drawLine(g, v1, v3);
            drawLine(g, v2, v3);
        }
        g.dispose();
    }

    public void drawObject(ObjectRep obj){
        obj.draw(this);
    }

    public void show(){
        frame.repaint(1000000000);
    }

    private void createCube(){
        System.out.println("Pressed!");

    }
}
