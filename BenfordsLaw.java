// Imports
import java.io.FileReader;
import java.io.BufferedReader;

class BenfordsLaw {

    /**
     * Gets an array of all the sales data found in a CSV file
     * <p>
     * Given a comma-seperated file, this method first parses the file into a string
     * of comma-seperated values. Then, it splits the values into an array and
     * returns an array containing only the sales data.
     * <p>
     * @param csvFileName	The path and directory of the csv file.	
     * @return			Alls sales in the CSV file as an array of integers
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
}
