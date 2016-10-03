/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.config;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author oktay
 */
public class config2 {

    public config2() {
    }

    public static void dialog(Alert.AlertType alertType, String s) {
        Alert alert = new Alert(alertType, s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Bilgi");
        ButtonType tamam = new ButtonType("Tamam", ButtonBar.ButtonData.OK_DONE);
        alert.setHeaderText("Bilgi");
        alert.getButtonTypes().set(0, tamam);
        alert.showAndWait();
    }

    public void newStage(Stage stage, Label lb, String load, String judul, boolean resize, StageStyle style, boolean maximized, String k) {
        try {
            Stage st = new Stage();
            stage = (Stage) lb.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            st.setResizable(resize);
            st.initStyle(style);
            Image anotherIcon = new Image(getClass().getResourceAsStream("icon.png"));
            st.getIcons().add(anotherIcon);
            st.setMaximized(maximized);
            st.setTitle(judul);
            st.setScene(scene);
            root.setUserData(k);
            st.show();
            stage.close();
        } catch (Exception e) {
        }
    }

    public void newStage2(Stage stage, Button lb, String load, String judul, boolean resize, StageStyle style, boolean maximized) {
        try {
            Stage st = new Stage();
            stage = (Stage) lb.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            st.setResizable(resize);
            st.initStyle(style);
            st.setMaximized(maximized);
            st.setTitle(judul);
            st.setScene(scene);
            st.show();
            stage.close();
        } catch (Exception e) {
        }
    }

    public void ac(TextField t, String load, String judul, boolean resize, StageStyle style, boolean maximized, String[] k) {
        try {
            Stage st = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            root.setUserData(k);
            st.setResizable(resize);
            st.initStyle(style);
            st.setMaximized(maximized);
            st.setTitle(judul);
            st.setScene(scene);
            st.setUserData(t);
            st.show();
        } catch (Exception e) {
        }
    }

    public void ac2(String load, String judul, boolean resize, StageStyle style, boolean maximized, String[] k) {
        try {
            Stage st = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            root.setUserData(k);
            st.setResizable(resize);
            st.initStyle(style);
            st.setMaximized(maximized);
            st.setTitle(judul);
            st.setScene(scene);
            st.show();
        } catch (Exception e) {
        }
    }

    public void loadAnchorPane(AnchorPane ap, String a, String uN) {
        try {
            AnchorPane p = FXMLLoader.load(getClass().getResource("/YAY/view/" + a));
            p.setUserData(uN);
            ap.getChildren().setAll(p);
        } catch (IOException e) {
        }
    }

    public static void setModelColumn(TableColumn tb, String a) {
        tb.setCellValueFactory(new PropertyValueFactory(a));
    }
}
