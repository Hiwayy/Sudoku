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


public class Sudoku implements KeyListener {
    public static JFrame f = new JFrame("Sudoku");
    public static JPanel p = new JPanel();
    public static JTextField[][] M = new JTextField[9][9];
    public static JTextField[][] CopieM = new JTextField[9][9];
    JMenuBar mb = new JMenuBar();
    JMenu menu = new JMenu("niveau");
    JMenuItem n1 = new JMenuItem("facile");
    JMenuItem n2 = new JMenuItem("Moyen");
    JMenuItem n3 = new JMenuItem("dificile");
    Font font = new Font("Lucida Console", Font.BOLD,28);
    
    public static void colorier(JTextField[][]M){
        int x,y;
        for (x = 0;x<9;x++){
            for (y = 0;y<9;y++){
             switch(x){
                 case 0:case 1:case 2:case 6: case 7: case 8:{
                 switch(y){
                     case 0:case 1:case 2: case 6:case 7:case 8: M[x][y].setBackground(Color.green);break;                            
                 }
             break;}  
                 case 3:case 4:case 5:{
                 switch(y){
                     case 3:case 4:case 5: M[x][y].setBackground(Color.green);break;                            
                 }
             break;}  
            }
        }
    }
    }
    
    public static void initialiser(JTextField[][]K){
    int i;int j;int ligne;int colonne;int v;
    for (i=0;i<9;i++){K[0][i].setText(Integer.toString(i+1));}
    for (j=9;j<81;j++){
        ligne=j/9;
        colonne=j%9;
        if(ligne%3==0) v=1; else v=0;
        K[ligne][colonne].setText(Integer.toString((Integer.parseInt(K[ligne-1][colonne].getText())+(2+v))%9+1));
          }
    }
    
    public static void permuterLigne(JTextField[][]K,int L1, int L2){
    int aux;
    for (int i=0;i<9;i++){
        aux = Integer.parseInt(K[L2][i].getText());
        K[L2][i].setText(K[L1][i].getText());
        K[L1][i].setText(Integer.toString(aux));
        }
    }
    
    public static void permuterColonne(JTextField[][]K,int C1, int C2){
    int aux;
    for (int i=0;i<9;i++){
        aux = Integer.parseInt(K[i][C2].getText());
        K[i][C2].setText(K[i][C1].getText());
        K[i][C1].setText(Integer.toString(aux));
        }
    }
    
    public static boolean exisiteEnLigne(JTextField[][]K,int x, int y, int i){
        if((K[x][i].getText().equals(K[y][0].getText()))||
                (K[x][i].getText().equals(K[y][1].getText()))||
                (K[x][i].getText().equals(K[y][2].getText()))) return true;
        return false;                
    }
    
     public static boolean exisiteEnColonne(JTextField[][]K,int x, int y, int i){
        if((K[i][x].getText().equals(K[0][y].getText()))||
                (K[i][x].getText().equals(K[1][y].getText()))||
                (K[i][x].getText().equals(K[2][y].getText()))) return true;
        return false;                
    }
     
     public static void effacer(JTextField[][]K,int x){
         int c;int col;
         for (int i=0;i<9;i++){
             c=0;
                 int v =(int)(x*random())+3;//v represente le nombre de cases à effacer dans la ligne i
                 while(c<v){
                     col=(int)(9.0*random());
                 if (!(K[i][col].getText().equals(""))) {
                     K[i][col].setText("0");
                     c++;
                 }
             }
         }
         verouiller(M);
     }
  
     public static void verouiller(JTextField[][]K){
         int i,j;
         for (i=0;i<9;i++){
           for (j=0;j<9;j++) {
             if (K[i][j].getText().equals("0")){
                 K[i][j].setText("");
                 K[i][j].setEditable(true);
             }  
            else K[i][j].setEditable(false);
           }         
     }
     }
     
     public static void Copier(JTextField [][]K, JTextField[][]L){
      int i,j;
         for (i=0;i<9;i++){
           for (j=0;j<9;j++) {
               try{
               L[i][j].setText(K[i][j].getText());
               } 
               catch(NullPointerException e){}
           }   }
     }
     
     public static void init(JTextField[][]K){
      int i,j;
         for (i=0;i<9;i++){
           for (j=0;j<9;j++) {
               K[i][j]=new JTextField();
               try{
           K[i][j].setText("0");  }
           catch(NullPointerException e){}
           } }  
     }
     
     public static void generer(JTextField[][]M, int x, int y){
         int cl=0;int cc=0;
         initialiser(M);
      do{
      int L1 = (int)(9.0*random());
      int L2 = (int)(9.0*random());
      int C1 = (int)(9.0*random());
      int C2 = (int)(9.0*random());
      if ((L1/3==L2/3)&&(L1!=L2)||((exisiteEnLigne(M,L1,L2,0))&&((exisiteEnLigne(M,L1,L2,1)) &&
             ((exisiteEnLigne(M,L1,L2,2))))))
      {
          permuterLigne(M,L1,L2);cl++;
      }
      if ((C1/3==C2/3)&&(C1!=C2)||((exisiteEnColonne(M,C1,C2,0))&&((exisiteEnColonne(M,C1,C2,1)) &&
              ((exisiteEnColonne(M,C1,C2,2)))))){
          permuterColonne(M,C1,C2);cc++;
      }
      }
      while((cl<=x)||(cc<=x));
      init(CopieM);
      Copier(M,CopieM);
      effacer(M,y);
      p.setVisible(true);
     }
     
