
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Baza {
private File Folder = new File("Baza");
private ArrayList<ArrayList<Slowka>> bazy=null;
private ArrayList<String> BazyNazwy;
private static Baza b = new Baza();
    public Baza() {
        bazy = new ArrayList<ArrayList<Slowka>>();
        BazyNazwy = new ArrayList<String>();
    }
    public static Baza getInstance(){
        return b;
    }
    public void Wczytaj(){        
        bazy = new ArrayList<ArrayList<Slowka>>();
        BazyNazwy = new ArrayList<String>();
        Folder.mkdir();
        if(Folder.listFiles().length>0){            
            File plik = null;
            for(int i=0;i<Folder.listFiles().length;i++){
                plik = Folder.listFiles()[i];
                if(plik.exists()){
                    XMLDecoder xd=null;
                    try {
                        xd = new XMLDecoder(new FileInputStream(plik));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ArrayList<Slowka> bazatemp = (ArrayList<Slowka>) xd.readObject();
                    if(bazatemp!=null){
                        bazy.add(bazatemp);
                        String temp = plik.getName();
                        BazyNazwy.add(temp.substring(0, temp.length()-4));
                    }                        
                }
            }
        }
    }
    public void Dopisz(ArrayList<Slowka> slowko,File base){
        ArrayList<Slowka> baza= slowko;
        if(base.exists()){      
            base.delete();
        }
          Collections.sort(baza);             
            try {
                base.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
            }
            XMLEncoder xe=null;
            try {
                xe = new XMLEncoder(new FileOutputStream(base));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
            }
            xe.writeObject(baza);
            xe.close();
            Wczytaj();
        
    }
    
    public ArrayList<Slowka> getSlowka(){
        String ActualBase = Preferencje.getInstance().getActualBase();
        if(ActualBase.length()>0){
            try{
                return bazy.get(BazyNazwy.indexOf(ActualBase));
            }catch(Exception e){
                Preferencje.getInstance().removeActualBase();
                return new ArrayList<Slowka>();                
            }
        }else{
            return new ArrayList<Slowka>();
        }
    }
    
    public ArrayList<ArrayList<Slowka>> getBases(){
        return bazy;
    }
    public ArrayList<String> getBaseNames(){
        return BazyNazwy;
    }
    public File[] getBaseFiles(){
        return Folder.listFiles();
    }
    private int Roznica(ArrayList<Slowka> a1, ArrayList<Slowka> a2){
        for(int i=0;i<a2.size();i++){            
            for(int j=0;j<a1.size();j++){
                if(a2.get(i).equal(a1.get(j))){
                    a2.remove(i);                    
                    i--;
                    break;
                }                
            }
        }
        a1.addAll(a2);
        return a2.size();
    }
}
