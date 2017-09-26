package cxf;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class Client
{

    private static final QName SERVICE_NAME = new QName("cxf", "OrderProcessService");
    private static final QName PORT_NAME = new QName("cxf", "OrderProcessPort");
    private static final String WSDL_LOCATION = "http://localhost:8080/cxf/ws/orderProcess?wsdl";

    public static void main(String args[]) throws Exception
    {
        URL wsdlURL = new URL(WSDL_LOCATION);
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        OrderProcess port = service.getPort(PORT_NAME, OrderProcess.class);
        Order order = new Order();
        order.setNo("0550");
        port.process(order);
        
    }

}
