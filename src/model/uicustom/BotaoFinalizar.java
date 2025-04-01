package model.uicustom;

import java.awt.event.ActionListener;
import javax.swing.JButton;;

public class BotaoFinalizar extends JButton {
    public BotaoFinalizar(final ActionListener actionListener)
    {
        this.setText("Concluir");
        this.addActionListener(actionListener);
    }
}
