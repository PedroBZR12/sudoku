package model.uicustom;

import java.awt.event.ActionListener;
import javax.swing.JButton;

public class BotaoChecarStatusDoJogo extends JButton {
    public BotaoChecarStatusDoJogo(final ActionListener actionListener){
        this.setText("Verificar jogo");
        this.addActionListener(actionListener);
    }
}
