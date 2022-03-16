package code;

import java.util.Vector;

import tables.VarTable;

@SuppressWarnings("serial")
public class Memory extends Vector<Word> {

	private static int memSize = 0;

	// Allocate a memory space the same size as the sum of all variable sizes
	public Memory(VarTable vt) {
		// Memory size starts the same as the VarTable size
		Memory.memSize = vt.size();

		// Adds the var args into account for the memory size
		for (int i = 0; i < vt.size(); i++) {
			int argSize = vt.getArgSize(i);

			if(argSize > 0) Memory.memSize += argSize;
		}

		// Empties the memory
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
