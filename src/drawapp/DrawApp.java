/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawapp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        public void start(Stage primaryStage) throws IOException {
        Reader reader = new InputStreamReader(System.in);
        MainWindow main = new MainWindow(primaryStage,reader);
        ImagePanel imagePanel = main.getImagePanel();
        Parser parser = new Parser(reader,imagePanel,main,primaryStage);
        primaryStage.show();
    }
}