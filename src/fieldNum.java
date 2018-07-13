

//!Enumeráció azon értékekből melyeket felvehet egy mező*/
public enum fieldNum {
    ZEROFIELD(0),/*!Mező ami fekete nulla értékkel*/
    ONEFIELD(1),/*!Mező ami fekete egy értékkel*/
    TWOFIELD(2),/*!Mező ami fekete kettő értékkel*/
    THREEFIELD(3),/*!Mező ami fekete három értékkel*/
    FOURFIELD(4),/*!Mező ami fekete négy értékkel*/
    EMPTYFIELD(5),/*!Mező ami üres de fekete*/
    NOTNUMBEREDFIELD(6);/*!Mező ami nem fekete*/

    private final int number;
    //! Konstruktor integer értékből
    /*
    \param value Érték amiből fieldNum-ot akarunk készíteni
    */
    fieldNum(int value){
        number = value;
    }
    //!Integer értéket készít fieldNumból*/
    public int getValue(){
        return this.number;
    }
    //!fieldNum értéket készít integerből*/
    static fieldNum fromValue(int value) {
        for (fieldNum my: fieldNum.values()) {
            if (my.number == value) {
                return my;
            }
        }

        return null;
    }
    //!Integerrel való összehasonlítás*/
    public boolean equals(int i){
        if(i == getValue()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    //!Stringgé alakítás*/
    public String toString() {
        return String.valueOf(number);
    }
}