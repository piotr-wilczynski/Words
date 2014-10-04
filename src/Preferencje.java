
import java.awt.Point;
import java.util.prefs.Preferences;


public class Preferencje {
    private static Preferencje p = new Preferencje();
    private Preferences pref;
    private Point AppLocation;
    public Preferencje() {
        pref = Preferences.userRoot().node(this.getClass().getName());
        int x = pref.getInt("appx", 100);
        int y = pref.getInt("appy", 100);
        AppLocation = new Point(x, y);
    }
    public static Preferencje getInstance(){
        return p;
    }
    public void setAppLocation(Point d){
        pref.putInt("appx",d.x);
        pref.putInt("appy",d.y);
        AppLocation=d;
    }

    public Point getAppLocation() {
        return AppLocation;
    }
    public void setActualBase(String Text){
        pref.put("ActualBase", Text);
    }
    public String getActualBase(){
        return pref.get("ActualBase", "");
    }
    public Boolean getBaseStatus(String BaseName){
        return pref.getBoolean("BaseStatus"+BaseName, false);
    }
    public void setBaseStatus (String BaseName,Boolean value){
        pref.putBoolean("BaseStatus"+BaseName, value);
    }
    public void removeActualBase(){
        pref.remove("ActualBase");
    }
    public long getLastTimeWordChange(){
        return pref.getLong("LastTimeWordChange", System.currentTimeMillis());             
    }
    public void setLastTimeWordChange(long time){
        pref.putLong("LastTimeWordChange", time);
    }
    public long getShuffleSeed(){
        return pref.getLong("ShuffleSeed", 0);
    }
    public void setShuffleSeed(long Seed){
        pref.putLong("ShuffleSeed", Seed);
    }
    public boolean getShuffle(){
        return pref.getBoolean("isShuffle", false);
    }
    public void setShuffle(boolean Value){
        pref.putBoolean("isShuffle", Value);
    }
    public int getWordNumber(){
        return pref.getInt("WordNumber", 0);
    }
    public void setWordNumber(int Number){
        pref.putInt("WordNumber", Number);
    }
    public float getTransparency(){
        return pref.getFloat("Transparency", 0.6f);
    }
    public void setTransparency(float Transparency){
        pref.putFloat("Transparency", Transparency);
    }
                

    
    
}
