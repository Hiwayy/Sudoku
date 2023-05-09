package twoSudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Math.random;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Sudoku implements KeyListener {						// Définition de la classe Sudoku qui implémente l'interface KeyListener
 /*TEST*/
	private int erreurRestantes;
	private int difficulte;
	/*TEST_NIVEAU_SPECIAL*/
	private int carreValide = 0;
	/*FIN_TEST*/
/*FIN TEST*/
	public static JFrame f = new JFrame("Sudoku");					// Création d'une fenêtre avec le titre "Sudoku"
    public static JPanel p = new JPanel();							// Création d'un panneau
    public static JTextField[][] M = new JTextField[9][9];			// Création d'un tableau de 9x9 pour les cellules du sudoku
    public static JTextField[][] CopieM = new JTextField[9][9];		// Création d'une copie du tableau de 9x9 pour une copie du sudoku
    JMenuBar mb = new JMenuBar();									// Création d'une barre de menu
    JMenu menu = new JMenu("niveau");								// Création d'un menu avec le titre "niveau"
    JMenuItem n1 = new JMenuItem("facile");							// Création d'un élément de menu "facile"
    JMenuItem n2 = new JMenuItem("Moyen");							// Création d'un élément de menu "Moyen"
    JMenuItem n3 = new JMenuItem("dificile");						// Création d'un élément de menu "difficile"
    /*TEST_NEW_NIVEAU*/
    JMenuItem n4 = new JMenuItem("Special");
    /*FIN_TEST_NEW_NIVEAU*/
    
    /*AVOIR SI LAISSE*/		Font font = new Font("Lucida Console", Font.BOLD,28);			// Création d'une police de caractère pour les cellules du sudoku
    
    public static void colorier(JTextField[][]M){					// Définition de la méthode "colorier" prenant en paramètre un tableau de champs de texte
        int x,y;													// Déclaration des variables entières "x" et "y"
        for (x = 0;x<9;x++){										// Boucle for pour parcourir les lignes du tableau
            for (y = 0;y<9;y++){									// Boucle for pour parcourir les colonnes du tableau
             switch(x){												// Début d'une structure switch basée sur la variable "x"
                 case 0:case 1:case 2:case 6: case 7: case 8:{		// Si la valeur de "x" est égale à l'une des valeurs spécifiées dans les cases, alors exécute le bloc de code suivant :
                 switch(y){											// Début d'une structure switch basée sur la variable "y"
                     case 0:case 1:case 2: case 6:case 7:case 8: M[x][y].setBackground(Color.green);break;   // Si la valeur de "y" est égale à l'une des valeurs spécifiées dans les cases, alors définit le fond du champ de texte à la couleur verte et sort de la structure switch interne                         
                 }
             break;}  												// Sort de la structure switch externe
                 case 3:case 4:case 5:{								// Si la valeur de "x" est égale à l'une des valeurs spécifiées dans les cases, alors exécute le bloc de code suivant :
                 switch(y){											// Début d'une structure switch basée sur la variable "y"
                     case 3:case 4:case 5: M[x][y].setBackground(Color.green);break;  	// Si la valeur de "y" est égale à l'une des valeurs spécifiées dans les cases, alors définit le fond du champ de texte à la couleur verte et sort de la structure switch interne                          
                 }
             break;}  												// Sort de la structure switch externe
            }
        }
    }
    }
    
    public static void initialiser(JTextField[][]K){				// Définition de la fonction initialiser prenant en entrée un tableau de JTextField
    int i;int j;int ligne;int colonne;int v;						// Déclaration de l'indice de la boucle de la première étape, de la seconde étape
    for (i=0;i<9;i++){K[0][i].setText(Integer.toString(i+1));}		// Boucle pour la première étape, initialise la première ligne avec les valeurs de 1 à 9	
    for (j=9;j<81;j++){												// Boucle pour la seconde étape, initialise les autres lignes avec des valeurs aléatoires
        ligne=j/9;													// Calcul de la ligne courante
        colonne=j%9;												// Calcul de la colonne courante
        if(ligne%3==0) v=1; else v=0;								// Calcul de la valeur aléatoire à partir de la ligne courante
        K[ligne][colonne].setText(Integer.toString((Integer.parseInt(K[ligne-1][colonne].getText())+(2+v))%9+1));		// Initialise le champ courant avec une valeur aléatoire
          }
    }
    
    public static void permuterLigne(JTextField[][]K,int L1, int L2){
    int aux;														// déclare une variable auxiliaire pour effectuer la permutation
    for (int i=0;i<9;i++){											 // boucle pour parcourir chaque colonne de la ligne
        aux = Integer.parseInt(K[L2][i].getText());					// stocke la valeur de la deuxième ligne dans la variable auxiliaire
        K[L2][i].setText(K[L1][i].getText());						// remplace la valeur de la deuxième ligne par celle de la première ligne
        K[L1][i].setText(Integer.toString(aux));					// remplace la valeur de la première ligne par celle stockée dans la variable auxiliaire
        }
    }
    
    public static void permuterColonne(JTextField[][]K,int C1, int C2){
    int aux;														// variable pour stocker une valeur temporairement
    for (int i=0;i<9;i++){											// boucle sur les lignes
        aux = Integer.parseInt(K[i][C2].getText());					// stocker la valeur de la colonne C2
        K[i][C2].setText(K[i][C1].getText());						// échanger les valeurs entre C1 et C2
        K[i][C1].setText(Integer.toString(aux));					 // mettre la valeur de C2 dans C1
        }
    }
    
    public static boolean exisiteEnLigne(JTextField[][]K,int x, int y, int i){		 // Définition de la méthode avec 4 paramètres : la grille K, les indices x, y et i.
        if((K[x][i].getText().equals(K[y][0].getText()))||							 // Si la valeur de K[x][i] est égale à l'une des valeurs de la ligne y, 
                (K[x][i].getText().equals(K[y][1].getText()))||
                (K[x][i].getText().equals(K[y][2].getText()))) return true;
        return false;      															 // alors la méthode renvoie true, sinon elle renvoie false.          
    }
    
     public static boolean exisiteEnColonne(JTextField[][]K,int x, int y, int i){	 // Vérifie si la valeur de la cellule de la colonne "x" existe dans l'une des trois cellules de la colonne "y" dans la même région (sous-grille)
        if((K[i][x].getText().equals(K[0][y].getText()))||
                (K[i][x].getText().equals(K[1][y].getText()))||
                (K[i][x].getText().equals(K[2][y].getText()))) return true;				 // si oui, renvoie vrai (true)
        return false;     																 // sinon, renvoie faux (false)           
    }
     
     public static void effacer(JTextField[][]K,int x){
         int c;int col;												// variable pour compter le nombre de cases effacées dans la ligne i // variable pour stocker la colonne de la case à effacer
         for (int i=0;i<9;i++){										// boucle pour parcourir les lignes de la grille
             c=0;													 // initialisation du compteur à 0 pour chaque ligne
                 int v =(int)(x*random())+3;						//v represente le nombre de cases à effacer dans la ligne i
                 while(c<v){										// boucle pour effacer le nombre de cases défini aléatoirement dans la ligne i
                     col=(int)(9.0*random());						// choix aléatoire de la colonne de la case à effacer
                 if (!(K[i][col].getText().equals(""))) {			// vérification que la case n'est pas déjà vide
                     K[i][col].setText("0");						// effacement de la case
                     c++;											// incrémentation du compteur de cases effacées dans la ligne i
                 }
             }
         }
         verouiller(M);												// appel de la fonction verouiller pour verrouiller les cases initialement remplies
     }
  
     public static void verouiller(JTextField[][]K){
         int i,j;
         for (i=0;i<9;i++){															// Parcours de toutes les cases de la grille
           for (j=0;j<9;j++) {
             if (K[i][j].getText().equals("0")){									// Si la case contient 0
            	// La case doit être vide et éditable
            	 K[i][j].setText("");
                 K[i][j].setEditable(true);
             }  
            else K[i][j].setEditable(false);										 // Sinon, la case doit être verrouillée et non éditable
           }         
     }
     }
     
     public static void Copier(JTextField [][]K, JTextField[][]L){
      int i,j;
         for (i=0;i<9;i++){															// boucle pour parcourir les lignes
           for (j=0;j<9;j++) {														 // boucle pour parcourir les colonnes
               try{																	// bloc try-catch pour éviter les erreurs de type NullPointerException
               L[i][j].setText(K[i][j].getText());									// copie la valeur de K[i][j] dans L[i][j]
               } 
               catch(NullPointerException e){}										 // ignore l'erreur de type NullPointerException
           }   }
     }
     
     public static void init(JTextField[][]K){
      int i,j;
         for (i=0;i<9;i++){															 // boucle pour initialiser chaque case avec un nouveau JTextField
           for (j=0;j<9;j++) {				
               K[i][j]=new JTextField();											
               try{		
           K[i][j].setText("0");  }													// on essaie d'initialiser la case avec le chiffre 0			
           catch(NullPointerException e){}											// si la case n'existe pas (NullPointerException), on ne fait rien
           } }  
     }
     
     public static void generer(JTextField[][]M, int x, int y){
         int cl=0;int cc=0;											// compteur de permutations de lignes, colonnes
         initialiser(M);											// Initialisation de la grille avec des nombres aléatoires
      do{															// Générer des nombres aléatoires pour les indices des lignes et des colonnes à permuter
      int L1 = (int)(9.0*random());
      int L2 = (int)(9.0*random());
      int C1 = (int)(9.0*random());
      int C2 = (int)(9.0*random());
      if ((L1/3==L2/3)&&(L1!=L2)||((exisiteEnLigne(M,L1,L2,0))&&((exisiteEnLigne(M,L1,L2,1)) &&				 // Permuter les lignes L1 et L2 si elles sont dans la même région ou si elles n'ont pas de nombres en commun
             ((exisiteEnLigne(M,L1,L2,2))))))					
      {
          permuterLigne(M,L1,L2);cl++;								 // permutation de L1 et L2
      }
      if ((C1/3==C2/3)&&(C1!=C2)||((exisiteEnColonne(M,C1,C2,0))&&((exisiteEnColonne(M,C1,C2,1)) &&			 // Permuter les lignes L1 et L2 si elles sont dans la même région ou si elles n'ont pas de nombres en commun
              ((exisiteEnColonne(M,C1,C2,2)))))){
          permuterColonne(M,C1,C2);cc++;																	// permutation de C1 et C2
      }
      }
      while((cl<=x)||(cc<=x));											// Répéter jusqu'à ce que le nombre minimal de permutations de lignes et colonnes soit atteint
      init(CopieM);														// Initialisation d'une grille de copie
      Copier(M,CopieM);													// Copie de la grille générée
      effacer(M,y);														// Effacement des cases aléatoires
      p.setVisible(true);												// Rendre visible la grille générée
     }
     
     public void ValiderLigne(JTextField[][]K, JTextField[][]L, int x){
         boolean valide = true; int i;
         for (i=0; i<9;i++){														    	// boucle pour chaque case de la ligne x
             if(!(K[x][i].getText().equals(L[x][i].getText()))) valide = false;				// si la valeur de la case dans K est differente de celle dans L
         }
         if(valide==true){																	// si la ligne est valide
         for ( i=0; i<9;i++){ 																 // boucle pour chaque case de la ligne x   
         K[x][i].setBackground(Color.orange);												// met la couleur de fond en orange
         K[x][i].setEditable(false);														 // empeche la modification
         }
     }
     }
     
     public void ValiderColonne(JTextField[][]K, JTextField[][]L, int x){
         boolean valide = true; int i;
         for (i=0; i<9;i++){																	// boucle pour chaque case de la ligne x
             if(!(K[i][x].getText().equals(L[i][x].getText()))) valide = false;					  // si la valeur de la case dans K est differente de celle dans L
         }
         if(valide==true){																		 // si la ligne est valide
         for ( i=0; i<9;i++){ 																	// boucle pour chaque case de la ligne x   
         K[i][x].setBackground(Color.orange);
         K[i][x].setEditable(false);
         }
     }
     }
     
     public void ValiderCarre3X3(JTextField[][]K,JTextField[][]L,int line,int col){
         int l=0;int c =0;boolean valide=true;int ligne, colonne;								// Initialisation des indices pour parcourir la case 3x3 correspondant à la ligne et colonne données
      switch(line){																				//commence le premier switch sur la variable "line"
          case 0:case 1: case 2:{																 //si line est 0, 1, ou 2, exécute ce bloc de code
              switch (col){																		//commence le deuxième switch sur la variable "col"
                  case 0:case 1: case 2:{ l=0;c=0;break;}										//si col est 0, 1, ou 2, fixe l=0 et c=0 et sort du switch
                     case 3:case 4: case 5: { l=0;c=3;break;}									//si col est 3, 4, ou 5, fixe l=0 et c=3 et sort du switch
                         case 6:case 7: case 8:{ l=0;c=6;break;} 								//si col est 6, 7, ou 8, fixe l=0 et c=6 et sort du switch
              }break;																			//sort du premier switch
          }
          case 3:case 4: case 5:{																//si line est 3, 4, ou 5, exécute ce bloc de code
              switch (col){																		//commence le deuxième switch sur la variable "col"
                  case 0:case 1: case 2:{ l=3;c=0;break;}										//si col est 0, 1, ou 2, fixe l=3 et c=0 et sort du switch
                     case 3:case 4: case 5: { l=3;c=3;break;}									//si col est 3, 4, ou 5, fixe l=3 et c=3 et sort du switch
                         case 6:case 7: case 8:{ l=3;c=6;break;} 								//si col est 6, 7, ou 8, fixe l=3 et c=6 et sort du switch
              }break;																			//sortie du deuxieme switch 
          }
          case 6:case 7: case 8:{																//si line est 6, 7, ou 8, exécute ce bloc de code
              switch (col){																		 //commence le deuxième switch sur la variable "col"
                  case 0:case 1: case 2:{ l=6;c=0;break;}										//si col est 0, 1, ou 2, fixe l=6 et c=0 et sort du switch
                     case 3:case 4: case 5: { l=3;c=3;break;}									//si col est 3, 4, ou 5, fixe l=3 et c=3 et sort du switch
                         case 6:case 7: case 8:{ l=6;c=6;break;} 								//si col est 6, 7, ou 8, fixe l=6 et c=6 et sort du switch
              }break;
          }
      }
      for (int i = 0; i<9;i++){																	// Vérification de chaque case dans la case 3x3 correspondant à la ligne et colonne données
          ligne=(i/3)+l;
          colonne = (i%3)+c;
          if(!(K[ligne][colonne].getText().equals(L[ligne][colonne].getText())))
              valide= false;
      }
      if (valide==true){																		 // Si la case 3x3 est valide, on verrouille toutes les cases correspondantes
          for (int i=0;i<9;i++){
           ligne=(i/3)+l;
           colonne = (i%3)+c;  
           K[ligne][colonne].setBackground(Color.orange);
            K[ligne][colonne].setEditable(false);
      }
     }
    /*TEST_NIVEAU_SPECIAL*/ 
      if (valide == true) {
    	  carreValide++;
      }
     /*TEST_FIN*/    
     }
     
     public static boolean verif(JTextField[][]K,JTextField[][]L){
         for (int i =0;i<9;i++){																	// boucle sur les lignes
            for (int j =0;j<9;j++){																	// boucle sur les colonnes
                if(!(K[i][j].getText().equals(L[i][j].getText()))) return false;					// si la case K[i][j] n'est pas égale à la case L[i][j], retourner false
            }  
         }
         return true;																				// si toutes les cases sont égales, retourner true
     }
     
/*TEST*/
     
     
    public void reinitialiseJeu() {
    	for (int i = 0; i < 9; i++) {
    		for (int j = 0; j < 9; j++) {
    			M[i][j].setText("");
    			M[i][j].setForeground(Color.black);
    			M[i][j].setEditable(true);
    		}
    	}
    	
    	
    	//générer la grille avec la difficulté enregistrée
    	if (difficulte == 1 ) {
    	     colorier(M);
    	     generer(M,10,3);
    	     
    	     difficulte = 1;
    	     erreurRestantes = 5;
    	}
    	else if (difficulte == 2) {
    	     colorier(M);
    	     generer(M,15,5);
    	     
    	     difficulte = 2;
    	     erreurRestantes = 10;
    	}
    	else if (difficulte == 4) {
    		colorier(M);
    		generer(M, 15, 5);
    		
    		difficulte = 4;
    		erreurRestantes = 15;
    	}
    	else {
    	     colorier(M);
    	     generer(M,40,15);
    	     
    	     difficulte = 3;
    	     erreurRestantes = 20;
    	}
    		
    } 
    public void recommencer() {
    	for (int i = 0; i < 9; i++) {
    		for (int j = 0; j < 9; j++) {
    			if (CopieM[i][j].getText().equals("")) {
    				M[i][j].setText("");
        			M[i][j].setForeground(Color.black);
        			M[i][j].setEditable(true);
        			
    			}
    		}
    	}
    	
    }
    
    
    public void inverserChiffres(JTextField[][] K) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = K[i][j].getText();
                if (!text.isEmpty()) {
                    int inverted = 10 - Integer.parseInt(text);
                    K[i][j].setText(Integer.toString(inverted));
                }
            }
        }
    }
    
    public void cacherCarré() 
    {
    	
    }

