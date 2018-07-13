import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import  java.util.List;

//!A játék egy példánya
/*!Mindennemű irányítáért ez az osztály felel és függvényei valósítják meg a felhasználóval való interakciót,
* illetve a játék logikáját.
*/
public class MainFrame extends JFrame {
    //!Az ablak mérete képpontban megadva
    static final Dimension windowsize = new Dimension(600,600);
    //!A pálya mérete mezők számában megadva
    static final Dimension matrixsize = new Dimension(7,7);
    //!5 miliszekundomos timer ami ekkora időközönként lefuttatja a renderelést
    private Timer timer = new Timer(5, new customactionlistener());
    //! Kétdimenziós lista ami tárolja a custombutton objektumok mátrixát
    private List<List<custombutton>> buttonarr = new ArrayList<>();
    //! Kétdimenziós lista ami tárolja a JButton-ok mátrixát
    private List<List<JButton>> jButtons = new ArrayList<>();
    //! Az üres, megvlágítatlan cellák száma
    int greyCellCount = 0;
    //!Boolean ami megmutatja van-e hibás elhelyezés a játékos által
    boolean isThereWrongCell = false;
    //!Boolean ami megmutatja be van-e kapcsolva a szerkeztési mód
    boolean editable = false;
    //!Default konstruktor
    public MainFrame(){
        for (int i=0; i<matrixsize.height; ++i) {
            ArrayList<custombutton> temp = new ArrayList<custombutton>();
            ArrayList<JButton> tempjb = new ArrayList<JButton>();
            for (int j = 0; j< matrixsize.width; j++){
                JButton tempJB = new JButton();
                custombutton tempButton = new custombutton(this, tempJB);
                //tempButton.jb.addActionListener(new customactionlistener());
                tempjb.add(tempJB);
                temp.add(tempButton);

            }
            jButtons.add(tempjb);
            buttonarr.add(temp);
        }


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(windowsize.width,windowsize.height+30);
        this.setTitle("Light up!");
        this.setBackground(Color.lightGray);
        JPanel mainpanel = new JPanel();
        GridLayout mainlayout = new GridLayout(matrixsize.height,matrixsize.width);
        mainpanel.setLayout(mainlayout);

        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        menu.add(file);
        JMenu options = new JMenu("Options");
        menu.add(options);



        JMenuItem saveitem = new JMenuItem("Save");
        saveitem.addActionListener(new customactionlistener());
        file.add(saveitem);

        JMenuItem loaditem = new JMenuItem("Load");
        loaditem.addActionListener(new customactionlistener());
        file.add(loaditem);

        JCheckBoxMenuItem editablebox = new JCheckBoxMenuItem("Edit mode");
        editablebox.addActionListener(new customactionlistener());
        options.add(editablebox);

        this.setJMenuBar(menu);
        this.add(mainpanel);

        timer.start();

        for(int i = 0; i< matrixsize.height; i++){
            for (int j= 0; j <matrixsize.width; j++){
                custombutton temp = buttonarr.get(i).get(j);
                temp.jb.setSize(windowsize.width/matrixsize.width,windowsize.height/matrixsize.width);
                temp.jb.addActionListener(new customactionlistener());
                mainpanel.add(temp.jb);
            }
        }

        this.add(mainpanel);
        load("default");
    }
    //!Saját ActionListener
    /*! A menük kezeléséhez kell, ez felel a FElső menüsáv összes gombjának függvényhívásaiért.
    *A Save Load gombok és az Edit checkbox, illetve a timer iratkozik fel rá.
    * Szintén ez felel a timer által megvalósított körönkénti renderelés hívásáért.
    */
    private class customactionlistener implements ActionListener{

