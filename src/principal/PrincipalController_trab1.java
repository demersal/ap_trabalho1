package principal;

import java.io.File;

import extrator_caracteristicas.ExtractCaracteristicasTrab1;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class PrincipalController_trab1 {
	
	@FXML
	private ImageView imageView;
	
	@FXML
	public TextArea text = new TextArea();
	
	@FXML
	public void extrairCaracteristicas() {
		ExtractCaracteristicasTrab1.extrair();
	}
	
	@FXML
	public void selecionaImagem() {
		File f = buscaImg();
		if(f != null) {
			Image img = new Image(f.toURI().toString());
			imageView.setImage(img);
			imageView.setFitWidth(img.getWidth());
			imageView.setFitHeight(img.getHeight());
			double[] caracteristicas = ExtractCaracteristicasTrab1.extraiCaracteristicas(f);
			for (double d : caracteristicas) {
				System.out.println(d);
			}
			NaiveBayesApp nb = new NaiveBayesApp();
			double[] naiveBayes = nb.naiveBayes(caracteristicas, "caracteristicas_trab1.arff");
			this.text.setText("Agnes: " + Math.round(naiveBayes[0] * 100) + "%\r\n" + "Barney: " + Math.round(naiveBayes[1] * 100) + "%");
			for (double d : naiveBayes) {
				System.out.println(d);
			}
		}
	}
	
	@FXML
	public void naiveBayes() {
		
	}
	
	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("src\\img_trab1"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
}
