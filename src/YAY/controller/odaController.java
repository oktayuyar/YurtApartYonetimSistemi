/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.controller;

import YAY.animations.FadeInUpTransition;
import YAY.config.config;
import YAY.config.config2;
import YAY.interfaces.interKullanicilar;
import YAY.interfaces.interOda;
import YAY.model.Oda;
import java.net.URL;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;
import org.springframework.context.ApplicationContext;

/**
 * FXML Controller class
 *
 * @author oktay
 */
public class odaController implements Initializable {

    @FXML
    private AnchorPane anaPanel;
    @FXML
    private AnchorPane paneCrud;
    @FXML
    private TextField txtoid;
    @FXML
    private TextField txtoad;
    @FXML
    private TextField txtomik;
    @FXML
    private TextField txtokat;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnBack;
    @FXML
    private AnchorPane paneTabel;
    @FXML
    private TableView<Oda> tableData;
    @FXML
    private TableColumn colAction;
    @FXML
    private TableColumn<Oda, String> colmoad;
    @FXML
    private TableColumn<Oda, Integer> colomik;
    @FXML
    private TableColumn<Oda, Integer> colkat;
    @FXML
    private TextField ara;
    @FXML
    private Button btnNew;
    @FXML
    private ImageView imgLoad;
    @FXML
    private ProgressBar bar;
    private ObservableList<Oda> listData;
    private ObservableList<Oda> listarama;
    config2 con = new config2();
    interOda crudOda;
    interKullanicilar crudKul;
    Integer status;

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
            crudOda = ct.getBean(interOda.class);
            crudKul = ct.getBean(interKullanicilar.class);
            listData = FXCollections.observableArrayList();
            status = 0;
            config2.setModelColumn(colmoad, "odaAd");
            config2.setModelColumn(colomik, "yatakAdet");
            config2.setModelColumn(colkat, "katAd");
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
        });
    }

    @FXML
    private void aksiSave(ActionEvent event) {
        if (txtoad.getText().isEmpty()) {
            config2.dialog(Alert.AlertType.ERROR, "Lütfen Oda Adı Giriniz.");
            txtoad.requestFocus();
        } else if (txtokat.getText().isEmpty()) {
            config2.dialog(Alert.AlertType.ERROR, "Lütfen Odanın Katını Giriniz.");
            txtokat.requestFocus();
        } else if (txtomik.getText().isEmpty()) {
            config2.dialog(Alert.AlertType.ERROR, "Lütfen Odanın Yatak Sayısını Giriniz.");
            txtomik.requestFocus();
        } else {
            Oda o = new Oda();
            o.setOdaId(Integer.valueOf(txtoid.getText()));
            o.setKatAd(Integer.valueOf(txtokat.getText()));
            o.setOdaAd(txtoad.getText());
            o.setYatakAdet(Integer.valueOf(txtomik.getText()));
            crudOda.odaEkle(o);
            clear();
            selectData();
            auto();
            config2.dialog(Alert.AlertType.INFORMATION, "Başarıyla kaydedildi. . .");
        }
    }

    @FXML
    private void aksiBack(ActionEvent event) {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();
    }

    @FXML
    private void aksiKlikTableData(MouseEvent event) {
        if (status == 1) {
            Oda klik = tableData.getSelectionModel().getSelectedItem();
            txtoad.setText(klik.getOdaAd());
            txtoid.setText(klik.getOdaId().toString());
            txtokat.setText(klik.getKatAd().toString());
            txtomik.setText(klik.getYatakAdet().toString());
        }
    }

    @FXML
    private void ara(KeyEvent event) {
        arama();
        paneCrud.setOpacity(0);
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
    private void aksiNew(ActionEvent event) {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();
            auto();
        });

    }

    @FXML
    private void katKeyPress(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (txtoad.getText().isEmpty()) {
                config2.dialog(Alert.AlertType.ERROR, "Lütfen Oda Adı Giriniz.");
                txtoad.requestFocus();
            } else if (txtokat.getText().isEmpty()) {
                config2.dialog(Alert.AlertType.ERROR, "Lütfen Odanın Katını Giriniz.");
                txtokat.requestFocus();
            } else if (txtomik.getText().isEmpty()) {
                config2.dialog(Alert.AlertType.ERROR, "Lütfen Odanın Yatak Sayısını Giriniz.");
                txtomik.requestFocus();
            } else {
                Oda o = new Oda();
                o.setOdaId(Integer.valueOf(txtoid.getText()));
                o.setKatAd(Integer.valueOf(txtokat.getText()));
                o.setOdaAd(txtoad.getText());
                o.setYatakAdet(Integer.valueOf(txtomik.getText()));
                crudOda.odaEkle(o);
                clear();
                selectData();
                auto();
                config2.dialog(Alert.AlertType.INFORMATION, "Başarıyla kaydedildi. . .");
            }
        }
    }

    private void selectWithService() {
        Service<Integer> service = new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                selectData();
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        Integer max = crudOda.select().size();
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

    private void selectData() {
        if (listData == null) {
            listData = FXCollections.observableArrayList(crudOda.select());
        } else {
            listData.clear();
            listData.addAll(crudOda.select());
        }
        tableData.setItems(listData);

    }

    private class ButtonCell extends TableCell<Object, Boolean> {

        final Hyperlink cellButtonDelete = new Hyperlink("Sil");
        final Hyperlink cellButtonEdit = new Hyperlink("Düzenle");
        final Hyperlink cellButtonAdd = new Hyperlink("Odayı Göster");
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
                alert.setContentText(txtoad.getText() + " İsimli müşteriyi silmek \nistediğinize eminmisiniz ?");
                alert.initStyle(StageStyle.UTILITY);
                ButtonType evet = new ButtonType("Evet", ButtonBar.ButtonData.OK_DONE);
                ButtonType hayir = new ButtonType("Hayır", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(evet, hayir);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == evet) {
                    dialog(txtoad.getText(), Integer.valueOf(txtoid.getText()));
                } else {
                    clear();
                    selectData();
                    auto();
                }
                status = 0;
            });
            cellButtonEdit.setOnAction((ActionEvent event) -> {
                status = 1;
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
                    s[0] = txtoid.getText();
                    s[1] = "";
                    s[2] = "0";
                    con.ac2("/YAY/view/YatakSecim.fxml", "Oda Bilgisi", false, StageStyle.UNDECORATED, false, s);
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

    private void arama() {
        listarama = FXCollections.observableArrayList(crudOda.ara(ara.getText()));
        tableData.setItems(listarama);

    }

    private void clear() {
        txtoad.setText("");
        txtoid.setText("");
        txtokat.setText("");
        txtomik.setText("");
    }

    private void auto() {
        if (crudOda.select().isEmpty()) {
            txtoid.setText("1");
        } else {
            txtoid.setText(String.valueOf(crudOda.auto2()));
        }
    }

    public void dialog(String s, Integer a) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setHeaderText(s + "isimli odayı\nsilmek için giriş yapmalısınız");
        dialog.setGraphic(new ImageView(this.getClass().getResource("img/login.png").toString()));
        ButtonType giris = new ButtonType("Giriş", ButtonBar.ButtonData.OK_DONE);
        ButtonType iptal = new ButtonType("Iptal", ButtonBar.ButtonData.CANCEL_CLOSE);
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
                if (crudKul.Kontrol(usernamePassword.getValue(), config.SHA1(config.MD5(usernamePassword.getKey())))) {
                    if (crudOda.varmi(a) == 0) {
                        crudOda.delete2(a);
                        clear();
                        selectData();
                        config2.dialog(Alert.AlertType.INFORMATION, "Oda Başarıyla Silindi.");
                    } else {
                        config2.dialog(Alert.AlertType.ERROR, "Oda Boş Değiş Önce Odayı Boşaltınız.");
                    }
                } else {
                    config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.");
                }
            } catch (Exception e) {
                config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.");
            }
        });
    }
}
