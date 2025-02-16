/*
 * Questa classe dà la possibilità all'utente di interrogare l'archivio presente nel file di tipo testauale storico.txt
 */
package game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
public class Profilo extends JPanel implements MouseListener, KeyListener{
    Rectangle areaBack = new Rectangle(0,21,81,67);
    Rectangle areaNome = new Rectangle(231, 305, 340, 37);
    JFrame genitore;
    String nome ="";
    String inesistente="";
    String partite="";
    //booleani
    boolean enableR = false;
    boolean enableRap=false;
    public Profilo(JFrame genitore)
    {
        this.genitore=genitore;
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        // Cast a Graphics2D per accedere ai metodi aggiuntivi
        Graphics2D g2d = (Graphics2D) g;

        //Oggetti utili
        Font font1 = new Font("Inter", Font.BOLD, 60);
        Font font2 = new Font("Inter", Font.BOLD, 25);
        Color coloreBianco = new Color(0xD9D9D9);
        
        //processo di caricamneto dell'immagine e disegno immagine freccia indetro
        BufferedImage back=null;
        BufferedImage profilo = null;
        BufferedImage vinto = null;
        BufferedImage perso = null;
        BufferedImage patta = null;
        try 
        {
            back = ImageIO.read(new File(Game.path+"back.png"));
            profilo = ImageIO.read(new File(Game.path+"profilo.png"));
            vinto = ImageIO.read(new File(Game.path+"vinto.png"));
            perso = ImageIO.read(new File(Game.path+"perso.png"));
            patta = ImageIO.read(new File(Game.path+"patta.png"));
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
        if(back != null)
        {
            g2d.drawImage(back,0,21,null);
        }
        
        //disegno scritta profilo
        g2d.setColor(Color.BLACK);
        g2d.setFont(font1);
        g.drawString("PROFILO", 279, 90); 
        
        //disegno immagine profilo
        if(profilo != null)
        {
            g2d.drawImage(profilo,179,88,null);
        }
        
        //Creazione della casella di ricerca del profilo
        g2d.setColor(coloreBianco);
        g2d.fillRoundRect(231, 305, 340, 37, 30, 30);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font2);
        g2d.drawString(nome, 236, 332);
        if(enableR)
        {
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(231, 305, 340, 37, 30, 30);
        }
        
        //Creazione della barra di divisione
        g2d.setColor(Color.BLACK);
        g2d.fillRoundRect(10, 372, 767, 5, 2, 2);
        
        //variabili per la creazione delle partite
        final int INC = 80;
        int xStato = 21;
        int xGiocatori = 262;
        int xNumero = 734;
        int xBarra = 21;
        
        int yStato = 377;
        int yGiocatori = 425;
        int yNumero = 425;
        int yBarra=452;
        int yND = yStato+45;
        
        
        //Creazioni partite
        if(enableRap)
        {
            if(inesistente.equals("Utente non trovato"))
            {
                g2d.setColor(Color.BLACK);
                g2d.setFont(font2);
                g2d.drawString(inesistente, 272, 425);
            }
            else
            {
                try
                {
                    String[] singole = partite.split(";;");
                    for(int i=0; i<singole.length;i++)
                    {
                        int primoPV = singole[i].indexOf(";");
                        String players = singole[i].substring(0,primoPV);
                        char stato = singole[i].charAt(singole[i].length()-1);
                        g2d.setColor(Color.BLACK);
                        g2d.setFont(font2);
                        g.drawString(players, xGiocatori, yGiocatori);
                        g2d.setColor(Color.BLACK);
                        g2d.setFont(font2);
                        g.drawString(Integer.toString(i+1), xNumero, yNumero);
                        switch (stato) {
                            case 'V':
                                if(vinto != null)
                                {
                                    g2d.drawImage(vinto,xStato,yStato,null);
                                }   break;
                            case 'P':
                                if(patta != null)
                                {
                                    g2d.drawImage(patta,xStato,yStato,null);
                                }   break;
                            case 'S':
                                if(perso != null)
                                {
                                    g2d.drawImage(perso,xStato,yStato,null);
                                }   break;
                            default:
                                g2d.drawString("ND",xStato,yND);
                                break;
                        }
                        g2d.setColor(coloreBianco);
                        g2d.fillRect(xBarra, yBarra, 751, 1);
                        yGiocatori+=INC;
                        yStato+=INC;
                        yND+=INC;
                        yNumero+=INC;
                        yBarra+=INC;
                    }
                }
                catch(StringIndexOutOfBoundsException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        
        //Creazione archivio partite
        
        
//        String giocatori;
//        String numero;
//
//        //Creazione partita vinta
//        if(vinto != null)
//        {
//            g2d.drawImage(vinto,xStato,yStato,null);
//        }
//        giocatori="Salvatore/Alessandro";
//        g2d.setColor(Color.BLACK);
//        g2d.setFont(font2);
//        g.drawString(giocatori, xGiocatori, yGiocatori);
//
//        numero ="1";
//        g2d.setColor(Color.BLACK);
//        g2d.setFont(font2);
//        g.drawString(numero, xNumero, yNumero);
//
//        g2d.setColor(coloreBianco);
//        g2d.fillRect(xBarra, yBarra, 751, 1);
//
//        //Creazione partita persa
//        yStato += INC;
//        yGiocatori += INC;
//        yNumero += INC;
//        yBarra += INC;
//        if(perso != null)
//        {
//            g2d.drawImage(perso,xStato-16,yStato,null);
//        }
//        giocatori="Salvatore/Alessandro";
//        g2d.setColor(Color.BLACK);
//        g2d.setFont(font2);
//        g.drawString(giocatori, xGiocatori, yGiocatori);
//
//        numero ="2";
//        g2d.setColor(Color.BLACK);
//        g2d.setFont(font2);
//        g.drawString(numero, xNumero, yNumero);
//
//        g2d.setColor(coloreBianco);
//        g2d.fillRect(xBarra, yBarra, 751, 1);
//
//
//
//        //Creazione partita patta
//        yStato += INC;
//        yGiocatori += INC;
//        yNumero += INC;
//        yBarra += INC;
//        if(patta != null)
//        {
//            g2d.drawImage(patta,xStato,yStato,null);
//        }
//        giocatori="Salvatore/Alessandro";
//        g2d.setColor(Color.BLACK);
//        g2d.setFont(font2);
//        g.drawString(giocatori, xGiocatori, yGiocatori);
//
//        numero ="3";
//        g2d.setColor(Color.BLACK);
//        g2d.setFont(font2);
//        g.drawString(numero, xNumero, yNumero);
//
//        g2d.setColor(coloreBianco);
//        g2d.fillRect(xBarra, yBarra, 751, 1);
//
//        //prova partita vinta
//        yStato += INC;
//        yGiocatori += INC;
//        yNumero += INC;
//        yBarra += INC;
//        if(vinto != null)
//        {
//            g2d.drawImage(vinto,xStato,yStato,null);
//        }
//        giocatori="Salvatore/Alessandro";
//        g2d.setColor(Color.BLACK);
//        g2d.setFont(font2);
//        g.drawString(giocatori, xGiocatori, yGiocatori);
//
//        numero ="4";
//        g2d.setColor(Color.BLACK);
//        g2d.setFont(font2);
//        g.drawString(numero, xNumero, yNumero);
//
//        g2d.setColor(coloreBianco);
//        g2d.fillRect(xBarra, yBarra, 751, 1);
//
//        //prova2 paritita patta
//        if(vinto != null)
//        {
//            g2d.drawImage(vinto,xStato,yStato,null);
//        }
//        giocatori="Salvatore/Alessandro";
//        g2d.setColor(Color.BLACK);
//        g2d.setFont(font2);
//        g.drawString(giocatori, xGiocatori, yGiocatori);
        
    } 

    @Override
    public void mouseClicked(MouseEvent e) {
        if(areaBack.contains(e.getPoint()))
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

            //aggiunta oggetto Nomi GIocatori
            Home h = new Home(fHome);
            h.setBackground(colore);
            c.add(h);
            genitore.dispose();
        }
        
        if(areaNome.contains(e.getPoint()))
        {
            enableR = true;
            repaint();
            requestFocusInWindow();
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

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(enableR)
        {
            int tastiG = e.getKeyCode();
            if(tastiG == KeyEvent.VK_BACK_SPACE)
            {
                if(nome.length()>0)
                {
                    int preFine = nome.length()-1;
                    nome = nome.substring(0,preFine);
                    repaint();
                }
            }
            else if(nome.length()>0&&tastiG==KeyEvent.VK_ENTER)
            {
                enableRap=false;
                partite="";
                inesistente="";
                try
                {
                    //Apertura del file in lettura
                    FileReader f = new FileReader(Game.path+"storico.txt");
                    BufferedReader fIN = new BufferedReader(f);
                    
                    String s = fIN.readLine();
                    while(s!=null)
                    {
                        if(s.equals(nome)==true)
                        {
                            String stringa = fIN.readLine();
                            try
                            {
                                while(stringa.equals("")==false)
                                {
                                    partite+=stringa+";;";
                                    stringa=fIN.readLine();
                                }
                            }
                            catch(NullPointerException exv)
                            {
                                System.out.println(exv.getMessage());
                            }
                            
                        }
                        s=fIN.readLine();
                    }
                    
                    if(partite.equals("")==true)
                    {
                        inesistente="Utente non trovato";
                    }
                    enableRap = true;
                    repaint();
                    //Ogni volte che rappresento utte le partite a schermo devo svuotare la stringa partite
                }
                catch(IOException exc)
                {
                    System.out.println(exc.getMessage());
                }
                
                
            }
            else
            {
                if(nome.length()<21)
                    {
                        if (Character.isLetterOrDigit(e.getKeyChar()) || Character.isWhitespace(e.getKeyChar())) 
                        {
                            nome+=e.getKeyChar();
                            repaint();
                        }
                    }
                    else 
                    {
                        enableR=false;
                        repaint();
                    }
            }
        }
    }
    
    

    @Override
    public void keyReleased(KeyEvent e) {}
    
    
    
}