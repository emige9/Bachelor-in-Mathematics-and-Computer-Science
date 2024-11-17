/* The following code was generated by JFlex 1.7.0 */

import java_cup.runtime.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>PLXC.flex</tt>
 */
class Yylex implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int STRING = 2;
  public static final int UNICODE = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2, 2
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\12\0\1\45\1\46\1\46\1\45\23\0\1\33\1\53\3\0\1\36"+
    "\1\44\1\21\1\22\1\42\1\40\1\30\1\41\1\31\1\43\1\50"+
    "\11\47\1\0\1\27\1\34\1\32\1\35\2\0\4\56\1\51\25\56"+
    "\1\23\1\54\1\24\1\0\1\52\1\0\1\17\1\56\1\20\1\6"+
    "\1\3\1\2\1\16\1\11\1\1\2\56\1\4\1\56\1\14\1\7"+
    "\1\13\1\56\1\12\1\5\1\15\1\55\1\56\1\10\3\56\1\25"+
    "\1\37\1\26\7\0\1\46\u1fa2\0\1\46\1\46\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\udfe6\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\12\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17"+
    "\2\1\1\20\1\21\1\22\1\23\1\1\2\24\1\25"+
    "\1\26\1\27\1\30\1\0\1\31\6\2\1\32\3\2"+
    "\1\33\1\34\1\35\1\36\1\37\1\40\1\41\2\0"+
    "\1\42\1\30\1\43\1\0\1\44\1\2\1\45\6\2"+
    "\1\0\1\46\1\0\1\2\1\47\4\2\1\50\1\0"+
    "\1\33\1\51\1\52\2\2\1\53\1\54\1\55\1\56";

  private static int [] zzUnpackAction() {
    int [] result = new int[94];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\57\0\136\0\215\0\274\0\353\0\u011a\0\u0149"+
    "\0\u0178\0\u01a7\0\u01d6\0\u0205\0\u0234\0\u0263\0\215\0\215"+
    "\0\215\0\215\0\215\0\215\0\215\0\215\0\u0292\0\u02c1"+
    "\0\u02f0\0\u031f\0\u034e\0\u037d\0\u03ac\0\215\0\215\0\215"+
    "\0\215\0\u03db\0\u040a\0\u0439\0\215\0\u0468\0\215\0\u0497"+
    "\0\u04c6\0\u01d6\0\u04f5\0\u0524\0\u0553\0\u0582\0\u05b1\0\u05e0"+
    "\0\u01d6\0\u060f\0\u063e\0\u066d\0\u069c\0\215\0\215\0\215"+
    "\0\215\0\215\0\215\0\u06cb\0\u0439\0\215\0\215\0\215"+
    "\0\u06fa\0\u01d6\0\u0729\0\u01d6\0\u0758\0\u0787\0\u07b6\0\u07e5"+
    "\0\u0814\0\u0843\0\u0872\0\215\0\u08a1\0\u08d0\0\u01d6\0\u08ff"+
    "\0\u092e\0\u095d\0\u098c\0\u01d6\0\u09bb\0\u09bb\0\215\0\u01d6"+
    "\0\u09ea\0\u0a19\0\u01d6\0\u01d6\0\u01d6\0\u01d6";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[94];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13"+
    "\1\14\2\13\1\15\4\13\1\16\1\17\1\20\1\21"+
    "\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31"+
    "\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41"+
    "\1\42\2\4\1\43\1\44\2\13\1\45\1\4\2\13"+
    "\45\46\1\0\5\46\1\47\1\50\2\46\1\0\20\51"+
    "\26\0\3\51\3\0\2\51\60\0\1\13\1\52\11\13"+
    "\1\53\4\13\26\0\4\13\2\0\2\13\1\0\3\13"+
    "\1\54\2\13\1\55\11\13\26\0\4\13\2\0\2\13"+
    "\1\0\3\13\1\56\14\13\26\0\4\13\2\0\2\13"+
    "\1\0\2\13\1\57\15\13\26\0\4\13\2\0\2\13"+
    "\1\0\14\13\1\60\3\13\26\0\4\13\2\0\2\13"+
    "\1\0\6\13\1\61\11\13\26\0\4\13\2\0\2\13"+
    "\1\0\20\13\26\0\4\13\2\0\2\13\1\0\10\13"+
    "\1\62\7\13\26\0\4\13\2\0\2\13\1\0\11\13"+
    "\1\63\6\13\26\0\4\13\2\0\2\13\1\0\10\13"+
    "\1\64\7\13\26\0\4\13\2\0\2\13\47\0\2\65"+
    "\40\0\1\66\56\0\1\67\56\0\1\70\56\0\1\71"+
    "\62\0\1\72\57\0\1\73\17\0\45\74\2\0\10\74"+
    "\31\0\1\65\15\0\2\43\37\0\1\65\15\0\2\75"+
    "\6\0\45\46\1\0\5\46\2\0\2\46\53\0\1\76"+
    "\1\77\1\100\2\0\20\101\26\0\3\101\3\0\2\101"+
    "\1\0\14\13\1\102\3\13\26\0\4\13\2\0\2\13"+
    "\1\0\6\13\1\103\11\13\26\0\4\13\2\0\2\13"+
    "\1\0\11\13\1\104\6\13\26\0\4\13\2\0\2\13"+
    "\1\0\4\13\1\105\13\13\26\0\4\13\2\0\2\13"+
    "\1\0\13\13\1\106\4\13\26\0\4\13\2\0\2\13"+
    "\1\0\11\13\1\107\6\13\26\0\4\13\2\0\2\13"+
    "\1\0\1\110\17\13\26\0\4\13\2\0\2\13\1\0"+
    "\1\111\17\13\26\0\4\13\2\0\2\13\1\0\16\13"+
    "\1\112\1\13\26\0\4\13\2\0\2\13\3\0\1\113"+
    "\43\0\2\65\1\113\51\0\1\114\13\0\20\115\26\0"+
    "\3\115\3\0\2\115\1\0\16\13\1\116\1\13\26\0"+
    "\4\13\2\0\2\13\1\0\2\13\1\117\15\13\26\0"+
    "\4\13\2\0\2\13\1\0\15\13\1\120\2\13\26\0"+
    "\4\13\2\0\2\13\1\0\1\121\17\13\26\0\4\13"+
    "\2\0\2\13\1\0\3\13\1\122\14\13\26\0\4\13"+
    "\2\0\2\13\1\0\13\13\1\123\4\13\26\0\4\13"+
    "\2\0\2\13\1\0\11\13\1\124\6\13\26\0\4\13"+
    "\2\0\2\13\40\0\2\125\5\0\2\126\7\0\20\127"+
    "\26\0\3\127\3\0\2\127\1\0\14\13\1\130\3\13"+
    "\26\0\4\13\2\0\2\13\1\0\14\13\1\131\3\13"+
    "\26\0\4\13\2\0\2\13\1\0\13\13\1\132\4\13"+
    "\26\0\4\13\2\0\2\13\1\0\2\13\1\133\15\13"+
    "\26\0\4\13\2\0\2\13\1\0\14\13\1\134\3\13"+
    "\26\0\4\13\2\0\2\13\47\0\2\126\7\0\10\13"+
    "\1\135\7\13\26\0\4\13\2\0\2\13\1\0\15\13"+
    "\1\136\2\13\26\0\4\13\2\0\2\13";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2632];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\11\12\1\10\11\7\1\4\11\3\1\1\11"+
    "\1\1\1\11\1\1\1\0\14\1\6\11\2\0\3\11"+
    "\1\0\11\1\1\0\1\11\1\0\7\1\1\0\1\1"+
    "\1\11\7\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[94];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true iff the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true iff the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */
	StringBuffer str = new StringBuffer();


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Yylex(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 168) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { return new java_cup.runtime.Symbol(sym.EOF); }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { 
            } 
            // fall through
          case 47: break;
          case 2: 
            { return new Symbol(sym.IDENT, yytext());
            } 
            // fall through
          case 48: break;
          case 3: 
            { return new Symbol(sym.AP);
            } 
            // fall through
          case 49: break;
          case 4: 
            { return new Symbol(sym.CP);
            } 
            // fall through
          case 50: break;
          case 5: 
            { return new Symbol(sym.AC);
            } 
            // fall through
          case 51: break;
          case 6: 
            { return new Symbol(sym.CC);
            } 
            // fall through
          case 52: break;
          case 7: 
            { return new Symbol(sym.ALL);
            } 
            // fall through
          case 53: break;
          case 8: 
            { return new Symbol(sym.CLL);
            } 
            // fall through
          case 54: break;
          case 9: 
            { return new Symbol(sym.PYC);
            } 
            // fall through
          case 55: break;
          case 10: 
            { return new Symbol(sym.COMA);
            } 
            // fall through
          case 56: break;
          case 11: 
            { return new Symbol(sym.PUNTO);
            } 
            // fall through
          case 57: break;
          case 12: 
            { return new Symbol(sym.ASIG);
            } 
            // fall through
          case 58: break;
          case 13: 
            { return new Symbol(sym.NOT);
            } 
            // fall through
          case 59: break;
          case 14: 
            { return new Symbol(sym.MENOR);
            } 
            // fall through
          case 60: break;
          case 15: 
            { return new Symbol(sym.MAYOR);
            } 
            // fall through
          case 61: break;
          case 16: 
            { return new Symbol(sym.MAS);
            } 
            // fall through
          case 62: break;
          case 17: 
            { return new Symbol(sym.MENOS);
            } 
            // fall through
          case 63: break;
          case 18: 
            { return new Symbol(sym.POR);
            } 
            // fall through
          case 64: break;
          case 19: 
            { return new Symbol(sym.DIV);
            } 
            // fall through
          case 65: break;
          case 20: 
            { return new Symbol(sym.ENTERO, yytext());
            } 
            // fall through
          case 66: break;
          case 21: 
            { str.setLength(0); yybegin(STRING);
            } 
            // fall through
          case 67: break;
          case 22: 
            { str.append(yytext());
            } 
            // fall through
          case 68: break;
          case 23: 
            { yybegin(YYINITIAL); return new Symbol(sym.STR, str.toString());
            } 
            // fall through
          case 69: break;
          case 24: 
            { str.append('\\');
            } 
            // fall through
          case 70: break;
          case 25: 
            { return new Symbol(sym.IF);
            } 
            // fall through
          case 71: break;
          case 26: 
            { return new Symbol(sym.DO);
            } 
            // fall through
          case 72: break;
          case 27: 
            { return new Symbol(sym.REAL, yytext());
            } 
            // fall through
          case 73: break;
          case 28: 
            { return new Symbol(sym.IGUAL);
            } 
            // fall through
          case 74: break;
          case 29: 
            { return new Symbol(sym.NOIGUAL);
            } 
            // fall through
          case 75: break;
          case 30: 
            { return new Symbol(sym.MENORIGUAL);
            } 
            // fall through
          case 76: break;
          case 31: 
            { return new Symbol(sym.MAYORIGUAL);
            } 
            // fall through
          case 77: break;
          case 32: 
            { return new Symbol(sym.AND);
            } 
            // fall through
          case 78: break;
          case 33: 
            { return new Symbol(sym.OR);
            } 
            // fall through
          case 79: break;
          case 34: 
            { str.append('\"');
            } 
            // fall through
          case 80: break;
          case 35: 
            { yybegin(UNICODE);
            } 
            // fall through
          case 81: break;
          case 36: 
            { return new Symbol(sym.INT);
            } 
            // fall through
          case 82: break;
          case 37: 
            { return new Symbol(sym.FOR);
            } 
            // fall through
          case 83: break;
          case 38: 
            { return new Symbol(sym.CH,String.valueOf((int)(yytext().charAt(1))));
            } 
            // fall through
          case 84: break;
          case 39: 
            { return new Symbol(sym.ELSE);
            } 
            // fall through
          case 85: break;
          case 40: 
            { return new Symbol(sym.CHAR);
            } 
            // fall through
          case 86: break;
          case 41: 
            { int code = Integer.parseInt(yytext(),16); str.append((char)code); yybegin(STRING);
            } 
            // fall through
          case 87: break;
          case 42: 
            { return new Symbol(sym.FLOAT);
            } 
            // fall through
          case 88: break;
          case 43: 
            { return new Symbol(sym.WHILE);
            } 
            // fall through
          case 89: break;
          case 44: 
            { return new Symbol(sym.PRINT);
            } 
            // fall through
          case 90: break;
          case 45: 
            { return new Symbol(sym.LENGTH);
            } 
            // fall through
          case 91: break;
          case 46: 
            { return new Symbol(sym.STRING);
            } 
            // fall through
          case 92: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}