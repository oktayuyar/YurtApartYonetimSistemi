/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.interfaces;

import YAY.model.Yonetici;

/**
 *
 * @author oktay
 */
public interface interKullanicilar {

    boolean Kontrol(String kad, String kpw);
    void saveOrUpdate(Yonetici c);
    void delete(Yonetici c);
    String idgetir(String s);
    Integer auto();
    public void sifreDegistir(String kad, String kpw);

}