     public void ValiderLigne(JTextField[][]K, JTextField[][]L, int x){
         boolean valide = true; int i;
         for (i=0; i<9;i++){
             if(!(K[x][i].getText().equals(L[x][i].getText()))) valide = false;
         }
         if(valide==true){
         for ( i=0; i<9;i++){    
         K[x][i].setBackground(Color.orange);
         K[x][i].setEditable(false);
         }
     }
     }
     
     public void ValiderColonne(JTextField[][]K, JTextField[][]L, int x){
         boolean valide = true; int i;
         for (i=0; i<9;i++){
             if(!(K[i][x].getText().equals(L[i][x].getText()))) valide = false;
         }
         if(valide==true){
         for ( i=0; i<9;i++){    
         K[i][x].setBackground(Color.orange);
         K[i][x].setEditable(false);
         }
     }
     }
     
     public static void ValiderCarre3X3(JTextField[][]K,JTextField[][]L,int line,int col){
         int l=0;int c =0;boolean valide=true;int ligne, colonne;
      switch(line){
          case 0:case 1: case 2:{
              switch (col){
                  case 0:case 1: case 2:{ l=0;c=0;break;}
                     case 3:case 4: case 5: { l=0;c=3;break;}
                         case 6:case 7: case 8:{ l=0;c=6;break;} 
              }break;
          }
          case 3:case 4: case 5:{
              switch (col){
                  case 0:case 1: case 2:{ l=3;c=0;break;}
                     case 3:case 4: case 5: { l=3;c=3;break;}
                         case 6:case 7: case 8:{ l=3;c=6;break;} 
              }break;
          }
          case 6:case 7: case 8:{
              switch (col){
                  case 0:case 1: case 2:{ l=6;c=0;break;}
                     case 3:case 4: case 5: { l=3;c=3;break;}
                         case 6:case 7: case 8:{ l=6;c=6;break;} 
              }break;
          }
      } 
      for (int i = 0; i<9;i++){
          ligne=(i/3)+l;
          colonne = (i%3)+c;
          if(!(K[ligne][colonne].getText().equals(L[ligne][colonne].getText())))
              valide= false;
      }
      if (valide==true){
          for (int i=0;i<9;i++){
           ligne=(i/3)+l;
           colonne = (i%3)+c;  
           K[ligne][colonne].setBackground(Color.orange);
            K[ligne][colonne].setEditable(false);
      }
     }
     }
     
     public static boolean verif(JTextField[][]K,JTextField[][]L){
         for (int i =0;i<9;i++){
            for (int j =0;j<9;j++){
                if(!(K[i][j].getText().equals(L[i][j].getText()))) return false;
            }  
         }
         return true;
     }
    Sudoku(){
      f.setSize(600,600);
      menu.add(n1);menu.add(n2);menu.add(n3);
      mb.add(menu);
      f.setJMenuBar(mb);
      p.setVisible(false);
      p.setLayout(new GridLayout(9,9));
      for (int i = 0; i<9;i++){
          for (int j = 0; j<9;j++){
              M[i][j]= new JTextField();
              M[i][j].setFont(font);
              M[i][j].setForeground(Color.black);
              M[i][j].setHorizontalAlignment(SwingConstants.CENTER);
              M[i][j].addKeyListener(this);
              p.add(M[i][j]);
          }
      }
      f.setContentPane(p);
      p.setVisible(false);
      f.setVisible(true);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setResizable(false);
      
      
       
      
      n1.addActionListener(new ActionListener(){
     @Override
    public void actionPerformed(ActionEvent e) {
     colorier(M);
     generer(M,10,3);
      
    }   
    });
      
      n2.addActionListener(new ActionListener(){
     @Override
    public void actionPerformed(ActionEvent e) {
     colorier(M);
     generer(M,20,5);  
    }   
    });
      
      n3.addActionListener(new ActionListener(){
     @Override
    public void actionPerformed(ActionEvent e) {
     colorier(M);
     generer(M,30,7);  
    }   
    });
    }
    
    
    

public static void main(String[]args){
new Sudoku();
}

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int v=0;int v1=0;
      JTextField tf = (JTextField) e.getSource();
      tf.setFont(new Font("Arial Black",Font.BOLD,28));
      tf.setForeground(Color.BLUE);
      String text = tf.getText();
      if((!text.equals("1"))&&(!text.equals("2"))&&(!text.equals("3"))&&(!text.equals("4"))&&
              (!text.equals("5"))&&(!text.equals("6"))&&(!text.equals("7"))&&(!text.equals("8"))&&
              (!text.equals("9")))
          tf.setText("");
      for (int i=0; i<M.length;i++)
          for (int j=0; j<M[i].length;j++){
           if(M[i][j]==tf ||M[i][j].equals(tf)){
               try{
                   ValiderLigne(M,CopieM,i);
                   ValiderColonne(M,CopieM,j);
                   ValiderCarre3X3(M,CopieM,i,j);
               v=Integer.parseInt(M[i][j].getText());
               v1=Integer.parseInt(CopieM[i][j].getText());} 
               catch(NumberFormatException nfe){}
               if(v!=v1) M[i][j].setForeground(Color.red);
               
             
           break;
           }
          }
      if(verif(M,CopieM)) JOptionPane.showMessageDialog(f,"Bravo!! vous avez gagné ");
      }
      
    }
