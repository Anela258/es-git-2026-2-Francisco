# Atividade prática — MVC, parte 1: Visão e Controlador

Aplique a arquitetura Model-View-Controller ao sistema de biblioteca. O ponto de partida é a versão **v0.2**, resultado da atividade de princípios de projeto. Nela, `Biblioteca`, `Caixa`, `Livro`, `Usuario` (e subclasses) e `Emprestimo` já formam o **modelo**: guardam os dados e aplicam as regras sem fazer entrada/saída. A classe `Menu`, porém, acumula dois papéis: lê o teclado e imprime na tela (**visão**) e decide o que fazer com cada opção, chamando o modelo (**controlador**).

Entrega: o link do repositório com o código na lista abaixo:

- Integrante(s): Francisco e Noemi
  - Link do repositório: https://github.com/Noemi-Danfa/es-2026-2-main--3- 

- Integrante(s): Wesley
  - Link do repositório: https://github.com/srwesleyramos/es-2026-2-library

- Integrante(s): X, Y
  - Link do repositório: 

# 11/11/2026

## Exercício 0 — Preparação e caracterização

1. Copie a pasta `biblioteca-v0.2` deste repositório para o seu repositório.

2. Em um prompt de comando nesta pasta, compile e rode o roteiro, guardando a saída:

   ```powershell
   $arquivosJava = @((Get-ChildItem src -Filter *.java).FullName)
   javac -encoding UTF-8 -d out $arquivosJava
   Get-Content entrada.txt | java -cp out Main > saida-antes.txt
   ```

3. Abra `saida-antes.txt` e confira se ela contém a linha `[ID 4] - Dora [servidor]` e a linha `Total de multas arrecadadas: R$ -1.0`.
4. Faça o primeiro commit (sem a pasta `out/`): `git add src entrada.txt saida-antes.txt` e `git commit -m "Ponto de partida v0.2"`.

**Como verificar cada exercício:** depois de cada mudança, compile e rode de novo, gerando `saida-depois.txt`, e compare:

```powershell
$arquivosJava = @((Get-ChildItem src -Filter *.java).FullName)
javac -encoding UTF-8 -d out $arquivosJava
Get-Content entrada.txt | java -cp out Main > saida-depois.txt
Compare-Object (Get-Content saida-antes.txt) (Get-Content saida-depois.txt)
```

Se `Compare-Object` não imprimir nada, as saídas são idênticas. Nesta parte, todos os exercícios mudam a estrutura do código, não o comportamento: a comparação deve ficar vazia em todos eles.

## Exercício 1 — Visão: saídas

**Problema na v0.2:** `Menu` imprime diretamente com `System.out` e também decide o que mostrar. No MVC, as classes de **visão** cuidam da interação com o usuário (exibir dados e ler entradas) e não decidem o fluxo do programa. Comece levando as saídas para uma classe de visão.

**Passos:**

1. Crie `src/ConsoleView.java` (com `import java.util.List;`) e os métodos públicos abaixo:
   - `mostrarOpcoes()`: recebe o corpo atual de `Menu.imprimirOpcoes()`, sem alterações;
   - `mostrarMensagem(String mensagem)`: imprime a mensagem com `System.out.println`;
   - `mostrarLivros(List<Livro> livros)`: recebe o laço `for` de `Menu.listarLivros()`, percorrendo o parâmetro `livros`;
   - `mostrarUsuarios(List<Usuario> usuarios)`: recebe o laço `for` de `Menu.listarUsuarios()`, percorrendo o parâmetro `usuarios`;
   - `mostrarTotalMultas(double total)`: imprime `"Total de multas arrecadadas: R$ " + total`.
2. Em `Menu`, crie o atributo `private final ConsoleView view = new ConsoleView();`.
3. Em `Menu`, faça as substituições:
   - a chamada `imprimirOpcoes()` por `view.mostrarOpcoes()`;
   - as chamadas `listarLivros()` e `listarUsuarios()` por `view.mostrarLivros(consulta.getLivros())` e `view.mostrarUsuarios(consulta.getUsuarios())`;
   - a impressão da opção 5 por `view.mostrarTotalMultas(consulta.getTotalMultasArrecadadas())`;
   - cada `System.out.println(...)` restante por `view.mostrarMensagem(...)`, mantendo o mesmo argumento.
