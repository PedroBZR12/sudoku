package model.uicustom.frame;

import static java.awt.Color.black;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import model.uicustom.input.TextoNumerico;

public class Sudoku extends JPanel {
    public Sudoku(final List<TextoNumerico> camposTexto){
        var dimensao = new Dimension(170, 170);
        this.setSize(dimensao);
        this.setPreferredSize(dimensao);
        this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);

        camposTexto.forEach(this::add);
    }
}
