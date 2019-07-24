package com.resolut.banktransferapi.config;

//import org.apache.catalina.LifecycleException;
//import org.apache.catalina.WebResourceRoot;
//import org.apache.catalina.core.StandardContext;
//import org.apache.catalina.startup.Tomcat;
//import org.apache.catalina.webresources.DirResourceSet;
//import org.apache.catalina.webresources.StandardRoot;
//import org.jboss.weld.environment.se.Weld;
//import org.jboss.weld.environment.se.WeldContainer;
//import org.jboss.weld.environment.servlet.Listener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.resolut.banktransferapi.account.domain.repository.AccountRepository;
import com.resolut.banktransferapi.account.domain.repository.JpaAccountRepository;
import com.resolut.banktransferapi.account.service.AccountService;
import com.resolut.banktransferapi.account.service.AccountServiceImpl;
import com.resolut.banktransferapi.account.view.AccountController;
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

        @Provides
        @Singleton
        public AccountRepository getAccountRepository() {
            return new JpaAccountRepository();
        }

        @Provides
        @Singleton
        public AccountService getAccountService() {
            return new AccountServiceImpl(getAccountRepository());
        }

        @Override
        protected void configure() {
            super.configure();
            bind(AccountController.class);
        }

    }
}