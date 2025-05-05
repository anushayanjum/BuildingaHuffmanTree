import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            this.left = null;
            this.right = null;
        }

        // Internal-node constructor
        public HuffmanNode(HuffmanNode left, HuffmanNode right) {
            this.symbols = left.symbols + right.symbols;
            this.frequency = left.frequency + right.frequency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(HuffmanNode o) {
            int cmp = this.frequency.compareTo(o.frequency);
            if (cmp != 0) return cmp;
            return this.symbols.compareTo(o.symbols);
        }
    }

    // Build a heap of HuffmanNodes from "symbol freq symbol freq ..." text
    public static BinaryHeap<HuffmanNode> freqToHeap(String frequencyStr) {
        String[] tokens = frequencyStr.trim().split("\\s+");
        HuffmanNode[] nodes = new HuffmanNode[tokens.length / 2];
        int idx = 0;
        for (int i = 0; i < tokens.length; i += 2) {
            String sym = tokens[i];
            Double freq = Double.valueOf(tokens[i + 1]);
            nodes[idx++] = new HuffmanNode(sym, freq);
        }
        return new BinaryHeap<>(nodes);
    }

    // Standard Huffman build
    public static HuffmanTree createFromHeap(BinaryHeap<HuffmanNode> heap) {
        while (heap.getSize() > 1) {
            HuffmanNode a = heap.extractMin();
            HuffmanNode b = heap.extractMin();
            heap.insert(new HuffmanNode(a, b));
        }
        return new HuffmanTree(heap.extractMin());
    }

    public void printLegend() {
        Map<String,String> map = new HashMap<>();
        buildLegend(root, "", map);
        List<Map.Entry<String,String>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getValue));
        for (Map.Entry<String,String> e : entries) {
            System.out.println(convertSymbolToChar(e.getKey()) + "\t" + e.getValue());
        }
    }

    private void buildLegend(HuffmanNode node, String bits, Map<String,String> map) {
        if (node.left == null && node.right == null) {
            map.put(node.symbols, bits);
        } else {
            buildLegend(node.left,  bits + "1", map);
            buildLegend(node.right, bits + "0", map);
        }
    }

    public void printTreeSpec() {
        traverseSpec(root, true);
        System.out.println();
    }

    private void traverseSpec(HuffmanNode node, boolean isLeftmost) {
        if (node.left == null && node.right == null) {
            System.out.print(convertSymbolToChar(node.symbols));
        } else {
            traverseSpec(node.right, false);
            traverseSpec(node.left, isLeftmost);
            if (!isLeftmost) {
                System.out.print("|");
            }
        }
    }

    // Convert token back to actual character
    public static String convertSymbolToChar(String symbol) {
        if (symbol.equals("space")) return " ";
        if (symbol.equals("eom"))   return "\\e";
        if (symbol.equals("|"))     return "\\|";
        if (symbol.equals("\\"))   return "\\\\";
        return symbol;
    }

    public HuffmanTree(HuffmanNode root) {
        this.root = root;
    }

    public static void main(String[] args) throws IOException {
        String mode = (args.length == 0) ? "spec" : args[0].toLowerCase();
        String frequencyStr = StdinToString.read();
        BinaryHeap<HuffmanNode> heap = freqToHeap(frequencyStr);
        HuffmanTree tree = createFromHeap(heap);

        if (mode.equals("legend")) {
            tree.printLegend();
        } else {
            tree.printTreeSpec();
        }
    }
}