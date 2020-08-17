package it.algos.vaadflow14.backend.enumeration;

public enum AETypeField {
    //    id(),
    text(8, 18),

    email(20, 18),

    phone(11, 11),

    textArea(8, 18),

    integer(5, 8),

    //    pref,
    //    noBinder,
    //    calculatedTxt,
    //    calculatedInt,
    //    integernotzero,
    //    lungo,
    //    onedecimal,
    //    password,

    combo(8, 0),

    //    multicombo,
    //    combolinkato,

    enumeration(8, 0),

    //    radio,
    //    monthdate,
    //    weekdate,
    //    date,
    //    localdate,

    localDateTime(6, 0),

    gridShowOnly(0, 20),

    //    localtime,
    //    dateNotEnabled,
    //    decimal,
    //    checkbox,
    //    checkboxreverse,
    //    checkboxlabel,
    //    link,
    //    image,
    //    resource,
    //    vaadinIcon,
    //    json,

    //    time,
    //    yesNo(0, 4),

    //    yesnobold,
    ugualeAlForm(0, 0),

    //    noone,
    booleano(4, 4),
    //    color,
    //    custom
    ;

    private int widthColumn;
    private int widthField;



    AETypeField(int widthColumn,int widthField) {
        this.widthColumn = widthColumn;
        this.widthField = widthField;
    }


    public int getWidthColumn() {
        return widthColumn;
    }


    public int getWidthField() {
        return widthField;
    }


}
