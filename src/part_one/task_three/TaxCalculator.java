package part_one.task_three;

import javax.swing.JLabel;

public class TaxCalculator {

	private final double[] baseValueInterval = new double[] { 240.0, 600 - 240, 840 - 600, 1080 - 840, 0 };

	private final double[] percentMap = new double[] { 9.0, 15.0, 20.0, 25.0, 30 };

	public void calculate(JLabel label, DoubleTextField earnings, DoubleTextField baseValue) {

		if (earnings.isValidDoulbe() && baseValue.isValidDoulbe()) {
			double baseValueOrig = Double.parseDouble(baseValue.getText());
			double countBase = getBaseCount(earnings.getText(), baseValue.getText());
			label.setText("From " + earnings.getText() + " tax is "
					+ String.format("%.2f", getTaxResult(countBase, baseValueOrig)));
		} else {
			label.setText("Please, input correct value!");
		}

	}

	public double getBaseCount(String earnings, String baseValue) {
		return Double.parseDouble(earnings) / Double.parseDouble(baseValue);
	}

	public double getTaxResult(double countBase, double baseValue) {

		double taxValue = 0;
		double tmpCountBase = countBase;
		for (int i = 0; i < baseValueInterval.length; i++) {
			if (Double.compare(tmpCountBase, baseValueInterval[i]) <= 0 || baseValueInterval[i] == 0) {
				taxValue += tmpCountBase * percentMap[i] / 100;
				break;
			} else {
				tmpCountBase -= baseValueInterval[i];
				taxValue += baseValueInterval[i] * percentMap[i] / 100;
			}

		}

		return taxValue * baseValue;
	}
}
