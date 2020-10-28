package part_one.task_three;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.TextField;

public class DoubleTextField extends TextField{
	private boolean validDouble = false;
	
	public DoubleTextField(int columns) {
		super(columns);
	}
	public DoubleTextField(String text, int columns) throws HeadlessException {
		super(text, columns);		
	}
	public DoubleTextField(String text) throws HeadlessException {
		super(text);		
	}
	DoubleTextField(){
		super();
	}
	public void setValidDouble(boolean valid){
		validDouble = valid;
		if(validDouble) {
			setBackground(Color.WHITE);
		}else {
			setBackground(new Color(243,163,151));
		}		
	}
	
	public boolean isValidDoulbe() {
		return validDouble;
	}
	
	public void validateDouble() {		
		try {
			Double.parseDouble(getText());
			setValidDouble(true);
		} catch (NumberFormatException err) {
			setValidDouble(false);
		}
		if(getText().equals("")) {
			setBackground(Color.WHITE);
		}
	}
	
}
