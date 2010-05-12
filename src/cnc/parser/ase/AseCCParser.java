/* Generated By:JavaCC: Do not edit this line. AseCCParser.java */
package cnc.parser.ase;
import cnc.operator.storage.*;
import cnc.parser.ICncParser;

public class AseCCParser implements ICncParser, AseCCParserConstants {

private IDataStorage storage;
private int lineCounter;

public void setStorage(IDataStorage stor)
{
        storage = stor;
}

public IDataStorage getStorage()
{
        return storage;
}

public void setLineCounter(int val)
{
        lineCounter = val;
}

  final public void Start() throws ParseException {
                if( storage == null)
                {
                        throw new RuntimeException("Storage must be set!");
                }
                storage.clearStorage();
    Object3D();
    jj_consume_token(0);
  }

  final public void Object3D() throws ParseException {
    skip_to_token(MESH_VERTEX_LIST);
    jj_consume_token(LBRACE);
    label_1:
    while (true) {
      Vertex();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ASTERISK:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
    }
    jj_consume_token(RBRACE);
    jj_consume_token(ASTERISK);
    jj_consume_token(MESH_FACE_LIST);
    jj_consume_token(LBRACE);
    label_2:
    while (true) {
      Line();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ASTERISK:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
    }
    jj_consume_token(RBRACE);
    skip_to_token(EOF);
  }

  final public void Vertex() throws ParseException {
        Token id;
        Token x;
        Token y;
        Token z;
    jj_consume_token(ASTERISK);
    jj_consume_token(MESH_VERTEX);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMBER:
      id = jj_consume_token(NUMBER);
      break;
    case DIGIT:
      id = jj_consume_token(DIGIT);
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    x = jj_consume_token(NUMBER);
    y = jj_consume_token(NUMBER);
    z = jj_consume_token(NUMBER);
                storage.addVertex(id.image, x.image, y.image, z.image);
  }

  final public void Line() throws ParseException {
        Token a = null;
        Token b = null;
        Token c = null;
        Token ab = null;
        Token bc = null;
        Token ca = null;
    jj_consume_token(ASTERISK);
    jj_consume_token(MESH_FACE);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMBER:
      jj_consume_token(NUMBER);
      break;
    case DIGIT:
      jj_consume_token(DIGIT);
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(SERVSYMB);
    jj_consume_token(ID);
    jj_consume_token(SERVSYMB);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMBER:
      a = jj_consume_token(NUMBER);
      break;
    case DIGIT:
      a = jj_consume_token(DIGIT);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(ID);
    jj_consume_token(SERVSYMB);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMBER:
      b = jj_consume_token(NUMBER);
      break;
    case DIGIT:
      b = jj_consume_token(DIGIT);
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(ID);
    jj_consume_token(SERVSYMB);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMBER:
      c = jj_consume_token(NUMBER);
      break;
    case DIGIT:
      c = jj_consume_token(DIGIT);
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(ID);
    jj_consume_token(SERVSYMB);
    ab = jj_consume_token(DIGIT);
    jj_consume_token(ID);
    jj_consume_token(SERVSYMB);
    bc = jj_consume_token(DIGIT);
    jj_consume_token(ID);
    jj_consume_token(SERVSYMB);
    ca = jj_consume_token(DIGIT);
    jj_consume_token(ASTERISK);
    jj_consume_token(MESH_SMOOTHING);
    jj_consume_token(DIGIT);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SERVSYMB:
      jj_consume_token(SERVSYMB);
      jj_consume_token(DIGIT);
      break;
    default:
      jj_la1[7] = jj_gen;
      ;
    }
    jj_consume_token(ASTERISK);
    jj_consume_token(MESH_MTLID);
    jj_consume_token(DIGIT);
                //Be attentive!!!  "lineCounter++" is intentional.
                //We need incremental augment on next iteration!! 
                if(ab.image.equals("1"))
                        storage.addLine(lineCounter++, a.image, b.image);
                if(bc.image.equals("1"))
                        storage.addLine(lineCounter++, b.image, c.image);
                if(ca.image.equals("1"))
                        storage.addLine(lineCounter++, c.image, a.image);
  }

  void skip_to_token(int kind) throws ParseException {
  Token tok;
  int nesting = 1;
  while (true) {
    tok = getToken(1);
    tok = getNextToken();
        if (tok.kind == kind)
                break;
    }
  }

  public AseCCParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[8];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x80,0x80,0x300,0x300,0x300,0x300,0x300,0x80000,};
   }

  public AseCCParser(java.io.InputStream stream) {
     this(stream, null);
  }
  public AseCCParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new AseCCParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  public AseCCParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new AseCCParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  public AseCCParser(AseCCParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  public void ReInit(AseCCParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[20];
    for (int i = 0; i < 20; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 8; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 20; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}
