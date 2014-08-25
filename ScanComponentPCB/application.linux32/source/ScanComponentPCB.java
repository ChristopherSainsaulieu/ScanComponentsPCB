import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.bezier.data.sql.*; 
import de.bezier.data.sql.mapper.*; 
import java.io.*; 
import gab.opencv.*; 
import processing.video.*; 
import java.awt.*; 
import java.awt.event.*; 
import controlP5.*; 
import javax.swing.JFrame; 
import java.awt.event.WindowAdapter; 
import javax.swing.*; 
import processing.serial.*; 

import org.firmata.*; 
import cc.arduino.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ScanComponentPCB extends PApplet {

/*********************************************************
Program of bench of verification  missing components on  electronic cards SmoothieBoard
Author : Christopher Sainsaulieu
Date : August 2014
RobotSeed
************************************************************/



//use of the library opencv/controlP5/Java













//import cc.arduino.*;


// Declaration of frames and different corresponding functions

static PFrame configurePositionWebcam = null;
static ConfigurePositionWebcam s;

static PFrame2 TakeACalibrationPicture = null;
static TakeACalibrationPicture s1;

static PFrame3 CheckYourCard = null;
static CheckYourCard s2;

static PFrame4 FindYourMissingComponent = null;
static FindYourMissingComponent s3;

static PFrame5 FindComponentReference = null;
static FindComponentReference s4;

static PFrame6 FindYourMissingComponent2 = null;
static FindYourMissingComponent2 s5;

static PFrame7 GUI = null;  // adjust the webcam
static GUI s6;

static PFrame8 GUI2 = null;  // find your missing components
static GUI2 s7;

static PFrame9 GUI3 = null;  // take a calibration picture
static GUI3 s8;

static PFrame10 GUI4 = null;  // check your card
static GUI4 s9;

// declaration of objects

//static Arduino arduino;

static Capture cam,cam2;  // representation of the cam
static OpenCV opencv,opencv2,opencvRslt;  // object openCV
static ControlP5 cp5; // object IHM
//String filename; // folder
static int compt=0;  // Variable increment the filename
static int comptrslt=0; // Variable increment the filename of the picture result
static PImage imgSrc,src; // Variable registration for image file
static PImage photoInit, CheckYourCardp, findYourMissingComponent,imgfinal;
PImage logo;
static String nomComposant="No missing components";
double x,y;
static String lienURL="https://www.google.fr/";
static boolean LinkButton = false;
static PVector locPixel;
PApplet _this ;
static File file;
DropdownList d1,d2;
static String nameCam;
static String Width;
static String Height;
static int requestWidth;
static int requestHeight;
static String TypeCard="noCard";
static String noCard="noCard";

static float xComponent;
static float yComponent;

static float xPixelsCard;
static float xMMCard=129;

static float xComponentMM; // (xComponent*xMMCard)/xPixelsCard; ->BDD
static float yComponentMM; // (yComponent*xMMCard)/xPixelsCard; -> BDD

static boolean LinkButton1 = false;
static boolean LinkButton2 = false;
static boolean j=false;
static boolean k=false;
static float xref,xref2;
static float yref,yref2;
static  boolean save=false;
static  boolean save2=false;

static int returnVal;
static SQLite db;

static float locx;
static float locy;



// colors

int jaune=color(211,195,0);
int vert=color(103,215,52);
int rouge=color(246,51,51);
int bleu=color(54,72,113);
int noir=color(0,0,0);
int blanc=color(248,248,248);
int bleuclair=color(0,255,255); 
int violet=color(255,0,255); 
int orange=color(245,142,0);
int gris=color(68,68,68);

controlP5.Button a,b,c,d,e;


//Frame for a configuration the position of the webcam

  public static class PFrame extends JFrame {
    public PFrame() {
      if(requestWidth==640 && requestHeight==480){
      setBounds(0, 0, 660, 520);
      }else if (requestWidth==1920 && requestHeight==1080){
       setBounds(0, 0, 1300, 700); 
      } else {
        setBounds(0, 0, 660, 520);
      }
       s = new ConfigurePositionWebcam();
      add(s);
      s.init();
      //println("Configure the position of the webcam");
    }
  }

//Frame for take a calibration picture

  public static class PFrame2 extends JFrame {
     public  PFrame2() {
     
      if(requestWidth==640 && requestHeight==480){
      setBounds(0, 0, 660, 520);
      }else if (requestWidth==1920 && requestHeight==1080){
       setBounds(0, 0, 1300, 700); 
      } else {
        setBounds(0, 0, 660, 520);
      }
     //size(640/2, 480/2); 
      s1= new TakeACalibrationPicture();
      add(s1);
      s1.init();
      //println("Take a calibration picture");
    }
  }

//Frame for Check your card

  public static class PFrame3 extends JFrame {
     public PFrame3() {
      
       if(requestWidth==640 && requestHeight==480){
      setBounds(0, 0, 660, 520);
      }else if (requestWidth==1920 && requestHeight==1080){
       setBounds(0, 0, 1300, 700); 
      } else {
        setBounds(0, 0, 660, 520);
      }
      s2= new CheckYourCard();
      add(s2);
      s2.init();
     // println("Check your card");
    }
  }

//Frame for find your missing component

  public  class PFrame4 extends JFrame {
     public PFrame4() {
       //size of window stable
       setBounds(0, 0, 350, 200);
      s3= new FindYourMissingComponent();
      add(s3);
      s3.init();
     // println("Find your missing component");
                
    }
  }


//Frame for DetectMax

  public static  class PFrame5 extends JFrame {
     public PFrame5() {
      //size of window stable
      setBounds(0, 0, 980, 495);
      s4= new FindComponentReference();
      add(s4);
      s4.init();
     // println("FindComponentReference");
    }
  }

// Frame for the window selection file
public static  class PFrame6 extends JFrame {
    public PFrame6() {
      //Frame for the window selection file
      setBounds(0, 0, 980, 580);
       s5 = new FindYourMissingComponent2();
      add(s5);
      s5.init();
     // println("Frame for the window selection file");
    }
  }
  
public static class PFrame7 extends JFrame {
    public PFrame7() {

      setBounds(0, 0, 500, 300);
       s6 = new GUI();
      add(s6);
      s6.init();
      //println("GUI");
    }
  }  
  
  public static class PFrame8 extends JFrame {
    public PFrame8() {

      setBounds(0, 0, 500, 300);
       s7 = new GUI2();
      add(s7);
      s7.init();
     // println("GUI2");
    }
  }  
   public static class PFrame9 extends JFrame {
    public PFrame9() {

      setBounds(0, 0, 500, 300);
       s8 = new GUI3();
      add(s8);
      s8.init();
     // println("GUI3");
    }
  }
    public static class PFrame10 extends JFrame {
    public PFrame10() {

      setBounds(0, 0, 500, 300);
       s9 = new GUI4();
      add(s9);
      s9.init();
     // println("GUI4");
    }
  }

String[] cameras = Capture.list();

//setup for the main window

 public void setup(){
     PFont pfont = createFont("Arial",10,true);
     ControlFont font = new ControlFont(pfont,10);
     

   
   size(1300,600); // window size
   cp5 = new ControlP5(this); // GUI object
   cp5.setControlFont(font);
   // create a new button with name 'adjust the webcam'
     d1 = cp5.addDropdownList("Choice of Webcam")
          .setPosition(0, 50)
          ;
          
  customize(d1); // customize the first list
    a = cp5.addButton("Adjust the webcam")
       .setValue(0)
       .setPosition(0,150)
       .setSize(310,35)
       .setColorBackground(gris)
    
      .addCallback(
        new CallbackListener() {
    
            public void controlEvent(CallbackEvent theEvent) {
      
                switch(theEvent.getAction()) {
                
                  case(ControlP5.ACTION_PRESSED):
                 // println("Adjust the webcam is pressed");
        
                         
                       configurePositionWebcam = new PFrame();
                       configurePositionWebcam.setVisible(true);
                       configurePositionWebcam.setTitle("Adjust the webcam");
                       //configurePositionWebcam.setLocationRelativeTo(null);
                       configurePositionWebcam.setLocation(100,100);
                       //configurePositionWebcam.setDefaultCloseOperation(PFrame.DISPOSE_ON_CLOSE);
                         
                       
                break;
        
                case(ControlP5.ACTION_RELEASED):
                case(ControlP5.ACTION_RELEASEDOUTSIDE):
               // println("send MIDI on release, note off.");
                break;
                
            }  
            
        } // end of controlEvent
      } // end of CallBackListener
     ); // end of addCallback
    ;  // end of addButton ' adjustTheWebcam'
    
  // create a new button with name 'take a calibration picture'
  b = cp5.addButton("Take a calibration picture")
     .setValue(100)
     .setPosition(0,230)
     .setSize(310,35)
     .setColorBackground(gris)
     .addCallback(
    new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      switch(theEvent.getAction()) {
        case(ControlP5.ACTION_PRESSED):
       // println("Take a calibration picture is pressed");
  
                         TakeACalibrationPicture = new PFrame2();
                         TakeACalibrationPicture.setTitle("Take a calibration picture");
                         TakeACalibrationPicture.setLocation(100,100);
                         //TakeACalibrationPicture.setDefaultCloseOperation(PFrame2.DISPOSE_ON_CLOSE);
                         TakeACalibrationPicture.setVisible(true);
                
                       
        break;
        
        case(ControlP5.ACTION_RELEASED):
        case(ControlP5.ACTION_RELEASEDOUTSIDE):
       // println("send MIDI on release, note off.");
        break;
      }
    } // end of controlEvent
  } // end of CallBackListener
    ); // end of addCallback
     ;
     
