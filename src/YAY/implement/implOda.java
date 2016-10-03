/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.implement;

import YAY.config.config2;

import YAY.interfaces.interOda;
import YAY.model.Oda;
import YAY.model.Ogrenci;
import YAY.model.Yatak;
import YAY.model.odaSayilar;

import java.util.List;
import javafx.scene.control.Alert;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oktay
 */
@Repository
public class implOda implements interOda {

    @Autowired
    configSessionFactory csf;

    @Override
    public void delete(Integer c) {
        try {
            Query q = csf.getSf().openSession().createQuery("delete from Yatak where yatakId = :yatakId");
            q.setParameter("yatakId", c);
            q.executeUpdate();
        } catch (Exception e) {
            config2.dialog(Alert.AlertType.INFORMATION, "Üzgünüz bu kayıt silinemedi");
        }
    }

    @Override
    public List<Yatak> anakat(Integer a) {
        Criteria cr = csf.getSf().openSession().createCriteria(Yatak.class);
        cr.add(Restrictions.eq("yatakId", a));
        cr.setProjection(Projections.property("ad"));
        return cr.list();
    }

    @Override
    public Integer idgetir(String sa) {
        Query q = csf.getSf().openSession().createQuery("select odaId from Oda where odaAd = :oad");
        q.setParameter("oad", sa);
        return Integer.parseInt(q.uniqueResult().toString());
    }

    @Override
    public String adgetir(Integer sa) {
        Query q = csf.getSf().openSession().createQuery("select odaAd from Oda where odaId = :oid");
        q.setParameter("oid", sa);
        String s = q.uniqueResult().toString();
        return s;
    }

    @Override
    public List<Oda> odalar() {
        Criteria cr = csf.getSf().openSession().createCriteria(Oda.class);
        cr.setProjection(Projections.property("odaAd"));
        return cr.list();
    }

    @Override
    public odaSayilar odaSayi(Integer s) {
        Query q = csf.getSf().openSession().createQuery("SELECT o.yatakAdet, count(y.yatakId) "
                + "FROM Yatak y, Oda o "
                + "WHERE o.odaId = y.odaId"
                + " AND o.odaId = :oid");
        q.setParameter("oid", s);
        odaSayilar o = null;
        List<Object[]> list = q.list();
        for (Object[] list1 : list) {
            o = new odaSayilar(Integer.parseInt(list1[0].toString()), Integer.parseInt(list1[1].toString()));
        }
        return o;
    }

    @Override
    public Integer auto() {
        Integer ret = null;
        try {
            Query q = csf.getSf().openSession().createQuery("select max(t.yatakId) from Yatak t");
            Object o = q.uniqueResult();
            ret = Integer.parseInt(o.toString()) + 1;
        } catch (HibernateException | NumberFormatException e) {
        }
        return ret;
    }

    @Override
    public void yatakEkle(Yatak c) {
        csf.create(c);

    }

    @Override
    public Integer yatakg(Integer id) {
        Criteria cr = csf.getSf().openSession().createCriteria(Ogrenci.class);
        cr.add(Restrictions.eq("ogrenciId", id));
        cr.setProjection(Projections.property("yatakId"));
        return (Integer) cr.uniqueResult();
    }

    @Override
    public List<Oda> select() {
        Criteria cr = csf.getSf().openSession().createCriteria(Oda.class);
        return cr.list();
    }

    @Override
    public List<Oda> ara(String m) {
        Criteria cr = csf.getSf().openSession().createCriteria(Oda.class);
        cr.add(Restrictions.like("odaAd", m));
        return cr.list();

    }

    @Override
    public Integer auto2() {
        Integer ret = null;
        try {
            Query q = csf.getSf().openSession().createQuery("select max(t.odaId) from Oda t");
            Object o = q.uniqueResult();
            ret = Integer.parseInt(o.toString()) + 1;
        } catch (HibernateException | NumberFormatException e) {
        }
        return ret;
    }

    @Override
    public void delete2(Integer odaid) {
        try {
            Query q = csf.getSf().openSession().createQuery("delete from Oda where odaId = :odaId");
            q.setParameter("odaId", odaid);
            q.executeUpdate();
        } catch (Exception e) {
            config2.dialog(Alert.AlertType.INFORMATION, "Üzgünüz bu kayıt silinemedi");
        }
    }

    @Override
    public Integer varmi(Integer odaid) {
        Query q = csf.getSf().openSession().createQuery("SELECT count(y.yatakId) "
                + "FROM Yatak y, Oda o "
                + "WHERE o.odaId = y.odaId "
                + "AND o.odaId = :oid");
        q.setParameter("oid", odaid);
        Object o = q.uniqueResult();
        return Integer.parseInt(o.toString());
    }

    @Override
    public void odaEkle(Oda c) {
        if (c.getOdaId() == null) {
            csf.create(c);
        } else {
            csf.update(c);
        }
    }

    @Override
    public Integer topYatak() {
        Query q = csf.getSf().openSession().createQuery("SELECT SUM(o.yatakAdet) FROM Oda o");
        Object o = q.uniqueResult();
        return Integer.parseInt(o.toString());
    }

    @Override
    public Integer doluYatak() {
        Query q = csf.getSf().openSession().createQuery("SELECT count(y.yatakId) FROM Yatak y");
        Object o = q.uniqueResult();
        return Integer.parseInt(o.toString());
    }
    
}
