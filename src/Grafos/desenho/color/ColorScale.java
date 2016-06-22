/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Grafos.desenho.color;

import javafx.scene.paint.Color;

/**
 *
 * @author Danilo Medeiros Eler
 */
public abstract class ColorScale {
     public abstract String getName();
     
     public Color getColor(int index){
         if (index > 255) index = 255;
         if (index <= 0) index = 0;
         return colors[index];
     }

    protected Color[] colors;
}
