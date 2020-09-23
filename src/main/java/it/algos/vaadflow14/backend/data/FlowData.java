package it.algos.vaadflow14.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.packages.anagrafica.via.Via;
import it.algos.vaadflow14.backend.packages.company.Company;
import it.algos.vaadflow14.backend.packages.crono.anno.Anno;
import it.algos.vaadflow14.backend.packages.crono.giorno.Giorno;
import it.algos.vaadflow14.backend.packages.crono.mese.Mese;
import it.algos.vaadflow14.backend.packages.crono.secolo.Secolo;
import it.algos.vaadflow14.backend.packages.geografica.regione.Regione;
import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.backend.packages.preferenza.Preferenza;
import it.algos.vaadflow14.backend.packages.security.Utente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 20-ott-2018
 * Time: 08:53
 * <p>
 * Poiché siamo in fase di boot, la sessione non esiste ancora <br>
 * Questo vuol dire che eventuali classi @VaadinSessionScope
 * NON possono essere iniettate automaticamente da Spring <br>
 * Vengono costruite con la BeanFactory <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class FlowData extends AData {


    /**
     * Check iniziale di alcune collections <br>
     * Crea un elenco specifico di collections che implementano il metodo 'reset' nella classe xxxLogic <br>
     * Controlla se le collections sono vuote e, nel caso, le ricrea <br>
     * Puo essere sovrascritto, ma SENZA invocare il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     */
    public void initData() {
        checkSingolaCollection(Preferenza.class);

        checkSingolaCollection(Secolo.class);
        checkSingolaCollection(Anno.class);
        checkSingolaCollection(Mese.class);
        checkSingolaCollection(Giorno.class);

        checkSingolaCollection(Stato.class);
        checkSingolaCollection(Regione.class);

        checkSingolaCollection(Company.class);
        checkSingolaCollection(Utente.class);
        checkSingolaCollection(Via.class);
    }

}
