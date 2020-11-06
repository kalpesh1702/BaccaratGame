import javafx.scene.control.Alert;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static byte[] convertObjectToByteArray(Map<String,Object> data) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(data);
        oos.flush();
        return bos.toByteArray();
    }

    public static Map<String,Object> convertByteArrayToObject(byte[] data) throws Exception {
        Map<String,Object> message = new HashMap<>();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        message = (Map<String, Object>) ois.readObject();
        return message;
    }

    public static void showErrorAlertBox(){
        alertBox("INTERNAL_SERVER_ERROR", "Something went wrong, please try again later.");
    }

    public static void alertBox(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setTitle(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
