package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Resultat_Controller implements Initializable {
    @FXML private Label label;
    public void Afficher(String text)
    {
        label.setText("La Requete : "+text);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
