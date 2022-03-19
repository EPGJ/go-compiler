package code;


public enum OpCode {

	RET("Ret", 2),
	BR("Br", 2),
	SWITCH("Switch", 2),
	INDIRECTBR("IndirectBr", 2),
	INVOKE("Invoke", 2),
	INVALID2("Invalid2", 2),
	UNREACHABLE("Unreachable", 2),
	ADD("Add", 2),
	FADD("FAdd", 2),
	SUB("Sub", 2),
	FSUB("FSub", 2),
	MUL("Mul",2),
	FMUL("FMul",2),
	UDIV("UDiv",2),
	SDIV("SDiv",2),
	FDIV("FDiv",2),
	UREM("URem",2),
	SREM("SRem",2),
	FREM("FRem",2),
	SHL("Shl",2),
	LSHR("LShr",2),
	ASHR("AShr",2),
	AND("And",2),
	OR("Or",2),
	XOR("Xor",2),
	ALLOCA("Alloca",2),
	LOAD("Load",2),
	STORE("Store",2),
	GETELEMENTPTR("GetElementPtr",2),
	TRUNC("Trunc",2),
	ZEXT("ZExt",2),
	SEXT("SExt",2),
	FPTOUI("FPToUI",2),
	FPTOSI("FPToSI",2),
	UITOFP("UIToFP",2),
	SITOFP("SIToFP",2),
	FPTRUNC("FPTrunc",2),
	FPEXT("FPExt",2),
	PTRTOINT("PtrToInt",2),
	INTTOPTR("IntToPtr",2),
	BITCAST("BitCast",2),
	ICMP("ICmp",2),
	FCMP("FCmp",2),
	PHI("PHI",2),
	CALL("Call",2),
	SELECT("Select",2),
	USEROP1("UserOp1",2),
	USEROP2("UserOp2",2),
	VAARG("VAArg",2),
	EXTRACTELEMENt("ExtractElement",2),
	INSERTELEMENT("InsertElement",2),
	SHUFFLEVECTOR("ShuffleVector",2),
	EXTRACTVALUE("ExtractValue",2),
	INSERTVALUE("InsertValue",2),
	FENCE("Fence",2),
	ATOMICCMPXCHG("AtomicCmpXchg",2),
	ATOMICRMW("AtomicRMW",2),
	RESUME("Resume",2),
	LANDINGPAD("LandingPad",2);


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


