/*
 * Questa classe definisce le caratteristiche del pedone
 */
package piece;
import game.*;
public class Pedone extends Pezzo {
    
    public Pedone(int color, int col, int row) {
        super(color, col, row);
        ID=4;
        if(color == GamePanel.WHITE)
            image = getImage("w-pawn");
        else
            image=getImage("b-pawn");
    }
    
    public boolean canMove(int targetCol, int targetRow)
    {
        if(isWithinBoard(targetCol, targetRow)&&isSameSquare(targetCol,targetRow)==false)
        {
            int moveValue;
            if(color==GamePanel.WHITE)
            {
                moveValue=-1;
            }
            else
            {
                moveValue=1;
            }
            
            //controllo del pezzo attaccatto
            hittingP=getHittingP(targetCol,targetRow);
            
            //movimento di 1 casella
            if(targetCol==preCol &&targetRow==preRow+ moveValue&&hittingP==null)
            {
                return true;
            }
            
            //movimento di 2 caselle
            if(targetCol==preCol&&targetRow ==preRow + moveValue*2&&hittingP==null&&moved==false&&pieceIsOnStraightLine(targetCol,targetRow)==false){
                return true;
            }
            
            //movimento diagonale per la cattura dei pezzi solo se sono di colore diverso dal pedone
            if(Math.abs(targetCol-preCol)==1 &&targetRow ==preRow +moveValue&&hittingP!=null&&hittingP.color!=color){
                return true;
            }
            
            //En Passant
            if(Math.abs(targetCol-preCol)==1 &&targetRow ==preRow +moveValue){
                for(Pezzo piece: GamePanel.simpieces)
                {
                    if(piece.col==targetCol && piece.row==preRow && piece.twoStepped==true)
                    {
                        hittingP = piece;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}
