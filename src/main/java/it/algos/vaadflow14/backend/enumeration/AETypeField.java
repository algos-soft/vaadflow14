package it.algos.vaadflow14.backend.enumeration;

public enum AETypeField {
    //    id(),
    text(12, 8),

    integer(8, 5),

    //    pref,
    //    noBinder,
    //    calculatedTxt,
    //    calculatedInt,
    //    integernotzero,
    //    lungo,
    //    onedecimal,
    email(20, 20),
    //    password,

    combo(0, 8),

    //    multicombo,
    //    combolinkato,

    enumeration(0, 8),

    //    radio,
    //    monthdate,
    //    weekdate,
    //    date,
    //    localdate,

    localDateTime(0, 6),

    gridShowOnly(20, 0),

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
    textArea(0, 6),

    //    time,
    //    yesNo(0, 4),

    //    yesnobold,
    ugualeAlForm(0, 0),

    //    noone,
    booleano(4, 4),
    //    color,
    //    custom
    ;

    private int widthField;

    private int widthColumn;


    AETypeField(int widthField, int widthColumn) {
        this.widthField = widthField;
        this.widthColumn = widthColumn;
    }


    public int getWidthField() {
        return widthField;
    }


    public int getWidthColumn() {
        return widthColumn;
    }
}
