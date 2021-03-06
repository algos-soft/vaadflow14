package it.algos.vaadflow14.ui.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.tabs.*;
import com.vaadin.flow.router.*;
import it.algos.vaadflow14.backend.application.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.logic.*;
import it.algos.vaadflow14.backend.service.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.util.*;


/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-set-2019 18.19.24 <br>
 * <p>
 * Classe di servizio <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ALayoutService extends AAbstractService {

    //    /**
    //     * Menu drawer toggle a sinistra. <br>
    //     *
    //     * @return the drawer toggle
    //     */
    //    public DrawerToggle creaDrawer() {
    //        final DrawerToggle drawerToggle = new DrawerToggle();
    //
    //        //@todo Controllare
    //        //        drawerToggle.addClassName("menu-toggle");
    //
    //        return drawerToggle;
    //    }


    /**
     * image, logo. <br>
     *
     * @return the horizontal layout
     */
    public HorizontalLayout creaTop() {
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        top.setClassName("menu-header");

        //@todo Funzionalità ancora da implementare
        //        // Note! Image resource url is resolved here as it is dependent on the
        //        // execution mode (development or production) and browser ES level support
        //        final String resolvedImage = VaadinService.getCurrent().resolveResource(pathLogo);
        //
        //        final Image image = new Image(resolvedImage, "");
        //        image.setHeight("24px");
        //        image.setWidth("24px");
        String check = FlowVar.projectName;
        final Label title = new Label(check);
        //        top.add(image, title);//@todo Funzionalità ancora da implementare
        top.add(title);

        return top;
    }


    /**
     * Crea menu tabs tabs.
     *
     * @return the tabs
     */
    public Tabs creaMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(getAvailableMenu());
        tabs.setSizeFull();

        return tabs;
    }


    /**
     * Get available menu tab [ ].
     *
     * @return the tab [ ]
     */
    public Tab[] getAvailableMenu() {
        final List<Tab> tabs = new ArrayList<>();
        Tab tab = null;

        if (array.isAllValid(FlowVar.menuRouteList)) {
            for (Class<?> menuClazz : FlowVar.menuRouteList) {
                tab = createTab(menuClazz);
                if (tab != null) {
                    tabs.add(tab);
                }
            }
        }

        return tabs.toArray(new Tab[tabs.size()]);
    }


    private Tab createTab(Class<?> menuClazz) {
        RouterLink routerLink = createMenuLink(menuClazz);

        if (routerLink != null) {
            return new Tab(routerLink);
        }
        else {
            return null;
        }
    }


    /**
     * Nel menu metto :
     * 1) Tutte le classi che hanno un'annotation @Route (link diretto alla classe) <br>
     * 2) Tutte le classi che hanno un'annotation @AIEntity (parametro riconoscibile da AView) <br>
     * //     * 3) Tutte le classi che hanno un'annotation @AIView (parametro riconoscibile da AView) <br>
     * //     * 4) Tutte le classi che estendono AEntityService (parametro riconoscibile da AView) <br>
     *
     * @param menuClazz inserito da FlowBoot.fixMenuRoutes() e sue sottoclassi
     */
    private RouterLink createMenuLink(Class<?> menuClazz) {
        RouterLink routerLink = null;
        QueryParameters query = null;
        Icon icon = annotation.getMenuIcon((Class<AEntity>) menuClazz);
        String menuName =  annotation.getMenuName(menuClazz);

        if (annotation.isRouteView(menuClazz) && Component.class.isAssignableFrom(menuClazz)) {
            routerLink = new RouterLink(null, (Class<Component>) menuClazz);
        }

        if (annotation.isEntityClass(menuClazz)) {
            query = route.getQueryList(menuClazz);
            routerLink = new RouterLink(null, GenericLogicList.class);
            routerLink.setQueryParameters(query);
        }

        //        if (AEntityService.class.isAssignableFrom(viewRouteClass)) {
        //            query = route.getQuery(KEY_SERVICE_CLASS, viewRouteClass.getCanonicalName());
        //            routerLink = new RouterLink(null, AGenericView.class);
        //            routerLink.setQueryParameters(query);
        //            icon = annotation.getMenuIcon((Class<AEntity>) viewRouteClass);
        //            menuLabel = viewRouteClass.getSimpleName();
        //        }

        if (routerLink == null) {
            return null;
        }
        routerLink.setClassName("menu-link");

        if (icon != null) {
            routerLink.add(icon);
            //            icon.setSize("24px");
        }

        if (text.isValid(menuName)) {
            menuName = FlowCost.HTLM_SPAZIO + menuName;
            Span span = new Span();
            span.getElement().setProperty("innerHTML", menuName);
            routerLink.add(span);
        }

        return routerLink;
    }


    /**
     * Create profile button but don't add it yet; admin view might be added. <br>
     * in between (see #onAttach()) <br>
     *
     * @return the button
     */
    public Button creaProfileButton() {
        Button profileButton;

        profileButton = createMenuButton("Profile", VaadinIcon.EDIT.create());
        profileButton.getElement().setAttribute("title", "Profile (Ctrl+E)");

        return profileButton;
    }

    /**
     * Create logout button but don't add it yet; admin view might be added. <br>
     * in between (see #onAttach()) <br>
     *
     * @return the button
     */
    public Button creaLogoutButton() {
        Button logoutButton;

        logoutButton = createMenuButton("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.getElement().setAttribute("title", "Logout (Ctrl+L)");

        return logoutButton;
    }

    //    private void logout() {
    //        VaadinSession.getCurrent().getSession().invalidate();
    //        UI.getCurrent().getPage().executeJavaScript("location.assign('logout')");
    //    }

    private Button createMenuButton(String caption, Icon icon) {
        final Button routerButton = new Button(caption);
        routerButton.setClassName("menu-button");
        routerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        routerButton.setIcon(icon);
        icon.setSize("24px");

        return routerButton;
    }

}