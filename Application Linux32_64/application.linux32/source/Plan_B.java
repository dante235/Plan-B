import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import processing.pdf.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Plan_B extends PApplet {

/*****************************************************************
 * - PLAN B - D\u00e9cembre 2014                                      *
 * Id\u00e9e originale : David Arnaud                                 *
 * R\u00e9ecriture et correction de Code : Mushussu de www.codelab.fr *
 * Copyleft : cette \u0153uvre est libre, vous pouvez la copier       *
 * la diffuser et la modifier selon les termes de la             *
 * Licence Art Libre http://www.artlibre.org                     *
 ****************************************************************/


                          //importation de la biblioth\u00e8que Control P5
                     //importation de la biblioth\u00e8que PDF

ControlP5 cp5;
PImage imageRef;                             // variable qui stocke en m\u00e9moire l'image de r\u00e9f\u00e9rence
int pagesValue = 200;                        // variable qui affiche le nombre de pages par d\u00e9faut
String cheminFichier;                        // variable qui stock le chemin d'enregistrement du fichier PDF
Textlabel myTextlabelA;                      // variable de l'\u00e9tiquette qui affiche le chemin du fichier selectionn\u00e9
Textlabel myTextlabelB;                      // variable de l'\u00e9tiquette qui affiche le chemin d'enregistrement du fichier PDF


public void setup() {
  size(400, 400);                             // dimension de l'interface
  imageRef = null;                            // Initialisation de l'objet PImage \u00e0 null
  cp5 = new ControlP5(this);

  cp5.addButton("img")                         //cr\u00e9ation du premier bouton "Va chercher l'image"
    .setPosition(100, 100)                     //position en x et y 
      .setSize(200, 19)                        //dimension largeur et hauteur 
        .setLabel("Va chercher l'image")       //libell\u00e9 du bouton
          ;

  myTextlabelA = cp5.addTextlabel("labelSelection")     //cr\u00e9ation d'un champ texte pour indiquer \u00e0 l'utilisateur la fin du chargement du fichier
    .setText("//")                                      //Texte affich\u00e9 au lancement du programme
      .setPosition(100, 120)                            //position en x et y
        .setColorValue(0xffFFFFFF)                         //couleur du texte affich\u00e9
          .setFont(createFont("Arial", 10))             //taille et typographie utilis\u00e9e
            ;

  cp5.addSlider("pagesValue")                  //cr\u00e9ation du slider horizontal "Nombres de Pages"
    .setRange(0, 500)                          //valeur minimum et maximum
      //.setValue(pagesValue)                  //valeur par d\u00e9faut du slideur
      .setPosition(100, 143)                   //position en x et y
        .setSize(125, 25)                      //dimension largeur et hauteur 
          .setLabel("Nombres de Pages")        //libell\u00e9 du slider
            ;

  cp5.addButton("gPDF")                        //cr\u00e9ation du troisi\u00e8me bouton "Enregistrer le PDF"
    .setPosition(100, 185)                     //position en x et y
      .setSize(200, 19)                        //dimension largeur et hauteur
        .setLabel("Enregistrer le PDF")        //libell\u00e9 du slider
          ;
          
  myTextlabelB = cp5.addTextlabel("labelEnregistrement")     //cr\u00e9ation d'un champ texte pour indiquer \u00e0 l'utilisateur la fin du chargement du fichier
    .setText("//")                                           //Texte affich\u00e9 au lancement du programme
      .setPosition(100, 205)                                 //position en x et y
        .setColorValue(0xffFFFFFF)                              //couleur du texte affich\u00e9
          .setFont(createFont("Arial", 10))                  //taille et typographie utilis\u00e9e
            ;
}

public void draw() {
  background(0);                               // Rafra\u00eechissement de la fen\u00eatre
}

/*M\u00e9thode pour l'incr\u00e9mentation ou la d\u00e9cr\u00e9mentation du nombre de pages :
 Permet lorsque l'on appuie sur les touches haut ou bas du clavier de respectivement
 incr\u00e9menter de 1 ou d\u00e9cr\u00e9menter de 1 le nombre de pages dans le slider*/

public void keyPressed() { 
  if (key == CODED) {
    if (keyCode == UP) {
      cp5.getController("pagesValue").setValue(pagesValue + 1);
    } else if (keyCode == DOWN) {
      cp5.getController("pagesValue").setValue(pagesValue - 1);
    }
  }
}

/*M\u00e9thode pour l'incr\u00e9mentation ou la d\u00e9cr\u00e9mentation du nombre de pages :
 \u00e0 le m\u00eame r\u00e9sultat que la m\u00e9thode pr\u00e9c\u00e9dente mais l'incr\u00e9mentation ou la d\u00e9cr\u00e9mentation
 se fait gr\u00e2ce \u00e0 la molette de la souris si disponible*/

public void mouseWheel(MouseEvent event) {
  cp5.getController("pagesValue").setValue(pagesValue + event.getCount());
}

/*M\u00e9thode associ\u00e9 au premier bouton, elle permet d'ouvrir une boite de dialogue
 afin de choisir un fichier image*/

public void img() {
  selectInput("Choix de l'image", "selectionImage");
}

/*M\u00e9thode qui v\u00e9rifie et stock dans la variable imageRef le fichier image choisi
 dans la m\u00e9thode pr\u00e9c\u00e9dente, elle inscrit \u00e9galement le message "// Fichier s\u00e9lectionn\u00e9 //"
 dans le champ texte myTextlabelA*/

public void selectionImage(File selection) {
  if (selection != null) {
    imageRef = loadImage(selection.getAbsolutePath());
    if (imageRef != null) {
      myTextlabelA.setValue("// Fichier s\u00e9lectionn\u00e9 //");
    }
  }
}

/*M\u00e9thode associ\u00e9 au troisi\u00e8me bouton, elle permet d'ouvrir une boite de dialogue
 afin de choisir l'emplacement du fichier PDF*/

public void gPDF () {
  selectOutput("choix de l'emplacement", "selectionEnregistrement");
}

/*M\u00e9thode qui v\u00e9rifie et stock dans la variable cheminFichier le chemin choisi
 dans la m\u00e9thode pr\u00e9c\u00e9dente, elle inscrit \u00e9galement le message "// Fichier PDF enregistre //"
 dans le champ texte myTextlabelB*/
 
public void selectionEnregistrement(File selection) {
  if (selection != null) {
    cheminFichier = (selection.getAbsolutePath());
    println (cheminFichier);
    if (imageRef != null) {
      myTextlabelB.setValue("// Fichier PDF enregistre //");
      redimension();
    }
  }
}

/*M\u00e9thode qui redimensionne le fichier image s\u00e9lectionn\u00e9 en fonction du nombre de pages indiqu\u00e9 par le slider
si l'image a un nombre de pixel en largeur sup\u00e9rieur au nompbre de pixel en hauteur elle est redimensionn\u00e9e
en hauteur avec le chiffre indiqu\u00e9 par le slider et en largeur proportionnellement. sinon elle est redimensionn\u00e9e
en largeur avec le chiffre indiqu\u00e9 par le slider et en hauteur proportionnellement, on renvoi ensuite \u00e0 la m\u00e9thode
de g\u00e9n\u00e9ration du pdf ci-dessous*/
 
public void redimension(){
  if (imageRef.width > imageRef.height) {
    imageRef.resize(0, pagesValue);
    generationPDF();
  } else {
    imageRef.resize(pagesValue, 0);
    generationPDF();
  }
}

/*M\u00e9thode qui g\u00e9n\u00e8re le fichier PDF \u00e0 proprement parler, elle n'est que l'application de la r\u00e9f\u00e9rence processing
relatif \u00e0 la librairie PDF https://processing.org/reference/libraries/pdf/ elle inscrit dans la console la taille
de l'image ainsi que le message "G\u00e9n\u00e9ration PDF termin\u00e9e"*/
  
public void generationPDF(){
   PGraphics pdf = createGraphics(imageRef.width, imageRef.height, PDF, cheminFichier+".pdf");
  pdf.beginDraw();
  for (int i = 0; i < pagesValue; i++) {
    pdf.background(255);
    pdf.image(imageRef.get(0, i, imageRef.width, 1), 0, 0, imageRef.width, imageRef.height);
     if (i < pagesValue - 1) {        // Evite d'afficher une page blanche \u00e0 la fin du fichier
      ((PGraphicsPDF) pdf).nextPage();
  }
  }
  pdf.dispose();
  pdf.endDraw();
  println("G\u00e9n\u00e9ration PDF termin\u00e9e");
  println(imageRef.width, "x", imageRef.height);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Plan_B" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
