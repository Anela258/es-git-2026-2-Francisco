/*
 * VERSAO 0.2 - PRINCIPIOS DE PROJETO (Cap. 5, secao 5.6)
 * Interface pequena so com as operacoes que alteram o estado (Segregacao de Interfaces).
 */
public interface OperacoesBiblioteca {
    String emprestar(int idUsuario, int idLivro);

    String devolver(int idUsuario, int idLivro, int diasAtraso);
}
