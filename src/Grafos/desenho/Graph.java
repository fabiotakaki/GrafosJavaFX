/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.desenho;

import Grafos.desenho.color.ColorScale;
import Grafos.desenho.color.GrayScale;
import Grafos.desenho.color.RainbowScale;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class Graph {
    public Graph(int nVert) {
        RainbowScale cS = new RainbowScale();
        //GrayScale cS = new GrayScale();
        int colorStep = 255 / nVert;
        for (int i=0; i<nVert; i++){
            Vertex v = new Vertex();
            v.setID(i);
            v.setColor(cS.getColor(i*colorStep));
           // v.setColor(Color.RED);
//            if (i % 2 == 0){
//                v.setSelected(false);
//            }
            this.vertex.add(v);
        }
        computeCircledPosition(150);
    }

    public void addVertex(Vertex v){
        this.vertex.add(v);
    }

    public void addEdge(Edge e){
        this.edges.add(e);
    }

    public void computeCircledPosition(int ray){
        int nVert = this.vertex.size();
        int step = 360 / nVert;
        int deslocX = 100 + ray;
        int deslocY = 100 + ray;
        for (int i=0; i<nVert; i++){
            double ang = i * step;
            ang = ang * Math.PI / 180;//necessario em radianos
            float X = (float) Math.cos(ang);
            float Y = (float) Math.sin(ang);
            X = X * ray + deslocX;
            Y = Y * ray + deslocY;
            this.vertex.get(i).setX(X);
            this.vertex.get(i).setY(Y);
        }
    }

    public ArrayList<Vertex> getVertex() {
        return this.vertex;
    }
    
    public void addObjects(BorderPane bp){
    	//Draw each vertice of the graph
    	Group group = new Group();
        for (Vertex v : this.vertex) {
            Circle circle = v.createCircle();
            group.getChildren().add(circle);
        }
        
        for (Edge edge : edges) {
            Group line = edge.connect(edge.getSource().getCircle(), edge.getTarget().getCircle());
            group.getChildren().add(line);
            edge.getSource().getCircle().toFront();
            edge.getTarget().getCircle().toFront();
        }
        
        bp.setCenter(group);
    }

    public java.awt.Dimension getSize() {
        if (this.vertex.size() > 0) {
            float maxX = vertex.get(0).getX();
            float minX = vertex.get(0).getX();
            float maxY = vertex.get(0).getY();
            float minY = vertex.get(0).getY();

            //Encontra o maior e menor valores para X e Y
            for (Vertex v : this.vertex) {
                if (maxX < v.getX()) {
                    maxX = v.getX();
                } else {
                    if (minX > v.getX()) {
                        minX = v.getX();
                    }
                }

                if (maxY < v.getY()) {
                    maxY = v.getY();
                } else {
                    if (minY > v.getY()) {
                        minY = v.getY();
                    }
                }
            }

            int w = (int) (maxX + (this.vertex.get(0).getRay() * 5)) + 350;
            int h = (int) (maxY + (this.vertex.get(0).getRay() * 5));

            return new java.awt.Dimension(w, h);
        } else {
            return new java.awt.Dimension(0, 0);
        }
    }
    protected ArrayList<Vertex> vertex = new ArrayList<Vertex>();
    protected ArrayList<Edge> edges = new ArrayList<Edge>();
}