/*TEST_FIN*/   
    
    Sudoku(){	
      f.setSize(600,600);												//Creation de la fenetre princiaple
      menu.add(n1);menu.add(n2);menu.add(n3);menu.add(n4);							 // Création du menu et des options de difficulté
      mb.add(menu);
      f.setJMenuBar(mb);												
      p.setVisible(false);												 // Configuration de la grille de jeu
      p.setLayout(new GridLayout(9,9));
      for (int i = 0; i<9;i++){
          for (int j = 0; j<9;j++){
              M[i][j]= new JTextField();								  // Création des cases de la grille
              M[i][j].setFont(font);
              M[i][j].setForeground(Color.black);
              M[i][j].setHorizontalAlignment(SwingConstants.CENTER);
              M[i][j].addKeyListener(this);
              p.add(M[i][j]);											// Ajout des cases à la grille
          }
      }
      // Ajout de la grille à la fenêtre principale
      f.setContentPane(p);
      p.setVisible(false);
      f.setVisible(true);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setResizable(false);
      
      
   // Ajout des actions pour chaque option de difficulté
      
      n1.addActionListener(new ActionListener(){
     @Override
    public void actionPerformed(ActionEvent e) {
     colorier(M);
     generer(M,10,3);
     
     difficulte = 1;
     erreurRestantes = 5;	
      
    }   
    });
      
      n2.addActionListener(new ActionListener(){
     @Override
    public void actionPerformed(ActionEvent e) {
     colorier(M);
     generer(M,15,5);
     
     difficulte = 2;
     erreurRestantes = 10;
    }   
    });
      
      n3.addActionListener(new ActionListener(){
     @Override
    public void actionPerformed(ActionEvent e) {
     colorier(M);
     generer(M,40,15);
     
     difficulte = 3;
     erreurRestantes = 20;
    }   
    });
    /*TEST_NEW_NIVEAU*/
      n4.addActionListener(new ActionListener(){
     @Override
    public void actionPerformed(ActionEvent e) {
     colorier(M);
     generer(M, 15, 5);
     
     difficulte = 4;
     erreurRestantes = 500;
    }   
    });
    /*FIN_TEST_NEW_NIVEAU*/  
    }
    
    
    

