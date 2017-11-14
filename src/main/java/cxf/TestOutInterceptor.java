package cxf;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class TestOutInterceptor extends AbstractPhaseInterceptor<Message>
{

    public TestOutInterceptor()
    {
        super(Phase.PRE_STREAM);
        addAfter(LoggingOutInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) throws Fault
    {
        System.out.println("MESSAGE SENT");
        final OutputStream os = message.getContent(OutputStream.class);
        final Writer iowriter = message.getContent(Writer.class);
        if (os == null && iowriter == null)
        {
            return;
        }
        final CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(os);
        message.setContent(OutputStream.class, newOut);
        newOut.registerCallback(new LoggerCallBack(message, os));
        

    }

    private class LoggerCallBack implements CachedOutputStreamCallback
    {

        private Message message;
        private OutputStream origOs;

        public LoggerCallBack(Message message, OutputStream os)
        {
            this.message = message;
            this.origOs = os;
        }

        @Override
        public void onClose(CachedOutputStream os)
        {
            try
            {
                InputStream is = os.getInputStream();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            message.setContent(OutputStream.class, this.origOs);
        }

        @Override
        public void onFlush(CachedOutputStream os)
        {

        }

    }

}
