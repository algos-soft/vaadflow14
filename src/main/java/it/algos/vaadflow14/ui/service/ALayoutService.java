package it.algos.vaadflow14.ui.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouterLink;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.service.AAbstractService;
import it.algos.vaadflow14.ui.list.AViewList;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-set-2019 18.19.24 <br>
 * <p>
 * Classe di servizio <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(ALayoutService.class); <br>
 * 3) @Autowired private ALayoutService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
            for (Class<?> view : FlowVar.menuRouteList) {
                tab = createTab(view);
                if (tab != null) {
                    tabs.add(tab);
                }
            }
        }

        return tabs.toArray(new Tab[tabs.size()]);
    }


    private Tab createTab(Class<?> viewRouteClass) {
        RouterLink routerLink = createMenuLink(viewRouteClass);

        if (routerLink != null) {
            return new Tab(routerLink);
        } else {
            return null;
        }
    }


    /**
     * Nel menu metto :
     * 1) Tutte le classi che hanno un'annotation @Route (link diretto alla classe) <br>
     * 2) Tutte le classi che hanno un'annotation @AIEntity (parametro riconoscibile da AView) <br>
     * 3) Tutte le classi che hanno un'annotation @AIView (parametro riconoscibile da AView) <br>
     * 4) Tutte le classi che estendono AEntityService (parametro riconoscibile da AView) <br>
     */
    private RouterLink createMenuLink(Class<?> viewRouteClass) {
        RouterLink routerLink = null;
        QueryParameters query = null;
        Icon icon = null;
        String menuName = "Error";

        if (annotation.isRouteView(viewRouteClass) && Component.class.isAssignableFrom(viewRouteClass)) {
            routerLink = new RouterLink(null, (Class<Component>) viewRouteClass);
            icon = annotation.getMenuIcon((Class<Component>) viewRouteClass);
            menuName = annotation.getMenuName(viewRouteClass);
        }

        if (annotation.isEntityClass(viewRouteClass)) {
            query = route.getQueryList(viewRouteClass);
            routerLink = new RouterLink(null, AViewList.class);
            routerLink.setQueryParameters(query);
            icon = annotation.getMenuIcon((Class<AEntity>) viewRouteClass);
            menuName = viewRouteClass.getSimpleName();
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