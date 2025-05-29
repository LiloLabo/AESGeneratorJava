/*
 * Copyright (C) PT. Marstech Global
 * http://www.marstech.co.id
 * Email. info@marstech.co.id
 * Telp. 0811-3636-09
 * Office        : Jl. Bulu Mas II No. 1 Kanigoro Kartoharjo Kota Madiun - Jawa Timur
 * Branch Office : Perum Griya Gadang Sejahtera Kav. 14 Gadang - Sukun - Kota Malang - Jawa Timur
 *
 * Global Accounting
 * Adalah merek dagang dari PT. Marstech Global
 * 
 * Licensi Agreement
 * Software komputer atau perangkat lunak komputer ini telah diakui sebagai salah satu aset perusahaan yang bernilai.
 * Di Indonesia secara khusus,
 * software telah dianggap seperti benda-benda berwujud lainnya yang memiliki kekuatan hukum.
 * Oleh karena itu pemilik software berhak untuk memberi ijin atau tidak memberi ijin orang lain untuk menggunakan softwarenya.
 * Dalam hal ini ada aturan hukum yang berlaku di Indonesia yang secara khusus melindungi para programmer dari pembajakan software yang mereka buat,
 * yaitu diatur dalam hukum hak kekayaan intelektual (HAKI).
 *
 ********************************************************************************************************
 * Pasal 72 ayat 3 UU Hak Cipta berbunyi,                                                               *
 * \" Barangsiapa dengan sengaja dan tanpa hak memperbanyak penggunaan untuk kepentingan komersial  "/  *
 * \" suatu program komputer dipidana dengan pidana penjara paling lama 5 (lima) tahun dan/atau     "/  *
 * \" denda paling banyak Rp. 500.000.000,00 (lima ratus juta rupiah)                               "/  *
 ********************************************************************************************************
 *
 * Proprietary Software
 * Adalah software berpemilik, sehingga seseorang harus meminta izin serta dilarang untuk mengedarkan,
 * menggunakan atau memodifikasi software tersebut.
 *
 * Commercial software
 * Adalah software yang dibuat dan dikembangkan oleh perusahaan dengan konsep bisnis,
 * dibutuhkan proses pembelian atau sewa untuk bisa menggunakan software tersebut.
 * Detail Licensi yang dianut di software https://en.wikipedia.org/wiki/Proprietary_software
 * EULA https://en.wikipedia.org/wiki/End-user_license_agreement
 * 
 * Lisensi Perangkat Lunas https://id.wikipedia.org/wiki/Lisensi_perangkat_lunak
 * EULA https://id.wikipedia.org/wiki/EULA
 *
 */
package app;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author LILO | lilo.suryo@gmail.com Created : 15 October 2020 - 09:25:41
 * Version : 1.0 Main Program
 */
public class Main extends Application {

    public static Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 800.0;
    private final double MINIMUM_WINDOW_HEIGHT = 600.0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("AES GENERATOR TOOL");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            stage.getIcons().add(new Image(getClass().getResource("logo_ico.png").toString()));
            Controller controller = (Controller) replaceSceneContent("view.fxml");
            controller.setApp(this);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page, 800, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
