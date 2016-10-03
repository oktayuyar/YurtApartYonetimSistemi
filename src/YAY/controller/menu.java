/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.controller;

import YAY.animations.FadeInUpTransition;
import YAY.config.config2;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author oktay
 */
public class menu implements Initializable {

    @FXML
    private Button close;
    @FXML
    private Button minimize;
    @FXML
    private Label title;
    Stage stage;
    Rectangle2D rec2;
    Double w, h, initX, initY;
    @FXML
    private ListView<ImageView> listMenu;
    @FXML
    private AnchorPane paneData;
    config2 con = new config2();
    @FXML
    private Button btnLogout;
    @FXML
    private AnchorPane anaPanel;
    @FXML
    private AnchorPane alist;
    double hh = 25;
    private final ImageView anas = new ImageView(this.getClass().getResource("img/anasayfa.png").toString());
    private final ImageView musteriler = new ImageView(this.getClass().getResource("img/musteri.png").toString());
    private final ImageView oda = new ImageView(this.getClass().getResource("img/oda.png").toString());
    private final ImageView kira = new ImageView(this.getClass().getResource("img/kira.png").toString());
    private final ImageView reklam = new ImageView(this.getClass().getResource("img/reklam.png").toString());
    private final ImageView anas2 = new ImageView(this.getClass().getResource("img/anasayfa2.png").toString());
    private final ImageView musteriler2 = new ImageView(this.getClass().getResource("img/musteri2.png").toString());
    private final ImageView oda2 = new ImageView(this.getClass().getResource("img/oda2.png").toString());
    private final ImageView kira2 = new ImageView(this.getClass().getResource("img/kira2.png").toString());
    private final ImageView reklam2 = new ImageView(this.getClass().getResource("img/reklam2.png").toString());
    @FXML
    private ListView<ImageView> listkMenu;
    @FXML
    private Button side;
    @FXML
    private Button maximize;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resimleriGetir();
        side.setTooltip(new Tooltip("Menu Gizle"));
        btnLogout.setTooltip(new Tooltip("Çıkış Yap"));
        rec2 = Screen.getPrimary().getVisualBounds();
        w = 0.1;
        h = 0.1;
        listkMenu.getItems().addAll(anas, musteriler, oda, kira, reklam);
        listMenu.getItems().addAll(anas2, musteriler2, oda2, kira2, reklam2);
        Platform.runLater(() -> {
            listMenu.getSelectionModel().select(0);
            con.loadAnchorPane(paneData, "anasayfa.fxml", anaPanel.getUserData().toString());
            listMenu.requestFocus();
            listkMenu.requestFocus();
            listkMenu.setVisible(false);
        });
    }

    @FXML
    private void aksiminimize(ActionEvent event) {
        stage = (Stage) minimize.getScene().getWindow();
        if (stage.isMaximized()) {
            w = rec2.getWidth();
            h = rec2.getHeight();
            stage.setMaximized(false);
            stage.setHeight(h);
            stage.setWidth(w);
            stage.centerOnScreen();
            Platform.runLater(() -> {
                stage.setIconified(true);
            });
        } else {
            stage.setIconified(true);
        }
    }

    @FXML
    private void aksiClose(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void aksiKlikListMenu(MouseEvent event) {
        switch (listMenu.getSelectionModel().getSelectedIndex()) {
            case 0: {
                con.loadAnchorPane(paneData, "anasayfa.fxml", anaPanel.getUserData().toString());
            }
            break;
            case 1: {
                con.loadAnchorPane(paneData, "musteriler.fxml", anaPanel.getUserData().toString());
            }
            break;
            case 2: {
                con.loadAnchorPane(paneData, "odalar.fxml", anaPanel.getUserData().toString());
            }
            break;
            case 3: {
                con.loadAnchorPane(paneData, "kira.fxml", anaPanel.getUserData().toString());
            }
            break;
            case 4: {
                con.loadAnchorPane(paneData, "ayarlar.fxml", anaPanel.getUserData().toString());
            }
            break;
        }
    }

    @FXML
    private void aksiLogout(ActionEvent event) {
        config2 config = new config2();
        config.newStage2(stage, btnLogout, "/YAY/view/login.fxml", "Oturum Aç", false, StageStyle.UNDECORATED, false);
    }

    @FXML
    private void moveDragged(MouseEvent evt) {
        Stage st = (Stage) anaPanel.getScene().getWindow();
        st.setX(evt.getScreenX() - initX);
        st.setY(evt.getScreenY() - initY);
    }

    @FXML
    private void movePressed(MouseEvent evt) {
        Stage st = (Stage) anaPanel.getScene().getWindow();
        initX = evt.getSceneX();
        initY = evt.getSceneY();
    }

    @FXML
    private void sidea(ActionEvent event) {
        stage = (Stage) maximize.getScene().getWindow();
        if (listMenu.isVisible()) {
            alist.setPrefWidth(55);
            listkMenu.getSelectionModel().select(listMenu.getSelectionModel().getSelectedIndex());
            listkMenu.setVisible(true);
            listMenu.setVisible(false);
            if (stage.isMaximized()) {
                paneData.setPrefWidth(1297);
            } else {
                paneData.setPrefWidth(997);
            }

            new FadeInUpTransition(listkMenu).play();
        } else {
            alist.setPrefWidth(137);
            listMenu.getSelectionModel().select(listkMenu.getSelectionModel().getSelectedIndex());
            listkMenu.setVisible(false);
            listMenu.setVisible(true);
            if (stage.isMaximized()) {
                paneData.setPrefWidth(1222);
            } else {
                paneData.setPrefWidth(922);
            }
            new FadeInUpTransition(listMenu).play();
        }
    }

    private void setimageview(ImageView v, ImageView v2) {
        v.setFitHeight(hh);
        v.setFitWidth(hh);
        v2.setFitHeight(30);
        v2.setFitWidth(125);

    }

    @FXML
    private void aksiKlikListkMenu(MouseEvent event) {
        switch (listkMenu.getSelectionModel().getSelectedIndex()) {
            case 0: {
                con.loadAnchorPane(paneData, "anasayfa.fxml", anaPanel.getUserData().toString());
            }
            break;
            case 1: {
                con.loadAnchorPane(paneData, "musteriler.fxml", anaPanel.getUserData().toString());
            }
            break;
            case 2: {
                con.loadAnchorPane(paneData, "odalar.fxml", anaPanel.getUserData().toString());
            }
            break;
            case 3: {
                con.loadAnchorPane(paneData, "kira.fxml", anaPanel.getUserData().toString());
            }
            break;
            case 4: {
                con.loadAnchorPane(paneData, "ayarlar.fxml", anaPanel.getUserData().toString());
            }
            break;
        }
    }

    private void resimleriGetir() {
        setimageview(anas, anas2);
        setimageview(musteriler, musteriler2);
        setimageview(oda, oda2);
        setimageview(kira, kira2);
        setimageview(reklam, reklam2);
        Tooltip.install(anas, new Tooltip("Anasayfa"));
        Tooltip.install(musteriler, new Tooltip("Müşteriler"));
        Tooltip.install(oda, new Tooltip("Odalar"));
        Tooltip.install(kira, new Tooltip("Kiralar"));
        Tooltip.install(reklam, new Tooltip("Reklam"));

    }

    @FXML
    private void aksiMaximized(ActionEvent event) {
        stage = (Stage) maximize.getScene().getWindow();
        if (stage.isMaximized()) {
            if (w == rec2.getWidth() && h == rec2.getHeight()) {
                stage.setMaximized(false);
                stage.setHeight(704);
                stage.setWidth(1070);
                stage.centerOnScreen();
                maximize.getStyleClass().remove("decoration-button-restore");
            } else {
                stage.setMaximized(false);
                maximize.getStyleClass().remove("decoration-button-restore");
            }
            if (listMenu.isVisible()) {
                paneData.setPrefWidth(922);
            } else {
                paneData.setPrefWidth(997);
            }
        } else {
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");
            if (listMenu.isVisible()) {
                paneData.setPrefWidth(1222);
            } else {
                paneData.setPrefWidth(1297);
            }
        }
    }
}
