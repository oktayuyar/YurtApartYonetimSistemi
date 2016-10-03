/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.controller;

import YAY.config.config;
import YAY.config.config2;
import YAY.interfaces.interKira;
import YAY.interfaces.interKullanicilar;
import YAY.interfaces.interMusteriler;
import YAY.interfaces.interOda;
import YAY.model.Ktarihler;
import YAY.model.Oda;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.springframework.context.ApplicationContext;

/**
 * FXML Controller class
 *
 * @author oktay
 */
public class KiraGrid implements Initializable {

    @FXML
    private AnchorPane anaPanel2;
    @FXML
    private GridPane kok;
    @FXML
    private Button close;
    @FXML
    private Label title;
    interKira crudKira;
    interMusteriler crudMus;
    interKullanicilar crudKul;
    Double initX, initY;
    Button[] btn = new Button[30];
    Button[] btnT = new Button[15];
    Button[] button = new Button[12];
    Button secilen = new Button();
    String style = "-fx-padding: 5 22 5 22;"
            + "    -fx-border-style: none; "
            + "    -fx-background-radius: 0;"
            + "    -fx-border-color : transparent;"
            + "    -fx-background-color: #299bb4;"
            + "    -fx-background-insets: 0;"
            + "    -fx-border-insets: 0;"
            + "    -fx-font-family:Segoe UI Semibold;"
            + "    -fx-font-size: 12px;"
            + "    -fx-text-fill: white;"
            + "    -fx-font-weight: bold;";
    Integer topSenet, kalan, ucret, kalanSenet, kid, secileni;
    String kIsim;
    private ObservableList<java.sql.Timestamp> odemeTarih;
    String[] s = new String[3];

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            ApplicationContext ctx = config.getInstance().getApplicationContext();
            crudKira = ctx.getBean(interKira.class);
            crudMus = ctx.getBean(interMusteriler.class);
            crudKul = ctx.getBean(interKullanicilar.class);
            s = (String[]) anaPanel2.getUserData();
            kIsim = s[0];
            kid = Integer.valueOf(s[1]);
            title.setText(s[2] + " isimli Müşterinin Kira Bilgi Ve Ödeme Sayfası");
            topSenet = crudKira.topKalan(kid).get(0).getSenetSayisi();
            kalanSenet = crudKira.topKalan(kid).get(0).getKalanSenet();
            kalan = (crudKira.topKalan(kid).get(0).getSenetSayisi() - crudKira.topKalan(kid).get(0).getKalanSenet());
            ucret = crudKira.topKalan(kid).get(0).getKiraUcret();
            odemeTarih = FXCollections.observableArrayList(crudKira.odemeler(kid));
            uret();
        });
    }

    @FXML
    private void aksiClose(ActionEvent event) {
        Stage st = (Stage) kok.getScene().getWindow();
        st.close();
    }

    @FXML
    private void moveDragged(MouseEvent event) {
        Stage st = (Stage) anaPanel2.getScene().getWindow();
        st.setX(event.getScreenX() - initX);
        st.setY(event.getScreenY() - initY);
    }

    @FXML
    private void movePressed(MouseEvent event) {
        Stage st = (Stage) anaPanel2.getScene().getWindow();
        initX = event.getSceneX();
        initY = event.getSceneY();
    }

    private void uret() {

        int satir = 0, sutun = 0;
        for (int i = 0; i < topSenet; i++) {
            btn[i] = new Button((i + 1) + ". Senet");
            btn[i].setPrefSize(150, 25);
            btn[i].setStyle(style);
            kok.add(btn[i], sutun, satir);
            satir++;

        }
        satir = 0;
        sutun = 1;
        for (int i = 0; i < topSenet; i++) {
            btn[i] = new Button(ucret.toString() + " TL");
            btn[i].setPrefSize(125, 25);
            btn[i].setStyle(style);
            kok.add(btn[i], sutun, satir);
            satir++;
        }
        satir = 0;
        sutun = 2;
        for (int i = 0; i < topSenet; i++) {
            if (i < odemeTarih.size()) {
                btnT[i] = new Button(Tarih(odemeTarih.get(i)));
            } else {
                btnT[i] = new Button("Henüz Ödeme Tarihi Yok");
            }
            btnT[i].setPrefSize(225, 25);
            btnT[i].setStyle(style);
            kok.add(btnT[i], sutun, satir);
            satir++;
        }
        satir = 0;
        sutun = 3;
        for (int i = 0; i < kalan; i++) {
            button[i] = new Button(" Ödendi ");
            button[i].setDisable(true);
            button[i].setStyle("-fx-text-fill: white; -fx-background-color: darkgreen; -fx-font-weight: bold; -fx-background-radius: 5px 5px 5px 5px;");
            button[i].setPrefSize(150, 25);
            kok.add(button[i], sutun, satir);
            satir++;
        }
        for (int i = kalan; i < topSenet; i++) {
            button[i] = new Button(" Ödenmedi ");
            button[i].setStyle("-fx-background-color: #f1161a;"
                    + "-fx-text-fill: white;"
                    + "-fx-font-weight: bold;");
            button[i].setPrefSize(150, 25);
            kok.add(button[i], sutun, satir);
            satir++;
            button[i].setOnMouseEntered((MouseEvent t) -> {
                for (int j = kalan; j < topSenet; j++) {
                    if (button[j].isDisable() == false) {
                        if (button[j].isHover()) {
                            button[j].setStyle("-fx-background-color: #ff6835;"
                                    + "-fx-text-fill: white;"
                                    + "-fx-font-weight: bold;");
                        }
                    }

                }
            });

            button[i].setOnMouseExited((MouseEvent t) -> {
                for (int j = kalan; j < topSenet; j++) {
                    if (button[j].isDisable() == false) {
                        if (!button[j].isHover()) {
                            button[j].setStyle("-fx-background-color: #f1161a;"
                                    + "-fx-text-fill: white;"
                                    + "-fx-font-weight: bold;");
                        }
                    }

                }
            });

            button[i].setOnMousePressed((MouseEvent event) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Eminmisiniz ");
                alert.setContentText("Müşterinin kirasını \n ödemek istediğinize eminmisiniz ?");
                alert.initStyle(StageStyle.UTILITY);
                ButtonType evet = new ButtonType("Evet", ButtonBar.ButtonData.OK_DONE);
                ButtonType hayir = new ButtonType("Hayır", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(evet, hayir);
                Optional<ButtonType> result = alert.showAndWait();
                for (int i1 = 0; i1 < 12; i1++) {
                    try {
                        if (button[i1].isPressed()) {
                            secilen = button[i1];
                            secileni = i1;
                            if (result.get() == evet) {
                                try {
                                    dialog();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            });
        }

    }

    public void dialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setHeaderText("Seneti ödemek için giriş yapmalısınız");
        dialog.setGraphic(new ImageView(this.getClass().getResource("img/login.png").toString()));
        ButtonType giris = new ButtonType("Giriş", ButtonData.OK_DONE);
        ButtonType iptal = new ButtonType("Iptal", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(giris, iptal);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField username = new TextField();
        username.setPromptText("Kullanıcı Adınız");
        PasswordField password = new PasswordField();
        password.setPromptText("Şifreniz");
        grid.add(new Label("Kullanıcı Adı:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Şifre:"), 0, 1);
        grid.add(password, 1, 1);
        username.setText(kIsim);
        username.setDisable(true);
        Node loginButton = dialog.getDialogPane().lookupButton(giris);
        loginButton.setDisable(true);
       password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> password.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == giris) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            try {
                if (crudKul.Kontrol(username.getText(), config.SHA1(config.MD5(password.getText())))) {
                    Ktarihler kt = new Ktarihler();
                    kt.setOdemeTarihi(simdi());
                    kt.setKiraId(kid);
                    crudKira.saveTarih(kt);
                    crudKira.kalanGuncelle(kid, kalanSenet - 1);
                    btnT[secileni].setText(Tarih(simdi()));
                    config2.dialog(Alert.AlertType.INFORMATION, "Senet Ödeme Işlemi\nBaşarıyla Gerçekleşti");
                    secilen.setText("Ödendi");
                    secilen.setDisable(true);
                    secilen.setStyle("-fx-text-fill: white; -fx-background-color: darkgreen; -fx-font-weight: bold; -fx-background-radius: 5px 5px 5px 5px;");
                } else {
                    config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.");
                }
            } catch (Exception e) {
                System.out.println(e);
                config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.");
            }
        });

    }

    public String Tarih(java.sql.Timestamp s) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = format.format(s);
        return date;
    }

    public java.sql.Timestamp simdi() throws ParseException {
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(format.format(now));
        java.sql.Timestamp d = new java.sql.Timestamp(date.getTime());
        return d;
    }
}
