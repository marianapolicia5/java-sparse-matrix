package org;

/**
 * Representa um nó de dados que armazena um elemento não nulo em uma matriz esparsa.
 * Cada DataNode contém as coordenadas (linha e coluna) e o valor do elemento.
 * Possui ponteiros para o próximo elemento não nulo na mesma linha (`right`)
 * e na mesma coluna (`down`), facilitando a travessia da matriz.
 */
public class DataNode {
    public int row;   // O índice da linha do elemento.
    public int col;   // O índice da coluna do elemento.
    public int value; // O valor do elemento.
    public DataNode right; // Ponteiro para o próximo DataNode na mesma linha.
    public DataNode down;  // Ponteiro para o próximo DataNode na mesma coluna.

    /**
     * Construtor para um DataNode.
     *
     * @param row O índice da linha do elemento.
     * @param col O índice da coluna do elemento.
     * @param value O valor do elemento.
     */
    public DataNode(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.right = null;
        this.down = null;
    }
}