package hr.fer.opprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

public class JNotepadPP extends JFrame {
	private static final long serialVersionUID = 1L;

	private Path openedFilePath;
	private DefaultMultipleDocumentModel multiModel;
	private JScrollPane sp;
	private JPanel stats;

	Locale hrLocale = new Locale("hr");
	Collator hrCollator = Collator.getInstance(hrLocale);

	private JMenu toolsMenu;

	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);

		initGUI();
	}

	private void initGUI() {

		this.getContentPane().setLayout(new BorderLayout());

		multiModel = new DefaultMultipleDocumentModel();

		this.getContentPane().add(multiModel, BorderLayout.CENTER);

		createActions();
		createMenus();
		createToolbars();

		stats = new JPanel();
		GridLayout gl= new GridLayout(1, 2);
		stats.setLayout(gl);
		this.getContentPane().add(stats, BorderLayout.PAGE_END);
	}

	private Action openDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();

			multiModel.loadDocument(filePath);
			openedFilePath = filePath;

			JTextArea ta = multiModel.getCurrentDocument().getTextComponent();

			ta.addCaretListener(new CaretListener() {

				@Override
				public void caretUpdate(CaretEvent e) {
					int dot = e.getDot();

					int mark = e.getMark();
					if (dot != mark) {
						toolsMenu.enable();
					} else {
						toolsMenu.disable();
						;
					}

					stats.removeAll();

					int caretPos = ta.getCaretPosition();
					int rowNum = (caretPos == 0) ? 1 : 0;
					for (int offset = caretPos; offset > 0;) {
						try {
							offset = Utilities.getRowStart(ta, offset) - 1;
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
						rowNum++;
					}

					int offset = 0;
					try {
						offset = Utilities.getRowStart(ta, caretPos);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					int colNum = caretPos - offset + 1;

					JLabel l1 = new JLabel("Length:" + ta.getText().length());
					l1.setAlignmentX(LEFT_ALIGNMENT);
					stats.add(l1);

					JPanel pom= new JPanel();
					
					pom.add(new JLabel("Ln: " + rowNum));
					pom.add(new JLabel("Col: " + colNum));
					
					pom.setAlignmentX(LEFT_ALIGNMENT);
					
					if (ta.getSelectedText() == null) {
						pom.add(new JLabel("Sel: 0"));
					} else {
						pom.add(new JLabel("Sel: " + ta.getSelectedText().length()));
					}
					
					stats.add(pom);
				}
			});

		}
	};

	private Action saveDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			multiModel.saveDocument(multiModel.getCurrentDocument(), multiModel.getCurrentDocument().getFilePath());
		}
	};

	private Action saveAsDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			multiModel.saveDocument(multiModel.getCurrentDocument(), null);
		}
	};

	private Action createDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multiModel.createNewDocument();

		}
	};

	private Action exitAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};

	private Action closeDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multiModel.closeDocument(multiModel.getCurrentDocument());
		}
	};

	private Action statisticsAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultSingleDocumentModel sdm = multiModel.getCurrentDocument();

			String str = sdm.getTextComponent().getText();
			int brKaraktera = str.length();

			String strBezPraznina = str.replaceAll("\\s", "");
			int brbezPraznina = strBezPraznina.length();

			int count = str.split("\r\n|\r|\n").length;

			JOptionPane.showMessageDialog(JNotepadPP.this, "Your document has " + brKaraktera + " characters, "
					+ brbezPraznina + " non-blank characters and " + count + " lines.");
		}
	};

	private Action copyAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultSingleDocumentModel sdm = multiModel.getCurrentDocument();

			JTextArea ta = sdm.getTextComponent();
			ta.copy();

		}
	};

	private Action pasteAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultSingleDocumentModel sdm = multiModel.getCurrentDocument();

			JTextArea ta = sdm.getTextComponent();
			ta.paste();
		}
	};

	private Action cutAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultSingleDocumentModel sdm = multiModel.getCurrentDocument();

			JTextArea ta = sdm.getTextComponent();
			ta.cut();
		}
	};

	private Action toLowerCaseAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultSingleDocumentModel sdm = multiModel.getCurrentDocument();

			JTextArea ta = sdm.getTextComponent();

			String str = ta.getSelectedText();

			str = str.toLowerCase();

			ta.setText(ta.getText().replace(ta.getSelectedText(), str));
		}
	};

	private Action toUpperCaseAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultSingleDocumentModel sdm = multiModel.getCurrentDocument();

			JTextArea ta = sdm.getTextComponent();

			String str = ta.getSelectedText();

			str = str.toUpperCase();

			ta.setText(ta.getText().replace(ta.getSelectedText(), str));
		}
	};

	private Action invertCaseAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultSingleDocumentModel sdm = multiModel.getCurrentDocument();

			JTextArea ta = sdm.getTextComponent();

			String str = ta.getSelectedText();

			str = reverseCase(str);

			ta.setText(ta.getText().replace(ta.getSelectedText(), str));
		}
	};

	private Action sortAscendingAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			DefaultSingleDocumentModel sdm = multiModel.getCurrentDocument();

			JTextArea ta = sdm.getTextComponent();
			String text = ta.getText();
			String selectedText = ta.getSelectedText();
			String[] linesText = text.split("\n");
			String[] linesSelected = selectedText.split("\n");

			// ta ima funkcije line i funkcije selected za start i end pa s tim slozi da
			// pocetnu i zadnju liniju cijelu dobijes

			int start = ta.getSelectionStart();
			int end = ta.getSelectionEnd();

			int brojac = 0;

			for (int i = 0; i < linesText.length; i++) {
				brojac = brojac + linesText[i].length() + 1;
				if (start < brojac) {
					linesSelected[0] = linesText[i];
					break;
				}
			}
			brojac = 0;
			for (int i = 0; i < linesText.length; i++) {
				brojac = brojac + linesText[i].length() + 1;
				if (end < brojac) {
					linesSelected[linesSelected.length - 1] = linesText[i];
					break;
				}
			}

			for (int i = 0; i < linesSelected.length; i++) {
				for (int j = i + 1; j < linesSelected.length; j++) {
					if (hrCollator.compare(linesSelected[i], linesSelected[j]) > 0) {
						String temp = linesSelected[i];
						linesSelected[i] = linesSelected[j];
						linesSelected[j] = temp;
					}
				}
			}
			brojac = 0;
			int seBrojac = 0;
			boolean startRjesen = false;
			for (int i = 0; i < linesText.length; i++) {
				brojac = brojac + linesText[i].length();

				if (!startRjesen && start < brojac) {
					linesText[i] = linesSelected[seBrojac];
					seBrojac++;
					startRjesen = true;
					continue;
				}

				if (startRjesen && seBrojac < linesSelected.length) {
					linesText[i] = linesSelected[seBrojac];
					seBrojac++;
				}

			}

			String noviStr = "";
			for (String line : linesText) {
				noviStr = noviStr + line + "\n";
			}
			ta.setText(noviStr);
		}
	};

	private Action sortDescendingAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultSingleDocumentModel sdm = multiModel.getCurrentDocument();

			JTextArea ta = sdm.getTextComponent();
			String text = ta.getText();
			String selectedText = ta.getSelectedText();
			String[] linesText = text.split("\n");
			String[] linesSelected = selectedText.split("\n");

			int start = ta.getSelectionStart();
			int end = ta.getSelectionEnd();

			int brojac = 0;

			for (int i = 0; i < linesText.length; i++) {
				brojac = brojac + linesText[i].length() + 1;
				if (start < brojac) {
					linesSelected[0] = linesText[i];
					break;
				}
			}
			brojac = 0;
			for (int i = 0; i < linesText.length; i++) {
				brojac = brojac + linesText[i].length() + 1;
				if (end < brojac) {
					linesSelected[linesSelected.length - 1] = linesText[i];
					break;
				}
			}

			for (int i = 0; i < linesSelected.length; i++) {
				for (int j = i + 1; j < linesSelected.length; j++) {
					if (hrCollator.compare(linesSelected[i], linesSelected[j]) < 0) {
						String temp = linesSelected[i];
						linesSelected[i] = linesSelected[j];
						linesSelected[j] = temp;
					}
				}
			}
			brojac = 0;
			int seBrojac = 0;
			boolean startRjesen = false;
			for (int i = 0; i < linesText.length; i++) {
				brojac = brojac + linesText[i].length();

				if (!startRjesen && start < brojac) {
					linesText[i] = linesSelected[seBrojac];
					seBrojac++;
					startRjesen = true;
					continue;
				}

				if (startRjesen && seBrojac < linesSelected.length) {
					linesText[i] = linesSelected[seBrojac];
					seBrojac++;
				}

			}

			String noviStr = "";
			for (String line : linesText) {
				noviStr = noviStr + line + "\n";
			}
			ta.setText(noviStr);
		}
	};

	private void createActions() {
		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk.");

		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file.");

		saveAsDocumentAction.putValue(Action.NAME, "Save As");
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");

		createDocumentAction.putValue(Action.NAME, "New");
		createDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to create new file");

		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application.");

		closeDocumentAction.putValue(Action.NAME, "Close");
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Close document.");

		statisticsAction.putValue(Action.NAME, "Statistics");
		statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		statisticsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		statisticsAction.putValue(Action.SHORT_DESCRIPTION, "Statistics of document.");

		copyAction.putValue(Action.NAME, "Copy");
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Copy selected text.");

		cutAction.putValue(Action.NAME, "Cut");
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Cut selected text.");

		toLowerCaseAction.putValue(Action.NAME, "To lower case");
		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		toLowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		toLowerCaseAction.putValue(Action.SHORT_DESCRIPTION, "Selected text becomes lower case.");

		toUpperCaseAction.putValue(Action.NAME, "To upper case");
		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUpperCaseAction.putValue(Action.SHORT_DESCRIPTION, "Selected text becomes upper case.");

		invertCaseAction.putValue(Action.NAME, "Invert case");
		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		invertCaseAction.putValue(Action.SHORT_DESCRIPTION, "Invert case of selected text.");

		pasteAction.putValue(Action.NAME, "Paste");
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Paste text.");

		sortAscendingAction.putValue(Action.NAME, "Sort ascending");
		sortAscendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		sortAscendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		sortAscendingAction.putValue(Action.SHORT_DESCRIPTION, "Sort ascending.");

		sortDescendingAction.putValue(Action.NAME, "Sort descending");
		sortDescendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control M"));
		sortDescendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);
		sortDescendingAction.putValue(Action.SHORT_DESCRIPTION, "Sort descending.");
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(createDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(statisticsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));

		toolsMenu = new JMenu("Tools");
		menuBar.add(toolsMenu);

		toolsMenu.add(toLowerCaseAction);
		toolsMenu.add(toUpperCaseAction);
		toolsMenu.add(invertCaseAction);
		this.setJMenuBar(menuBar);

		JMenu sortMenu = new JMenu("Sort");
		menuBar.add(sortMenu);

		sortMenu.add(sortAscendingAction);
		sortMenu.add(sortDescendingAction);

	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		JButton bOpen = new JButton(new ImageIcon("src/resources/open.png"));
		bOpen.addActionListener(openDocumentAction);
		toolBar.add(bOpen);

		JButton bSave = new JButton(new ImageIcon("src/resources/save.png"));
		bSave.addActionListener(saveDocumentAction);
		toolBar.add(bSave);

		JButton bSaveAs = new JButton(new ImageIcon("src/resources/saveas.png"));
		bSaveAs.addActionListener(saveAsDocumentAction);
		toolBar.add(bSaveAs);

		JButton bNew = new JButton(new ImageIcon("src/resources/new.png"));
		bNew.addActionListener(createDocumentAction);
		toolBar.add(bNew);

		JButton bClose = new JButton(new ImageIcon("src/resources/close.png"));
		bClose.addActionListener(closeDocumentAction);
		toolBar.add(bClose);

		JButton bStatistics = new JButton(new ImageIcon("src/resources/statistics.png"));
		bStatistics.addActionListener(statisticsAction);
		toolBar.add(bStatistics);

		toolBar.addSeparator();

		JButton bCopy = new JButton(new ImageIcon("src/resources/copy.png"));
		bCopy.addActionListener(copyAction);
		toolBar.add(bCopy);

		JButton bCut = new JButton(new ImageIcon("src/resources/cut.png"));
		bCut.addActionListener(cutAction);
		toolBar.add(bCut);

		JButton bPaste = new JButton(new ImageIcon("src/resources/paste.png"));
		bPaste.addActionListener(pasteAction);
		toolBar.add(bPaste);

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);

	}

	public String reverseCase(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isUpperCase(c)) {
				chars[i] = Character.toLowerCase(c);
			} else if (Character.isLowerCase(c)) {
				chars[i] = Character.toUpperCase(c);
			}
		}
		return new String(chars);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}

}
