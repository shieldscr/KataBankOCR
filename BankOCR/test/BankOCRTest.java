package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import converter.BankOCR;
import converter.DigitalNumber;

public class BankOCRTest {
	
	private BankOCR bankOCR = new BankOCR();
	
	private static Map<Integer,String>	testInput;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testInput = new HashMap<Integer, String>();
		
		testInput.put(0,         " _  _  _  _  _  _  _  _  _ "+
								 "| || || || || || || || || |"+
								 "|_||_||_||_||_||_||_||_||_|"+
								 "                           ");
	}
	
	/**
	 * 
	 */
	@Test
	public void readInputFileTestNotNull() {
		assertNotNull("File input read fail", BankOCR.readInputFile());
	}
	
	/**
	 * Runs through solid number accounts 0 through 9, and tests if the translated int arrays match output.
	 */
	@Test
	public void getTranslationTest() {
		ArrayList<ArrayList<String>> inputList = BankOCR.readInputFile();
		DigitalNumber number = new DigitalNumber();
		
		for(int i=0;i<inputList.size();i++){
			ArrayList<Integer> returnArray = number.getTranslation(inputList.get(i));
			assertEquals("Returned characters do not match test case", Arrays.asList(i, i, i, i, i, i, i, i, i), returnArray);
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void verifyAccountNumberTestSucceed() {
		DigitalNumber number = new DigitalNumber();
		ArrayList<Integer> testList = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
		
		assertEquals("Account number failed checksum test", 0, number.verifyAccountNumberChecksum(testList));
	}
	
	/**
	 * 
	 */
	@Test
	public void verifyAccountNumberTestFail() {
		DigitalNumber number = new DigitalNumber();
		ArrayList<Integer> testList = new ArrayList<Integer>(Arrays.asList(0, 1, 0, 1, 0, 1, 0, 1, 0));
		
		assertEquals("Account number failed checksum test", -1, number.verifyAccountNumberChecksum(testList));
	}

}