// create a new button with name 'take a picture of your card'

  c = cp5.addButton("Take a picture of your card")
     .setValue(100)
     .setPosition(0,310)
     .setSize(310,35)
     .setColorBackground(gris)
     .addCallback(
    new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      switch(theEvent.getAction()) {
        case(ControlP5.ACTION_PRESSED):
      //  println("Take a calibration picture is pressed");
     
                         CheckYourCard = new PFrame3();
                         CheckYourCard.setTitle("Take a picture of your card");
                         CheckYourCard.setLocation(100,100);
                         //CheckYourCard.setDefaultCloseOperation(PFrame3.DISPOSE_ON_CLOSE);
                         CheckYourCard.setVisible(true);
                         
                
        break;
        
        case(ControlP5.ACTION_RELEASED):
        case(ControlP5.ACTION_RELEASEDOUTSIDE):
       // println("send MIDI on release, note off.");
        break;
      }
    } // end of controlEvent
  } // end of CallBackListener
    ); // end of addCallback
     ;
     
     
  // create a new button with name 'check your electronic card'  
  
  d = cp5.addButton("Check your electronic card")
     .setPosition(0,390)
     .setSize(310,35)
     .setValue(0)
     .setColorBackground(gris)
     .addCallback(
    new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      switch(theEvent.getAction()) {
        case(ControlP5.ACTION_PRESSED):
       // println("Take a calibration picture is pressed");
    
                                 if(TypeCard.compareTo(noCard)==0){       
      
                         FindYourMissingComponent = new PFrame4();
                         FindYourMissingComponent.setVisible(true);
                         FindYourMissingComponent.setTitle("Find your missing component");
                         FindYourMissingComponent.setLocationRelativeTo(null);
                         //FindYourMissingComponent.setDefaultCloseOperation(PFrame3.DISPOSE_ON_CLOSE);
                                 } else {
                                   
                                   FindYourMissingComponent2 = new PFrame6();
                                   FindYourMissingComponent2.setTitle("Function after loading file");
                                   FindYourMissingComponent2.setLocation(250,50);
                                   FindYourMissingComponent2.setVisible(true);
                                   
                                 }
                      
                        
                       
        break;
        
        case(ControlP5.ACTION_RELEASED):
        case(ControlP5.ACTION_RELEASEDOUTSIDE):
       // println("send MIDI on release, note off.");
        break;
      }
    } // end of controlEvent
  } // end of CallBackListener
    ); // end of addCallback
     ;
     
    
    cp5.addButton("exit")
       .setValue(0)
       .setPosition(1100,520)
       .setSize(300,35)
       .setColorBackground(gris)
    
      .addCallback(
        new CallbackListener() {
    
            public void controlEvent(CallbackEvent theEvent) {
      
                switch(theEvent.getAction()) {
                
                  case(ControlP5.ACTION_PRESSED):
                 // println("exit is pressed");
                     exit();       
                break;
        
                case(ControlP5.ACTION_RELEASED):
                case(ControlP5.ACTION_RELEASEDOUTSIDE):
               // println("send MIDI on release, note off.");
                break;
                
            }  
            
        } // end of controlEvent
      } // end of CallBackListener
     ); // end of addCallback
    ;  // end of addButton ' exit'
    
    
  
  cp5.getController("Adjust the webcam")
     .getCaptionLabel()
     .setFont(font)
     .toUpperCase(false)
     .setSize(20)
     ;
     a.captionLabel().getStyle().marginLeft = 4;
   

  cp5.getController("Take a calibration picture")
     .getCaptionLabel()
     .setFont(font)
     .toUpperCase(false)
     .setSize(20)
     ;
     b.captionLabel().getStyle().marginLeft = 4;
  
     
     cp5.getController("Take a picture of your card")
     .getCaptionLabel()
     .setFont(font)
     .toUpperCase(false)
     .setSize(20)
     ;
     c.captionLabel().getStyle().marginLeft = 4;

     
  cp5.getController("Check your electronic card")
     .getCaptionLabel()
     .setFont(font)
     .toUpperCase(false)
     .setSize(20)
     ;
     d.captionLabel().getStyle().marginLeft = 4;

     

     
  cp5.getController("exit")
     .getCaptionLabel()
     .setFont(font)
     .toUpperCase(false)
     .setSize(24)
     ;
     
     textSize(20);

 }    // end of MAIN SETUP
 

