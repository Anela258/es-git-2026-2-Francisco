/*
 * VERSAO 0.2 - PRINCIPIOS DE PROJETO (Cap. 5, secao 5.6)
 * Livro deixa de ser String[] e guarda os proprios dados (ocultamento).
 * Disponibilidade e boolean, nao mais "sim"/"nao" (integridade conceitual).
 */
public class Livro {
    private static int proximoId = 1;

    private final int id;
    private final String titulo;
    private final String autor;
    private boolean disponivel;

    public Livro(String titulo, String autor) {
        this.id = proximoId++;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void emprestar() {
        disponivel = false;
    }

    public void devolver() {
        disponivel = true;
    }
}