        public void actionPerformed(ActionEvent e){

            if (e.getSource().getClass() == JMenuItem.class && ((JMenuItem)e.getSource()).getText() == "Save"){
                save();
            }
            else if (e.getSource().getClass() == JMenuItem.class && ((JMenuItem)e.getSource()).getText() == "Load"){
                load();
            }else if (e.getSource().getClass() == JCheckBoxMenuItem.class && ((JCheckBoxMenuItem)e.getSource()).getText() == "Edit mode"){
                editable = ((JCheckBoxMenuItem)( e.getSource())).getState();
            }else {
                renderLights();
                timer.restart();
            }

        }
    }
    //! A játék működéséért felelős függvény
    /*! Körönként fut le, azaz a timer által beállított időközönként hívódik újra.
    * Rendereli a fényeket, és a játék logikáját foglalja magában, mint például a hibák és üres cellák ellenőrzése
    */
    private void renderLights(){

        greyCellCount = 0;
        isThereWrongCell = false;

        for(int i = 0; i< matrixsize.height; i++){
            for (int j= 0; j <matrixsize.width; j++){
                buttonarr.get(i).get(j).setLighted(false);
                buttonarr.get(i).get(j).setWrong(false);
            }
        }

        for(int i = 0; i< matrixsize.height; i++){
            for (int j= 0; j <matrixsize.width; j++){
                if(buttonarr.get(i).get(j).isState()){
                    for(int x = i+1; x< matrixsize.height; x++){
                        if(buttonarr.get(x).get(j).getNumber()!= 6) break;
                        if(buttonarr.get(x).get(j).isState()){
                            buttonarr.get(x).get(j).setWrong(true);
                        }else {
                            buttonarr.get(x).get(j).setLighted(true);
                        }
                    }
                    for(int x = i-1; x>=0 ; x--){
                        if(buttonarr.get(x).get(j).getNumber() !=6) break;
                        if(buttonarr.get(x).get(j).isState()){
                            buttonarr.get(x).get(j).setWrong(true);
                        }else {
                            buttonarr.get(x).get(j).setLighted(true);
                        }
                    }
                    for(int y = j+1; y< matrixsize.width; y++){
                        if(buttonarr.get(i).get(y).getNumber() !=6) break;
                        if(buttonarr.get(i).get(y).isState()){
                            buttonarr.get(i).get(y).setWrong(true);
                        }else {
                            buttonarr.get(i).get(y).setLighted(true);
                        }
                    }
                    for(int y = j-1; y>=0 ; y--){
                        if(buttonarr.get(i).get(y).getNumber() !=6) break;
                        if(buttonarr.get(i).get(y).isState()){
                            buttonarr.get(i).get(y).setWrong(true);
                        }else{
                            buttonarr.get(i).get(y).setLighted(true);
                        }

                    }
                }
            }
        }
        for(int i = 0; i< matrixsize.height; i++){
            for (int j= 0; j <matrixsize.width; j++){
                custombutton temp = buttonarr.get(i).get(j);
                temp.colorRefresh();
                if(temp.getNumber() == 6 && !temp.isLighted() && !temp.isState()) greyCellCount++;
                if(temp.isWrong()) isThereWrongCell = true;
            }
        }
        for(int i = 0; i< matrixsize.height; i++) {
            for (int j = 0; j < matrixsize.width; j++) {
                custombutton temp = buttonarr.get(i).get(j);
                int lightedNerbies = 0;
                if(temp.getNumber() < 5){
                    custombutton[] nearby =  new custombutton[]{
                            buttonarr.get(i+1).get(j),
                            buttonarr.get(i).get(j+1),
                            buttonarr.get(i-1).get(j),
                            buttonarr.get(i).get(j-1),
                    };
                    for (custombutton temp2: nearby) {
                            if(temp2.isState()){
                                lightedNerbies++;
                            }
                    }
                    if(!(temp.getNumber()==lightedNerbies)){
                        isThereWrongCell = true;
                    }
                }
            }
        }
        this.setTitle("Light up! | Remaining cells: "+greyCellCount);
        if(!isThereWrongCell && greyCellCount == 0){
            JOptionPane winNotif = new JOptionPane();
            if(JOptionPane.showConfirmDialog(this,"Do you want to start again","You won!",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                load();
            }else{
                System.exit(0);
            }
        }

    }

    //! A fáljból való betöltésért felelős
    /*! Szerializálva tölt be a felugró ablakban megadott fáljnévből.
    */
    public void load(){
        try {
            String input = JOptionPane.showInputDialog(null,
                    "Choose save name:", "");
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(input));
            buttonarr = (List<List<custombutton>>)reader.readObject();
            reader.close();
            for (int i=0; i<matrixsize.height; ++i) {
                for (int j = 0; j< matrixsize.width; j++){
                    buttonarr.get(i).get(j).setparams(this, jButtons.get(i).get(j));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //! A fáljból való betöltésért felelős
    /*! Szerializálva tölt be a paraméterben megadott fáljnévből.
    \param input Bemeneti fálj neve
    */
    public void load(String input){
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(input));
            buttonarr = (List<List<custombutton>>)reader.readObject();
            reader.close();
            for (int i=0; i<matrixsize.height; ++i) {
                for (int j = 0; j< matrixsize.width; j++){
                    buttonarr.get(i).get(j).setparams(this, jButtons.get(i).get(j));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //! A fáljba való mentésért felelős
    /*! Szerializálva ment a felugró ablakban megadott fáljnévbe.
    */
    public void save(){
        try {
            String input = JOptionPane.showInputDialog(null,
                    "Choose save name:", "");
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(input));
            writer.writeObject(buttonarr);
            writer.close();



        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}
