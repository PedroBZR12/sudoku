package model.uicustom.frame;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    public MainFrame(final Dimension dimension, final JPanel PainelPrincipal){
            super("Sudoku");
            this.setSize(dimension);
            this.setPreferredSize(dimension);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.add(PainelPrincipal);
            this.setVisible(true);
    }
}