// select the type of webcam
public void controlEvent(ControlEvent theEvent) {
if(theEvent.isGroup()){
      
  
  for(int j=0; j<cameras.length; j++){
  if(theEvent.getGroup().getValue()==j){
      //println(cameras[j]);
      nameCam=cameras[j].substring(5,16);
      println(nameCam); 
      String testx = cameras[j].substring(25,26); //123x123
      //println("x vaut :"+testx);
      String testx2 = cameras[j].substring(26,27);//1234x123
      String testVirgule = cameras[j].substring(31,32); //1234x1234,
      String x="x";  
      String virgule=",";   
      
      if ( testx.compareTo(x)==0){
      Width = cameras[j].substring(22,25);
      Height = cameras[j].substring(26,29);
      //println(Width+" "+Height);
      } else if ( testx2.compareTo(x)==0 && testVirgule.compareTo(virgule)==0){
        Width = cameras[j].substring(22,26);
        Height = cameras[j].substring(27,31);
        //println(Width+" "+Height);
      }else if (testx2.compareTo(x)==0 ){
      Width = cameras[j].substring(22,26);
      Height = cameras[j].substring(27,30);
      //println(Width+" "+Height); 
      }
      requestWidth = Integer.parseInt(Width);
      requestHeight = Integer.parseInt(Height);
      println(requestWidth+" "+requestHeight);
      
      cam = new Capture(this,requestWidth,requestHeight,nameCam); // size of the webcam object //////////////////////////////////////////////////////////////////////////////////////////
      opencv = new OpenCV(this,requestWidth,requestHeight); // The size of the
      }
      
  }
}
}  // end of select the type of webcam

