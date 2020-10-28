package part_four.task_one.gui;

import java.awt.Color;

import javax.swing.JTextField;

/**
 * Class extends from JTextField and add validation by regex string.
 */
public class JTextFieldValidation extends JTextField {

	/**
	 * Valid value.
	 */
	private boolean isValueValid = false;
	/**
	 * String regex validator.
	 */
	private String validator;

	/**
	 * Set @{link #validator validator} value
	 * 
	 * @param regex regex to use in @{String#matches(String) String.matches(String)}
	 */
	public void setValidator(String regex) {
		validator = regex;
	}

	/**
	 * Get @{link #validator validator}
	 * 
	 * @return validator value
	 */

	public String getValidator() {
		return validator;
	}

	/**
	 * Check value and set background
	 */
	public void validationValue() {
		isValueValid = this.getText().trim().matches(validator);
		if (!isValueValid) {
			this.setBackground(Color.RED);
		} else {
			this.setBackground(Color.WHITE);
		}
	}

	/**
	 * Get {@link #isValueValid isValueValid}
	 * 
	 * @return isValueValid value
	 */
	public boolean getValueValid() {
		return isValueValid;
	}
}
