package com.wix.pay.paymentexpress;

import com.wix.pay.paymentexpress.model.TransactionRequest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class TransactionRequestParser {
    public String stringify(TransactionRequest obj) throws IOException {
        try {
            final JAXBContext jc = JAXBContext.newInstance(TransactionRequest.class);
            final Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, true);

            final StringWriter writer = new StringWriter();
            try {
                m.marshal(obj, writer);
            } finally {
                writer.close();
            }
            return writer.toString();
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    public TransactionRequest parse(String xml) throws IOException {
        try {
            final JAXBContext jc = JAXBContext.newInstance(TransactionRequest.class);
            final Unmarshaller um = jc.createUnmarshaller();

            final StringReader reader = new StringReader(xml);
            try {
                return (TransactionRequest) um.unmarshal(reader);
            } finally {
                reader.close();
            }
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }
}
