package YAY.model;
// Generated Jan 1, 2016 3:43:02 AM by Hibernate Tools 4.3.1



/**
 * Yonetici generated by hbm2java
 */
public class Yonetici  implements java.io.Serializable {


     private Integer kid;
     private String kadi;
     private String sifre;

    public Yonetici() {
    }

    public Yonetici(String kadi, String sifre) {
       this.kadi = kadi;
       this.sifre = sifre;
    }
   
    public Integer getKid() {
        return this.kid;
    }
    
    public void setKid(Integer kid) {
        this.kid = kid;
    }
    public String getKadi() {
        return this.kadi;
    }
    
    public void setKadi(String kadi) {
        this.kadi = kadi;
    }
    public String getSifre() {
        return this.sifre;
    }
    
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }




}

