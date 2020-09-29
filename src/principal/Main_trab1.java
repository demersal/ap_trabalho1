package principal;
	
import org.opencv.core.Core;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main_trab1 extends Application {
	@Override
	public void start(Stage primaryStage) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Principal_trab1.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
