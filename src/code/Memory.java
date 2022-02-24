package code;

import java.util.Vector;

import tables.VarTable;

@SuppressWarnings("serial")
public class Memory extends Vector<Word> {

	private static int memSize;

	public Memory(VarTable vt) {
		Memory.memSize = vt.size();

		for (int i = 0; i < Memory.memSize; i++) {
			this.add(Word.fromInt(0));
		}
	}

	public static void checkAddress(int addr) {
		if (addr < 0 || addr >= Memory.memSize) throw new Error("Memory address not valid! Aborting");
	}
	
	public void storeInt(int addr, int value) {
		Memory.checkAddress(addr);

		this.set(addr, Word.fromInt(value));
	}
	
	public int loadInt(int addr) {
		Memory.checkAddress(addr);

		return this.get(addr).toInt();
	}
	
	public void storeFloat(int addr, float value) {
		Memory.checkAddress(addr);

		this.set(addr, Word.fromFloat(value));
	}
	
	public float loadFloat(int addr) {
		Memory.checkAddress(addr);

		return this.get(addr).toFloat();
	}
	
}
