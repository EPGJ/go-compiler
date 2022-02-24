package tables;

import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;

import typing.Type;

public final class VarTable {

	private Map<String, Entry> table = new HashMap<>();

	public boolean lookupVar(String key) {
		if (table.containsKey(key))
			return true;
		return false;
	}

	public void addVar(String name, int line, Type type, String scope, int argSize) {
		Entry entry = new Entry(name, line, type,scope,argSize);
		table.put(name.concat(scope), entry);
	}

	public String getName(String key) {
	return table.get(key).name;
	}

	public int getLine(String key) {
		return table.get(key).line;
	}

	public Type getType(String key) {
		return table.get(key).type;
	}
	public String getScope(String key){
		return table.get(key).scope;
	}
	public int getArgSize(String key){
		return  table.get(key).argSize;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Variables table:\n");

		for( Map.Entry entry:table.entrySet() ) {
			f.format("Entry -- name: %s, scope: %s, line: %d, type: %s\n", getName(entry.getKey().toString()), getScope(entry.getKey().toString()), getLine(entry.getKey().toString()), getType(entry.getKey().toString()));
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
