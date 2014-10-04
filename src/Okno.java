import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.*;



public class Okno extends javax.swing.JDialog{
    private Preferencje p;
    private static Okno o = new Okno();
    public Okno() {
        new Myszka(this);
        p = Preferencje.getInstance();
        Baza.getInstance().Wczytaj();
        new NastepneSlowko();
        initComponents();
    }
    public static Okno getInstance(){
        return o;
    }
    private void initComponents(){
        initLookAndFeel();
        initTrayMenu();
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        panel = new JPanel();
        panel.setBackground(getBackground());
        l = new GridLayout();
        panel.setLayout(l);
        l.setColumns(1);
        l.setVgap(10);        
        add(panel);
        NumerSlowka = Preferencje.getInstance().getWordNumber();
        Kolejnosc();
        WyswietlSlowko();
        setLocation(p.getAppLocation());
        tray = new Tray(this,MenuTraya);
        setVisible(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_RIGHT:{
                        setNumerSlowka(NumerSlowka+1);
                    }break;
                    case KeyEvent.VK_LEFT:{
                        setNumerSlowka(NumerSlowka-1);
                    }break;
                        
                }
            }            
        });
    }
    public void WyswietlSlowko(){ 
        Kolejnosc();
        panel.removeAll();        
        if(Baza.getInstance().getSlowka().size()>0){            
            Slowka s=Baza.getInstance().getSlowka().get((int)Kolejnosc.get(NumerSlowka));
            int Size = s.size();
            l.setRows(Size);
            for(int i=0;i<Size;i++){
                panel.add(new Tlumaczenie(s.getSlowka()[i]));
            }
            setSize(200,Size*100+(Size-1)*l.getVgap());
        }
        if(panel.getComponentCount()==0){
            pokaz.setEnabled(false);
        }else{
            pokaz.setEnabled(true);
        }
        panel.updateUI();   
        repaint(); 
    }
    private void initTrayMenu(){
        MenuTraya = new PopupMenu();
        pokaz = new MenuItem("Pokaż/Ukryj");
        pokaz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    
                setVisible(!isVisible());
            }
        });
        MenuTraya.add(pokaz);
        MenuTraya.addSeparator();
        MenuItem mi = new MenuItem("Następne");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(NumerSlowka<Baza.getInstance().getSlowka().size()-1){
                    setNumerSlowka(NumerSlowka+1);
                }
            }
        });
        MenuTraya.add(mi);
        mi = new MenuItem("Poprzednie");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(NumerSlowka>0){
                    setNumerSlowka(NumerSlowka-1);
                }
            }
        });
        MenuTraya.add(mi);
        CheckboxMenuItem cbmi = new CheckboxMenuItem("Pomieszaj");
        cbmi.setState(Preferencje.getInstance().getShuffle());
        cbmi.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean state = ((CheckboxMenuItem)e.getSource()).getState();
                Preferencje.getInstance().setShuffle(state);
                if(state){
                    Preferencje.getInstance().setShuffleSeed(new Random().nextLong());
                }
                Preferencje.getInstance().setWordNumber(0);
                NumerSlowka=0;
                Kolejnosc();
                WyswietlSlowko();
            }
        });
        MenuTraya.add(cbmi);
        MenuTraya.addSeparator();
        mi = new MenuItem("Opcje");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Opcje.getInstance().setVisible(true);
            }
        });
        MenuTraya.add(mi);   
        mi = new MenuItem("Przeźroczystość");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Opcje.getInstance().przezroczystosc();
                    }
                }).start();
            }
        });
        MenuTraya.add(mi);         
        MenuTraya.addSeparator();
        mi = new MenuItem("Zamknij");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        MenuTraya.add(mi);                 
    }
    private void initLookAndFeel(){
        try {
          UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            
        }
    }
    public void KolejneSlowko(){
        setNumerSlowka(NumerSlowka+1);
    }
    private void Kolejnosc(){
        ArrayList list = new ArrayList();
        for(int i=0;i<Baza.getInstance().getSlowka().size();i++){
            list.add(i);
        }
        if(Preferencje.getInstance().getShuffle()){
            Collections.shuffle(list, new Random(Preferencje.getInstance().getShuffleSeed()));            
        }
        Kolejnosc = list;
    }
    private void setNumerSlowka(int Numer){
        if(Numer<Baza.getInstance().getSlowka().size()&&Numer>=0){
            NumerSlowka=Numer;
            Preferencje.getInstance().setWordNumber(Numer);
            WyswietlSlowko();
            tray.Display("Słówko "+(Numer+1)+" ("+Baza.getInstance().getSlowka().size()+")");
        }
    }
    private PopupMenu MenuTraya;
    private GridLayout l;
    private JPanel panel;    
    private int NumerSlowka;
    private MenuItem pokaz;
    private ArrayList Kolejnosc;
    private Tray tray;
}