// Draw for the main frame

  public void draw() {
    scale(1); // scale webcam
    background(blanc);  // GUI background
    logo =loadImage("logo.jpg");
    image(logo,20,520);
    
    PImage CinqXC = loadImage("5XC Smoothieboard.png");
    CinqXC.resize(200,180);
    image(CinqXC,400,80);
    text("5XC Smoothieboard",400,80);
    fill(bleu);
    if(TypeCard.compareTo(noCard)==0 || TypeCard.compareTo("5X.jpg")==0 && TypeCard.compareTo("4XC.jpg")==0 && TypeCard.compareTo("4X.jpg")==0 && TypeCard.compareTo("3XC.jpg")==0 && TypeCard.compareTo("3X.jpg")==0){
    line(400,85,600,85);
    stroke(bleu);
    strokeWeight(4);
    }else if(TypeCard.compareTo("5XC.jpg")==0){
      line(400,85,600,85);
      stroke(vert);
      strokeWeight(4);    
    }
    
    
    PImage CinqX = loadImage("5x Smoothieboard.png");
    CinqX.resize(200,180);
    image(CinqX,700,80);
    text("5X Smoothieboard",700,80);
    fill(bleu);
    if(TypeCard.compareTo(noCard)==0 || TypeCard.compareTo("5XC.jpg")==0 && TypeCard.compareTo("4XC.jpg")==0 && TypeCard.compareTo("4X.jpg")==0 && TypeCard.compareTo("3XC.jpg")==0 && TypeCard.compareTo("3X.jpg")==0){
    line(700,85,900,85);
    stroke(blanc);
    strokeWeight(4);
    }else if(TypeCard.compareTo("5X.jpg")==0){
      line(700,85,900,85);
      stroke(vert);
      strokeWeight(4);    
    }
    
    PImage QuatreXC = loadImage("4XC Smoothieboard.png");
    QuatreXC.resize(200,180);
    image(QuatreXC,1000,80);
    text("4XC Smoothieboard",1000,80);
    fill(bleu);
    if(TypeCard.compareTo(noCard)==0 || TypeCard.compareTo("5XC.jpg")==0 && TypeCard.compareTo("5X.jpg")==0 && TypeCard.compareTo("4X.jpg")==0 && TypeCard.compareTo("3XC.jpg")==0 && TypeCard.compareTo("3X.jpg")==0){
    line(1000,85,1200,85);
    stroke(blanc);
    strokeWeight(4);
    }else if(TypeCard.compareTo("4XC.jpg")==0){
      line(1000,85,1200,85);
      stroke(vert);
      strokeWeight(4);    
    }
    
    PImage QuatreX = loadImage("4X Smoothieboard.png");
    QuatreX.resize(200,180);
    image(QuatreX,400,313);
    text("4X Smoothieboard",400,313);
    fill(bleu);
    if(TypeCard.compareTo(noCard)==0 || TypeCard.compareTo("5XC.jpg")==0 && TypeCard.compareTo("5X.jpg")==0 && TypeCard.compareTo("4XC.jpg")==0 && TypeCard.compareTo("3XC.jpg")==0 && TypeCard.compareTo("3X.jpg")==0){
    line(400,318,600,318);
    stroke(blanc);
    strokeWeight(4);
    }else if(TypeCard.compareTo("4X.jpg")==0){
      line(400,318,600,318);
      stroke(vert);
      strokeWeight(4);    
    }
    
    PImage TroisXC = loadImage("3XC Smoothieboard.png");
    TroisXC.resize(200,180);
    image(TroisXC,700,313);
    text("3XC Smoothieboard",700,313);
    fill(bleu);
    if(TypeCard.compareTo(noCard)==0 || TypeCard.compareTo("5XC.jpg")==0 && TypeCard.compareTo("5X.jpg")==0 && TypeCard.compareTo("4X.jpg")==0 && TypeCard.compareTo("4XC.jpg")==0 && TypeCard.compareTo("3X.jpg")==0){
    line(700,318,900,318);
    stroke(blanc);
    strokeWeight(4);
    }else if(TypeCard.compareTo("3XC.jpg")==0){
      line(700,318,900,318);
      stroke(vert);
      strokeWeight(4);    
    }
    
    PImage TroisX = loadImage("3X Smoothieboard.png");
    TroisX.resize(200,180);
    image(TroisX,1000,313);
    text("3X Smoothieboard",1000,313);
    fill(bleu);  
  if(TypeCard.compareTo(noCard)==0 || TypeCard.compareTo("5XC.jpg")==0 && TypeCard.compareTo("5X.jpg")==0 && TypeCard.compareTo("4X.jpg")==0 && TypeCard.compareTo("4XC.jpg")==0 && TypeCard.compareTo("3X.jpg")==0){
    line(1000,318,1200,318);
    stroke(blanc);
    strokeWeight(4);
    }else if(TypeCard.compareTo("3X.jpg")==0){
      line(1000,318,1200,318);
      stroke(vert);
      strokeWeight(4);    
    }  
  }  // end of MAIN DRAW
  
  
