# Assignment 4a: Building a Huffman Tree

**Course**: Data Structures (CSCI-UA 102, Section 7)
**Instructor**: Max Sklar
**Author**: Anushay Anjum

---

## Table of Contents

1. [Runbook](#runbook)
2. [Time Spent](#time-spent)
3. [Notes](#notes)
4. [Resources & Acknowledgements](#resources--acknowledgements)

---

## Runbook

### 1. Compile All Sources

```bash
cd src
javac *.java
```

### 2. Generate Huffman Tree Specification

```bash
cat sample_frequencies.txt | java HuffmanTree spec
```

### 3. Generate Huffman Legend (Symbol → Code Table)

```bash
cat sample_frequencies.txt | java HuffmanTree legend
```

---

## Time Spent

Roughly **3–4 hours**, including debugging spec traversal and legend ordering.

---

## Notes

* **Spec output** uses a right-first post-order traversal and omits trailing pipes on the far-left spine.
* **Legend output** collects all symbol→code pairs, sorts by code, and prints with inverted bits (left=1, right=0).
* No changes to provided `BinaryHeap.java` or `StdinToString.java`—all work confined to `HuffmanTree.java`.

---

## Resources & Acknowledgements

* [Oracle Java Documentation](https://docs.oracle.com/javase/8/docs/api/) — Standard library reference
* Assignment spec and sample files provided by Prof. Max Sklar
* Implementation and testing by Anushay Anjum
