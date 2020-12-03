package it.algos.vaadflow14.backend.interfaces;

import it.algos.vaadflow14.backend.enumeration.AETypePref;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mer, 09-ott-2019
 * Time: 23:18
 */
public interface AIPreferenza {

    String getKeyCode();

    String getDescrizione();

    AETypePref getType();

    boolean isCompanySpecifica();

    Object getValue();

    Object getDefaultValue();

    String getNote();


}// end of interface
