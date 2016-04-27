package com.wix.pay.paymentexpress.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** @see https://www.paymentexpress.com/Technical_Resources/Ecommerce_NonHosted/PxPost */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Txn")
public class TransactionResponse {
	@XmlElement
	public String ReCo;

	@XmlElement
	public String ResponseText;
	
	@XmlElement
	public String HelpText;
	
	@XmlElement
	public Integer Success;
	
	@XmlElement
	public String DpsTxnRef;
	
	@XmlElement
	public String TxnRef;
	
	@XmlElement
	public Transaction Transaction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionResponse that = (TransactionResponse) o;

        if (DpsTxnRef != null ? !DpsTxnRef.equals(that.DpsTxnRef) : that.DpsTxnRef != null) return false;
        if (HelpText != null ? !HelpText.equals(that.HelpText) : that.HelpText != null) return false;
        if (ReCo != null ? !ReCo.equals(that.ReCo) : that.ReCo != null) return false;
        if (ResponseText != null ? !ResponseText.equals(that.ResponseText) : that.ResponseText != null) return false;
        if (Success != null ? !Success.equals(that.Success) : that.Success != null) return false;
        if (Transaction != null ? !Transaction.equals(that.Transaction) : that.Transaction != null) return false;
        if (TxnRef != null ? !TxnRef.equals(that.TxnRef) : that.TxnRef != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ReCo != null ? ReCo.hashCode() : 0;
        result = 31 * result + (ResponseText != null ? ResponseText.hashCode() : 0);
        result = 31 * result + (HelpText != null ? HelpText.hashCode() : 0);
        result = 31 * result + (Success != null ? Success.hashCode() : 0);
        result = 31 * result + (DpsTxnRef != null ? DpsTxnRef.hashCode() : 0);
        result = 31 * result + (TxnRef != null ? TxnRef.hashCode() : 0);
        result = 31 * result + (Transaction != null ? Transaction.hashCode() : 0);
        return result;
    }
}
