
import java.awt.Dimension;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.*;

public class Tabela extends JDialog{
    private final Jezyk[] ColumNames;
    private ArrayList<Slowka> Slowka;
    private File Plik;
    private Jezyk Key=null;
    public Tabela(File Base,Jezyk[] ColumnNames,ArrayList<Slowka> Slowka) {
        this.ColumNames=ColumnNames;
        this.Slowka=Slowka;
        this.Plik = Base;
        String title;
        initComponents();
        if(Base==null){
            title = "Nowa Baza";
            setTitle(title);
        }else{
            title= Base.getName();
            setTitle(title.substring(0, title.length()-4));
        }        
        if(Slowka.size()>0){
            Key = Slowka.get(0).getPrimaryKey();
            sorter.toggleSortOrder(0);
        }
    }
    private void initComponents(){
        JScrollPane sp= new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(sp);
        tbm = new DefaultTableModel(); 
        Tabela = new JTable(tbm);
        initPopup();
        Tabela.setShowVerticalLines(false);
        for(int i=0;i<ColumNames.length;i++){
            tbm.addColumn(ColumNames[i]);
        }
        sorter = new TableRowSorter<TableModel>(tbm);
        Tabela.setRowSorter(sorter);
        sorter.setSortsOnUpdates(true);
        Tabela.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    if(Tabela.getSelectedRow()==Tabela.getRowCount()-1){
                        tbm.addRow(new String[]{});
                    }                        
                }                    
            }            
        });
        if(Slowka==null){     
            tbm.addRow(new String[]{});
        }else{
            String[] temp = new String[Tabela.getColumnCount()];
            for(int i=0;i<Slowka.size();i++){
                Slowko[] s = Slowka.get(i).getSlowka();
                for(int j=0;j<s.length;j++){
                    temp[j]=s[j].getText();
                }
                tbm.addRow(temp);
            }            
        }   
        initMenuBar();
        sp.setViewportView(Tabela);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width-getWidth())/2, (d.height-getHeight())/2);
        setVisible(true);
    }
    private void initMenuBar(){
        Menu = new JMenuBar();
        JMenu m = new JMenu("Plik");
        Menu.add(m);
        JMenuItem mi= new JMenuItem("Zapisz");
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Zapisz();
            }
        });
        m.add(mi);
        m.addSeparator();
        mi = new JMenuItem("Zakończ");
        mi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        m.add(mi);
        m = new JMenu("Opcje");
        Menu.add(m);
        ds = new JMenuItem("Dodaj słówko");
        ds.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        mi= new JMenuItem("Dodaj język");
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DodajJezyk();
                if(ds.isEnabled()==false){
                    ds.setEnabled(true);
                }
            }
        });
        m.add(mi);
        if(tbm.getRowCount()==0){
            ds.setEnabled(false);
        }
        ds.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tbm.addRow(new String[]{});
            }
        });
        m.add(ds);
        setJMenuBar(Menu);
    }
    private void initPopup(){    
        TablePopup = new JPopupMenu();
        JMenuItem mi = new JMenuItem("Dodaj słówko");
        mi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(Tabela.getSelectedRow());
                tbm.insertRow(Tabela.getSelectedRow(), new String[]{});
            }
        });
        TablePopup.add(mi);
        mi = new JMenuItem("Usuń słówko");
        mi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(Tabela.getSelectedRow());
                tbm.removeRow(Tabela.getSelectedRow());
            }
        });
        TablePopup.add(mi);
        Tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  
                if(e.getButton()==MouseEvent.BUTTON3){
                    Point point = e.getPoint();
                    int currentRow = Tabela.rowAtPoint(point);
                    Tabela.setRowSelectionInterval(currentRow, currentRow);
                    TablePopup.show(Tabela.getComponentAt(point), point.x, point.y);
                }
            }
            
        });
    }
    private void DodajJezyk(){
        ArrayList<Jezyk> al = new ArrayList<Jezyk>();
        al.addAll(Arrays.asList(Jezyk.values()));
        for(int i=0;i<tbm.getColumnCount();i++){
            Jezyk j = Jezyk.valueOf(tbm.getColumnName(i));
            if(al.contains(j)){
                al.remove(j);
            }
        }
        if(al.size()>0){
            Jezyk[] jezyki=new Jezyk[al.size()];
            al.toArray(jezyki);
            Jezyk j= (Jezyk)JOptionPane.showInputDialog(this, "Wybierz język", "Dodaj język", JOptionPane.INFORMATION_MESSAGE, null, jezyki,jezyki[0]);
            if(j!=null){
                if(tbm.getColumnCount()==0&&Key==null){
                    Key=j;
                }
                tbm.addColumn(j);
                sorter.toggleSortOrder(0);
                
            }            
        }else{
            JOptionPane.showMessageDialog(rootPane, "Wykorzystałeś już wszystkie dostępne języki","Błąd",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void Zapisz(){   
        if (Tabela.isEditing())
            Tabela.getCellEditor().stopCellEditing();
        if(tbm.getRowCount()>0){
            if(Plik==null){
                String text = JOptionPane.showInputDialog(this, "Podaj nazwe bazy", "Podaj nazwe bazy", JOptionPane.INFORMATION_MESSAGE);
                if(text!=null){
                    setTitle(text);
                    Plik = new File("Baza/"+text+".xml");
                }
            }
            if(Plik!=null){
                ArrayList<Slowka> al = new ArrayList<Slowka>();
                Boolean niepelna = false;
                for(int i=0;i<Tabela.getRowCount();i++){
                    Slowko[] slowka = new Slowko[Tabela.getColumnCount()];
                    Boolean kazdy = true;
                    int jakis = 0;
                    for(int j=0;j<Tabela.getColumnCount();j++){
                        String ColumnName = Tabela.getColumnName(j);
                        int index = 0;
                        for(int k=0;k<Tabela.getColumnCount();k++){
                            if(tbm.getColumnName(k).matches(ColumnName))
                                index = k;
                        }
                        String value = (String)Tabela.getModel().getValueAt(i, index);
                        kazdy=kazdy&&(value.length()>0);
                        if(value.length()==0)
                            jakis++;
                        slowka[j]=new Slowko(value,Jezyk.valueOf(ColumnName));
                    }
                    if(kazdy){
                        al.add(new Slowka(Jezyk.valueOf(Tabela.getColumnName(0)), slowka));
                    }else if(jakis>0&&jakis<Tabela.getColumnCount()){
                        niepelna=true;
                    }
                }
                int req = JOptionPane.YES_OPTION;
                if(niepelna){
                    req = JOptionPane.showConfirmDialog(this, "W bazie występują niekompletne słówka.\nCzy chcesz zapisać tylko kompletne słówka?", "Błąd",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
                }
                if(req == JOptionPane.YES_OPTION){    
                    Baza.getInstance().Dopisz(al, Plik);
                }
                Opcje.getInstance().AktualizacjaListy();String name = Plik.getName();
                name = name.substring(0,name.length()-4);
                if(Preferencje.getInstance().getActualBase().matches(name)){
                    Okno.getInstance().setVisible(false);
                    Okno.getInstance().WyswietlSlowko();
                    Okno.getInstance().setVisible(true);
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Brak słówek w bazie","Błąd",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JTable Tabela;
    private JMenuBar Menu;
    private DefaultTableModel tbm;
    private JMenuItem ds;
    private TableRowSorter<TableModel> sorter;
    private JPopupMenu TablePopup;
}
