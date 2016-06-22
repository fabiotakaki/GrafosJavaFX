/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.desenho;

import javafx.scene.paint.Color;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class Vertex {
    private float x;
    private float y;
    private int ray = 12;
    private Boolean selected = true;
    private Color color = Color.RED;
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void draw(GraphicsContext g2) {
        if (this.selected) {
            g2.setGlobalAlpha(1.0f);
            g2.setLineWidth(3.0f);
        } else { //not selected and there is not a global vertex selected
            g2.setGlobalAlpha(0.2f);
            g2.setLineWidth(1.5f);
        }

        g2.setFill(this.color);
        g2.fillOval(((int) this.x) - this.getRay(), ((int) this.y)
                - this.getRay(), this.getRay() * 2, this.getRay() * 2);

        g2.setStroke(Color.BLACK);
        g2.setLineWidth(3);
        g2.strokeOval(((int) this.x) - this.getRay(), ((int) this.y)
                - this.getRay(), this.getRay() * 2, this.getRay() * 2);
        g2.setGlobalAlpha(1.0f);

    }

    public float getX() {
        return x;
    }

    public void setX(float X) {
        this.x = X;
    }

    public float getY() {
        return y;
    }

    public void setY(float Y) {
        this.y = Y;
    }

    public int getRay() {
        return ray;
    }

    public void setRay(int ray) {
        this.ray = ray;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean flag) {
        this.selected = flag;
    }
}
