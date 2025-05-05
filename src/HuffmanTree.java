import java.io.IOException;

public class HuffmanTree {
    private HuffmanNode root;

    private static class HuffmanNode implements Comparable<HuffmanNode> {
        final public String symbols;
        final public Double frequency;
        final public HuffmanNode left, right;

        // Leaf-node constructor
        public HuffmanNode(String symbol, Double frequency) {
            this.symbols = symbol;
            this.frequency = frequency;
            this.left = this.right = null;
        }
        // Internal-node constructor
        public HuffmanNode(HuffmanNode left, HuffmanNode right) {
            this.symbols = left.symbols + right.symbols;
            this.frequency = left.frequency + right.frequency;
            this.left = left;
            this.right = right;
        }
        // Order by frequency, tie-break by lexicographic on symbols
        @Override
        public int compareTo(HuffmanNode o) {
            int cmp = this.frequency.compareTo(o.frequency);
            return (cmp != 0) ? cmp : this.symbols.compareTo(o.symbols);
        }
        @Override
        public String toString() {
            return "<" + symbols + ", " + frequency + ">";
        }
    }

    // Build from root node
    public HuffmanTree(HuffmanNode root) {
        this.root = root;
    }

    /**
     * Turn the flat legend string into a min-heap of HuffmanNodes.
     * E.g. "A 20 E 24 G 3 …" ⇒ [HuffmanNode("A",20), …]
     */
    public static BinaryHeap<HuffmanNode> freqToHeap(String legend) {
        String[] tokens = legend.trim().split("\\s+");
        // tokens: [symbol, freq, symbol, freq, …]
        HuffmanNode[] nodes = new HuffmanNode[tokens.length/2];
        int idx = 0;
        for (int i = 0; i < tokens.length; i += 2) {
            String sym = tokens[i];
            Double freq = Double.valueOf(tokens[i+1]);
            nodes[idx++] = new HuffmanNode(sym, freq);
        }
        return new BinaryHeap<>(nodes);
    }

    /**
     * Classic Huffman build: keep extracting two smallest,
     * combine into new node, re-insert, until one remains.
     */
    public static HuffmanTree createFromHeap(BinaryHeap<HuffmanNode> heap) {
        while (heap.getSize() > 1) {
            HuffmanNode a = heap.extractMin();
            HuffmanNode b = heap.extractMin();
            heap.insert(new HuffmanNode(a, b));
        }
        return new HuffmanTree(heap.extractMin());
    }

    /**
     * Print each symbol and its bit-string, one per line,
     * in a preorder traversal: left=0 then right=1.
     */
    public void printLegend() {
        traverseLegend(root, "");
    }
    private void traverseLegend(HuffmanNode node, String bits) {
        if (node.left == null && node.right == null) {
            System.out.println(convertSymbolToChar(node.symbols) + "\t" + bits);
        } else {
            traverseLegend(node.left, bits + "0");
            traverseLegend(node.right, bits + "1");
        }
    }

    /**
     * Print the post-order spec: at each leaf emit symbol,
     * at each internal node emit '|' unless it’s on the
     * far-right spine (those trailing pipes are implied).
     */
    public void printTreeSpec() {
        traverseSpec(root, true);
        System.out.println();
    }
    private void traverseSpec(HuffmanNode node, boolean isRightmost) {
        if (node.left == null && node.right == null) {
            System.out.print(convertSymbolToChar(node.symbols));
        } else {
            traverseSpec(node.left, false);
            traverseSpec(node.right, isRightmost);
            if (!isRightmost) {
                System.out.print("|");
            }
        }
    }

    public static String convertSymbolToChar(String symbol) {
        if (symbol.equals("space")) return " ";
        if (symbol.equals("eom"))   return "\\e";
        if (symbol.equals("|"))     return "\\|";
        if (symbol.equals("\\"))    return "\\\\";
        return symbol;
    }

    public static void main(String[] args) throws IOException {
        String mode = (args.length == 0) ? "spec" : args[0].toLowerCase();
        String freqStr = StdinToString.read();
        BinaryHeap<HuffmanNode> heap = freqToHeap(freqStr);
        HuffmanTree tree = createFromHeap(heap);
        if (mode.equals("legend")) {
            tree.printLegend();
        } else {
            tree.printTreeSpec();
        }
    }
}
