
import java.io.Serializable;

public class Slowka implements Serializable, Comparable<Slowka>{
    private Slowko[] slowka;
    private Jezyk PrimaryKey;

    public Slowka(Jezyk PrimaryKey, Slowko[] slowka) {
        this.slowka=slowka;
        this.PrimaryKey=PrimaryKey;
    }

    public Slowka() {
    }

    public void setPrimaryKey(Jezyk PrimaryKey) {
        this.PrimaryKey = PrimaryKey;
    }

    public Jezyk getPrimaryKey() {
        return PrimaryKey;
    }

    public Slowko[] getSlowka() {
        return slowka;
    }
    public void setSlowka(Slowko[] slowka) {
        this.slowka = slowka;
    }
    @Override
    public int compareTo(Slowka o) {
        Slowko s1=null,s2=null;
        for(int i=0;i<o.size();i++){
            if(slowka[i].getJezyk()==PrimaryKey){
                s1=o.slowka[i];
            }
        }
        for(int i=0;i<o.size();i++){
            if(o.getSlowka()[i].getJezyk()==PrimaryKey){
                s2=o.getSlowka()[i];
            }
        }
        return s1.getText().compareTo(s2.getText());
    }
    public int size(){
        return slowka.length;
    }
    public Jezyk[] getJezyki(){
        Jezyk[] j = new Jezyk[size()];
        for(int i=0;i<size();i++){
            j[i]=slowka[i].getJezyk();
        }
        return j;
    }
    public Boolean equal(Slowka o){
        if(o.getJezyki().length==o.getJezyki().length){
            Boolean jezyki = true;
            for(int i=0;i<getJezyki().length;i++){
                jezyki=jezyki&&(getJezyki()[i]==o.getJezyki()[i]);
            }
            if(jezyki){
                for(int i=0;i<slowka.length;i++){
                    if(!(slowka[i].getText().matches(o.getSlowka()[i].getText())))
                        return false;                    
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }        
    }
    
}
