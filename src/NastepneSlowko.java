
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;



public class NastepneSlowko implements Runnable{
    private final long Day = 1000*60*60*24;
    public NastepneSlowko() {
        new Thread(this).start();
    }

    
    @Override
    public void run() {
        if(SprawdzDate())
            Okno.getInstance().KolejneSlowko();
        SleepToNextDay();        
        while(true){    
            Okno.getInstance().KolejneSlowko();
            try {
                Thread.sleep(Day);
            } catch (InterruptedException ex) {
                SleepToNextDay();
            }            
        }
    }
    private boolean SprawdzDate(){
        Date now = new Date(System.currentTimeMillis());
        Date last = new Date(Preferencje.getInstance().getLastTimeWordChange());
        if(now.getDate()!=last.getDate()||now.getTime()-last.getTime()>(Day))
            return true;
        else
            return false;
    }
    private void SleepToNextDay(){
        
        long time = System.currentTimeMillis();
        Date d = new Date(time);
        d.setSeconds(0);
        d.setMinutes(0);
        d.setHours(0);
        d.setDate(d.getDate()+1);
        time = d.getTime()-time;
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            SleepToNextDay();
        }        
    }
    
}
