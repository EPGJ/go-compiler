package tables;

import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;

public final class StrTable extends HashMap<String,String>{


    public String add(String s) {
		return super.put(s,s);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Strings table:\n");
		for(Map.Entry m:this.entrySet()){
			f.format("Entry %s", m.getValue());
		}
		f.close();
		return sb.toString();
	}

}

