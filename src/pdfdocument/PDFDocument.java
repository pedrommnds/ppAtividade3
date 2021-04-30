package pdfdocument;

import interfaces.IDocument;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.*;
import javax.swing.*;

public class PDFDocument implements IDocument {
	private PDDocument doc;
	
	public PDFDocument(File f) throws IOException {
		this.doc = PDDocument.load(f);
	};
	
	public void open() throws IOException {
		JFrame frame = this.getEditor();
		frame.setVisible(true);
	};

	public JFrame getEditor() throws IOException {
		PDFRenderer renderer = new PDFRenderer(this.doc);
		BufferedImage image = renderer.renderImage(0, 1);
		
		JFrame frame = new JFrame("PDF Viewer");
		JLabel label = new JLabel(new ImageIcon(image));
		
		frame.setSize(image.getWidth(), image.getHeight());
	    frame.getContentPane().add(label,BorderLayout.CENTER);
	    frame.setLocationRelativeTo(null);
	    frame.pack();
	    
	    return frame;
	};
	
	public String supportedExtension() {
		return "pdf";
	};
}