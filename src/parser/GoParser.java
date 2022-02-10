// Generated from src/GoParser.g4 by ANTLR 4.9.2
package parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GoParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BREAK=1, DEFAULT=2, FUNC=3, INTERFACE=4, SELECT=5, CASE=6, DEFER=7, GO=8, 
		MAP=9, STRUCT=10, CHAN=11, ELSE=12, GOTO=13, PACKAGE=14, SWITCH=15, CONST=16, 
		FALLTHROUGH=17, IF=18, RANGE=19, TYPE=20, CONTINUE=21, FOR=22, IMPORT=23, 
		RETURN=24, VAR=25, INT=26, STRING=27, BOOL=28, FLOAT32=29, NIL_LIT=30, 
		BOOLEAN_LIT=31, TRUE=32, FALSE=33, MAIN=34, IDENTIFIER=35, INPUT=36, OUTPUT=37, 
		FMT=38, PRINTLN=39, SCANLN=40, L_PAREN=41, R_PAREN=42, L_CURLY=43, R_CURLY=44, 
		L_BRACKET=45, R_BRACKET=46, ASSIGN=47, COMMA=48, SEMI=49, COLON=50, DOT=51, 
		PLUS_PLUS=52, MINUS_MINUS=53, DECLARE_ASSIGN=54, ELLIPSIS=55, LOGICAL_OR=56, 
		LOGICAL_AND=57, EQUALS=58, NOT_EQUALS=59, LESS=60, LESS_OR_EQUALS=61, 
		GREATER=62, GREATER_OR_EQUALS=63, PLUS_ASSIGN=64, MINUS_ASSIGN=65, OR=66, 
		DIV=67, MOD=68, LSHIFT=69, RSHIFT=70, BIT_CLEAR=71, EXCLAMATION=72, PLUS=73, 
		MINUS=74, CARET=75, STAR=76, AMPERSAND=77, RECEIVE=78, DECIMAL_LIT=79, 
		BINARY_LIT=80, OCTAL_LIT=81, HEX_LIT=82, FLOAT_LIT=83, DECIMAL_FLOAT_LIT=84, 
		HEX_FLOAT_LIT=85, IMAGINARY_LIT=86, RUNE_LIT=87, BYTE_VALUE=88, OCTAL_BYTE_VALUE=89, 
		HEX_BYTE_VALUE=90, LITTLE_U_VALUE=91, BIG_U_VALUE=92, RAW_STRING_LIT=93, 
		INTERPRETED_STRING_LIT=94, WS=95, COMMENT=96, TERMINATOR=97, LINE_COMMENT=98;
	public static final int
		RULE_program = 0, RULE_func_section = 1, RULE_import_section = 2, RULE_package_import = 3, 
		RULE_func_main = 4, RULE_func_declaration = 5, RULE_var_declaration = 6, 
		RULE_declare_assign = 7, RULE_array_declaration = 8, RULE_array_init = 9, 
		RULE_input = 10, RULE_output = 11, RULE_func_args = 12, RULE_func_call = 13, 
		RULE_statement_section = 14, RULE_return_statement = 15, RULE_statement = 16, 
		RULE_if_statement = 17, RULE_for_statement = 18, RULE_assign_statement = 19, 
		RULE_switch_statement = 20, RULE_case_statement = 21, RULE_default_statement = 22, 
		RULE_expression_list = 23, RULE_expression = 24, RULE_id = 25, RULE_var_types = 26;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "func_section", "import_section", "package_import", "func_main", 
			"func_declaration", "var_declaration", "declare_assign", "array_declaration", 
			"array_init", "input", "output", "func_args", "func_call", "statement_section", 
			"return_statement", "statement", "if_statement", "for_statement", "assign_statement", 
			"switch_statement", "case_statement", "default_statement", "expression_list", 
			"expression", "id", "var_types"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'break'", "'default'", "'func'", "'interface'", "'select'", "'case'", 
			"'defer'", "'go'", "'map'", "'struct'", "'chan'", "'else'", "'goto'", 
			"'package'", "'switch'", "'const'", "'fallthrough'", "'if'", "'range'", 
			"'type'", "'continue'", "'for'", "'import'", "'return'", "'var'", "'int'", 
			"'string'", "'bool'", "'float32'", "'nil'", null, "'true'", "'false'", 
			"'main'", null, null, null, "'fmt'", "'Println'", "'Scanln'", "'('", 
			"')'", "'{'", "'}'", "'['", "']'", "'='", "','", "';'", "':'", "'.'", 
			"'++'", "'--'", "':='", "'...'", "'||'", "'&&'", "'=='", "'!='", "'<'", 
			"'<='", "'>'", "'>='", "'+='", "'-='", "'|'", "'/'", "'%'", "'<<'", "'>>'", 
			"'&^'", "'!'", "'+'", "'-'", "'^'", "'*'", "'&'", "'<-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BREAK", "DEFAULT", "FUNC", "INTERFACE", "SELECT", "CASE", "DEFER", 
			"GO", "MAP", "STRUCT", "CHAN", "ELSE", "GOTO", "PACKAGE", "SWITCH", "CONST", 
			"FALLTHROUGH", "IF", "RANGE", "TYPE", "CONTINUE", "FOR", "IMPORT", "RETURN", 
			"VAR", "INT", "STRING", "BOOL", "FLOAT32", "NIL_LIT", "BOOLEAN_LIT", 
			"TRUE", "FALSE", "MAIN", "IDENTIFIER", "INPUT", "OUTPUT", "FMT", "PRINTLN", 
			"SCANLN", "L_PAREN", "R_PAREN", "L_CURLY", "R_CURLY", "L_BRACKET", "R_BRACKET", 
			"ASSIGN", "COMMA", "SEMI", "COLON", "DOT", "PLUS_PLUS", "MINUS_MINUS", 
			"DECLARE_ASSIGN", "ELLIPSIS", "LOGICAL_OR", "LOGICAL_AND", "EQUALS", 
			"NOT_EQUALS", "LESS", "LESS_OR_EQUALS", "GREATER", "GREATER_OR_EQUALS", 
			"PLUS_ASSIGN", "MINUS_ASSIGN", "OR", "DIV", "MOD", "LSHIFT", "RSHIFT", 
			"BIT_CLEAR", "EXCLAMATION", "PLUS", "MINUS", "CARET", "STAR", "AMPERSAND", 
			"RECEIVE", "DECIMAL_LIT", "BINARY_LIT", "OCTAL_LIT", "HEX_LIT", "FLOAT_LIT", 
			"DECIMAL_FLOAT_LIT", "HEX_FLOAT_LIT", "IMAGINARY_LIT", "RUNE_LIT", "BYTE_VALUE", 
			"OCTAL_BYTE_VALUE", "HEX_BYTE_VALUE", "LITTLE_U_VALUE", "BIG_U_VALUE", 
			"RAW_STRING_LIT", "INTERPRETED_STRING_LIT", "WS", "COMMENT", "TERMINATOR", 
			"LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "GoParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GoParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode PACKAGE() { return getToken(GoParser.PACKAGE, 0); }
		public TerminalNode MAIN() { return getToken(GoParser.MAIN, 0); }
		public Func_sectionContext func_section() {
			return getRuleContext(Func_sectionContext.class,0);
		}
		public Import_sectionContext import_section() {
			return getRuleContext(Import_sectionContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(PACKAGE);
			setState(55);
			match(MAIN);
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPORT) {
				{
				setState(56);
				import_section();
				}
			}

			setState(59);
			func_section();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Func_sectionContext extends ParserRuleContext {
		public Func_mainContext func_main() {
			return getRuleContext(Func_mainContext.class,0);
		}
		public List<Func_declarationContext> func_declaration() {
			return getRuleContexts(Func_declarationContext.class);
		}
		public Func_declarationContext func_declaration(int i) {
			return getRuleContext(Func_declarationContext.class,i);
		}
		public Func_sectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_section; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitFunc_section(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_sectionContext func_section() throws RecognitionException {
		Func_sectionContext _localctx = new Func_sectionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_func_section);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(61);
					func_declaration();
					}
					} 
				}
				setState(66);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(67);
			func_main();
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FUNC) {
				{
				{
				setState(68);
				func_declaration();
				}
				}
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Import_sectionContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(GoParser.IMPORT, 0); }
		public Package_importContext package_import() {
			return getRuleContext(Package_importContext.class,0);
		}
		public Import_sectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_import_section; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitImport_section(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Import_sectionContext import_section() throws RecognitionException {
		Import_sectionContext _localctx = new Import_sectionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_import_section);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(IMPORT);
			setState(75);
			package_import();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Package_importContext extends ParserRuleContext {
		public TerminalNode L_PAREN() { return getToken(GoParser.L_PAREN, 0); }
		public TerminalNode R_PAREN() { return getToken(GoParser.R_PAREN, 0); }
		public List<TerminalNode> INTERPRETED_STRING_LIT() { return getTokens(GoParser.INTERPRETED_STRING_LIT); }
		public TerminalNode INTERPRETED_STRING_LIT(int i) {
			return getToken(GoParser.INTERPRETED_STRING_LIT, i);
		}
		public Package_importContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_package_import; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitPackage_import(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Package_importContext package_import() throws RecognitionException {
		Package_importContext _localctx = new Package_importContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_package_import);
		int _la;
		try {
			setState(85);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case L_PAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				match(L_PAREN);
				setState(79); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(78);
					match(INTERPRETED_STRING_LIT);
					}
					}
					setState(81); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==INTERPRETED_STRING_LIT );
				setState(83);
				match(R_PAREN);
				}
				break;
			case INTERPRETED_STRING_LIT:
				enterOuterAlt(_localctx, 2);
				{
				setState(84);
				match(INTERPRETED_STRING_LIT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Func_mainContext extends ParserRuleContext {
		public TerminalNode FUNC() { return getToken(GoParser.FUNC, 0); }
		public TerminalNode MAIN() { return getToken(GoParser.MAIN, 0); }
		public TerminalNode L_PAREN() { return getToken(GoParser.L_PAREN, 0); }
		public TerminalNode R_PAREN() { return getToken(GoParser.R_PAREN, 0); }
		public Statement_sectionContext statement_section() {
			return getRuleContext(Statement_sectionContext.class,0);
		}
		public Func_argsContext func_args() {
			return getRuleContext(Func_argsContext.class,0);
		}
		public Var_typesContext var_types() {
			return getRuleContext(Var_typesContext.class,0);
		}
		public Func_mainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_main; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitFunc_main(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_mainContext func_main() throws RecognitionException {
		Func_mainContext _localctx = new Func_mainContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_func_main);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(FUNC);
			setState(88);
			match(MAIN);
			setState(89);
			match(L_PAREN);
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(90);
				func_args();
				}
			}

			setState(93);
			match(R_PAREN);
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << STRING) | (1L << BOOL) | (1L << FLOAT32))) != 0)) {
				{
				setState(94);
				var_types();
				}
			}

			setState(97);
			statement_section();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Func_declarationContext extends ParserRuleContext {
		public TerminalNode FUNC() { return getToken(GoParser.FUNC, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GoParser.IDENTIFIER, 0); }
		public TerminalNode L_PAREN() { return getToken(GoParser.L_PAREN, 0); }
		public TerminalNode R_PAREN() { return getToken(GoParser.R_PAREN, 0); }
		public Statement_sectionContext statement_section() {
			return getRuleContext(Statement_sectionContext.class,0);
		}
		public Func_argsContext func_args() {
			return getRuleContext(Func_argsContext.class,0);
		}
		public Var_typesContext var_types() {
			return getRuleContext(Var_typesContext.class,0);
		}
		public Func_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_declaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitFunc_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_declarationContext func_declaration() throws RecognitionException {
		Func_declarationContext _localctx = new Func_declarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_func_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(FUNC);
			setState(100);
			match(IDENTIFIER);
			setState(101);
			match(L_PAREN);
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(102);
				func_args();
				}
			}

			setState(105);
			match(R_PAREN);
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << STRING) | (1L << BOOL) | (1L << FLOAT32))) != 0)) {
				{
				setState(106);
				var_types();
				}
			}

			setState(109);
			statement_section();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_declarationContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(GoParser.VAR, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GoParser.IDENTIFIER, 0); }
		public Var_typesContext var_types() {
			return getRuleContext(Var_typesContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(GoParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Array_declarationContext array_declaration() {
			return getRuleContext(Array_declarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(GoParser.SEMI, 0); }
		public Var_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_declaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitVar_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_declarationContext var_declaration() throws RecognitionException {
		Var_declarationContext _localctx = new Var_declarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_var_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(VAR);
			setState(112);
			match(IDENTIFIER);
			setState(120);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(113);
				var_types();
				}
				break;
			case 2:
				{
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << STRING) | (1L << BOOL) | (1L << FLOAT32))) != 0)) {
					{
					setState(114);
					var_types();
					}
				}

				setState(117);
				match(ASSIGN);
				setState(118);
				expression(0);
				}
				break;
			case 3:
				{
				setState(119);
				array_declaration();
				}
				break;
			}
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(122);
				match(SEMI);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Declare_assignContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GoParser.IDENTIFIER, 0); }
		public TerminalNode DECLARE_ASSIGN() { return getToken(GoParser.DECLARE_ASSIGN, 0); }
		public Array_initContext array_init() {
			return getRuleContext(Array_initContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(GoParser.SEMI, 0); }
		public Declare_assignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declare_assign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitDeclare_assign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Declare_assignContext declare_assign() throws RecognitionException {
		Declare_assignContext _localctx = new Declare_assignContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_declare_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(IDENTIFIER);
			setState(126);
			match(DECLARE_ASSIGN);
			setState(129);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case L_BRACKET:
				{
				setState(127);
				array_init();
				}
				break;
			case BOOLEAN_LIT:
			case IDENTIFIER:
			case L_PAREN:
			case DECIMAL_LIT:
			case FLOAT_LIT:
			case INTERPRETED_STRING_LIT:
				{
				setState(128);
				expression(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(132);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(131);
				match(SEMI);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Array_declarationContext extends ParserRuleContext {
		public TerminalNode L_BRACKET() { return getToken(GoParser.L_BRACKET, 0); }
		public TerminalNode DECIMAL_LIT() { return getToken(GoParser.DECIMAL_LIT, 0); }
		public TerminalNode R_BRACKET() { return getToken(GoParser.R_BRACKET, 0); }
		public Var_typesContext var_types() {
			return getRuleContext(Var_typesContext.class,0);
		}
		public Array_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_declaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitArray_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Array_declarationContext array_declaration() throws RecognitionException {
		Array_declarationContext _localctx = new Array_declarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_array_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(L_BRACKET);
			setState(135);
			match(DECIMAL_LIT);
			setState(136);
			match(R_BRACKET);
			setState(137);
			var_types();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Array_initContext extends ParserRuleContext {
		public Array_declarationContext array_declaration() {
			return getRuleContext(Array_declarationContext.class,0);
		}
		public TerminalNode L_CURLY() { return getToken(GoParser.L_CURLY, 0); }
		public TerminalNode R_CURLY() { return getToken(GoParser.R_CURLY, 0); }
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public Array_initContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_init; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitArray_init(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Array_initContext array_init() throws RecognitionException {
		Array_initContext _localctx = new Array_initContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_array_init);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			array_declaration();
			setState(140);
			match(L_CURLY);
			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 31)) & ~0x3f) == 0 && ((1L << (_la - 31)) & ((1L << (BOOLEAN_LIT - 31)) | (1L << (IDENTIFIER - 31)) | (1L << (L_PAREN - 31)) | (1L << (DECIMAL_LIT - 31)) | (1L << (FLOAT_LIT - 31)) | (1L << (INTERPRETED_STRING_LIT - 31)))) != 0)) {
				{
				setState(141);
				expression_list();
				}
			}

			setState(144);
			match(R_CURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InputContext extends ParserRuleContext {
		public TerminalNode INPUT() { return getToken(GoParser.INPUT, 0); }
		public TerminalNode L_PAREN() { return getToken(GoParser.L_PAREN, 0); }
		public TerminalNode AMPERSAND() { return getToken(GoParser.AMPERSAND, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode R_PAREN() { return getToken(GoParser.R_PAREN, 0); }
		public InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_input; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitInput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InputContext input() throws RecognitionException {
		InputContext _localctx = new InputContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(INPUT);
			setState(147);
			match(L_PAREN);
			setState(148);
			match(AMPERSAND);
			setState(149);
			id();
			setState(150);
			match(R_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OutputContext extends ParserRuleContext {
		public TerminalNode OUTPUT() { return getToken(GoParser.OUTPUT, 0); }
		public TerminalNode L_PAREN() { return getToken(GoParser.L_PAREN, 0); }
		public TerminalNode R_PAREN() { return getToken(GoParser.R_PAREN, 0); }
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public OutputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_output; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitOutput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutputContext output() throws RecognitionException {
		OutputContext _localctx = new OutputContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_output);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(OUTPUT);
			setState(153);
			match(L_PAREN);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 31)) & ~0x3f) == 0 && ((1L << (_la - 31)) & ((1L << (BOOLEAN_LIT - 31)) | (1L << (IDENTIFIER - 31)) | (1L << (L_PAREN - 31)) | (1L << (DECIMAL_LIT - 31)) | (1L << (FLOAT_LIT - 31)) | (1L << (INTERPRETED_STRING_LIT - 31)))) != 0)) {
				{
				setState(154);
				expression_list();
				}
			}

			setState(157);
			match(R_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Func_argsContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<Var_typesContext> var_types() {
			return getRuleContexts(Var_typesContext.class);
		}
		public Var_typesContext var_types(int i) {
			return getRuleContext(Var_typesContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GoParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GoParser.COMMA, i);
		}
		public Func_argsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_args; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitFunc_args(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_argsContext func_args() throws RecognitionException {
		Func_argsContext _localctx = new Func_argsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_func_args);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			id();
			setState(160);
			var_types();
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(161);
				match(COMMA);
				setState(162);
				id();
				setState(163);
				var_types();
				}
				}
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Func_callContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GoParser.IDENTIFIER, 0); }
		public TerminalNode L_PAREN() { return getToken(GoParser.L_PAREN, 0); }
		public TerminalNode R_PAREN() { return getToken(GoParser.R_PAREN, 0); }
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public Func_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_call; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitFunc_call(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_callContext func_call() throws RecognitionException {
		Func_callContext _localctx = new Func_callContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_func_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(IDENTIFIER);
			setState(171);
			match(L_PAREN);
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 31)) & ~0x3f) == 0 && ((1L << (_la - 31)) & ((1L << (BOOLEAN_LIT - 31)) | (1L << (IDENTIFIER - 31)) | (1L << (L_PAREN - 31)) | (1L << (DECIMAL_LIT - 31)) | (1L << (FLOAT_LIT - 31)) | (1L << (INTERPRETED_STRING_LIT - 31)))) != 0)) {
				{
				setState(172);
				expression_list();
				}
			}

			setState(175);
			match(R_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Statement_sectionContext extends ParserRuleContext {
		public TerminalNode L_CURLY() { return getToken(GoParser.L_CURLY, 0); }
		public TerminalNode R_CURLY() { return getToken(GoParser.R_CURLY, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Return_statementContext return_statement() {
			return getRuleContext(Return_statementContext.class,0);
		}
		public Statement_sectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_section; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitStatement_section(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Statement_sectionContext statement_section() throws RecognitionException {
		Statement_sectionContext _localctx = new Statement_sectionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_statement_section);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			match(L_CURLY);
			setState(181);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SWITCH) | (1L << IF) | (1L << FOR) | (1L << VAR) | (1L << IDENTIFIER) | (1L << INPUT) | (1L << OUTPUT))) != 0)) {
				{
				{
				setState(178);
				statement();
				}
				}
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(185);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RETURN) {
				{
				setState(184);
				return_statement();
				}
			}

			setState(187);
			match(R_CURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Return_statementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(GoParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(GoParser.SEMI, 0); }
		public Return_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitReturn_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Return_statementContext return_statement() throws RecognitionException {
		Return_statementContext _localctx = new Return_statementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_return_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			match(RETURN);
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 31)) & ~0x3f) == 0 && ((1L << (_la - 31)) & ((1L << (BOOLEAN_LIT - 31)) | (1L << (IDENTIFIER - 31)) | (1L << (L_PAREN - 31)) | (1L << (DECIMAL_LIT - 31)) | (1L << (FLOAT_LIT - 31)) | (1L << (INTERPRETED_STRING_LIT - 31)))) != 0)) {
				{
				setState(190);
				expression(0);
				}
			}

			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(193);
				match(SEMI);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public Var_declarationContext var_declaration() {
			return getRuleContext(Var_declarationContext.class,0);
		}
		public Declare_assignContext declare_assign() {
			return getRuleContext(Declare_assignContext.class,0);
		}
		public If_statementContext if_statement() {
			return getRuleContext(If_statementContext.class,0);
		}
		public For_statementContext for_statement() {
			return getRuleContext(For_statementContext.class,0);
		}
		public Assign_statementContext assign_statement() {
			return getRuleContext(Assign_statementContext.class,0);
		}
		public Switch_statementContext switch_statement() {
			return getRuleContext(Switch_statementContext.class,0);
		}
		public Func_callContext func_call() {
			return getRuleContext(Func_callContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(GoParser.SEMI, 0); }
		public InputContext input() {
			return getRuleContext(InputContext.class,0);
		}
		public OutputContext output() {
			return getRuleContext(OutputContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_statement);
		int _la;
		try {
			setState(208);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(196);
				var_declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(197);
				declare_assign();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(198);
				if_statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(199);
				for_statement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(200);
				assign_statement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(201);
				switch_statement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(202);
				func_call();
				setState(204);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMI) {
					{
					setState(203);
					match(SEMI);
					}
				}

				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(206);
				input();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(207);
				output();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_statementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GoParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<Statement_sectionContext> statement_section() {
			return getRuleContexts(Statement_sectionContext.class);
		}
		public Statement_sectionContext statement_section(int i) {
			return getRuleContext(Statement_sectionContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(GoParser.ELSE, 0); }
		public If_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitIf_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_statementContext if_statement() throws RecognitionException {
		If_statementContext _localctx = new If_statementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_if_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(IF);
			setState(211);
			expression(0);
			setState(212);
			statement_section();
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(213);
				match(ELSE);
				setState(214);
				statement_section();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class For_statementContext extends ParserRuleContext {
		public For_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_statement; }
	 
		public For_statementContext() { }
		public void copyFrom(For_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ForContext extends For_statementContext {
		public TerminalNode FOR() { return getToken(GoParser.FOR, 0); }
		public Declare_assignContext declare_assign() {
			return getRuleContext(Declare_assignContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(GoParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(GoParser.SEMI, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Assign_statementContext assign_statement() {
			return getRuleContext(Assign_statementContext.class,0);
		}
		public Statement_sectionContext statement_section() {
			return getRuleContext(Statement_sectionContext.class,0);
		}
		public ForContext(For_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitFor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileContext extends For_statementContext {
		public TerminalNode FOR() { return getToken(GoParser.FOR, 0); }
		public Statement_sectionContext statement_section() {
			return getRuleContext(Statement_sectionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WhileContext(For_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitWhile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final For_statementContext for_statement() throws RecognitionException {
		For_statementContext _localctx = new For_statementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_for_statement);
		int _la;
		try {
			setState(230);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				_localctx = new WhileContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(217);
				match(FOR);
				setState(219);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 31)) & ~0x3f) == 0 && ((1L << (_la - 31)) & ((1L << (BOOLEAN_LIT - 31)) | (1L << (IDENTIFIER - 31)) | (1L << (L_PAREN - 31)) | (1L << (DECIMAL_LIT - 31)) | (1L << (FLOAT_LIT - 31)) | (1L << (INTERPRETED_STRING_LIT - 31)))) != 0)) {
					{
					setState(218);
					expression(0);
					}
				}

				setState(221);
				statement_section();
				}
				break;
			case 2:
				_localctx = new ForContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(222);
				match(FOR);
				setState(223);
				declare_assign();
				setState(224);
				match(SEMI);
				setState(225);
				expression(0);
				setState(226);
				match(SEMI);
				setState(227);
				assign_statement();
				setState(228);
				statement_section();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_statementContext extends ParserRuleContext {
		public Assign_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_statement; }
	 
		public Assign_statementContext() { }
		public void copyFrom(Assign_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssignPPMMContext extends Assign_statementContext {
		public Token op;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode PLUS_PLUS() { return getToken(GoParser.PLUS_PLUS, 0); }
		public TerminalNode MINUS_MINUS() { return getToken(GoParser.MINUS_MINUS, 0); }
		public TerminalNode SEMI() { return getToken(GoParser.SEMI, 0); }
		public AssignPPMMContext(Assign_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitAssignPPMM(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignExpressionContext extends Assign_statementContext {
		public Token op;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(GoParser.ASSIGN, 0); }
		public TerminalNode MINUS_ASSIGN() { return getToken(GoParser.MINUS_ASSIGN, 0); }
		public TerminalNode PLUS_ASSIGN() { return getToken(GoParser.PLUS_ASSIGN, 0); }
		public TerminalNode SEMI() { return getToken(GoParser.SEMI, 0); }
		public AssignExpressionContext(Assign_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assign_statementContext assign_statement() throws RecognitionException {
		Assign_statementContext _localctx = new Assign_statementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_assign_statement);
		int _la;
		try {
			setState(243);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				_localctx = new AssignExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(232);
				id();
				setState(233);
				((AssignExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((((_la - 47)) & ~0x3f) == 0 && ((1L << (_la - 47)) & ((1L << (ASSIGN - 47)) | (1L << (PLUS_ASSIGN - 47)) | (1L << (MINUS_ASSIGN - 47)))) != 0)) ) {
					((AssignExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(234);
				expression(0);
				setState(236);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMI) {
					{
					setState(235);
					match(SEMI);
					}
				}

				}
				break;
			case 2:
				_localctx = new AssignPPMMContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(238);
				id();
				setState(239);
				((AssignPPMMContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PLUS_PLUS || _la==MINUS_MINUS) ) {
					((AssignPPMMContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMI) {
					{
					setState(240);
					match(SEMI);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Switch_statementContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(GoParser.SWITCH, 0); }
		public TerminalNode L_CURLY() { return getToken(GoParser.L_CURLY, 0); }
		public Case_statementContext case_statement() {
			return getRuleContext(Case_statementContext.class,0);
		}
		public TerminalNode R_CURLY() { return getToken(GoParser.R_CURLY, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public Func_callContext func_call() {
			return getRuleContext(Func_callContext.class,0);
		}
		public Switch_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switch_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitSwitch_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Switch_statementContext switch_statement() throws RecognitionException {
		Switch_statementContext _localctx = new Switch_statementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_switch_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			match(SWITCH);
			setState(248);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(246);
				id();
				}
				break;
			case 2:
				{
				setState(247);
				func_call();
				}
				break;
			}
			setState(250);
			match(L_CURLY);
			setState(251);
			case_statement();
			setState(252);
			match(R_CURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Case_statementContext extends ParserRuleContext {
		public List<TerminalNode> CASE() { return getTokens(GoParser.CASE); }
		public TerminalNode CASE(int i) {
			return getToken(GoParser.CASE, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(GoParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(GoParser.COLON, i);
		}
		public Default_statementContext default_statement() {
			return getRuleContext(Default_statementContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Case_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_case_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitCase_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Case_statementContext case_statement() throws RecognitionException {
		Case_statementContext _localctx = new Case_statementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_case_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE) {
				{
				{
				setState(254);
				match(CASE);
				setState(255);
				expression(0);
				setState(256);
				match(COLON);
				setState(260);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SWITCH) | (1L << IF) | (1L << FOR) | (1L << VAR) | (1L << IDENTIFIER) | (1L << INPUT) | (1L << OUTPUT))) != 0)) {
					{
					{
					setState(257);
					statement();
					}
					}
					setState(262);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(267);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(269);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(268);
				default_statement();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Default_statementContext extends ParserRuleContext {
		public TerminalNode DEFAULT() { return getToken(GoParser.DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(GoParser.COLON, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Default_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_default_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitDefault_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Default_statementContext default_statement() throws RecognitionException {
		Default_statementContext _localctx = new Default_statementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_default_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(DEFAULT);
			setState(272);
			match(COLON);
			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SWITCH) | (1L << IF) | (1L << FOR) | (1L << VAR) | (1L << IDENTIFIER) | (1L << INPUT) | (1L << OUTPUT))) != 0)) {
				{
				{
				setState(273);
				statement();
				}
				}
				setState(278);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expression_listContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GoParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GoParser.COMMA, i);
		}
		public Expression_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitExpression_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression_listContext expression_list() throws RecognitionException {
		Expression_listContext _localctx = new Expression_listContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_expression_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			expression(0);
			setState(284);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(280);
				match(COMMA);
				setState(281);
				expression(0);
				}
				}
				setState(286);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExpressionIdContext extends ExpressionContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ExpressionIdContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitExpressionId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntValContext extends ExpressionContext {
		public TerminalNode DECIMAL_LIT() { return getToken(GoParser.DECIMAL_LIT, 0); }
		public IntValContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitIntVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusMinusContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(GoParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(GoParser.MINUS, 0); }
		public PlusMinusContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitPlusMinus(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StarDivModContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode STAR() { return getToken(GoParser.STAR, 0); }
		public TerminalNode DIV() { return getToken(GoParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(GoParser.MOD, 0); }
		public StarDivModContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitStarDivMod(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionFuncCallContext extends ExpressionContext {
		public Func_callContext func_call() {
			return getRuleContext(Func_callContext.class,0);
		}
		public ExpressionFuncCallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitExpressionFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionParenContext extends ExpressionContext {
		public TerminalNode L_PAREN() { return getToken(GoParser.L_PAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode R_PAREN() { return getToken(GoParser.R_PAREN, 0); }
		public ExpressionParenContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitExpressionParen(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringValContext extends ExpressionContext {
		public TerminalNode INTERPRETED_STRING_LIT() { return getToken(GoParser.INTERPRETED_STRING_LIT, 0); }
		public StringValContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitStringVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FloatValContext extends ExpressionContext {
		public TerminalNode FLOAT_LIT() { return getToken(GoParser.FLOAT_LIT, 0); }
		public FloatValContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitFloatVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolValContext extends ExpressionContext {
		public TerminalNode BOOLEAN_LIT() { return getToken(GoParser.BOOLEAN_LIT, 0); }
		public BoolValContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitBoolVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RelationalOperatorsContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode EQUALS() { return getToken(GoParser.EQUALS, 0); }
		public TerminalNode NOT_EQUALS() { return getToken(GoParser.NOT_EQUALS, 0); }
		public TerminalNode LESS() { return getToken(GoParser.LESS, 0); }
		public TerminalNode LESS_OR_EQUALS() { return getToken(GoParser.LESS_OR_EQUALS, 0); }
		public TerminalNode GREATER() { return getToken(GoParser.GREATER, 0); }
		public TerminalNode GREATER_OR_EQUALS() { return getToken(GoParser.GREATER_OR_EQUALS, 0); }
		public RelationalOperatorsContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitRelationalOperators(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 48;
		enterRecursionRule(_localctx, 48, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				_localctx = new ExpressionParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(288);
				match(L_PAREN);
				setState(289);
				expression(0);
				setState(290);
				match(R_PAREN);
				}
				break;
			case 2:
				{
				_localctx = new ExpressionIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(292);
				id();
				}
				break;
			case 3:
				{
				_localctx = new ExpressionFuncCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(293);
				func_call();
				}
				break;
			case 4:
				{
				_localctx = new IntValContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(294);
				match(DECIMAL_LIT);
				}
				break;
			case 5:
				{
				_localctx = new FloatValContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(295);
				match(FLOAT_LIT);
				}
				break;
			case 6:
				{
				_localctx = new StringValContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(296);
				match(INTERPRETED_STRING_LIT);
				}
				break;
			case 7:
				{
				_localctx = new BoolValContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(297);
				match(BOOLEAN_LIT);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(311);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(309);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
					case 1:
						{
						_localctx = new StarDivModContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(300);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(301);
						((StarDivModContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (DIV - 67)) | (1L << (MOD - 67)) | (1L << (STAR - 67)))) != 0)) ) {
							((StarDivModContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(302);
						expression(11);
						}
						break;
					case 2:
						{
						_localctx = new PlusMinusContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(303);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(304);
						((PlusMinusContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((PlusMinusContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(305);
						expression(10);
						}
						break;
					case 3:
						{
						_localctx = new RelationalOperatorsContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(306);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(307);
						((RelationalOperatorsContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUALS) | (1L << NOT_EQUALS) | (1L << LESS) | (1L << LESS_OR_EQUALS) | (1L << GREATER) | (1L << GREATER_OR_EQUALS))) != 0)) ) {
							((RelationalOperatorsContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(308);
						expression(9);
						}
						break;
					}
					} 
				}
				setState(313);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GoParser.IDENTIFIER, 0); }
		public TerminalNode L_BRACKET() { return getToken(GoParser.L_BRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode R_BRACKET() { return getToken(GoParser.R_BRACKET, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			match(IDENTIFIER);
			setState(319);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				{
				setState(315);
				match(L_BRACKET);
				setState(316);
				expression(0);
				setState(317);
				match(R_BRACKET);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_typesContext extends ParserRuleContext {
		public Var_typesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_types; }
	 
		public Var_typesContext() { }
		public void copyFrom(Var_typesContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IntTypeContext extends Var_typesContext {
		public TerminalNode INT() { return getToken(GoParser.INT, 0); }
		public IntTypeContext(Var_typesContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitIntType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringTypeContext extends Var_typesContext {
		public TerminalNode STRING() { return getToken(GoParser.STRING, 0); }
		public StringTypeContext(Var_typesContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitStringType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolTypeContext extends Var_typesContext {
		public TerminalNode BOOL() { return getToken(GoParser.BOOL, 0); }
		public BoolTypeContext(Var_typesContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitBoolType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Float32TypeContext extends Var_typesContext {
		public TerminalNode FLOAT32() { return getToken(GoParser.FLOAT32, 0); }
		public Float32TypeContext(Var_typesContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoParserVisitor ) return ((GoParserVisitor<? extends T>)visitor).visitFloat32Type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_typesContext var_types() throws RecognitionException {
		Var_typesContext _localctx = new Var_typesContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_var_types);
		try {
			setState(325);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(321);
				match(INT);
				}
				break;
			case STRING:
				_localctx = new StringTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(322);
				match(STRING);
				}
				break;
			case BOOL:
				_localctx = new BoolTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(323);
				match(BOOL);
				}
				break;
			case FLOAT32:
				_localctx = new Float32TypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(324);
				match(FLOAT32);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 24:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3d\u014a\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\5\2<\n\2\3\2\3\2\3\3\7\3A\n"+
		"\3\f\3\16\3D\13\3\3\3\3\3\7\3H\n\3\f\3\16\3K\13\3\3\4\3\4\3\4\3\5\3\5"+
		"\6\5R\n\5\r\5\16\5S\3\5\3\5\5\5X\n\5\3\6\3\6\3\6\3\6\5\6^\n\6\3\6\3\6"+
		"\5\6b\n\6\3\6\3\6\3\7\3\7\3\7\3\7\5\7j\n\7\3\7\3\7\5\7n\n\7\3\7\3\7\3"+
		"\b\3\b\3\b\3\b\5\bv\n\b\3\b\3\b\3\b\5\b{\n\b\3\b\5\b~\n\b\3\t\3\t\3\t"+
		"\3\t\5\t\u0084\n\t\3\t\5\t\u0087\n\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\5\13\u0091\n\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\5\r\u009e"+
		"\n\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u00a8\n\16\f\16\16\16"+
		"\u00ab\13\16\3\17\3\17\3\17\5\17\u00b0\n\17\3\17\3\17\3\20\3\20\7\20\u00b6"+
		"\n\20\f\20\16\20\u00b9\13\20\3\20\5\20\u00bc\n\20\3\20\3\20\3\21\3\21"+
		"\5\21\u00c2\n\21\3\21\5\21\u00c5\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\5\22\u00cf\n\22\3\22\3\22\5\22\u00d3\n\22\3\23\3\23\3\23\3\23"+
		"\3\23\5\23\u00da\n\23\3\24\3\24\5\24\u00de\n\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\5\24\u00e9\n\24\3\25\3\25\3\25\3\25\5\25\u00ef"+
		"\n\25\3\25\3\25\3\25\5\25\u00f4\n\25\5\25\u00f6\n\25\3\26\3\26\3\26\5"+
		"\26\u00fb\n\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\7\27\u0105\n\27"+
		"\f\27\16\27\u0108\13\27\7\27\u010a\n\27\f\27\16\27\u010d\13\27\3\27\5"+
		"\27\u0110\n\27\3\30\3\30\3\30\7\30\u0115\n\30\f\30\16\30\u0118\13\30\3"+
		"\31\3\31\3\31\7\31\u011d\n\31\f\31\16\31\u0120\13\31\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u012d\n\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\7\32\u0138\n\32\f\32\16\32\u013b\13\32"+
		"\3\33\3\33\3\33\3\33\3\33\5\33\u0142\n\33\3\34\3\34\3\34\3\34\5\34\u0148"+
		"\n\34\3\34\2\3\62\35\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60"+
		"\62\64\66\2\7\4\2\61\61BC\3\2\66\67\4\2EFNN\3\2KL\3\2<A\2\u0168\28\3\2"+
		"\2\2\4B\3\2\2\2\6L\3\2\2\2\bW\3\2\2\2\nY\3\2\2\2\fe\3\2\2\2\16q\3\2\2"+
		"\2\20\177\3\2\2\2\22\u0088\3\2\2\2\24\u008d\3\2\2\2\26\u0094\3\2\2\2\30"+
		"\u009a\3\2\2\2\32\u00a1\3\2\2\2\34\u00ac\3\2\2\2\36\u00b3\3\2\2\2 \u00bf"+
		"\3\2\2\2\"\u00d2\3\2\2\2$\u00d4\3\2\2\2&\u00e8\3\2\2\2(\u00f5\3\2\2\2"+
		"*\u00f7\3\2\2\2,\u010b\3\2\2\2.\u0111\3\2\2\2\60\u0119\3\2\2\2\62\u012c"+
		"\3\2\2\2\64\u013c\3\2\2\2\66\u0147\3\2\2\289\7\20\2\29;\7$\2\2:<\5\6\4"+
		"\2;:\3\2\2\2;<\3\2\2\2<=\3\2\2\2=>\5\4\3\2>\3\3\2\2\2?A\5\f\7\2@?\3\2"+
		"\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2CE\3\2\2\2DB\3\2\2\2EI\5\n\6\2FH\5\f"+
		"\7\2GF\3\2\2\2HK\3\2\2\2IG\3\2\2\2IJ\3\2\2\2J\5\3\2\2\2KI\3\2\2\2LM\7"+
		"\31\2\2MN\5\b\5\2N\7\3\2\2\2OQ\7+\2\2PR\7`\2\2QP\3\2\2\2RS\3\2\2\2SQ\3"+
		"\2\2\2ST\3\2\2\2TU\3\2\2\2UX\7,\2\2VX\7`\2\2WO\3\2\2\2WV\3\2\2\2X\t\3"+
		"\2\2\2YZ\7\5\2\2Z[\7$\2\2[]\7+\2\2\\^\5\32\16\2]\\\3\2\2\2]^\3\2\2\2^"+
		"_\3\2\2\2_a\7,\2\2`b\5\66\34\2a`\3\2\2\2ab\3\2\2\2bc\3\2\2\2cd\5\36\20"+
		"\2d\13\3\2\2\2ef\7\5\2\2fg\7%\2\2gi\7+\2\2hj\5\32\16\2ih\3\2\2\2ij\3\2"+
		"\2\2jk\3\2\2\2km\7,\2\2ln\5\66\34\2ml\3\2\2\2mn\3\2\2\2no\3\2\2\2op\5"+
		"\36\20\2p\r\3\2\2\2qr\7\33\2\2rz\7%\2\2s{\5\66\34\2tv\5\66\34\2ut\3\2"+
		"\2\2uv\3\2\2\2vw\3\2\2\2wx\7\61\2\2x{\5\62\32\2y{\5\22\n\2zs\3\2\2\2z"+
		"u\3\2\2\2zy\3\2\2\2{}\3\2\2\2|~\7\63\2\2}|\3\2\2\2}~\3\2\2\2~\17\3\2\2"+
		"\2\177\u0080\7%\2\2\u0080\u0083\78\2\2\u0081\u0084\5\24\13\2\u0082\u0084"+
		"\5\62\32\2\u0083\u0081\3\2\2\2\u0083\u0082\3\2\2\2\u0084\u0086\3\2\2\2"+
		"\u0085\u0087\7\63\2\2\u0086\u0085\3\2\2\2\u0086\u0087\3\2\2\2\u0087\21"+
		"\3\2\2\2\u0088\u0089\7/\2\2\u0089\u008a\7Q\2\2\u008a\u008b\7\60\2\2\u008b"+
		"\u008c\5\66\34\2\u008c\23\3\2\2\2\u008d\u008e\5\22\n\2\u008e\u0090\7-"+
		"\2\2\u008f\u0091\5\60\31\2\u0090\u008f\3\2\2\2\u0090\u0091\3\2\2\2\u0091"+
		"\u0092\3\2\2\2\u0092\u0093\7.\2\2\u0093\25\3\2\2\2\u0094\u0095\7&\2\2"+
		"\u0095\u0096\7+\2\2\u0096\u0097\7O\2\2\u0097\u0098\5\64\33\2\u0098\u0099"+
		"\7,\2\2\u0099\27\3\2\2\2\u009a\u009b\7\'\2\2\u009b\u009d\7+\2\2\u009c"+
		"\u009e\5\60\31\2\u009d\u009c\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009f\3"+
		"\2\2\2\u009f\u00a0\7,\2\2\u00a0\31\3\2\2\2\u00a1\u00a2\5\64\33\2\u00a2"+
		"\u00a9\5\66\34\2\u00a3\u00a4\7\62\2\2\u00a4\u00a5\5\64\33\2\u00a5\u00a6"+
		"\5\66\34\2\u00a6\u00a8\3\2\2\2\u00a7\u00a3\3\2\2\2\u00a8\u00ab\3\2\2\2"+
		"\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\33\3\2\2\2\u00ab\u00a9"+
		"\3\2\2\2\u00ac\u00ad\7%\2\2\u00ad\u00af\7+\2\2\u00ae\u00b0\5\60\31\2\u00af"+
		"\u00ae\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\7,"+
		"\2\2\u00b2\35\3\2\2\2\u00b3\u00b7\7-\2\2\u00b4\u00b6\5\"\22\2\u00b5\u00b4"+
		"\3\2\2\2\u00b6\u00b9\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8"+
		"\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00ba\u00bc\5 \21\2\u00bb\u00ba\3\2"+
		"\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00be\7.\2\2\u00be"+
		"\37\3\2\2\2\u00bf\u00c1\7\32\2\2\u00c0\u00c2\5\62\32\2\u00c1\u00c0\3\2"+
		"\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4\3\2\2\2\u00c3\u00c5\7\63\2\2\u00c4"+
		"\u00c3\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5!\3\2\2\2\u00c6\u00d3\5\16\b\2"+
		"\u00c7\u00d3\5\20\t\2\u00c8\u00d3\5$\23\2\u00c9\u00d3\5&\24\2\u00ca\u00d3"+
		"\5(\25\2\u00cb\u00d3\5*\26\2\u00cc\u00ce\5\34\17\2\u00cd\u00cf\7\63\2"+
		"\2\u00ce\u00cd\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d3\3\2\2\2\u00d0\u00d3"+
		"\5\26\f\2\u00d1\u00d3\5\30\r\2\u00d2\u00c6\3\2\2\2\u00d2\u00c7\3\2\2\2"+
		"\u00d2\u00c8\3\2\2\2\u00d2\u00c9\3\2\2\2\u00d2\u00ca\3\2\2\2\u00d2\u00cb"+
		"\3\2\2\2\u00d2\u00cc\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d1\3\2\2\2\u00d3"+
		"#\3\2\2\2\u00d4\u00d5\7\24\2\2\u00d5\u00d6\5\62\32\2\u00d6\u00d9\5\36"+
		"\20\2\u00d7\u00d8\7\16\2\2\u00d8\u00da\5\36\20\2\u00d9\u00d7\3\2\2\2\u00d9"+
		"\u00da\3\2\2\2\u00da%\3\2\2\2\u00db\u00dd\7\30\2\2\u00dc\u00de\5\62\32"+
		"\2\u00dd\u00dc\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e9"+
		"\5\36\20\2\u00e0\u00e1\7\30\2\2\u00e1\u00e2\5\20\t\2\u00e2\u00e3\7\63"+
		"\2\2\u00e3\u00e4\5\62\32\2\u00e4\u00e5\7\63\2\2\u00e5\u00e6\5(\25\2\u00e6"+
		"\u00e7\5\36\20\2\u00e7\u00e9\3\2\2\2\u00e8\u00db\3\2\2\2\u00e8\u00e0\3"+
		"\2\2\2\u00e9\'\3\2\2\2\u00ea\u00eb\5\64\33\2\u00eb\u00ec\t\2\2\2\u00ec"+
		"\u00ee\5\62\32\2\u00ed\u00ef\7\63\2\2\u00ee\u00ed\3\2\2\2\u00ee\u00ef"+
		"\3\2\2\2\u00ef\u00f6\3\2\2\2\u00f0\u00f1\5\64\33\2\u00f1\u00f3\t\3\2\2"+
		"\u00f2\u00f4\7\63\2\2\u00f3\u00f2\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f6"+
		"\3\2\2\2\u00f5\u00ea\3\2\2\2\u00f5\u00f0\3\2\2\2\u00f6)\3\2\2\2\u00f7"+
		"\u00fa\7\21\2\2\u00f8\u00fb\5\64\33\2\u00f9\u00fb\5\34\17\2\u00fa\u00f8"+
		"\3\2\2\2\u00fa\u00f9\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc"+
		"\u00fd\7-\2\2\u00fd\u00fe\5,\27\2\u00fe\u00ff\7.\2\2\u00ff+\3\2\2\2\u0100"+
		"\u0101\7\b\2\2\u0101\u0102\5\62\32\2\u0102\u0106\7\64\2\2\u0103\u0105"+
		"\5\"\22\2\u0104\u0103\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3\2\2\2"+
		"\u0106\u0107\3\2\2\2\u0107\u010a\3\2\2\2\u0108\u0106\3\2\2\2\u0109\u0100"+
		"\3\2\2\2\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"\u010f\3\2\2\2\u010d\u010b\3\2\2\2\u010e\u0110\5.\30\2\u010f\u010e\3\2"+
		"\2\2\u010f\u0110\3\2\2\2\u0110-\3\2\2\2\u0111\u0112\7\4\2\2\u0112\u0116"+
		"\7\64\2\2\u0113\u0115\5\"\22\2\u0114\u0113\3\2\2\2\u0115\u0118\3\2\2\2"+
		"\u0116\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117/\3\2\2\2\u0118\u0116\3"+
		"\2\2\2\u0119\u011e\5\62\32\2\u011a\u011b\7\62\2\2\u011b\u011d\5\62\32"+
		"\2\u011c\u011a\3\2\2\2\u011d\u0120\3\2\2\2\u011e\u011c\3\2\2\2\u011e\u011f"+
		"\3\2\2\2\u011f\61\3\2\2\2\u0120\u011e\3\2\2\2\u0121\u0122\b\32\1\2\u0122"+
		"\u0123\7+\2\2\u0123\u0124\5\62\32\2\u0124\u0125\7,\2\2\u0125\u012d\3\2"+
		"\2\2\u0126\u012d\5\64\33\2\u0127\u012d\5\34\17\2\u0128\u012d\7Q\2\2\u0129"+
		"\u012d\7U\2\2\u012a\u012d\7`\2\2\u012b\u012d\7!\2\2\u012c\u0121\3\2\2"+
		"\2\u012c\u0126\3\2\2\2\u012c\u0127\3\2\2\2\u012c\u0128\3\2\2\2\u012c\u0129"+
		"\3\2\2\2\u012c\u012a\3\2\2\2\u012c\u012b\3\2\2\2\u012d\u0139\3\2\2\2\u012e"+
		"\u012f\f\f\2\2\u012f\u0130\t\4\2\2\u0130\u0138\5\62\32\r\u0131\u0132\f"+
		"\13\2\2\u0132\u0133\t\5\2\2\u0133\u0138\5\62\32\f\u0134\u0135\f\n\2\2"+
		"\u0135\u0136\t\6\2\2\u0136\u0138\5\62\32\13\u0137\u012e\3\2\2\2\u0137"+
		"\u0131\3\2\2\2\u0137\u0134\3\2\2\2\u0138\u013b\3\2\2\2\u0139\u0137\3\2"+
		"\2\2\u0139\u013a\3\2\2\2\u013a\63\3\2\2\2\u013b\u0139\3\2\2\2\u013c\u0141"+
		"\7%\2\2\u013d\u013e\7/\2\2\u013e\u013f\5\62\32\2\u013f\u0140\7\60\2\2"+
		"\u0140\u0142\3\2\2\2\u0141\u013d\3\2\2\2\u0141\u0142\3\2\2\2\u0142\65"+
		"\3\2\2\2\u0143\u0148\7\34\2\2\u0144\u0148\7\35\2\2\u0145\u0148\7\36\2"+
		"\2\u0146\u0148\7\37\2\2\u0147\u0143\3\2\2\2\u0147\u0144\3\2\2\2\u0147"+
		"\u0145\3\2\2\2\u0147\u0146\3\2\2\2\u0148\67\3\2\2\2+;BISW]aimuz}\u0083"+
		"\u0086\u0090\u009d\u00a9\u00af\u00b7\u00bb\u00c1\u00c4\u00ce\u00d2\u00d9"+
		"\u00dd\u00e8\u00ee\u00f3\u00f5\u00fa\u0106\u010b\u010f\u0116\u011e\u012c"+
		"\u0137\u0139\u0141\u0147";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}