package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
import application.FxImaging;
import application.Main;

public class MainController {

    // Reference to the main application.
    private Main mainApp;
    
    private Graph graph;
    private Grafo grafo;
    
    @FXML
    private MenuBar menuBar;
    
    /**
     * É chamado pela aplicação principal para referenciar a si mesma.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Sobre
     */
    @FXML
    private void about(){
    	FlowPane pane;
    	pane = new FlowPane();
		Scene scene = new Scene(pane, 200, 120);	

	    Stage newStage = new Stage();
	    newStage.setScene(scene);

	    newStage.initModality(Modality.APPLICATION_MODAL);
	    newStage.setTitle("Sobre");
	    
        TextFlow message = new TextFlow();
        message.setPadding(new Insets(20, 20, 20, 20));
        Text text = new Text("Desenvolvido por:\n");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Text fabio = new Text("\nFábio S. Takaki");
        Text arthur = new Text("\nArthur Pires");
        Text lucas = new Text("\nLucas Martins");
        message.getChildren().addAll(text, fabio);
        
        pane.getChildren().add(message);
        newStage.showAndWait();
    }
    
    /**
     * Salva png como prometido :)
     */
    @FXML
    private void saveToPNG(){
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar PNG");
         
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        
    	FxImaging imager = new FxImaging();
        imager.nodeToImage(mainApp.getBP().getCenter(),mainApp.getBP().getChildren(),file);
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
                int grafo = Integer.parseInt(in.readLine());
                
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
                    int vPeso = Integer.parseInt(t1.nextToken().trim()); //Peso do Vértice
                    
                    Vertex vS = this.graph.getVertex().get(vIni);
                    Vertex vT = this.graph.getVertex().get(vFim);
                    this.grafo.addAresta(vIni, vFim, vPeso, grafo); //estrutura de dados
                    Edge e = new Edge(vS, vT, vPeso, grafo); //desenho
                    //Exemplo de seleção de aresta
                    if (vIni % 2 == 0){
                        e.setSelected(true);                        
                    }
                    
                    this.graph.addEdge(e);    //desenho

                }  //se tiver peso nas arestas, adicionar mais uma leitura de token

                this.addObjects();
                
                // Gero o menu algoritmos
                // verifico se existe algum menu já criado e deleto
                if(menuBar.getMenus().size() > 1) menuBar.getMenus().remove(1);
                
                final Menu algorithms = new Menu("Algoritmos");
                algorithms.setId("algorithms");
                menuBar.getMenus().add(algorithms);
    
                if(grafo == 0){
	                // Coloração
	                MenuItem coloracao = new MenuItem("Coloração");
	                algorithms.getItems().add(coloracao);
	                coloracao.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                        coloracao();
	                    }
	                });
	                
	                // Componentes
	                MenuItem componentes = new MenuItem("Componentes");
	                algorithms.getItems().add(componentes);
	                componentes.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                    	componentes();
	                    }
	                });
                }
                
                // mensagem
                TextFlow message = new TextFlow();
                message.setPadding(new Insets(20, 20, 20, 20));
                Text text = new Text("Selecione um algoritmo para interagir com o grafo ou dígrafo.");
                text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                message.getChildren().addAll(text);
                mainApp.getBP().setBottom(message);

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
    
    protected void addObjects(){
    	graph.addObjects(mainApp.getBP());
    }
    
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
            this.graph.getVertex().get(i).changeColor();
        }
    }
    
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
            System.out.println("Vertice: " + i + " Componente: " + comp[i]);
            this.graph.getVertex().get(i).setColor(rbS.getColor(comp[i] * compStep));
            this.graph.getVertex().get(i).changeColor();
        }
    }
    
    
}