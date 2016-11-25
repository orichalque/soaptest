package com.alma;

import javax.xml.soap.*;
import java.net.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String args[]) throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        String url = "http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx";
        URL url1 = new URL(url);
        Socket socket = new Socket();

        SocketAddress inetAddress = new InetSocketAddress("proxy.ensinfo.sciences.univ-nantes.prive", 3128);
        socket.connect(inetAddress);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(socket.getInetAddress(), 3128));
        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection(proxy);
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), httpURLConnection);

        // print SOAP Response
        System.out.print("Response SOAP Message:");
        soapResponse.writeTo(System.out);

        socket.close();
        soapConnection.close();
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://ws.cdyne.com/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <example:VerifyEmail>
                    <example:email>mutantninja@gmail.com</example:email>
                    <example:LicenseKey>123</example:LicenseKey>
                </example:VerifyEmail>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>

<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <VerifyEmail xmlns="http://ws.cdyne.com/">
      <email>string</email>
      <LicenseKey>string</LicenseKey>
    </VerifyEmail>
  </soap:Body>
</soap:Envelope>
         */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("VerifyEmail");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("email");
        soapBodyElem1.addTextNode("mutantninja@gmail.com");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("LicenseKey");
        soapBodyElem2.addTextNode("0");

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", "http://ws.cdyne.com/VerifyEmail");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
}
