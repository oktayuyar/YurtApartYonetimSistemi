/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.model;

/**
 *
 * @author oktay
 */
public class KiraBilgi implements java.io.Serializable {
    private String kid;
    private String adsoy;
    private String kiraUc;
    private String senetSay;
    private Integer kalanBorc;
    private Integer topBorc;
    private String odemeBas;

    public KiraBilgi(String kid, String adsoy, String kiraUc, String senetSay, Integer kalanBorc, Integer topBorc, String odemeBas) {
        this.kid = kid;
        this.adsoy = adsoy;
        this.kiraUc = kiraUc;
        this.senetSay = senetSay;
        this.kalanBorc = kalanBorc;
        this.topBorc = topBorc;
        this.odemeBas = odemeBas;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getAdsoy() {
        return adsoy;
    }

    public void setAdsoy(String adsoy) {
        this.adsoy = adsoy;
    }

    public String getKiraUc() {
        return kiraUc;
    }

    public void setKiraUc(String kiraUc) {
        this.kiraUc = kiraUc;
    }

    public String getSenetSay() {
        return senetSay;
    }

    public void setSenetSay(String senetSay) {
        this.senetSay = senetSay;
    }

    public Integer getKalanBorc() {
        return kalanBorc;
    }

    public void setKalanBorc(Integer kalanBorc) {
        this.kalanBorc = kalanBorc;
    }

    public Integer getTopBorc() {
        return topBorc;
    }

    public void setTopBorc(Integer topBorc) {
        this.topBorc = topBorc;
    }

    public String getOdemeBas() {
        return odemeBas;
    }

    public void setOdemeBas(String odemeBas) {
        this.odemeBas = odemeBas;
    }

    public KiraBilgi() {
    }
    
}
