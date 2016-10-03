/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.implement;

import YAY.config.config2;
import YAY.interfaces.interKullanicilar;
import YAY.model.Yonetici;
import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oktay
 */
@Repository
public class implKullanicilar implements interKullanicilar {

    @Autowired
    configSessionFactory csf;

    @Override
    public boolean Kontrol(String kad, String kpw) {
        Query q = csf.getSf().openSession().createQuery("select sifre from Yonetici where kadi = :kad");
        q.setParameter("kad", kad);
        String s = (String) q.uniqueResult();
        System.out.println(s);
        System.out.println(kpw);
        return s.equals(kpw);
    }

    @Override
    public void saveOrUpdate(Yonetici c) {
        if (c.getKid()== null) {
            csf.create(c);
        } else {
            csf.update(c);
        }
    }

    @Override
    public void delete(Yonetici c) {
        try {
            Query q = csf.getSf().openSession().createQuery("delete from Yonetici where kadi = :kad");
            q.setParameter("kad", c.getKadi());
            q.executeUpdate();
        } catch (Exception e) {
        }
    }

    @Override
    public Integer auto() {
        Integer ret = null;
        try {
            Query q = csf.getSf().openSession().createQuery("select max(t.kid) from Yonetici t");
            Object o = q.uniqueResult();
            ret = Integer.parseInt(o.toString()) + 1;
        } catch (HibernateException | NumberFormatException e) {
            System.out.println(e);
        }
        return ret;
    }

    @Override
    public String idgetir(String sa) {
        String s = null;
        Query q = csf.getSf().openSession().createQuery("select kid from Yonetici where kadi = :kad");
        q.setParameter("kad", sa);
        s = (String) q.uniqueResult().toString();
        return s;
    }
    
    @Override
    public void sifreDegistir(String kad, String kpw){
         try {
         Query q = csf.getSf().openSession().createQuery("UPDATE Yonetici SET sifre = :kpw WHERE kadi = :kad");
        q.setParameter("kad", kad);
        q.setParameter("kpw", kpw);
        q.executeUpdate();
        } catch (Exception e) {
             System.out.println(e);
            config2.dialog(Alert.AlertType.INFORMATION, "Şifre Değişiminde Bir Hata Oldu");
        }
        
    }
}
