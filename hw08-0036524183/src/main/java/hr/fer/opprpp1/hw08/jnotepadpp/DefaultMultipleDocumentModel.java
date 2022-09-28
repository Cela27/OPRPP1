package hr.fer.opprpp1.hw08.jnotepadpp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultSingleDocumentModel currDocumentModel;
	private List<SingleDocumentModel> listOfModels = new ArrayList<>();
	private List<MultipleDocumentListener> listeners = new ArrayList<>();

	private boolean toolsDisabled = true;

	private KeyListener kl = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			currDocumentModel.setModified(true);
			changeIconForcurrModel();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			currDocumentModel.setModified(true);
			changeIconForcurrModel();

		}

		@Override
		public void keyPressed(KeyEvent e) {
			currDocumentModel.setModified(true);
			changeIconForcurrModel();
		}
	};
	private CaretListener cl = new CaretListener() {

		@Override
		public void caretUpdate(CaretEvent e) {
			int dot = e.getDot();

			int mark = e.getMark();
			if (dot != mark) {
				setToolsDisabled(false);
			} else {
				setToolsDisabled(true);
			}

		}
	};

	public DefaultMultipleDocumentModel() {
		listeners.add(new MyMultiDocumentListener());
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				currModelChanged();
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		DefaultSingleDocumentModel sdm = new DefaultSingleDocumentModel(null, "");

		this.addTab("untitled", sdm.getTextComponent());
		listOfModels.add(sdm);

		DefaultSingleDocumentModel perivous = currDocumentModel;
		currDocumentModel = sdm;

		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(perivous, currDocumentModel);
			l.documentAdded(sdm);
		}

		sdm.setModified(true);
		this.setIconAt(getIndexOfDocument(sdm), sdm.getIcon());

		this.getComponentAt(this.getSelectedIndex()).addKeyListener(kl);

		return sdm;
	}

	@Override
	public DefaultSingleDocumentModel getCurrentDocument() {
		return currDocumentModel;
	}

	@Override
	public DefaultSingleDocumentModel loadDocument(Path path) {
		if (path == null)
			throw new NullPointerException();

		DefaultSingleDocumentModel sdm = null;

		File fileName = new File(path.toUri());

		if (!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
					"Datoteka " + fileName.getAbsolutePath() + " ne postoji!", "Pogre�ka", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		byte[] okteti;
		try {
			okteti = Files.readAllBytes(path);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
					"Pogre�ka prilikom č.itanja datoteke " + fileName.getAbsolutePath() + ".", "Pogreška",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		String tekst = new String(okteti, StandardCharsets.UTF_8);

		sdm = new DefaultSingleDocumentModel(path, tekst);

		DefaultSingleDocumentModel perivous = currDocumentModel;
		currDocumentModel = sdm;

		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(perivous, currDocumentModel);
			l.documentAdded(sdm);
		}

		listOfModels.add(sdm);

		this.addTab(path.toString(), sdm.getTextComponent());
		this.setIconAt(getIndexOfDocument(sdm), sdm.getIcon());

		this.getComponentAt(this.getSelectedIndex()).addKeyListener(kl);

		return sdm;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		

		if (newPath == null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if (jfc.showSaveDialog(DefaultMultipleDocumentModel.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Ni�ta nije snimljeno.", "Upozorenje",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			newPath = jfc.getSelectedFile().toPath();
		}
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

		File file = new File(newPath.toString());
		if (file.exists() && model.getFilePath()!=newPath) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Vec postoji file na tom mjestu.",
					"Upozorenje", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			Files.write(newPath, podatci);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
					"Pogreška prilikom zapisivanja datoteke " + newPath.toFile().getAbsolutePath()
							+ ".\nPa�nja: nije jasno u kojem je stanju datoteka na disku!",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}
		

		JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Datoteka je snimljena.", "Informacija",
				JOptionPane.INFORMATION_MESSAGE);

		model.setFilePath(newPath);

		this.setTitleAt(this.getSelectedIndex(), newPath.toString());

		currDocumentModel.setModified(false);
		this.setIconAt(getIndexOfDocument(model), currDocumentModel.getIcon());

		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		this.removeTabAt(this.getSelectedIndex());
		listOfModels.remove(model);
		if (listOfModels.size() == 0) {
			currDocumentModel = null;
		} else {
			currDocumentModel = (DefaultSingleDocumentModel) listOfModels.get(0);
		}

		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(model, currDocumentModel);
			l.documentRemoved(model);
		}

	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);

	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);

	}

	@Override
	public int getNumberOfDocuments() {
		return listOfModels.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index > listOfModels.size())
			throw new IllegalArgumentException();
		return listOfModels.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		for (SingleDocumentModel sdm : listOfModels) {
			try {
				if (sdm.getFilePath().equals(path))
					return sdm;

			} catch (NullPointerException ex) {
			}

		}
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return listOfModels.indexOf(doc);
	}

	private void changeIconForcurrModel() {
		this.setIconAt(this.getSelectedIndex(), currDocumentModel.getIcon());
	}

	private void currModelChanged() {

		if (listOfModels.size() == 0 || this.getSelectedIndex() < 0) {
			currDocumentModel = null;
			return;
		}

		currDocumentModel.getTextComponent().removeKeyListener(kl);
		currDocumentModel.getTextComponent().removeCaretListener(cl);

		currDocumentModel = (DefaultSingleDocumentModel) listOfModels.get(this.getSelectedIndex());

		currDocumentModel.getTextComponent().addKeyListener(kl);
		currDocumentModel.getTextComponent().addCaretListener(cl);
	}

	public boolean isToolsDisabled() {
		return toolsDisabled;
	}

	public void setToolsDisabled(boolean toolsDisabled) {
		this.toolsDisabled = toolsDisabled;
		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(null, currDocumentModel);
		}
	}

	private static class MyMultiDocumentListener implements MultipleDocumentListener {

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			//System.out.println("ovdje");
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
			// TODO Auto-generated method stub

		}

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			// TODO Auto-generated method stub

		}

	}
}
