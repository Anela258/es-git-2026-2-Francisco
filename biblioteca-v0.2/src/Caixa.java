/*
 * VERSAO 0.2 - PRINCIPIOS DE PROJETO (Cap. 5, secao 5.6)
 * Caixa tem um unico motivo para mudar: a forma de registrar multas
 * (Responsabilidade Unica). Biblioteca usa um Caixa por composicao.
 */
public class Caixa {
    private double total = 0.0;

    public void registrar(double multa) {
        total += multa;
    }

    public double getTotal() {
        return total;
    }
}
