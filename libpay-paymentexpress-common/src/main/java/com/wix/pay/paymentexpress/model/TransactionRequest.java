package com.wix.pay.paymentexpress.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** @see https://www.paymentexpress.com/Technical_Resources/Ecommerce_NonHosted/PxPost */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Txn")
public class TransactionRequest {
    @XmlElement
    public String Amount;

    @XmlElement
    public String CardHolderName;

    @XmlElement
    public String CardNumber;

    @XmlElement
    public String BillingId;

    @XmlElement
    public String Cvc2;

    @XmlElement
    public Integer Cvc2Presence;

    @XmlElement
    public String DateExpiry;

    @XmlElement
    public String DpsBillingId;

    @XmlElement
    public String DpsTxnRef;

    @XmlElement
    public Integer EnableAddBillCard;

    @XmlElement
    public String InputCurrency;

    @XmlElement
    public String MerchantReference;

	@XmlElement
	public String PostUsername;

	@XmlElement
	public String PostPassword;
	
	@XmlElement
	public String TxnType;

    @XmlElement
    public String TxnData1;

    @XmlElement
    public String TxnData2;

    @XmlElement
    public String TxnData3;

    @XmlElement
    public String TxnId;

    @XmlElement
    public Integer EnableAvsData;

    @XmlElement
    public Integer AvsAction;

    @XmlElement
    public String AvsPostCode;

    @XmlElement
    public String AvsStreetAddress;

    @XmlElement
    public String DateStart;

    @XmlElement
    public Integer IssueNumber;

    @XmlElement
    public String Track2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionRequest that = (TransactionRequest) o;

        if (Amount != null ? !Amount.equals(that.Amount) : that.Amount != null) return false;
        if (AvsAction != null ? !AvsAction.equals(that.AvsAction) : that.AvsAction != null) return false;
        if (AvsPostCode != null ? !AvsPostCode.equals(that.AvsPostCode) : that.AvsPostCode != null) return false;
        if (AvsStreetAddress != null ? !AvsStreetAddress.equals(that.AvsStreetAddress) : that.AvsStreetAddress != null) return false;
        if (BillingId != null ? !BillingId.equals(that.BillingId) : that.BillingId != null) return false;
        if (CardHolderName != null ? !CardHolderName.equals(that.CardHolderName) : that.CardHolderName != null) return false;
        if (CardNumber != null ? !CardNumber.equals(that.CardNumber) : that.CardNumber != null) return false;
        if (Cvc2 != null ? !Cvc2.equals(that.Cvc2) : that.Cvc2 != null) return false;
        if (Cvc2Presence != null ? !Cvc2Presence.equals(that.Cvc2Presence) : that.Cvc2Presence != null) return false;
        if (DateExpiry != null ? !DateExpiry.equals(that.DateExpiry) : that.DateExpiry != null) return false;
        if (DateStart != null ? !DateStart.equals(that.DateStart) : that.DateStart != null) return false;
        if (DpsBillingId != null ? !DpsBillingId.equals(that.DpsBillingId) : that.DpsBillingId != null) return false;
        if (DpsTxnRef != null ? !DpsTxnRef.equals(that.DpsTxnRef) : that.DpsTxnRef != null) return false;
        if (EnableAddBillCard != null ? !EnableAddBillCard.equals(that.EnableAddBillCard) : that.EnableAddBillCard != null) return false;
        if (EnableAvsData != null ? !EnableAvsData.equals(that.EnableAvsData) : that.EnableAvsData != null) return false;
        if (InputCurrency != null ? !InputCurrency.equals(that.InputCurrency) : that.InputCurrency != null) return false;
        if (IssueNumber != null ? !IssueNumber.equals(that.IssueNumber) : that.IssueNumber != null) return false;
        if (MerchantReference != null ? !MerchantReference.equals(that.MerchantReference) : that.MerchantReference != null) return false;
        if (PostPassword != null ? !PostPassword.equals(that.PostPassword) : that.PostPassword != null) return false;
        if (PostUsername != null ? !PostUsername.equals(that.PostUsername) : that.PostUsername != null) return false;
        if (Track2 != null ? !Track2.equals(that.Track2) : that.Track2 != null) return false;
        if (TxnData1 != null ? !TxnData1.equals(that.TxnData1) : that.TxnData1 != null) return false;
        if (TxnData2 != null ? !TxnData2.equals(that.TxnData2) : that.TxnData2 != null) return false;
        if (TxnData3 != null ? !TxnData3.equals(that.TxnData3) : that.TxnData3 != null) return false;
        if (TxnId != null ? !TxnId.equals(that.TxnId) : that.TxnId != null) return false;
        if (TxnType != null ? !TxnType.equals(that.TxnType) : that.TxnType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Amount != null ? Amount.hashCode() : 0;
        result = 31 * result + (CardHolderName != null ? CardHolderName.hashCode() : 0);
        result = 31 * result + (CardNumber != null ? CardNumber.hashCode() : 0);
        result = 31 * result + (BillingId != null ? BillingId.hashCode() : 0);
        result = 31 * result + (Cvc2 != null ? Cvc2.hashCode() : 0);
        result = 31 * result + (Cvc2Presence != null ? Cvc2Presence.hashCode() : 0);
        result = 31 * result + (DateExpiry != null ? DateExpiry.hashCode() : 0);
        result = 31 * result + (DpsBillingId != null ? DpsBillingId.hashCode() : 0);
        result = 31 * result + (DpsTxnRef != null ? DpsTxnRef.hashCode() : 0);
        result = 31 * result + (EnableAddBillCard != null ? EnableAddBillCard.hashCode() : 0);
        result = 31 * result + (InputCurrency != null ? InputCurrency.hashCode() : 0);
        result = 31 * result + (MerchantReference != null ? MerchantReference.hashCode() : 0);
        result = 31 * result + (PostUsername != null ? PostUsername.hashCode() : 0);
        result = 31 * result + (PostPassword != null ? PostPassword.hashCode() : 0);
        result = 31 * result + (TxnType != null ? TxnType.hashCode() : 0);
        result = 31 * result + (TxnData1 != null ? TxnData1.hashCode() : 0);
        result = 31 * result + (TxnData2 != null ? TxnData2.hashCode() : 0);
        result = 31 * result + (TxnData3 != null ? TxnData3.hashCode() : 0);
        result = 31 * result + (TxnId != null ? TxnId.hashCode() : 0);
        result = 31 * result + (EnableAvsData != null ? EnableAvsData.hashCode() : 0);
        result = 31 * result + (AvsAction != null ? AvsAction.hashCode() : 0);
        result = 31 * result + (AvsPostCode != null ? AvsPostCode.hashCode() : 0);
        result = 31 * result + (AvsStreetAddress != null ? AvsStreetAddress.hashCode() : 0);
        result = 31 * result + (DateStart != null ? DateStart.hashCode() : 0);
        result = 31 * result + (IssueNumber != null ? IssueNumber.hashCode() : 0);
        result = 31 * result + (Track2 != null ? Track2.hashCode() : 0);
        return result;
    }
}
