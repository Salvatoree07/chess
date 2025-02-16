/*
 * Questa classe rappresenta le caratteristiche della scacchiera
 */
package game;
import java.awt.Color;
import java.awt.Graphics2D;
public class Board {
    final int nCol = 8;
    final int nRig = 8;
    public static final int dim = 100;
    public static final int mDim = dim/2;
    int c=0;
    public void draw (Graphics2D g2){
        for(int i = 0; i<nRig; i++)
        {
            for(int j=0; j<nCol; j++)
            {
                if(i%2==0)
                {
                    if(c%2==0)
                        g2.setColor(new Color(210,165,125));
                    else
                        g2.setColor(new Color(175,115,70));
                }
                else
                {
                    if(c%2!=0)
                        g2.setColor(new Color(210,165,125));
                    else
                        g2.setColor(new Color(175,115,70));
                }
                g2.fillRect(j*dim, i*dim, dim, dim);
                c++;
            }
            c=0;
        }
    }
}
