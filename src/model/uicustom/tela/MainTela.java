package model.uicustom.tela;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.JPanel;
import model.Espaco;
import model.uicustom.BotaoChecarStatusDoJogo;
import model.uicustom.BotaoFinalizar;
import model.uicustom.BotaoReset;
import model.uicustom.frame.MainFrame;
import model.uicustom.frame.PainelPrincipal;
import model.uicustom.frame.Sudoku;
import model.uicustom.input.TextoNumerico;
import static service.EventEnum.LIMPAR_ESPACO;
import service.Notificacao;
import service.QuadroService;

public class MainTela {
    private final static Dimension dimensao = new Dimension(600, 600);
    private final QuadroService quadroService;
    private final Notificacao notificacao;

    private JButton finalizarButton;
    private JButton checarEstadoDoJogButton;
    private JButton resetButton;
   
   
   
    public MainTela(final Map<String, String> config) {
        this.quadroService = new QuadroService(config);
        this.notificacao = new Notificacao();
    }

    public void construirTelaPrincipal(){
        JPanel painelPrincipal = new PainelPrincipal(dimensao);
        JFrame framePrincipal = new MainFrame(dimensao, painelPrincipal);
        for(int r = 0; r < 9; r = r + 3)
        {
            var fimDaLinha = r + 2;
            for(int c = 0; c < 9; c = c + 3)
            {
                var fimDaColuna = c + 2;
                var espacos = getEspacosDaSecao(quadroService.getEspacos(), c, fimDaColuna, r, fimDaLinha);
                painelPrincipal.add(gerarSecao(espacos));
            }
        }
        addResetButton(painelPrincipal);
        addChecarStatusDoJogoButton(painelPrincipal);
        addFinalizarButton(painelPrincipal);
        framePrincipal.revalidate();
        framePrincipal.repaint();
    }

    private List<Espaco> getEspacosDaSecao(final List<List<Espaco>> espaco, final int initColuna, final int endColuna, final int initLinha, final int endLinha){
        List<Espaco> secaoEspaco = new ArrayList<>();

        for(int r = initLinha; r <= endLinha; r++){
            for(int c = initColuna; c <= endColuna; c++){
                secaoEspaco.add(espaco.get(c).get(r));
            }
        }
        return secaoEspaco;
    }


    private JPanel gerarSecao(final List<Espaco> espaco){
        List<TextoNumerico> campo = new ArrayList<>(espaco.stream().map(TextoNumerico::new).toList());
        campo.forEach(t -> notificacao.subscriber(LIMPAR_ESPACO, t));
        return new Sudoku(campo);
    }
    private void addFinalizarButton(JPanel painelPrincipal) {
        finalizarButton = new BotaoFinalizar(e ->{
            if(quadroService.jogoAcabou()){
                JOptionPane.showMessageDialog(null, "Parabéns! Você concluiu o jogo!");
                resetButton.setEnabled(false);
                checarEstadoDoJogButton.setEnabled(false);
                finalizarButton.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(null, "Seu jogo tem algum erro, arrume e tente novamente!");
            }
        });
        painelPrincipal.add(finalizarButton);
    }

    private void addChecarStatusDoJogoButton(JPanel painelPrincipal) {
            checarEstadoDoJogButton = new BotaoChecarStatusDoJogo(e ->{
            var temErro = quadroService.temErro();
            var statusDoJogo = quadroService.pegarStatusDoJogo();
            var mensagem = switch (statusDoJogo){
                case NAO_INICIADO -> "O jogo não foi iniciado";
                case INCOMPLETO -> "O jogo está incompleto";
                case COMPLETO -> "O jogo está completo";
            };
                mensagem += temErro ? " e contém erro" : " não contém erro";
                JOptionPane.showMessageDialog(null, mensagem);
            });
        painelPrincipal.add(checarEstadoDoJogButton);    
    }

    private void addResetButton(JPanel painelPrincipal) {
        resetButton = new BotaoReset(e ->{
            var confirmar = JOptionPane.showConfirmDialog(null, 
            "Deseja realmente reiniciar o jogo?",
             "Limpar o jogo", 
             YES_NO_OPTION, 
             QUESTION_MESSAGE);
             if(confirmar == 0){
                quadroService.reset();
                notificacao.notificar(LIMPAR_ESPACO);
             }
        });
        painelPrincipal.add(resetButton);    
    }
}
