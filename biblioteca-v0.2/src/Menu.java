/*
 * VERSAO 0.2 - PRINCIPIOS DE PROJETO (Cap. 5, secao 5.6)
 *
 * Menu concentra toda a E/S de console usando a ConsoleView.
 * Depende apenas das interfaces ConsultaBiblioteca e OperacoesBiblioteca,
 * nunca da classe concreta Biblioteca (Inversao de Dependencias).
 */
public class Menu {
    private final ConsultaBiblioteca consulta;
    private final OperacoesBiblioteca operacoes;
    private final ConsoleView view;

    public Menu(ConsultaBiblioteca consulta, OperacoesBiblioteca operacoes, ConsoleView view) {
        this.consulta = consulta;
        this.operacoes = operacoes;
        this.view = view;
    }

    public void executar() {
        int opcao = -1;
        while (opcao != 0) {
            imprimirOpcoes();
            
            Integer lida = view.lerOpcao();
            if (lida == null) {
                view.mostrarMensagem("Opcao invalida.");
                continue;
            }
            opcao = lida;

            if (opcao == 1) {
                listarLivros();
            } else if (opcao == 2) {
                listarUsuarios();
            } else if (opcao == 3) {
                emprestar();
            } else if (opcao == 4) {
                devolver();
            } else if (opcao == 5) {
                view.mostrarMensagem("Total de multas arrecadadas: R$ " + consulta.getTotalMultasArrecadadas());
            } else if (opcao == 0) {
                view.mostrarMensagem("Ate logo.");
            } else {
                view.mostrarMensagem("Opcao invalida.");
            }
        }
    }

    private void imprimirOpcoes() {
        view.mostrarMensagem("");
        view.mostrarMensagem("=== BIBLIOTECA ===");
        view.mostrarMensagem("1 - Listar livros");
        view.mostrarMensagem("2 - Listar usuarios");
        view.mostrarMensagem("3 - Emprestar livro");
        view.mostrarMensagem("4 - Devolver livro");
        view.mostrarMensagem("5 - Relatorio de multas");
        view.mostrarMensagem("0 - Sair");
        view.mostrarMensagem("Opcao: ");
    }

    private void listarLivros() {
        for (Livro livro : consulta.getLivros()) {
            view.mostrarMensagem("[ID " + livro.getId() + "] - " + livro.getTitulo() + " (" + livro.getAutor() + ") ["
                    + (livro.isDisponivel() ? "disponivel" : "emprestado") + "]");
        }
    }

    private void listarUsuarios() {
        for (Usuario usuario : consulta.getUsuarios()) {
            view.mostrarMensagem("[ID " + usuario.getId() + "] - " + usuario.getNome() + " [" + usuario.getTipo() + "]");
        }
    }

    private void emprestar() {
        Integer idUsuario = lerInteiro("ID do usuario: ");
        if (idUsuario == null) {
            view.mostrarMensagem("ID invalido.");
            return;
        }
        if (!consulta.existeUsuario(idUsuario)) {
            view.mostrarMensagem("Usuario nao encontrado.");
            return;
        }
        Integer idLivro = lerInteiro("ID do livro: ");
        if (idLivro == null) {
            view.mostrarMensagem("ID invalido.");
            return;
        }
        view.mostrarMensagem(operacoes.emprestar(idUsuario, idLivro));
    }

    private void devolver() {
        Integer idUsuario = lerInteiro("ID do usuario: ");
        if (idUsuario == null) {
            view.mostrarMensagem("ID invalido.");
            return;
        }
        Integer idLivro = lerInteiro("ID do livro: ");
        if (idLivro == null) {
            view.mostrarMensagem("ID invalido.");
            return;
        }
        Integer diasAtraso = lerInteiro("Dias em atraso: ");
        if (diasAtraso == null) {
            diasAtraso = 0;
        }
        view.mostrarMensagem(operacoes.devolver(idUsuario, idLivro, diasAtraso));
    }

    private Integer lerInteiro(String rotulo) {
        return view.lerInteiro(rotulo);
    }
}