///////////////////////////////Definition of functions/////////////////////////////////////////////////////////////////////  
public void mousePressed()
{
  if (mouseX > 400 && mouseX < 600 && mouseY > 80 && mouseY < 260) {
    println("5XC is pressed");
    TypeCard="5XC.jpg"; 
    
  }
  else if (mouseX > 700 && mouseX < 900 && mouseY > 80 && mouseY < 260) {
    println("5X is pressed");
    TypeCard="5X.jpg"; 
  }
  else if (mouseX > 1000 && mouseX < 1200 && mouseY > 80 && mouseY < 260) {
    println("4XC is pressed");
    TypeCard="4XC.jpg"; 
  }
  else if (mouseX > 400 && mouseX < 600 && mouseY > 313 && mouseY < 493) {
    println("4X is pressed");
    TypeCard="4X.jpg"; 
  }
   else if (mouseX > 700 && mouseX < 900 && mouseY > 313 && mouseY < 493) {
    println("3XC is pressed");
    TypeCard="3XC.jpg"; 
  }
   else if (mouseX > 1000 && mouseX < 1200 && mouseY > 313 && mouseY < 493) {
    println("3X is pressed");
    TypeCard="3X.jpg"; 
  }
}

public void customize(DropdownList ddl) {
  // a convenience function to customize a DropdownList
  ddl.setWidth(310);
  //ddl.setFont(font);
  ddl.setBackgroundColor(gris);
  //ddl.setItemHeight(20);
  ddl.setBarHeight(24);
  ddl.captionLabel().set("Select your webcam");
  ddl.captionLabel().style().marginTop = 5;
  ddl.captionLabel().style().marginLeft = 20;
  ddl.valueLabel().style().marginTop = 5;

  
  if (cameras.length == 0) {
    println("There are no cameras available for capture.");
    exit();
  } else {
    
    for ( int i=0; i<cameras.length;i++){
      ddl.addItem(cameras[i], i);  // camera[0], camera[1]
    }
    
  }
  
  ddl.setColorBackground(gris);
  ddl.setColorActive(color(255, 128));
}  
  
    
// configure position webcam

public static class ConfigurePositionWebcam extends PApplet {

 public void setup() {
   // the first solution has been two leds for calibration zero and scale.
   /*try{
        arduino = new Arduino(this, Arduino.list()[0], 57600);
   }
   catch ( Error e){
     println("Aucune carte Arduino branch\u00e9");
   }*/
  GUI = new PFrame7();
  GUI.setTitle("Find the components references");
  GUI.setLocation(780,250);
  GUI.setVisible(true);
  //end of the first solution
  // the second solution is to ask at the user, to click on the  pixel for define the calibration.
  textSize(20); 
  try{
  cam = new Capture(this,requestWidth,requestHeight,nameCam); // size of the webcam object
  opencv = new OpenCV(this, requestWidth, requestHeight); // The size of the object opencv must be the same that the size of the object cam
  }
  catch (IllegalArgumentException e){
    cam = new Capture(this,640,480,"/dev/video0"); // size of the webcam object
   opencv = new OpenCV(this, 640, 480);
    
  }
  cam.start(); 
}
public void draw() {
  if(cam.width==640 && cam.height==480){
  scale(1);
  }else if (cam.width==1920 && cam.height==1080){
   scale(0.5f); 
  }else if (cam.width==352 && cam.height==288){
   scale(1.7f); 
  }else if (cam.width==320 && cam.height==240){
  scale(2);
  }else if (cam.width==176 && cam.height==144){
  scale(2.5f);
  }else{
   scale(1); 
  }
  opencv.loadImage(cam);
  image(cam, 0, 0);
  noFill();
  stroke(0, 255, 0);
  strokeWeight(3);
  
  //the first solution of calibration:
  /*
  if(k==true){
    PVector loc = opencv.max();
    ellipse(loc.x, loc.y, 10, 10);
    println(loc.x + " " + loc.y); // attribuer ses valeurs
    xref = loc.x;  // xref define the zero x-position
    yref = loc.y;  // yref define the zero y-position
    k=false;
  }
  else if (j==true){
     PVector loc2 = opencv.max();
      ellipse(loc2.x, loc2.y, 10, 10);
      println(loc2.x + " " + loc2.y); // attribuer ses valeurs
      xref2 = loc2.x;  // xref2 define the position for compare the size pixel to mm
      yref2 = loc2.y;  // yref2 define the position for compare the siez pixel to mm
      j=false;
  }*/
  //the second solution of calibration:
  
   if(k==true){
    
    if (mousePressed){
       xref = mouseX;  // xref define the zero x-position
       yref = mouseY;  // yref define the zero y-position
       println(xref+" "+yref);
       k=false;
    }
       
  } else if (j==true){
     
      if (mousePressed){
       xref2 = mouseX;  // xref define the zero x-position
       yref2 = mouseY;  // yref define the zero y-position
       println(xref2+" "+yref2);
       j=false;
    }
  }
  
}


public void captureEvent(Capture c) {
  c.read();
}
   
}   // end of ConfigurePositionWebcam

