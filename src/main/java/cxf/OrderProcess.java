package cxf;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface OrderProcess
{

    @WebMethod
    public void process(Order order);
    
}
