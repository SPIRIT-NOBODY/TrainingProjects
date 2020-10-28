package part_four.task_one.gui;

import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import part_four.task_one.database.Database;
import part_four.task_one.interfaces.PropertiesGetter;

/**
 * Class work with JFrame and user interface
 */

public class StartFrame implements PropertiesGetter {

	/**
	 * Main frame for show table.
	 */
	protected JFrame startFrame;
	/**
	 * Database object. Use for change and select table. {@link Database
	 * Database.class}
	 */
	protected Database db;

	/**
	 * Main box for all components.
	 */
	protected Box mainBox;
	/**
	 * Label show select query. For show all not visible
	 */
	protected JLabel selectText;

	/**
	 * Button for show all rows
	 */
	protected JButton showAll;

	/**
	 * Button for show popup dialog frame. Frame contains columns from table as
	 * inputs
	 */
	protected JButton addNew;

	/**
	 * Button for delete selected rows
	 */
	protected JButton deleteSelected;

	/**
	 * Button for show popup dialog with select and delete combobox by condition.
	 */
	protected JButton selectQuery;

	/**
	 * True if popup dialog is show
	 */
	volatile protected Boolean isAddDialogShowed = false;

	/**
	 * Header table
	 */
	protected String[] columns;

	/**
	 * Params string for show with input
	 */
	protected String[] columnParams;

	/**
	 * New students fields
	 */
	protected JTextFieldValidation[] addStudentsFields;

	/**
	 * Main table with results
	 */
	protected JTable table;

	/**
	 * Table model, build JTable
	 */
	protected DefaultTableModel tableModel;

	/**
	 * Properties for select or delete by conditions. Properties are in file.
	 */
	protected Properties selectDialogProperties;

	/**
	 * Init {@link #db db} , {@link #columns columns} and {@link #columnParams
	 * columnParams} invoke {@link #createFrame() createFrame() } method
	 * 
	 * @param db database object
	 * @throws SQLException {@inheritDoc}}
	 */
	public StartFrame(Database db) throws SQLException {
		this.db = db;
		columns = db.getColumnNames();
		columnParams = db.getColumnParams();
		createFrame();
	}