public static class GUI extends PApplet {
  public void setup() {
    textSize(16);
  }
 
  public void draw() {
  text("setting reference point", 50, 30); 
  fill(0, 102, 153);
  text("configuration of the scale units of pixels/mm", 50, 80); 
  fill(0, 102, 153);
  text("exit", 50, 130); 
  fill(0, 102, 153); 
  
  rect(410,15,20,20);
  rect(410,70,20,20);
  rect(410,130,20,20);
  
   
  } 
  public void mousePressed() {
  if (mouseX > 410 && mouseX < 430 && mouseY > 15 && mouseY < 35) {
    /*arduino.analogWrite(9,100);
    delay(1000);
    k=true;
    arduino.analogWrite(9,0);*/
    k=true;
     
 } 
  else if (mouseX > 410 && mouseX < 430 && mouseY > 130 && mouseY < 150) {
    cam.stop();
    GUI.setVisible(false);
    configurePositionWebcam.setVisible(false);
    
  }
  else if(mouseX > 410 && mouseX < 430 && mouseY > 70 && mouseY < 90) {
    /*arduino.analogWrite(10,100);
    delay(1000);
    j=true;
    arduino.analogWrite(10,0);*/
    j=true;
    }
}
   
}

public static class GUI2 extends PApplet {
  public void setup() {
    textSize(16);
  }
 
  public void draw() {
  text("List and url missing components", 50, 30); 
  fill(0, 102, 153);
  text("Check the next electronic card", 50, 80); 
  fill(0, 102, 153);
  text("exit", 50, 130); 
  fill(0, 102, 153);

  
  rect(410,15,20,20);
  rect(410,70,20,20);
  rect(410,130,20,20);
  
   
  } 
  public void mousePressed() {
  if (mouseX > 410 && mouseX < 430 && mouseY > 15 && mouseY < 35) {
     // open l'autre frame
                         FindComponentReference = new PFrame5();
                         FindComponentReference.setTitle("Find the components references");
                         FindComponentReference.setLocationRelativeTo(null);
                         FindComponentReference.setVisible(true);
     
 } 
  else if (mouseX > 410 && mouseX < 430 && mouseY > 130 && mouseY < 150) {
    //close the window
    GUI2.setVisible(false);
    FindYourMissingComponent2.setVisible(false);
  }else if(mouseX > 410 && mouseX < 430 && mouseY > 70 && mouseY < 90){
    
  }
}
   
}
public static class GUI3 extends PApplet {
  public void setup() {
    textSize(16);
  }
 
  public void draw() {
  text("save the picture with the name of the type Card", 50, 30); 
  fill(0, 102, 153);
  text("exit", 50, 80); 
  fill(0, 102, 153);
  rect(450,15,20,20);
  rect(450,70,20,20);

  } 
  public void mousePressed() {
  if (mouseX > 450 && mouseX < 470 && mouseY > 15 && mouseY < 35) {
      save=true;
                             
 } else if (mouseX > 450 && mouseX < 470 && mouseY > 70 && mouseY < 90) {
    cam.stop();
    GUI3.setVisible(false);
    TakeACalibrationPicture.setVisible(false);
  } 

}

   
}
public static class GUI4 extends PApplet {
  public void setup() {
    textSize(16);
  }
 
  public void draw() {
  text("Take one picture of your card", 50, 30); 
  fill(0, 102, 153);
  text("exit", 50, 80); 
  fill(0, 102, 153);
  rect(410,15,20,20);
  rect(410,70,20,20);

  } 
  public void mousePressed() {
  if (mouseX > 410 && mouseX < 430 && mouseY > 15 && mouseY < 35) {
    save2=true;
                             
 } else if (mouseX > 410 && mouseX < 430 && mouseY > 70 && mouseY < 90) {
   cam.stop();
   //exit();
   GUI4.setVisible(false); 
   CheckYourCard.setVisible(false);
    
  } 

}
   
}



// Take a Calibration picture
/* an algorithm for detecting the type of card photographed will automatically save the picture under the name "5XC.jpg"
or, depending on the type of card found. And always ask the operator to save himself photography Reference: 
but you have to specify to the operator the name for the image corresponding to the map pictured: 
eg 4X card will be registered under the name "4X.jpg" and the right track recording of the image.*/



