package it.algos.vaadflow14.wizard;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow14.backend.service.ALogService;
import it.algos.vaadflow14.ui.MainLayout;
import it.algos.vaadflow14.wizard.enumeration.AEFlag;
import it.algos.vaadflow14.wizard.scripts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

import static it.algos.vaadflow14.backend.application.FlowCost.TAG_WIZ;
import static it.algos.vaadflow14.wizard.scripts.WizCost.*;


/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 10-ott-2020
 * Time: 17:56
 * Utilizzato da VaadFlow14 direttamente, per creare/aggiornare un nuovo progetto esterno <br>
 * Utilizzato dal progetto corrente, per importare/aggiornare il codice da VaadFlow14 <br>
 * Utilizzato dal progetto corrente, per creare/aggiornare nuovi packages <br>
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
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ALogService logger;


    /**
     * Utilizzato dall'interno di VaadFlow14 per creare un nuovo progetto <br>
     */
    @Autowired
    private WizDialogNewProject dialogNewProject;

    /**
     * Utilizzato dall'interno di VaadFlow14 per aggiornare un progetto esistente <br>
     * Utilizzato dall'interno di un qualsiasi progetto per importare/aggiornare il codice da VaadFlow14 <br>
     */
    @Autowired
    private WizDialogUpdateProject dialogUpdateProject;

    /**
     * Utilizzato dall'interno di un qualsiasi progetto per creare nuovi packages <br>
     */
    @Autowired
    private WizDialogNewPackage dialogNewPackage;

    /**
     * Utilizzato dall'interno di un qualsiasi progetto per aggiornare i packages esistenti <br>
     */
    @Autowired
    private WizDialogUpdatePackage dialogUpdatePackage;

    /**
     * Utilizzato dall'interno di un qualsiasi progetto per aggiornare la documentazione delle varie classi standard <br>
     */
    @Autowired
    private WizDialogDocPackages dialogDocPackages;

    /**
     * Utilizzato dall'interno di un qualsiasi progetto per aggiornare la directory wizard di VaadFlow14 <br>
     */
    @Autowired
    private WizDialogFeedbackWizard dialogFeedbackWizard;

    /**
     * Utilizzato dall'interno di VaadFlow14 per creare un nuovo progetto <br>
     */
    @Autowired
    private WizElaboraNewProject elaboraNewProject;

    /**
     * Utilizzato dall'interno di VaadFlow14 per aggiornare un progetto esistente <br>
     * Utilizzato dall'interno di un qualsiasi progetto per importare/aggiornare il codice da VaadFlow14 <br>
     */
    @Autowired
    private WizElaboraUpdateProject elaboraUpdateProject;

    /**
     * Utilizzato dall'interno di un qualsiasi progetto per creare nuovi packages <br>
     */
    @Autowired
    private WizElaboraNewPackage elaboraNewPackage;

    /**
     * Utilizzato dall'interno di un qualsiasi progetto per aggiornare i packages esistenti <br>
     */
    @Autowired
    private WizElaboraUpdatePackage elaboraUpdatePackage;

    /**
     * Utilizzato dall'interno di un qualsiasi progetto per aggiornare la documentazione delle varie classi standard <br>
     */
    @Autowired
    private WizElaboraDocPackages elaboraDocPackages;


    /**
     * Utilizzato dall'interno di un qualsiasi progetto per aggiornare la directory wizard di VaadFlow14 <br>
     */
    @Autowired
    private WizElaboraFeedbackWizard elaboraFeedbackWizard;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    private WizService wizService;


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
        this.setMargin(true);
        this.setPadding(false);
        this.setSpacing(false);
        this.titolo();

        wizService.printStart();
        wizService.fixVariabiliIniziali();
        wizService.printInfo("Apertura iniziale della view Wizard");

        if (AEFlag.isBaseFlow.is()) {
            paragrafoNewProject();
            paragrafoUpdateProject();
        }
        else {
            paragrafoUpdateProject();
            paragrafoNewPackage();
            paragrafoUpdatePackage();
            paragrafoDocPackages();
            paragrafoFeedBackWizard();
        }
    }


    public void titolo() {
        H1 titolo = new H1("Gestione Wizard");
        titolo.getElement().getStyle().set("color", "green");
        this.add(titolo);
    }


    public void paragrafoNewProject() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3(TITOLO_NUOVO_PROGETTO);
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);

        layout.add(new Label("Crea un nuovo project IntelliJIdea, nella directory 'IdeaProjects'"));
        layout.add(new Label("Seleziona un progetto vuoto tra quelli esistenti"));
        layout.add(new Label("Regola alcuni flags iniziali"));
        layout.add(new Label("Il progetto va poi spostato nella directory 'operativi'"));
        layout.add(new Label("Il progetto va poi aggiunto alla enumeration AEProgetto"));

        Button bottone = new Button("Crea project");
        bottone.getElement().setAttribute("theme", "primary");
        dialogNewProject = appContext.getBean(WizDialogNewProject.class);
        bottone.addClickListener(event -> dialogNewProject.open(this::elaboraNewProject));

        layout.add(bottone);
        this.add(layout);
        this.add(new H2());
    }


    private void elaboraNewProject() {
        dialogNewProject.close();
        elaboraNewProject.esegue();
    }


    public void paragrafoUpdateProject() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3();
        paragrafo.setText(AEFlag.isBaseFlow.is() ? TITOLO_MODIFICA_PROGETTO : TITOLO_MODIFICA_QUESTO_PROGETTO);
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);

        if (AEFlag.isBaseFlow.is()) {
            layout.add(new Label("Seleziona un progetto dalla enumeration AEProgetto"));
        }
        else {
            layout.add(new Label("Update del modulo vaadflow14 di questo progetto"));
        }
        layout.add(new Label("Regola alcuni flags per le modifiche"));

        Button bottone = new Button("Update project");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> dialogUpdateProject.open(this::elaboraUpdateProject));

        layout.add(bottone);
        this.add(layout);
        this.add(new H2());
    }


    private void elaboraUpdateProject() {
        elaboraUpdateProject.esegue();
        dialogUpdateProject.close();
    }


    public void paragrafoNewPackage() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3(TITOLO_NEW_PACKAGE);
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);

        layout.add(new Label("Creazione di un nuovo package"));
        layout.add(new Label("Regola alcuni flags di possibili opzioni"));

        Button bottone = new Button("New package");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> dialogNewPackage.open(this::elaboraNewPackage));

        layout.add(bottone);
        this.add(layout);
        this.add(new H2());
    }


    private void elaboraNewPackage() {
        elaboraNewPackage.esegue();
        dialogNewPackage.close();
    }


    public void paragrafoUpdatePackage() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3(TITOLO_UPDATE_PACKAGE);
        paragrafo.getElement().getStyle().set("color", "blue");

        this.add(paragrafo);

        layout.add(new Label("Update di un package esistente"));
        layout.add(new Label("Seleziona il package dalla lista di quelli esistenti"));
        layout.add(new Label("Regola alcuni flags di possibili opzioni"));

        Button bottone = new Button("Update package");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> dialogUpdatePackage.open(this::elaboraUpdatePackage));

        layout.add(bottone);
        this.add(layout);
        this.add(new H2());
    }

    private void elaboraUpdatePackage() {
        elaboraUpdatePackage.esegue();
        dialogUpdatePackage.close();
    }

    public void paragrafoDocPackages() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3(TITOLO_DOC_PACKAGES);
        paragrafo.getElement().getStyle().set("color", "blue");

        this.add(paragrafo);

        layout.add(new Label("Documentation delle classi standard dei package esistenti"));
        layout.add(new Label("Seleziona quali classi modificare"));

        Button bottone = new Button("Doc packages");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> dialogDocPackages.open(this::elaboraDocPackages));

        layout.add(bottone);
        this.add(layout);
        this.add(new H2());
    }


    private void elaboraDocPackages() {
        elaboraDocPackages.esegue();
        dialogDocPackages.close();
    }


    public void paragrafoFeedBackWizard() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3(TITOLO_FEEDBACK_PROGETTO);
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);

        layout.add(new Label("Ricopia su vaadflow14 la directory wizard di questo progetto"));

        Button bottone = new Button("Send back");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> dialogFeedbackWizard.open(this::elaboraFeedbackWizard));

        layout.add(bottone);
        this.add(layout);
        this.add(new H2());
    }


    private void elaboraFeedbackWizard() {
        elaboraFeedbackWizard.esegue();
        dialogFeedbackWizard.close();
    }

}
