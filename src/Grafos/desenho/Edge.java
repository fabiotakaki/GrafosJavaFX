/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.desenho;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class Edge {

    private Color color = Color.WHITE; //Cor da aresta
    private Vertex source; //primeiro vetice da aresta
    private Vertex target; //segundo vertice da aresta
    private int weight; //Peso
    private Boolean directed = true; //se a aresta é direcionada
    private Boolean selected = false; //se a aresta está selecionada
    private Line line;
    private Group arrow;
    private Line line1;
    private Line line2;

    public Edge(Vertex source, Vertex target, int weight, int directed) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        if(directed == 0) this.directed = false;
    }
    
    // para direcionado
    private float r;
    private float cos;
    private float sen;
    
    public Line getConnect(){
    	return this.line;
    }
    
    public void clear(){
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
    	
    	arrow.getChildren().remove(line1);
    	arrow.getChildren().remove(line2);
    	
    	if(isDirected()){
	        
	        line1 = new Line();
	        line2 = new Line();
	 
	    	line.endXProperty().addListener((observable, oldvalue, newvalue)->{
	    		updateArrow();     
	        });
	    	
	    	line.endYProperty().addListener((observable, oldvalue, newvalue)->{
	    		updateArrow();
	        });
	    	
	    	line.startXProperty().addListener((observable, oldvalue, newvalue)->{
	    		updateArrow();
	        });
	    	
	    	line.startYProperty().addListener((observable, oldvalue, newvalue)->{
	    		updateArrow();
		        
	        });
	    	
	    	updateArrow();
	        
	        arrow.getChildren().add(line1);
	        arrow.getChildren().add(line2);
	        
	    }
    }
    
    public Group connect(Circle c1, Circle c2) {
    	arrow = new Group();
        line = new Line();
        
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
	    
        TextFlow weight = new TextFlow();
        weight.setLayoutX((line.startXProperty().doubleValue() + line.endXProperty().doubleValue())/ 2);
        weight.setLayoutY((line.startYProperty().doubleValue() + line.endYProperty().doubleValue())/ 2);
        Text text = new Text(Integer.toString(this.weight));
        text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        weight.getChildren().addAll(text);
        arrow.getChildren().add(weight);
	    
	    this.clear();
	    
	    line.endXProperty().addListener((observable, oldvalue, newvalue)->{
    		weight.setLayoutX((line.startXProperty().get() + newvalue.doubleValue())/2);
	        
        });
    	
    	line.endYProperty().addListener((observable, oldvalue, newvalue)->{
    		weight.setLayoutY((line.startYProperty().get() + newvalue.doubleValue())/2);
	        
        });
    	
    	line.startXProperty().addListener((observable, oldvalue, newvalue)->{
    		weight.setLayoutX((newvalue.doubleValue() + line.endXProperty().get())/2);
        });
    	
    	line.startYProperty().addListener((observable, oldvalue, newvalue)->{
    		weight.setLayoutY((newvalue.doubleValue() + line.endYProperty().get())/2); 
        });

        return arrow;
    }
    
    protected void updateArrow(){
    	int size = 6;
    	int deslocamento = 30;
    	
    	double SourceX = line.startXProperty().get();
    	double TargetX = line.endXProperty().get();
    	double SourceY = line.startYProperty().get();
    	double TargetY = line.endYProperty().get();
        
        this.r = (float) Math.sqrt(Math.pow(SourceX - TargetX, 2) + Math.pow(SourceY - TargetY, 2));
        this.cos = (float) ((TargetX - SourceX) / r);
        this.sen = (float) ((TargetY - SourceY) / r);
        
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;
    	
        line1.setEndY(TargetY + Math.round( xAB * -sen + yA * -cos ));
        line1.setEndX(TargetX + Math.round( xAB * -cos - yA * -sen ));
        
        line1.setStrokeWidth(4.0f);
        line1.setStartX(TargetX + Math.round( deslocamento * -cos));
        line1.setStartY(TargetY + Math.round( deslocamento * -sen));
        

        line2.setEndY(TargetY +Math.round( xAB * -sen + yB * -cos ));
        line2.setEndX(TargetX + Math.round( xAB * -cos - yB * -sen ));
        line2.setStrokeWidth(4.0f);
        line2.setStartX(TargetX + Math.round( deslocamento * -cos));
        line2.setStartY(TargetY + Math.round( deslocamento * -sen));
    }
    
    public void direct(){
    	arrow.getChildren().remove(line1);
    	arrow.getChildren().remove(line2);
    	
    	double SourceX = line.startXProperty().get();
    	double TargetX = line.endXProperty().get();
    	double SourceY = line.startYProperty().get();
    	double TargetY = line.endYProperty().get();
    	this.r = (float) Math.sqrt(Math.pow(SourceX - TargetX, 2) + Math.pow(SourceY - TargetY, 2));
        this.cos = (float) ((TargetX - SourceX) / r);
        this.sen = (float) ((TargetY - SourceY) / r);
        
    	
             
        line1 = new Line();
        line2 = new Line();
 
    	line.endXProperty().addListener((observable, oldvalue, newvalue)->{
    		updateArrow();     
        });
    	
    	line.endYProperty().addListener((observable, oldvalue, newvalue)->{
    		updateArrow();
        });
    	
    	line.startXProperty().addListener((observable, oldvalue, newvalue)->{
    		updateArrow();
        });
    	
    	line.startYProperty().addListener((observable, oldvalue, newvalue)->{
    		updateArrow();
	        
        });
    	
    	updateArrow();
        
        arrow.getChildren().add(line1);
        arrow.getChildren().add(line2);
    }
    
    public Vertex getSource()
    {
    	return this.source;
    }
    
    public Vertex getTarget(){
    	return this.target;
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
