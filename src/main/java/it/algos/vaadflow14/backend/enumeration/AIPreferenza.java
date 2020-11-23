package it.algos.vaadflow14.backend.enumeration;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mer, 09-ott-2019
 * Time: 23:18
 */
public interface AIPreferenza {

    public String getKeyCode();

    public String getDescrizione();

    public AETypePref getType();

    public boolean isCompanySpecifica();

    public Object getValue();

}// end of interface
