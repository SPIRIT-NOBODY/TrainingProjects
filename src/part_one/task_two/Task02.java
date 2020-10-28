package part_one.task_two;

import java.io.*;
import java.util.Scanner;

/**
 * Есть массив, в котором хранятся слова и правила их переноса (например,
 * пары типа «компьютер» — «ком-пью-тер») Вводится слово (например, компьют-ер),
 * причем перенос только один. Нужно ответить, есть ли такое слово в «базе» и
 * можно ли так осуществить перенос. Задача подразумевает использование
 * некоторой карты.
 */

public class Task02 {

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in,"Cp866");	

		String consoleEncoding = System.getProperty("consoleEncoding");
		if (consoleEncoding != null) {
			try {
				System.setOut(new PrintStream(System.out, true, consoleEncoding));
			} catch (java.io.UnsupportedEncodingException ex) {
				System.err.println("Unsupported encoding set for console: "+consoleEncoding);
			}
		}

		System.out.println("Input word:");
		
		if(scan.hasNextLine()) {
			WordsHyphenation wordFinder = new WordsHyphenation();
			wordFinder.findWordHyphenation(scan.nextLine());
		}		
	}	

}