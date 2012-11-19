/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawapp;
/**
 *
 * @author AditTrivedi
 */
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  private Parser parser;
  private Stage stage;

  private ImagePanel imageRegion = new ImagePanel(DEFAULT_WIDTH,DEFAULT_HEIGHT); ;
  private TextArea textarea=new TextArea();
  private HBox pictureRegion2 = new HBox();
  private Button closeButton=new Button("Close Window");
  private Button nextButton=new Button("Next");
  private Button completeButton=new Button("Complete");
  private Button saveButton = new Button("Save");
  
  public MainWindow(Stage stage, Reader reader){
    this(stage,DEFAULT_WIDTH, DEFAULT_HEIGHT,reader);
    this.stage=stage;
    parser = new Parser(reader,imageRegion,this,stage);
  }

  public MainWindow (Stage primaryStage, int width, int height,Reader reader) {
    primaryStage.setTitle("DrawApp"); 
    Group root = new Group(); 
    Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.WHITE);
    GridPane gridpane = buildGUI();
    root.getChildren().add(gridpane);         
    primaryStage.setScene(scene); 
  }
  
  private GridPane buildGUI(){
    GridPane gridpane = new GridPane(); 
    gridpane.setHgap(0); 
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
    textarea.setText("Please click one the buttons");
        // Border decorate the picture 
    imageRegion.setStyle(cssDefault); 
    imageRegion.setPrefHeight(400);
    gridpane.add(imageRegion, 0, 0);
        
    pictureRegion2.setPrefHeight(50);
    pictureRegion2.setAlignment(Pos.CENTER);
    pictureRegion2.setStyle("-fx-background-color: #E8E8E8");
    
    closeButton.setOnAction(new EventHandler<ActionEvent>() { 
        @Override
        public void handle(ActionEvent event) {
            Platform.exit();
        }
    });  

    completeButton.setOnAction(new EventHandler<javafx.event.ActionEvent>()
      {
        @Override
        public void handle(javafx.event.ActionEvent event)
        {
          parser.parse();
        }
      });
    
    nextButton.setOnAction(new EventHandler<javafx.event.ActionEvent>()
      {
        @Override
        public void handle(javafx.event.ActionEvent event)
        {
              try {
                  parser.parseStep(nextButton);
              } catch (IOException ex) {
                  Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
      });
    
    
    saveButton.setOnAction(new EventHandler<javafx.event.ActionEvent>()
      {
        @Override
        public void handle(javafx.event.ActionEvent event)
        {
              try {
                  parser.saveImage("Image");
              } catch (ParseException ex) {
                  Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
      });
 
    pictureRegion2.getChildren().add(completeButton);
    pictureRegion2.getChildren().add(nextButton);
    pictureRegion2.getChildren().add(closeButton);
    pictureRegion2.getChildren().add(saveButton);

    gridpane.add(pictureRegion2, 0, 2);
    return gridpane;
  }

  public Button getNext(){
      return nextButton;
  }
  
  public Button getComplete(){
      return completeButton;
  }
  
  public ImagePanel getImagePanel(){
    return imageRegion;
  }

  public void postMessage(final String s){
     textarea.setText(s);
  }
  
  public void setSizeTextarea(int width,int height){
      textarea.setPrefHeight(height);
      textarea.setPrefWidth(width);
  }
  
  public void setSizeImageregion(int width,int height){
      imageRegion.setPrefHeight(height);
      imageRegion.setPrefWidth(width);
  }
  
  public void setSizePictureregion(int width,int height){
      pictureRegion2.setPrefHeight(height);
      pictureRegion2.setPrefWidth(width);
  }
          
}