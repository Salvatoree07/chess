/*
 * Questa classe contiene tutti i metodi per il funzionamento del gioco 
 */
package game;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import piece.*;
public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    Thread gameThread;
    Board b = new Board();
    Mouse m = new Mouse();
    
    
    //colore
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;
    
    
    //insieme pezzi scacchi
    public static ArrayList <Pezzo> pieces= new ArrayList <Pezzo>();
    public static ArrayList <Pezzo> simpieces= new ArrayList <Pezzo>();
    ArrayList  <Pezzo> promoPieces = new ArrayList <>(); 
    Pezzo activeP;
    Pezzo checkingP;
    public static Pezzo castlingP;
    
    
    //booleani
    boolean canMove;
    boolean validSquare;
    boolean promotion;
    boolean gameover;
    boolean stalemate;
    boolean can;
    
    public GamePanel(){
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.BLACK);
        
        addMouseMotionListener(m);
        addMouseListener(m);
        
        setPieces();
        copyPieces(pieces, simpieces);
    }
    
    public void launchGame(){
        gameThread = new  Thread(this);
        gameThread.start();
    }
    
    public void testPromozione(){
        pieces.add(new Pedone(WHITE,0,4));
        pieces.add(new Pedone(BLACK,6,6));
        
    }
    
    public void testStallo(){
        pieces.add(new Re(BLACK,0,3));
        pieces.add(new Regina(WHITE,2,1));
        pieces.add(new Re(WHITE,2,4));
    }
    public void testMosseIllegali(){
        pieces.add(new Torre(WHITE, 7,6));
        pieces.add(new Re(WHITE,3,7));
        pieces.add(new Re(BLACK,0,3));
        pieces.add(new Alfiere(BLACK,1,4));
        pieces.add(new Alfiere(BLACK,2,4));
        pieces.add(new Regina(BLACK,4,5));
        pieces.add(new Pedone(WHITE,2,3));
    }
    
    public void testBug(){
        pieces.add(new Re(BLACK,0,3));
        pieces.add(new Regina(WHITE,2,1));
        pieces.add(new Re(WHITE,2,4));
    }
    
    //metodo che posiziona i pedoni sulla scacchiera all'inizio del gioco
    public void setPieces(){
        
        //White team
        pieces.add(new Pedone(WHITE,0,6));
        pieces.add(new Pedone(WHITE,1,6));
        pieces.add(new Pedone(WHITE,2,6));
        pieces.add(new Pedone(WHITE,3,6));
        pieces.add(new Pedone(WHITE,4,6));
        pieces.add(new Pedone(WHITE,5,6));
        pieces.add(new Pedone(WHITE,6,6));
        pieces.add(new Pedone(WHITE,7,6));
        pieces.add(new Cavallo(WHITE,1,7));
        pieces.add(new Cavallo(WHITE,6,7));
        pieces.add(new Torre(WHITE,7,7));
        pieces.add(new Torre(WHITE,0,7));
        pieces.add(new Alfiere(WHITE,2,7));
        pieces.add(new Alfiere(WHITE,5,7));
        pieces.add(new Regina(WHITE,3,7));
        pieces.add(new Re(WHITE,4,7));
        
        //Black team
        pieces.add(new Pedone(BLACK,0,1));
        pieces.add(new Pedone(BLACK,1,1));
        pieces.add(new Pedone(BLACK,2,1));
        pieces.add(new Pedone(BLACK,3,1));
        pieces.add(new Pedone(BLACK,4,1));
        pieces.add(new Pedone(BLACK,5,1));
        pieces.add(new Pedone(BLACK,6,1));
        pieces.add(new Pedone(BLACK,7,1));
        pieces.add(new Cavallo(BLACK,1,0));
        pieces.add(new Cavallo(BLACK,6,0));
        pieces.add(new Torre(BLACK,7,0));
        pieces.add(new Torre(BLACK,0,0));
        pieces.add(new Alfiere(BLACK,2,0));
        pieces.add(new Alfiere(BLACK,5,0));
        pieces.add(new Regina(BLACK,3,0));
        pieces.add(new Re(BLACK,4,0));
    }
    
    public void copyPieces(ArrayList <Pezzo> source, ArrayList <Pezzo> target){
        target.clear();
        for(int i=0; i<source.size();i++)
        {
            target.add(source.get(i));
        }
    }
    
    private boolean canPromote(){
        if(activeP.ID == 4)
        {
            if(currentColor == WHITE && activeP.row == 0 || currentColor == BLACK && activeP.row==7)
            {
                promoPieces.clear();
                promoPieces.add(new Torre(currentColor,9,2));
                promoPieces.add(new Cavallo (currentColor,9,3));
                promoPieces.add(new Alfiere(currentColor,9,4));
                promoPieces.add(new Regina(currentColor,9,5));
                return true; 
            }
        }
        return false;
    }
    
    
        
    @Override
    public void run() {
        double intervalloDisegno = 1000000000/FPS;
        double delta=0;
        long ultimoIstante = System.nanoTime();
        long tempoCorrente;
        
        while(gameThread != null)
        {
            tempoCorrente = System.nanoTime();
            delta+=(tempoCorrente - ultimoIstante)/intervalloDisegno;
            ultimoIstante = tempoCorrente;
            
            if(delta>= 1)
            {
                aggiorna();
                repaint();
                delta--;
            }
        }
    }
    
    private void aggiorna(){
        if(promotion)
        {
            promoting();
        }
        else if(gameover==false&&stalemate==false)
        {
            if(m.pressed)
            {
                if(activeP ==null)
                {
                    for(Pezzo piece: simpieces)
                    {
                        //se il pedone non è cliccato e il mouse si trova sul pedone allora trasfroma ul pedone in uno attivo
                        if(piece.color==currentColor && piece.col == m.x/Board.dim&&piece.row==m.y/Board.dim)
                            activeP=piece;
                    }
                }
                else
                {
                    //se l'utente sta puntanto il mouse su un pedone
                    simulate();
                }
            }
            else
            {
                if(activeP != null)
                {
                    if(validSquare)
                    {
                        //mossa confermata
                        
                        //La mossa è valida quindi copia le informazione da quello aggiornato a qullo precedente
                        copyPieces(simpieces, pieces);
                        activeP.updatePosition();
                        if(castlingP!=null)
                        {
                            castlingP.updatePosition();
                        }
                        if(isKingInCheck()&&isCheckmate())
                        {
                            //cose da fare per la possibile perdita
                            gameover=true;
                        }
                        else if(isStalemate()&&isKingInCheck()==false)
                        {
                            stalemate=true;
                        }
                        else //il gioco può andare avanti
                        {
                            if(canPromote())
                            {
                                promotion = true;
                            }
                            else
                            {
                               changePlayer();
                            }
                        }
                        
                        
                    }
                    else
                    {
                        //La mossa non è valita quindi copia le informazioni da quello precedente a quello aggiornato
                        copyPieces(pieces, simpieces);
                        activeP.resetPosition();
                        activeP=null;
                    }
                }
            }
        }
    }
            
    private void promoting(){
        if(m.pressed)
        {
            try
            {
                simpieces.remove(activeP.getIndex());
                for (Pezzo piece : promoPieces) 
                {
                    if (piece.col == m.x / Board.dim && piece.row == m.y / Board.dim) 
                    {
                    // Aggiungi il pezzo promosso corrente
                        switch(piece.ID)
                        {
                            case 1:
                                piece=new Alfiere(currentColor, activeP.col,activeP.row);
                                break;
                            case 6:
                                piece=new Torre(currentColor,activeP.col,activeP.row);
                                break;
                            case 3:
                                piece= new Cavallo(currentColor, activeP.col,activeP.row);
                                break;
                            case 5:
                                piece=new Regina (currentColor, activeP.col,activeP.row);
                                break;
                        }
                        simpieces.add(piece);
                    }
                }
                copyPieces(simpieces,pieces);
                activeP=null;
                promotion = false;
                changePlayer();
            }
            catch(NullPointerException e)
            {
                System.out.println(e.getMessage());
            }
        }
                
    }
    private void simulate()
    {
        canMove = false;
        validSquare = false;
        
        //copia delle informazioni necessaria tra  vari pezzi
        copyPieces (pieces, simpieces);
        
        //riprisitno sitazione ormale dopo arrocco
        if(castlingP!=null)
        {
            castlingP.col=castlingP.preCol;
            castlingP.x=castlingP.getX(castlingP.col);
            castlingP=null;
        }
        activeP.x = m.x-b.mDim;
        activeP.y = m.y-b.mDim;
        activeP.col=activeP.getCol(activeP.x);
        activeP.row=activeP.getRow(activeP.y);
        
        //controlla se è possibile il movimento
        if(activeP.canMove(activeP.col, activeP.row))
        {
            canMove=true;
            //in caso di cattura di un pezzo rimuovilo dalla tastiera
            if(activeP.hittingP!=null)
            {
                simpieces.remove(activeP.hittingP.getIndex());
            }
            
            checkCastling();
            
            if(!isIllegal(activeP) && !opponentCanCaptureKing())
            {
                validSquare=true;
            }
        }
    }
    
    private boolean isIllegal(Pezzo king){
        if(king.ID==2)
        {
            for(Pezzo piece:simpieces)
            {
                if(piece!=king && piece.color != king.color && piece.canMove(king.col, king.row))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean opponentCanCaptureKing(){
        Pezzo king = getKing(false);
        for(Pezzo piece : simpieces)
        {
            if(piece.color != king.color&& piece.canMove(king.col, king.row))
            {
                int rDif = Math.abs(king.row-piece.row);
                int cDif = Math.abs(king.col-piece.col);
                if(rDif==cDif)
                {
                    int rowDiff = king.row - piece.preRow;
                    int colDiff = king.col - piece.preCol;

                    // Calcolo la direzione della diagonale
                    int rowDirection = rowDiff > 0 ? 1 : -1;
                    int colDirection = colDiff > 0 ? 1 : -1;

                    // Controllo della diagonale
                    for (int c = piece.preCol + colDirection, r = piece.preRow + rowDirection;
                            c != king.col && r != king.row;
                            c += colDirection, r += rowDirection) 
                    {
                        for (Pezzo piecee : simpieces) 
                        {
                            if (piecee.col == c && piecee.row == r) 
                            {
                                return false;
                            }
                        }
                    }
                    return true;
                }
                else
                {
                    return true;
                }
                
            }
            
        }
        return false;
        
    }
    
    
    private boolean isKingInCheck(){
        Pezzo king = getKing(true);
        if(activeP.canMove(king.col, king.row))
        {
            checkingP=activeP;
            return true;
        }
        else
        {
            checkingP=null;
        }
        return false;
    }
    
    private Pezzo getKing(boolean opponent){
        Pezzo king = null;
        for(Pezzo piece: simpieces)
        {
            if(opponent)
            {
                if(piece.ID==2&&piece.color!=currentColor)
                {
                    king=piece;
                }
            }
            else
            {
                if(piece.ID == 2 &&piece.color==currentColor)
                {
                    king=piece;
                }
            }
        }
        return king;
    }
    
    private boolean isCheckmate(){
        Pezzo king = getKing(true);
        if(kingCanMove(king))
        {
            return false;
        }
        else
        {
            //Ma hai ancora un'oppurtunità cioà qualche pezzo che blocca il re dall'attacco
            
            //Controlla la posizione del pezzo che dà scacco e del re
            int colDiff = Math.abs(checkingP.col-king.col);
            int rowDiff= Math.abs(checkingP.row-king.row);
            
            if(colDiff ==0)
            {
                //il pezzo sta attaccando verticalemnte
                if(checkingP.row<king.row)
                {
                    //il pezzo che dà scacco è sopra il re
                    for(int row = checkingP.row; row<king.row; row++)
                    {
                        for(Pezzo piece:simpieces)
                        {
                            if(piece!=king &&piece.color!=currentColor&&piece.canMove(checkingP.col,row))
                            {
                                return false;
                            }
                        }
                    }
                }
                if(checkingP.row>king.row)
                {
                    //Il pezzo che dà scacco è sotto il re
                    for(int row = checkingP.row; row>king.row; row--)
                    {
                        for(Pezzo piece:simpieces)
                        {
                            if(piece!=king &&piece.color!=currentColor&&piece.canMove(checkingP.col,row))
                            {
                                return false;
                            }
                        }
                    }
                }
                
            }
            else if(rowDiff==0)
            {
                //Il pezzo sta attaccando orizzontalemnte
                if(checkingP.col<king.col)
                {
                    //il pezzo sta attaccando da sinistra
                    for(int col= checkingP.col;col<king.col;col++)
                    {
                        for(Pezzo piece:simpieces)
                        {
                            if(piece != king && piece.color != currentColor && piece.canMove(col,checkingP.row))
                                return false;
                        }
                    }
                }
                
                if(checkingP.col>king.col)
                {
                    //il pezzo sta attaccando da destra
                    for(int col= checkingP.col;col>king.col;col--)
                    {
                        for(Pezzo piece:simpieces)
                        {
                            if(piece != king && piece.color != currentColor && piece.canMove(col,checkingP.row))
                                return false;
                        }
                    }
                }
            }
            else if(colDiff==rowDiff)
            {
                //Il pezzo sta attaccando diagoalmente
                if(checkingP.row<king.row)
                {
                    //Il pezzo sta attaccando in alto
                    if(checkingP.col<king.col)
                    {
                        //Il pezzo sta attaccando in alto a sinistra
                        for(int col = checkingP.col, row = checkingP.row; col<king.col;col++,row++)
                        {
                            for(Pezzo piece: simpieces)
                            {
                                if(piece!=king&&piece.color!=currentColor&&piece.canMove(col,row))
                                {
                                    can=true;
                                    return false;
                                }
                                    
                            }
                        }
                    }
                    if(checkingP.col>king.col)
                    {
                        //Il pezzo sta attaccando in alto a destra
                        for(int col = checkingP.col, row = checkingP.row; col>king.col;col--,row++)
                        {
                            for(Pezzo piece: simpieces)
                            {
                                if(piece!=king&&piece.color!=currentColor&&piece.canMove(col,row))
                                {
                                    can=true;
                                    return false;
                                }
                                    
                            }
                        }
                    }
                }
                
                if(checkingP.row>king.row)
                {
                    //Il pezzo sta attaccando in basso
                    if(checkingP.col<king.col)
                    {
                        //Il pezzo sta attaccando in basso a sinistra
                        for(int col = checkingP.col, row = checkingP.row; col<king.col;col++,row--)
                        {
                            for(Pezzo piece: simpieces)
                            {
                                if(piece!=king&&piece.color!=currentColor&&piece.canMove(col,row))
                                    return false;
                            }
                        }
                    }
                    if(checkingP.col>king.col)
                    {
                        //Il pezzo sta attaccando in basso a destra
                        for(int col = checkingP.col, row = checkingP.row; col>king.col;col--,row--)
                        {
                            for(Pezzo piece: simpieces)
                            {
                                if(piece!=king&&piece.color!=currentColor&&piece.canMove(col,row))
                                    return false;
                            }
                        }
                    }
                } 
            }     
        return true;
    }
}
    
    
    private boolean kingCanMove(Pezzo king){
        //controllo della presenza di una casella su cui il re può muoversi
        if(isValidMove(king,-1,-1)){
            return true;
        }
        if(isValidMove(king,0,-1)){
            return true;
        }
        if(isValidMove(king,1,-1)){
            return true;   
        }
        if(isValidMove(king,-1,0)){
            return true;
        }
        if(isValidMove(king,1,0)){
            return true;
        }
        if(isValidMove(king,-1,1)){
            return true;
        }
        if(isValidMove(king,0,1)){
            return true;
        }
        if(isValidMove(king,1,1)){
            return true;
        }
        
        return false; 
    }
    
    private boolean isValidMove(Pezzo king, int colPlus, int rowPlus){
        boolean isValidMove=false;
        
        //Aggiornamento della posizione del re
        king.col+=colPlus;
        king.row+=rowPlus;
        
        if(king.canMove(king.col, king.row))
        {
            if(king.hittingP!=null)
            {
                simpieces.remove(king.hittingP.getIndex());
            }
            if(isIllegal(king)==false)
            {
                isValidMove=true;
            }
        }
        
        //resettare la posizione del re ed aggiornare la lista dei pezzi con il pezzo rimosso
        king.resetPosition();
        copyPieces(pieces,simpieces);
        
        return isValidMove;
        
    }
    
    private boolean isStalemate(){
        int count =0;
        //conta del numero di pezzi sulla scacchiera
        for(Pezzo piece: simpieces)
        {
            if(piece.color!=currentColor)
                count++;
        }
        
        if(count ==1)
        {
            if(kingCanMove(getKing(true))==false)
            {
                return true;
            }
        }
        return false;
        
    }
    
    private void checkCastling(){
        if(castlingP!=null)
        {
            if(castlingP.col==0)
                castlingP.col+=3;
            else if(castlingP.col==7)
                castlingP.col-=2;
            castlingP.x=castlingP.getX(castlingP.col);
        }
    }
    private void changePlayer(){
        if(currentColor==WHITE)
        {
            currentColor=BLACK;
            //Resettare possibilità di fare en Passant
            for(Pezzo piece : pieces)
            {
                if(piece.color==BLACK)
                    piece.twoStepped=false;
            }
        } 
        else
        {
            currentColor=WHITE;
            //resettare possibilità di fare En Passant
            for(Pezzo piece : pieces)
            {
                if(piece.color==WHITE)
                    piece.twoStepped=false;
            }
        }
        activeP=null;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        //board
        Graphics2D g2 = (Graphics2D) g;
        b.draw(g2);
        
        //components
        for(Pezzo p: simpieces)
        {
            p.draw(g2);
        }
        

        if(activeP !=null){
            if(canMove)
            {
                if(isIllegal(activeP)||opponentCanCaptureKing())
                {
                    g2.setColor(Color.gray);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.7f));
                    g2.fillRect(activeP.col*Board.dim, activeP.row*Board.dim, Board.dim, Board.dim);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
                }
                else
                {
                    g2.setColor(Color.white);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.7f));
                    g2.fillRect(activeP.col*Board.dim, activeP.row*Board.dim, Board.dim, Board.dim);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
                }
                
            }
            
            
            activeP.draw(g2);
        }
        
        //messaggio di stato e gestione della promozione
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Calibri",Font.PLAIN,30));
        g2.setColor(Color.WHITE);
        if(promotion)
        {
            g2.drawString("Promozione a : ",840, 150);
            try
            {
                BufferedImage background;
                background=ImageIO.read(new File("C:\\Users\\Utente\\Documents\\NetBeansProjects\\Chess\\src\\res\\back-promote.png"));
                g2.drawImage(background,900,190,100,430,null);        
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            for(Pezzo piece: promoPieces)
            { 
                g2.drawImage(piece.image,piece.getX(piece.col),piece.getY(piece.row),Board.dim,Board.dim,null);
            }
        }
        else
        {
            if(currentColor==WHITE)
            {
                g2.drawString("TURNO BIANCO", 840, 550);
                if(checkingP !=null &&checkingP.color==BLACK)
                {
                    g2.setColor(Color.red);
                    g2.drawString("IL RE è SOTTO ", 840, 650);
                    g2.drawString("SCACCO",840,700);
                }

            }
            else
            {
                g2.drawString("TURNO NERO", 840, 250);
                if(checkingP !=null &&checkingP.color==WHITE)
                {
                    g2.setColor(Color.red);
                    g2.drawString("IL RE è SOTTO ", 840, 100);
                    g2.drawString("SCACCO",840,150);
                }
            }
        }
        
        //testo in caso di fine gioco
        if(gameover)
        {
            String s="";
            if(currentColor == WHITE)
            {
                s="BIANCO VINCE";
            }
            else
            {
                s="NERO VINCE";
            }
            g2.setFont(new Font("Arial",Font.PLAIN,90));
            g2.setColor(Color.green);
            g2.drawString(s, 200, 420);
            
            ArrayList<String> v = new ArrayList<>();
            try (BufferedReader fIN = new BufferedReader(new FileReader(Game.path + "storico.txt"))) 
            {
                String line;
                while ((line = fIN.readLine()) != null) {
                    v.add(line);
                }
                
                for(String e:v)
                {
                    int stato = e.indexOf('?');
                    if(stato!=-1)
                    {
                        String[] partita = e.split(";");
                        String[] giocatori = partita[0].split("/");

                        //Se vince il nero 
                        if(currentColor==BLACK)
                        {
                            int j=NomiGiocatori.trovaIndiceNome(giocatori[0],v);
                            int k= NomiGiocatori.trovaIndicePartitaNonConclusa(j,v);
                            String partitaAggiornata = giocatori[0]+"/"+giocatori[1]+";V";
                            v.set(k, partitaAggiornata);

                            int j1=NomiGiocatori.trovaIndiceNome(giocatori[1],v);
                            int k1= NomiGiocatori.trovaIndicePartitaNonConclusa(j1,v);
                            String partitaAggiornata1 = giocatori[0]+"/"+giocatori[1]+";S";
                            v.set(k1, partitaAggiornata1);
                        }
                        else if(currentColor==WHITE)
                        {
                            int j=NomiGiocatori.trovaIndiceNome(giocatori[0],v);
                            int k= NomiGiocatori.trovaIndicePartitaNonConclusa(j,v);
                            String partitaAggiornata = giocatori[0]+"/"+giocatori[1]+";S";
                            v.set(k, partitaAggiornata);

                            int j1=NomiGiocatori.trovaIndiceNome(giocatori[1],v);
                            int k1= NomiGiocatori.trovaIndicePartitaNonConclusa(j1,v);
                            String partitaAggiornata1 = giocatori[0]+"/"+giocatori[1]+";V";
                            v.set(k1, partitaAggiornata1);
                        }
                        break;
                    }

                }
            }
            catch(IOException e){}
            try (PrintWriter fOUT = new PrintWriter(new FileWriter(Game.path + "storico.txt"))) 
            {
                for (String elemento : v) 
                {
                    fOUT.println(elemento);
                }
            }
            catch(IOException e){}
        }
        
        if(stalemate)
        {
            g2.setFont(new Font("Arial",Font.PLAIN,90));
            g2.setColor(Color.gray);
            g2.drawString("Stallo", 350, 450);
            ArrayList<String> v = new ArrayList<>();
            try (BufferedReader fIN = new BufferedReader(new FileReader(Game.path + "storico.txt"))) 
            {
                String line;
                while ((line = fIN.readLine()) != null) {
                    v.add(line);
                }
                
                for(String e:v)
                {
                    int stato = e.indexOf('?');
                    if(stato!=-1)
                    {
                        String[] partita = e.split(";");
                        String[] giocatori = partita[0].split("/");

                        //In caso di patta
                        int j=NomiGiocatori.trovaIndiceNome(giocatori[0],v);
                        int k= NomiGiocatori.trovaIndicePartitaNonConclusa(j,v);
                        String partitaAggiornata = giocatori[0]+"/"+giocatori[1]+";P";
                        v.set(k, partitaAggiornata);

                        int j1=NomiGiocatori.trovaIndiceNome(giocatori[1],v);
                        int k1= NomiGiocatori.trovaIndicePartitaNonConclusa(j1,v);
                        String partitaAggiornata1 = giocatori[0]+"/"+giocatori[1]+";P";
                        v.set(k1, partitaAggiornata1);
                    }
                }
            }
            catch(IOException e){}
            try (PrintWriter fOUT = new PrintWriter(new FileWriter(Game.path + "storico.txt"))) 
            {
                for (String elemento : v) 
                {
                    fOUT.println(elemento);
                }
            }
            catch(IOException e){}
        }
        
    }
    
    
    
}


