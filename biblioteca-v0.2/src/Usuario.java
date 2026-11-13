/*
 * VERSAO 0.2 - PRINCIPIOS DE PROJETO (Cap. 5, secao 5.6)
 * Usuario guarda id e nome. As regras que variam por tipo (limite e multa)
 * ficam escondidas em cada subclasse: ninguem fora delas conhece os valores.
 */
public abstract class Usuario {
    private static int proximoId = 1;

    private final int id;
    private final String nome;

    public Usuario(String nome) {
        this.id = proximoId++;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public abstract String getTipo();

    public abstract int getLimiteEmprestimos();

    public abstract double getMultaPorDia();
}
