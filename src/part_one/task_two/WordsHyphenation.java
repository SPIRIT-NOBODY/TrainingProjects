package part_one.task_two;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

class WordsHyphenation {
	private final HashMap<String, ArrayList<String>> wordsMaps = new HashMap<String, ArrayList<String>>();
	// File with word wrap rules, example:
	// компьютер=ком-пью-тер,компью-тер,ком-пьютер
	private String filePropertiesName = "words.properties";

	public WordsHyphenation() {
		try {
			getProperties();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method get word with wraps rules from file properties
	 */
	private void getProperties() throws IOException {
		File properties = new File(filePropertiesName);
		if (properties.exists() && properties.isFile()) {
			InputStreamReader iReader = new InputStreamReader(new FileInputStream (properties), "UTF-8");			
			BufferedReader fileReader = new BufferedReader(iReader);
			String fileLine = null;
			while ((fileLine = fileReader.readLine()) != null) {
				String[] arLine = fileLine.replace(" ", "").split("=");
				if (arLine.length == 2) {
					String key = arLine[0].replace(" ", "").toLowerCase();
					String[] tmpWrap = arLine[1].replace(" ", "").toLowerCase().split(",");
					ArrayList<String> wraps = new ArrayList<String>(Arrays.asList(tmpWrap));
					wordsMaps.put(key, wraps);
				}
			}
			fileReader.close();
		}
	}

	public void findWordHyphenation(String word) {
		

		String tmpWord = word.replaceAll("[^\\da-zA-Z\\u0400-\\u04FF]", "").toLowerCase();
		StringBuilder tmpResult = new StringBuilder();
		tmpResult.append("Word \"" + tmpWord + "\"");
		if (wordsMaps.containsKey(tmpWord)) {
			tmpResult.append(" in base. ");
			int index = wordsMaps.get(tmpWord).indexOf(word.toLowerCase());
			tmpResult.append("The hyphenation \"" + word + "\" is " + (index != -1 ? "" : "not ") + "correct");
		} else {
			tmpResult.append(" not found.");
		}		
		System.out.println(tmpResult.toString());

	}

}