public static class TakeACalibrationPicture extends PApplet {
public void setup() {
  GUI3 = new PFrame9();
  GUI3.setTitle("Find the components references");
  GUI3.setLocation(780,250);
  GUI3.setVisible(true);
  try{
  cam = new Capture(this,requestWidth,requestHeight,nameCam); // size of the webcam object
  opencv = new OpenCV(this, requestWidth, requestHeight); // The size of the object opencv must be the same that the size of the object cam
  imgSrc = createImage(requestWidth,requestHeight,RGB);
  }
  catch(IllegalArgumentException e){
    cam = new Capture(this,640,480,"/dev/video0"); // size of the webcam object
   opencv = new OpenCV(this, 640, 480);
   imgSrc = createImage(640,480,RGB);
    
  }
  
  cam.start(); 
  
}
public void draw() {
  if(cam.width==640 && cam.height==480){
  scale(1);
  }else if (cam.width==1920 && cam.height==1080){
   scale(0.5f); 
  }else if (cam.width==352 && cam.height==288){
   scale(1.7f); 
  }else if (cam.width==320 && cam.height==240){
  scale(2);
  }else if (cam.width==176 && cam.height==144){
  scale(2.5f);
  }else{
   scale(1); 
  }
  
  opencv.loadImage(cam);
  image(cam, 0, 0 );
  
  noFill();
  stroke(0, 255, 0);
  strokeWeight(3);
   if(save==true){
     imgSrc=cam.get();
     //imgSrc.save("PhotoInit.jpg");
     save=false;
     selectOutput("click here for select a file to write to:", "fileSelected");
     cam.stop();
  }
  
}

public void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    println("User selected " + selection.getAbsolutePath());
    save(selection.getAbsolutePath()+".jpg");
  }
}

public void captureEvent(Capture c) {
  c.read();
}   
} // FIN DE TakeACalibrationPicture


//Check your card

public static class CheckYourCard extends PApplet {

public void setup() {
  GUI4 = new PFrame10();
  GUI4.setTitle("Find the components references");
  GUI4.setLocation(780,250);
  GUI4.setVisible(true);
  
  try{
  cam = new Capture(this,requestWidth,requestHeight,nameCam); // size of the webcam object
  opencv = new OpenCV(this, requestWidth, requestHeight); // The size of the object opencv must be the same that the size of the object cam
  imgSrc = createImage(requestWidth,requestHeight,RGB);
  }catch(IllegalArgumentException e){
    cam = new Capture(this,640,480,"/dev/video0"); // size of the webcam object
    opencv = new OpenCV(this, 640, 480);
    imgSrc = createImage(640,480,RGB);
    
  }
  cam.start(); 
}
public void draw() {
  if(cam.width==640 && cam.height==480){
  scale(1);
  }else if (cam.width==1920 && cam.height==1080){
   scale(0.5f); 
  }else if (cam.width==352 && cam.height==288){
   scale(1.7f); 
  }else if (cam.width==320 && cam.height==240){
  scale(2);
  }else if (cam.width==176 && cam.height==144){
  scale(2.5f);
  }else{
   scale(1); 
  }
  
  opencv.loadImage(cam);
  image(cam, 0, 0 );
  noFill();
  stroke(0, 255, 0);
  strokeWeight(3);
  if(save2==true){
   save2=false; 
   imgSrc=cam.get();
   imgSrc.save("Photo"+str(compt)+".jpg");
   compt=compt+1;
    
  }
  
}

public void captureEvent(Capture c) {
  c.read();
}
   
} // FIN DE CheckYourCard


//Find your missing component

public  class FindYourMissingComponent extends PApplet {

public void setup() {
  size(300,200);
}

public void draw() {
 
  background(120);
  rect(150, 90, 55, 55, 18, 18, 18, 18);
  text("click here and select your type of card",75,50);
}

public void mousePressed()
{
  if (mouseX > 150 && mouseX < 205 && mouseY > 90 && mouseY < 140) {
  chooseFile(); 
  }
}




 public void chooseFile()
{
// set system look and feel 
try { 
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
} 
catch (Exception e) { 
  e.printStackTrace();  
} 
 
// create a file chooser 
 final JFileChooser fc = new JFileChooser(); 
 
// in response to a button click: 
  returnVal = fc.showOpenDialog(_this); 
 
if (returnVal == JFileChooser.APPROVE_OPTION) { 
    file = fc.getSelectedFile(); 
 
     
      
      FindYourMissingComponent2 = new PFrame6();
      FindYourMissingComponent2.setTitle("Function after loading file");
      FindYourMissingComponent2.setLocationRelativeTo(null);
      FindYourMissingComponent2.setVisible(true); 
}
}
   
} //Fin DE FindYourMissingComponent

