/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package YAY.implement;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oktay
 */
@Repository
public class configSessionFactory {
    @Autowired
    SessionFactory sf;

    public SessionFactory getSf() {
        return sf;
    }
    
    public void setSf(SessionFactory sf) {
        this.sf = sf;
    }

    public void create(Object o) {
        Session session = this.sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
           session.save(o);
        } catch (HibernateException e) {
           System.out.println(e+"Create olmadı"); 
        }
        tx.commit();
        session.close();
    }

    public void update(Object o) {
        Session session = this.sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.merge(o);
        } catch (HibernateException e) {
            System.out.println("Update olmadı"); 
        }
        tx.commit();
        session.close();
    }

    public void delete(Object o) {
        Session session = this.sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(o);
        } catch (HibernateException e) {
            System.out.println("Delete olmadı"); 
        }
        
        tx.commit();
        session.close();
    }
    
}
