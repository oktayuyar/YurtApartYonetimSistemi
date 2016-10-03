package YAY.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oktay
 */
public class musteri implements java.io.Serializable {
    private String id;
    private String adsoy;
    private String odaAd;
    private String kiraUc;
    private Integer senetSay;
    private String tc;
    private String tel;
    private Integer yatakId;
    private Integer topSenetSay;
    private String topbolkal;
    private String resim;
    private String tarGiris;

    public musteri(String id, String adsoy, String odaAd, String kiraUc, Integer senetSay, String tc, String tel, Integer yatakId, Integer topSenetSay, String topbolkal, String resim, String tarGiris) {
        this.id = id;
        this.adsoy = adsoy;
        this.odaAd = odaAd;
        this.kiraUc = kiraUc;
        this.senetSay = senetSay;
        this.tc = tc;
        this.tel = tel;
        this.yatakId = yatakId;
        this.topSenetSay = topSenetSay;
        this.topbolkal = topbolkal;
        this.resim = resim;
        this.tarGiris = tarGiris;
    }

    public musteri() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdsoy() {
        return adsoy;
    }

    public void setAdsoy(String adsoy) {
        this.adsoy = adsoy;
    }

    public String getOdaAd() {
        return odaAd;
    }

    public void setOdaAd(String odaAd) {
        this.odaAd = odaAd;
    }

    public String getKiraUc() {
        return kiraUc;
    }

    public void setKiraUc(String kiraUc) {
        this.kiraUc = kiraUc;
    }

    public Integer getSenetSay() {
        return senetSay;
    }

    public void setSenetSay(Integer senetSay) {
        this.senetSay = senetSay;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getYatakId() {
        return yatakId;
    }

    public void setYatakId(Integer yatakId) {
        this.yatakId = yatakId;
    }

    public Integer getTopSenetSay() {
        return topSenetSay;
    }

    public void setTopSenetSay(Integer topSenetSay) {
        this.topSenetSay = topSenetSay;
    }

    public String getTopbolkal() {
        return topbolkal;
    }

    public void setTopbolkal(String topbolkal) {
        this.topbolkal = topbolkal;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public String getTarGiris() {
        return tarGiris;
    }

    public void setTarGiris(String tarGiris) {
        this.tarGiris = tarGiris;
    }
    
}