package tables;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import typing.Type;

public final class VarTable {

	private List<Entry> table = new ArrayList<Entry>();

	public int lookupVar(String name, String scope) {
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).name.equals(name) && table.get(i).scope.equals(scope)) {
				return i;
			}
		}
		return -1;
	}

	public int addVar(String name, String scope, int line, Type type, int argSize) {
		Entry entry = new Entry(name, scope, line, type, argSize);
		int idxAdded = table.size();
		table.add(entry);
		return idxAdded;
	}

	public String getName(int i) {
		return table.get(i).name;
	}

	public String getScope(int i) {
		return table.get(i).scope;
	}

	public int getLine(int i) {
		return table.get(i).line;
	}

	public Type getType(int i) {
		return table.get(i).type;
	}

	public int getArgSize(int i) {
		return table.get(i).argSize;
	}

	public int size() {
		return table.size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Variables table:\n");
		for (int i = 0; i < table.size(); i++) {
			f.format("Entry %d -- name: %s, scope: %s, line: %d, type: %s\n",
				i, getName(i), getScope(i), getLine(i), getType(i).toString()
			);
		}
		f.close();
		return sb.toString();
	}

	private final class Entry {
		String name;
		String scope;
		int line;
		Type type;
		int argSize;		


		Entry(String name, String scope, int line, Type type, int argSize) {
			this.name = name;
			this.scope = scope;
			this.line = line;
			this.type = type;
			this.argSize = argSize;
		}
	}
}
