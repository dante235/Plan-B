/*****************************************************************
 * - PLAN B - Décembre 2014                                      *
 * Idée originale : David Arnaud                                 *
 * Réecriture et correction de Code : Mushussu de www.codelab.fr *
 * Copyleft : cette œuvre est libre, vous pouvez la copier       *
 * la diffuser et la modifier selon les termes de la             *
 * Licence Art Libre http://www.artlibre.org                     *
 ****************************************************************/


import controlP5.*;                          //importation de la bibliothèque Control P5
import processing.pdf.*;                     //importation de la bibliothèque PDF

ControlP5 cp5;
PImage imageRef;                             // variable qui stocke en mémoire l'image de référence
int pagesValue = 200;                        // variable qui affiche le nombre de pages par défaut
Textlabel myTextlabelA;                      // variable de l'étiquette qui affiche le chemin du fichier selectionné 

void setup() {
  size(400, 400);                             // dimension de l'interface
  imageRef = null;                            // Initialisation de l'objet PImage à null
  cp5 = new ControlP5(this);

  cp5.addButton("img")                         //création du premier bouton "Va chercher l'image"
    .setPosition(100, 100)                     //position en x et y 
      .setSize(200, 19)                        //dimension largeur et hauteur 
        .setLabel("Va chercher l'image")       //libellé du bouton
          ;

  myTextlabelA = cp5.addTextlabel("label")     //création d'un champ texte pour indiquer à l'utilisateur la fin du chargement du fichier
    .setText("//")                             //Texte affiché au lancement du programme
      .setPosition(100, 120)                   //position en x et y
        .setColorValue(#FFFFFF)                //couleur du texte affiché
          .setFont(createFont("Arial", 10))    //taille et typographie utilisée
            ;

  cp5.addSlider("pagesValue")                  //création du slider horizontal "Nombres de Pages"
    .setRange(0, 500)                          //valeur minimum et maximum
      //.setValue(pagesValue)                  //valeur par défaut du slideur
      .setPosition(100, 142)                 //position en x et y
        .setSize(125, 19)                      //dimension largeur et hauteur 
          .setLabel("Nombres de Pages")         //libellé du slider
            ;

  cp5.addButton("gPDF")                        //création du troisième bouton "Enregistrer le PDF"
    .setPosition(100, 180)                     //position en x et y
      .setSize(200, 19)                        //dimension largeur et hauteur
        .setLabel("Enregistrer le PDF")        //libellé du slider
          ;
}

void draw() {
  background(0);                               // Rafraîchissement de la fenêtre
}


/*Méthode pour l'incrémentation ou la décrémentation du nombre de pages :
 Permet lorsque l'on appuie sur les touches haut ou bas du clavier de respectivement
 incrémenter de 1 ou décrémenter de 1 le nombre de pages dans le slider*/

public void keyPressed() { 
  if (key == CODED) {
    if (keyCode == UP) {
      cp5.getController("pagesValue").setValue(pagesValue + 1);
    } else if (keyCode == DOWN) {
      cp5.getController("pagesValue").setValue(pagesValue - 1);
    }
  }
}



/*Méthode pour l'incrémentation ou la décrémentation du nombre de pages :
 à le même résultat que la méthode précédente mais l'incrémentation ou la décrémentation
 se fait grâce à la molette de la souris si disponible*/

public void mouseWheel(MouseEvent event) {
  cp5.getController("pagesValue").setValue(pagesValue + event.getCount());
}



/*Méthode associé au premier bouton, elle permet d'ouvrir une boite de dialogue
 afin de choisir un fichier image*/

public void img() {
  selectInput("Choix de l'image", "selectionImage");
}



/*Méthode qui vérifie et stock dans la variable imageRef le fichier image choisi
 dans la méthode précédente, elle inscrit également le message "// fichier sélectionné //"
 dans le champ texte myTextlabelA*/

public void selectionImage(File selection) {
  if (selection != null) {
    imageRef = loadImage(selection.getAbsolutePath());
    if (imageRef != null) {
      myTextlabelA.setValue("// fichier sélectionné //");
    }
  }
}



/*Méthode qui redimensionne le fichier image sélectionné. Elle crée ensuite un PDF qui sera composé
 du nombre de pages indiqué par le slider, la première page contient la première ligne de pixel
 de l'image sélectionnée, cette ligne est étendue sur la hauteur de la page puis le processus recommence
 avec la page 2 et la seconde ligne de pixel de l'image etc. Jusqu'à atteindre le nombre de pages désiré*/

public void gPDF () {
  if (imageRef.width > imageRef.height) {
    imageRef.resize(0, pagesValue);
  } else {
    imageRef.resize(pagesValue, 0);
  }

  PGraphics pdf = createGraphics(imageRef.width, imageRef.height, PDF, "FichierPDF.pdf");
  pdf.beginDraw();
  for (int i = 1; i < pagesValue; i++) {
    pdf.background(255);
    pdf.image(imageRef.get(0, i, imageRef.width, 1), 0, 0, imageRef.width, imageRef.height);
    if (i < pagesValue - 1) {        // Evite d'afficher une page blanche à la fin du fichier
      ((PGraphicsPDF) pdf).nextPage();
    }
  }
  pdf.dispose();
  pdf.endDraw();
  println("Génération PDF terminée");
  println(imageRef.width, "x", imageRef.height);
}
