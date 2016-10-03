/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.implement;

import YAY.config.config2;
import YAY.interfaces.interMusteriler;
import YAY.model.Ogrenci;
import YAY.model.musteri;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oktay
 */
@Repository
public class implMusteriler implements interMusteriler {

    @Autowired
    configSessionFactory csf;

    @Override
    public void saveOrUpdate(Ogrenci c) {
        if (c.getOgrenciId() == null) {
            csf.create(c);
        } else {
            csf.update(c);
        }
    }

    @Override
    public Integer auto() {
        Integer ret = null;
        try {
            Query q = csf.getSf().openSession().createQuery("select max(t.ogrenciId) from Ogrenci t");
            Object o = q.uniqueResult();
            ret = Integer.parseInt(o.toString()) + 1;
        } catch (HibernateException | NumberFormatException e) {
        }
        return ret;
    }

    @Override
    public void delete(Ogrenci c) {
        try {
            Query q = csf.getSf().openSession().createQuery("delete from Ogrenci where ogrenciId = :customerId");
            q.setParameter("customerId", c.getOgrenciId());
            q.executeUpdate();
            config2.dialog(Alert.AlertType.INFORMATION, "Başarıyla silindi");
        } catch (Exception e) {
            config2.dialog(Alert.AlertType.INFORMATION, "Üzgünüz bu kayıt silinemedi");
        }
    }

    @Override
    public List<musteri> select() {
        Query q = csf.getSf().openSession().createQuery("SELECT o.ogrenciAd, o.girisTarihi, o.resim, od.odaAd, k.kiraUcret, k.senetSayisi, o.tc, o.tel, o.ogrenciId, k.kalanSenet, o.yatakId "
                + "FROM Ogrenci o, Oda od, Kira k, Yatak y "
                + "WHERE y.yatakId = o.yatakId "
                + "AND o.kiraId = k.kiraId AND od.odaId = y.odaId");
        musteri m;
        ObservableList<musteri> listt = FXCollections.observableArrayList();
        List<Object[]> list = q.list();
        for (Object[] listData1 : list) {
            int s = Integer.parseInt(listData1[5].toString());
            int s2 = Integer.parseInt(listData1[9].toString());
            int s3 = Integer.parseInt(listData1[10].toString());
            String topbolkal = listData1[5].toString() + "/" + listData1[9].toString();
            m = new musteri(listData1[8].toString(), listData1[0].toString(), listData1[3].toString(), listData1[4].toString(), s2, listData1[6].toString(), "0"+listData1[7].toString(), s3, s, topbolkal, listData1[2].toString(), listData1[1].toString());
            listt.add(m);
        }
        return listt;
    }

    @Override
    public List<musteri> ara(String s) {
        Query q = csf.getSf().openSession().createQuery("SELECT o.ogrenciAd, o.girisTarihi, o.resim, od.odaAd, k.kiraUcret, k.senetSayisi, o.tc, o.tel, o.ogrenciId, k.kalanSenet, o.yatakId "
                + "FROM Ogrenci o, Yatak y, Kira k, Oda od "
                + "WHERE y.yatakId = o.yatakId "
                + "AND o.kiraId = k.kiraId AND od.odaId = y.odaId AND o.ogrenciAd LIKE '%" + s + "%'");
        List<Object[]> list = q.list();
        musteri m;
        ObservableList<musteri> listt = FXCollections.observableArrayList();
        for (Object[] listData1 : list) {
            int i = Integer.parseInt(listData1[5].toString());
            int i2 = Integer.parseInt(listData1[9].toString());
            int i3 = Integer.parseInt(listData1[10].toString());
            String topbolkal = listData1[5].toString() + "/" + listData1[9].toString();
            m = new musteri(listData1[8].toString(), listData1[0].toString(), listData1[3].toString(), listData1[4].toString(), i2, listData1[6].toString(), "0"+listData1[7].toString(), i3, i, topbolkal, listData1[2].toString(), listData1[1].toString());
            listt.add(m);
        }
        return listt;
    }

    @Override
    public List<String> odadakiler(int i) {
        Query q = csf.getSf().openSession().createQuery("select o.ogrenciAd from Ogrenci o,Yatak y, Oda od where y.yatakId=o.yatakId and od.odaId=y.odaId and od.odaId = :oid");
        q.setParameter("oid", i);
        List<String> listt;
        listt = q.list();
        return listt;
    }

    @Override
    public List<Ogrenci> musteriGetir(Integer d) {
        Criteria cr = csf.getSf().openSession().createCriteria(Ogrenci.class);
        cr.add(Restrictions.eq("ogrenciId", d));
        return cr.list();
    }
   
}
