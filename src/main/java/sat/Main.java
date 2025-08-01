package sat;

import org.flywaydb.core.Flyway;
import sat.configuration.ComponentContainer;
import sat.runner.ApplicationRunner;


public class Main {
    public static void main(String[] args) {
        ComponentContainer componentContainer = new ComponentContainer(Main.class);
        componentContainer.configure();

        Flyway flyway = componentContainer.getComponent(Flyway.class);
        flyway.migrate();

        ApplicationRunner applicationRunner  = componentContainer.getComponent(ApplicationRunner.class);
        applicationRunner.applicationRun();
    }
}
