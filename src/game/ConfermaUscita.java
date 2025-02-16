/*
 * Questa classe viene generata in caso di click sul pulsante uscita e serve per chiedere all'utente se vuole confermare o meno l'uscita
 */
package game;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;
public class ConfermaUscita extends JPanel implements MouseListener{
    JFrame genitore;
    public ConfermaUscita(JFrame genitore){
        this.genitore=genitore;
        addMouseListener(this);
    }
    Rectangle areaSi = new Rectangle(144, 191, 100, 100);
    Rectangle areaNo = new Rectangle(579, 191, 100, 100);
    Rectangle areaCancella = new Rectangle(312, 191, 200, 100);
    
    boolean clickN=false;
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Cast a Graphics2D per accedere ai metodi aggiuntivi
        Graphics2D g2d = (Graphics2D) g;

        //Oggetti utili
        Font font3 = new Font("Inter", Font.PLAIN, 30);
        Font font4 = new Font ("Inter",Font.BOLD,40);
        Font font6 = new Font ("Inter",Font.BOLD,20);
        Color coloreBianco = new Color(0xD9D9D9);
        
        //Creazione dell'etichetta centrale
        g2d.setColor(Color.BLACK);
        g2d.setFont(font3);
        g.drawString("Sei sicuro di voler uscire?", 220,140);
        
        //Creazione dei bottoni
        g2d.setColor(coloreBianco);
        g2d.fillRoundRect(144, 191, 100, 100, 40, 40);
        g2d.fillRoundRect(579, 191, 100, 100, 40, 40);
        g2d.fillRoundRect(312, 191, 200, 100, 40, 40);
        
        
        String conferma = "Sì";
        g2d.setColor(Color.BLACK);
        g2d.setFont(font4);
        g2d.drawString(conferma, 175, 255); 
        conferma = "No";
        g2d.drawString(conferma, 605, 255);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font6);
        g2d.drawString("CANCELLA Dati", 334, 240);
        g2d.drawString("ed ESCI", 373, 266);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(areaSi.contains(e.getPoint()))
        {
            System.exit(0);
        }
        
        if(!clickN)
        {
            if(areaNo.contains(e.getPoint()))
            {
                //operazioni preliminari
                JFrame fHome = new JFrame ("Gioco di scacchi");
                fHome.setSize(800,800);
                fHome.setLocationRelativeTo(null);
                fHome.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fHome.setVisible(true);
                Container c = fHome.getContentPane();

                //settaggio colore
                Color colore = new Color(0xd9b681);

                //aggiunta oggetto Home
                Home h = new Home(fHome);
                h.setBackground(colore);
                c.add(h);
                genitore.dispose();
            }
        }
        
        if(areaCancella.contains(e.getPoint()))
        {
            try (PrintWriter fOUT = new PrintWriter(new FileWriter(Game.path + "storico.txt"))) 
            {
                fOUT.println("");
            }
            catch(IOException exc){}
            System.exit(0);
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
