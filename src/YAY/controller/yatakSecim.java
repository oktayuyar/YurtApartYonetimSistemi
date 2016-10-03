/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.controller;

import YAY.config.config;
import YAY.config.config2;
import YAY.interfaces.interMusteriler;
import YAY.interfaces.interOda;
import YAY.model.odaSayilar;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

/**
 * FXML Controller class
 *
 * @author oktay
 */
public class yatakSecim implements Initializable {

    Image image = new Image(this.getClass().getResource("img/s2.png").toString());
    Image image2 = new Image(this.getClass().getResource("img/s.png").toString());
    ImageView[] img = new ImageView[20];
    @FXML
    private GridPane kok;
    RadioButton[] ch = new RadioButton[20];
    Label[] lb = new Label[20];
    @FXML
    private Button close;
    @FXML
    private Label title;
    interOda crudOda;
    interMusteriler crudMus;
    @FXML
    private AnchorPane anaPanel2;
    String id, isim;
    String[] s = new String[3];
    Double initX, initY;
    List<String> kalanlar ;
    odaSayilar o ;
    int sectimi= 0;
    Integer sayfa;
    @FXML
    private Button btnEkle;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            s = (String[]) anaPanel2.getUserData();
             id = s[0];
             isim = s[1];
             sayfa = Integer.valueOf(s[2]);
            ApplicationContext ctx = config.getInstance().getApplicationContext();
            crudOda = ctx.getBean(interOda.class);
            crudMus = ctx.getBean(interMusteriler.class);
           title.setText(crudOda.adgetir(Integer.parseInt(id))+" Adlı Oda");
           kalanlar = crudMus.odadakiler(Integer.parseInt(id));
            o=crudOda.odaSayi(Integer.parseInt(id));
            ToggleGroup grup = new ToggleGroup();
            int row = 1, column = 0, b = 0, d = 2;
            for (int i = 0; i < o.getTopYatak(); i++) {
                img[i] = new ImageView(image);
                ch[i] = new RadioButton();
                ch[i].setToggleGroup(grup);
                lb[i] = new Label("Boş");
                lb[i].setTextFill(Color.web("#cc0d0d"));
                lb[i].setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 14));
                lb[i].setTooltip(new Tooltip("Boş"));
                img[i].setFitHeight(60);
                img[i].setFitWidth(90);
                kok.add(img[i], column, row);
                kok.add(lb[i], column, b);
                kok.add(ch[i], column, d);
                column++;
                if (column == 4) {
                    row = row + 3;
                    b = b + 3;
                    d = d + 3;
                    column = 0;
                }
                ch[i].setOnMouseClicked((MouseEvent event) -> {
                    kontrol();
                });

            }
            for (int i2 = 0; i2 < o.getDoluyatak(); i2++) {
                ch[i2].setToggleGroup(null);
                ch[i2].setDisable(true);
                ch[i2].setSelected(true);
                img[i2].setImage(image2);
                lb[i2].setText(kalanlar.get(i2));
                lb[i2].setTooltip(new Tooltip(kalanlar.get(i2)));
            }
            kontrol();
            if(sayfa == 1){
                btnEkle.setVisible(true);
                btnEkle.setDisable(false);
            }else{
                btnEkle.setVisible(false);
                btnEkle.setDisable(true);
            }
        });
    }

    public void kontrol() {
        for (int a = o.getDoluyatak(); a < o.getTopYatak(); a++) {
            if (ch[a].isSelected()) {
                lb[a].setText(isim);
                lb[a].setTooltip(new Tooltip(isim));
                img[a].setImage(image2);
                sectimi = 1;
            } else {
                lb[a].setText("Boş");
                lb[a].setTooltip(new Tooltip("Boş"));
                img[a].setImage(image);
            }
        }
    }

    @FXML
    private void aksiClose(ActionEvent event) {
        Stage st = (Stage) kok.getScene().getWindow();
         st.close();
    }

    @FXML
    private void ekle(ActionEvent event) {
        if(sectimi == 1){
            Stage st = (Stage) kok.getScene().getWindow();
        TextField t = (TextField) st.getUserData();
        t.setText(crudOda.auto().toString());
            st.close();
        }else{
            config2.dialog(Alert.AlertType.ERROR, "Lütfen Yatak Seçin");
        }
    }

    @FXML
    private void moveDragged(MouseEvent evt) {
        Stage st = (Stage) anaPanel2.getScene().getWindow();
        st.setX(evt.getScreenX() - initX);
        st.setY(evt.getScreenY() - initY);
    }

    @FXML
    private void movePressed(MouseEvent evt) {
        Stage st = (Stage) anaPanel2.getScene().getWindow();
        initX = evt.getSceneX();
        initY = evt.getSceneY();
    }

    
}
