package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Grafos II - 2016");

        initRootLayout();
    }
    
    public BorderPane getBP(){
    	return rootLayout;
    }

    /**
     * Inicializa o root layout (layout base).
     */
    public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MainView.fxml"));
            rootLayout = (BorderPane) loader.load();


            // Mostra a scene (cena) contendo oroot layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            // Dá ao controller o acesso ao main app.
            MainController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna o palco principal.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
