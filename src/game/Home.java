/*
 * Questa classe rappresenta la home del gioco e dà la possibilità all'utente di cliccare su 3 pulsanti
 */
package game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class Home extends JPanel implements MouseListener{
    Rectangle areaGioca = new Rectangle(230, 423, 341, 76);
    Rectangle areaProfilo = new Rectangle(230, 536, 341, 76);
    Rectangle areaEsci = new Rectangle(230, 649, 341, 76);
    
    //valori booleani
    boolean blGioca = false;
    boolean blProfilo = false;
    boolean clickG = false, clickP =false,clickE=false;
    
    JFrame genitore;
    
    public Home(JFrame genitore){
        this.genitore=genitore;
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        addMouseListener(this);
        // Cast a Graphics2D per accedere ai metodi aggiuntivi
        Graphics2D g2d = (Graphics2D) g;
        
        
        //Oggetti utili
        Font font = new Font("Inter", Font.BOLD, 35);
        Color coloreBianco = new Color(0xD9D9D9);
        
        // Creazione dei tre pulsanti Gioca, Profilo ed Esci 
        g2d.setColor(coloreBianco);
        g2d.fillRoundRect(230, 423, 341, 76, 30, 30);
        g2d.fillRoundRect(230, 536, 341, 76, 30, 30);
        g2d.fillRoundRect(230, 649, 341, 76, 30, 30);
        
        String gioca = "Gioca";
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g.drawString(gioca, 350, 473); 
        
        String profilo = "Profilo";
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g.drawString(profilo, 340, 586); 
        
        String esci = "Esci";
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(esci, 360, 699); 
        
        //processo di caricamneto dell'immagine e disegno immagine logo
        BufferedImage logo=null;
        try 
        {
            logo = ImageIO.read(new File(Game.path+"logo.png"));
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
        if(logo != null)
        {
            g2d.drawImage(logo,94,74,null);
        }     
    }  


    @Override
    public void mouseClicked(MouseEvent e) {
        if(areaGioca.contains(e.getPoint()))
        {
            if(!clickG)
            {
                //operazioni preliminari
                JFrame fGioco = new JFrame ("Inserimento Nomi Giocatori");
                fGioco.setSize(800,400);
                fGioco.setLocationRelativeTo(null);
                fGioco.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fGioco.setVisible(true);
                Container c = fGioco.getContentPane();
                
                //settaggio colore
                Color colore = new Color(0xd9b681);
                
                //aggiunta oggetto Nomi GIocatori
                NomiGiocatori ng = new NomiGiocatori(fGioco);
                ng.setBackground(colore);
                c.add(ng);
                genitore.dispose();
                clickG=true;
            }
        }
        else if(areaProfilo.contains(e.getPoint()))
        {
            if(!clickP)
            {
                //operazioni preliminari
                JFrame fProfilo = new JFrame ("Archivio partite");
                fProfilo.setSize(800,800);
                fProfilo.setLocationRelativeTo(null);
                fProfilo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fProfilo.setVisible(true);
                Container c = fProfilo.getContentPane();
                
                //settaggio colore
                Color colore = new Color(0xd9b681);
                
                //aggiunta oggetto Nomi GIocatori
                Profilo p = new Profilo(fProfilo);
                p.setBackground(colore);
                c.add(p);
                genitore.dispose();
                clickP=true;
            }    
        }
        else if(areaEsci.contains(e.getPoint()))
        {
            if(!clickE)
            {
                //operazioni preliminari
                JFrame fUscita = new JFrame ("Conferma Uscita");
                fUscita.setSize(800,400);
                fUscita.setLocationRelativeTo(null);
                fUscita.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fUscita.setVisible(true);
                Container c = fUscita.getContentPane();
                
                //settaggio colore
                Color colore = new Color(0xd9b681);
                
                //aggiunta oggetto Nomi GIocatori
                ConfermaUscita cu = new ConfermaUscita(fUscita);
                cu.setBackground(colore);
                c.add(cu);
                genitore.dispose();
                clickE=true;
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}