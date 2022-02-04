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
    FLOAT64_TYPE {
		public String toString() {
			return "float64";
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
	}
}
