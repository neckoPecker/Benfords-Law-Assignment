/*
Due date: 2021, 05, 12
Date: 2021, 05, 10
Names: Tyson Z and Vaughn C
Teacher: Mr. Ho
Description: This code will be able receive files, read them, and be able to find the first digit distribution 
			 according to Benford's Law. After calculating the percentage, the program will display a graph and export a table. 
*/

//Importing JavaFX applications for bar graph
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Imports for I/O
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

//Public application class for JavaFX applications to extend off
public class salesAnalysisGraph extends Application {
	/*
	Method Name: main()
	Description: The main method only launches the JavaFX application
	@author: Tyson Z
	*/
    public static void main(String[] args) {
		//Launches the start method
		Application.launch(args);
    }
	//Override is able to change the javafx packages and input data to display the graph
    @Override
	/*
	Method Name: start()
	Description: Is able to run when the application is launched from main. It takes the role of 'main'
				 running all the code in the application. The start method will call methods and has the code to display graph
	@author: Tyson Z
	@parameter: stage (A function from Javafx package to make stages for display)
	*/
    public void start(Stage stage) {

		//Calls the method getSalesData() to read the file and store in int[] sales
		int[] sales = getSalesData(getFilePath());
		//Calls the method getFirstNums() to get the values of each digit and store in int[] firstNums
		int[] firstNums = getFirstNums(sales);
		//Calls the method numberToPercentages to calculate the percentages and store in float[] percentages
		float[] percentages = numberToPercentages(firstNums);

		//CSV file
		String[][] firstNumsOutput = new String[10][2];				// Represents 10 rows and 2 columns
		firstNumsOutput[0] = new String[] {"Benford Number", "Percentage"};	//

		// Add all the values to the 2d list and print it to "results.csv"
		for (int i = 1; i < 10; i++) {
		    firstNumsOutput[i] = new String[] {String.valueOf(i) ,String.valueOf(percentages[i-1]) + "%"};
		}
		outputFile("results.csv", firstNumsOutput);

		/*
		Bar Graph

		This portion of the start method will be the part where the bar graph is made and then displayed. The values will be 
		passed from the methods called above to be used as the values for the bar graph
		*/
		//Creates a new x/Category Axis of the bar graph
		CategoryAxis x = new CategoryAxis();
		//Names the label of x Axis
		x.setLabel("First Digit");
		//Creates a new y/Number Axis of the bar graph
		NumberAxis y = new NumberAxis();
		//Names the label of y Axis
		y.setLabel("Percentage (%)");
		//Creates a new bar graph with axes
		BarChart analysisLawGraph = new BarChart(x, y);
		//Sets the title of the bar graph to be displayed
		analysisLawGraph.setTitle("Sales Distribution Graphical Dislpay");
		//Creating a new series to store the data
		XYChart.Series benfordGraph = new XYChart.Series();
		//Names the series for the legend
		benfordGraph.setName("Percentage of the First Digit");
		//For loop that is able to call values from percentages and place them into series
		for (int k = 0; k < percentages.length; k++){
			//Converts the for loop value from int to string for Benford first digit
			String benfordDigit = String.valueOf(k+1);
			//Calls the percentages for digits and adds them to series 
			benfordGraph.getData().add(new XYChart.Data(benfordDigit,percentages[k]));
		}
		//Adds the data of series to the bar graph 
		analysisLawGraph.getData().add(benfordGraph);
		//Creates a new Vertical box to place the bar graph
		VBox vbox = new VBox(analysisLawGraph);
		//Creates a new scene with the bar graph in it
		Scene sc = new Scene(vbox, 800, 900);
		//Adds the new scene to the one from JavaFx packages
		stage.setScene(sc);
		//Sets the original scene to dimensions
		stage.setHeight(400);
		stage.setWidth(600);
		//Displays the stage with the bar graph in it
		stage.show();
    }

	/* Method Name: getSalesData()
     * Description: Gets an array of all the sales data found in a CSV file. Given a comma-seperated file, this method first parses the file into a string
     * 				of comma-seperated values. Then, it splits the values into an array and returns an array containing only the sales data.
     * @author: Vaughn C
	 * @parameter: csvFileName (Gets the file path)
	 * @return: sales (int array with each sales in array)
     */
    public static int[] getSalesData(String csvFileName) {

		String data = "";					// csvFileName will be parsed to this string
		
		/*
		* This section parses the file into a string by reading each line in the
		* file and putting it into the string. Since each row doesn't have a
		* comma seperating a row's value and the next row after that, it has to 
		* manually add it to the string.	
		*/
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(csvFileName));
			
			String fileLine;					// Represents the current line being parsed	    
			int fileLineNum = 1;				// Represents the current line number

			while ((fileLine = fileReader.readLine()) != null) {

			if (fileLineNum != 1) {				// Don't include the header at row 
				data = data + fileLine + ",";		// Add comma delimter
			}
			
			fileLineNum++;					// Represents the next line number
			}

			data = data.substring(0, data.length() - 1);	// Remove the last comma from data string

