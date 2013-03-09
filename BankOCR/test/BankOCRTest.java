package test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import converter.BankOCR;
import converter.DigitalNumber;

public class BankOCRTest {
	private BankOCR bankOCR = new BankOCR();
	private static final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
	
	private static Map<Integer,String>	testInput;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testInput = new HashMap<Integer, String>();
		
		testInput.put(0,         " _  _  _  _  _  _  _  _  _ "+
								 "| || || || || || || || || |"+
								 "|_||_||_||_||_||_||_||_||_|"+
								 "                           ");
	}
	
	@After
	public void closeStream(){
		System.setOut(null);
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
	 * Tests for correct account numbers, and that they are judged as so
	 */
	@Test
	public void verifyAccountNumberTestSucceed() {
		DigitalNumber number = new DigitalNumber();
		ArrayList<Integer> testList = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
		
		assertEquals("Account number failed checksum test", 0, number.verifyAccountNumberChecksum(testList));
	}
	
	/**
	 * Tests for incorrect account numbers, and that they are judged as so
	 */
	@Test
	public void verifyAccountNumberTestFail() {
		DigitalNumber number = new DigitalNumber();
		ArrayList<Integer> testList = new ArrayList<Integer>(Arrays.asList(0, 1, 0, 1, 0, 1, 0, 1, 0));
		
		assertEquals("Account number failed checksum test", -1, number.verifyAccountNumberChecksum(testList));
	}
	
	/**
	 * Prints out and tests for correct output of a valid number
	 * @throws IOException 
	 */
	@Test
	public void testOutputSucceed() throws IOException{
		System.setOut(new PrintStream(testOutput));
		ArrayList<Integer> testList = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
		testOutput.flush();
		testOutput.reset();
		for(String out:BankOCR.generatePrintOCR(testList)){
			System.out.print(out);
		}
		
		assertEquals("000000000", testOutput.toString());
	}
	
	/**
	 * Takes a number which would fail the OCR checksum, and shows that print output will append proper error code to end before printing.
	 * @throws IOException 
	 */
	@Test
	public void testOutputErrChecksum() throws IOException{
		System.setOut(new PrintStream(testOutput));
		ArrayList<Integer> testList = new ArrayList<Integer>(Arrays.asList(9, 9, 9, 9, 9, 9, 9, 9, 9));
		testOutput.flush();
		testOutput.reset();
		for(String out:BankOCR.generatePrintOCR(testList)){
			System.out.print(out);
		}
		assertEquals("999999999 ERR", testOutput.toString());
	}
	
	/**
	 * Takes a number which contains an invalid number, and shows that print output will append proper error code to end before printing.
	 * @throws IOException 
	 */
	@Test
	public void testOutputErrInvalidNum() throws IOException{
		System.setOut(new PrintStream(testOutput));
		ArrayList<Integer> testList = new ArrayList<Integer>(Arrays.asList(9, -1, 9, 9, -1, 9, 9, 9, 9));
		testOutput.flush();
		testOutput.reset();
		for(String out:BankOCR.generatePrintOCR(testList)){
			System.out.print(out);
		}

		assertEquals("9?99?9999 ILL", testOutput.toString());
		testOutput.flush();
		testOutput.reset();
	}

}
