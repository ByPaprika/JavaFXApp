package sample.model;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class MessageError {
    private TranslateTransition tt;

    public MessageError(Node node) {
        tt = new TranslateTransition(Duration.millis(200), node);
        tt.setByX(10f);
        tt.setByY(0f);
        tt.setCycleCount(4);
        tt.setAutoReverse(true);
    }

    public void playMessagerError(){
        tt.playFromStart();
    }

}
