package extrator_caracteristicas;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ExtractCaracteristicasTrab1 {

	
	
	public static double[] extraiCaracteristicas(File f) {
		
		double[] caracteristicas = new double[7];
		
		double roxoSapatoAgnes = 0;
		double azulCasacoAgnes = 0;
		double cinzaCabeloAgnes = 0;
		
		double marronCabeloBarney = 0;
		double marromCamisaBarney = 0;
		double azulCalcaBarney = 0; 
		
		
		Image img = new Image(f.toURI().toString());
		PixelReader pr = img.getPixelReader();
		
		Mat imagemOriginal = Imgcodecs.imread(f.getPath());
        Mat imagemProcessada = imagemOriginal.clone();
		
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		
		
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				
				Color cor = pr.getColor(j,i);
				
				double r = cor.getRed()*255; 
				double g = cor.getGreen()*255;
				double b = cor.getBlue()*255;
				
				if (i > (h / 3) && isRoxoSapatoAgnes(r, g, b)) {
					roxoSapatoAgnes++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if (i > (h / 3) && isAzulCasacoAgnes(r, g, b)) {
					azulCasacoAgnes++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if (i < (h / 3) && isCinzaCabeloAgnes(r, g, b)) {
					cinzaCabeloAgnes++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if( i < (h / 3) && isMarronCabeloBarney(r, g, b)) {
					marronCabeloBarney++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				if (isMarromCamisaBarney(r, g, b)) {
					marromCamisaBarney++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				if (i > (h / 2) && isAzulCalcaBarney(r, g, b)) {
					azulCalcaBarney++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				
			}
		}
		
		// Normaliza as caracter�sticas pelo n�mero de pixels totais da imagem para %
        roxoSapatoAgnes = (roxoSapatoAgnes / (w * h)) * 100;
        azulCasacoAgnes = (azulCasacoAgnes / (w * h)) * 100;
        cinzaCabeloAgnes = (cinzaCabeloAgnes / (w * h)) * 100;
        marronCabeloBarney = (marronCabeloBarney / (w * h)) * 100;
        marromCamisaBarney = (marromCamisaBarney / (w * h)) * 100;
        azulCalcaBarney = (azulCalcaBarney / (w * h)) * 100;
        
        caracteristicas[0] = roxoSapatoAgnes;
        caracteristicas[1] = azulCasacoAgnes;
        caracteristicas[2] = cinzaCabeloAgnes;
        caracteristicas[3] = marronCabeloBarney;
        caracteristicas[4] = marromCamisaBarney;
        caracteristicas[5] = azulCalcaBarney;
        //APRENDIZADO SUPERVISIONADO - J� SABE QUAL A CLASSE NAS IMAGENS DE TREINAMENTO
        caracteristicas[6] = f.getAbsolutePath().contains("agnes_skinner") ? 0 : 1;
		
		HighGui.imshow("Imagem original", imagemOriginal);
        HighGui.imshow("Imagem processada", imagemProcessada);
        
        HighGui.waitKey(1);
		
		return caracteristicas;
	}
	
	public static boolean isRoxoSapatoAgnes(double r, double g, double b) {
		 if (b >= 119 && b <= 180 &&  g >= 75 && g <= 140 &&  r >= 105 && r <= 170) {                       
         	return true;
         }
		 return false;
	}
	public static boolean isAzulCasacoAgnes(double r, double g, double b) {
		if (b >= 69 && b <= 158 &&  g >= 50 && g <= 116 &&  r >= 41 && r <= 95) {                       
			return true;
		}
		return false;
	}
	public static boolean isCinzaCabeloAgnes(double r, double g, double b) {
		if (b >= 189 && b <= 222 &&  g >= 146 && g <= 201 &&  r >= 130 && r <= 192) {                       
			return true;
		}
		return false;
	}
	
	public static boolean isMarronCabeloBarney(double r, double g, double b) {
		if (b >= 27 && b <= 35 &&  g >= 79 && g <= 118 &&  r >= 103 && r <= 150) {                       
			return true;
		}
		return false;
	}
	public static boolean isMarromCamisaBarney(double r, double g, double b) {
		if (b >= 62 && b <= 109 &&  g >= 105 && g <= 151 &&  r >= 153 && r <= 197) {                       
			return true;
		}
		return false;
	}
	public static boolean isAzulCalcaBarney(double r, double g, double b) {
		if (b >= 105 && b <= 186 &&  g >= 55 && g <= 96 &&  r >= 20 && r <= 41) {                       
			return true;
		}
		return false;
	}



	public static void extrair() {
				
	    // Cabe�alho do arquivo Weka
		String exportacao = "@relation caracteristicas\n\n";
		exportacao += "@attribute roxoSapatoAgnes real\n";
		exportacao += "@attribute azulCasacoAgnes real\n";
		exportacao += "@attribute cinzaCabeloAgnes real\n";
		exportacao += "@attribute marronCabeloBarney real\n";
		exportacao += "@attribute marromCamisaBarney real\n";
		exportacao += "@attribute azulCalcaBarney real\n";
		exportacao += "@attribute classe {Agnes, Barney}\n\n";
		exportacao += "@data\n";
	        
	    // Diret�rio onde est�o armazenadas as imagens
		List<File> finalList = new ArrayList<File>();
	    List<File> agnesList = Arrays.asList(new File("src\\img_trab1\\agnes_skinner").listFiles());
	    List<File> barneyList = Arrays.asList(new File("src\\img_trab1\\barney_gumble").listFiles());
	    for (File file : agnesList) {
	    	finalList.add(file);
	    }
	    for (File file : barneyList) {
			finalList.add(file);
		}
	    
        // Defini��o do vetor de caracter�sticas
        double[][] caracteristicas = new double[148][7];
        
        // Percorre todas as imagens do diret�rio
        int cont = -1;
        for (File imagem : finalList) {
        	cont++;
        	caracteristicas[cont] = extraiCaracteristicas(imagem);
        	
        	String classe = caracteristicas[cont][6] == 0 ?"Agnes":"Barney";
        	
        	System.out.println("Roxo Sapato Agnes: " + caracteristicas[cont][0] 
            		+ " - Azul Casaco Agnes: " + caracteristicas[cont][1] 
            		+ " - Cinza Cabelo Agnes: " + caracteristicas[cont][2] 
            		+ " - Marron Cabelo Barney: " + caracteristicas[cont][3] 
            		+ " - Marrom Camisa Barney: " + caracteristicas[cont][4] 
            		+ " - Azul Calca Barney: " + caracteristicas[cont][5] 
            		+ " - Classe: " + classe);
        	
        	exportacao += caracteristicas[cont][0] + "," 
                    + caracteristicas[cont][1] + "," 
        		    + caracteristicas[cont][2] + "," 
                    + caracteristicas[cont][3] + "," 
        		    + caracteristicas[cont][4] + "," 
                    + caracteristicas[cont][5] + "," 
                    + classe + "\n";
        }
        
     // Grava o arquivo ARFF no disco
        try {
        	File arquivo = new File("caracteristicas_trab1.arff");
        	FileOutputStream f = new FileOutputStream(arquivo);
        	f.write(exportacao.getBytes());
        	f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
