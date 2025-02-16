/*
 * Questa classe dà la possibilità all'utente di inserire i nomi dei giocatori della partita e di iniziare a giocare
 */
package game;
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
import java.util.ArrayList;
public class NomiGiocatori extends JPanel implements MouseListener, KeyListener { //settaggio variabili di istanza private
    private Rectangle nomeGioc1 = new Rectangle(95, 158, 247, 37);
    private Rectangle nomeGioc2 = new Rectangle(473, 158, 247, 37);
    private Rectangle areaGioca = new Rectangle(313, 263, 182, 51);
    private String nGioc1="";
    private String nGioc2="";
    private JFrame genitore;
    private String partite ="";
    
    //booleani
    boolean enable1 = false;
    boolean enable2 = false;
    boolean clickG= false;
    boolean enableError=false;
    
    
    //costruttore dle panello
    public NomiGiocatori(JFrame genitore){
        this.addMouseListener(this);
        this.addKeyListener(this);
        setFocusable(true);
        this.genitore=genitore;
    }
    protected void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        // Cast a Graphics2D per accedere ai metodi aggiuntivi
        Graphics2D g2d = (Graphics2D) g;

        //Oggetti utili
        Font font3 = new Font("Inter", Font.PLAIN, 30);
        Font font5 = new Font ("Inter",Font.BOLD,30);
        Font font2 = new Font("Inter", Font.BOLD, 25);
        Color coloreBianco = new Color(0xD9D9D9);
        
        //Creazione dell'etichetta centrale
        g2d.setColor(Color.BLACK);
        g2d.setFont(font3);
        g.drawString("Inserisci nome giocatori", 223,85);
        
