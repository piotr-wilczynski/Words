
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public enum Jezyk implements Serializable{
    Angielski,Francuski,Hiszpański,Niemiecki,Polski,Szwedzki,Włoski;

    
    private Jezyk() {
    }

    public static Jezyk getAngielski() {
        return Angielski;
    }

    public static Jezyk getPolski() {
        return Polski;
    }

    public static Jezyk getSzwedzki() {
        return Szwedzki;
    }
    
    public Image getImage(){
        Image im = null;
        try {
            im = ImageIO.read(getClass().getResource("/Dane/"+this.name()+".png"));
        } catch (IOException ex) {
            Logger.getLogger(Jezyk.class.getName()).log(Level.SEVERE, null, ex);
        }
        return im;
    }
}
