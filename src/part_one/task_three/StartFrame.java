package part_one.task_three;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;

class StartFrame extends JFrame {

	private final int defaultWidth = 400;
	private final int defaultHeight = 300;
	
	private JPanel mainPanel;
	
	private JButton calculate = new JButton("Calculate");
	
	private DoubleTextField baseValue = new DoubleTextField(20);
	private DoubleTextField earnings = new DoubleTextField(20);
	
	private JLabel calculateResult = new JLabel("");
	
	private TextListener textListener = new TextListenerImpl();

	StartFrame() {
		TaxCalculator calculator = new TaxCalculator();
		
		setSize(defaultWidth,defaultHeight);		
		
		mainPanel = new JPanel(new VerticalLayout());
		add(mainPanel);
		
		baseValue.addTextListener(textListener);		
		earnings.addTextListener(textListener);
		calculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculator.calculate(calculateResult, earnings, baseValue);				
			}
		});
		mainPanel.add(new JLabel("Enter earnings", SwingConstants.RIGHT));
		mainPanel.add(earnings);
		mainPanel.add(new JLabel("Enter base value", SwingConstants.RIGHT));
		mainPanel.add(baseValue);
		mainPanel.add(calculate);
		mainPanel.add(calculateResult);
		
		setContentPane(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tax calculation");
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	class TextListenerImpl implements TextListener {
		@Override
		public void textValueChanged(TextEvent e) {
			DoubleTextField source = (DoubleTextField) e.getSource();
			source.validateDouble();
		}
	}

}
