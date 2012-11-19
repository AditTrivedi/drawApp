/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawapp;
/**
 *
 * @author AditTrivedi
 */
import javafx.scene.paint.Color;
import java.io.File;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

public class ImagePanel extends HBox{
    private Group graphic=new Group();
    private HBox iView;
    private Paint colour;

    public ImagePanel(int width, int height){
        setImageSize(width, height);
    }

    private void setImageSize(int width, int height){ 
        this.setMaxSize(width, height);
        this.getChildren().add(graphic);
        clear(Color.BLACK);
    }
    
    public void setBackgroundColour(Color colour){
        this.setStyle("-fx-fill:"+colour.toString()+";");
    }
    
    public void setGradientColour(Color start,Color end){
        Stop[] stops = new Stop[] { new Stop(0, start), new Stop(1, end)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        colour = lg1;
    }
    
    public void clear(Color colour){
        setBackgroundColour(colour);
    }
    
    public void setColour(Color colourName){
        this.colour = colourName;
    }
    
    public void drawLine(int x1, int y1, int x2, int y2){
        Line line = new Line(x1,y1,x2,y2);
        line.setStroke(colour);
        graphic.getChildren().add(line);
        colour = Color.BLACK;
    }
    
    public void drawRect(int x1, int y1, int x2, int y2){
        Rectangle rect = new Rectangle(x1,y1,x2,y2);
        rect.setStroke(colour);
        rect.setFill(colour);
        graphic.getChildren().add(rect);
        colour = Color.BLACK;
    }
    
    public void fillRect(int x1, int y1, double x2, double y2){
        Rectangle rectFill = new Rectangle(x1,y1,x2,y2);
        rectFill.setFill(colour);
        graphic.getChildren().add(rectFill);
        colour = Color.BLACK;
    }
    
    public void drawString(int x, int y, String s){
        Text t = new Text(x,y,s);
        graphic.getChildren().add(t);
    }
    
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle){
        Arc arc = new Arc(x,y,width,height,startAngle,arcAngle);
        arc.setStroke(colour);
        arc.setFill(colour);
        graphic.getChildren().add(arc);
        colour = Color.BLACK;
    }
    
    public void drawOval(int x, int y, int width, int height){
        Ellipse oval = new Ellipse(x,y,width,height);
        oval.setStroke(colour);
        oval.setFill(colour);
        graphic.getChildren().add(oval);
        colour = Color.BLACK;
    }
    
    public void drawImage(int x, int y, int width, int height, String path) {
        File file = new File(path);
        Image i = new Image(file.toURI().toString());
        ImageView image = new ImageView(i);
        image.setTranslateX(x);
        image.setTranslateY(y);
        image.setFitWidth(width);
        image.setFitHeight(height);
	graphic.getChildren().add(image);
    }
}