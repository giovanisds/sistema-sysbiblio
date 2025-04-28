import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    public static final int ANO_PUBLICACAO_MINIMO = 1400;

    private List<Livro> acervo = new ArrayList<>();

    public Livro adicionar(Livro novoLivro) throws Exception {
        if (novoLivro.getTitulo() == null || novoLivro.getTitulo().isEmpty())
            throw new Exception("Título inválido!");

        if (novoLivro.getAnoPublicacao() < ANO_PUBLICACAO_MINIMO)
            throw new Exception("Ano de publicação inválido! Deve ser maior ou igual a " + ANO_PUBLICACAO_MINIMO);

        if (novoLivro.getNumeroPaginas() <= 0)
            throw new Exception("Número de páginas deve ser maior que zero!");

        for (Livro livroExistente : acervo) {
            if (livroExistente.getTitulo().equalsIgnoreCase(novoLivro.getTitulo())) {
                throw new Exception("Já existe um livro com este título!");
            }
        }

        acervo.add(novoLivro);
        return novoLivro;
    }

    public List<Livro> pesquisarPorTitulo(String titulo) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarTodos() {
        return acervo;
    }

    public int removerPorTitulo(String titulo) {
        List<Livro> livrosParaRemover = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                livrosParaRemover.add(livro);
            }
        }
        acervo.removeAll(livrosParaRemover);
        return livrosParaRemover.size();
    }

    public List<Livro> buscarPorAutor(String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    public List<Livro> listarPorTipo(boolean listarFisicos) {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : acervo) {
            if (listarFisicos && livro instanceof LivroFisico) {
                resultado.add(livro);
            } else if (!listarFisicos && livro instanceof LivroDigital) {
                resultado.add(livro);
            }
        }
        return resultado;
    }
}
