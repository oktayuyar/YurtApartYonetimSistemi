package YAY.model;
// Generated Jan 1, 2016 3:43:02 AM by Hibernate Tools 4.3.1



/**
 * Oda generated by hbm2java
 */
public class Oda  implements java.io.Serializable {


     private Integer odaId;
     private String odaAd;
     private Integer katAd;
     private Integer yatakAdet;

    public Oda() {
    }

	
    public Oda(int katAd) {
        this.katAd = katAd;
    }
    public Oda(String odaAd, Integer katAd, Integer yatakAdet) {
       this.odaAd = odaAd;
       this.katAd = katAd;
       this.yatakAdet = yatakAdet;
    }
   
    public Integer getOdaId() {
        return this.odaId;
    }
    
    public void setOdaId(Integer odaId) {
        this.odaId = odaId;
    }
    public String getOdaAd() {
        return this.odaAd;
    }
    
    public void setOdaAd(String odaAd) {
        this.odaAd = odaAd;
    }
    public Integer getKatAd() {
        return this.katAd;
    }
    
    public void setKatAd(Integer katAd) {
        this.katAd = katAd;
    }
    public Integer getYatakAdet() {
        return this.yatakAdet;
    }
    
    public void setYatakAdet(Integer yatakAdet) {
        this.yatakAdet = yatakAdet;
    }




}


