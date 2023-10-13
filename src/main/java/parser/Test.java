package parser;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Test {

    public static void main(String[] args) {

        System.out.println("Hibernate test");

        Session session = DbConnect.getSession();
        Transaction transaction = session.beginTransaction();
        boolean vsetkoOK = true;
        if (vsetkoOK) {


            transaction.commit();
        } else
            transaction.rollback();


        session.close();


    }


}
