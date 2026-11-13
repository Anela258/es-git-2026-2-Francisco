import java.util.List;

/*
 * VERSAO 0.2 - PRINCIPIOS DE PROJETO (Cap. 5, secao 5.6)
 * Interface pequena so com consultas (Segregacao de Interfaces).
 */
public interface ConsultaBiblioteca {
    List<Livro> getLivros();

    List<Usuario> getUsuarios();

    double getTotalMultasArrecadadas();

    boolean existeUsuario(int id);
}
