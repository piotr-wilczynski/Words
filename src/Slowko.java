
import java.io.Serializable;

public class Slowko implements Serializable{

    private String text;
    private Jezyk jezyk;

    public Slowko(String text, Jezyk jezyk) {
        this.text = text;
        this.jezyk = jezyk;
    }

    public Slowko(){
        this("lol", Jezyk.Polski);
    }

    public Jezyk getJezyk() {
        return jezyk;
    }

    public void setJezyk(Jezyk jezyk) {
        this.jezyk = jezyk;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
    
    
}
