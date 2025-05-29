
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
