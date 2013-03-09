package converter;
import java.util.*;
import java.io.*;

public class BankOCR{
	public static void main(String args[]){
		
		ArrayList<ArrayList<String>> inputList = readInputFile();
		ArrayList<ArrayList<Integer>> finalList = new ArrayList<ArrayList<Integer>>();
		DigitalNumber number = new DigitalNumber();
		
		for(ArrayList<String> line:inputList){
			finalList.add(number.getTranslation(line));
		}
		
		for(ArrayList<Integer> out:finalList){
			for(String printOut:generatePrintOCR(out)){
				System.out.print(printOut);
			}
			System.out.println();
		}
	}
	
	/**
	* Reads file of underscores and pipes and creates a list of top, middle, and bottom portions for each entry
	*
	*@return ArrayList<ArrayList<String>> A list of Strings containing top, middle, and bottom portions of a digital number
	*/
	public static ArrayList<ArrayList<String>> readInputFile(){
		Scanner scan = null;
		ArrayList<ArrayList<String>> wholeFile = new ArrayList<ArrayList<String>>();
		
		try {
			scan = new Scanner(new FileReader("input.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("File could not be found.");
		}
		
		String top = "";
		String middle = "";
		String bottom = "";
		
		while(scan.hasNextLine()) {
		    top = scan.nextLine();
		    middle = scan.nextLine();
		    bottom = scan.nextLine();
		    wholeFile.add(new ArrayList<String>(Arrays.asList(top, middle, bottom)));
		    if(scan.hasNextLine()){
		    	 scan.nextLine();
		    }
		    else{
		    	break;
		    }
		}
	    scan.close();
		
		return wholeFile;
	}
	
	/**
	 * Appends either "ERR" if number does not pass checksum, OR "ILL" if number is not valid and returns an ArrayList<String for each number in read input. Replaces all unreadable
	 * numbers with a "?" character
	 * 
	 * @param inList The list to be printed out
	 * @return ArrayList<String> The generated list ready for printing
	 */
	public static ArrayList<String> generatePrintOCR(ArrayList<Integer> inList){
		ArrayList<String> convertedList = new ArrayList<String>();
		DigitalNumber number = new DigitalNumber();
		
		for(Integer num:inList){
			if(num == -1){
				convertedList.add("?");
			}
			else{
				convertedList.add(num.toString());
			}
		}
		if(number.verifyAccountNumberReading(inList) == -1){
			convertedList.add(" ILL");
			return convertedList;
		}
		if(number.verifyAccountNumberChecksum(inList) == -1){
			convertedList.add(" ERR");
			return convertedList;
		}
		
		return convertedList;
	}
}