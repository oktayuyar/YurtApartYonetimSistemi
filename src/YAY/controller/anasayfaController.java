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
import YAY.model.musteri;
import java.net.URL;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;

/**
 * FXML Controller class
 *
 * @author oktay
 */
public class anasayfaController implements Initializable {

    @FXML
    private ImageView imgLoad;
    @FXML
    private ProgressBar bar;
    @FXML
    private TableView<musteri> tableData;
    @FXML
    private Label lblKullanici;
    interMusteriler crud;
    interOda crudOda;
    interKullanicilar crudKul;
    interKira crudKira;
    private ObservableList<musteri> listData;
    private ObservableList<musteri> listarama;
    Integer status;
    @FXML
    private TextField ki;
    @FXML
    private AnchorPane anaPanel;
    String KId;
    DropShadow ds = new DropShadow();
    @FXML
    private PieChart pie;
    @FXML
    private Text topMus;
    @FXML
    private Text topAylik;
    @FXML
    private Text topSenet;
    @FXML
    private Text odSenet;
    @FXML
    private Text tTopMus;
    @FXML
    private Text tAylik;
    @FXML
    private Text tSenet;
    @FXML
    private Text tOdenen;
    String Style = " -fx-font-size: 13px;"
            + "   -fx-font-family: \"Arial Black\";"
            + "   -fx-fill: #ea8118;"
            + "   -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 );";
    @FXML
    private TableColumn colAction;
    @FXML
    private TableColumn<musteri, String> colmAdSoy;
    @FXML
    private TableColumn<musteri, String> colOda;
    @FXML
    private TableColumn<musteri, String> colAyUcret;
    @FXML
    private TextField ara;
    config2 con = new config2();
    @FXML
    private Label yuzde;
    String odaid,kiraid,mAdSoy;

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
            crud = ctx.getBean(interMusteriler.class);
            crudOda = ctx.getBean(interOda.class);
            crudKul = ctx.getBean(interKullanicilar.class);
            crudKira = ctx.getBean(interKira.class);
            CSS();
            topAylik.setText(crudKira.aylıkGelir().toString() + " TL");
            topMus.setText(crudOda.doluYatak().toString() + " Kişi");
            topSenet.setText(crudKira.topSenet().get(0).toString() + " Adet");
            Integer odenen = (crudKira.topSenet().get(0) - crudKira.topSenet().get(1));
            odSenet.setText(odenen.toString() + " Adet");
            KId = crudKul.idgetir(anaPanel.getUserData().toString());
            lblKullanici.setText("Hoş Geldin " + anaPanel.getUserData().toString().toUpperCase());
            status = 0;
            config2.setModelColumn(colmAdSoy, "adsoy");
            config2.setModelColumn(colOda, "odaAd");
            config2.setModelColumn(colAyUcret, "kiraUc");
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
            pieChart();
        });
    }

    public void CSS() {
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        lblKullanici.setEffect(ds);
        lblKullanici.setCache(true);
        lblKullanici.setTextFill(Color.RED);
        lblKullanici.setFont(Font.font(null, FontWeight.BOLD, 16));
        tAylik.setEffect(ds);
        tAylik.setCache(true);
        tAylik.setFill(Color.BLUEVIOLET);
        tAylik.setFont(Font.font(null, FontWeight.BOLD, 14));
        tOdenen.setEffect(ds);
        tOdenen.setCache(true);
        tOdenen.setFill(Color.BLUEVIOLET);
        tOdenen.setFont(Font.font(null, FontWeight.BOLD, 14));
        tSenet.setEffect(ds);
        tSenet.setCache(true);
        tSenet.setFill(Color.BLUEVIOLET);
        tSenet.setFont(Font.font(null, FontWeight.BOLD, 14));
        tTopMus.setEffect(ds);
        tTopMus.setCache(true);
        tTopMus.setFill(Color.BLUEVIOLET);
        tTopMus.setFont(Font.font(null, FontWeight.BOLD, 14));
        odSenet.setStyle(Style);
        topAylik.setStyle(Style);
        topMus.setStyle(Style);
        topSenet.setStyle(Style);
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

    private void aksiKlikTableData(MouseEvent event) {
        if (status == 1) {
            try {
                musteri klik = tableData.getSelectionModel().getSelectedItem();
                odaid = crudOda.idgetir(klik.getOdaAd()).toString();
                kiraid = crudKira.kiraidge(Integer.valueOf(klik.getId())).toString();
                mAdSoy = klik.getAdsoy();
            } catch (Exception e) {
            }
        }
    }

    @FXML
    private void ara(KeyEvent event) {
        arama();
    }

    @FXML
    private void arab(KeyEvent event) {
        if (ara.getText().isEmpty()) {
            selectWithService();
        } else {
            arama();
        }
    }

    private void arama() {
        listarama = FXCollections.observableArrayList(crud.ara(ara.getText()));
        tableData.setItems(listarama);
    }

    private class ButtonCell extends TableCell<Object, Boolean> {

        final Hyperlink cellButtonEdit = new Hyperlink("Odasını Göster");
        final Hyperlink cellButtonAdd = new Hyperlink("Kira Kontrol");
        final HBox hb = new HBox(cellButtonAdd, cellButtonEdit);

        ButtonCell(final TableView tblView) {

            hb.setSpacing(4);
            cellButtonEdit.setOnAction((ActionEvent event) -> {
                status = 1;
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                aksiKlikTableData(null);
                try {
                    String[] s = new String[3];
                    s[0] = odaid;
                    s[1] = "";
                    s[2] = "0";
                    con.ac2("/YAY/view/YatakSecim.fxml", "Odası", false, StageStyle.UNDECORATED, false, s);
                    status = 0;
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
            cellButtonAdd.setOnAction((ActionEvent event) -> {
                status = 1;
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                aksiKlikTableData(null);
                try {
                    String[] s = new String[3];
                    s[0] = anaPanel.getUserData().toString();
                    s[1] = kiraid;
                    s[2] = mAdSoy;
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

    public void pieChart() {
        Integer bos = crudOda.topYatak() - crudOda.doluYatak();
        System.out.println(crudOda.topYatak());
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Boş", bos),
                        new PieChart.Data("Dolu", crudOda.doluYatak()));
        pie.setData(pieChartData);
        pie.setTitle("Yurdun Doluluk Durumu");
        yuzde.setTextFill(Color.BLACK);
        yuzde.setFont(Font.font(null, FontWeight.BOLD, 12));

        pie.getData().stream().forEach((data) -> {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                yuzde.setLayoutX(e.getSceneX() - 150);
                yuzde.setLayoutY(e.getSceneY() - 50);
                yuzde.setText((int) data.getPieValue() + " Adet");
            });
        });

    }
}
