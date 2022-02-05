package tables;

import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;

import typing.Type;

public final class VarTable {

	private Map<String, Entry> table = new HashMap<>();

	public boolean lookupVar(String name) {
		if (table.containsKey(name))
			return true;
		return false;
	}

	public void addVar(String name, int line, Type type) {
		Entry entry = new Entry(name, line, type);
		table.put(name, entry);
	}

	// public String getName(String name) {
	// return table.get(name).name;
	// }

	public int getLine(String name) {
		return table.get(name).line;
	}

	public Type getType(String name) {
		return table.get(name).type;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Variables table:\n");

		for( Map.Entry entry:table.entrySet() ) {
			f.format("Entry -- name: %s, line: %d, type: %s\n", entry.getKey(), getLine(entry.getKey().toString()), getType(entry.getKey().toString()));
		}

		f.close();
		return sb.toString();
	}

	private final class Entry {
		String name;
		int line;
		Type type;

		Entry(String name, int line, Type type) {
			this.name = name;
			this.line = line;
			this.type = type;
		}
	}
}
