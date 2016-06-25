/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.desenho;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.geometry.Point2D;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class Edge {

    private Color color = Color.WHITE; //Cor da aresta
    private Vertex source; //primeiro vetice da aresta
    private Vertex target; //segundo vertice da aresta
    private Boolean directed = true; //se a aresta é direcionada
    private Boolean selected = false; //se a aresta está selecionada

    public Edge(Vertex source, Vertex target) {
        this.source = source;
        this.target = target;
    }

    public void draw(GraphicsContext g2) {
        //Combines the color of the two vertex to paint the edge

        if (selected) {
            g2.setGlobalAlpha(1.0f);
            g2.setLineWidth(3.0f);
        } else {
            g2.setLineWidth(1.0f);
            if ((this.target.isSelected() && this.source.isSelected())) { //se os vertices estao selecionados
                g2.setGlobalAlpha(0.5f);
            } else {//se os vertices nao estao selecionados
                g2.setGlobalAlpha(0.2f);
            }
        }
       this.color = new Color((this.source.getColor().getRed() + this.target.getColor().getRed()) / 2,
                (this.source.getColor().getGreen() + this.target.getColor().getGreen()) / 2,
                (this.source.getColor().getBlue() + this.target.getColor().getBlue()) / 2, 1.0);

        g2.setStroke(this.color);
        
        g2.strokeLine(((int) this.source.getX()), ((int) this.source.getY()),
                ((int) this.target.getX()), ((int) this.target.getY()));
        g2.setLineWidth(1.0f);

        if (isDirected()) {
//            drawArrow(g2, new Point2D((int) source.getX(), (int) source.getY()),
//                    new Point2D((int) target.getX(), (int) target.getY()),
//                    6.0f);
            
            drawArrowNew(g2, new Point2D((int) source.getX(), (int) source.getY()),
                    new Point2D((int) target.getX(), (int) target.getY()),
                    6, 14);
        }

        g2.setGlobalAlpha(1.0f);
    }
    
    public Group connect(Circle c1, Circle c2) {
    	Group arrow = new Group();
        Line line = new Line();
        
        if (selected) {
            line.setOpacity(1.0f);
            line.setStrokeWidth(3.0f);
        } else {
            line.setStrokeWidth(1.0f);
            if ((this.target.isSelected() && this.source.isSelected())) { //se os vertices estao selecionados
                line.setOpacity(0.5f);
            } else {//se os vertices nao estao selecionados
            	line.setOpacity(0.2f);
            }
        }
        
        this.color = new Color((this.source.getColor().getRed() + this.target.getColor().getRed()) / 2,
                (this.source.getColor().getGreen() + this.target.getColor().getGreen()) / 2,
                (this.source.getColor().getBlue() + this.target.getColor().getBlue()) / 2, 1.0);

        line.startXProperty().bind(c1.centerXProperty());
        line.startYProperty().bind(c1.centerYProperty());

        line.endXProperty().bind(c2.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());

        line.setStroke(color);
        
	    line.toBack();
	    
	    arrow.getChildren().add(line);
	    
	    if(isDirected()){
	    	int size = 6;
	    	int deslocamento = 30;
//	    	Point2D s = new Point2D((int) source.getX(), (int) source.getY());
//	    	Point2D t = new Point2D((int) target.getX(), (int) target.getY());    	
//	    	float r = (float) Math.sqrt(Math.pow(s.getX() - t.getX(), 2) + Math.pow(s.getY() - t.getY(), 2));
//	        float cos = (float) ((t.getX() - s.getX()) / r);
//	        float sen = (float) ((t.getY() - s.getY()) / r); 
	    	
	    	line.endXProperty().addListener((observable, oldvalue, newvalue)->{
            	System.out.println(newvalue);	
	        	}
	        );
	    
	    	double SourceX = line.startXProperty().get();
	    	double TargetX = line.endXProperty().get();
	    	double SourceY = line.startYProperty().get();
	    	double TargetY = line.endYProperty().get();
	    	float r = (float) Math.sqrt(Math.pow(SourceX - TargetX, 2) + Math.pow(SourceY - TargetY, 2));
	        float cos = (float) ((TargetX - SourceX) / r);
	        float sen = (float) ((TargetY - SourceY) / r);
	        
	    	
	        int xAB = size + deslocamento;
	        int yA = size;
	        int yB = -size;
	             
//	        g2.strokeLine(pc.getX(), pc.getY(), pa.getX(), pa.getY());
//	        g2.strokeLine(pc.getX(), pc.getY(), pb.getX(), pb.getY());
	        Line line1 = new Line();
	        Line line2 = new Line();
	 
	        line1.endYProperty().bind(line.endYProperty().add(Math.round( xAB * -sen + yA * -cos )));
	        line1.endXProperty().bind(line.endXProperty().add(Math.round( xAB * -cos - yA * -sen )));
	        
	        line1.setStrokeWidth(4.0f);
	        line1.startXProperty().bind(line.endXProperty().add(Math.round( deslocamento * -cos)));
	        line1.startYProperty().bind(line.endYProperty().add(Math.round( deslocamento * -sen)));
	        

	        line2.endYProperty().bind(line.endYProperty().add(Math.round( xAB * -sen + yB * -cos )));
	        line2.endXProperty().bind(line.endXProperty().add(Math.round( xAB * -cos - yB * -sen )));
	        line2.setStrokeWidth(4.0f);
	        line2.startXProperty().bind(line.endXProperty().add(Math.round( deslocamento * -cos)));
	        line2.startYProperty().bind(line.endYProperty().add(Math.round( deslocamento * -sen)));
	        
	        arrow.getChildren().add(line1);
	        arrow.getChildren().add(line2);
	        
	    }

        return arrow;
    }
    
    
    
    public Vertex getSource()
    {
    	return this.source;
    }
    
    public Vertex getTarget(){
    	return this.target;
    }
    
    private void drawArrowNew(GraphicsContext g2, Point2D s, Point2D t, int size, int deslocamento) {
        float r = (float) Math.sqrt(Math.pow(s.getX() - t.getX(), 2) + Math.pow(s.getY() - t.getY(), 2));
        float cos = (float) ((t.getX() - s.getX()) / r);
        float sen = (float) ((t.getY() - s.getY()) / r);                
        
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;
        
        Point2D pa = new Point2D(Math.round( xAB * -cos - yA * -sen )+t.getX(), Math.round( xAB * -sen + yA * -cos )+t.getY());
        Point2D pb = new Point2D(Math.round( xAB * -cos - yB * -sen )+t.getX(), Math.round( xAB * -sen + yB * -cos )+t.getY());
        Point2D pc = new Point2D(Math.round( deslocamento * -cos)+t.getX(), Math.round( deslocamento * -sen)+t.getY());
        
        g2.strokeLine(pc.getX(), pc.getY(), pa.getX(), pa.getY());
        g2.strokeLine(pc.getX(), pc.getY(), pb.getX(), pb.getY());
    }

    private void drawArrow(GraphicsContext g2, Point2D s, Point2D t, float size) {
        float r = (float) Math.sqrt(Math.pow(s.getX() - t.getX(), 2) + Math.pow(s.getY() - t.getY(), 2));
        float cos = (float) ((t.getX() - s.getX()) / (r));
        float sen = (float) ((t.getY() - s.getY()) / (r));

        //rotação e translação
        int transX = (int) ((t.getX() + s.getX()) * 0.5f); //metade da reta
        int transY = (int) ((t.getY() + s.getY()) * 0.5f); //metade da reta

        Point2D pa = new Point2D(Math.round(-sen * size) + (transX), Math.round(cos * size) + (transY));
        Point2D pb = new Point2D(Math.round(-sen * -size) + (transX), Math.round(cos * -size) + (transY));
        Point2D pc = new Point2D(Math.round(cos * size) + (transX), Math.round(sen * size) + (transY));

        g2.strokeLine(pa.getX(), pa.getY(), pc.getX(), pc.getY());
        g2.strokeLine(pb.getX(), pb.getY(), pc.getX(), pc.getY());

//        g2.setFont(new Font("Verdana", Font.BOLD, 10));
//        java.awt.FontMetrics metrics = g2.getFontMetrics(g2.getFont());
//        g2.drawString("T", pc.x, pc.y);
    }

    public Boolean isDirected() {
        return directed;
    }

    public void setDirected(Boolean directed) {
        this.directed = directed;
    }
    
    public void setSelected(Boolean selected){
        this.selected = selected;
    }
}
