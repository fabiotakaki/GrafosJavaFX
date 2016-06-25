/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.desenho;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;

public class Vertex {
    private float x;
    private float y;
    private int ray = 8;
    private Boolean selected = true;
    private Color color = Color.RED;
    private int ID;
    private Circle vertex;
    
    private double orgSceneX, orgSceneY;

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
    
    public Circle createCircle() {
        vertex = new Circle(((int) this.x) - this.getRay(), ((int) this.y)
                - this.getRay(), this.getRay() * 2, this.color);
        
        if(this.selected){
        	vertex.setOpacity(1.0f);
        	vertex.setStrokeWidth(3.0f);
        }else{
        	vertex.setStyle("opacity:0.2;");
        	vertex.setOpacity(0.2f);
        	vertex.setStrokeWidth(1.5f);
        }
        
        vertex.setStroke(Color.BLACK);

        vertex.setCursor(Cursor.HAND);

        vertex.setOnMousePressed((t) -> {
          orgSceneX = t.getSceneX();
          orgSceneY = t.getSceneY();

          Circle c = (Circle) (t.getSource());
          c.toFront();
        });
        vertex.setOnMouseDragged((t) -> {
          double offsetX = t.getSceneX() - orgSceneX;
          double offsetY = t.getSceneY() - orgSceneY;

          Circle c = (Circle) (t.getSource());

          c.setCenterX(c.getCenterX() + offsetX);
          c.setCenterY(c.getCenterY() + offsetY);

          orgSceneX = t.getSceneX();
          orgSceneY = t.getSceneY();
        });
        return vertex;
    }
    
    public Circle getCircle(){
    	return this.vertex;
    }
    
    public void changeColor(){
    	vertex.setFill(this.color);
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
