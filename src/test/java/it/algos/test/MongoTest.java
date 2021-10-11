package it.algos.test;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.application.*;
import static it.algos.vaadflow14.backend.application.FlowCost.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.wrapper.*;
import static org.junit.Assert.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.io.*;
import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 11-ott-2021
 * Time: 17:13
 */
public abstract class MongoTest extends ATest{

    protected void printCount(final String simpleName, final int size, final String property, final Object value) {
        System.out.println(String.format(String.format("La classe %s ha %s entities filtrate con %s=%s", simpleName, size, property, value)));
        System.out.println(VUOTA);
    }

    protected void printCount(final Class clazz, final int previstoIntero, final int ottenutoIntero, final boolean risultatoEsatto) {
        if (clazz == null) {
            System.out.println("Manca la entityClazz");
            return;
        }
        if (ottenutoIntero == previstoIntero) {
            if (risultatoEsatto) {
                System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono esattamente quelli previsti (obbligatori)", clazz.getSimpleName(), ottenutoIntero));
            }
            else {
                System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono uguali a quelli indicativamente previsti (facoltativi)", clazz.getSimpleName(), ottenutoIntero));
            }
        }
        else {
            if (ottenutoIntero > previstoIntero) {
                if (risultatoEsatto) {
                    System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono più dei %s previsti e non va bene", clazz.getSimpleName(), ottenutoIntero, previstoIntero));
                }
                else {
                    System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono più dei %s indicativamente previsti", clazz.getSimpleName(), ottenutoIntero, previstoIntero));
                }
            }
            else {
                if (risultatoEsatto) {
                    System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono meno dei %s previsti e non va bene", clazz.getSimpleName(), ottenutoIntero, previstoIntero));
                }
                else {
                    System.out.println(String.format("La collezione '%s' contiene %s records (entities) totali che sono meno dei %s indicativamente previsti", clazz.getSimpleName(), ottenutoIntero, previstoIntero));
                }
            }
        }

        if (risultatoEsatto) {
            assertEquals(previstoIntero, ottenutoIntero);
        }
    }

    protected void printCount(final Class clazz, final String propertyName, final Serializable propertyValue, final int previstoIntero, final int ottenutoIntero) {
        String clazzName;
        if (clazz == null) {
            System.out.println(String.format("Manca la entityClazz"));
            return;
        }
        else {
            clazzName = clazz.getSimpleName();
        }

        if (ottenutoIntero == previstoIntero) {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) filtrati con %s=%s che sono quelli previsti", clazzName, ottenutoIntero, propertyName, propertyValue));
        }
        else {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) filtrati con %s=%s che non sono i %s previsti", clazzName, ottenutoIntero, propertyName, propertyValue, previstoIntero));
        }
    }


    protected void printCount(final Class clazz, AFiltro filtro, final int previstoIntero, final int ottenutoIntero) {
        String clazzName;
        String key = filtro.getCriteria().getCriteriaObject().keySet().toString();
        String value = filtro.getCriteria().getCriteriaObject().values().toString();

        if (clazz == null) {
            System.out.println(String.format("Manca la entityClazz"));
            return;
        }
        else {
            clazzName = clazz.getSimpleName();
        }

        if (ottenutoIntero == previstoIntero) {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) filtrati con %s = %s che sono quelli previsti", clazzName, ottenutoIntero, key, value));
        }
        else {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) filtrati con %s = %s che non sono i %s previsti", clazzName, ottenutoIntero, key, value, previstoIntero));
        }
    }

    protected void printCount(final Class clazz, Map<String, AFiltro> mappaFiltri, final int previstoIntero, final int ottenutoIntero) {
        String clazzName;
        AFiltro filtro;
        String property;
        String value;
        if (clazz == null) {
            System.out.println(String.format("Manca la entityClazz"));
            return;
        }
        else {
            clazzName = clazz.getSimpleName();
        }

        if (ottenutoIntero == previstoIntero) {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) che sono quelli previsti", clazzName, ottenutoIntero));
            for (String key : mappaFiltri.keySet()) {
                filtro = mappaFiltri.get(key);
                property = filtro.getCriteria().getCriteriaObject().keySet().toString();
                value = filtro.getCriteria().getCriteriaObject().values().toString();
                System.out.println(String.format("Filtro %s = %s", property, value));
            }
        }
        else {
            System.out.println(String.format("La collezione '%s' contiene %s records (entities) che non sono i %s previsti", clazzName, ottenutoIntero, previstoIntero));
            for (String key : mappaFiltri.keySet()) {
                filtro = mappaFiltri.get(key);
                property = filtro.getCriteria().getCriteriaObject().keySet().toString();
                value = filtro.getCriteria().getCriteriaObject().values().toString();
                System.out.println(String.format("Filtro %s = %s", property, value));
            }
        }
    }


    protected void printEntityBeanFromKeyId(final Class clazz, final Serializable keyPropertyValue, final AEntity entityBean, final int previstoIntero) {
        printEntityBeanFromProperty(clazz, FlowCost.FIELD_ID, keyPropertyValue, entityBean, previstoIntero);
    }

    protected void printEntityBeanFromProperty(final Class clazz, final String propertyName, final Serializable propertyValue, final AEntity entityBean, final int previstoIntero) {
        if (clazz == null) {
            System.out.print("Non esiste la classe indicata");
            System.out.println(VUOTA);
            System.out.println(VUOTA);
            return;
        }

        if (entityBean == null) {
            System.out.println(String.format("non è stata creata nessuna entityBean di classe %s", clazz.getSimpleName()));
            System.out.println(VUOTA);
            System.out.println(VUOTA);
            return;
        }

        System.out.print(String.format("%s%s%s: ", propertyName, UGUALE_SEMPLICE, propertyValue));
        printMappa(entityBean);
    }

}
