/*
 * Questa classe implementa la home-page del gioco
 */
package game;
import javax.swing.*;
import java.awt.*;
public class Game {
    public static String path = "..\\Chess\\src\\res\\";
    public static void main(String[] args) {
        //Operazioni Preliminari
        JFrame finestra = new JFrame ("Gioco di scacchi");
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = finestra.getContentPane();
        finestra.setSize(800,800);
        finestra.setLocationRelativeTo(null);
        finestra.setVisible(true);
        
        //Settaggio del colore
        Color colore = new Color(0xd9b681);
        
        //Aggiunta della homePage del gioco
        Home h = new Home (finestra);
        h.setBackground(colore);
        c.add(h); 
    }
    
}
