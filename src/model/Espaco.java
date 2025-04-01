package model;
public class Espaco {
    private final boolean fixo;
    private  Integer atual;
    private final Integer esperado;

    public Espaco(boolean fixo, Integer esperado) {
        this.fixo = fixo;
        this.esperado = esperado;
        this.atual = fixo ? esperado : null;
    }

    public Integer getAtual() {
        return atual;
    }

    public void setAtual(final Integer atual) {
        if(fixo) return;
        this.atual = atual;
    }

    public boolean isFixo() {
        return this.fixo;
    }

    public Integer getEsperado() {
        return esperado;
    }
    
    public void limparEspaco()
    {
        setAtual(null);
    }

        
}
