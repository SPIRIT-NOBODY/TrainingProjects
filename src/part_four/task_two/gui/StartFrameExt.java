package part_four.task_two.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import part_four.task_one.database.Database;
import part_four.task_one.gui.StartFrame;

public class StartFrameExt extends StartFrame {

	protected String orderBy = " ORDER BY %s %s";
	protected String additionalColumn = "( MarkMath+  MarkLiterature+  MarkEnglish+  MarkProgramming)/4";
	protected String additionalColumnName = "Average";
	protected String[] newColumns;

	public StartFrameExt(Database db) throws SQLException {
		super(db);
		newColumns = Arrays.copyOf(columns, columns.length + 1);
		newColumns[newColumns.length - 1 ] = additionalColumnName;
		addSortButtons();
	}

	protected void addSortButtons() {
		Box sortButtonBox = new Box(BoxLayout.X_AXIS);
		sortButtonBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton sortAsc = new JButton("Sort Asc");
		sortAsc.addActionListener((ev)->{
			actionEvent("ASC");
		});		
		JButton sortDesc = new JButton("Sort Desc");		
		sortDesc.addActionListener((ev) -> {
			actionEvent("DESC");
		});
		sortButtonBox.add(sortAsc);
		sortButtonBox.add(sortDesc);
		mainBox.add(sortButtonBox);
	}
	
	private void actionEvent(String sort) {
		try {
			String condition = "Id >";
			String order = String.format(orderBy, additionalColumnName, sort);				
			fillTableModel(db.selectByCondition(condition, new String[] { "0" }, order, "*",
					additionalColumn + " as " + additionalColumnName), newColumns);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void fillTableModel(List<String[]> allRow, String[] additionalColumns) throws SQLException {
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}
		tableModel.setColumnIdentifiers(additionalColumns);
		for (String[] row : allRow) {
			tableModel.addRow(row);
		}
	}
	
}
