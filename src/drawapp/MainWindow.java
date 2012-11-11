/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawapp;
/**
 *
 * @author AditTrivedi
 */
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainWindow
{
  public static final int DEFAULT_WIDTH = 600;
  public static final int DEFAULT_HEIGHT = 600;

  private int width;
  private int height;

  private ImagePanel imageRegion = new ImagePanel(DEFAULT_WIDTH,DEFAULT_HEIGHT); ;
  private TextArea textarea=new TextArea();
  private Button closeButton=new Button("Close Window");
  
  public MainWindow(Stage stage){
    this(stage,DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  public MainWindow (Stage primaryStage, int width, int height) {
    primaryStage.setTitle("DrawApp"); 
    Group root = new Group(); 
    Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.WHITE);
    GridPane gridpane = buildGUI();
    root.getChildren().add(gridpane);         
    primaryStage.setScene(scene); 
  }

  private GridPane buildGUI(){
    GridPane gridpane = new GridPane(); 
    gridpane.setHgap(10); 
    gridpane.setVgap(0);  
        // Text area for CSS editor  
    textarea.setWrapText(true); 
    textarea.setPrefWidth(600); 
    textarea.setPrefHeight(150);
    GridPane.setHalignment(textarea, HPos.CENTER); 
    gridpane.add(textarea, 0, 1); 
    String cssDefault = "-fx-border-color: black;\n" 
                            + "-fx-border-insets: 5;\n" 
                            + "-fx-border-width: 0;\n";
    textarea.setText("Drawing Completed!!"); 
        // Border decorate the picture 
    imageRegion.setStyle(cssDefault); 
    imageRegion.setPrefHeight(400);
    gridpane.add(imageRegion, 0, 0);
        
    final HBox pictureRegion2 = new HBox(); 
    //pictureRegion2.setStyle(cssDefault); 
    pictureRegion2.setPrefHeight(50);
    pictureRegion2.setAlignment(Pos.CENTER);
    pictureRegion2.setStyle("-fx-background-color: #E8E8E8");
    
    closeButton.setOnAction(new EventHandler<ActionEvent>() { 
        @Override
        public void handle(ActionEvent event) {
            Platform.exit();
        }
    }); 
    pictureRegion2.getChildren().add(closeButton);
    gridpane.add(pictureRegion2, 0, 2);
    return gridpane;
  }

  public ImagePanel getImagePanel(){
    return imageRegion;
  }

  public void postMessage(final String s){
     textarea.setText(s);
  }
}