package hr.fer.opprpp1.hw08.jnotepadpp;

public interface SingleDocumentListener {
	
	void documentModifyStatusUpdated(SingleDocumentModel model);

	void documentFilePathUpdated(SingleDocumentModel model);
}
