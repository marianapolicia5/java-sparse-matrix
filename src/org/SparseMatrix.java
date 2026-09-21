package org;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;

/**
 * Classe principal para representar e manipular matrizes esparsas.
 * Utiliza uma estrutura de dados baseada em nós sentinela e listas ligadas
 * para armazenar apenas os elementos não nulos, otimizando o uso de memória.
 */
public class SparseMatrix {
    int rows; // Número total de linhas da matriz.
    int cols; // Número total de colunas da matriz.
    HeaderNode[] headers; // Array de nós sentinela que gerenciam as linhas e colunas.

    /**
     * Construtor da matriz esparsa.
     * Inicializa a matriz com as dimensões especificadas, criando os nós sentinela
     * e estabelecendo uma estrutura circular entre eles.
     *
     * @param rows O número de linhas da matriz.
     * @param cols O número de colunas da matriz.
     */
    public SparseMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        // O tamanho do array de sentinelas é o maior entre rows e cols.
        int size = Math.max(rows, cols);
        headers = new HeaderNode[size];

        for (int i = 0; i < size; i++) {
            headers[i] = new HeaderNode(i);
        }

        // Conecta os HeaderNodes para formar uma lista ligada circular.
        for (int i = 0; i < size; i++) {
            headers[i].next = headers[(i + 1) % size];
        }
    }

    /**
     * Retorna o valor de um elemento na posição (row, col) da matriz.
     * Se o elemento não for encontrado (ou seja, é um zero implícito), retorna 0.
     *
     * @param row O índice da linha (0-based).
     * @param col O índice da coluna (0-based).
     * @return O valor do elemento na posição especificada, ou 0 se não for um elemento não nulo.
     * @throws IllegalArgumentException Se os índices estiverem fora dos limites da matriz.
     */
    public int getValue(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Índices fora dos limites.");
        }

        // Percorre a lista ligada da linha para encontrar o DataNode correspondente.
        DataNode current = headers[row].rowHead;
        while (current != null) {
            if (current.col == col) {
                return current.value;
            }
            current = current.right;
        }
        return 0; // Elemento não encontrado, portanto é zero.
    }

    /**
     * Insere (ou atualiza) um valor não nulo na matriz.
     * Se o valor for 0, a operação é ignorada.
     * O nó de dados é inserido nas listas ligadas da linha e da coluna correspondentes,
     * mantendo-as ordenadas por coluna (para linhas) e por linha (para colunas).
     * Se um elemento já existe na posição, seu valor é atualizado.
     *
     * @param row O índice da linha do elemento.
     * @param col O índice da coluna do elemento.
     * @param value O valor a ser inserido.
     * @throws IllegalArgumentException Se os índices estiverem fora dos limites da matriz.
     */
    public void insert(int row, int col, int value) {
        if (value == 0) return; // Ignora a inserção de valores nulos.

        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Índices fora dos limites: (" + row + ", " + col + ")");
        }

        DataNode newNode = new DataNode(row, col, value);

        // --- Inserção na lista da linha (ordenada por coluna) ---
        DataNode prevRow = null;
        DataNode currentRow = headers[row].rowHead;
        while (currentRow != null && currentRow.col < col) {
            prevRow = currentRow;
            currentRow = currentRow.right;
        }

        // Se o elemento já existe nesta posição da linha, atualiza o valor.
        if (currentRow != null && currentRow.col == col) {
            currentRow.value = value;
            return;
        }

        // Insere o novo nó na lista da linha.
        if (prevRow == null) {
            newNode.right = headers[row].rowHead;
            headers[row].rowHead = newNode;
        } else {
            newNode.right = prevRow.right;
            prevRow.right = newNode;
        }

        // --- Inserção na lista da coluna (ordenada por linha) ---
        DataNode prevCol = null;
        DataNode currentCol = headers[col].colHead;
        while (currentCol != null && currentCol.row < row) {
            prevCol = currentCol;
            currentCol = currentCol.down;
        }

        // Se o elemento já existe nesta posição da coluna, atualiza o valor.
        if (currentCol != null && currentCol.row == row) {
            currentCol.value = value;
            return;
        }

        // Insere o novo nó na lista da coluna.
        if (prevCol == null) {
            newNode.down = headers[col].colHead;
            headers[col].colHead = newNode;
        } else {
            newNode.down = prevCol.down;
            prevCol.down = newNode;
        }
    }

    /**
     * Calcula e retorna a transposta da matriz.
     *
     * @return Uma nova SparseMatrix que é a transposta da matriz atual.
     */
    public SparseMatrix transpose() {
        // A matriz transposta terá as dimensões invertidas.
        SparseMatrix transposed = new SparseMatrix(cols, rows);

        // Percorre todos os elementos não nulos da matriz original e os insere na transposta com índices trocados.
        for (int i = 0; i < rows; i++) {
            DataNode current = headers[i].rowHead;
            while (current != null) {
                transposed.insert(current.col, current.row, current.value);
                current = current.right;
            }
        }
        return transposed;
    }

    /**
     * Multiplica todos os elementos não nulos da matriz por um valor escalar.
     * Retorna uma nova matriz esparsa com os resultados.
     * Elementos que se tornam zero após a multiplicação não são armazenados.
     *
     * @param scalar O valor pelo qual os elementos serão multiplicados.
     * @return Uma nova SparseMatrix contendo o resultado da multiplicação.
     */
    public SparseMatrix multiplyByScalar(int scalar) {
        SparseMatrix result = new SparseMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            DataNode current = headers[i].rowHead;
            while (current != null) {
                int multipliedValue = current.value * scalar;
                if (multipliedValue != 0) { // Insere apenas se o valor resultante não for zero.
                    result.insert(i, current.col, multipliedValue);
                }
                current = current.right;
            }
        }
        return result;
    }

    /**
     * Soma esta matriz com outra matriz esparsa.
     * As matrizes devem ter as mesmas dimensões.
     * O processo envolve um "merge" das listas de linha de ambas as matrizes.
     * Se a soma de elementos na mesma posição for zero, o elemento não é incluído.
     *
     * @param other A outra matriz esparsa a ser somada.
     * @return Uma nova SparseMatrix que é o resultado da soma.
     */
    public SparseMatrix add(SparseMatrix other) {
        // Validação de dimensões.
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("As matrizes devem ter as mesmas dimensões para serem somadas.");
        }

        SparseMatrix result = new SparseMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            DataNode a = this.headers[i].rowHead;
            DataNode b = other.headers[i].rowHead;

            // Percorre ambas as listas de linha, como um merge de listas ligadas ordenadas.
            while (a != null || b != null) {
                if (b == null || (a != null && a.col < b.col)) {
                    // O elemento 'a' está presente e vem antes de 'b', ou 'b' já terminou.
                    result.insert(i, a.col, a.value);
                    a = a.right;
                } else if (a == null || b.col < a.col) {
                    // O elemento 'b' está presente e vem antes de 'a', ou 'a' já terminou.
                    result.insert(i, b.col, b.value);
                    b = b.right;
                } else { // a.col == b.col (ambos os elementos existem na mesma posição).
                    int sum = a.value + b.value;
                    if (sum != 0) { // Insere a soma apenas se não for zero.
                        result.insert(i, a.col, sum);
                    }
                    a = a.right;
                    b = b.right;
                }
            }
        }
        return result;
    }

    /**
     * Converte a matriz esparsa para uma lista de arrays de inteiros.
     * Cada array representa um elemento não nulo no formato [linha, coluna, valor].
     *
     * @return Uma lista de todos os elementos não nulos da matriz.
     */
    public List<int[]> toList() {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            DataNode current = headers[i].rowHead;
            while (current != null) {
                list.add(new int[]{current.row, current.col, current.value});
                current = current.right;
            }
        }
        return list;
    }

    /**
     * Imprime a matriz no formato especificado:
     * - Primeira linha: número de linhas, número de colunas, número de elementos não nulos.
     * - Linhas seguintes: cada elemento não nulo (linha, coluna, valor),
     * ordenado primeiro por linha e depois por coluna.
     */
    public void printMatrix() {
        List<int[]> elements = toList();

        // Ordena os elementos para a saída: primeiro por linha, depois por coluna.
        elements.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] e1, int[] e2) {
                if (e1[0] != e2[0]) {
                    return Integer.compare(e1[0], e2[0]); // Compara as linhas.
                }
                return Integer.compare(e1[1], e2[1]); // Se as linhas são iguais, compara as colunas.
            }
        });

        System.out.println(rows + " " + cols + " " + elements.size());
        for (int[] e : elements) {
            System.out.println(e[0] + " " + e[1] + " " + e[2]);
        }
    }

    /**
     * Lê uma matriz esparsa do console de vídeo, usando o formato definido.
     *
     * @param sc O objeto Scanner para ler a entrada.
     * @return Uma nova SparseMatrix preenchida com os dados lidos.
     */
    public static SparseMatrix readMatrix(Scanner sc) {
        int r = sc.nextInt();
        int c = sc.nextInt();
        int n = sc.nextInt();
        SparseMatrix matrix = new SparseMatrix(r, c);
        for (int i = 0; i < n; i++) {
            int row = sc.nextInt();
            int col = sc.nextInt();
            int val = sc.nextInt();
            matrix.insert(row, col, val);
        }
        return matrix;
    }

    /**
     * Método principal que executa o programa.
     * Lê uma matriz inicial, uma operação (+, *, t) e, dependendo da operação,
     * lê outra matriz ou um escalar. Realiza a operação e imprime o resultado.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SparseMatrix m1 = readMatrix(sc);
        String operation = sc.next();

        switch (operation) {
            case "+":
                SparseMatrix m2 = readMatrix(sc);
                System.out.println("Result:");
                m1.add(m2).printMatrix();
                break;
            case "*":
                int scalar = sc.nextInt();
                System.out.println("Result:");
                m1.multiplyByScalar(scalar).printMatrix();
                break;
            case "t":
                System.out.println("Result:");
                m1.transpose().printMatrix();
                break;
            default:
                System.out.println("Operação inválida.");
        }
        sc.close();
    }
}