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
        BarChart analysisLawGraph = new BarChart(x, y);
        analysisLawGraph.setTitle("Benford Law Analysis");
        //add values
        XYChart.Series benfordGraph = new XYChart.Series();
        benfordGraph.setName("Percentage of Benford Digit");
        benfordGraph.getData().add(new XYChart.Data("1", 33));
        benfordGraph.getData().add(new XYChart.Data("2"  , 25));
        benfordGraph.getData().add(new XYChart.Data("3"  , 10));
        benfordGraph.getData().add(new XYChart.Data("4", 33));
        benfordGraph.getData().add(new XYChart.Data("5"  , 25));
        benfordGraph.getData().add(new XYChart.Data("6"  , 9));
        benfordGraph.getData().add(new XYChart.Data("7", 33));
        benfordGraph.getData().add(new XYChart.Data("8"  , 25));
        benfordGraph.getData().add(new XYChart.Data("9"  , 10));
        analysisLawGraph.getData().add(benfordGraph);
        //vertical box
        VBox vbox = new VBox(analysisLawGraph);
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