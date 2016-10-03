/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.controller;

import YAY.model.KiraBilgi;
import YAY.animations.FadeInUpTransition;
import YAY.config.config;
import YAY.config.config2;
import YAY.interfaces.interKullanicilar;
import YAY.interfaces.interKira;
import YAY.model.Oda;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.ButtonBar.ButtonData;
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
public class kiraController implements Initializable {

    @FXML
    private TableView<KiraBilgi> tableData;
    @FXML
    private TableColumn colAction;
    @FXML
    private AnchorPane paneTabel;

    interKira crud;
    interKullanicilar crudKul;
    Integer status;
    @FXML
    private ImageView imgLoad;
    @FXML
    private ProgressBar bar;
    private ObservableList<KiraBilgi> listData;
    private ObservableList<KiraBilgi> listarama;
    @FXML
    private TextField ara;
    @FXML
    private AnchorPane anaPanel;

    @FXML
    private TableColumn<KiraBilgi, String> colmAdSoy;
    @FXML
    private TableColumn<KiraBilgi, String> colAyUcret;
    @FXML
    private TableColumn<KiraBilgi, String> colKalSenet;

    @FXML
    private TableColumn<KiraBilgi, String> colSenet;
    @FXML
    private TableColumn<KiraBilgi, String> colTopUcret;

    @FXML
    private TableColumn<KiraBilgi, String> colGiris;

    config2 con = new config2();

    String adSoy, kid;

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
            crud = ct.getBean(interKira.class);
            crudKul = ct.getBean(interKullanicilar.class);
            listData = FXCollections.observableArrayList();
            status = 0;
            config2.setModelColumn(colmAdSoy, "adsoy");
            config2.setModelColumn(colAyUcret, "kiraUc");
            config2.setModelColumn(colKalSenet, "senetSay");
            config2.setModelColumn(colSenet, "kalanBorc");
            config2.setModelColumn(colTopUcret, "topBorc");
            config2.setModelColumn(colGiris, "odemeBas");
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
                KiraBilgi k = tableData.getSelectionModel().getSelectedItem();
                adSoy = k.getAdsoy();
                kid=k.getKid();
            }catch (Exception e) {
                
            }
        }
    }

    @FXML
    private void arab(KeyEvent event) {
        if (ara.getText().isEmpty()) {
            selectWithService();
        } else {
            arama();
        }
    }

    private class ButtonCell extends TableCell<Object, Boolean> {
        
        final Hyperlink cellButtonAdd = new Hyperlink("Kira Öde");
        final HBox hb = new HBox(cellButtonAdd);

        ButtonCell(final TableView tblView) {

            hb.setSpacing(3);

            cellButtonAdd.setOnAction((ActionEvent event) -> {
                status = 1;
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                aksiKlikTableData(null);
                System.out.println(adSoy+"  "+kid+"   "+anaPanel.getUserData().toString());
                try {
                    String[] s = new String[3];
                    s[0] = anaPanel.getUserData().toString();
                    s[1] = kid;
                    s[2] = adSoy;
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

    public String simdi() throws ParseException {
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(now);
    }

    public void dialog(String s) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setHeaderText(s + "\nkira ödemek için giriş yapmalısınız");
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

                } else {
                    config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.");
                }
            } catch (Exception e) {
                config2.dialog(Alert.AlertType.ERROR, "Kullanıcı adı ya da şifreniz yanlış \nlütfen kontrol edip tekrar deneyiniz.");
            }
        });
    }
}
