/*
 * Questa classe definisce le caratteristiche della torre
 */
package piece;
import game.*;
public class Torre extends Pezzo {
    
    public Torre(int color, int col, int row) {
        super(color, col, row);
        if(color == GamePanel.WHITE)
            image = getImage("w-rook");
        else
            image=getImage("b-rook");
        ID=6;
    }
    
    //la torre si muove sulle celle che hanno la stessa riga oppure la stessa colonna della torre
    @Override
    public boolean canMove(int targetCol, int targetRow)
    {
        if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow)==false)
        {
            if(targetCol == preCol || targetRow == preRow)
            {
                if(isValidSquare(targetCol, targetRow)&&pieceIsOnStraightLine(targetCol, targetRow)==false)
                    return true;
                
            }
        }
        return false;
    }
}