/*
 * VERSAO 0.2 - PRINCIPIOS DE PROJETO (Cap. 5, secao 5.6)
 * Main monta o sistema. E o unico lugar que conhece a classe concreta
 * Biblioteca e os tipos concretos de usuario (Inversao de Dependencias).
 */
public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        seed(biblioteca);
	ConsoleView view = new ConsoleView();
        new Menu(biblioteca, biblioteca, view).executar();
    }

    private static void seed(Biblioteca biblioteca) {
        biblioteca.cadastrarLivro("Engenharia de Software Moderna", "Marco Tulio");
        biblioteca.cadastrarLivro("Clean Code", "Robert Martin");
        biblioteca.cadastrarLivro("Refactoring", "Martin Fowler");
        biblioteca.cadastrarLivro("O Cortico", "Aluisio Azevedo");
        biblioteca.adicionarUsuario(new Aluno("Ana"));
        biblioteca.adicionarUsuario(new Professor("Prof. Bruno"));
        biblioteca.adicionarUsuario(new Comunidade("Carlos"));
        biblioteca.adicionarUsuario(new Servidor("Dora"));
    }
}
