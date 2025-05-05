public class HuffmanConverter {
    
}
import java.io.IOException;

public class HuffmanConverter {
    // Usage: cat message.txt | java HuffmanConverter encode|decode spec.txt
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: java HuffmanConverter <encode|decode|analyze> <treeSpecFile>");
            System.exit(1);
        }
        String mode = args[0].toLowerCase();
        String treeSpec = StdinToString.readfile(args[1]);
        HuffmanTree tree = HuffmanTree.loadTree(treeSpec);
        String input = StdinToString.read();

        switch (mode) {
            case "encode":
                System.out.println(tree.encode(input));
                break;
            case "decode":
                System.out.println(tree.decode(input));
                break;
            case "analyze":
                System.out.println(tree.analyze(input));
                break;
            default:
                System.err.println("Unknown mode: " + mode);
        }
    }
}