        //Aggiunta delle immagini relative ai pedoni bianco e nero
        BufferedImage bianco=null;
        BufferedImage nero=null;
        try 
        {
            bianco = ImageIO.read(new File(Game.path+"bianco.png"));
            nero = ImageIO.read(new File(Game.path+"nero.png"));
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
        if(bianco != null)
        {
            g2d.drawImage(bianco,720,144,null);
        }
        if(nero != null)
        {
            g2d.drawImage(nero,34,139,null);
        }
        
        
        //Creazione dei rettangoli contenti i nomi dei giocatori e il pulsante Gioca
        g2d.setColor(coloreBianco);
        g2d.fillRoundRect(95, 158, 247, 37, 30, 30);
        g2d.fillRoundRect(473, 158, 247, 37, 30, 30);
        g2d.fillRoundRect(313, 263, 182, 51, 30, 30);
        
        
        //Creazione delle stringhe 
        String versus = "VS";
        String gioca = "Gioca";
        g2d.setColor(Color.BLACK);
        g2d.setFont(font5);
        g2d.drawString(versus, 388, 185);
        g2d.drawString(gioca,363,300);
        
        //Creazione stringhe per i  nomi dei giocatori
        g2d.setColor(Color.BLACK);
        g2d.setFont(font2);
        g2d.drawString(nGioc1,100,185);
        g2d.drawString(nGioc2, 478, 185);
        
        //Disegno stringa di errore
        if(enableError)
        {
            g2d.drawString("I campi sono obbligatori", 250, 240);
            enableError=false;
        }
        
        //Marcatura del focus delle caselle di testo
        if(enable1)
        {
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(95, 158, 247, 37, 30, 30);
        }
        
        if(enable2)
        {
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(473, 158, 247, 37, 30, 30);
        }
        
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int tastiG = e.getKeyCode();
        if(enable1)
        {
            if(tastiG == KeyEvent.VK_BACK_SPACE)
            {
                if(nGioc1.length()>0)
                {
                    int preFine = nGioc1.length()-1;
                    nGioc1 = nGioc1.substring(0,preFine);
                    repaint();
                }
            }
            else
            {
                if(nGioc1.length()<15)
                    {
                        if (Character.isLetterOrDigit(e.getKeyChar()) || Character.isWhitespace(e.getKeyChar())) 
                        {
                            nGioc1+=e.getKeyChar();
                            repaint();
                        }
                    }
                    else
                    {
                        enable1=false;
                    }
            }
        }
        if(enable2)
        {
            if(tastiG == KeyEvent.VK_BACK_SPACE)
            {
                if(nGioc2.length()>0)
                {
                    int preFine = nGioc2.length()-1;
                    nGioc2 = nGioc2.substring(0,preFine);
                    repaint();
                }
            }
            else
            {
                if(nGioc2.length()<15)
                    {
                        if (Character.isLetterOrDigit(e.getKeyChar()) || Character.isWhitespace(e.getKeyChar())) 
                        {
                            nGioc2+=e.getKeyChar();
                            repaint(); 
                        }
                    }
                    else
                    {
                        enable2=false;
                    }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if(nomeGioc1.contains(e.getPoint()))
        {
            enable1 = true;
            enable2=false;
            repaint();
            requestFocusInWindow();
        }
        
        if(nomeGioc2.contains(e.getPoint()))
        { 
            enable2=true;
            enable1 = false;
            repaint();
            requestFocusInWindow();
        }
        
        if(areaGioca.contains(e.getPoint()))
        {
            if(!nGioc1.isEmpty()&&!nGioc2.isEmpty())
            {
                if(!clickG)
                {
                    //operazioni preliminari
                    JFrame fEngine = new JFrame ("Chess!!");
                    fEngine.setSize(1100,830);
                    fEngine.setLocationRelativeTo(null);
                    fEngine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    fEngine.setVisible(true);

                    //aggiunta oggetto Nomi GIocatori
                    GamePanel gp = new GamePanel();
                    fEngine.add(gp);
                    fEngine.pack(); 
                    gp.launchGame();
                    //Scrittura dei nomi dei giocatori all'interno del file
                    ArrayList<String> v = new ArrayList<>();
                    String giocatori=nGioc1+"/"+nGioc2;
                    try (BufferedReader fIN = new BufferedReader(new FileReader(Game.path + "storico.txt"))) 
                    {
                        //copiare ogni linea de file nel vettore dinamico
                        String line;
                        while ((line = fIN.readLine()) != null) {
                            v.add(line);
                        }
                        if (!v.isEmpty()) {
                            aggiungiPartitaSeNecessario(v, nGioc1, giocatori);
                            aggiungiPartitaSeNecessario(v, nGioc2, giocatori);
                        } else {
                            aggiungiNuovoGiocatore(v, nGioc1, giocatori);
                            aggiungiNuovoGiocatore(v, nGioc2, giocatori);
                        }

                        // Scrittura su file
                        try (PrintWriter fOUT = new PrintWriter(new FileWriter(Game.path + "storico.txt"))) {
                            for (String elemento : v) {
                                fOUT.println(elemento);
                            }
                        }
                    } catch (IOException exc){}
                    genitore.dispose();
                    clickG=true;
                }     
            }
            else
            {
                enableError=true;
                repaint();
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
    
    public static void aggiungiPartitaSeNecessario(ArrayList<String> v, String nome, String giocatori) {
        int index = v.indexOf(nome);
        if (index != -1) 
        {
            int numPartite = contaPartiteSuccessive(v, index + 1);
            if (numPartite < 4) 
            {
                aggiungiPartita(index + numPartite + 1, v, giocatori);
            } 
            else 
            {
                v.remove(index + 1);
                aggiungiPartita(index + numPartite, v, giocatori);
            }
        } 
        else 
        {
            aggiungiNuovoGiocatore(v, nome, giocatori);
        }
    }

    public static int contaPartiteSuccessive(ArrayList<String> v, int startIndex) {
        int count = 0;
        for (int i = startIndex; i < v.size(); i++) {
            if (!v.get(i).isEmpty()) 
            {
                count++;
            } 
            else 
            {
                break;
            }
        }
        return count;
    }

    public static void aggiungiPartita(int index, ArrayList<String> v, String giocatori) {
        giocatori+=";?";
        v.add(index, giocatori);
    }

    public static void aggiungiNuovoGiocatore(ArrayList<String> v, String nome, String giocatori) {
        v.add(nome);
        giocatori+=";?";
        v.add(giocatori);
        v.add("");
    }
    
    public static int trovaIndiceNome(String nome, ArrayList <String> v)
    {
        return v.indexOf(nome);
    }
    
    public static int trovaIndicePartitaNonConclusa(int startIndex, ArrayList <String> v){
        int i=0;
        for(i=startIndex;i<v.size();i++)
        {
            if(v.get(i).indexOf('?')!=-1)
            {
                return i;
            }
        }
        return i;
    }
}
