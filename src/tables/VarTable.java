package tables;

import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;

import typing.Type;

public final class VarTable {

	private Map<String, Entry> table = new HashMap<>();

	public boolean lookupVar(String name, String scope) {
		if (table.containsKey(name) && table.get(name).scope.equals(scope))
			return true;
		return false;
	}

	public void addVar(String name, int line, Type type, String scope, int argSize) {
		Entry entry = new Entry(name, line, type,scope,argSize);
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
	public String getScope(String name){
		return table.get(name).scope;
	}
	public int getArgSize(String name){
		return  table.get(name).argSize;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Variables table:\n");

		for( Map.Entry entry:table.entrySet() ) {
			f.format("Entry -- name: %s, scope: %s, line: %d, type: %s\n", entry.getKey(), getScope(entry.getKey().toString()), getLine(entry.getKey().toString()), getType(entry.getKey().toString()));
		}

		f.close();
		return sb.toString();
	}

	private final class Entry {
		String name;
		int line;
		Type type;
		String scope;
		int argSize;

		Entry(String name, int line, Type type, String scope, int argSize) {
			this.name = name;
			this.line = line;
			this.type = type;
			this.scope = scope;
			this.argSize = argSize;
		}
	}
}
