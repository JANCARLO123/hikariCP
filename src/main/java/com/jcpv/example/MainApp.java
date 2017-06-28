package com.jcpv.example;

import com.jcpv.example.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;



/**
 * Created by JanCarlo on 20/06/2017.
 */
public class MainApp {
    private static final Logger logger = LogManager.getLogger(MainApp.class);
    public static void main(String[] args){
        Session session= null;
        Transaction transaction =null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction =session.getTransaction();
            transaction.begin();

            Customer customer = new Customer();
            customer.setName("Jan Carlo");
            session.save(customer);

            transaction.commit();

            logger.info("Customer saved successfully...");
        }catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();

            }
            logger.error("Failed to save customer..." + ex);
        }finally {
            if (session != null) {
                session.close();
            }
        }
        HibernateUtil.shutdown();

    }
}
