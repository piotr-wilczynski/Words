
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Myszka extends MouseAdapter {
    private Component c;
    private Point p = new Point(0, 0);
    public Myszka(Component component) {
        c=component;
        c.addMouseListener(this);
        c.addMouseMotionListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point pt = new Point(e.getLocationOnScreen().x-p.x, e.getLocationOnScreen().y-p.y);
        Preferencje.getInstance().setAppLocation(pt);
        c.setLocation(pt);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        p=e.getPoint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        p=e.getPoint();        
    }
    
    
}
