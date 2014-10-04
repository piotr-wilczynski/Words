
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Tray{
    public Tray(Component component,PopupMenu MenuTraya) {
        this.MenuTraya=MenuTraya;
        c=component;
        if(SystemTray.isSupported()){
            initTray();
        }
    }
    public void Display(String message){
        tray.displayMessage(null, message, TrayIcon.MessageType.INFO);
    }
    private void initTray(){
        //Pobranie Systemowego Traya
        systray = SystemTray.getSystemTray();
        //Ustawienie Icony Traya
        Dimension TraySize = systray.getTrayIconSize();
        BufferedImage im=new BufferedImage(TraySize.width, TraySize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setBackground(new Color(0,0,0,0));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font f = g.getFont();
        g.setFont(new Font(f.getFontName(), Font.BOLD, 20));
        String text = "D";
        g.setColor(Color.red);
        FontMetrics fm = g.getFontMetrics();
        g.drawString("D", (TraySize.width-fm.stringWidth(text))/2, TraySize.height/2+(fm.getHeight())/2-fm.getDescent());
        tray = new TrayIcon(im);
        //Dodanie Traya do Systemowego Traya
        try {
            systray.add(tray);
        } catch (AWTException ex) {
            Logger.getLogger(Tray.class.getName()).log(Level.SEVERE, null, ex);
        }
        tray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {   
                c.setVisible(!c.isVisible());
            }
        });
        tray.setPopupMenu(MenuTraya);
        
    }
    private Component c;
    private SystemTray systray;
    private TrayIcon tray;
    private PopupMenu MenuTraya;
}
