import javax.swing.*;
//!Main class, csak a main függvényt tartalmazza
public class Main {
/*! \mainpage
*
* \section intro_sec Bevezetés
*A program célja:
*A program az “Akari” más néven a “Light up” játékot valósítja meg, amelyet majd a végfelhaszáló játszhat majd.
* \subsection A A játék alapszabályai:
*1.	A számmal jelölt négyzetek mellett a számmal megadott mennyiségű lámpát kötelező elhelyezni. Egy lámpa akkor van mellette ha horizontálisan vagy vertikálisan érintkezik vele. Az átlóban elhelyezett lámpákat nem kell beleszámolni
*2.	Vannak üres, számmal nem jelölt négyzetek ezek mindegyikét meg kell világítani. A lámpák szintén vízszintesen és függőlegesen világítanak csak, egészen a pálya széléig.
*3.	Vannak számmal nem jelölt négyzetek ezeken keresztül nem terjed a fény, ahogy a számmal jelölteken keresztül sem.
*4.	Lámpát csak üres, nemfekete négyzetekre lehet rakni
*/

    /*! A program belépési pontja, létrehoz egy példányt a játékból*/
    public static void main(String[] args) {
        MainFrame m = new MainFrame();
        m.setVisible(true);


    }
}
