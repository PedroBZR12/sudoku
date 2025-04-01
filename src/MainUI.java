
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.uicustom.tela.MainTela;

public class MainUI {
    public static void main(String[] args) {
        final var configJogo = Stream.of(args)
        .collect(Collectors.toMap(k -> k.split(";")[0], 
        v -> v.split(";")[1])); 
        var mainTela = new MainTela(configJogo);
        mainTela.construirTelaPrincipal();
    }
}
