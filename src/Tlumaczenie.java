
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.Serializable;
import javax.swing.JPanel;
public class Tlumaczenie extends JPanel implements Serializable{
    private final Slowko slowko;   

    public Tlumaczenie(Slowko slowko) {
        this.slowko = slowko; 
        
    }
    public Slowko getSlowko(){
        return slowko;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(new Color(0,0,0,0));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Opcje.getInstance().getTransparency()));
        g2.drawImage(slowko.getJezyk().getImage().getScaledInstance(getSize().width, getSize().height, Image.SCALE_SMOOTH), 0, 0,getSize().width,getSize().height,new Color(0, 0, 0, 0), this);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setFont(new Font(getFont().getFontName(), Font.BOLD, 20)); 
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(Color.BLACK);
        g2.drawString(slowko.getText(), (getWidth()-fm.stringWidth(slowko.getText()))/2, getSize().height/2+(fm.getHeight())/2-fm.getDescent());        
    }
}
