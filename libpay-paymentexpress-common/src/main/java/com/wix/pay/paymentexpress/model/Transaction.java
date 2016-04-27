package com.wix.pay.paymentexpress.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/** @see https://www.paymentexpress.com/Technical_Resources/Ecommerce_NonHosted/PxPost */
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {
	@XmlAttribute
	public String success;
	
	@XmlAttribute
	public String reco;
	
	@XmlAttribute
	public String responseText;
	
	@XmlAttribute
	public String pxTxn;
	
	@XmlElement
	public String AuthCode;
	
	@XmlElement
	public Integer Authorized;
	
	@XmlElement
	public String Cvc2ResultCode;
	
	@XmlElement
	public String DateSettlement;
	
	@XmlElement
	public String DpsTxnRef;
	
	@XmlElement
	public String DpsBillingId;
	
	@XmlElement
	public String HelpText;
	
	@XmlElement
	public String ReCo;
	
	@XmlElement
	public String ResponseText;
	
	@XmlElement
	public Integer StatusRequired;
	
	@XmlElement
	public Integer Success;

    @XmlElement
    public String CardHolderResponseText;

    @XmlElement
    public String CardHolderHelpText;

    @XmlElement
    public String CardHolderResponseDescription;

    @XmlElement
    public String MerchantResponseText;

    @XmlElement
    public String MerchantHelpText;

    @XmlElement
    public String MerchantResponseDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (AuthCode != null ? !AuthCode.equals(that.AuthCode) : that.AuthCode != null) return false;
        if (Authorized != null ? !Authorized.equals(that.Authorized) : that.Authorized != null) return false;
        if (CardHolderHelpText != null ? !CardHolderHelpText.equals(that.CardHolderHelpText) : that.CardHolderHelpText != null) return false;
        if (CardHolderResponseDescription != null ? !CardHolderResponseDescription.equals(that.CardHolderResponseDescription) : that.CardHolderResponseDescription != null) return false;
        if (CardHolderResponseText != null ? !CardHolderResponseText.equals(that.CardHolderResponseText) : that.CardHolderResponseText != null) return false;
        if (Cvc2ResultCode != null ? !Cvc2ResultCode.equals(that.Cvc2ResultCode) : that.Cvc2ResultCode != null) return false;
        if (DateSettlement != null ? !DateSettlement.equals(that.DateSettlement) : that.DateSettlement != null) return false;
        if (DpsBillingId != null ? !DpsBillingId.equals(that.DpsBillingId) : that.DpsBillingId != null) return false;
        if (DpsTxnRef != null ? !DpsTxnRef.equals(that.DpsTxnRef) : that.DpsTxnRef != null) return false;
        if (HelpText != null ? !HelpText.equals(that.HelpText) : that.HelpText != null) return false;
        if (MerchantHelpText != null ? !MerchantHelpText.equals(that.MerchantHelpText) : that.MerchantHelpText != null) return false;
        if (MerchantResponseDescription != null ? !MerchantResponseDescription.equals(that.MerchantResponseDescription) : that.MerchantResponseDescription != null) return false;
        if (MerchantResponseText != null ? !MerchantResponseText.equals(that.MerchantResponseText) : that.MerchantResponseText != null) return false;
        if (ReCo != null ? !ReCo.equals(that.ReCo) : that.ReCo != null) return false;
        if (ResponseText != null ? !ResponseText.equals(that.ResponseText) : that.ResponseText != null) return false;
        if (StatusRequired != null ? !StatusRequired.equals(that.StatusRequired) : that.StatusRequired != null) return false;
        if (Success != null ? !Success.equals(that.Success) : that.Success != null) return false;
        if (pxTxn != null ? !pxTxn.equals(that.pxTxn) : that.pxTxn != null) return false;
        if (reco != null ? !reco.equals(that.reco) : that.reco != null) return false;
        if (responseText != null ? !responseText.equals(that.responseText) : that.responseText != null) return false;
        if (success != null ? !success.equals(that.success) : that.success != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = success != null ? success.hashCode() : 0;
        result = 31 * result + (reco != null ? reco.hashCode() : 0);
        result = 31 * result + (responseText != null ? responseText.hashCode() : 0);
        result = 31 * result + (pxTxn != null ? pxTxn.hashCode() : 0);
        result = 31 * result + (AuthCode != null ? AuthCode.hashCode() : 0);
        result = 31 * result + (Authorized != null ? Authorized.hashCode() : 0);
        result = 31 * result + (Cvc2ResultCode != null ? Cvc2ResultCode.hashCode() : 0);
        result = 31 * result + (DateSettlement != null ? DateSettlement.hashCode() : 0);
        result = 31 * result + (DpsTxnRef != null ? DpsTxnRef.hashCode() : 0);
        result = 31 * result + (DpsBillingId != null ? DpsBillingId.hashCode() : 0);
        result = 31 * result + (HelpText != null ? HelpText.hashCode() : 0);
        result = 31 * result + (ReCo != null ? ReCo.hashCode() : 0);
        result = 31 * result + (ResponseText != null ? ResponseText.hashCode() : 0);
        result = 31 * result + (StatusRequired != null ? StatusRequired.hashCode() : 0);
        result = 31 * result + (Success != null ? Success.hashCode() : 0);
        result = 31 * result + (CardHolderResponseText != null ? CardHolderResponseText.hashCode() : 0);
        result = 31 * result + (CardHolderHelpText != null ? CardHolderHelpText.hashCode() : 0);
        result = 31 * result + (CardHolderResponseDescription != null ? CardHolderResponseDescription.hashCode() : 0);
        result = 31 * result + (MerchantResponseText != null ? MerchantResponseText.hashCode() : 0);
        result = 31 * result + (MerchantHelpText != null ? MerchantHelpText.hashCode() : 0);
        result = 31 * result + (MerchantResponseDescription != null ? MerchantResponseDescription.hashCode() : 0);
        return result;
    }
}
