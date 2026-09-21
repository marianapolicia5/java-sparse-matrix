
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.SparseMatrix;

public class SparseMatrixTest {

    @Test
    void testInsertAndGetValue() {
        SparseMatrix matrix = new SparseMatrix(4, 4);
        matrix.insert(1, 2, 5);
        matrix.insert(3, 3, 9);
        matrix.insert(0, 0, 1);

        assertEquals(5, matrix.getValue(1, 2));
        assertEquals(9, matrix.getValue(3, 3));
        assertEquals(1, matrix.getValue(0, 0));
        assertEquals(0, matrix.getValue(2, 2)); // não existe
    }

    @Test
    void testAddition() {
        SparseMatrix a = new SparseMatrix(2, 2);
        a.insert(0, 0, 2);
        a.insert(1, 1, 4);

        SparseMatrix b = new SparseMatrix(2, 2);
        b.insert(0, 0, -2);
        b.insert(1, 0, 3);

        SparseMatrix result = a.add(b);

        assertEquals(0, result.getValue(0, 0));
        assertEquals(4, result.getValue(1, 1));
        assertEquals(3, result.getValue(1, 0));
    }

    @Test
    void testMultiplyByScalar() {
        SparseMatrix m = new SparseMatrix(2, 2);
        m.insert(0, 0, 5);
        m.insert(1, 1, -2);

        SparseMatrix r = m.multiplyByScalar(2);

        assertEquals(10, r.getValue(0, 0));
        assertEquals(-4, r.getValue(1, 1));
        assertEquals(0, r.getValue(0, 1));
    }

    @Test
    void testTranspose() {
        SparseMatrix m = new SparseMatrix(3, 2);
        m.insert(0, 1, 8);
        m.insert(2, 0, 4);

        SparseMatrix t = m.transpose();

        assertEquals(8, t.getValue(1, 0));
        assertEquals(4, t.getValue(0, 2));
        assertEquals(0, t.getValue(1, 1));
    }

    @Test
    void testToListOrdering() {
        SparseMatrix matrix = new SparseMatrix(3, 3);
        matrix.insert(2, 0, 1);
        matrix.insert(0, 2, 5);
        matrix.insert(1, 1, 3);

        List<int[]> list = matrix.toList();
        list.sort((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        assertEquals(3, list.size());
        assertArrayEquals(new int[]{0, 2, 5}, list.get(0));
        assertArrayEquals(new int[]{1, 1, 3}, list.get(1));
        assertArrayEquals(new int[]{2, 0, 1}, list.get(2));
    }

}
