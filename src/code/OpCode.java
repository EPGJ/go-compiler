package code;

/*
 * Accepted instructions for NSTM (Not So Tiny Machine).
 * These instructions are based on TM (Tiny Machine) used by Zambon, 
 * but with a few more abstractions.
 */
public enum OpCode {
	// Basic ops
    HALT("HALT", 0),
    NOOP("NOOP", 0),

	/*------------------------------------------------------------------------------*
	 * Arithmetic ops
	 *------------------------------------------------------------------------------*/
	
    ADDi("ADDi", 3),	// ADDi ix, iy, iz	; ix <- iy + iz
    ADDf("ADDf", 3),	// ADDf fx, fy, fz	; fx <- fy + fz

    SUBi("SUBi", 3),	// SUBi ix, iy, iz	; ix <- iy - iz
    SUBf("SUBf", 3),	// SUBf fx, fy, fz	; fx <- fy - fz

    MULi("MULi", 3),	// MULi ix, iy, iz	; ix <- iy * iz
    MULf("MULf", 3),	// MULf fx, fy, fz	; fx <- fy * fz

    DIVi("DIVi", 3),	// DIVi ix, iy, iz	; ix <- iy / iz
    DIVf("DIVf", 3),	// DIVf fx, fy, fz	; fx <- fy / fz

	MODi("MODi", 3),	// MODi ix, iy, iz	; ix <- iy % iz
	MODf("MODf", 3),	// MODf fx, fy, fz	; fx <- fy % fz

	/*------------------------------------------------------------------------------*
	 * Relational ops
	 *------------------------------------------------------------------------------*/

	// Equals
	EQUi("EQUi", 3), 	// EQUi ix, iy, iz	; ix <- iy == iz ? 1 : 0
    EQUf("EQUf", 3),	// EQUf ix, fy, fz	; ix <- fy == fz ? 1 : 0
    EQUs("EQUs", 3), 	// EQUs ix, iy, iz	; ix <- str_tab[iy] == str_tab[iz] ? 1 : 0

	// Not Equals
	NEQi("NEQi", 3), 	// NEQi ix, iy, iz	; ix <- iy != iz ? 1 : 0
    NEQf("NEQf", 3),	// NEQf ix, fy, fz	; ix <- fy != fz ? 1 : 0
    NEQs("NEQs", 3), 	// NEQs ix, iy, iz	; ix <- str_tab[iy] != str_tab[iz] ? 1 : 0

	// Less than
    LTHi("LTHi", 3), 	// LTHi ix, iy, iz	; ix <- iy < iz ? 1 : 0
    LTHf("LTHf", 3), 	// LTHi ix, fy, fz	; ix <- iy < iz ? 1 : 0
    LTHs("LTHs", 3), 	// LTHs ix, iy, iz	; ix <- str_tab[iy] < str_tab[iz] ? 1 : 0

	// Less than or equals to
    LTEi("LTEi", 3), 	// LTEi ix, iy, iz	; ix <- iy <= iz ? 1 : 0
    LTEf("LTEf", 3), 	// LTEi ix, fy, fz	; ix <- iy <= iz ? 1 : 0
    LTEs("LTEs", 3), 	// LTEs ix, iy, iz	; ix <- str_tab[iy] <= str_tab[iz] ? 1 : 0

	// Greater than
    GTHi("GTHi", 3), 	// GTHi ix, iy, iz	; ix <- iy > iz ? 1 : 0
    GTHf("GTHf", 3), 	// GTHi ix, fy, fz	; ix <- iy > iz ? 1 : 0
    GTHs("GTHs", 3), 	// GTHs ix, iy, iz	; ix <- str_tab[iy] > str_tab[iz] ? 1 : 0

	// Greater than or equals to
    GTEi("GTEi", 3), 	// GTEi ix, iy, iz	; ix <- iy >= iz ? 1 : 0
    GTEf("GTEf", 3), 	// GTEi ix, fy, fz	; ix <- iy >= iz ? 1 : 0
    GTEs("GTEs", 3), 	// GTEs ix, iy, iz	; ix <- str_tab[iy] >= str_tab[iz] ? 1 : 0


	/*------------------------------------------------------------------------------*
	 * Branches and jumps
	 *------------------------------------------------------------------------------*/

    // Absolute jump
    JUMP("JUMP", 1),	// JUMP addr		; PC <- addr

    // Branch on true
    BOTb("BOTb", 2), 	// BOTb ix, off		; PC <- PC + off, if ix == 1

    // Branch on false
    BOFb("BOFb", 2),	// BOFb ix, off		; PC <- PC + off, if ix == 0


	/*------------------------------------------------------------------------------*
	 * Loads and stores
	 *------------------------------------------------------------------------------*/

    // Load word (from address)
    LDWi("LDWi", 2), 	// LDWi ix, addr	; ix <- data_mem[addr]
    LDWf("LDWf", 2), 	// LDWf fx, addr	; fx <- data_mem[addr]

    // Load immediate (constant)
    LDIi("LDIi", 2), 	// LDIi ix, int_const	; ix <- int_const
    LDIf("LDIf", 2),  	// LDIf fx, float_const	; fx <- float_const (must be inside an int)

    // Store word (to address)
    STWi("STWi", 2),  	// STWi addr, ix		; data_mem[addr] <- ix
    STWf("STWf", 2),  	// STWf addr, fx		; data_mem[addr] <- fx
   
    // Store string (cheating a little bit)
    SSTR("SSTR", 1),  // SSTR str_const		; str_tab <- str_const

	/*------------------------------------------------------------------------------*
	 * System calls, for I/O (see below)
	 *------------------------------------------------------------------------------*/
	
    CALL("CALL", 2); // CALL code, x
	
	// CALL (very basic simulation of OS system calls)
	// . code: sets the operation to be called.
	// . x: register involved in the operation.
	// List of calls:
	// ----------------------------------------------------------------------------
	// code	| x  | Description
	// ----------- -----------------------------------------------------------
	// 0	| ix | Read int:   		register ix <- int  from stdin
	// 1	| fx | Read float32:  	register fx <- float32 from stdin
	// 2	| ix | Read bool:  		register ix <- bool from stdin (as int)
	// 3	| ix | Read str:   		str_tab[ix] <- str from stdin
	// 4	| ix | Write int:  		stdout <- register ix (as str)
	// 5	| fx | Write float32: 	stdout <- register fx (as str)
	// 6	| ix | Write bool: 		stdout <- register ix (as str)
	// 7	| ix | Write str:  		stdout <- str_tab[ix]
	// ----------------------------------------------------------------------------
	// OBS.: All strings in memory are null ('\0') terminated, like in C.
	// ----------------------------------------------------------------------------


	public final String name;
	public final int opCount;
	
	private OpCode(String name, int opCount) {
		this.name = name;
		this.opCount = opCount;
	}
	
	public String toString() {
		return this.name;
	}
	
}


