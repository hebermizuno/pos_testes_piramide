package br.com.pupposoft.poc.piramidetestes.motorista.util.container;

import org.testcontainers.containers.MySQLContainer;

public class MySqlTestContainer extends MySQLContainer<MySqlTestContainer>{

    private static final String IMAGE_VERSION = "mysql:9.0.1";
    private static MySqlTestContainer container;


    private MySqlTestContainer() {
        super(IMAGE_VERSION);
    }

    public static MySqlTestContainer getInstance() {
        if (container == null) {
            container = new MySqlTestContainer();
        }
        return container;
    }
}
