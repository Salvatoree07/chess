/*
 * Questa classe definisce le caratteristiche del cavallo
 */
package piece;
import game.*;
public class Cavallo extends Pezzo {
    
    public Cavallo(int color, int col, int row) {
        super(color, col, row);
        if(color == GamePanel.WHITE)
            image = getImage("w-knight");
        else
            image=getImage("b-knight");
        ID=3;
    }
    
    //il movimento del cavallo è 2:1 e 1:2
    @Override
    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow)){
            if(Math.abs(targetCol-preCol)*Math.abs(targetRow-preRow)==2)
            {
                if(isValidSquare(targetCol, targetRow))
                    return true;
            }
        }
        return false;
    }
    
}
