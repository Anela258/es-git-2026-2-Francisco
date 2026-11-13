import java.util.Scanner;

public class ConsoleView {
    private final Scanner sc = new Scanner(System.in);

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public Integer lerOpcao() {
        String linha = sc.nextLine();
        try {
            return Integer.parseInt(linha);
        } catch (Exception e) {
            return null;
        }
    }

    public int lerInteiro(String rotulo) {
        mostrarMensagem(rotulo);
        while (true) {
            Integer lida = lerOpcao();
            if (lida != null) {
                return lida;
            }
            mostrarMensagem("Valor inválido. Tente novamente: ");
        }
    }
}