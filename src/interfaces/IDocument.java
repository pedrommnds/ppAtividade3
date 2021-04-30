package interfaces;

import java.io.IOException;
import javax.swing.JFrame;

public interface IDocument {
	public abstract void open() throws IOException;
	public abstract JFrame getEditor() throws IOException;
	public abstract String supportedExtension();
}