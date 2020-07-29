package it.algos.vaadflow14.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import it.algos.vaadflow14.backend.application.FlowVar;
import it.algos.vaadflow14.ui.service.ALayoutService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: lun, 27-apr-2020
 * Time: 21:03
 * The main view is a top-level placeholder for other views.
 */
@Theme(value = Lumo.class)
//@CssImport("./styles/shared-styles.css")
//@CssImport(value = "./styles/menu-buttons.css", themeFor = "vaadin-button")
public class MainLayout extends AppLayout {


    private final Tabs menu;


    private Button logoutButton;


    /**
     * Costruttore con una classe di servizio iniettata. <br>
     *
     * @param layoutService the layout service
     */
    @Autowired
    public MainLayout(ALayoutService layoutService) {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, layoutService.creaDrawer());

        menu = layoutService.creaMenuTabs();
        addToDrawer(menu);

        addToNavbar(layoutService.creaTop());

        if (FlowVar.usaSecurity) {
            logoutButton = layoutService.creaLogoutButton();
            logoutButton.addClickListener(e -> logout());
            menu.add(logoutButton);
        }

    }


    private void logout() {
        //        AccessControlFactory.getInstance().createAccessControl().signOut();
        System.exit(0);
    }


    //    private void registerAdminViewIfApplicable(AccessControl accessControl) {
    //        // register the admin view dynamically only for any admin user logged in
    //        if (accessControl.isUserInRole(AccessControl.ADMIN_ROLE_NAME)
    //                && !RouteConfiguration.forSessionScope()
    //                .isRouteRegistered(AdminView.class)) {
    //            RouteConfiguration.forSessionScope().setRoute(AdminView.VIEW_NAME,
    //                    AdminView.class, MainLayout.class);
    //            // as logout will purge the session route registry, no need to
    //            // unregister the view on logout
    //        }
    //    }


    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }


    private void selectTab() {
        String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // User can quickly activate logout with Ctrl+E (Exit)
        attachEvent.getUI().addShortcutListener(() -> logout(), Key.KEY_E, KeyModifier.CONTROL);

        //        // add the admin view menu item if user has admin role
        //        final AccessControl accessControl = AccessControlFactory.getInstance().createAccessControl();
        //        if (accessControl.isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
        //
        //            // Create extra navigation target for admins
        //            registerAdminViewIfApplicable(accessControl);
        //
        //            // The link can only be created now, because the RouterLink checks
        //            // that the target is valid.
        //            addToDrawer(createMenuLink(AdminView.class, AdminView.VIEW_NAME,
        //                    VaadinIcon.DOCTOR.create()));
        //        }

        // Finally, add logout button for all users
//        addToDrawer(logoutButton);

    }

}

