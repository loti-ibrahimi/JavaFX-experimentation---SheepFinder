/*
 * Date: 29/04/2018
 * Author: Loti Ibrahimi (W20015453)
 * Module: Data Structure & Algorithms 2
 * Course: Internet of Things (Year 2)
 * 
 * 												Application: Aerial Sheep Recognition
 * 											--------------------------------------------
 * Description:
 * ===========
 * This is a small app that basically takes in an image and converts it to greyscale, only then to read its pixels and group up the white in clusters = sheep. Then giving the following:
 * - Count the number of sheep
 * - Locate and create a box around larger groups.   
 * 
 * - Union find algorithm is to be used for finding the disjoint pixel sets.
 * 
 * Notes:
 * ===========
 * - Had difficulties implementing pixel reader/union find... so did not get far. (Attempted code which did not work has been removed to keep the working code preview clean) 
 * - Below I've simply just worked on some JavaFx to upgrade from previous console usage.
 * - The app currently allows users to upload an image of their choice and simply convert it to greyscale in a suitable friendly UI.
 * 
 */

package application;
	
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class Main extends Application { 
	

	//-------- Image View --------------//
	@FXML
	ImageView sheep_image;
	@FXML
    private ImageView imageView;
	@FXML
    private ImageView imageViewCopy;
    
	//-------- Buttons ----------------//
    @FXML
    private Button btn_open_image;
    @FXML
    private Button btn_convert;

    
    
    //--------------------------- Main Stage Setup ------------------------------------------------//
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));

			primaryStage.setTitle("Aerial Sheep Recognition App");
			primaryStage.setMinWidth(700);
			primaryStage.setMinHeight(600);
			primaryStage.setMaxWidth(700);
			primaryStage.setMaxHeight(600);
			
			Scene scene = new Scene(root,700,600);
			
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
	private BufferedImage bufferedImage;
	   
	private Image image;
    
	 //== Viewer to manage opening images ==//
        public void openviewer(ActionEvent t) {
            FileChooser fileChooser = new FileChooser();

            //== Extension filters being set for image files ==//
            FileChooser.ExtensionFilter extFilterJPG =
                    new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG =
                    new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
            FileChooser.ExtensionFilter extFilterGIF =
                    new FileChooser.ExtensionFilter("GIF files (*.GIF)", "*.GIF");
            FileChooser.ExtensionFilter extFilterJPEG =
                    new FileChooser.ExtensionFilter("JPEG files (*.JPEG)", "*.JPEG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterGIF, extFilterJPEG);

            //== Shows the 'Open File' dialog box ==//
            File file = fileChooser.showOpenDialog(null);

            try {
                bufferedImage = ImageIO.read(file);
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
                
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    
    //== Converter to turn the loaded image into Grayscale image ==//
        public void converter(ActionEvent t) {
            for (int i = 0; i < bufferedImage.getWidth(); i++) {
                for (int j = 0; j < bufferedImage.getHeight(); j++) {
                    int p = bufferedImage.getRGB(i, j);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    int gray = (r + g + b) / 3 ;
                    int argb = (a<<24) | (gray<<16) | (gray<<8) | gray;
                        bufferedImage.setRGB(i, j, argb);         
                }
            }
            
            imageViewCopy.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
    };
