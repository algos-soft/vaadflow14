package it.algos.vaadflow14.backend.interfaces;

import it.algos.vaadflow14.backend.enumeration.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mer, 09-ott-2019
 * Time: 23:18
 */
public interface AIPreferenza {

    String getKeyCode();

    AETypePref getType();

    Object getValue();

    Object getDefaultValue();

     boolean isVaadFlow();

     boolean isUsaCompany();

     boolean isNeedRiavvio();

     boolean isVisibileAdmin();

    String getDescrizione();

    String getNote();

}// end of interface