	/**
	 * Create main frame with main table. Use {@link WindowListenerAdapter
	 * WindowListenerAdapter.class} for event close. When close frame connection
	 * with database must be close. Invokes {@link #createTable() createTable() }
	 * method and {@link #addButtons() addButtons()} method
	 * 
	 * @throws SQLException {@inheritDoc}
	 */
	private void createFrame() throws SQLException {
		startFrame = new JFrame("Task one");
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.addWindowListener(new WindowListenerAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					db.closeConnection();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		mainBox = new Box(BoxLayout.Y_AXIS);
		mainBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		selectText = new JLabel("");
		selectText.setAlignmentX(Component.CENTER_ALIGNMENT);
		createTable();
		mainBox.add(selectText);
		mainBox.add(new JScrollPane(table));
		addButtons();
		startFrame.setContentPane(mainBox);
		startFrame.pack();
		startFrame.setLocationRelativeTo(null);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				startFrame.setMinimumSize(startFrame.getSize());
				startFrame.setVisible(true);
			}
		});

	}

	/**
	 * Create main table and set header. Invoke {@link #fillTableModel(List)
	 * fillTableModel(List) } method
	 * 
	 * @throws SQLException {@inheritDoc}
	 */

	private void createTable() throws SQLException {
		fillTableModel(db.showAll());
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * Method redraw table.
	 * 
	 * @param allRow list with array string for insert to table.
	 * @throws SQLException {@inheritDoc}
	 */
	public void fillTableModel(List<String[]> allRow) throws SQLException {
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}
		tableModel.setColumnIdentifiers(columns);
		for (String[] row : allRow) {
			tableModel.addRow(row);
		}
	}

	/**
	 * Method add buttons to main frame
	 */
	private void addButtons() {
		Box buttonBox = new Box(BoxLayout.X_AXIS);
		showAll = new JButton("Show All");
		showAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fillTableModel(db.showAll());
					selectText.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		buttonBox.add(showAll);
		addNew = new JButton("Add new");
		addNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (isAddDialogShowed) {
					if (!isAddDialogShowed) {
						showAddDialog();
						isAddDialogShowed = true;
					}
				}

			}
		});
		buttonBox.add(addNew);
		selectQuery = new JButton("Select");
		selectQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (isAddDialogShowed) {
					if (!isAddDialogShowed) {
						try {
							showSelectDialog();
							isAddDialogShowed = true;
						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				}

			}
		});
		buttonBox.add(selectQuery);
		deleteSelected = new JButton("Delete selected");
		deleteSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedRow = table.getSelectedRows();
				if (selectedRow.length > 0) {
					try {
						List<String> valuesList = new ArrayList<>();
						for (int i = 0; i < selectedRow.length; i++) {
							int rowNum = selectedRow[i];
							valuesList.add(table.getModel().getValueAt(rowNum, 0).toString());
						}
						String[] values = new String[valuesList.size()];
						valuesList.toArray(values);
						db.deleteSelectedRowQuery(values);
						fillTableModel(db.showAll());
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		buttonBox.add(deleteSelected);
		mainBox.add(buttonBox);
	}

	/**
	 * Show frame for add new students to table
	 */
	private void showAddDialog() {
		JFrame dialog = new JFrame("Insert students");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Box box = new Box(BoxLayout.Y_AXIS);
		Box inputBox = new Box(BoxLayout.Y_AXIS);
		addTextField(inputBox);
		box.add(inputBox);
		JButton addStud = new JButton("Add student");
		addStud.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isFieldsValid = false;
				int countFields = addStudentsFields.length - 1;
				int countValidFields = 0;
				List<String> valuesList = new ArrayList<>();
				for (int i = 1; i < addStudentsFields.length; i++) {
					JTextFieldValidation field = addStudentsFields[i];
					field.validationValue();
					if (field.getValueValid()) {
						countValidFields++;
						valuesList.add(field.getText());
					}
				}
				if (countFields == countValidFields) {
					String[] values = new String[valuesList.size()];
					valuesList.toArray(values);
					try {
						db.addValues(values);
						dialog.dispose();
						fillTableModel(db.showAll());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		box.add(addStud);
		dialog.setContentPane(box);
		dialog.pack();
		dialog.setMinimumSize(startFrame.getMinimumSize());
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.addWindowListener(new WindowListenerAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				isAddDialogShowed = false;
			}
		});
	}

	/**
	 * Added text field to dialog frame. Invoke {@link #createFieldsArray()
	 * createFieldsArray()}
	 * 
	 * @param box container for insert fields
	 */

	private void addTextField(Box box) {
		createFieldsArray();
		for (int i = 1; i < columns.length; i++) {
			GridLayout grid = new GridLayout(1, 2);
			JPanel panel = new JPanel(grid);
			panel.setMaximumSize(new Dimension(400, 30));
			panel.add(new JLabel(columns[i] + " (" + columnParams[i] + ") : "));
			panel.add(addStudentsFields[i]);
			box.add(panel);
		}
	}

	/**
	 * Create fields for insert to dialog frame. Set validation to integer or text.
	 * Use {@link JTextFieldValidation JTextFieldValidation.class }
	 */

	private void createFieldsArray() {
		addStudentsFields = new JTextFieldValidation[columns.length];
		for (int i = 1; i < addStudentsFields.length; i++) {
			JTextFieldValidation txf = new JTextFieldValidation();
			txf.setMaximumSize(new Dimension(400, 30));
			if (columnParams[i].matches(".*INT.*")) {
				txf.setValidator("[0-9]|10");
			} else {
				txf.setValidator(".+");
			}
			txf.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JTextFieldValidation target = (JTextFieldValidation) e.getSource();
					target.validationValue();
				}
			});
			addStudentsFields[i] = txf;
		}
	}

	/**
	 * Show dialog with select or delete by conditions inputs. Create buttons and
	 * buttons events.Invoke {@link #setComboBoxValues(JComboBox)
	 * setComboBoxValues(JComboBox)}
	 * 
	 * @throws IOException {@inheritDoc}
	 */

	private void showSelectDialog() throws IOException {
		JFrame dialog = new JFrame("Choose select or delete:");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container pane = dialog.getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 20;
		gbc.insets = new Insets(20, 20, 10, 30);
		gbc.weighty = 1;
		JComboBox<String> comboBox = new JComboBox<String>();
		setComboBoxValues(comboBox);

		pane.add(comboBox, gbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;

		JTextFieldValidation queryInput = new JTextFieldValidation();
		queryInput.setValidator("[0-9]|10");
		pane.add(queryInput, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 2;
		gbc.weighty = 2;
		Box buttonBox = new Box(BoxLayout.X_AXIS);
		JButton selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				queryInput.validationValue();
				if (queryInput.getValueValid()) {
					String key = (String) comboBox.getSelectedItem();
					try {
						dialog.dispose();
						fillTableModel(db.selectByCondition(selectDialogProperties.getProperty(key),
								new String[] { queryInput.getText() }, ""));
						selectText.setText(key.trim() + queryInput.getText());

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				queryInput.validationValue();
				if (queryInput.getValueValid()) {
					String key = (String) comboBox.getSelectedItem();
					try {
						db.deleteByCondition(selectDialogProperties.getProperty(key),
								new String[] { queryInput.getText() });
						dialog.dispose();
						fillTableModel(db.showAll());
						selectText.setText("");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		buttonBox.add(selectButton);
		buttonBox.add(deleteButton);
		pane.add(buttonBox, gbc);
		dialog.pack();
		dialog.setMinimumSize(startFrame.getMinimumSize());
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.addWindowListener(new WindowListenerAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				isAddDialogShowed = false;
			}
		});
	}

	/**
	 * Add items to combobox. Invoke
	 * {@link PropertiesGetter#getProperties(String, Properties)
	 * PropertiesGetter#getProperties(String, Properties)} for fill
	 * {@link #selectDialogProperties selectDialogProperties}. Properties in file
	 * has format: key - show into combobox text, value - use into sql execute
	 * query.
	 * 
	 * @param comboBox combobox for added items.
	 * @throws IOException {@inheritDoc}
	 */
	
	private void setComboBoxValues(JComboBox comboBox) throws IOException {
		selectDialogProperties = new Properties();
		getProperties("selectdialog.properties", selectDialogProperties);
		Set keys = selectDialogProperties.keySet();
		for (Object key : keys) {
			String comboItem = (String) key;
			comboBox.addItem(comboItem);
		}
	}
}
