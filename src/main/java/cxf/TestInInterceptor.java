package cxf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class TestInInterceptor extends AbstractPhaseInterceptor<Message>
{

    public static final int limit = 48 * 1024;

    public TestInInterceptor()
    {
        super(Phase.RECEIVE);
        addAfter(LoggingInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) throws Fault
    {
        System.out.println("MESSAGE RECEIVED");

        InputStream is = message.getContent(InputStream.class);

        if (is != null)
        {
            try
            {
                CachedOutputStream bos = new CachedOutputStream();

                IOUtils.copy(is, bos);
                bos.flush();
                is = new SequenceInputStream(bos.getInputStream(), is);

                // restore the delegating input stream or the input stream
                if (is instanceof DelegatingInputStream)
                {
                    ((DelegatingInputStream) is).setInputStream(is);
                }
                else
                {
                    message.setContent(InputStream.class, is);
                }
                String xmlString = new String(bos.getBytes());
                System.out.println(xmlString);
                if (xmlString != null && xmlString.trim().length() > 1)
                {
                    // StaxUtils.createXMLStreamReader(is);
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document dom = db.parse(new InputSource(new StringReader(xmlString.trim())));
                    // NodeList nl = dom.getElementsByTagName("Order");
                    NodeList nl = dom.getElementsByTagName("no");
                    if (nl != null && nl.getLength() > 0)
                    {
                        for (int i = 0; i < nl.getLength(); i++)
                        {

                            Element el = (Element) nl.item(i);
                            System.out.println("log interceptor : " + el.getTextContent());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

}
