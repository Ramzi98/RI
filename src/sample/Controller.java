package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML TextField requete ;

    String nom_fichier="/home/ramzi/IdeaProjects/projet_RI/requete.txt";

    public void write(String requete) throws IOException {
        FileWriter fichier = new FileWriter(nom_fichier);
        fichier.write(requete);
        fichier.close();
    }

    public void tokeni() throws IOException {

        Tokeniser tokeniser=new Tokeniser();
        tokeniser.setFichier_in(nom_fichier);
        tokeniser.setFichier_tokeniser("/home/ramzi/IdeaProjects/projet_RI/requete_tokeniser.txt");
        tokeniser.tok();

    }



    public void Rechercher(ActionEvent actionEvent) throws IOException {
        System.out.println("La requete : "+requete.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Resultat_affichage.fxml"));
        Parent home_page_parent =(Parent) loader.load();
        Resultat_Controller cn =loader.getController();
        cn.Afficher(requete.getText());

        write(requete.getText());
        tokeni();


        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource() ).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
