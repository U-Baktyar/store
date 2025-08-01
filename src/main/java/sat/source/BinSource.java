package sat.source;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.flywaydb.core.Flyway;
import sat.configuration.annotation.ComponentMethod;
import sat.configuration.annotation.ComponentSource;

import java.util.Scanner;

@ComponentSource
public class BinSource {

    @ComponentMethod
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @ComponentMethod
    public Flyway flyway() {
        return Flyway
                .configure()
                .driver("org.postgresql.Driver")
                .dataSource("jdbc:postgresql://localhost:5432/store_db", "baktiyar", "baxa1999")
                .validateMigrationNaming(true)
                .validateOnMigrate(true)
                .baselineOnMigrate(true)
                .outOfOrder(true)
                .load();
    }

    @ComponentMethod
    public EntityManagerFactory entityManagerFactory(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sat");
        return emf;
    }

}
