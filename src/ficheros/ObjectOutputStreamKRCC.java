
package ficheros;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 *
 * @author KRene
 */
public class ObjectOutputStreamKRCC extends ObjectOutputStream {

    @Override
    protected void writeStreamHeader() throws IOException {
    }
    
    //constructores
    public ObjectOutputStreamKRCC () throws IOException {
        super ();
    }
    
    public ObjectOutputStreamKRCC (OutputStream out) throws IOException {
        super (out);
    }
}
