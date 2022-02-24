package ast;

import static typing.Type.NO_TYPE;

import java.util.ArrayList;
import java.util.List;

import tables.VarTable;
import typing.Type;

public class AST {
	public  final NodeKind kind;
	public  final String key;
 	public  final float floatData;
	public  final Type type;
	private final List<AST> children;

	private AST(NodeKind kind, float floatData, Type type, String key) {
		this.kind = kind;
		this.floatData = floatData;
		this.type = type;
		this.key = key;
		this.children = new ArrayList<AST>();
	}

	public AST(NodeKind kind, Type type, String key) {
		this(kind, 0.0f, type, key);
	}

	public AST(NodeKind kind,Type type, float floatData ) {
		this(kind, floatData, type, " ");
	}

	// Add child to node
	public void addChild(AST child) {
		if(child == null) return;

		this.children.add(child);
	}

	// Get child at given index
	public AST getChild(int idx) {
		if(idx >= 0 && idx <= this.children.size()) {
			return this.children.get(idx);
		}
		return null;
	}

	// Add all children to node
	public static AST newSubtree(NodeKind kind, Type type, AST... children) {
		AST node = new AST(kind, type, " ");
	    for (AST child: children) {
			if(children == null) continue;

	    	node.addChild(child);
	    }
	    return node;
	}

	public void print() {
		System.out.println(this.kind);
		System.out.println(this.type);
		System.out.println(this.children);
		System.out.println(this.children.size());
	}

	// --------------------------------- Testing -------------------------------
	
	private static int nr;
	private static VarTable vt;

	private int printNodeDot() {
		int myNr = nr++;

	    System.err.printf("node%d[label=\"", myNr);
	    if (this.type != NO_TYPE) {
	    	System.err.printf("(%s) ", this.type.toString());
	    }
	    if (this.kind == NodeKind.VAR_DECL_NODE 
			|| this.kind == NodeKind.VAR_USE_NODE 
			|| this.kind == NodeKind.DECLARE_ASSIGN_NODE
			) {
			if(vt.getArgSize(this.key) > 0) {
				System.err.printf("[%d] ", vt.getArgSize(this.key));
			}
	    	System.err.printf("%s@", vt.getName(this.key));
	    } else {
			System.err.printf("%s", this.kind.toString());
	    }
	    if (NodeKind.hasData(this.kind)) {
	        if (this.kind == NodeKind.FLOAT32_VAL_NODE) {
	        	System.err.printf("%.2f", this.floatData);
	        } else if (this.kind == NodeKind.STRING_VAL_NODE) {
	        	System.err.printf("@%s", vt.getName(this.key));
	        } else {
	        	System.err.printf("%s", vt.getName(this.key));
	        }
	    }
	    System.err.printf("\"];\n");

	    for (int i = 0; i < this.children.size(); i++) {
			int childNr = this.children.get(i).printNodeDot();
			System.err.printf("node%d -> node%d;\n", myNr, childNr);
			
	    }
	    return myNr;
	}

	public static void printDot(AST tree, VarTable table) {
	    nr = 0;
	    vt = table;
	    System.err.printf("digraph {\ngraph [ordering=\"out\"];\n");
	    tree.printNodeDot();
	    System.err.printf("}\n");
	}
}
