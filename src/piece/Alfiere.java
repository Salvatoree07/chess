/*
 * Questa classe definisce le caratteristiche del pezzo Alfiere
 */
package piece;
import game.*;
public class Alfiere extends Pezzo {  
    
    public Alfiere(int color, int col, int row) {
        super(color, col, row);
        if(color == GamePanel.WHITE)
            image = getImage("w-bishop");
        else
            image=getImage("b-bishop");
        ID=1;
    }
    
    @Override
    public boolean canMove(int targetCol, int targetRow)
    {
            if(isWithinBoard(targetCol,targetRow)&& isSameSquare(targetCol, targetRow)==false)
            {
                if(Math.abs(targetCol-preCol)==Math.abs(targetRow-preRow))
                {
                    if(isValidSquare(targetCol, targetRow) && pieceIsOnDiagonalLine(targetCol, targetRow)==false)
                        return true;
                }
            }
            return false;
    }
    
}
