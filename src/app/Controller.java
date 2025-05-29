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

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author LILO | lilo.suryo@gmail.com Created : 15 October 2020 - 09:25:41
 * Version : 1.0 Main Program
 */
public class Controller extends AnchorPane implements Initializable {

    @FXML
    private TextField text, secretkey, result;
    @FXML
    public Label messageLabel;
    @FXML
    private ComboBox type;

    private Main application;

    final String[] metaCharacters = {"\\", "=", "^", "$", "{", "}", "[", "]", "(", ")", ".", "*", "+", "?", "|", "<", ">", "-", "&", "%"};

    AES cipher = new AES();
    AESCBC cipherGCM = new AESCBC();

    public void setApp(Main application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        result.setEditable(false);
        ObservableList<String> dataCombo = FXCollections.observableArrayList(
                "ECB",
                "CBC");
        type.setItems(dataCombo);
        type.getSelectionModel().select(0);
    }

    @FXML
    private void Copy() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(result.getText());
        clipboard.setContent(content);
        if (result.getText().isEmpty()) {
            messageLabel.setText("RESULT MASIH KOSONG");
        } else {
            messageLabel.setText("COPY RESULT SUKSES!");
        }
    }

    @FXML
    private void Clear() {
        result.clear();
        text.clear();
        secretkey.clear();
        messageLabel.setText("CLEAR SUKSES");
    }

    @FXML
    private void Encrypt() {
//        long start1 = System.currentTimeMillis();
        if (type.getValue().equals("ECB")) {
            if (!text.getText().trim().isEmpty() || !secretkey.getText().trim().isEmpty()) {
                result.setText(cipher.encrypt(text.getText().trim(), secretkey.getText().trim()));
                for (int i = 0; i < metaCharacters.length; i++) {
                    if (result.getText().contains(metaCharacters[i])) {
                        result.setText(result.getText().replace(metaCharacters[i], "\\" + metaCharacters[i]));
                    }
                }
                messageLabel.setText("ENCRYPT SUKSES");
            }
        }
        if (type.getValue().equals("CBC")) {
            if (!text.getText().trim().isEmpty() || !secretkey.getText().trim().isEmpty()) {
                result.setText(cipherGCM.encrypt(text.getText().trim(), secretkey.getText().trim()));
                for (int i = 0; i < metaCharacters.length; i++) {
                    if (result.getText().contains(metaCharacters[i])) {
                        result.setText(result.getText().replace(metaCharacters[i], "\\" + metaCharacters[i]));
                    }
                }
                messageLabel.setText("ENCRYPT SUKSES");
            }
        }
//        long end1 = System.currentTimeMillis();
//        System.out.println("ENCRYPT " + type.getValue() + " Elapsed Time in seconds: " + (end1 - start1) / 1000F);
        System.out.println(result.getText());
    }

    @FXML
    private void Decrypt() {
//        long start1 = System.currentTimeMillis();
        if (type.getValue().equals("ECB")) {
            if (!text.getText().trim().isEmpty() || !secretkey.getText().trim().isEmpty()) {
                String teks = text.getText();
//                for (int i = 0; i < metaCharacters.length; i++) {
//                    if (teks.contains(metaCharacters[i])) {
//                        teks = teks.replace(metaCharacters[i], "");
//                    }
//                }
                teks = teks.replace("\\", "");
                result.setText(cipher.decrypt(teks.trim(), secretkey.getText().trim()));
                messageLabel.setText("DECRYPT SUKSES");
                if (result.getText().isEmpty()) {
                    messageLabel.setText("DECRYPT GAGAL");
                }
            }
        }
        if (type.getValue().equals("CBC")) {
            if (!text.getText().trim().isEmpty() || !secretkey.getText().trim().isEmpty()) {
                String teks = text.getText();
//                for (int i = 0; i < metaCharacters.length; i++) {
//                    if (teks.contains(metaCharacters[i])) {
//                        teks = teks.replace(metaCharacters[i], "");
//                    }
//                }
                //                result.setText(cipherGCM.decryptGCM(teks.trim(), secretkey.getText().trim()));
                teks = teks.replace("\\", "");
                result.setText(cipherGCM.decrypt(teks.trim().trim(), secretkey.getText().trim()));
                messageLabel.setText("DECRYPT SUKSES");
                if (result.getText().isEmpty()) {
                    messageLabel.setText("DECRYPT GAGAL");
                }
            }
        }
//        long end1 = System.currentTimeMillis();
//        System.out.println("DECRYPT " + type.getValue() + " Elapsed Time in seconds: " + (end1 - start1) / 1000F);
        System.out.println(result.getText());
    }

}
