/*
 * Questa classe definisce le caratteristiche del pezzo Re
 */
package piece;
import game.*;
public class Re extends Pezzo {
    
    public Re(int color, int col, int row) {
        super(color, col, row);
        if(color == GamePanel.WHITE)
            image = getImage("w-king");
        else
            image=getImage("b-king");
        ID=2;
    }
    
    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow))
        {
            if(Math.abs(targetCol-preCol)+Math.abs(targetRow-preRow)==1||Math.abs(targetCol -preCol)*Math.abs(targetRow-preRow)==1)
            {
                if(isValidSquare(targetCol, targetRow))
                    return true;
            }
            
            //arrocco (castling)
            if(moved==false)
            {
                //arroco corto
                if(targetCol == preCol+2 && targetRow ==preRow &&pieceIsOnStraightLine(targetCol,targetRow)==false)
                {
                    for(Pezzo piece:GamePanel.simpieces)
                    {
                        if(piece.col==preCol+3&&piece.row==preRow&&piece.moved==false)
                        {
                            GamePanel.castlingP =piece;
                            return true;
                        }
                    }
                }
            }
            
            //arrocco lungo
            if(targetCol==preCol-2 &&targetRow ==preRow&&pieceIsOnStraightLine(targetCol,targetRow)==false)
            {
                Pezzo p[]=new Pezzo[2];
                for(Pezzo piece:GamePanel.simpieces){
                    if(piece.col==preCol-3 && piece.row==targetRow){
                        p[0]=piece;
                    }
                    if(piece.col==preCol-4&&piece.row==targetRow)
                    {
                        p[1]=piece;
                    }
                    
                    if(p[0]==null&&p[1]!=null&&p[1].moved==false)
                    {
                        GamePanel.castlingP=p[1];
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    
}
