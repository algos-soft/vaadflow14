package it.algos.vaadflow14.wizard;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow14.ui.MainLayout;
import it.algos.vaadflow14.wizard.scripts.WizDialogNewProject;
import it.algos.vaadflow14.wizard.scripts.WizDialogUpdateProject;
import it.algos.vaadflow14.wizard.scripts.WizElaboraNewProject;
import it.algos.vaadflow14.wizard.scripts.WizElaboraUpdateProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.io.File;

import static it.algos.vaadflow14.backend.application.FlowCost.*;
import static it.algos.vaadflow14.wizard.scripts.WizCost.TITOLO_MODIFICA_PROGETTO;
import static it.algos.vaadflow14.wizard.scripts.WizCost.TITOLO_NUOVO_PROGETTO;


/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 10-ott-2020
 * Time: 17:56
 * Utilizzato da VaadFlow14 direttamente, per creare/aggiornare un nuovo progetto esterno <br>
 * Utilizzato dal progetto corrente, per importare/aggiornare il codice da VaadFlow14 <br>
 * Utilizzato da VaadFlow14 direttamente, per creare nuovi packages <br>
 * Utilizzato dal progetto corrente, per creare nuovi packages <br>
 */
@Route(value = TAG_WIZ, layout = MainLayout.class)
@SpringComponent
@UIScope
@PageTitle(TAG_WIZ)
@Qualifier(TAG_WIZ)
@CssImport("./styles/shared-styles.css")
public class Wizard extends VerticalLayout {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;


    /**
     * Utilizzato dall'interno di VaadFlow per creare/aggiornare un altro progetto <br>
     */
    @Autowired
    private WizDialogNewProject dialogNewProject;

    /**
     * Utilizzato dall'interno di un qualsiasi progetto per importare/aggiornare il codice da VaadFlow14 <br>
     */
    @Autowired
    private WizDialogUpdateProject dialogUpdateProject;

    /**
     * Utilizzato dall'interno di VaadFlow per creare/aggiornare un altro progetto <br>
     */
    @Autowired
    private WizElaboraNewProject elaboraNewProject;

    /**
     * Utilizzato dall'interno di un qualsiasi progetto per importare/aggiornare il codice da VaadFlow14 <br>
     */
    @Autowired
    private WizElaboraUpdateProject elaboraUpdateProject;

    private boolean isProgettoBase = false;


    /**
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     */
    public Wizard() {
        super();
    }// end of Vaadin/@Route constructor


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le (eventuali) istanze @Autowired <br>
     * Questo metodo viene chiamato subito dopo che il framework ha terminato l' init() implicito <br>
     * del costruttore e PRIMA di qualsiasi altro metodo <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l' ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void postConstruct() {
        initView();
    }


    /**
     * Qui va tutta la logica iniziale della view <br>
     */
    protected void initView() {
        this.titolo();
        selezioneProgettoInUso();
        if (isProgettoBase) {
            paragrafoNewProject();
        } else {
            paragrafoUpdateProject();
        }
    }


    public void titolo() {
        H1 titolo = new H1("Gestione Wizard");
        titolo.getElement().getStyle().set("color", "green");
        this.add(titolo);
    }


    /**
     * Seleziona se parte da VaadFlow o da un altro progetto <br>
     * Comportamento diverso:
     * 1) da VaadFlow può creare nuovi progetti
     * 2) da un progetto derivato può 'aggiornare' la propria cartella VaadFlow
     */
    public void selezioneProgettoInUso() {
        String pathUserDir = System.getProperty("user.dir") + SLASH;
        File unaDirectory = new File(pathUserDir);
        String nomeDirectory = unaDirectory.getName();
        isProgettoBase = nomeDirectory.equals(NAME_VAADFLOW);
    }


    public void paragrafoNewProject() {
        H3 paragrafo = new H3(TITOLO_NUOVO_PROGETTO);
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);
        Label label = new Label("Dialogo per selezionare alcuni flags iniziali");

        Button bottone = new Button("Open dialog");
        bottone.getElement().setAttribute("theme", "primary");
        dialogNewProject = appContext.getBean(WizDialogNewProject.class);
        bottone.addClickListener(event -> dialogNewProject.open(this::elaboraNewProject));

        this.add(new HorizontalLayout(label, bottone));
        this.add(new H1());
    }


    private void elaboraNewProject() {
        dialogNewProject.close();
        elaboraNewProject.esegue();
    }


    public void paragrafoUpdateProject() {
        H3 paragrafo = new H3(TITOLO_MODIFICA_PROGETTO);
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);
        this.add(new Label("Esistente in qualsiasi directory"));

        Button bottone = new Button("Update project");
        bottone.getElement().setAttribute("theme", "pimary");
        bottone.addClickListener(event -> dialogUpdateProject.open(this::elaboraUpdateProject));
        this.add(bottone);
        this.add(new H1());
    }


    private void elaboraUpdateProject() {
        elaboraUpdateProject.esegue();
        dialogUpdateProject.close();
    }

}
