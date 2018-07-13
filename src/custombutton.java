import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.EventListener;
//!Egy mező a játékban, annak mindenfajta viselkedéséért felel
public class custombutton implements Serializable {
    //!Lámpa-e a mező
    private boolean state = false;
    //!(A játékos által) Hibás elhelyezésű-e a mező
    private boolean wrong = false;
    //!Meg van-e világítva a mező
    private boolean lighted = false;
    //!Milyen értékű a mező (lásd.: fieldNum)
    private fieldNum number = fieldNum.NOTNUMBEREDFIELD;
    //!A mezőhöz társított JButton ami a játékossal való interakcióért felel
    public transient JButton jb;
    //!A MainFrame osztály mely tartalmazza a mezőt
    private MainFrame container;
    //!Konstruktor amit használni fogunk
    /*
    \param cont A main frame ami tartalmazza a mezőt
    \param jin A JButton amit a mezőhöz kívánunk társítani
    */
    public custombutton(MainFrame cont, JButton jin) {
        jb = jin;
        this.container = cont;
        jb.addActionListener(new bEventlistener());
        jb.setForeground(Color.WHITE);
        jb.setMargin(new Insets(0, 0, 0, 0));
    }
    //!Setter függvény
    /*
    \param cont A main frame ami tartalmazza a mezőt
    \param jin A JButton amit a mezőhöz kívánunk társítani
    */
    public void setparams(MainFrame cont, JButton jin){
        jb = jin;
        container = cont;
        jb.addActionListener(new bEventlistener());
    }
    //!Default konstruktor (nem használatos, de a serializálás miatt szükséges

    public custombutton() {
    }
    //!A state adattagot váltja negáltjára, azaz a lámpát kapcsolja ki, illetve be

    public void click() {
        state = !state;


    }
    //!Rendereli a mező szinét annak adattagjai szerint

    public void colorRefresh() {
        jb.setText("");
        if (state) {
            jb.setBackground(Color.CYAN);
        } else if (lighted) {
            jb.setBackground(Color.YELLOW);
        } else if(number.equals(6)){
            jb.setBackground(Color.GRAY);
        } else if(number.equals(5)){
            jb.setBackground(Color.BLACK);
        }
        else if (number.getValue() >= 0 && number.getValue() <=4){
            jb.setBackground(Color.BLACK);
            jb.setText(String.valueOf(number));
        }
        if(wrong){
            jb.setBackground(Color.RED);
        }
    }
    //!Getter függvény
    public boolean isState() {
        return state;
    }
    //!Setter függvény
    public void setState(boolean st) {
        state = st;
    }
    //!Getter függvény/*
    public boolean isWrong(){
        return wrong;
    }
    //!Setter függvény
    public void setWrong(boolean wr){
        wrong = wr;
    }
    //!Setter függvény
    public void setLighted(boolean lighted) {
        this.lighted = lighted;
    }
    //!Getter függvény
    public  boolean isLighted(){
        return lighted;
    }
    //!Setter függvény
    public void setNumber(int number) {
        this.number = fieldNum.fromValue(number);
    }
    //!Getter függvény
    public int getNumber() {
        return number.getValue();
    }
    //!EventListener a a gombra kattintások kezelésére
    private class bEventlistener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                if (!container.editable) {
                    click();
                } else {
                    if (getNumber() < 6) {
                        setNumber(getNumber() + 1);
                    }else{
                        setNumber(0);
                    }
                }
        }

    }
}
