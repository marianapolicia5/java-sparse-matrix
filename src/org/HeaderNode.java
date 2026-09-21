package org;

/**
 * Representa um nó sentinela na estrutura de matriz esparsa.
 * Cada HeaderNode atua como a cabeça (início) de uma lista ligada de elementos
 * não nulos para uma linha (rowHead) e, potencialmente, para uma coluna (colHead).
 * Os HeaderNodes também são conectados em uma lista ligada circular para fácil navegação.
 */
public class HeaderNode {
    public int index;       // O índice da linha ou coluna que este sentinela representa.
    public DataNode rowHead; // O primeiro DataNode na lista de elementos da linha.
    public DataNode colHead; // O primeiro DataNode na lista de elementos da coluna.
    public HeaderNode next;  // Referência para o próximo HeaderNode na lista circular.

    /**
     * Construtor para um HeaderNode.
     *
     * @param index O índice (linha ou coluna) associado a este nó sentinela.
     */
    public HeaderNode(int index) {
        this.index = index;
        this.rowHead = null;
        this.colHead = null;
        this.next = null;
    }
}