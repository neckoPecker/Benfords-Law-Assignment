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

    /**
	 * Get first digit number quantities from 1 to 9.
	 * <p>
	 * In accordance to Benford's Law, it states that the first digits
	 * that should appear the most is 1 and 2. If graphically layed out,
	 * there should be a natural cuve from 1 to 9. For the purpose of
	 * the assignment, if the fequency percentage of 1 appears between
	 * 29% and 32%, fraud most likely had not happen.	
	 * <p>	
	 * @param numbers	An array of integers to check for first-digit	
	 * @return		The frequency of each first digit number
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

    /**
     * Program starst here.
     */
    public static void main(String[] args) {
	int[] sales = getSalesData("./sales.csv");		// Get sales data
	int[] firstNums = getFirstNums(sales);			// Get first nums using sales data

	for (int num : firstNums) {				// TEST: Print frequencies
	    System.out.println(num);
	}
    }
}
