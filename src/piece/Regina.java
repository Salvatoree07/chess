/*
 * Questa classe definisce le caratteristiche della regine
 */
package piece;
import game.*;
public class Regina extends Pezzo {
    
    public Regina(int color, int col, int row) {
        super(color, col, row);
        if(color == GamePanel.WHITE)
            image = getImage("w-queen");
        else
            image=getImage("b-queen");
        ID=5;
    }
    
    public boolean canMove(int targetCol, int targetRow)
    {
        if(isWithinBoard(targetCol,targetRow)&&isSameSquare(targetCol,targetRow)==false){
            //Vertical and Horizontal movement
            if(targetCol==preCol || targetRow ==preRow)
            {
                if(isValidSquare(targetCol, targetRow)&&pieceIsOnStraightLine(targetCol,targetRow)==false){
                    return true;
                }
            }
            
            
            //Diagonal movement
            if(Math.abs(targetCol-preCol)==Math.abs(targetRow-preRow))
            {
                if(isValidSquare(targetCol,targetRow)&&pieceIsOnDiagonalLine(targetCol,targetRow)==false){
                    return true;
                }
            }
        }
        return false;
    }
    
}
