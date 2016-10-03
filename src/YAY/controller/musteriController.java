/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.controller;

import YAY.model.musteri;
import YAY.animations.FadeInUpTransition;
import YAY.config.config;
import YAY.config.config2;
import YAY.interfaces.interKira;
import YAY.interfaces.interKullanicilar;
import YAY.interfaces.interMusteriler;
import YAY.interfaces.interOda;
import YAY.model.Kira;
import YAY.model.Oda;
import YAY.model.Ogrenci;
import YAY.model.Yatak;
import YAY.style.SayisizTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;
import org.springframework.context.ApplicationContext;

/**
 * FXML Controller class
 *
 * @author oktay
 */
public class musteriController implements Initializable {
    
    @FXML
    private ImageView musteriR;
    @FXML
    private TableView<musteri> tableData;
    @FXML
    private TableColumn colAction;
    @FXML
    private Button btnNew;
    @FXML
    private AnchorPane paneTabel;
    interMusteriler crud;
    interOda crudOda;
    interKullanicilar crudKul;
    interKira crudKira;
    @FXML
    private AnchorPane paneCrud;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnBack;
    Integer status;
    @FXML
    private ImageView imgLoad;
    @FXML
    private ProgressBar bar;
    private ObservableList<musteri> listData;
    private ObservableList<musteri> listarama;
    private ObservableList<Oda> listOdaAd;
    @FXML
    private TextField ara;
    @FXML
    private AnchorPane anaPanel;
    @FXML
    private SayisizTextField txtad;
    @FXML
    private TextField txttc;
    @FXML
    private TextField txttel;
    @FXML
    private TextField txtsenet;
    @FXML
    private TextField txtucret;
    @FXML
    public TextField txtyatak;
    @FXML
    private TableColumn<musteri, String> colmAdSoy;
    @FXML
    private TableColumn<musteri, String> colOda;
    @FXML
    private TableColumn<musteri, String> colAyUcret;
    @FXML
    private TableColumn<musteri, String> colSenet;
    @FXML
    private TableColumn<musteri, String> colGiris;
    @FXML
    private TextField txtId;
    @FXML
    private ComboBox cmbOda;
    @FXML
    private TableColumn<musteri, String> colTc;
    @FXML
    private TableColumn<musteri, String> colTel;
    config2 con = new config2();
    String sResim = "person.png";
    @FXML
    private DatePicker dbaslangic;
    int sss = 0;
    int d = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     * @param a
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Platform.runLater(() -> {
            ApplicationContext ctx = config.getInstance().getApplicationContext();
            crud = ctx.getBean(interMusteriler.class);
            crudOda = ctx.getBean(interOda.class);
            crudKul = ctx.getBean(interKullanicilar.class);
            crudKira = ctx.getBean(interKira.class);
            listData = FXCollections.observableArrayList();
            listOdaAd = FXCollections.observableArrayList(crudOda.odalar());
            status = 0;
            config2.setModelColumn(colmAdSoy, "adsoy");
            config2.setModelColumn(colOda, "odaAd");
            config2.setModelColumn(colAyUcret, "kiraUc");
            config2.setModelColumn(colSenet, "topbolkal");
            config2.setModelColumn(colTc, "tc");
            config2.setModelColumn(colTel, "tel");
            config2.setModelColumn(colGiris, "tarGiris");
            colAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                    return new SimpleBooleanProperty(p.getValue() != null);
                }
            });
            colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
                @Override
                public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
                    return new ButtonCell(tableData);
                }
            });
            selectWithService();
            cmbOda.setItems(listOdaAd);
        });
    }
    
    private void clear() {
        txtad.clear();
        txtsenet.clear();
        txttc.clear();
        txttel.clear();
        cmbOda.setValue(null);
        txtyatak.clear();
        txtucret.clear();
        dbaslangic.setValue(null);
        musteriR.setImage(new Image(this.getClass().getResource("userPic/person.png").toString()));
        txtsenet.setDisable(false);
        txtucret.setDisable(false);
        dbaslangic.setDisable(false);
        d = 0;
        
    }
    
    private void auto() {
        if (crud.select().isEmpty()) {
            txtId.setText("1");
        } else {
            txtId.setText(String.valueOf(crud.auto()));
        }
    }
    
    private void selectData() {
        if (listData == null) {
            listData = FXCollections.observableArrayList(crud.select());
        } else {
            listData.clear();
            listData.addAll(crud.select());
        }
        tableData.setItems(listData);
    }
    
    private void arama() {
        listarama = FXCollections.observableArrayList(crud.ara(ara.getText()));
        tableData.setItems(listarama);
    }
    
    @FXML
    private void ara(KeyEvent event) {
        arama();
        paneCrud.setOpacity(0);
        
    }
    
    private void selectWithService() {
        Service<Integer> service = new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                selectData();
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        Integer max = crud.select().size();
                        if (max > 35) {
                            max = 30;
                        }
                        updateProgress(0, max);
                        for (int k = 0; k < max; k++) {
                            Thread.sleep(40);
                            updateProgress(k + 1, max);
                        }
                        return max;
                    }
                };
            }
        };
        service.start();
        bar.progressProperty().bind(service.progressProperty());
        service.setOnRunning((WorkerStateEvent event) -> {
            imgLoad.setVisible(true);
        });
        service.setOnSucceeded((WorkerStateEvent event) -> {
            imgLoad.setVisible(false);
            new FadeInUpTransition(paneTabel).play();
        });
    }
    
    @FXML
    private void aksiKlikTableData(MouseEvent event) {
        if (status == 1) {
            try {
                d = 1;
                musteri klik = tableData.getSelectionModel().getSelectedItem();
                Integer kira = Integer.parseInt(klik.getKiraUc());
                Integer ucret = (klik.getTopSenetSay() * kira);
                txtId.setText(klik.getId());
                txtad.setText(klik.getAdsoy());
                txtsenet.setText(klik.getSenetSay().toString());
                txtsenet.setDisable(true);
                txttc.setText(klik.getTc());
                txttel.setText(crud.musteriGetir(Integer.valueOf(klik.getId())).get(0).getTel());
                txtucret.setText(ucret.toString());
                txtucret.setDisable(true);
                txtyatak.setText(klik.getYatakId().toString());
                try {
                    musteriR.setImage(new Image(this.getClass().getResource("userPic/" + klik.getResim()).toString()));
                } catch (Exception e) {
                    musteriR.setImage(new Image(this.getClass().getResource("userPic/person.png").toString()));
                }
                dbaslangic.setValue(dateFormat(klik.getTarGiris()));
                dbaslangic.setDisable(true);
            } catch (NumberFormatException | ParseException e) {
            }
        }
    }
    
    @FXML
    private void aksiNew(ActionEvent event) {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();
            auto();
        });
    }
    
    @FXML
    private void aksiSave(ActionEvent event) throws ParseException {
        if (txtad.getText().isEmpty() || !txtad.getText().contains(" ")) {
            config2.dialog(Alert.AlertType.ERROR, "Lütfen Ad Soyad Giriniz orn(Ali Yılmaz).");
            txtad.requestFocus();
        } else if (txtsenet.getText().isEmpty() || Integer.valueOf(txtsenet.getText()) > 12) {
            config2.dialog(Alert.AlertType.ERROR, "Lütfen Senet Sayısı Giriniz");
            txtsenet.requestFocus();
        } else if (txttel.getText().isEmpty() || txttel.getText().length() < 13) {
            config2.dialog(Alert.AlertType.ERROR, "Lütfen 10 haneli Telefon  Numarası Giriniz");
            txttel.requestFocus();
        } else if (txtucret.getText().isEmpty() || Integer.valueOf(txtucret.getText()) < 500) {
            config2.dialog(Alert.AlertType.ERROR, "Lütfen Toplam Ucreti Giriniz 500 Den\nKüçük Deger Girmeyiniz.");
            txtucret.requestFocus();
        } else if (txtyatak.getText().isEmpty()) {
            config2.dialog(Alert.AlertType.ERROR, "Lütfen Yatak Seçin");
            txtyatak.requestFocus();
        } else if (!txttc.getText().isEmpty() && txttc.getText().length() != 11) {
            config2.dialog(Alert.AlertType.ERROR, "Lütfen 11 haneli Bir Tc Numarası Giriniz");
            txtsenet.requestFocus();
        } else {
            Ogrenci a = new Ogrenci();
            Kira k = new Kira();
            Yatak y = new Yatak();
            if (txttc.getText().isEmpty()) {
                a.setTc(0);
            } else {
                a.setTc(Long.valueOf(txttc.getText()));
            }
            a.setTel(txttel.getText());
            a.setOgrenciAd(txtad.getText());
            a.setGirisTarihi(donDate(simdi()));
            a.setYatakId(Integer.valueOf(txtyatak.getText()));
            if (d == 0) {
                a.setKiraId(crudKira.auto());
            } else {
                a.setKiraId(crudKira.kiraidge(Integer.valueOf(txtId.getText())));
            }
            a.setResim(sResim);
            a.setOgrenciId(Integer.valueOf(txtId.getText()));
            k.setKiraId(crudKira.auto());
            k.setKiraUcret(Integer.valueOf(txtucret.getText()) / Integer.valueOf(txtsenet.getText()));
            k.setSenetSayisi(Integer.valueOf(txtsenet.getText()));
            k.setKalanSenet(Integer.valueOf(txtsenet.getText()));
            y.setYatakId(Integer.valueOf(txtyatak.getText()));
            try {
                k.setBaslangicTarihi(donDate(dbaslangic.getValue().toString()));
                if (d == 1) {
                    if (cmbOda.getValue() != null) {
                        y.setOdaId(crudOda.idgetir(cmbOda.getValue().toString()));
                        crudOda.delete(crudOda.yatakg(Integer.valueOf(txtId.getText())));
                        crudOda.yatakEkle(y);
                    }
                }
                crud.saveOrUpdate(a);
                if (d == 0) {
                    y.setOdaId(crudOda.idgetir(cmbOda.getValue().toString()));
                    crudOda.yatakEkle(y);
                    crudKira.saveOrUpdate(k);
                }
                clear();
                selectData();
                auto();
                config2.dialog(Alert.AlertType.INFORMATION, "Başarıyla kaydedildi. . .");
            } catch (ParseException | NumberFormatException e) {
                config2.dialog(Alert.AlertType.ERROR, "Lütfen Ödeme Başlangıç Tarihi Giriniz.");
                System.out.println(e);
            }
        }
    }
    
    @FXML
    private void aksiBack(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }
    
    @FXML
    private void arab(KeyEvent event) {
        if (ara.getText().isEmpty()) {
            selectWithService();
            paneCrud.setOpacity(0);
        } else {
            arama();
            paneCrud.setOpacity(0);
        }
    }
    
    @FXML
    private void odaSecAction(ActionEvent event) {
        try {
            String[] s = new String[3];
            s[0] = crudOda.idgetir(cmbOda.getValue().toString()).toString();
            s[1] = txtad.getText();
            s[2] = "1";
            con.ac(txtyatak, "/YAY/view/YatakSecim.fxml", "Yatak Seçim", false, StageStyle.UNDECORATED, false, s);
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void resimSec(ActionEvent event) {
        Image mg = null;
        
        FileChooser fc = new FileChooser();
        fc.setTitle("Bir Resim Seciniz");
        FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("JPG", "*.jpg");
        FileChooser.ExtensionFilter png = new FileChooser.ExtensionFilter("PNG", "*.png");
        fc.getExtensionFilters().addAll(png, jpg);
        try {
            File resim = fc.showOpenDialog(null);
            FileInputStream from = new FileInputStream(resim);
            String yol = "src/YAY/controller/userPic/" + resim.getName();
            FileOutputStream hedef = new FileOutputStream(yol);
            byte[] buffer = new byte[4096];
            int okunan;
            while ((okunan = from.read(buffer)) != -1) {
                hedef.write(buffer, 0, okunan);
            }
            Thread.sleep(50);
            from.close();
            hedef.close();
            mg = new Image(resim.toURI().toString());
            sResim = resim.getName();
        } catch (IOException | InterruptedException e) {
            sResim = "person.png";
            mg = new Image(this.getClass().getResource("userPic/person.png").toString());
            config2.dialog(Alert.AlertType.ERROR, "Lütfen Resim Dosyayı Seçiniz ya da tekrar deneyiniz.");
        }
        musteriR.setImage(mg);
        
    }
    
    @FXML
    private void senetKeyReleased(KeyEvent event) {
        try {
            if (Integer.valueOf(txtsenet.getText()) > 12) {
                config2.dialog(Alert.AlertType.INFORMATION, "Senet sayısı 12 den küçük olmalı");
                txtsenet.setText("");
            }
        } catch (Exception e) {
        }
        
    }
    
    @FXML
    private void telKeyReleased(KeyEvent event) {
        try {
            if (Integer.valueOf(txttel.getText().substring(0, 1)) == 0) {
                config2.dialog(Alert.AlertType.INFORMATION, "Telefon Numarası Sıfırsız (555-555-55-55) Şekilde Olmalı");
                txttel.setText("");
            }
        } catch (Exception e) {
        }
        
    }
    
    private class ButtonCell extends TableCell<Object, Boolean> {
        
        final Hyperlink cellButtonDelete = new Hyperlink("Sil");
        final Hyperlink cellButtonEdit = new Hyperlink("Düzenle");
        final Hyperlink cellButtonAdd = new Hyperlink("Kira Kontrol");
        final HBox hb = new HBox(cellButtonAdd, cellButtonDelete, cellButtonEdit);
        
        ButtonCell(final TableView tblView) {
            
            hb.setSpacing(4);
            cellButtonDelete.setOnAction((ActionEvent t) -> {
                status = 1;
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                aksiKlikTableData(null);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Eminmisiniz ");
                alert.setContentText(txtad.getText() + " İsimli müşteriyi silmek \nistediğinize eminmisiniz ?");
                alert.initStyle(StageStyle.UTILITY);
                ButtonType evet = new ButtonType("Evet", ButtonData.OK_DONE);
                ButtonType hayir = new ButtonType("Hayır", ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(evet, hayir);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == evet) {
                    dialog(txtad.getText(), Integer.valueOf(txtId.getText()));
                } else {
                    clear();
                    selectData();
                    auto();
                }
                status = 0;
            });
            cellButtonEdit.setOnAction((ActionEvent event) -> {
                status = 1;
                d = 1;
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                aksiKlikTableData(null);
                paneTabel.setOpacity(0);
                new FadeInUpTransition(paneCrud).play();
                status = 0;
            });
            cellButtonAdd.setOnAction((ActionEvent event) -> {
                status = 1;
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                aksiKlikTableData(null);
                try {
                    String[] s = new String[3];
                    s[0] = anaPanel.getUserData().toString();
                    s[1] = crudKira.kiraidge(Integer.valueOf(txtId.getText())).toString();
                    s[2] = txtad.getText();
                    con.ac2("/YAY/view/KiraGrid.fxml", "Kira Bilgi Ve Odeme", false, StageStyle.UNDECORATED, false, s);
                    status = 0;
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
        }
        
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(hb);
            } else {
                setGraphic(null);
            }
        }
    }
    
    public LocalDate dateFormat(String s) throws ParseException {
        LocalDate myDate = LocalDate.parse(s);
        return myDate;
    }
    
    public java.sql.Date donDate(String s) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(s);
        java.sql.Date d = new java.sql.Date(date.getTime());
        return d;
    }
    
    public void dialog(String s, Integer a) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setHeaderText(s + "\nmüşterisini silmek için giriş yapmalısınız");
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
        username.setText(anaPanel.getUserData().toString());
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
                    List<Ogrenci> p;
                    p = crud.musteriGetir(a);
                    crudKira.delete(p.get(0).getKiraId());
                    crudOda.delete(p.get(0).getYatakId());
                    crud.delete(p.get(0));
                    clear();
                    selectData();
                } else {
                    config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.");
                }
            } catch (Exception e) {
                config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.");
            }
        });
    }
    
    public String simdi() throws ParseException {
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(now);
    }
    
}
