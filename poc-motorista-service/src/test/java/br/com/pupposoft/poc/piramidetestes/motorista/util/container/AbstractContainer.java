package br.com.pupposoft.poc.piramidetestes.motorista.util.container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public abstract class AbstractContainer {

    static {
    	MySqlTestContainer.getInstance().start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {

    	MySqlTestContainer mySqlTestContainer = MySqlTestContainer.getInstance();

        registry.add("spring.datasource.url", mySqlTestContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySqlTestContainer::getUsername);
        registry.add("spring.datasource.password", mySqlTestContainer::getPassword);
    }

}
