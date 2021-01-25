package it.algos.vaadflow14.backend.interfaces;

import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.backend.service.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: ven, 27-nov-2020
 * Time: 14:29
 */
public interface AIResult {

    boolean isValido();

    boolean isErrato();

    String getMessage();

    String getErrorMessage();

    String getValidationMessage();

    int getValore();

    void print(ALogService logger, AETypeLog typeLog);

}// end of interface

