/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawapp;

import java.io.InputStreamReader;
import java.io.Reader;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author AditTrivedi
 */
public class DrawApp extends Application {
    public static void main(String[] args) { 
        
    }
    @Override
        public void start(Stage primaryStage) {
        MainWindow main = new MainWindow(primaryStage);
        ImagePanel imagePanel = main.getImagePanel();
        Reader reader = new InputStreamReader(System.in);
        Parser parser = new Parser(reader,imagePanel,main);
        parser.parse();
        primaryStage.show();
    }
}