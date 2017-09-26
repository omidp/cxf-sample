package cxf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService
public interface OrderProcess
{

    @WebMethod
    public void process(@WebParam(name = "Order") @XmlElement(required = true, nillable = false)Order order);
    
}
