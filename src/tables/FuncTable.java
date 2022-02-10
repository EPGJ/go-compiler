package tables;

import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;

import typing.Type;

public class FuncTable {
	
    private Map<String,Entry> table = new HashMap<>();

	public boolean lookupFunc(String name) {
		if (table.containsKey(name))
			return true;
		return false;
	}

	public void addFunc(String name, int line, Type type, int argsSize) {
		Entry entry = new Entry(name, line, type, argsSize);
		table.put(name, entry);
	}

	// public String getName(int i) {
	// 	return table.get(i).name;
	// }

	public int getLine(String name) {
		return table.get(name).line;
	}

	public Type getType(String name) {
		return table.get(name).type;
	}

    public int getArgsSize(String name) {
		return table.get(name).argsSize;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Variables table:\n");

		for( Map.Entry entry:table.entrySet() ) {
			f.format("Entry -- name: %s, line: %d, type: %s, argsSize: '%d'\n", entry.getKey(), getLine(entry.getKey().toString()), getType(entry.getKey().toString()), getArgsSize(entry.getKey().toString()));
		}

		f.close();
		return sb.toString();
	}


	private final class Entry {
		String name;
		int line;
		Type type;
        int argsSize;

		Entry(String name, int line, Type type, int argsSize) {
			this.name = name;
			this.line = line;
			this.type = type;
            this.argsSize = argsSize;
		}
	}
}
