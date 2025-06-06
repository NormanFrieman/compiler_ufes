// Generated from /home/edsilva/Documents/compiler_ufes/jvmParser.g by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class jvmParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, COMMENTS=2, TYPE_UINT=3, TYPE_INT=4, TYPE_INT8=5, TYPE_INT16=6, 
		TYPE_INT32=7, TYPE_INT64=8, TYPE_FLOAT32=9, TYPE_FLOAT64=10, TYPE_STRING=11, 
		TYPE_BOOL=12, PACKAGE=13, IMPORT=14, FUNCTION=15, VAR=16, CONST=17, RETURN=18, 
		FOR=19, RANGE=20, IF=21, ELSE=22, TRUE=23, FALSE=24, ASSIGN=25, ASSIGN_VAR=26, 
		EQUAL=27, NOT_EQUAL=28, LESS=29, GREATER=30, LESS_EQ=31, GREATER_EQ=32, 
		PLUS=33, MINUS=34, TIMES=35, DIV=36, MOD=37, AND=38, OR=39, NOT=40, DOT=41, 
		COMMA=42, SEMICOLON=43, COLON=44, BRACE_LEFT=45, BRACE_RIGHT=46, BRACKET_LEFT=47, 
		BRACKET_RIGHT=48, PAREN_LEFT=49, PAREN_RIGHT=50, UNDERSCORE=51, INT_DEC=52, 
		INT_HEX=53, INT_OCT=54, INT_BIN=55, FLOAT_LITERAL=56, ID=57, STRING_VALUE=58, 
		NEGATIVE_FLOAT_LITERAL=59, CHAR_VALUE=60, NEGATIVE_INT_VALUE=61;
	public static final int
		RULE_program = 0, RULE_init = 1, RULE_stmt_sect = 2, RULE_var_declaration = 3, 
		RULE_param_declaration = 4, RULE_function_declaration = 5, RULE_function_call = 6, 
		RULE_type = 7, RULE_value = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "init", "stmt_sect", "var_declaration", "param_declaration", 
			"function_declaration", "function_call", "type", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'uint'", "'int'", "'int8'", "'int16'", "'int32'", 
			"'int64'", "'float32'", "'float64'", "'string'", "'bool'", "'package'", 
			"'import'", "'func'", "'var'", "'const'", "'return'", "'for'", "'range'", 
			"'if'", "'else'", "'true'", "'false'", "':='", "'='", "'=='", "'!='", 
			"'<'", "'>'", "'<='", "'>='", "'+'", "'-'", "'*'", "'/'", "'%'", "'&&'", 
			"'||'", "'!'", "'.'", "','", "';'", "':'", "'{'", "'}'", "'['", "']'", 
			"'('", "')'", "'_'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "COMMENTS", "TYPE_UINT", "TYPE_INT", "TYPE_INT8", "TYPE_INT16", 
			"TYPE_INT32", "TYPE_INT64", "TYPE_FLOAT32", "TYPE_FLOAT64", "TYPE_STRING", 
			"TYPE_BOOL", "PACKAGE", "IMPORT", "FUNCTION", "VAR", "CONST", "RETURN", 
			"FOR", "RANGE", "IF", "ELSE", "TRUE", "FALSE", "ASSIGN", "ASSIGN_VAR", 
			"EQUAL", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQ", "GREATER_EQ", "PLUS", 
			"MINUS", "TIMES", "DIV", "MOD", "AND", "OR", "NOT", "DOT", "COMMA", "SEMICOLON", 
			"COLON", "BRACE_LEFT", "BRACE_RIGHT", "BRACKET_LEFT", "BRACKET_RIGHT", 
			"PAREN_LEFT", "PAREN_RIGHT", "UNDERSCORE", "INT_DEC", "INT_HEX", "INT_OCT", 
			"INT_BIN", "FLOAT_LITERAL", "ID", "STRING_VALUE", "NEGATIVE_FLOAT_LITERAL", 
			"CHAR_VALUE", "NEGATIVE_INT_VALUE"
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
	public String getGrammarFileName() { return "jvmParser.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public jvmParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public Stmt_sectContext stmt_sect() {
			return getRuleContext(Stmt_sectContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			init();
			setState(19);
			stmt_sect();
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

	@SuppressWarnings("CheckReturnValue")
	public static class InitContext extends ParserRuleContext {
		public List<TerminalNode> PACKAGE() { return getTokens(jvmParser.PACKAGE); }
		public TerminalNode PACKAGE(int i) {
			return getToken(jvmParser.PACKAGE, i);
		}
		public List<TerminalNode> ID() { return getTokens(jvmParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(jvmParser.ID, i);
		}
		public List<TerminalNode> IMPORT() { return getTokens(jvmParser.IMPORT); }
		public TerminalNode IMPORT(int i) {
			return getToken(jvmParser.IMPORT, i);
		}
		public List<TerminalNode> STRING_VALUE() { return getTokens(jvmParser.STRING_VALUE); }
		public TerminalNode STRING_VALUE(int i) {
			return getToken(jvmParser.STRING_VALUE, i);
		}
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_init);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PACKAGE) {
				{
				{
				setState(21);
				match(PACKAGE);
				setState(22);
				match(ID);
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(28);
				match(IMPORT);
				setState(29);
				match(STRING_VALUE);
				}
				}
				setState(34);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Stmt_sectContext extends ParserRuleContext {
		public Function_declarationContext function_declaration() {
			return getRuleContext(Function_declarationContext.class,0);
		}
		public TerminalNode BRACE_LEFT() { return getToken(jvmParser.BRACE_LEFT, 0); }
		public Function_callContext function_call() {
			return getRuleContext(Function_callContext.class,0);
		}
		public TerminalNode BRACE_RIGHT() { return getToken(jvmParser.BRACE_RIGHT, 0); }
		public Stmt_sectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt_sect; }
	}

	public final Stmt_sectContext stmt_sect() throws RecognitionException {
		Stmt_sectContext _localctx = new Stmt_sectContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stmt_sect);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			function_declaration();
			setState(36);
			match(BRACE_LEFT);
			setState(37);
			function_call();
			setState(38);
			match(BRACE_RIGHT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Var_declarationContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(jvmParser.VAR, 0); }
		public TerminalNode ID() { return getToken(jvmParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Var_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_declaration; }
	}

	public final Var_declarationContext var_declaration() throws RecognitionException {
		Var_declarationContext _localctx = new Var_declarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_var_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(VAR);
			setState(41);
			match(ID);
			setState(42);
			type();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Param_declarationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(jvmParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Param_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_declaration; }
	}

	public final Param_declarationContext param_declaration() throws RecognitionException {
		Param_declarationContext _localctx = new Param_declarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_param_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(ID);
			setState(45);
			type();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Function_declarationContext extends ParserRuleContext {
		public TerminalNode FUNCTION() { return getToken(jvmParser.FUNCTION, 0); }
		public TerminalNode ID() { return getToken(jvmParser.ID, 0); }
		public TerminalNode PAREN_LEFT() { return getToken(jvmParser.PAREN_LEFT, 0); }
		public TerminalNode PAREN_RIGHT() { return getToken(jvmParser.PAREN_RIGHT, 0); }
		public List<Param_declarationContext> param_declaration() {
			return getRuleContexts(Param_declarationContext.class);
		}
		public Param_declarationContext param_declaration(int i) {
			return getRuleContext(Param_declarationContext.class,i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(jvmParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jvmParser.COMMA, i);
		}
		public Function_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_declaration; }
	}

	public final Function_declarationContext function_declaration() throws RecognitionException {
		Function_declarationContext _localctx = new Function_declarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_function_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(FUNCTION);
			setState(48);
			match(ID);
			setState(49);
			match(PAREN_LEFT);
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(50);
				param_declaration();
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(51);
					match(COMMA);
					setState(52);
					param_declaration();
					}
					}
					setState(57);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(60);
			match(PAREN_RIGHT);
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8184L) != 0)) {
				{
				setState(61);
				type();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Function_callContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(jvmParser.ID, 0); }
		public TerminalNode PAREN_LEFT() { return getToken(jvmParser.PAREN_LEFT, 0); }
		public TerminalNode PAREN_RIGHT() { return getToken(jvmParser.PAREN_RIGHT, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public List<TerminalNode> DOT() { return getTokens(jvmParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(jvmParser.DOT, i);
		}
		public List<Function_callContext> function_call() {
			return getRuleContexts(Function_callContext.class);
		}
		public Function_callContext function_call(int i) {
			return getRuleContext(Function_callContext.class,i);
		}
		public Function_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_call; }
	}

	public final Function_callContext function_call() throws RecognitionException {
		Function_callContext _localctx = new Function_callContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_function_call);
		int _la;
		try {
			int _alt;
			setState(77);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				match(ID);
				setState(65);
				match(PAREN_LEFT);
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STRING_VALUE) {
					{
					setState(66);
					value();
					}
				}

				setState(69);
				match(PAREN_RIGHT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(70);
				match(ID);
				setState(73); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(71);
						match(DOT);
						setState(72);
						function_call();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(75); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode TYPE_UINT() { return getToken(jvmParser.TYPE_UINT, 0); }
		public TerminalNode TYPE_INT() { return getToken(jvmParser.TYPE_INT, 0); }
		public TerminalNode TYPE_INT8() { return getToken(jvmParser.TYPE_INT8, 0); }
		public TerminalNode TYPE_INT16() { return getToken(jvmParser.TYPE_INT16, 0); }
		public TerminalNode TYPE_INT32() { return getToken(jvmParser.TYPE_INT32, 0); }
		public TerminalNode TYPE_INT64() { return getToken(jvmParser.TYPE_INT64, 0); }
		public TerminalNode TYPE_FLOAT32() { return getToken(jvmParser.TYPE_FLOAT32, 0); }
		public TerminalNode TYPE_FLOAT64() { return getToken(jvmParser.TYPE_FLOAT64, 0); }
		public TerminalNode TYPE_STRING() { return getToken(jvmParser.TYPE_STRING, 0); }
		public TerminalNode TYPE_BOOL() { return getToken(jvmParser.TYPE_BOOL, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8184L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode STRING_VALUE() { return getToken(jvmParser.STRING_VALUE, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(STRING_VALUE);
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

	public static final String _serializedATN =
		"\u0004\u0001=T\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002"+
		"\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005"+
		"\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007"+
		"\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0005\u0001"+
		"\u0018\b\u0001\n\u0001\f\u0001\u001b\t\u0001\u0001\u0001\u0001\u0001\u0005"+
		"\u0001\u001f\b\u0001\n\u0001\f\u0001\"\t\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u00056\b\u0005"+
		"\n\u0005\f\u00059\t\u0005\u0003\u0005;\b\u0005\u0001\u0005\u0001\u0005"+
		"\u0003\u0005?\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006"+
		"D\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0004\u0006"+
		"J\b\u0006\u000b\u0006\f\u0006K\u0003\u0006N\b\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0000\u0000\t\u0000\u0002\u0004\u0006\b"+
		"\n\f\u000e\u0010\u0000\u0001\u0001\u0000\u0003\fR\u0000\u0012\u0001\u0000"+
		"\u0000\u0000\u0002\u0019\u0001\u0000\u0000\u0000\u0004#\u0001\u0000\u0000"+
		"\u0000\u0006(\u0001\u0000\u0000\u0000\b,\u0001\u0000\u0000\u0000\n/\u0001"+
		"\u0000\u0000\u0000\fM\u0001\u0000\u0000\u0000\u000eO\u0001\u0000\u0000"+
		"\u0000\u0010Q\u0001\u0000\u0000\u0000\u0012\u0013\u0003\u0002\u0001\u0000"+
		"\u0013\u0014\u0003\u0004\u0002\u0000\u0014\u0001\u0001\u0000\u0000\u0000"+
		"\u0015\u0016\u0005\r\u0000\u0000\u0016\u0018\u00059\u0000\u0000\u0017"+
		"\u0015\u0001\u0000\u0000\u0000\u0018\u001b\u0001\u0000\u0000\u0000\u0019"+
		"\u0017\u0001\u0000\u0000\u0000\u0019\u001a\u0001\u0000\u0000\u0000\u001a"+
		" \u0001\u0000\u0000\u0000\u001b\u0019\u0001\u0000\u0000\u0000\u001c\u001d"+
		"\u0005\u000e\u0000\u0000\u001d\u001f\u0005:\u0000\u0000\u001e\u001c\u0001"+
		"\u0000\u0000\u0000\u001f\"\u0001\u0000\u0000\u0000 \u001e\u0001\u0000"+
		"\u0000\u0000 !\u0001\u0000\u0000\u0000!\u0003\u0001\u0000\u0000\u0000"+
		"\" \u0001\u0000\u0000\u0000#$\u0003\n\u0005\u0000$%\u0005-\u0000\u0000"+
		"%&\u0003\f\u0006\u0000&\'\u0005.\u0000\u0000\'\u0005\u0001\u0000\u0000"+
		"\u0000()\u0005\u0010\u0000\u0000)*\u00059\u0000\u0000*+\u0003\u000e\u0007"+
		"\u0000+\u0007\u0001\u0000\u0000\u0000,-\u00059\u0000\u0000-.\u0003\u000e"+
		"\u0007\u0000.\t\u0001\u0000\u0000\u0000/0\u0005\u000f\u0000\u000001\u0005"+
		"9\u0000\u00001:\u00051\u0000\u000027\u0003\b\u0004\u000034\u0005*\u0000"+
		"\u000046\u0003\b\u0004\u000053\u0001\u0000\u0000\u000069\u0001\u0000\u0000"+
		"\u000075\u0001\u0000\u0000\u000078\u0001\u0000\u0000\u00008;\u0001\u0000"+
		"\u0000\u000097\u0001\u0000\u0000\u0000:2\u0001\u0000\u0000\u0000:;\u0001"+
		"\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000<>\u00052\u0000\u0000=?\u0003"+
		"\u000e\u0007\u0000>=\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000"+
		"?\u000b\u0001\u0000\u0000\u0000@A\u00059\u0000\u0000AC\u00051\u0000\u0000"+
		"BD\u0003\u0010\b\u0000CB\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000"+
		"DE\u0001\u0000\u0000\u0000EN\u00052\u0000\u0000FI\u00059\u0000\u0000G"+
		"H\u0005)\u0000\u0000HJ\u0003\f\u0006\u0000IG\u0001\u0000\u0000\u0000J"+
		"K\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000"+
		"\u0000LN\u0001\u0000\u0000\u0000M@\u0001\u0000\u0000\u0000MF\u0001\u0000"+
		"\u0000\u0000N\r\u0001\u0000\u0000\u0000OP\u0007\u0000\u0000\u0000P\u000f"+
		"\u0001\u0000\u0000\u0000QR\u0005:\u0000\u0000R\u0011\u0001\u0000\u0000"+
		"\u0000\b\u0019 7:>CKM";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}