package it.algos.vaadflow14.backend.enumeration;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mer, 26-set-2018
 * Time: 07:39
 */
public enum AELogType implements AILogType {

    system("system"),

    setup("setup"),

    startup("startup"),

    checkData("checkData"),

    nuovo("newEntity"),

    edit("edit"),

    modifica("modifica"),

    delete("delete"),

    deleteAll("deleteAll"),

    debug("debug"),

    info("info"),

    warn("warn"),

    error("error"),

    importo("import"),

    export("export"),

    download("download"),

    update("update"),

    elabora("elabora"),

    upload("upload"),

    reset("reset"),

    utente("utente"),

    password("password");


    private String tag;


    AELogType(String tag) {
        this.tag = tag;
    }


    public static AELogType getType(String tag) {
        for (AELogType type : values()) {
            if (type.getTag().equals(tag)) {
                return type;
            }
        }

        return null;
    }


    public static List<String> getAll() {
        List<String> lista = new ArrayList<>();

        for (AELogType type : values()) {
            lista.add(type.tag);
        }

        return lista;
    }


    @Override
    public String getTag() {
        return tag;
    }


}

