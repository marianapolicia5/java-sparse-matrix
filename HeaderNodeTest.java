import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.HeaderNode;
import org.DataNode;

public class HeaderNodeTest {

    @Test
    void testConstructorAndFields() {
        HeaderNode header = new HeaderNode(3);
        assertEquals(3, header.index);
        assertNull(header.rowHead);
        assertNull(header.colHead);
        assertNull(header.next);
    }

    @Test
    void testRowAndColHeadAssignment() {
        HeaderNode header = new HeaderNode(0);
        DataNode rowNode = new DataNode(0, 1, 5);
        DataNode colNode = new DataNode(2, 0, 7);

        header.rowHead = rowNode;
        header.colHead = colNode;

        assertSame(rowNode, header.rowHead);
        assertSame(colNode, header.colHead);
    }

    @Test
    void testNextHeaderLink() {
        HeaderNode h1 = new HeaderNode(1);
        HeaderNode h2 = new HeaderNode(2);
        h1.next = h2;
        assertSame(h2, h1.next);
    }
}