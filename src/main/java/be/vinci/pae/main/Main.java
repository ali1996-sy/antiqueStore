
package be.vinci.pae.main;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

import be.vinci.pae.utils.ApplicationBinder;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.Loggers;

public class Main {


  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
   * 
   * @return Grizzly HTTP server.
   */
  public static HttpServer startServer() {
    // Create a resource config that scans for JAX-RS resources and providers

    final ResourceConfig rc =
        new ResourceConfig().packages("be.vinci.pae.view").register(new ApplicationBinder())
            .register(JacksonFeature.class).property("jersey.config.server.wadl.disableWadl", true);

    return GrizzlyHttpServerFactory.createHttpServer(URI.create(Config.getProperties("server.url")),
        rc);
  }

  /**
   * Start server.
   * 
   * @throws IOException throw when server not started or failed.
   * 
   */
  public static void main(String[] args) throws IOException {
    // Load properties file
    Config.load("prod.properties");
    // Logger
    String fichierSortie = Config.getProperties("fichierLog");
    Loggers.init(fichierSortie);
    Loggers.getLogger().info("Application lancée");
    // Start the server
    final HttpServer server = startServer();
    System.out.println("Jersey app started at " + Config.getProperties("server.url"));
    // Listen to key press and shutdown server
    System.out.println("Hit enter to stop it...");

    System.in.read();
    server.shutdownNow();
  }
}
