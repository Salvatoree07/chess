/*
 * Questa classe corrisponde alla superclasse di tutti i pezzi e contiene tutti i metodi utili alle sottoclassi
 */
package piece;
import game.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import javax.imageio.ImageIO;

public class Pezzo {
    public BufferedImage image;
    public int x, y;
    public int col, row, preCol, preRow;
    public int color;
    public Pezzo hittingP;
    public boolean moved=false;
    public int ID;
    public boolean twoStepped;
    
    public Pezzo (int color, int col, int row){
        this.color=color;
        this.col=col;
        this.row=row;
        x=getX(col);
        y=getY(row);
        preCol = col; 
        preRow=row;
    }
    
    public BufferedImage getImage(String imagePath){
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(Game.path+imagePath+".png"));   
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return image;
    }
    
    public int getX(int col)
    {
        return col*Board.dim;
    }
    public int getY(int row)
    {
        return row*Board.dim;
    }
    public int getCol(int x){
        return (x+Board.mDim)/Board.dim;
    }
    public int getRow(int y){
        return (y+Board.mDim)/Board.dim;
    }
    public int getIndex(){
        for(int i=0; i<GamePanel.simpieces.size();i++)
        {
            if(GamePanel.simpieces.get(i)==this)
                return i;
        }
        return 0;
    }
    public void updatePosition(){
        //controllo En Passant
        if(ID==4)
        {
            if(Math.abs(row-preRow)==2)
                twoStepped=true;
        }
        x=getX(col);
        y=getY(row);
        preCol=getCol(x);
        preRow=getRow(y);
        moved=true;
    }
    
    public void resetPosition(){
        col=preCol;
        row=preRow;
        x=getX(col);
        y=getY(row);
    }
    
    public boolean canMove(int target, int targetRow){
        return false;
    }
    
    public boolean isWithinBoard(int targetCol, int targetRow){
        if(targetCol >= 0 && targetCol <=7 && targetRow>=0 && targetRow <= 7)
            return true;
        return false;
    }
    
    public Pezzo getHittingP(int targetCol, int targetRow){
        for (var piece : GamePanel.simpieces) {
            if(piece.col==targetCol && piece.row == targetRow && piece != this)
                return piece;
        }
        return null;
    }
    
    public boolean isSameSquare(int targetCol, int targetRow){
        if(targetCol == preCol && targetRow == preRow)
            return true;
        return false;
    }
    public boolean isValidSquare(int targetCol, int targetRow){
        hittingP = getHittingP(targetCol, targetRow);
        if(hittingP==null)
        {
            return true;
        }
        else
        {
            if(hittingP.color != this.color)
                return true;
            else
                hittingP = null;
        }
        
        return false;
    }
    
    public boolean pieceIsOnStraightLine(int targetCol, int targetRow){
        //per vedere se è presente un pedone nel lato sinistro
        for (int c = preCol - 1; c > targetCol; c--) {
            for (Pezzo piece : GamePanel.simpieces) {
                if (piece.col == c && piece.row == targetRow) {
                    hittingP = piece;
                    return true;
                }
            }
        }

        //per vedere se è presente un pedone nel lato destro
        for (int c = preCol + 1; c < targetCol; c++) {
            for (Pezzo piece : GamePanel.simpieces) {
                if (piece.col == c && piece.row == targetRow) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        //per vedere se è presente un pedone nel lato in alto
        for (int r = preRow - 1; r > targetRow; r--) {
            for (Pezzo piece : GamePanel.simpieces) {
                if (piece.col == targetCol && piece.row == r) {
                    hittingP = piece;
                    return true;
                }
            }
        }

        //per vedere se è presente un pedone nel lato in basso
        for (int r = preRow + 1; r < targetRow; r++) {
            for (Pezzo piece : GamePanel.simpieces) {
                if (piece.col == targetCol && piece.row == r) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        return false;
   }
    
    public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow)
    {
        if(targetRow < preRow)
        {
            //In alto a sinistra
            for(int c= preCol-1; c> targetCol; c--)
            {
                int diff = Math.abs(c-preCol);
                for(Pezzo piece : GamePanel.simpieces)
                {
                    if(piece.col==c && piece.row == preRow-diff)
                    {
                        hittingP=piece;
                        return true;
                    }
                }
            }
            
            //in alto a destra
            for(int c= preCol+1; c< targetCol; c++)
            {
                int diff = Math.abs(c-preCol);
                for(Pezzo piece : GamePanel.simpieces)
                {
                    if(piece.col==c && piece.row == preRow-diff)
                    {
                        hittingP=piece;
                        return true;
                    }
                }
            }
        }
        else
        {
            //in basso a sinistra
            for(int c= preCol-1; c> targetCol; c--)
            {
                int diff = Math.abs(c-preCol);
                for(Pezzo piece : GamePanel.simpieces)
                {
                    if(piece.col==c && piece.row == preRow-diff)
                    {
                        hittingP=piece;
                        return true;
                    }
                }
            }
            
            //in basso a destra
            for(int c= preCol+1; c< targetCol; c++)
            {
                int diff = Math.abs(c-preCol);
                for(Pezzo piece : GamePanel.simpieces)
                {
                    if(piece.col==c && piece.row == preRow-diff)
                    {
                        hittingP=piece;
                        return true;
                    }
                }
            }
        }
        return false;
        
    }
    public void draw(Graphics2D g2)
    {
        g2.drawImage(image, x,y,Board.dim, Board.dim,null);
    }
    
    
    
}
