package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
import controller.TopologicalOrder;
import controller.Transposed;
import controller.WidthSearch;
//algoritmos
import controller.ComponentesConexas;
import controller.Connectivity;
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
        message.getChildren().addAll(text, fabio, arthur, lucas);
        
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG png(*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if(!file.getName().contains(".")) {
    	  file = new File(file.getAbsolutePath() + ".png");
    	}
        
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
                int type_grafo = Integer.parseInt(in.readLine());
                
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
                    
                    if(type_grafo == 0){
                    	this.grafo.addAresta(vIni, vFim, vPeso); //estrutura de dados
                    	Edge e1 = new Edge(vS, vT, vPeso, type_grafo); //desenho
                    	this.graph.addEdge(e1);    //desenho
                    	Edge e2 = new Edge(vT, vS, vPeso, type_grafo); //desenho
                    	this.graph.addEdge(e2);    //desenho
                    }else{
                    	this.grafo.addArestaD(vIni, vFim, vPeso); //estrutura de dados
                    	Edge e = new Edge(vS, vT, vPeso, type_grafo); //desenho
                    	this.graph.addEdge(e);    //desenho
                    }
                    

                }  //se tiver peso nas arestas, adicionar mais uma leitura de token
                this.addObjects();
                
                // Gero o menu algoritmos
                // verifico se existe algum menu já criado e deleto
                if(menuBar.getMenus().size() > 1) menuBar.getMenus().remove(1);
                
                final Menu algorithms = new Menu("Algoritmos");
                algorithms.setId("algorithms");
                menuBar.getMenus().add(algorithms);
                
                // limpar grafo
                MenuItem clearGraph = new MenuItem("Limpar Grafo");
                algorithms.getItems().add(clearGraph);
                clearGraph.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						clearObjects();
						mainApp.getBP().setBottom(null);
					} 	
                });
                
                // Busca Largura
                MenuItem largura = new MenuItem("Busca em Largura");
                algorithms.getItems().add(largura);
                largura.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                    	SplitPane sp = new SplitPane();
                    	Text t = new Text("Digite o vértice:");
                    	TextField tf = new TextField();
                    	Button execute = new Button("Executar");
                    	execute.setOnAction(new EventHandler<ActionEvent>(){
                    		@Override public void handle(ActionEvent e){
                    			buscaLargura(Integer.parseInt(tf.getText()));
                    		}
                    	});
                    	sp.getItems().addAll(t, tf, execute);
                    	mainApp.getBP().setBottom(sp);
                    	buscaLargura(0);
                    }
                });
    
                if(type_grafo == 0){
	                // Coloração
	                MenuItem coloracao = new MenuItem("Coloração");
	                algorithms.getItems().add(coloracao);
	                coloracao.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                        coloracao();
	                    }
	                });
	                
	                // Componentes
	                MenuItem componentes = new MenuItem("Componentes Conexas");
	                algorithms.getItems().add(componentes);
	                componentes.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                    	componentes();
	                    }
	                });
	                
	                
	                
	                // Prim
	                MenuItem prim = new MenuItem("Prim");
	                algorithms.getItems().add(prim);
	                prim.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                    	SplitPane sp = new SplitPane();
	                    	Text t = new Text("Digite o vértice:");
	                    	TextField tf = new TextField();
	                    	Button execute = new Button("Executar");
	                    	execute.setOnAction(new EventHandler<ActionEvent>(){
	                    		@Override public void handle(ActionEvent e){
	                    			Prim(Integer.parseInt(tf.getText()));
	                    		}
	                    	});
	                    	sp.getItems().addAll(t, tf, execute);
	                    	mainApp.getBP().setBottom(sp);
	                    	Prim(0);
	                    }
	                });
	                
	                // Dijkstra
	                MenuItem dijkstra = new MenuItem("Dijkstra");
	                algorithms.getItems().add(dijkstra);
	                dijkstra.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                    	SplitPane sp = new SplitPane();
	                    	Text t = new Text("Digite o vértice:");
	                    	TextField tf = new TextField();
	                    	Button execute = new Button("Executar");
	                    	execute.setOnAction(new EventHandler<ActionEvent>(){
	                    		@Override public void handle(ActionEvent e){
	                    			Dijkstra('g', Integer.parseInt(tf.getText()));
	                    		}
	                    	});
	                    	sp.getItems().addAll(t, tf, execute);
	                    	mainApp.getBP().setBottom(sp);
	                    	Dijkstra('g', 0);
	                    }
	                });
                }else{
	                
	                // Dijkstra
	                MenuItem dijkstra = new MenuItem("Dijkstra");
	                algorithms.getItems().add(dijkstra);
	                dijkstra.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                    	SplitPane sp = new SplitPane();
	                    	Text t = new Text("Digite o vértice:");
	                    	TextField tf = new TextField();
	                    	Button execute = new Button("Executar");
	                    	execute.setOnAction(new EventHandler<ActionEvent>(){
	                    		@Override public void handle(ActionEvent e){
	                    			Dijkstra('d', Integer.parseInt(tf.getText()));
	                    		}
	                    	});
	                    	sp.getItems().addAll(t, tf, execute);
	                    	mainApp.getBP().setBottom(sp);
	                    	Dijkstra('d', 0);
	                    }
	                });
	                
	                // Transposição
	                MenuItem transposed = new MenuItem("Transposição");
	                algorithms.getItems().add(transposed);
	                transposed.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                    	transposed();
	                    }
	                });
	                
	                // Conectividade
	                MenuItem connectivity = new MenuItem("Conectividade");
	                algorithms.getItems().add(connectivity);
	                connectivity.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                    	SplitPane sp = new SplitPane();
	                    	Text t = new Text("Digite o vértice:");
	                    	TextField tf = new TextField();
	                    	Button execute = new Button("Executar");
	                    	execute.setOnAction(new EventHandler<ActionEvent>(){
	                    		@Override public void handle(ActionEvent e){
	                    			connectivity(Integer.parseInt(tf.getText()));
	                    		}
	                    	});
	                    	sp.getItems().addAll(t, tf, execute);
	                    	mainApp.getBP().setBottom(sp);
	                    	connectivity(0);
	                    }
	                });
	                
	                // Ordem topológica
	                MenuItem topological = new MenuItem("Ordem Topológica");
	                algorithms.getItems().add(topological);
	                topological.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent e) {
	                    	SplitPane sp = new SplitPane();
	                    	Text t = new Text("Digite o vértice:");
	                    	TextField tf = new TextField();
	                    	Button execute = new Button("Executar");
	                    	execute.setOnAction(new EventHandler<ActionEvent>(){
	                    		@Override public void handle(ActionEvent e){
	                    			topological(Integer.parseInt(tf.getText()));
	                    		}
	                    	});
	                    	sp.getItems().addAll(t, tf, execute);
	                    	mainApp.getBP().setBottom(sp);
	                    	topological(0);
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
    
    private void clearObjects(){
    	graph.clearObjects();	
    }
    
    private void buscaLargura(int vertex){
    	clearObjects();
    	WidthSearch w = new WidthSearch(grafo);
    	w.process(vertex);
    	int distance[] = w.getDistances();
        int nCores = 10;
        
        int coresStep = 250 / nCores;
        
        RainbowScale rbS = new RainbowScale();
		for(int i=0; i< grafo.getNumVertices(); i++){
            if(distance[i] == Integer.MAX_VALUE){
                System.out.println("D ["+i+"]: XX\n");
                this.graph.getVertex().get(i).changeColor(rbS.getColor(255));
            }else{
                System.out.println("D ["+i+"]: "+distance[i]+"\n");
	            this.graph.getVertex().get(i).changeColor(rbS.getColor(distance[i] * coresStep));
            }
		}
    }
    
    private void coloracao(){
    	clearObjects();
    	mainApp.getBP().setBottom(null);
        Coloracao coloracao = new Coloracao();
        coloracao.execute(grafo);
        int cores[] = coloracao.getCores();
        int nCores = coloracao.getNumCores();
        
        int coresStep = 255 / nCores;
        RainbowScale rbS = new RainbowScale();
        for (int i = 0; i < cores.length; i++) {
            System.out.println("Vertice: " + i + " Cor: " + cores[i]);
            this.graph.getVertex().get(i).changeColor(rbS.getColor(cores[i] * coresStep));
        }
    }
    
    private void Prim(int v){
    	clearObjects();
    	controller.Prim p = new controller.Prim(grafo);
    	Grafo primG = p.process(v);
    	
    	for (Edge edge : this.graph.getEdges()) {
            edge.getConnect().setOpacity(0.1f);
        }
    	
    	for (Vertex vertex : this.graph.getVertex()) {
    		vertex.getCircle().setOpacity(0.1f);
        }
    	
    	ListaAdjacencia l = (ListaAdjacencia) primG.getRepresentacao();
    	l.prim(graph);
    }
    
    private void Dijkstra(char type, int vertex){
    	clearObjects();
    	controller.Dijkstra d = new controller.Dijkstra(grafo);
    	int pi[] = d.process(vertex, type);
    	int x=0;
    	while(x < grafo.getNumVertices()){
	    	for (Edge edge : graph.getEdges()) {
	    		if(x == edge.getTarget().getID() && pi[x] == edge.getSource().getID()){
	    			edge.direct();
	    			edge.getConnect().setOpacity(1.0f);
	    			edge.getConnect().setStrokeWidth(3.0f);
	    		}
	    		System.out.print(edge.getSource().getID()+"->"+edge.getTarget().getID()+"| ");
	    	}
    		System.out.println("\nP "+x+":"+pi[x]);
    		x++;
    	}
    }
    
    private void componentes(){
    	clearObjects();
    	mainApp.getBP().setBottom(null);
    	// TODO add your handling code here:
    	ComponentesConexas componentesConexas = new ComponentesConexas();
        componentesConexas.execute(grafo);
        int comp[] = componentesConexas.getComponentes();
        int numComp = componentesConexas.getNumComponentes();
        int compStep = 255 / numComp;
        RainbowScale rbS = new RainbowScale();
        for (int i = 0; i < comp.length; i++) {
            System.out.println("Vertice: " + i + " Componente: " + comp[i]);
            this.graph.getVertex().get(i).changeColor(rbS.getColor(comp[i] * compStep));
        }
    }
    
    private void transposed(){
    	clearObjects();
    	mainApp.getBP().setBottom(null);
    	Transposed t = new Transposed(grafo);
    	t.execute();
    	
    	BorderPane pane;
    	pane = new BorderPane();
		Scene scene = new Scene(pane, 400, 400);	

	    Stage newStage = new Stage();
	    newStage.setScene(scene);

	    newStage.initModality(Modality.APPLICATION_MODAL);
	    newStage.setTitle("Transposição");
	    
	    Graph d = t.getGraph();
	    d.addObjects(pane);
        
        newStage.showAndWait();
    }
    
    private void connectivity(int vertex){
    	clearObjects();
    	Connectivity c = new Connectivity(grafo, graph);
    	c.process(vertex);
    }
    
    private void topological(int vertex){
    	clearObjects();
    	mainApp.getBP().setBottom(null);
    	TopologicalOrder to = new TopologicalOrder(grafo, graph);
    	to.process(vertex);
    	// redimensiono janela :)
    	mainApp.getPrimaryStage().setWidth(100*grafo.getNumVertices());
    }
    
    
}
    
 