			fileReader.close();
			
		} catch (Exception e) {					// Report error in case anything goes wrong,
			System.out.println("Something went wrong:");	// such as the file being unavailable.
			System.out.println(e.getMessage());
		}

		/*
		* This section splits the values seperated by commas within the data 
		* string into an array of String values. An integer array will be 
		* made at half the size of the string array to only get the sales
		* data.
		*/
		String[] salesArray = data.split(",");			// Split the data string into an array

		int[] sales = new int[salesArray.length / 2];		// Array to hold all the sales data

		int salesElement = 0;					// Holds which index to hold the sales data in
		
		for (int i = 1; i < salesArray.length; i +=2) {			// i starts at 1 because all sales data
			sales[salesElement] = Integer.parseInt(salesArray[i]);	// is stored in the second column
			salesElement++;
		}
		
		return sales;
    }

    /* Method Name: getFirstNums() 
     * Description: Get first digit number quantities from 1 to 9.In accordance to Benford's Law, it states that the first digits
     * 				that should appear the most is 1 and 2. If graphically layed out, there should be a natural cuve from 1 to 9. For the purpose of
     * 				the assignment, if the fequency percentage of 1 appears between 29% and 32%, fraud most likely had not happen.	
     * @author: Vaughn C
     * @parameter: numbers (An array of integers to check for first-digit)	
     * @return: firstNums (An array for the frequency of each first digit number)
     */
    public static int[] getFirstNums(int[] numbers) {

		/*
		* The strategy for getting the first numbers is to iterate through all
		* the numbers 9 times from 1 to 9. For each number, it checks the first
		* digit and, if it is equal to the number being checked, it is added to 
		* the array of the same index.	
		*/
		int[] firstNums = new int[9];				// Array consisting frequency of numbers
									// from 1 to 9.

		for (int i = 0; i < 9; i++) {
			for (int number : numbers) {
			
			/*
			* Integer -> String -> Character -> Integer
			* 	Each number gets converted to a string. Using the function
			* 	'charAt(0)', I can get the first element of the string, but
			* 	as a character. Then I use a character method to convert
			* 	a characater value back into an integer.
			* 
			* For example, say that 45638 was the number to check:
			* 	(int) 45638 -> (string) "45638" -> (char) '4' -> (int) 4
			*/
				int firstDigit = Character.getNumericValue(Integer.toString(number).charAt(0));

				if (firstDigit == i + 1) {			// Add 1, otherwise it would check
					firstNums[i]++;				// numbers 0-8 instead.
				}
			}
		}
		
		return firstNums;
    }

    /* Method Name: numberToPercentages()
     * Description: Return the percentage of numbers of an array to their complete sum
	 * @author: Vaughn C 
     * @parameter: numbers (The numbers to get the percentage from)
     * @return: percentages (The percentage of all numbers to the sum of them in order)	
     */
    public static float[] numberToPercentages(int[] numbers) {

		int numTotal = 0;
		
		for (int number : numbers) {				// Add sum of all numbers in total
			numTotal += number;
		}

		float[] percentages = new float[numbers.length];	// Size of array of percentage is the
									// same as size of array of numbers
		
		for (int i = 0; i < numbers.length; i++) {
			percentages[i] = (float) numbers[i] / numTotal * 100;	// Divide number to total for decimal
											// and multiply by 100 for percentage
		}

		return percentages;
    }

    /* Method Name: getFilePath()
     * Description: Get and verify a csv file path form user This method asks the user for an input for a file or location to a file. 
	 * 				If the user inputted a file that doesn't exist, or just a directory, then it keeps asking until what they have entered is valid.	
     * @author: Vaughn C
     * @return: A valid string of a file path.
     */
    public static String getFilePath() {

	Scanner scanner = new Scanner(System.in);
	
	boolean fileIsValid = false;
	String filePath = "";

	while (!fileIsValid) {					// Make sure filepath is valid
	    
	    System.out.print("Enter the path/location of the sales file: ");
	    filePath = scanner.nextLine();
	    File tmpFile = new File(filePath);

	    if (tmpFile.exists() && !tmpFile.isDirectory()) {	// Exit loop if file exists and not a directory
		fileIsValid = true;
	    } else {						// Otherwise, redo the loop
		System.out.println("File either does not exist or input was a directory.");
		System.out.println("Please try again.\n");
	    }
	}

	return filePath;
    }

    /* Method Name: outputFile()
     * Description: Write a multidimensional array into a file The 2D array is used to represent a table of values. 
	 * 				This should be created before calling this function.	
     * @author: Vaughn C 	
     * @param: fileLocation (Where the file should be written/created to)
     * @param: outputs (A 2D array, representing row and column values.)	
     */	
    public static void outputFile(String fileLocation, String[][] outputs) {
	
	String contentsToWrite = "";
	
	/*
	 * Before writing to the file, all the outputs are written to a string first
	 * so that the file writer only has to write once to the file. It takes all
	 * the inputs and then adds them to this string, sepearting each value with
	 * a comma.
	 */	
	for (String[] row: outputs) {
	    for (String rowColumn : row) {
		contentsToWrite += (rowColumn + ", ");
	    }
	    contentsToWrite += "\n";
	}
	
	/*
	 * Need to remove the comma at the very end of the string by taking a substring
	 * of the original contents from index 0 all the way to the last comma. Getting
	 * string length returns how possible values can be stored, but not the max
	 * storage. Consequently, the length will always be 1 more than the max. Thus,
	 * removing 3 will remove 1 for this feature, and 1 for the comma.
	 * Also remove 1 for the extra space caused by the for loop.	
	 */
	contentsToWrite = contentsToWrite.substring(0, contentsToWrite.length() - 3);

	System.out.println(contentsToWrite);			// Print contents to console

	try {
	    FileWriter writer = new FileWriter(fileLocation);	// Write the contents to the file
	    writer.write(contentsToWrite);
	    writer.close();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
