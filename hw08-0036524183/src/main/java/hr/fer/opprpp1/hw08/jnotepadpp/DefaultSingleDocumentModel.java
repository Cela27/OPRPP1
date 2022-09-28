package hr.fer.opprpp1.hw08.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;



public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private Path filePath;
	private boolean modified=false;
	private JTextArea textComponent;
	private List<SingleDocumentListener> listeners= new ArrayList<>();
	private Icon icon;
	
	
	
	public DefaultSingleDocumentModel(Path filePath, String content) {
		this.textComponent= new JTextArea(content);
		
		this.filePath=filePath;
		listeners.add(new MySingleDocumentListener());
		icon= new ImageIcon("src/resources/save.png");
		
		textComponent.getDocument().addDocumentListener( new DocumentListener() {

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	setModified(true);
	        }

	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	setModified(true);
	        }

	        @Override
	        public void changedUpdate(DocumentEvent arg0) {
	        	setModified(true);
	        }
	    });
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		this.filePath=path;
		modified=true;
		for( SingleDocumentListener l: listeners) {
			l.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified=modified;
		for( SingleDocumentListener l: listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
		
	}
	
	public Icon getIcon() {
		return icon;
	}
	
	public void setIcon(Icon icon) {
		this.icon=icon;
	}
	
	private static class MySingleDocumentListener implements SingleDocumentListener{

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			DefaultSingleDocumentModel mdl= (DefaultSingleDocumentModel) model;
			if(mdl.isModified()) {
				Icon ic=new ImageIcon("src/resources/unsaved.png");
				mdl.setIcon(ic);
			}
			else {
				Icon ic=new ImageIcon("src/resources/save.png");
				mdl.setIcon(ic);
			}
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
