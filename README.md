# Sparse Matrix Implementation in Java

A memory-efficient sparse matrix implementation in Java using dynamic linked data structures with sentinel header nodes, developed for the Algorithms and Data Structures (AED) course at ISMAT.

## Overview

A sparse matrix is a matrix where the vast majority of elements are zero. Storing such matrices using standard two-dimensional arrays results in excessive memory allocation. This project implements an optimized representation that dynamically allocates memory strictly for non-zero elements, linking them by row and column using sentinel header nodes.

## Tech Stack & Key Concepts

* **Language:** Java (JDK 17+)
* **Testing Framework:** JUnit 5 (TDD approach)
* **Paradigms:** Object-Oriented Programming (OOP), Test-Driven Development (TDD)
* **Core Concepts:** Dynamic Linked Lists, Sentinel Nodes, Memory Optimization, Time Complexity

## Architecture & Classes

The project is structured under the `org` package across three core classes:

* `DataNode`: Represents an individual non-zero element in the matrix. It stores matrix coordinates (`row`, `col`), the integer `value`, a pointer to the next element in the same row (`right`), and a pointer to the next element in the same column (`down`).
* `HeaderNode`: Represents sentinel nodes anchoring each row and column. Contains references to the first non-zero elements (`rowHead`, `colHead`) and links circular header pointers through the `next` reference.
* `SparseMatrix`: The primary controller handling matrix dimensions, the sentinel header array, value insertion, element lookup, algorithmic matrix operations, and input/output formatting.

## Implemented Operations

* **Value Retrieval (`getValue`):** Traverses the row linked list to find a value at `(row, col)`, returning `0` if omitted.
* **Ordered Insertion (`insert`):** Inserts or updates non-zero elements while preserving sorted ascending order across row and column lists. Zeros are discarded.
* **Matrix Addition (`add`):** Merges corresponding row lists of two matrices of matching dimensions using a two-pointer merge approach.
* **Scalar Multiplication (`multiplyByScalar`):** Multiplies non-zero values by an integer scalar, dropping elements that evaluate to zero.
* **Matrix Transposition (`transpose`):** Inverts row and column coordinates while maintaining the orthogonal pointer architecture.
* **Formatted Output (`printMatrix`):** Formats output sorted primarily by row index and secondarily by column index.

## Test-Driven Development (TDD)

The codebase was validated using automated JUnit 5 test suites:

* `DataNodeTest`: Verifies node constructors, property mutations, and pointer connections.
* `HeaderNodeTest`: Verifies sentinel initialization, circular header links, and head pointer assignments.
* `SparseMatrixTest`: Tests insertion, retrieval, edge cases, addition, scalar multiplication, transposition, and list conversions.

## Repository Structure

\`\`\`text
├── README.md
├── src/
│   └── org/
│       ├── DataNode.java
│       ├── HeaderNode.java
│       └── SparseMatrix.java
└── test/
    ├── DataNodeTest.java
    ├── HeaderNodeTest.java
    └── SparseMatrixTest.java
\`\`\`

## How to Run

### Compilation
From the project root:
\`\`\`bash
javac -d bin src/org/*.java
\`\`\`

### Execution
\`\`\`bash
java -cp bin org.SparseMatrix
\`\`\`

### Input Format
The program reads standard input in the following format:
\`\`\`text
rows cols non_zero_count
row col value
...
operation (+, *, t)
\`\`\`

#### Example
**Input:**
\`\`\`text
10 100 2
0 0 1
9 99 2
*
2
\`\`\`

**Output:**
\`\`\`text
Result:
10 100 2
0 0 2
9 99 4
\`\`\`

## Academic Context

* **Institution:** Instituto Superior Manuel Teixeira Gomes (ISMAT)
* **Degree:** BSc in Computer Science and Engineering (Licenciatura em Engenharia Informática)
* **Course:** Algorithms and Data Structures (AED)
* **Author:** Mariana Polícia

## Notes

* Source code comments and terminal prompts are written in Portuguese as developed for the academic curriculum at ISMAT. Documentation is provided in English for international review.