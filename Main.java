import java.util.List;
import java.util.Scanner;

public class Main {
    private static Biblioteca biblio = new Biblioteca();

    public static void main(String[] args) {
        String menu = """
                ====== SYSBIBLIO ======
                Escolha uma das opções abaixo:
                1 - Adicionar novo livro
                2 - Pesquisar livro por título
                3 - Listar todos os livros
                4 - Remover livro por título
                5 - Listar livros físicos ou digitais
                0 - Sair
                """;
        int opcao;
        Scanner lerTeclado = new Scanner(System.in);
        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            opcao = inputNumerico(lerTeclado, menu);
            switch (opcao) {
                case 1:
                    adicionar(lerTeclado);
                    break;
                case 2:
                    pesquisarPorTitulo(lerTeclado);
                    break;
                case 3:
                    pesquisarTodos();
                    break;
                case 4:
                    removerPorTitulo(lerTeclado);
                    break;
                case 5:
                    listarPorTipo(lerTeclado);
                    break;
                case 0:
                    System.out.println("Encerrando programa ...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (opcao != 0);
        lerTeclado.close();
    }

    private static void adicionar(Scanner lerTeclado) {
        System.out.println("Digite o título do livro:");
        String titulo = lerTeclado.nextLine();
        System.out.println("Digite o autor do livro:");
        String autor = lerTeclado.nextLine();
        int anoPublicacao = inputNumerico(lerTeclado, "Digite o ano da publicação:");
        int numeroPaginas = inputNumerico(lerTeclado, "Digite o número de páginas:");

        Livro novoLivro;

        int tipoLivro = inputNumerico(lerTeclado, "Qual o tipo do livro: 1-Físico, 2-Digital");
        if (tipoLivro == 1) {
            novoLivro = new LivroFisico();
            System.out.println("Digite as dimensões do livro:");
            String dimensoes = lerTeclado.nextLine();
            int numeroExemplares = inputNumerico(lerTeclado, "Digite o número de exemplares:");

            LivroFisico novoLivroFisico = (LivroFisico) novoLivro;
            novoLivroFisico.setDimensoes(dimensoes);
            novoLivroFisico.setNumeroExemplares(numeroExemplares);
        } else {
            novoLivro = new LivroDigital();
            System.out.println("Digite o formato do arquivo:");
            String formatoArquivo = lerTeclado.nextLine();

            LivroDigital novoLivroDigital = (LivroDigital) novoLivro;
            novoLivroDigital.setFormatoArquivo(formatoArquivo);
        }

        novoLivro.setTitulo(titulo);
        novoLivro.setAutor(autor);
        novoLivro.setAnoPublicacao(anoPublicacao);
        novoLivro.setNumeroPaginas(numeroPaginas);

        try {
            biblio.adicionar(novoLivro);
            System.out.println("Livro adicionado com Sucesso!");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        esperarEnter(lerTeclado);
    }

    private static void pesquisarPorTitulo(Scanner lerTeclado) {
        System.out.println("Digite o título que deseja pesquisar:");
        String titulo = lerTeclado.nextLine();
        List<Livro> livros = biblio.pesquisarPorTitulo(titulo);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado com esse título.");
        } else {
            System.out.println("Livros encontrados:");
            for (Livro livro : livros) {
                System.out.println(livro.toString());
                System.out.println("Formato: " + livro.getFormato());
                System.out.println("Tempo de publicação: " + livro.calcularTempoPublicacao() + " anos\n");
            }
        }

        esperarEnter(lerTeclado);
    }

    private static void pesquisarTodos() {
        List<Livro> livros = biblio.pesquisarTodos();
        if (livros.isEmpty()) {
            System.out.println("NENHUM LIVRO CADASTRADO");
        } else {
            System.out.println("Livros Cadastrados:");
            for (Livro livro : livros) {
                System.out.println(livro.toString());
                System.out.println("Formato: " + livro.getFormato());
                System.out.println("Tempo de publicação: " + livro.calcularTempoPublicacao() + " anos\n");
            }
        }

        Scanner lerTeclado = new Scanner(System.in);
        esperarEnter(lerTeclado);
    }

    private static void removerPorTitulo(Scanner lerTeclado) {
        System.out.println("Digite o título que deseja remover:");
        String titulo = lerTeclado.nextLine();
        int removidos = biblio.removerPorTitulo(titulo);
        System.out.println(removidos + " livro(s) removido(s).");

        esperarEnter(lerTeclado);
    }

    private static void listarPorTipo(Scanner lerTeclado) {
        int tipo = inputNumerico(lerTeclado, "Digite 1 para listar livros Físicos ou 2 para listar livros Digitais:");
        boolean listarFisicos = tipo == 1;
        List<Livro> livros = biblio.listarPorTipo(listarFisicos);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado desse tipo.");
        } else {
            System.out.println("Livros encontrados:");
            for (Livro livro : livros) {
                System.out.println(livro.toString());
                System.out.println("Formato: " + livro.getFormato());
                System.out.println("Tempo de publicação: " + livro.calcularTempoPublicacao() + " anos\n");
            }
        }

        esperarEnter(lerTeclado);
    }

    private static int inputNumerico(Scanner lerTeclado, String mensagem) {
        int valor = 0;
        boolean entradaValida = false;
        System.out.println(mensagem);
        do {
            String valorStr = lerTeclado.nextLine();
            try {
                valor = Integer.parseInt(valorStr);
                entradaValida = true;
            } catch (Exception e) {
                System.out.println("Erro. Por favor informe um número inteiro.");
            }
        } while (!entradaValida);
        return valor;
    }

    private static void esperarEnter(Scanner lerTeclado) {
        System.out.println("\nPressione Enter para continuar...");
        lerTeclado.nextLine();
    }
}
