/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.interfaces;

import YAY.model.Kira;
import YAY.model.KiraBilgi;
import YAY.model.Ktarihler;
import java.util.List;

/**
 *
 * @author oktay
 */
public interface interKira {
    List<KiraBilgi> select();
    void saveOrUpdate(Kira m);
    void delete(Integer m);
    List<KiraBilgi> ara(String m);
    Integer auto();
    Integer kiraidge(Integer oid);
    List<Kira> topKalan(Integer kid);
    List<java.sql.Timestamp> odemeler(Integer kid);
    void saveTarih(Ktarihler c);
    void kalanGuncelle(Integer kid, Integer s);
     List<Integer> topSenet();
     Integer aylıkGelir();
}
