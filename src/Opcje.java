
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class Opcje extends JDialog{
    private static Opcje o = null;
    private float Transparency=0.6f;
    public Opcje() {
        initComponents();
        Transparency = Preferencje.getInstance().getTransparency();
    }

    
    public static Opcje getInstance(){
        if(o==null){
            o = new Opcje();
        }
        return o;
    }   
    private void initComponents(){
        setTitle("Opcje");
        Lista = new JList();
        Lista.setCellRenderer(new CellRenderer());
        Lista.setBorder(new BevelBorder(BevelBorder.LOWERED));
        PanelWyboru = new JPanel();
        PanelOpcji = new JPanel();
        PanelOpcjiWybor = new JPanel();
        Uzyj = new JButton("Użyj");
        OK = new JButton("OK");
        Anuluj = new JButton("Anuluj");
        Dodaj = new JButton("Dodaj");
        Edytuj = new JButton("Edytuj");
        Usun = new JButton("Usuń");
        listaAktualnychSlowek = new ArrayList<JCheckBox>();
        GroupLayout l = new GroupLayout(getContentPane());
        getContentPane().setLayout(l);
        l.setAutoCreateGaps(true);
        l.setAutoCreateContainerGaps(true);
        l.setHorizontalGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(PanelOpcji)
                .addComponent(PanelWyboru));
        l.setVerticalGroup(l.createSequentialGroup()
                .addComponent(PanelOpcji)
                .addComponent(PanelWyboru)
                );
        l = new GroupLayout(PanelWyboru);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<listaAktualnychSlowek.size();i++){
                    Preferencje.getInstance().setBaseStatus(listaAktualnychSlowek.get(i).getText(), listaAktualnychSlowek.get(i).isSelected());                    
                }
                dispose();
            }
        });
        Anuluj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        PanelWyboru.setLayout(l);
        l.setAutoCreateGaps(true);
        l.setHorizontalGroup(l.createSequentialGroup()
                .addComponent(OK,Anuluj.getMinimumSize().width,Anuluj.getMinimumSize().width,Anuluj.getMinimumSize().width)
                .addComponent(Anuluj));
        l.setVerticalGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(OK)
                .addComponent(Anuluj));
        l = new GroupLayout(PanelOpcji);
        l.setAutoCreateContainerGaps(true);
        l.setAutoCreateGaps(true);
        PanelOpcji.setLayout(l);
        PanelOpcji.setBorder(new BevelBorder(BevelBorder.RAISED));
        JScrollPane sp = new JScrollPane();
        sp.setViewportView(Lista);
        l.setHorizontalGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(sp,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
                .addComponent(PanelOpcjiWybor));
        l.setVerticalGroup(l.createSequentialGroup()
                .addComponent(sp,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
                .addComponent(PanelOpcjiWybor));
        
        l = new GroupLayout(PanelOpcjiWybor);
        PanelOpcjiWybor.setLayout(l);
        l.setAutoCreateGaps(true);
        l.setHorizontalGroup(l.createSequentialGroup()
                .addComponent(Uzyj)
                .addComponent(Dodaj)
                .addComponent(Edytuj)
                .addComponent(Usun));
        l.setVerticalGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(Uzyj)
                .addComponent(Dodaj)
                .addComponent(Edytuj)
                .addComponent(Usun));
        Lista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {        
                if(Lista.getSelectedIndex()>=0){
                    Edytuj.setEnabled(true);
                    Usun.setEnabled(true);
                }else{
                    Edytuj.setEnabled(false);
                    Usun.setEnabled(false);
                }
                if(e.getValueIsAdjusting()){  
                    if(((Jezyki)(Lista.getSelectedValue())).getName().matches(Preferencje.getInstance().getActualBase()))
                    {
                        Uzyj.setEnabled(false);
                    }else{
                        Uzyj.setEnabled(true);
                    }
                }
            }
        });
        Uzyj.setEnabled(false);
        Uzyj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = Baza.getInstance().getBaseNames().indexOf(Preferencje.getInstance().getActualBase());
                if(index>=0){
                    Jezyki j = (Jezyki) Lista.getModel().getElementAt(index);
                    j.setBorder(null);
                
                    if(Lista.getSelectedIndex()>=0){
                        Preferencje.getInstance().setActualBase(((Jezyki)Lista.getSelectedValue()).getName());
                        ((Jezyki)Lista.getSelectedValue()).setBorder(new LineBorder(Color.black));
                        Uzyj.setEnabled(false);
                        Lista.repaint();
                        Okno.getInstance().setVisible(false);
                        Okno.getInstance().WyswietlSlowko();
                        Okno.getInstance().setVisible(true);
                    }
                }else{
                    if(Lista.getSelectedIndex()>=0){
                        Preferencje.getInstance().setActualBase(((Jezyki)Lista.getSelectedValue()).getName());
                        Preferencje.getInstance().setWordNumber(0);
                        ((Jezyki)Lista.getSelectedValue()).setBorder(new LineBorder(Color.black));
                        Uzyj.setEnabled(false);
                        Lista.repaint();
                        Okno.getInstance().WyswietlSlowko();
                    }
                }
                
            }
        });
        Dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [] opcje = new String[]{"Dodaj nową","Importuj z pliku"}; 
                new Tabela(null, new Jezyk[]{}, new ArrayList<Slowka>());
            }
        });
        Edytuj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                int numer = Lista.getSelectedIndex();
                if(numer>=0){
                    ArrayList<Slowka> baza = Baza.getInstance().getBases().get(numer);
                    Jezyk[] Names = baza.get(0).getJezyki();                
                    new Tabela(Baza.getInstance().getBaseFiles()[numer],Names,baza);
                }
            }
        });
        Usun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int index = Lista.getSelectedIndex();
                if(index>=0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {   
                            boolean aktualna = false;
                            if(Baza.getInstance().getBaseNames().get(index).matches(((Jezyki)Lista.getSelectedValue()).getName())){
                                Preferencje.getInstance().setWordNumber(0);
                                Preferencje.getInstance().removeActualBase();
                                aktualna = true;
                            }
                            Baza.getInstance().getBaseFiles()[index].delete();
                            AktualizacjaListy();
                            if(aktualna){
                                Okno.getInstance().setVisible(false);
                                Okno.getInstance().WyswietlSlowko();
                                Okno.getInstance().setVisible(true);
                            }
                        }
                    }).start();
                }
            }
        });
        if(Lista.getSelectedIndex()>0){
            Edytuj.setEnabled(true);
            Usun.setEnabled(false);
        }else{
            Edytuj.setEnabled(false);
            Usun.setEnabled(false);
        }
        pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width/2-getWidth()/2,d.height/2-getHeight()/2);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        AktualizacjaListy();
        
    }
    public void AktualizacjaListy(){
        Baza.getInstance().Wczytaj();
        ArrayList<ArrayList<Slowka>> baza = Baza.getInstance().getBases();
        Jezyki[] jez = new Jezyki[baza.size()];
        for(int i=0;i<baza.size();i++){
            jez[i]=new Jezyki(Baza.getInstance().getBaseNames().get(i),baza.get(i).get(0).getJezyki());
        }
        Lista.setListData(jez);
        int index = Baza.getInstance().getBaseNames().indexOf(Preferencje.getInstance().getActualBase());
        if(index>0){
        Lista.setSelectedIndex(index);
        ((Jezyki)Lista.getSelectedValue()).setBorder(new LineBorder(Color.black,1));
        Uzyj.setEnabled(false);
        }        
    }

    @Override
    public void setVisible(boolean b) {
        AktualizacjaListy();
        super.setVisible(b);
    }
    
    JList Lista;    
    JPanel PanelOpcji,PanelWyboru,PanelOpcjiWybor;
    JButton Anuluj,OK,Uzyj,Dodaj,Edytuj,Usun;
    ArrayList<JCheckBox> listaAktualnychSlowek;
    private class CellRenderer implements ListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component p = (Component)value;
            p.setBackground(isSelected ? UIManager.getColor("Table.selectionBackground") : Color.WHITE);
            return p;
        }
        
    }
    public float getTransparency() {
        return Transparency;
    }

    public void setTransparency(float Transparency) {
        this.Transparency = Transparency;
    }
    public void przezroczystosc(){
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        JSlider slider = new JSlider(0, 100, (int)(Transparency*100));
        float temp = Transparency;
        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                Transparency = (float)((JSlider)e.getSource()).getValue()/100;
                Okno.getInstance().WyswietlSlowko();
            }
        });
        if(JOptionPane.showConfirmDialog(rootPane, slider, "Ustaw przezroczystość", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE)==JOptionPane.OK_OPTION)
            Preferencje.getInstance().setTransparency(Transparency);
        else{
            Transparency = temp;
            Okno.getInstance().WyswietlSlowko();
        }
        
    }
    private class Jezyki extends JPanel{
        private final Jezyk[] jezyki;
        private final String Name;
        private final int przerwa = 2;

        public Jezyki(String Name,Jezyk[] jezyki) {
            this.jezyki = jezyki;
            this.Name=Name;
            initComponents();
        }
        public String getName(){
            return Name;
        }
        private Image ikony(){
            BufferedImage im = new BufferedImage(30*jezyki.length+(jezyki.length-1)*przerwa, 20, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) im.getGraphics();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            for(int i=0;i<jezyki.length;i++){
                g.drawImage(jezyki[i].getImage().getScaledInstance(30, 20, Image.SCALE_FAST), 30*i+(i*przerwa), 0, rootPane);
            }
            return im;
        }
        private void initComponents(){
            JLabel icon = new JLabel(new ImageIcon(ikony()));
            JLabel lab = new JLabel(Name);
            GroupLayout l = new GroupLayout(this);
            this.setLayout(l);
            l.setHorizontalGroup(l.createSequentialGroup()
                    .addComponent(lab,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
                    .addComponent(icon));
            l.setVerticalGroup(l.createParallelGroup()
                    .addComponent(lab)
                    .addComponent(icon));
        }        
    }
}
