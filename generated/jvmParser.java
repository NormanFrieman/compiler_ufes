// Generated from /home-temp/aluno/Documents/compiler_ufes/jvmParser.g by ANTLR 4.13.2
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
		WS=1, COMMENTS=2, NEGATIVE_FLOAT_LITERAL=3, CHAR_VALUE=4, NEGATIVE_INT_VALUE=5, 
		TYPE_UINT=6, TYPE_INT=7, TYPE_INT8=8, TYPE_INT16=9, TYPE_INT32=10, TYPE_INT64=11, 
		TYPE_FLOAT32=12, TYPE_FLOAT64=13, TYPE_STRING=14, TYPE_BOOL=15, PACKAGE=16, 
		IMPORT=17, FUNCTION=18, VAR=19, CONST=20, RETURN=21, FOR=22, RANGE=23, 
		IF=24, ELSE=25, TRUE=26, FALSE=27, ASSIGN=28, ASSIGN_VAR=29, EQUAL=30, 
		NOT_EQUAL=31, LESS=32, GREATER=33, LESS_EQ=34, GREATER_EQ=35, PLUS=36, 
		MINUS=37, TIMES=38, DIV=39, MOD=40, AND=41, OR=42, NOT=43, DOT=44, COMMA=45, 
		SEMICOLON=46, COLON=47, BRACE_LEFT=48, BRACE_RIGHT=49, BRACKET_LEFT=50, 
		BRACKET_RIGHT=51, PAREN_LEFT=52, PAREN_RIGHT=53, UNDERSCORE=54, INT_DEC=55, 
		INT_HEX=56, INT_OCT=57, INT_BIN=58, FLOAT_LITERAL=59, ID=60, STRING_VALUE=61;
	public static final int
		RULE_program = 0, RULE_init = 1, RULE_stmt_sect = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "init", "stmt_sect"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, "'uint'", "'int'", "'int8'", "'int16'", 
			"'int32'", "'int64'", "'float32'", "'float64'", "'string'", "'bool'", 
			"'package'", "'import'", "'func'", "'var'", "'const'", "'return'", "'for'", 
			"'range'", "'if'", "'else'", "'true'", "'false'", "':='", "'='", "'=='", 
			"'!='", "'<'", "'>'", "'<='", "'>='", "'+'", "'-'", "'*'", "'/'", "'%'", 
			"'&&'", "'||'", "'!'", "'.'", "','", "';'", "':'", "'{'", "'}'", "'['", 
			"']'", "'('", "')'", "'_'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "COMMENTS", "NEGATIVE_FLOAT_LITERAL", "CHAR_VALUE", "NEGATIVE_INT_VALUE", 
			"TYPE_UINT", "TYPE_INT", "TYPE_INT8", "TYPE_INT16", "TYPE_INT32", "TYPE_INT64", 
			"TYPE_FLOAT32", "TYPE_FLOAT64", "TYPE_STRING", "TYPE_BOOL", "PACKAGE", 
			"IMPORT", "FUNCTION", "VAR", "CONST", "RETURN", "FOR", "RANGE", "IF", 
			"ELSE", "TRUE", "FALSE", "ASSIGN", "ASSIGN_VAR", "EQUAL", "NOT_EQUAL", 
			"LESS", "GREATER", "LESS_EQ", "GREATER_EQ", "PLUS", "MINUS", "TIMES", 
			"DIV", "MOD", "AND", "OR", "NOT", "DOT", "COMMA", "SEMICOLON", "COLON", 
			"BRACE_LEFT", "BRACE_RIGHT", "BRACKET_LEFT", "BRACKET_RIGHT", "PAREN_LEFT", 
			"PAREN_RIGHT", "UNDERSCORE", "INT_DEC", "INT_HEX", "INT_OCT", "INT_BIN", 
			"FLOAT_LITERAL", "ID", "STRING_VALUE"
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
			setState(6);
			init();
			setState(7);
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
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PACKAGE) {
				{
				{
				setState(9);
				match(PACKAGE);
				setState(10);
				match(ID);
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(20);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(16);
				match(IMPORT);
				setState(17);
				match(STRING_VALUE);
				}
				}
				setState(22);
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
		public TerminalNode FUNCTION() { return getToken(jvmParser.FUNCTION, 0); }
		public TerminalNode STRING_VALUE() { return getToken(jvmParser.STRING_VALUE, 0); }
		public TerminalNode PAREN_LEFT() { return getToken(jvmParser.PAREN_LEFT, 0); }
		public TerminalNode PAREN_RIGHT() { return getToken(jvmParser.PAREN_RIGHT, 0); }
		public TerminalNode BRACE_LEFT() { return getToken(jvmParser.BRACE_LEFT, 0); }
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
			setState(23);
			match(FUNCTION);
			setState(24);
			match(STRING_VALUE);
			setState(25);
			match(PAREN_LEFT);
			setState(26);
			match(PAREN_RIGHT);
			setState(27);
			match(BRACE_LEFT);
			setState(28);
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

	public static final String _serializedATN =
		"\u0004\u0001=\u001f\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0005\u0001\f\b\u0001\n\u0001\f\u0001\u000f\t\u0001\u0001\u0001"+
		"\u0001\u0001\u0005\u0001\u0013\b\u0001\n\u0001\f\u0001\u0016\t\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0000\u0000\u0003\u0000\u0002\u0004\u0000\u0000\u001d"+
		"\u0000\u0006\u0001\u0000\u0000\u0000\u0002\r\u0001\u0000\u0000\u0000\u0004"+
		"\u0017\u0001\u0000\u0000\u0000\u0006\u0007\u0003\u0002\u0001\u0000\u0007"+
		"\b\u0003\u0004\u0002\u0000\b\u0001\u0001\u0000\u0000\u0000\t\n\u0005\u0010"+
		"\u0000\u0000\n\f\u0005<\u0000\u0000\u000b\t\u0001\u0000\u0000\u0000\f"+
		"\u000f\u0001\u0000\u0000\u0000\r\u000b\u0001\u0000\u0000\u0000\r\u000e"+
		"\u0001\u0000\u0000\u0000\u000e\u0014\u0001\u0000\u0000\u0000\u000f\r\u0001"+
		"\u0000\u0000\u0000\u0010\u0011\u0005\u0011\u0000\u0000\u0011\u0013\u0005"+
		"=\u0000\u0000\u0012\u0010\u0001\u0000\u0000\u0000\u0013\u0016\u0001\u0000"+
		"\u0000\u0000\u0014\u0012\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000"+
		"\u0000\u0000\u0015\u0003\u0001\u0000\u0000\u0000\u0016\u0014\u0001\u0000"+
		"\u0000\u0000\u0017\u0018\u0005\u0012\u0000\u0000\u0018\u0019\u0005=\u0000"+
		"\u0000\u0019\u001a\u00054\u0000\u0000\u001a\u001b\u00055\u0000\u0000\u001b"+
		"\u001c\u00050\u0000\u0000\u001c\u001d\u00051\u0000\u0000\u001d\u0005\u0001"+
		"\u0000\u0000\u0000\u0002\r\u0014";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}