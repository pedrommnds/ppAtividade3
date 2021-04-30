package ppAtividade3;

import interfaces.IDocument;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.awt.*;
import javax.swing.*;

public class Main {
	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("File Chooser");
		JPanel panel = new JPanel(new BorderLayout());
		JFileChooser fileChooser = new JFileChooser();
		JButton readButton = new JButton("Browse");
		
		frame.setMinimumSize(new Dimension(200, 75));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel.add(readButton, BorderLayout.CENTER);
		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		readButton.addActionListener (ev -> {
			int returnVal = fileChooser.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String fileName = file.getName();
				String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
				
				File currentDir = new File("./plugins");
				String[] plugins = currentDir.list();
				URL[] jars = new URL[plugins.length];
				for (int i = 0; i < plugins.length; i++) {
					try {
						jars[i] = (new File("./plugins/" + plugins[i])).toURL();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				URLClassLoader ucl = new URLClassLoader(jars);
				Class[] cArg = new Class[1];
				cArg[0] = File.class;
				
				for(String documentType : plugins) {
					try {	
						Class MetaDocument = Class.forName(documentType.toLowerCase().split("\\.")[0] + "." + documentType.split("\\.")[0], true, ucl);
						IDocument document = (IDocument) MetaDocument.getDeclaredConstructor(cArg).newInstance(file);
						if(document.supportedExtension().equals(fileExtension)) {
					        document.open();
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	};
}