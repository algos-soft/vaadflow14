package it.algos.vaadflow14.backend.exceptions;

import it.algos.vaadflow14.backend.entity.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 25-ago-2021
 * Time: 20:00
 */
public class AlgosException extends Exception {

    private AEntity entityBean;

    private String stack;

    public AlgosException(String message) {
        super(message);
    }

    public AlgosException(Throwable cause, AEntity entityBean) {
        super(cause);
        this.entityBean = entityBean;
    }

    public AlgosException(Throwable cause, AEntity entityBean, String message) {
        super(message, cause);
        this.entityBean = entityBean;
    }

    public AlgosException(Throwable cause, AEntity entityBean, String message, String stack) {
        super(message, cause);
        this.entityBean = entityBean;
        this.stack = stack;
    }

    public AEntity getEntityBean() {
        return entityBean;
    }

    public String getStack() {
        return stack;
    }

}
