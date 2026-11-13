/*
 * VERSAO 0.2 - PRINCIPIOS DE PROJETO (Cap. 5, secao 5.6)
 * Emprestimo liga um Usuario a um Livro. encerrar() devolve o livro e
 * calcula a multa: quem chama nao precisa navegar ate o Livro (Demeter).
 * Como na v0, atraso negativo produz multa negativa (comportamento preservado).
 */
public class Emprestimo {
    private final Usuario usuario;
    private final Livro livro;

    public Emprestimo(Usuario usuario, Livro livro) {
        this.usuario = usuario;
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public boolean refereSe(int idUsuario, int idLivro) {
        return usuario.getId() == idUsuario && livro.getId() == idLivro;
    }

    public double calcularMulta(int diasAtraso) {
        return diasAtraso * usuario.getMultaPorDia();
    }

    public double encerrar(int diasAtraso) {
        livro.devolver();
        return calcularMulta(diasAtraso);
    }
}