4. Apague de `Menu` os métodos `imprimirOpcoes`, `listarLivros` e `listarUsuarios`, que ficaram sem uso.
5. Busque `System.out` em `Menu.java`. Resultado esperado: uma única ocorrência, `System.out.print(rotulo);`, dentro de `lerInteiro`. Ela pertence à leitura e será tratada no Exercício 2.

**Verificação:** compile, rode o roteiro e compare. Resultado esperado: nenhuma diferença.

**Commit:** `Ex1: saidas na ConsoleView`. Troque o driver.

## Exercício 2 — Visão: entradas

**Problema:** `Menu` ainda usa `Scanner` para ler o teclado. Ler a entrada do usuário também é papel da visão.

**Passos:**

1. Mova o atributo `private final Scanner sc = new Scanner(System.in);` de `Menu` para `ConsoleView`, junto com `import java.util.Scanner;`.
2. Mova o método `lerInteiro(String rotulo)` de `Menu` para `ConsoleView` e torne-o `public`.
3. Em `ConsoleView`, crie o método que lê a opção do menu. A leitura da linha fica fora do `try`, como no `Menu` original:

   ```java
   // Devolve null quando a linha nao e um numero inteiro.
   public Integer lerOpcao() {
       String linha = sc.nextLine();
       try {
           return Integer.parseInt(linha);
       } catch (Exception e) {
           return null;
       }
   }
   ```

4. Em `Menu.executar`, substitua o trecho entre `view.mostrarOpcoes();` e o primeiro `if (opcao == 1)` por:

   ```java
   Integer lida = view.lerOpcao();
   if (lida == null) {
       view.mostrarMensagem("Opcao invalida.");
       continue;
   }
   opcao = lida;
   ```

5. Em `Menu`, troque cada chamada `lerInteiro(` por `view.lerInteiro(` e remova `import java.util.Scanner;`.
6. Busque `System.out` e `Scanner` em `Menu.java`. Resultado esperado: nenhuma ocorrência.

**Verificação:** compile, rode o roteiro e compare. Resultado esperado: nenhuma diferença.

**Commit:** `Ex2: entradas na ConsoleView`. Troque o driver.

## Exercício 3 — Controlador

**Problema:** o que restou em `Menu` é o papel de **controlador**: recebe a opção lida pela visão, pede ao modelo a consulta ou a operação correspondente e entrega o resultado à visão. O nome `Menu` não descreve mais esse papel, e a classe ainda cria a própria visão, em vez de recebê-la de quem monta o sistema.

**Passos:**

1. Renomeie o arquivo preservando o histórico: `git mv src/Menu.java src/BibliotecaController.java`. Renomeie a classe para `BibliotecaController`.
2. Em `BibliotecaController`:
   - troque a declaração do atributo por `private final ConsoleView view;`;
   - troque o construtor por `public BibliotecaController(ConsultaBiblioteca consulta, OperacoesBiblioteca operacoes, ConsoleView view)`, guardando o novo parâmetro no atributo.
3. Em `Main.main`, troque a linha que cria o `Menu` por:

   ```java
   ConsoleView view = new ConsoleView();
   new BibliotecaController(biblioteca, biblioteca, view).executar();
   ```

4. Compile, rode o roteiro e compare. Resultado esperado: nenhuma diferença.
5. Crie `fluxo-mvc.md` na raiz do repositório com a tabela abaixo preenchida. Ela descreve o que acontece, em ordem, quando o roteiro escolhe a opção 3 e informa um usuário e um livro válidos. Cada linha deve citar um método que existe no código.

   | Passo | O que acontece | Classe e método | Camada (Model, View ou Controller) |
   |---|---|---|---|
   | 1 | A opção digitada é lida | | |
   | 2 | A opção 3 é encaminhada para o tratamento do empréstimo | | |
   | 3 | O ID do usuário é lido | | |
   | 4 | A existência do usuário é verificada | | |
   | 5 | O ID do livro é lido | | |
   | 6 | As regras do empréstimo são aplicadas | | |
   | 7 | O resultado é exibido | | |

**Verificação:** a comparação está vazia e cada linha da tabela cita um método existente.

**Commit:** `Ex3: Menu vira BibliotecaController`.

## Verificação final e entrega

1. Compile e rode o roteiro uma última vez. A comparação com `saida-antes.txt` deve ser vazia.
2. Confira os critérios abaixo.
3. Envie o código com `git push` e informe o link do repositório.
