/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.interfaces;

import YAY.model.Ogrenci;
import YAY.model.musteri;
import java.util.List;

/**
 *
 * @author oktay
 */
public interface interMusteriler {
    List<musteri> select();
    List<musteri> ara(String q);
    void saveOrUpdate(Ogrenci c);
    void delete(Ogrenci c);
    Integer auto();
    List<String> odadakiler(int i);
    List<Ogrenci> musteriGetir(Integer d);
}
