package converter;
import java.util.*;

public class DigitalNumber{
	private static int Rows = 3;
	private static int TotalNumbers = 9;
	private ArrayList<String> MatchList = null;
	
	public DigitalNumber(){
		MatchList = new ArrayList<String>();
		MatchList.add(" _ \n| |\n|_|\n"); //0
		MatchList.add("   \n  |\n  |\n"); //1
		MatchList.add(" _ \n _|\n|_ \n"); //2
		MatchList.add(" _ \n _|\n _|\n"); //3
		MatchList.add("   \n|_|\n  |\n"); //4
		MatchList.add(" _ \n|_ \n _|\n"); //5
		MatchList.add(" _ \n|_ \n|_|\n"); //6
		MatchList.add(" _ \n  |\n  |\n"); //7
		MatchList.add(" _ \n|_|\n|_|\n"); //8
		MatchList.add(" _ \n|_|\n _|\n"); //9
	}
	
	/**
	 * Takes a group of digital numbers, breaks them up one by one, and returns a list of integers representing each number
	 * 
	 * @param inputList The string list representing digital numbers that will be translated
	 * @return
	 */
	public ArrayList<Integer> getTranslation(ArrayList<String> inputList){
		ArrayList<Integer> translateList = new ArrayList<Integer>();
		
		for(int i=0;i<TotalNumbers;i++){
			if(translateNumbers(inputList, i) != -1){
				translateList.add(translateNumbers(inputList, -1));
			}
			else{
				translateList.add(translateNumbers(inputList, i));
			}
		}
		
		return translateList;
	}
	
	/**
	*Takes one digital number and translates it to an integer
	*
	*@param inList The String list that represents a digital number
	*@param startPoint An integer that specifies where the translation should start from provided input
	*
	*@return int The integer that represents the input boolean list or -1 if no match was found
	**/
	private Integer translateNumbers(List<String> inList, int startPoint){
		String match = "";
		
		for(int i=0; i<inList.size(); i++){
			for(int j=0; j<Rows; j++){
				match += (Character.toString(inList.get(i).charAt(j + (startPoint*3))));
			}
			match += "\n";
		}
		
		
		if(MatchList.indexOf(match) == -1){
			return -1;
		}
		else{
			return MatchList.indexOf(match);
		} 
	}
	
	/**
	 * Verifies that account number is valid by use of checksum. Returns 0 if valid and -1 otherwise
	 * 
	 * @param inputList The list of account number to be verified
	 * @return 0 if account number is valid, -1 otherwise
	 */
	public int verifyAccountNumberChecksum(ArrayList<Integer> inputList){
		Integer sum = inputList.get(inputList.size()-1) + inputList.get(inputList.size()-2);
		for(int i=inputList.size()-2;i>0;i--){
			sum = sum * (inputList.get(i-1) + inputList.get(i));
		}
		if(sum%11 == 0){
			return 0;
		}
		return -1;
	}
	
	public int verifyAccountNumberReading(ArrayList<Integer> inputList){
		for(Integer num: inputList){
			if(num == -1){
				return -1;
			}
		}
		return 0;
	}

}