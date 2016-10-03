/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.interfaces;

import YAY.model.Oda;
import YAY.model.Ogrenci;
import YAY.model.Yatak;
import YAY.model.odaSayilar;
import java.util.List;

/**
 *
 * @author oktay
 */ 

public interface interOda {
      List<Yatak> anakat(Integer a);
      Integer idgetir(String sa);
      String adgetir(Integer sa);
      List<Oda> odalar();
      odaSayilar odaSayi(Integer s);
      Integer auto();
      void yatakEkle(Yatak c);
      void delete(Integer c);
      Integer yatakg(Integer id);
      List<Oda> select();
      List<Oda> ara(String m);
      Integer auto2();
      Integer varmi(Integer odaid);
      void delete2(Integer odaid);
      void odaEkle(Oda c);
      public Integer topYatak();
      Integer doluYatak();
}