public static void main(String[]args){
new Sudoku();								    // création d'une nouvelle instance de la classe Sudoku
}

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (carreValide >= 2 && difficulte == 4) {
            inverserChiffres(M);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int v=0;int v1=0;
      JTextField tf = (JTextField) e.getSource();										    // On récupère le composant qui a généré l'événement
      tf.setFont(new Font("Arial Black",Font.BOLD,28));
      tf.setForeground(Color.BLUE);
      String text = tf.getText();
      if((!text.equals("1"))&&(!text.equals("2"))&&(!text.equals("3"))&&(!text.equals("4"))&&					  // On vérifie si le texte est un chiffre compris entre 1 et 9
              (!text.equals("5"))&&(!text.equals("6"))&&(!text.equals("7"))&&(!text.equals("8"))&&
              (!text.equals("9")))
          tf.setText("");
      for (int i=0; i<M.length;i++)																		  // On parcourt la grille M pour valider les chiffres saisis par l'utilisateur
          for (int j=0; j<M[i].length;j++){	  
        	  if(M[i][j]==tf ||M[i][j].equals(tf)){
               try{																	 // On vérifie si la ligne, la colonne et le carré 3x3 sont corrects
                   ValiderLigne(M,CopieM,i);
                   ValiderColonne(M,CopieM,j);
                   ValiderCarre3X3(M,CopieM,i,j);
               v=Integer.parseInt(M[i][j].getText());											   // On récupère la valeur du champ saisi par l'utilisateur et la valeur du champ dans la grille initiale
               v1=Integer.parseInt(CopieM[i][j].getText());} 
               catch(NumberFormatException nfe){}
               if(v!=v1) M[i][j].setForeground(Color.red);								// Si la valeur est différente de la valeur initiale, on change la couleur du champ en rouge
         /*TEST*/
               if(v != v1) {
            	    M[i][j].setForeground(Color.red);
            	    erreurRestantes--;
            	    if (erreurRestantes <= 0) {
            	        int choix = JOptionPane.showOptionDialog(f, "Désolé, vous avez atteint le nombre maximum d'erreurs autorisées. Voulez-vous recommencer ou réinitialiser le jeu ?", "Fin du jeu", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Recommencer", "Réinitialiser"}, null);

            	        if (choix == JOptionPane.YES_OPTION) {
            	            recommencer();
            	            // Réinitialisez le compteur d'erreurs pour le niveau actuel
            	            switch (difficulte) {
            	                case 1:
            	                    erreurRestantes = 5; // Facile
            	                    break;
            	                case 2:
            	                    erreurRestantes = 10; // Moyen
            	                    break;
            	                case 3:
            	                    erreurRestantes = 20;  // Difficile
            	                    break;
            	                case 4:
            	                	erreurRestantes = 15; // Special
            	            }
            	        } else if (choix == JOptionPane.NO_OPTION) {
            	            reinitialiseJeu();
            	            // Réinitialisez le compteur d'erreurs en fonction du niveau actuel
            	           
            	        }
            	    }
            	}
 
         /*TEST_FIN*/    
         
               
               
         /*TEST_FIN*/              
           break;
           }
          }
      
      if(verif(M,CopieM)) JOptionPane.showMessageDialog(f,"Bravo!! vous avez gagné ");						  // Si toutes les valeurs sont correctes, on affiche un message de victoire
      
    
    }
      
    }