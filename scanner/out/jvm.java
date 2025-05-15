// Generated from jvm.g by ANTLR 4.13.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class jvm extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, COMMENTS=2, DIGITS=3, PACKAGE=4, IMPORT=5, BRACE_LEFT=6, BRACE_RIGHT=7, 
		BRACKET_LEFT=8, BRACKET_RIGHT=9, PARENTHESES_LEFT=10, PARENTHESES_RIGHT=11, 
		FUNCTION=12, ID=13, DOT=14, STRING_VALUE=15, INT=16, STRING=17, VAR=18, 
		FOR=19, RANGE=20, IF=21, ELSE=22, EQUAL=23, PLUS=24, MINUS=25, TIMES=26, 
		OVER=27, ASSING=28, COMMA=29, BIGGER=30, UNDERSCORE=31;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WS", "COMMENTS", "DIGITS", "PACKAGE", "IMPORT", "BRACE_LEFT", "BRACE_RIGHT", 
			"BRACKET_LEFT", "BRACKET_RIGHT", "PARENTHESES_LEFT", "PARENTHESES_RIGHT", 
			"FUNCTION", "ID", "DOT", "STRING_VALUE", "INT", "STRING", "VAR", "FOR", 
			"RANGE", "IF", "ELSE", "EQUAL", "PLUS", "MINUS", "TIMES", "OVER", "ASSING", 
			"COMMA", "BIGGER", "UNDERSCORE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'package'", "'import'", "'{'", "'}'", "'['", 
			"']'", "'('", "')'", "'func'", null, "'.'", null, "'int'", "'string'", 
			"'var'", "'for'", "'range'", "'if'", "'else'", "'=='", "'+'", "'-'", 
			"'*'", "'/'", "':='", "','", "'>'", "'_'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "COMMENTS", "DIGITS", "PACKAGE", "IMPORT", "BRACE_LEFT", 
			"BRACE_RIGHT", "BRACKET_LEFT", "BRACKET_RIGHT", "PARENTHESES_LEFT", "PARENTHESES_RIGHT", 
			"FUNCTION", "ID", "DOT", "STRING_VALUE", "INT", "STRING", "VAR", "FOR", 
			"RANGE", "IF", "ELSE", "EQUAL", "PLUS", "MINUS", "TIMES", "OVER", "ASSING", 
			"COMMA", "BIGGER", "UNDERSCORE"
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


	public jvm(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "jvm.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u001f\u00bb\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d"+
		"\u0002\u001e\u0007\u001e\u0001\u0000\u0004\u0000A\b\u0000\u000b\u0000"+
		"\f\u0000B\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0005\u0001K\b\u0001\n\u0001\f\u0001N\t\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0002\u0004\u0002S\b\u0002\u000b\u0002\f\u0002T\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n"+
		"\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\f\u0004\fx\b\f\u000b\f\f\fy\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0005"+
		"\u000e\u0080\b\u000e\n\u000e\f\u000e\u0083\t\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019"+
		"\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001c"+
		"\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0000\u0000"+
		"\u001f\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006"+
		"\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e"+
		"\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017"+
		"/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f\u0001\u0000\u0005"+
		"\u0002\u0000\t\n  \u0001\u0000\n\n\u0001\u000009\u0002\u0000AZaz\u0001"+
		"\u0000\"\"\u00bf\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001"+
		"\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000"+
		"\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000"+
		"\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000"+
		"\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000"+
		"\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000"+
		"\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000"+
		"\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000"+
		"%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001"+
		"\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000"+
		"\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u0000"+
		"3\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001"+
		"\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000"+
		"\u0000\u0000=\u0001\u0000\u0000\u0000\u0001@\u0001\u0000\u0000\u0000\u0003"+
		"F\u0001\u0000\u0000\u0000\u0005R\u0001\u0000\u0000\u0000\u0007V\u0001"+
		"\u0000\u0000\u0000\t^\u0001\u0000\u0000\u0000\u000be\u0001\u0000\u0000"+
		"\u0000\rg\u0001\u0000\u0000\u0000\u000fi\u0001\u0000\u0000\u0000\u0011"+
		"k\u0001\u0000\u0000\u0000\u0013m\u0001\u0000\u0000\u0000\u0015o\u0001"+
		"\u0000\u0000\u0000\u0017q\u0001\u0000\u0000\u0000\u0019w\u0001\u0000\u0000"+
		"\u0000\u001b{\u0001\u0000\u0000\u0000\u001d}\u0001\u0000\u0000\u0000\u001f"+
		"\u0086\u0001\u0000\u0000\u0000!\u008a\u0001\u0000\u0000\u0000#\u0091\u0001"+
		"\u0000\u0000\u0000%\u0095\u0001\u0000\u0000\u0000\'\u0099\u0001\u0000"+
		"\u0000\u0000)\u009f\u0001\u0000\u0000\u0000+\u00a2\u0001\u0000\u0000\u0000"+
		"-\u00a7\u0001\u0000\u0000\u0000/\u00aa\u0001\u0000\u0000\u00001\u00ac"+
		"\u0001\u0000\u0000\u00003\u00ae\u0001\u0000\u0000\u00005\u00b0\u0001\u0000"+
		"\u0000\u00007\u00b2\u0001\u0000\u0000\u00009\u00b5\u0001\u0000\u0000\u0000"+
		";\u00b7\u0001\u0000\u0000\u0000=\u00b9\u0001\u0000\u0000\u0000?A\u0007"+
		"\u0000\u0000\u0000@?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000"+
		"B@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000"+
		"\u0000DE\u0006\u0000\u0000\u0000E\u0002\u0001\u0000\u0000\u0000FG\u0005"+
		"/\u0000\u0000GH\u0005/\u0000\u0000HL\u0001\u0000\u0000\u0000IK\b\u0001"+
		"\u0000\u0000JI\u0001\u0000\u0000\u0000KN\u0001\u0000\u0000\u0000LJ\u0001"+
		"\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000MO\u0001\u0000\u0000\u0000"+
		"NL\u0001\u0000\u0000\u0000OP\u0006\u0001\u0000\u0000P\u0004\u0001\u0000"+
		"\u0000\u0000QS\u0007\u0002\u0000\u0000RQ\u0001\u0000\u0000\u0000ST\u0001"+
		"\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000"+
		"U\u0006\u0001\u0000\u0000\u0000VW\u0005p\u0000\u0000WX\u0005a\u0000\u0000"+
		"XY\u0005c\u0000\u0000YZ\u0005k\u0000\u0000Z[\u0005a\u0000\u0000[\\\u0005"+
		"g\u0000\u0000\\]\u0005e\u0000\u0000]\b\u0001\u0000\u0000\u0000^_\u0005"+
		"i\u0000\u0000_`\u0005m\u0000\u0000`a\u0005p\u0000\u0000ab\u0005o\u0000"+
		"\u0000bc\u0005r\u0000\u0000cd\u0005t\u0000\u0000d\n\u0001\u0000\u0000"+
		"\u0000ef\u0005{\u0000\u0000f\f\u0001\u0000\u0000\u0000gh\u0005}\u0000"+
		"\u0000h\u000e\u0001\u0000\u0000\u0000ij\u0005[\u0000\u0000j\u0010\u0001"+
		"\u0000\u0000\u0000kl\u0005]\u0000\u0000l\u0012\u0001\u0000\u0000\u0000"+
		"mn\u0005(\u0000\u0000n\u0014\u0001\u0000\u0000\u0000op\u0005)\u0000\u0000"+
		"p\u0016\u0001\u0000\u0000\u0000qr\u0005f\u0000\u0000rs\u0005u\u0000\u0000"+
		"st\u0005n\u0000\u0000tu\u0005c\u0000\u0000u\u0018\u0001\u0000\u0000\u0000"+
		"vx\u0007\u0003\u0000\u0000wv\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000"+
		"\u0000yw\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z\u001a\u0001"+
		"\u0000\u0000\u0000{|\u0005.\u0000\u0000|\u001c\u0001\u0000\u0000\u0000"+
		"}\u0081\u0005\"\u0000\u0000~\u0080\b\u0004\u0000\u0000\u007f~\u0001\u0000"+
		"\u0000\u0000\u0080\u0083\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000"+
		"\u0000\u0000\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u0084\u0001\u0000"+
		"\u0000\u0000\u0083\u0081\u0001\u0000\u0000\u0000\u0084\u0085\u0005\"\u0000"+
		"\u0000\u0085\u001e\u0001\u0000\u0000\u0000\u0086\u0087\u0005i\u0000\u0000"+
		"\u0087\u0088\u0005n\u0000\u0000\u0088\u0089\u0005t\u0000\u0000\u0089 "+
		"\u0001\u0000\u0000\u0000\u008a\u008b\u0005s\u0000\u0000\u008b\u008c\u0005"+
		"t\u0000\u0000\u008c\u008d\u0005r\u0000\u0000\u008d\u008e\u0005i\u0000"+
		"\u0000\u008e\u008f\u0005n\u0000\u0000\u008f\u0090\u0005g\u0000\u0000\u0090"+
		"\"\u0001\u0000\u0000\u0000\u0091\u0092\u0005v\u0000\u0000\u0092\u0093"+
		"\u0005a\u0000\u0000\u0093\u0094\u0005r\u0000\u0000\u0094$\u0001\u0000"+
		"\u0000\u0000\u0095\u0096\u0005f\u0000\u0000\u0096\u0097\u0005o\u0000\u0000"+
		"\u0097\u0098\u0005r\u0000\u0000\u0098&\u0001\u0000\u0000\u0000\u0099\u009a"+
		"\u0005r\u0000\u0000\u009a\u009b\u0005a\u0000\u0000\u009b\u009c\u0005n"+
		"\u0000\u0000\u009c\u009d\u0005g\u0000\u0000\u009d\u009e\u0005e\u0000\u0000"+
		"\u009e(\u0001\u0000\u0000\u0000\u009f\u00a0\u0005i\u0000\u0000\u00a0\u00a1"+
		"\u0005f\u0000\u0000\u00a1*\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005e"+
		"\u0000\u0000\u00a3\u00a4\u0005l\u0000\u0000\u00a4\u00a5\u0005s\u0000\u0000"+
		"\u00a5\u00a6\u0005e\u0000\u0000\u00a6,\u0001\u0000\u0000\u0000\u00a7\u00a8"+
		"\u0005=\u0000\u0000\u00a8\u00a9\u0005=\u0000\u0000\u00a9.\u0001\u0000"+
		"\u0000\u0000\u00aa\u00ab\u0005+\u0000\u0000\u00ab0\u0001\u0000\u0000\u0000"+
		"\u00ac\u00ad\u0005-\u0000\u0000\u00ad2\u0001\u0000\u0000\u0000\u00ae\u00af"+
		"\u0005*\u0000\u0000\u00af4\u0001\u0000\u0000\u0000\u00b0\u00b1\u0005/"+
		"\u0000\u0000\u00b16\u0001\u0000\u0000\u0000\u00b2\u00b3\u0005:\u0000\u0000"+
		"\u00b3\u00b4\u0005=\u0000\u0000\u00b48\u0001\u0000\u0000\u0000\u00b5\u00b6"+
		"\u0005,\u0000\u0000\u00b6:\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005>"+
		"\u0000\u0000\u00b8<\u0001\u0000\u0000\u0000\u00b9\u00ba\u0005_\u0000\u0000"+
		"\u00ba>\u0001\u0000\u0000\u0000\u0006\u0000BLTy\u0081\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}