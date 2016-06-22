package view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.Grafo;
import controller.ListaAdjacencia;

//algoritmos
import controller.ComponentesConexas;
import controller.Coloracao;

import Grafos.desenho.Edge;
import Grafos.desenho.Graph;
import Grafos.desenho.Vertex;
import Grafos.desenho.color.RainbowScale;
import application.Main;

public class MainController {

    // Reference to the main application.
    private Main mainApp;
    
    @FXML
    private Canvas canvasGraph;
    
    private Graph graph;
    private Grafo grafo;
    
    /**
     * É chamado pela aplicação principal para referenciar a si mesma.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Abre o FileChooser
     */
    @FXML
    private void openGraph() {
        FileChooser fileChooser = new FileChooser();

        // Define um filtro de extensão
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostra a janela de salvar arquivo
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        
        if (file != null) {
            String filename = file.getAbsolutePath();
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(filename));
                ////////LEMBRAR DE VERIFICAR SE É GRAFO OU DÍGRAFO
                ////////FAZER O TRATAMENTO NA INSERÇÃO DOS DADOS NA ESTRUTURA
                //int grafo = Integer.parseInt(in.readLine());
                
                //le numero de vertices
                int nVert =  Integer.parseInt(in.readLine());
                this.graph = new Graph(nVert); ///desenho
                this.grafo = new Grafo(nVert, new ListaAdjacencia()); ///estrutura de dados

                //leitura das arestas
                String line;
                while ((line = in.readLine()) != null && line.trim().length() > 0) {
                    StringTokenizer t1 = new StringTokenizer(line, " ");
                    int vIni = Integer.parseInt(t1.nextToken().trim()); //verticeInicial
                    int vFim = Integer.parseInt(t1.nextToken().trim()); //verticeFinal
                    //int vPeso = Integer.parseInt(t1.nextToken().trim()); //Peso do Vértice
                    
                    Vertex vS = this.graph.getVertex().get(vIni);
                    Vertex vT = this.graph.getVertex().get(vFim);
                    this.grafo.addAresta(vIni, vFim); //estrutura de dados
                    Edge e = new Edge(vS, vT); //desenho
                    //Exemplo de seleção de aresta
                    if (vIni % 2 == 0){
                        e.setSelected(true);                        
                    }
                    
                    this.graph.addEdge(e);    //desenho

                }  //se tiver peso nas arestas, adicionar mais uma leitura de token

                this.repaint();              

            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                //fechar o arquivo
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } // fim do if
    }
    
    protected void repaint(){
    	GraphicsContext gc = this.canvasGraph.getGraphicsContext2D();
        // limpa canvas pra desenhar
        gc.clearRect(0, 0, canvasGraph.getWidth(), canvasGraph.getHeight());
        graph.draw(gc);
    }
    
    @FXML
    private void coloracao(){
    	if(grafo == null) return;
        Coloracao coloracao = new Coloracao();
        coloracao.execute(grafo);
        int cores[] = coloracao.getCores();
        int nCores = coloracao.getNumCores();
        
        int coresStep = 255 / nCores;
        RainbowScale rbS = new RainbowScale();
        for (int i = 0; i < cores.length; i++) {
            System.out.println("Vertice: " + i + " Cor: " + cores[i]);
            this.graph.getVertex().get(i).setColor(rbS.getColor(cores[i] * coresStep));
        }
        this.repaint();
    }
    
    @FXML
    private void componentes(){
    	// TODO add your handling code here:
    	if(grafo == null) return;
    	ComponentesConexas componentesConexas = new ComponentesConexas();
        componentesConexas.execute(grafo);
        int comp[] = componentesConexas.getComponentes();
        int numComp = componentesConexas.getNumComponentes();
        int compStep = 255 / numComp;
        RainbowScale rbS = new RainbowScale();
        for (int i = 0; i < comp.length; i++) {
            System.out.println("Vertice: " + i + " Compoente: " + comp[i]);
            this.graph.getVertex().get(i).setColor(rbS.getColor(comp[i] * compStep));
        }
        this.repaint();
    }
    
    
}