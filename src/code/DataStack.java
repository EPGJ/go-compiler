package code;

import java.util.Formatter;
import java.util.Stack;

@SuppressWarnings("serial")
public final class DataStack extends Stack<Word> {
	
	public void pushInt(int value) {
		super.push(Word.fromInt(value));
	}

	public int popInt() {
		return super.pop().toInt();
	}
	
	public void pushFloat(float value) {
		super.push(Word.fromFloat(value));
	}

	public float popFloat() {
		return super.pop().toFloat();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("*** STACK: ");
		for (int i = 0; i < this.size(); i++) {
			f.format("%d ", this.get(i).toInt());
		}
		f.format("\n");
		f.close();
		return sb.toString();
	}
	
}
