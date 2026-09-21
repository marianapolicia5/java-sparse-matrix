import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.DataNode;

public class DataNodeTest {

    @Test
    void testConstructorAndFields() {
        DataNode node = new DataNode(1, 2, 10);
        assertEquals(1, node.row);
        assertEquals(2, node.col);
        assertEquals(10, node.value);
        assertNull(node.right);
        assertNull(node.down);
    }

    @Test
    void testLinks() {
        DataNode node1 = new DataNode(0, 0, 1);
        DataNode node2 = new DataNode(0, 1, 2);
        DataNode node3 = new DataNode(1, 0, 3);

        node1.right = node2;
        node1.down = node3;

        assertSame(node2, node1.right);
        assertSame(node3, node1.down);
    }

    @Test
    void testFieldMutation() {
        DataNode node = new DataNode(0, 0, 0);
        node.row = 5;
        node.col = 6;
        node.value = 42;
        assertEquals(5, node.row);
        assertEquals(6, node.col);
        assertEquals(42, node.value);
    }
}