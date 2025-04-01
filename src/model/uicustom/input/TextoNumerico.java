package model.uicustom.input;

import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.Espaco;
import service.EventEnum;
import static service.EventEnum.LIMPAR_ESPACO;
import service.EventListeners;

public class TextoNumerico extends JTextField implements EventListeners {


    private final Espaco espaco;

    public TextoNumerico(final Espaco espaco){
        this.espaco = espaco;
        var dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new LimiteNumerico());
        this.setEnabled(!espaco.isFixo());
        this.setVisible(true);

        if(espaco.isFixo()){
            Integer valorAtual = espaco.getAtual();
            if (valorAtual != null) {
                this.setText(valorAtual.toString());
            } else {
                this.setText("");  // Ou algum outro valor padrão, caso 'espaco.getAtual()' seja nulo
            }
        }

        this.getDocument().addDocumentListener(new DocumentListener() {
            private void MudarEspaco(){
                if(getText().isEmpty()){
                  espaco.limparEspaco();
                  return;  
                }
                espaco.setAtual(Integer.parseInt(getText()));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
               MudarEspaco();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                MudarEspaco();    
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                MudarEspaco();    
            }
        });
    }


    public void update(final EventEnum tipoEvento){
        if(tipoEvento.equals(LIMPAR_ESPACO) && this.isEnabled()){
            this.setText("");
        }
    }
}
