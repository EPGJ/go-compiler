package typing;

public enum Type {
	INT_TYPE {
		public String toString() {
            return "int";
        }
	},
    REAL_TYPE {
		public String toString() {
			return "real";
		}
	},
    FLOAT32_TYPE {
		public String toString() {
			return "float32";
		}
	},
    BOOL_TYPE {
		public String toString() {
            return "bool";
        }
	},
    STR_TYPE {
		public String toString() {
            return "string";
        }
	},
	NO_TYPE {
		public String toString() {
			return "no_type";
		}
	};

	// Unification table for math operations
	// like '+', '-', '*', '/', '%'
	private static Type mathOps[][] = {
			// INT_TYPE		FLOAT32_TYPE	BOOL_TYPE		STR_TYPE
	/*INT_TYPE*/		{ INT_TYPE,  	NO_TYPE, 		NO_TYPE,  		NO_TYPE },
	/*FLOAT32_TYPE*/	{ NO_TYPE, 		FLOAT32_TYPE, 	NO_TYPE, 		NO_TYPE },
	/*BOOL_TYPE*/		{ NO_TYPE, 		NO_TYPE, 		NO_TYPE, 		NO_TYPE },
	/*STR_TYPE*/		{ NO_TYPE, 		NO_TYPE,  		NO_TYPE,  		NO_TYPE }
	};

	// Unification table for relational operations
	// like '==', '!='
	private static Type compare[][] = {
			// INT_TYPE		FLOAT32_TYPE	BOOL_TYPE		STR_TYPE
	/*INT_TYPE*/		{ BOOL_TYPE,  	NO_TYPE, 		NO_TYPE,  		NO_TYPE },
	/*FLOAT32_TYPE*/	{ NO_TYPE, 		BOOL_TYPE, 		NO_TYPE, 		NO_TYPE },
	/*BOOL_TYPE*/		{ NO_TYPE, 		NO_TYPE, 		BOOL_TYPE, 		NO_TYPE },
	/*STR_TYPE*/		{ NO_TYPE, 		NO_TYPE,  		NO_TYPE,  		BOOL_TYPE },
	};

	// Unification table for relational operations
	// like '<', '<=', '>', '>='
	private static Type compare2[][] = {
			// INT_TYPE		FLOAT32_TYPE	BOOL_TYPE		STR_TYPE
	/*INT_TYPE*/		{ BOOL_TYPE,  	NO_TYPE, 		NO_TYPE,  		NO_TYPE },
	/*FLOAT32_TYPE*/	{ NO_TYPE, 		BOOL_TYPE, 		NO_TYPE, 		NO_TYPE },
	/*BOOL_TYPE*/		{ NO_TYPE, 		NO_TYPE, 		NO_TYPE, 		NO_TYPE },
	/*STR_TYPE*/		{ NO_TYPE, 		NO_TYPE,  		NO_TYPE,  		BOOL_TYPE },
	};

	public Type unifyMathOps(Type that) {
	if(!isOpSuported(that)) return NO_TYPE;

	return mathOps[this.ordinal()][that.ordinal()];
	}

	public Type unifyCompare(Type that) {
	if(!isOpSuported(that)) return NO_TYPE;

	return compare[this.ordinal()][that.ordinal()];
	}

	public Type unifyCompare2(Type that) {
	if(!isOpSuported(that)) return NO_TYPE;

	return compare2[this.ordinal()][that.ordinal()];
	}

	public Boolean isOpSuported(Type that) {
	if (this == NO_TYPE || that == NO_TYPE) return false;

	return true;
	}

}
