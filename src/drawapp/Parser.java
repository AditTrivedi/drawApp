package drawapp;

import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Parser
{
  private BufferedReader reader;
  private ImagePanel image;
  private MainWindow frame;
  private Stage primaryStage;
  private int i =0;
  private Color colour;

  public Parser(Reader reader, ImagePanel image, MainWindow frame, Stage primaryStage)
  {
    this.reader = new BufferedReader(reader);
    this.image = image;
    this.frame = frame;
    this.primaryStage=primaryStage;
  }

  public void parse()
  {
    try
    {
      String line = reader.readLine();
      while (line != null)
      {
        parseLine(line);
        line = reader.readLine();
      }
    }
    catch (IOException e)
    {
      frame.postMessage("Parse failed.");
      return;
    }
    catch (ParseException e)
    {
      frame.postMessage("Parse Exception: " + e.getMessage());
      return;
    }
    frame.postMessage("Drawing completed");
  }
  
  public Color getColour(String colourName) throws ParseException
  {
    if (colourName.equals("black")) { return Color.BLACK; }
    if (colourName.equals("blue")) { return Color.BLUE;}
    if (colourName.equals("cyan")) { return Color.CYAN;}
    if (colourName.equals("darkgray")) { return Color.DARKGRAY;}
    if (colourName.equals("gray")) { return Color.GRAY;}
    if (colourName.equals("green")) { return Color.GREEN;}
    if (colourName.equals("lightgray")) { return Color.LIGHTGRAY;}
    if (colourName.equals("magenta")) { return Color.MAGENTA;}
    if (colourName.equals("orange")) { return Color.ORANGE;}
    if (colourName.equals("pink")) { return Color.PINK;}
    if (colourName.equals("red")) { return Color.RED;}
    if (colourName.equals("white")) { return Color.WHITE;}
    if (colourName.equals("yellow")) { return Color.YELLOW;}
    if (colourName.equals("transparent")) { return Color.TRANSPARENT;}
    throw new ParseException("Invalid colour name: " + colourName);
  }

    public void setColour(String colourName) throws ParseException {
        image.setColour(getColour(colourName));
    }
    
    private void setGradient(String args){
        StringTokenizer tokenizer = new StringTokenizer(args);
        try {
            Color start = getColour(tokenizer.nextToken());
            Color end = getColour(tokenizer.nextToken());
            image.setGradientColour(start,end);
        } catch (ParseException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  private void parseLine(String line) throws ParseException
  {
    if (line.length() < 2) return;
    String command = line.substring(0, 2);
    if (command.equals("DL")) { drawLine(line.substring(2,line.length())); return; }
    if (command.equals("DR")) { drawRect(line.substring(2, line.length())); return; }
    if (command.equals("FR")) { fillRect(line.substring(2, line.length())); return; }
    if (command.equals("SC")) { setColour(line.substring(3, line.length())); return; }
    if (command.equals("DS")) { drawString(line.substring(3, line.length())); return; }
    if (command.equals("DA")) { drawArc(line.substring(2, line.length())); return; }
    if (command.equals("DO")) { drawOval(line.substring(2, line.length())); return; }
    if (command.equals("DI")) { drawImage(line.substring(3, line.length())); return; }
    if (command.equals("SI")) { saveImage(line.substring(2, line.length()));return;}
    if (command.equals("SG")) { setGradient(line.substring(3, line.length()));return;}
    if (command.equals("CD")) { changeDimensions(line.substring(2, line.length()));return;}
    if (command.equals("SE")) { shadowEffect(line.substring(3, line.length()));return;}
    if (command.equals("IS")) { innerShadow(line.substring(3, line.length()));return;}
    throw new ParseException("Unknown drawing command");
  }
  
  private void changeDimensions (String args) throws ParseException 
  {
      int height =-1;
      int width =-1;
      StringTokenizer tokenizer = new StringTokenizer(args);
      height = getInteger(tokenizer);
      width = getInteger(tokenizer);
      
      if(width<0 || height<0) throw new ParseException("Invalid Integer value");
      
      primaryStage.setHeight(height);
      primaryStage.setWidth(width);
      
      frame.setSizeImageregion(width, height-200);
      frame.setSizeTextarea(width,150);
      frame.setSizePictureregion(width,50);
  }
  
  private String getString(StringTokenizer tokenizer) throws ParseException
  {
    if (tokenizer.hasMoreTokens())
        return tokenizer.nextToken();
    else
        throw new ParseException("Missing String value");
}
  private void shadowEffect(String args) throws ParseException{
      int x=-1;
      int y =-1;
      int size = -1;
      String s ="";
      StringTokenizer tokenizer = new StringTokenizer(args);
      x = getInteger(tokenizer);
      y = getInteger(tokenizer);
      size = getInteger(tokenizer);
      s= getString(tokenizer);
      int position = args.indexOf("@");
      if (position == -1) throw new ParseException("Missing string in shadowEffect");
        s = args.substring(position+1,args.length());
      
      Text shadow = new Text(x,y,s);
      shadow.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD,size));
      final DropShadow dropShadow = new DropShadow();
      shadow.setEffect(dropShadow);
      if(x<0 || y<0 || size<0) throw new ParseException("Invalid Integer value");
      image.getChildren().add(shadow);
  }
  
  private void innerShadow(String args) throws ParseException{
      int x=-1;
      int y =-1;
      int size = -1;
      String s ="";
      
      StringTokenizer tokenizer = new StringTokenizer(args);
      x = getInteger(tokenizer);
      y = getInteger(tokenizer);
      size = getInteger(tokenizer);
      s= getString(tokenizer);
      int position = args.indexOf("@");
      if (position == -1) throw new ParseException("Missing string in shadowEffect");
        s = args.substring(position+1,args.length());
        
      Text sample = new Text(x,y,s);
      sample.setFont(Font.font("Arial Black",size));
      sample.setFill(Color.RED);
      final InnerShadow innerShadow = new InnerShadow();
      innerShadow.setRadius(5d);
      innerShadow.setOffsetX(2);
      innerShadow.setOffsetY(2);
      innerShadow.setColor(Color.YELLOW);
      sample.setEffect(innerShadow);
      image.getChildren().add(sample);
  }

  private void drawLine(String args) throws ParseException
  {
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;
    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    x2 = getInteger(tokenizer);
    y2 = getInteger(tokenizer);
    if(x1<0 || y1<0 || x2<0 || y2<0)
        throw new ParseException("Invalid Integer value");
    image.drawLine(x1,y1,x2,y2);
  }

  private void drawRect(String args) throws ParseException
  {
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;
    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    x2 = getInteger(tokenizer);
    y2 = getInteger(tokenizer);
    if(x1<0 || y1<0 || x2<0 || y2<0)
        throw new ParseException("Invalid Integer value");
    image.drawRect(x1, y1, x2, y2);
  }

  private void fillRect(String args) throws ParseException
  {
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;
    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    x2 = getInteger(tokenizer);
    y2 = getInteger(tokenizer);
    if(x1<0 || y1<0 || x2<0 || y2<0)
        throw new ParseException("Invalid Integer value");
    image.fillRect(x1, y1, x2, y2);
  }

  private void drawArc(String args) throws ParseException
  {
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    int startAngle = 0;
    int arcAngle = 0;
    StringTokenizer tokenizer = new StringTokenizer(args);
    x = getInteger(tokenizer);
    y = getInteger(tokenizer);
    width = getInteger(tokenizer);
    height = getInteger(tokenizer);
    startAngle = getInteger(tokenizer);
    arcAngle = getInteger(tokenizer);
    if(x<0 || y<0 || width<0 || height<0 || startAngle<0 || arcAngle<0)
        throw new ParseException("Invalid Integer value");
    image.drawArc(x, y, width, height, startAngle, arcAngle);
  }

  private void drawOval(String args) throws ParseException
  {
    int x1 = 0;
    int y1 = 0;
    int width = 0;
    int height = 0;
    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    width = getInteger(tokenizer);
    height = getInteger(tokenizer);
    if(x1<0 || y1<0 || width<0 || height<0)
        throw new ParseException("Invalid Integer value");
    image.drawOval(x1, y1, width, height);
  }

  private void drawString(String args) throws ParseException
  {
    int x = 0;
    int y = 0 ;
    String s = "";
    StringTokenizer tokenizer = new StringTokenizer(args);
    x = getInteger(tokenizer);
    y = getInteger(tokenizer);
    int position = args.indexOf("@");
    if (position == -1) throw new ParseException("DrawString string is missing");
    s = args.substring(position+1,args.length());
    if(x<0 || y<0)
        throw new ParseException("Invalid Integer value");
    image.drawString(x,y,s);
  }

  private void setColour(Color colourName) throws ParseException
  {
    colour = Color.BLACK;
    this.colour = colourName;
  }
  
  public void saveImage(String imName) throws ParseException {
        if (imName.isEmpty()) {
            throw new ParseException("No file name specified");
        }
        File file = new File(imName + ".png");
        WritableImage wImage = image.snapshot(null, null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), "png", file);
            frame.postMessage("Drawing has been saved");
        } catch (IOException e) {
            throw new ParseException("Save failed");
        }
    }
  
  private void drawImage(String args) throws ParseException
  {
	int x = -1;
	int y = -1;
	int width = -1;
	int height = -1;
	String path = "";
	StringTokenizer tokenizer = new StringTokenizer(args);
	x = getInteger(tokenizer);
	y = getInteger(tokenizer);
	width = getInteger(tokenizer);
	height = getInteger(tokenizer);
	int position = args.indexOf("@");
	if (position == -1) 
            throw new ParseException("Path is currently missing");
	path = args.substring(position+1,args.length());
        if(x<0||y<0||width<0||height<0)
            throw new ParseException("Invalid numbers for drawImage");
	image.drawImage(x, y, width, height, path);
    }   


  private int getInteger(StringTokenizer tokenizer) throws ParseException
  {
    if (tokenizer.hasMoreTokens())
      return Integer.parseInt(tokenizer.nextToken());
    else
      throw new ParseException("Missing Integer value");
  }
  
  public void completeStep(final Button b) throws IOException{
      String line = reader.readLine();
      line = reader.readLine();
      b.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent t) {
              parse();
          }
      });      
  }
  
    public void parseStep(final Button b) throws IOException 
    {
        String line = reader.readLine();
        final ArrayList<String> asl=new ArrayList<String>();
        while (line != null)
        { 
         asl.add(line);
         line = reader.readLine();
        }
        b.setOnAction(new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
                try 
                {
                    parseLine(asl.get(i));
                    i++;
                    frame.postMessage("Drawing getting completed");
                    if(i==asl.size())
                    {
                        frame.postMessage("Drawing is complete");
                        b.setDisable(true);
                    }
                } catch (ParseException ex) { 
                    Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                }        
            }
        });
    }

}
