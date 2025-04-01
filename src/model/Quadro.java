package model;

import java.util.Collection;
import java.util.List;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static model.StatusDoJogo.COMPLETO;
import static model.StatusDoJogo.INCOMPLETO;
import static model.StatusDoJogo.NAO_INICIADO;

public class Quadro {
    private final List<List<Espaco>> espaco;

    public Quadro(final List<List<Espaco>> espaco)
    {
        this.espaco = espaco;
    }

    public List<List<Espaco>> getEspaco() {
        return espaco;
    }

    public StatusDoJogo getStatusDoJogo()
    {
        if(espaco.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixo() && nonNull(s.getAtual()))){
            return NAO_INICIADO;
        }

        return espaco.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getAtual())) ? INCOMPLETO : COMPLETO;

    }

    public boolean temErro(){
        if(getStatusDoJogo() == NAO_INICIADO)
        {
            return false;
        }
        return espaco.stream().flatMap(Collection::stream).anyMatch(s -> nonNull(s.getAtual()) && !s.getAtual().equals(s.getEsperado()));
    }


    public boolean mudarValor(final int coluna, final int linha, final Integer valor){
        var posicao = espaco.get(coluna).get(linha);
        if(posicao.isFixo()){
            return false;
        }
        
        posicao.setAtual(valor);
        return true;
    }
    public boolean valorLimpo(final int coluna, final int linha){
        var posicao = espaco.get(coluna).get(linha);
        if(posicao.isFixo()){
            return false;
        }

        posicao.limparEspaco();
        return true;
    }

    public void reset(){
        espaco.forEach(s -> s.forEach(Espaco::limparEspaco));
    }

    public boolean jogoAcabou()
    {
        return !temErro() && getStatusDoJogo() == COMPLETO;
    }
}

