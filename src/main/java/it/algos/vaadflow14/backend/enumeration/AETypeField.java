package it.algos.vaadflow14.backend.enumeration;

public enum AETypeField {
    //    id(),
    text(10, 14),

    email(20, 18),

    phone(11, 11),

    password(10, 14),

    textArea(8, 18),

    integer(5, 8),

    lungo(5, 8),

    localDateTime(9, 0),

    localDate(8, 0),

    localTime(6, 0),

//    meseShort(6, 0, AETypeData.meseShort),
//
//    meseNormal(6, 0, AETypeData.meseNormal),
//
//    meseLong(6, 0, AETypeData.meseLong),
//
//    weekShort(6, 0, AETypeData.weekShort),
//
//    weekShortMese(6, 0, AETypeData.weekShortMese),
//
//    weekLong(6, 0, AETypeData.weekShort),

    preferenza(6, 0),

    //    noBinder,
    //    calculatedTxt,
    //    calculatedInt,
    //    integernotzero,
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

    private AETypeData data;


    AETypeField(int widthColumn, int widthField) {
        this(widthColumn, widthField, AETypeData.dateNormal);
    }


    AETypeField(int widthColumn, int widthField, AETypeData data) {
        this.widthColumn = widthColumn;
        this.widthField = widthField;
        this.data = data;
    }


    public int getWidthColumn() {
        return widthColumn;
    }


    public int getWidthField() {
        return widthField;
    }


    public AETypeData getData() {
        return data;
    }
}
