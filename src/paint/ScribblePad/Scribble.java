package paint.ScribblePad;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*; 
import paint.ScribbleMenu.ColorDialog;

public class Scribble extends JFrame {

  public Scribble(String title) {
    super(title);
    // calling factory method 
    canvas = makeCanvas(); 
    getContentPane().setLayout(new BorderLayout()); 
    menuBar = createMenuBar(); 
    getContentPane().add(menuBar, BorderLayout.NORTH); 
    getContentPane().add(canvas, BorderLayout.CENTER);
    addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {
	  if (exitAction != null) { 
	    exitAction.actionPerformed(new ActionEvent(Scribble.this, 0, null)); 
	  }
	}
      }); 
  }

  protected JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu; 
    JMenuItem mi;

    // File menu 
    menu = new JMenu("Archivo"); 
    menuBar.add(menu); 

    mi = new JMenuItem("Nuevo");
    menu.add(mi);
    mi.addActionListener(new NewFileListener()); 

    mi = new JMenuItem("Abrir");
    menu.add(mi);
    mi.addActionListener(new OpenFileListener()); 

    mi = new JMenuItem("Guardar");
    menu.add(mi);
    mi.addActionListener(new SaveFileListener()); 

    mi = new JMenuItem("Guardar como");
    menu.add(mi);
    mi.addActionListener(new SaveAsFileListener()); 

    menu.add(new JSeparator()); 

    exitAction = new ExitListener(); 
    mi = new JMenuItem("Salir");
    menu.add(mi);
    mi.addActionListener(exitAction); 

    // option menu
    menu = new JMenu("Opciones"); 
    menuBar.add(menu); 

    mi = new JMenuItem("Color");
    menu.add(mi);
    mi.addActionListener(new ColorListener()); 

    // horizontal space 
    menuBar.add(Box.createHorizontalGlue());

    // Help menu 
    menu = new JMenu("Ayuda"); 
    menuBar.add(menu); 

    mi = new JMenuItem("Acerca de");
    menu.add(mi);
    mi.addActionListener(new AboutListener()); 

    return menuBar; 
  }

  // factory method 
  protected ScribbleCanvas makeCanvas() {
    return new ScribbleCanvas(); 
  }

  protected void newFile() { 
    currentFilename = null; 
    canvas.newFile(); 
    setTitle("Paint - Grupo 3 - Universidad Manuéla Beltrán");
  }

  protected void openFile(String filename) { 
    currentFilename = filename; 
    canvas.openFile(filename);
    setTitle("Paint - Grupo 3 [" + currentFilename + "]"); 
  }

  protected void saveFile() { 
    if (currentFilename == null) {
      currentFilename = "Untitled"; 
    }
    canvas.saveFile(currentFilename); 
    setTitle("Paint - Grupo 3 [" + currentFilename + "]");
  }

  protected void saveFileAs(String filename) { 
    currentFilename = filename; 
    canvas.saveFile(filename); 
    setTitle("Paint - Grupo 3 [" + currentFilename + "]");
  }

  class NewFileListener implements ActionListener { 
    
    public void actionPerformed(ActionEvent e) {
      newFile(); 
    }

  }

  class OpenFileListener implements ActionListener { 

    public void actionPerformed(ActionEvent e) {
      int retval = chooser.showDialog(null, "Abrir");
      if (retval == JFileChooser.APPROVE_OPTION) {
	File theFile = chooser.getSelectedFile();
	if (theFile != null) {
	  if (theFile.isFile()) {
	    String filename = chooser.getSelectedFile().getAbsolutePath();
	    openFile(filename); 
	  }
	}
      }
    }

  }

  class SaveFileListener implements ActionListener { 
    
    public void actionPerformed(ActionEvent e) {
      saveFile(); 
    }

  }

  class SaveAsFileListener implements ActionListener { 

    public void actionPerformed(ActionEvent e) {
      int retval = chooser.showDialog(null, "Guardar como");
      if (retval == JFileChooser.APPROVE_OPTION) {
	File theFile = chooser.getSelectedFile();
	if (theFile != null) {
	  if (!theFile.isDirectory()) {
	    String filename = chooser.getSelectedFile().getAbsolutePath();
	    saveFileAs(filename); 
	  }
	}
      }
    }

  }

  class ExitListener implements ActionListener { 
    
    public void actionPerformed(ActionEvent e) {
      int result = JOptionPane.showConfirmDialog(null,
						 "¿Quiere cerrar Paint - Grupo 3?", 
						 "Salir",
						 JOptionPane.YES_NO_OPTION);
      if (result == JOptionPane.YES_OPTION) {
	saveFile(); 
	System.exit(0); 
      }
    }

  }

  class ColorListener implements ActionListener { 
    
    public void actionPerformed(ActionEvent e) {
      Color result = dialog.showDialog();
      if (result != null) { 
	canvas.setCurColor(result);
      }
    }

    protected ColorDialog dialog = 
      new ColorDialog(Scribble.this, "Escoja color", canvas.getCurColor());

  }

  class AboutListener implements ActionListener { 
    
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(null, 
				    "Paint - Grupo 3 version 1.0 - 2017", "Acerca de", 
				    JOptionPane.INFORMATION_MESSAGE); 
    }

  }

  protected ScribbleCanvas canvas; 
  protected JMenuBar menuBar; 

  protected String currentFilename = null; 
  protected ActionListener exitAction; 
  protected JFileChooser chooser = new JFileChooser(".");

  public static void main(String[] args) {
    JFrame frame = new Scribble("Paint - Grupo 3");
    frame.setSize(width, height);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation(screenSize.width/2 - width/2,
		      screenSize.height/2 - height/2);
    frame.show();
  }

  protected static int width = 600; 
  protected static int height = 400; 

}
