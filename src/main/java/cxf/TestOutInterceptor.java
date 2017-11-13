package cxf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class TestOutInterceptor extends AbstractPhaseInterceptor<Message>
{

    public TestOutInterceptor()
    {
        super(Phase.PRE_STREAM);
        addBefore(LoggingOutInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) throws Fault
    {
        System.out.println("MESSAGE SENT");

//        InputStream is = message.getContent(InputStream.class);
//
//        if (is != null)
//        {
//            CachedOutputStream bos = new CachedOutputStream();
//            try
//            {
//                IOUtils.copy(is, bos);
//                String soapMessage = new String(bos.getBytes());
//                System.out.println("-------------------------------------------");
//                System.out.println("incoming message is " + soapMessage);
//                System.out.println("-------------------------------------------");
//                bos.flush();
//                message.setContent(InputStream.class, is);
//
//                is.close();
//                InputStream inputStream = new ByteArrayInputStream(soapMessage.getBytes());
//                message.setContent(InputStream.class, inputStream);
//                bos.close();
//            }
//            catch (IOException ioe)
//            {
//                ioe.printStackTrace();
//            }
//        }

    }

}
