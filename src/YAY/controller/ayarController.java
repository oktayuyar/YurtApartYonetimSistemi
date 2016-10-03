/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.controller;

import YAY.animations.FadeInRightTransition;
import YAY.animations.FadeInUpTransition;
import YAY.config.config;
import YAY.config.config2;
import YAY.interfaces.interKullanicilar;
import YAY.model.Yonetici;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

/**
 * FXML Controller class
 *
 * @author oktay
 */
public class ayarController implements Initializable {

    @FXML
    private AnchorPane anaPanel;
    @FXML
    private AnchorPane degistirPanel;
    @FXML
    private PasswordField sifreT;
    @FXML
    private PasswordField sifre;
    @FXML
    private PasswordField eskisifre;
    @FXML
    private TextField txtkad;
    @FXML
    private Button degistir;
    @FXML
    private Button btnBack1;
    @FXML
    private AnchorPane eklePanel;
    @FXML
    private PasswordField ysifreT;
    @FXML
    private PasswordField ysifre;
    @FXML
    private TextField txtykad;
    @FXML
    private Button Ekle;
    @FXML
    private Button btnBack;
    @FXML
    private ProgressBar bar;
    @FXML
    private AnchorPane panel;
    interKullanicilar crudKul;
    @FXML
    private AnchorPane silPanel;
    @FXML
    private PasswordField ssifre;
    @FXML
    private TextField txtskad;
    @FXML
    private Button sil;
    @FXML
    private Button btnBack2;
    Stage stage;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            ApplicationContext ct = config.getInstance().getApplicationContext();
            crudKul = ct.getBean(interKullanicilar.class);
            new FadeInRightTransition(panel).play();
            txtkad.setText(anaPanel.getUserData().toString());
        });
    }

    @FXML
    private void degistir(ActionEvent event) {
        if (!crudKul.Kontrol(txtkad.getText(), config.SHA1(config.MD5(eskisifre.getText())))) {
            config2.dialog(Alert.AlertType.ERROR, "Şifre Yanlış Lütfen Kontrol Ediniz.");
            eskisifre.requestFocus();
        } else if (sifre.getText().isEmpty() || sifre.getText().length() < 5) {
            config2.dialog(Alert.AlertType.ERROR, "Şifre Boş Yada 5 Karakterden Az Olamaz.");
            sifre.requestFocus();
        } else if (!sifreT.getText().equals(sifre.getText())) {
            config2.dialog(Alert.AlertType.ERROR, "Şifre Tekrarı Uyumsuz");
            ysifreT.requestFocus();
        } else {
            crudKul.sifreDegistir(txtkad.getText(), config.SHA1(config.MD5(sifre.getText())));
            config2.dialog(Alert.AlertType.INFORMATION, "Şifreniz Başarıyla Değiştirildi...\nÇıkış Yaptıktan Sonra Yeni Şifreniz Ile Giriş Yapabilirsiniz.\nBaşka Bir Kullanıcı Şifresi Değiştirmek Için O Kullanıcı Ile Giriş Yapınız.");
            clear();

        }
    }

    @FXML
    private void aksiBack(ActionEvent event) {
        silPanel.setOpacity(0);
        degistirPanel.setOpacity(0);
        eklePanel.setOpacity(0);
        new FadeInRightTransition(panel).play();
        clear();
    }

    @FXML
    private void Ekle(ActionEvent event) {
        try {
            crudKul.idgetir(txtykad.getText());
            config2.dialog(Alert.AlertType.ERROR, "Girdiğiniz Kullanıcı Adı Kullanılıyor\nBaska Bir Kullanıcı Adı Deneyiniz.");
            txtykad.requestFocus();
        } catch (Exception e) {
            if (txtykad.getText().isEmpty() || txtykad.getText().length() < 5) {
                config2.dialog(Alert.AlertType.ERROR, "Kullanıcı Adı Boş Yada 5 Karakterden Az Olamaz.");
                txtykad.requestFocus();
            } else if (ysifre.getText().isEmpty() || ysifre.getText().length() < 5) {
                config2.dialog(Alert.AlertType.ERROR, "Şifre Boş Yada 5 Karakterden Az Olamaz.");
                ysifre.requestFocus();
            } else if (!ysifreT.getText().equals(ysifre.getText())) {
                config2.dialog(Alert.AlertType.ERROR, "Şifre Tekrarı Uyumsuz");
                ysifreT.requestFocus();
            } else {
                Yonetici y = new Yonetici();
                y.setKadi(txtykad.getText());
                y.setSifre(config.SHA1(config.MD5(ysifre.getText())));
                crudKul.saveOrUpdate(y);
                clear();
                config2.dialog(Alert.AlertType.INFORMATION, "Kullanıcı Başarıyla Eklendi...");
            }
        }
    }

    @FXML
    private void sifreD(ActionEvent event) {
        silPanel.setOpacity(0);
        panel.setOpacity(0);
        eklePanel.setOpacity(0);
        new FadeInUpTransition(degistirPanel).play();
    }

    @FXML
    private void yeniK(ActionEvent event) {
        silPanel.setOpacity(0);
        panel.setOpacity(0);
        degistirPanel.setOpacity(0);
        new FadeInUpTransition(eklePanel).play();
    }

    public void clear() {
        sifre.setText("");
        sifreT.setText("");
        ysifre.setText("");
        ysifreT.setText("");
        eskisifre.setText("");
        txtykad.setText("");
        txtskad.setText("");
        ssifre.setText("");

    }

    @FXML
    private void kSil(ActionEvent event) {
        degistirPanel.setOpacity(0);
        eklePanel.setOpacity(0);
        panel.setOpacity(0);
        new FadeInUpTransition(silPanel).play();
    }

    @FXML
    private void sil(ActionEvent event) {
        try {
            if (txtskad.getText().equals(txtkad.getText())) {
                config2.dialog(Alert.AlertType.ERROR, "Giriş Yaptıgınız Kullanıcıyı Silemezsiniz.");
            } else if (!crudKul.Kontrol(txtskad.getText(), config.SHA1(config.MD5(ssifre.getText())))) {
                config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.");
            }
            Yonetici y = new Yonetici(txtskad.getText(), null);
            crudKul.delete(y);
            config2.dialog(Alert.AlertType.INFORMATION, "Kullanıcı Başarıyla Silindi...");
            clear();
        } catch (Exception e) {
            config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.\nBüyük Küçük Harf Duyarlıdır.");
        }
    }
}
