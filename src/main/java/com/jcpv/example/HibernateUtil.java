package com.jcpv.example;

import com.jcpv.example.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by JanCarlo on 20/06/2017.
 */
public class HibernateUtil {
    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            try {
                StandardServiceRegistryBuilder registryBuilder  = new StandardServiceRegistryBuilder();
                Map<String, Object> settings = new HashMap<>();
                settings.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL,"jdbc:mysql://127.0.0.1:3306/example1?useSSL=false&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC");
                settings.put(Environment.USER,"user");
                settings.put(Environment.PASS,"Pa$$w0rd");
                settings.put(Environment.HBM2DDL_AUTO,"update");

                settings.put(Environment.SHOW_SQL, true);

                // HikariCP settings

                // Maximum waiting time for a connection from the pool
                settings.put("hibernate.hikari.connectionTimeout", "20000");
                // Minimum number of ideal connections in the pool
                settings.put("hibernate.hikari.minimumIdle", "10");
                // Maximum number of actual connection in the pool
                settings.put("hibernate.hikari.maximumPoolSize", "20");
                // Maximum time that a connection is allowed to sit ideal in the pool
                settings.put("hibernate.hikari.idleTimeout", "300000");

                registryBuilder.applySettings(settings);
                registry= registryBuilder.build();
                logger.info("Hibernate Registry builder created.");

                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Customer.class);
                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory= metadata.getSessionFactoryBuilder().build();

            } catch (Exception ex) {
                logger.error("SessionFactory creation failed");
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                ex.printStackTrace();
            }

        } return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
