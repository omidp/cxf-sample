package cxf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@WebService(serviceName="OrderProcessService", portName="OrderProcessPort", targetNamespace="cxf", endpointInterface="cxf.OrderProcess")
public class OrderProcessImpl implements OrderProcess
{
    @WebMethod(operationName="process")
    public void process(@WebParam(name="order") Order order)
    {
        System.out.println("Order No : " + order.getNo());
    }

}
