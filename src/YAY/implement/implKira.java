/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.implement;

import YAY.config.config2;
import YAY.interfaces.interKira;
import YAY.model.Kira;
import YAY.model.KiraBilgi;
import YAY.model.Ktarihler;
import YAY.model.Ogrenci;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class implKira implements interKira {

    @Autowired
    configSessionFactory csf;

    @Override
    public List<KiraBilgi> select() {
        Query q = csf.getSf().openSession().createQuery("SELECT o.kiraId , o.ogrenciAd, k.kiraUcret, k.kalanSenet , k.senetSayisi, k.baslangicTarihi"
                + " FROM Ogrenci o, Oda od, Kira k, Yatak y "
                + "WHERE y.yatakId = o.yatakId "
                + "AND o.kiraId = k.kiraId AND od.odaId = y.odaId");
        KiraBilgi k;
        ObservableList<KiraBilgi> listt = FXCollections.observableArrayList();
        List<Object[]> list = q.list();
        for (Object[] listData1 : list) {
            int s = Integer.parseInt(listData1[3].toString());
            int s2 = Integer.parseInt(listData1[4].toString());
            int s3 = Integer.parseInt(listData1[2].toString());
            int kalanBorc = s * s3;
            int topBorc = s2 * s3;
            String topbolkal = listData1[4].toString() + "/" + listData1[3].toString();
            k = new KiraBilgi(listData1[0].toString(), listData1[1].toString(), listData1[2].toString(), topbolkal, kalanBorc, topBorc, listData1[5].toString());
            listt.add(k);
        }
        return listt;
    }

    @Override
    public void saveOrUpdate(Kira m) {
        csf.create(m);

    }

    @Override
    public void delete(Integer m) {
        try {
            Query q = csf.getSf().openSession().createQuery("delete from Kira where kiraId = :id");
            q.setParameter("id", m);
            q.executeUpdate();
        } catch (Exception e) {
            config2.dialog(Alert.AlertType.INFORMATION, "Silinemedi");
        }
    }

    @Override
    public Integer auto() {
        Integer ret = null;
        try {
            Query q = csf.getSf().openSession().createQuery("select max(t.kiraId) from Kira t");
            Object o = q.uniqueResult();
            ret = Integer.parseInt(o.toString()) + 1;
        } catch (HibernateException | NumberFormatException e) {
        }
        return ret;
    }

    @Override
    public List<KiraBilgi> ara(String m) {
        Query q = csf.getSf().openSession().createQuery("SELECT o.kiraId , o.ogrenciAd, k.kiraUcret, k.kalanSenet , k.senetSayisi, k.baslangicTarihi"
                + " FROM Ogrenci o, Oda od, Kira k, Yatak y "
                + "WHERE y.yatakId = o.yatakId "
                + "AND o.kiraId = k.kiraId AND od.odaId = y.odaId AND o.ogrenciAd LIKE '%" + m + "%'");
        KiraBilgi k;
        ObservableList<KiraBilgi> listt = FXCollections.observableArrayList();
        List<Object[]> list = q.list();
        for (Object[] listData1 : list) {
            int s = Integer.parseInt(listData1[3].toString());
            int s2 = Integer.parseInt(listData1[4].toString());
            int s3 = Integer.parseInt(listData1[2].toString());
            int kalanBorc = s * s3;
            int topBorc = s2 * s3;
            String topbolkal = listData1[4].toString() + "/" + listData1[3].toString();
            k = new KiraBilgi(listData1[0].toString(), listData1[1].toString(), listData1[2].toString(), topbolkal, kalanBorc, topBorc, listData1[5].toString());
            listt.add(k);
        }
        return listt;

    }

    @Override
    public List<Kira> topKalan(Integer kid) {
        Criteria cr = csf.getSf().openSession().createCriteria(Kira.class);
        cr.add(Restrictions.eq("kiraId", kid));
        return cr.list();
    }

    @Override
    public List<java.sql.Timestamp> odemeler(Integer kid) {
        Criteria cr = csf.getSf().openSession().createCriteria(Ktarihler.class);
        cr.add(Restrictions.eq("kiraId", kid));
        cr.setProjection(Projections.property("odemeTarihi"));
        return cr.list();
    }

    @Override
    public Integer kiraidge(Integer oid) {
        Criteria cr = csf.getSf().openSession().createCriteria(Ogrenci.class);
        cr.add(Restrictions.eq("ogrenciId", oid));
        cr.setProjection(Projections.property("kiraId"));
        return (Integer) cr.uniqueResult();
    }

    @Override
    public void saveTarih(Ktarihler c) {
        if (c.getKiraId() == null) {
            csf.create(c);
        } else {
            csf.update(c);
        }
    }

    @Override
    public void kalanGuncelle(Integer kid, Integer s) {
        try {
            Query q = csf.getSf().openSession().createQuery("UPDATE  Kira SET kalanSenet = :s WHERE kiraId = :id");
            q.setParameter("id", kid);
            q.setParameter("s", s);
            q.executeUpdate();
        } catch (Exception e) {
            config2.dialog(Alert.AlertType.INFORMATION, "Silinemedi");
        }

    }

    @Override
    public Integer aylıkGelir() {
        Query q = csf.getSf().openSession().createQuery("SELECT SUM(k.kiraUcret) FROM Kira k");
        Object o = q.uniqueResult();
        return Integer.parseInt(o.toString());
    }

    @Override
    public List<Integer> topSenet() {
        Query q = csf.getSf().openSession().createQuery("SELECT SUM(k.senetSayisi), SUM(k.kalanSenet) FROM Kira k");
        ObservableList<Integer> listt = FXCollections.observableArrayList();
        List<Object[]> list = q.list();
        for (Object[] listData1 : list) {
            Integer top = Integer.parseInt(listData1[0].toString());
            Integer kalan = Integer.parseInt(listData1[1].toString());
            listt.addAll(top,kalan);
        }
        return listt;
    }

}