public static class FindYourMissingComponent2 extends PApplet {
 public void setup() {
   
  GUI2 = new PFrame8();
  GUI2.setTitle("Find the components references");
  GUI2.setLocation(200,360);
  GUI2.setVisible(true);
   
   
   if(TypeCard.compareTo("noCard")==0){
     photoInit = loadImage(file.getPath());
     delay(1000);
   } else{
     println("je charge typecard");
     photoInit = loadImage(TypeCard);
     delay(1000);
   }
   //photoInit =loadImage("PhotoInit.jpg");
   
  CheckYourCardp = loadImage("Photo0.jpg");
  
  //try{
    
  opencv = new OpenCV(this, photoInit);
  
     /* }catch(NullPointerException e){
   
        opencv= new OpenCV(this, CheckYourCardp);  // very bad situation !
  }*/
  
  
  opencv2 = new OpenCV(this,CheckYourCardp);
  
  //opencv.adaptiveThreshold(591, 1);
  //photoInit = opencv.getSnapshot();
  
 // opencv2.adaptiveThreshold(591,1);
  //CheckYourCardp = opencv2.getSnapshot();
  
  //try{
     
    opencv2.diff(photoInit);
  
     /* }catch(NullPointerException e){
    
        opencv2.diff(CheckYourCardp);  //very bad situation !
  }*/
  findYourMissingComponent = opencv2.getSnapshot();
  delay(1000);
  findYourMissingComponent.save("resultat0.jpg");
  //comptrslt= comptrslt+1;
  //delay(1000);
  
 
  //PImage prslt = loadImage("resultat0.jpg");
  opencvRslt = new OpenCV(this, findYourMissingComponent);
 
   opencvRslt.threshold(100);
  // opencv2.threshold(10);
  imgfinal = opencvRslt.getSnapshot();
  //imgfinal.save("findcomponent0.jpg");
 //imgfinal =opencv2.getSnapshot();
 //imgfinal.save("resultat0.jpg");
 
 //never put this three line in the draw, otherwise he re-dimension the picture, and this don't works:
   photoInit.resize(1920/2,1080/2);  // 960,540
   CheckYourCardp.resize(1920/2,1080/2);
   imgfinal.resize(1920/2,1080/2);
  
}

public void draw() {
   
  
  
  pushMatrix();
  scale(0.5f);
  image(photoInit, 0, 0); //image(img, 0, 0, width/2, height/2);
  image(CheckYourCardp, photoInit.width, 0);  //image(img, photoInit.width/2, 0, width/2, height/2);
  image(imgfinal, photoInit.width, photoInit.height);  // image(img, photoInit.width/2, photoInit.height/2, width/2, height/2);
  popMatrix();
  
  fill(255);
  text("Calibration picture", 10, 20);
  text("Picture of your Card", photoInit.width/2 +10, 20);
  text("find your missing component", photoInit.width/2 + 10, photoInit.height/2+ 20);


}
  
}

// FindComponentReference

public static class FindComponentReference extends PApplet {
  
 public void setup() {
   
   db = new SQLite( this, "ReferencesComponents.db" );  // open database file
   delay(1000);
   src = loadImage("resultat0.jpg");
   delay(1000);  
   src.resize(800, 0);
   size(src.width, src.height);
   opencv = new OpenCV(this, src);
   textSize(14);
   //text(nomComposant, 820, 50);   
   //fill(0, 102, 153, 51);
    opencv.threshold(100);
    
    locPixel = opencv.max(); 
    
    xComponent = xref - locPixel.x;
    yComponent = locPixel.y-yref;
    xPixelsCard = xref-xref2;
    xComponentMM=(xComponent*xMMCard)/xPixelsCard;
    yComponentMM=(yComponent*xMMCard)/xPixelsCard;
    
    
    //BDD(xComponent,yComponent);
    
    // for wait to finish the BDD mils to mm
     locx=locPixel.x;
     locy=locPixel.y;     
     BDD(locx,locy);
}

public void draw() {

  locPixel = opencv.max(); 
  //opencv.getOutput();
  image(opencv.getOutput(), 0, 0);
  //locPixel = opencv.max();  // rajouter une dizaine de loc pour d\u00e9tecter plusieurs composants, puis afficher leur r\u00e9sultat que si ils sont diff\u00e9rents
  stroke(255, 0, 0);
  strokeWeight(4);
  noFill();
  rect(locPixel.x, locPixel.y, 50, 30);
  text(nomComposant, 820, 50);   
  fill(0, 102, 153, 51);

// Left buttom
  if (LinkButton == true) {
    fill(255);
  } else {
    noFill();
  }
   



} // end of draw

public void BDD(float locx,float locy){
  
  if ( db.connect() )
    {
      db.query( "SELECT name as \"Name\" FROM SQLITE_MASTER where type=\"table\"" );
        
        while (db.next())
        {
            println( db.getString("Name") );
        }
       
        db.query( "SELECT name,URL FROM components where XMIN <= "+ locx + " AND XMAX >= " + locx +" AND YMIN >= " + locy + " AND YMAX <= "+ locy +";");
        while (db.next())
        {
            nomComposant=db.getString("NAME");
            lienURL=db.getString("URL");
        }
    }
  
}

public void mousePressed() {
  if (LinkButton) {
    try{
    link(lienURL);
    }
   catch(NullPointerException e){
    link("https://www.google.fr/?gws_rd=ssl");
   } 
  }
}

public void mouseMoved() {
  checkButtons();
}
 
public void mouseDragged() {
  checkButtons();
}

public void checkButtons() {
  if (mouseX > 820 && mouseX < 980 && mouseY > 30 && mouseY < 50) {
    LinkButton = true; 
  } else {
    LinkButton = false;
  }
}

   
}   // end of FindComponentReference

//end of programm
public void stop(){
   super.stop();  
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "ScanComponentPCB" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
