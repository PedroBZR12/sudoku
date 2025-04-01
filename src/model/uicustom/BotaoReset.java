package model.uicustom;

import java.awt.event.ActionListener;
import javax.swing.JButton;;

public class BotaoReset extends JButton {
    public BotaoReset(final ActionListener actionListener)
    {
        this.setText("Reiniciar jogo");
        this.addActionListener(actionListener);
    }
}
