package com.zenika.fx.zwitter;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.zenika.fx.zwitter.model.Zweet;
import com.zenika.fx.zwitter.model.ZwitterUser;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class MainController implements Initializable {

    @FXML
    private ListView<Zweet> timeline;

    @FXML
    private TextArea zweetArea;

    private Zwitter zwitter;

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        // ceci est un exemple d'utilisation du ZwitterSDK
        final ObservableList<Zweet> list = FXCollections.observableList(new LinkedList<Zweet>());
        timeline.setItems(list);

        timeline.setCellFactory(new ZweetCell());

        zwitter = ZwitterBuilder.create().withObservableList(list).build();
        zwitter.start();
    }

    @FXML
    public void start() {
        zwitter.start();
    }

    @FXML
    public void stop() {
        zwitter.stop();
    }

    @FXML
    public void publish() {
        ZwitterUser zwitterUser = new ZwitterUser("", "", "");
        Zweet zweet = new Zweet(zwitterUser, zweetArea.getText());
        zwitter.publish(zweet);
    }

    public static Transition createTransition(Node pane) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1d), pane);
        fade.setFromValue(0.5d);
        fade.setToValue(1d);

        ScaleTransition scale1 = new ScaleTransition(Duration.millis(500d), pane);
        scale1.setFromX(0d);
        scale1.setToX(0.5);
        scale1.setFromY(0d);
        scale1.setToY(0.5);

        ScaleTransition scale2 = new ScaleTransition(Duration.millis(500d), pane);
        scale2.setFromX(0.5);
        scale2.setFromY(0.5);
        scale2.setToX(1d);
        scale2.setToY(1d);
        scale2.setCycleCount(3);
        scale2.setAutoReverse(true);

        return new ParallelTransition(fade, new SequentialTransition(scale1, scale2));
    }

}
