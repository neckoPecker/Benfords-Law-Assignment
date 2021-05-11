import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class salesAnalysisGraph extends Application {
    @Override
    public void start(Stage s) {
        s.setTitle("Benford Law Analysis");
        //x axis
        CategoryAxis x = new CategoryAxis();
        x.setLabel("Benford Digit");
        //y axis
        NumberAxis y = new NumberAxis();
        y.setLabel("Percentage (%)");
        //bar chart creation
        BarChart bc = new BarChart(x, y);
        bc.setTitle("Benford Law Analysis");
        //add values
        XYChart.Series ds = new XYChart.Series();
        ds.setName("Percentage of Benford Digit");
        ds.getData().add(new XYChart.Data("1", 33));
        ds.getData().add(new XYChart.Data("2"  , 25));
        ds.getData().add(new XYChart.Data("3"  , 10));
        ds.getData().add(new XYChart.Data("4", 33));
        ds.getData().add(new XYChart.Data("5"  , 25));
        ds.getData().add(new XYChart.Data("6"  , 9));
        ds.getData().add(new XYChart.Data("7", 33));
        ds.getData().add(new XYChart.Data("8"  , 25));
        ds.getData().add(new XYChart.Data("9"  , 10));
        bc.getData().add(ds);
        //vertical box
        VBox vbox = new VBox(bc);
        Scene sc = new Scene(vbox, 800, 700);
        s.setScene(sc);
        s.setHeight(500);
        s.setWidth(600);
        s.show();
        }
    public static void main(String[] args) {
        Application.launch(args);
    }
}