package com.revolut.banktransferapi.config;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.revolut.banktransferapi.account.domain.repository.AccountRepository;
import com.revolut.banktransferapi.account.domain.repository.JpaAccountRepository;
import com.revolut.banktransferapi.account.service.AccountService;
import com.revolut.banktransferapi.account.service.AccountServiceImpl;
import com.revolut.banktransferapi.account.view.AccountController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.inject.Singleton;

public class Main {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new RevolutServiceModule());

        injector.getAllBindings();

        injector.createChildInjector().getAllBindings();

        Server server = new Server(8080);
        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addEventListener(injector.getInstance(GuiceResteasyBootstrapServletContextListener.class));

        ServletHolder sh = new ServletHolder(HttpServletDispatcher.class);

        servletHandler.addServlet(sh, "/*");

        server.setHandler(servletHandler);
        server.start();
        server.join();
    }

    private static class RevolutServiceModule extends RequestScopeModule {

        @Singleton
        private static class JPAInitializer {
            @Inject
            public JPAInitializer(final PersistService service) {
                service.start();
            }
        }

        @Override
        protected void configure() {
            super.configure();

            install(new JpaPersistModule("bank-transfer-pu"));

            bind(AccountRepository.class).to(JpaAccountRepository.class);
            bind(AccountService.class).to(AccountServiceImpl.class);
            bind(AccountController.class);

            bind(JPAInitializer.class).asEagerSingleton();

        }

    }
}