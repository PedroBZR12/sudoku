
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Espaco;
import model.Quadro;
import static util.QuadroTemplate.QUADRO_TEMPLATE;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static Quadro quadro;
    private final static int LIMITE_DO_QUADRO = 9;
    public static void main(String[] args) throws Exception {
        final var posicoes = Stream.of(args)
        .collect(Collectors.toMap(k -> k.split(";")[0], 
        v -> v.split(";")[1])); 

        var opcao = -1;
        while(true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    comecarJogo(posicoes);
                    break;
                case 2:
                    colocarNumero();
                    break;
                case 3:
                    removerNumero();
                    break;
                case 4:
                    mostrarJogoAtual();
                    break;
                case 5:
                    verificarStatus();
                    break;
                case 6:
                    limparJogo();
                    break;
                case 7:
                    finalizarJogo();
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcao inválida.");
                    break;
            }
        }
    }
    
    private static void comecarJogo(Map<String, String> posicoes) {
        if (nonNull(quadro)) {
            System.out.println("O jogo já foi iniciado.");
            return;
        }
    
        List<List<Espaco>> espacos = new ArrayList<>();
    
        for (int i = 0; i < LIMITE_DO_QUADRO; i++) {
            espacos.add(new ArrayList<>());
    
            for (int j = 0; j < LIMITE_DO_QUADRO; j++) {
                String chave = String.format("%d,%d", i, j);
                String posicaoConfig = posicoes.get(chave);
    
                if (posicaoConfig == null) {
                    System.out.printf("Posição [%d,%d] não encontrada, assumindo (0, false)\n", i, j);
                    posicaoConfig = "0,false"; 
                }
    
                
                String[] valores = posicaoConfig.split(",");
    
                if (valores.length != 2) {
                    System.out.printf("Erro ao processar posição [%d,%d], assumindo (0, false)\n", i, j);
                    valores = new String[]{"0", "false"};
                }
    
                int esperado = Integer.parseInt(valores[0]);
                boolean fixo = Boolean.parseBoolean(valores[1]);
    
                Espaco espacoAtual = new Espaco(fixo, esperado);
                espacos.get(i).add(espacoAtual);
            }
        }
    
        quadro = new Quadro(espacos);
        System.out.println("O jogo está pronto para começar.");
    }
    
        
    
    private static void finalizarJogo() {
        if(isNull(quadro)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        if(quadro.jogoAcabou()){
            System.out.println("Parabéns! Você conseguiu concluir!");
            mostrarJogoAtual();
            quadro = null;
        }
        else if(quadro.temErro()){
            System.out.println("Seu jogo tem algum erro, confira e tente novamente!");
        }
        else{
            System.out.println("Espaços em branco!!");
        }
    }
    private static void limparJogo() {
        if(isNull(quadro)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Tem certeza que deseja limpar seu jogo e perder o seu progresso?");
        var confirmar = scanner.next();
        while(!confirmar.equalsIgnoreCase("sim") && !confirmar.equalsIgnoreCase("nao")){
            System.out.println("Informe 'sim' ou 'nao'");
            confirmar = scanner.next();
        }

        if(confirmar.equalsIgnoreCase("sim")){
            quadro.reset();
        }
    }
    private static void colocarNumero() {
        if(isNull(quadro)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        System.out.println("Informe a coluna que deseja inserir o número: ");
        var coluna = conseguirNumeroValido(0, 8);
        System.out.println("Informe a linha que deseja inserir o número: ");
        var linha = conseguirNumeroValido(0, 8);
        System.out.printf("Informe o número que vai ser inserido na posição: %s %s", coluna, linha);
        var valor = conseguirNumeroValido(1, 9);
        if(!quadro.mudarValor(coluna, linha, valor)){
            System.out.printf("A posição [%s, %s] tem um valor fixo\n", coluna, linha);
        }
    }
    private static void removerNumero() {
        if(isNull(quadro)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        System.out.println("Informe a coluna que deseja remover o número: ");
        var coluna = conseguirNumeroValido(0, 8);
        System.out.println("Informe a linhaa que deseja remover o número: ");
        var linha = conseguirNumeroValido(0, 8);
        if(!quadro.valorLimpo(coluna, linha)){
            System.out.printf("A posição [%s, %s] tem um número fixo.", coluna, linha);
        }
    }
  
        private static void mostrarJogoAtual() {
            if (isNull(quadro)) {
                System.out.println("O jogo ainda não foi iniciado");
                return;
            }
        
            System.out.println("\nTabuleiro Atual:");
            
            // Imprimir o tabuleiro linha por linha
            for (int i = 0; i < LIMITE_DO_QUADRO; i++) {
                for (int j = 0; j < LIMITE_DO_QUADRO; j++) {
                    Espaco espaco = quadro.getEspaco().get(i).get(j);
                    
                    Integer valorParaExibir = espaco.isFixo() ? espaco.getEsperado() : espaco.getAtual();
                    
                    }
                System.out.println();
            }
        
            System.out.println("\nRepresentação formatada:");
            
          
            var args = new Object[81];
            var argsPos = 0;
        
            for (int i = 0; i < LIMITE_DO_QUADRO; i++) {
                for (var col : quadro.getEspaco()) {
                    Integer valorParaExibir = col.get(i).isFixo() ? col.get(i).getEsperado() : col.get(i).getAtual();
                    args[argsPos++] = " " + (valorParaExibir == null ? " " : valorParaExibir);
                }
            }
        
            System.out.printf((QUADRO_TEMPLATE) + "%n", args);
        }
        
    
    private static void verificarStatus() {
        if(isNull(quadro)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.printf("O jogo atualmente se encontra no status [%s]\n", quadro.getStatusDoJogo().getLabel());
        if(quadro.temErro())
        {
            System.out.println("O jogo contém erros");
        }else{
            System.out.println("O jogo não contém erros");
        }
    }

    private static int conseguirNumeroValido(final int min, final int max){
        var atual = scanner.nextInt();
        while ((atual < min || atual > max)) { 
            atual = scanner.nextInt();
            System.out.printf("Informe um número entre %s e %s", min, max);
        }

        return atual;
    }
}
