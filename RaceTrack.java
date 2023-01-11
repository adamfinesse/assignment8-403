package ProducerConsumer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class RaceTrack extends Application {
    Image racecar = new Image("file:sportive-car.png");

    private void updateCar(ImageView racecar){
        racecar.setX(racecar.getX() + ((int) (Math.random() * 10)));
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Richmond Raceway");
        primaryStage.setWidth(500);
        primaryStage.setHeight(200);

        Rectangle car1 = new Rectangle(375,15);
        car1.setX(60);
        car1.setY(55);
        car1.setFill(Color.GREY);

        ImageView racecarview1 = new ImageView(racecar);
        racecarview1.setX(27);
        racecarview1.setY(46);
        racecarview1.setId("One");

        Rectangle car2 = new Rectangle(375,15);
        car2.setX(60);
        car2.setY(90);
        car2.setFill(Color.GREY);

        ImageView racecarview2 = new ImageView(racecar);
        racecarview2.setX(27);
        racecarview2.setY(81);
        racecarview2.setId("Two");

        Rectangle car3 = new Rectangle(375,15);
        car3.setX(60);
        car3.setY(125);
        car3.setFill(Color.GREY);

        ImageView racecarview3 = new ImageView(racecar);
        racecarview3.setX(27);
        racecarview3.setY(116);
        racecarview3.setId("Three");

        ImageView[] carsArr = new ImageView[3];
        carsArr[0] = racecarview1;
        carsArr[1] = racecarview2;
        carsArr[2] = racecarview3;

        Runnable[] runnablesArr = new Runnable[3];
        final long[] mainThreadId = {-1};

        Button start = new Button("Start");
        start.setOnAction(actionEvent -> {
            //pass the car number with the runnable so we know which one we wins?
            if(mainThreadId[0] != -1) return;
            final String[] k = {"10"};
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<carsArr.length;i++) {
                        int finalI = i;
                        runnablesArr[i] = new Runnable() {

                            @Override
                            public void run() {
                                    if(carsArr[finalI].getX() < 400 && k[0].equals("10")){
                                        updateCar(carsArr[finalI]);
                                    }
                                    else if (k[0].equals("10")) {
                                        k[0] = carsArr[finalI].getId();
                                        mainThreadId[0] = -1;
                                        Runnable winnerRunnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                Alert winner = new Alert(Alert.AlertType.INFORMATION, "Car " + k[0] + " Wins!");
                                                winner.show();
                                            }
                                        };
                                        Platform.runLater(winnerRunnable);
                                    }
                            }
                        };
                    }
                    loop: while(true){
                        if(!k[0].equals("10")){
                            break;
                        }

                        for(int i =0;i<runnablesArr.length;i++){
                            Platform.runLater(runnablesArr[i]);

                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                mainThreadId[0] =-1;
                                break loop;
                            }
                        }
                    }

                }
            });
            thread.start();
            mainThreadId[0] = thread.getId();
        });

        Button pause = new Button("Pause");
        pause.setOnAction(actionEvent -> {
            for (Thread t : Thread.getAllStackTraces().keySet()) if (t.getId()==mainThreadId[0]){
                t.interrupt();
            }

        });

        Button reset = new Button("Reset");
        reset.setOnAction(actionEvent -> {
            for (Thread t : Thread.getAllStackTraces().keySet()) if (t.getId()==mainThreadId[0]){
                t.interrupt();
            }
            racecarview1.setX(27);
            racecarview2.setX(27);
            racecarview3.setX(27);
        });

        HBox buttonBox = new HBox(50,start,pause,reset);
        buttonBox.setPadding(new Insets(10,0,0,95));
        buttonBox.setAlignment(Pos.CENTER);

        Pane pane = new Pane();
        pane.getChildren().add(buttonBox);
        pane.getChildren().addAll(racecarview1,car1,car2,racecarview2,car3,racecarview3);
        pane.setPadding(new Insets(5,0,0,50));

        Group g = new Group();
        g.getChildren().add(pane);
        Scene scene = new Scene(g,500,200);
        racecarview1.toFront();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setIconified(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
