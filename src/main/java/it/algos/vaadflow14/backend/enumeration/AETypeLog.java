package it.algos.vaadflow14.backend.enumeration;

import it.algos.vaadflow14.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mer, 26-set-2018
 * Time: 07:39
 */
public enum AETypeLog implements AILogType {

    system("system"),
    setup("setup"),
    startup("startup"),
    checkData("checkData"),
    preferenze("preferenze"),
    nuovo("newEntity"),
    edit("edit"),
    modifica("modifica"),
    delete("delete"),
    deleteAll("deleteAll"),
    debug("debug"),
    info("info"),
    warn("warn"),
    error("error"),
    wizard("wizard"),
    importo("import"),
    export("export"),
    download("download"),
    update("update"),
    elabora("elabora"),
    upload("upload"),
    reset("reset"),
    utente("utente"),
    password("password"),
    ;

    private String tag;


    AETypeLog(String tag) {
        this.tag = tag;
    }


    public static AETypeLog getType(String tag) {
        for (AETypeLog type : values()) {
            if (type.getTag().equals(tag)) {
                return type;
            }
        }

        return null;
    }


    public static List<String> getAll() {
        List<String> lista = new ArrayList<>();

        for (AETypeLog type : values()) {
            lista.add(type.tag);
        }

        return lista;
    }


    @Override
    public String getTag() {
        return tag;
    }

}

