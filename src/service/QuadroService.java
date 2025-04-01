package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Espaco;
import model.Quadro;
import model.StatusDoJogo;

public class QuadroService {
    private final static int LIMITE_DO_QUADRO = 9;
    private final Quadro quadro;

    public QuadroService(final Map<String, String> config) {
        this.quadro = new Quadro(initQuadro(config));
    }

    public List<List<Espaco>> getEspacos(){
        return this.quadro.getEspaco();
    }

    public void reset(){
        this.quadro.reset();
    }

    public boolean temErro(){
        
        for (List<Espaco> linha : quadro.getEspaco()) {
            for (Espaco espaco : linha) {
                if (espaco.getAtual() != null && espaco.getAtual() != espaco.getEsperado()) {
                    return true; // Encontrou um erro
                }
            }
        }
        
        return false;
    }

    
    public StatusDoJogo pegarStatusDoJogo(){
        return quadro.getStatusDoJogo();
    }

    public boolean jogoAcabou(){
        return this.quadro.jogoAcabou();
    }

    private List<List<Espaco>> initQuadro(final Map<String, String> config) { 
        List<List<Espaco>> espacos = new ArrayList<>();
        for (int i = 0; i < LIMITE_DO_QUADRO; i++) {
            espacos.add(new ArrayList<>());
            for (int j = 0; j < LIMITE_DO_QUADRO; j++) {
                var key = "%s,%s".formatted(i, j);
                var posicoesConfig = config.getOrDefault(key, "0,false"); // Valor padrão
                
                var parts = posicoesConfig.split(",");
                if (parts.length < 2) {
                    System.out.printf(" Erro ao ler a posição %s. Usando padrão: 0,false\n", key);
                    parts = new String[]{"0", "false"}; // Evita erro de acesso
                }

                var esperado = Integer.parseInt(parts[0]);
                var fixo = Boolean.parseBoolean(parts[1]);
                var espacoAtual = new Espaco(fixo, esperado);
                espacos.get(i).add(espacoAtual);
            }
        }
        return espacos;
    }
}
