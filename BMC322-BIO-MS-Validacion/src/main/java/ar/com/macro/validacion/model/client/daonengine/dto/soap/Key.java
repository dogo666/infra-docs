//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.validacion.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Key complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Key"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KeyIdentifier" type="{http://www.daon.com/ws/de}KeyIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="KeyType" type="{http://www.daon.com/ws/de}KeyType"/&gt;
 *         &lt;element name="KeyUsage" type="{http://www.daon.com/ws/de}KeyUsage"/&gt;
 *         &lt;element name="Label" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CurrentFromDtm" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ValidToDtm" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDtm" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RevokedDtm" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="RevokedReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Cert" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Key", propOrder = {
    "keyIdentifier",
    "keyType",
    "keyUsage",
    "label",
    "currentFromDtm",
    "validToDtm",
    "createdDtm",
    "revokedDtm",
    "revokedReason",
    "cert",
    "key"
})
public class Key {

    @XmlElement(name = "KeyIdentifier")
    protected KeyIdentifier keyIdentifier;
    @XmlElement(name = "KeyType", required = true)
    @XmlSchemaType(name = "string")
    protected KeyType keyType;
    @XmlElement(name = "KeyUsage", required = true)
    @XmlSchemaType(name = "string")
    protected KeyUsage keyUsage;
    @XmlElement(name = "Label", required = true)
    protected String label;
    @XmlElement(name = "CurrentFromDtm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar currentFromDtm;
    @XmlElement(name = "ValidToDtm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validToDtm;
    @XmlElement(name = "CreatedDtm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDtm;
    @XmlElement(name = "RevokedDtm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar revokedDtm;
    @XmlElement(name = "RevokedReason")
    protected String revokedReason;
    @XmlElement(name = "Cert")
    protected byte[] cert;
    @XmlElement(name = "Key")
    protected byte[] key;

    /**
     * Gets the value of the keyIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link KeyIdentifier }
     *     
     */
    public KeyIdentifier getKeyIdentifier() {
        return keyIdentifier;
    }

    /**
     * Sets the value of the keyIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyIdentifier }
     *     
     */
    public void setKeyIdentifier(KeyIdentifier value) {
        this.keyIdentifier = value;
    }

    /**
     * Gets the value of the keyType property.
     * 
     * @return
     *     possible object is
     *     {@link KeyType }
     *     
     */
    public KeyType getKeyType() {
        return keyType;
    }

    /**
     * Sets the value of the keyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyType }
     *     
     */
    public void setKeyType(KeyType value) {
        this.keyType = value;
    }

    /**
     * Gets the value of the keyUsage property.
     * 
     * @return
     *     possible object is
     *     {@link KeyUsage }
     *     
     */
    public KeyUsage getKeyUsage() {
        return keyUsage;
    }

    /**
     * Sets the value of the keyUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyUsage }
     *     
     */
    public void setKeyUsage(KeyUsage value) {
        this.keyUsage = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the currentFromDtm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCurrentFromDtm() {
        return currentFromDtm;
    }

    /**
     * Sets the value of the currentFromDtm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCurrentFromDtm(XMLGregorianCalendar value) {
        this.currentFromDtm = value;
    }

    /**
     * Gets the value of the validToDtm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidToDtm() {
        return validToDtm;
    }

    /**
     * Sets the value of the validToDtm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidToDtm(XMLGregorianCalendar value) {
        this.validToDtm = value;
    }

    /**
     * Gets the value of the createdDtm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDtm() {
        return createdDtm;
    }

    /**
     * Sets the value of the createdDtm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDtm(XMLGregorianCalendar value) {
        this.createdDtm = value;
    }

    /**
     * Gets the value of the revokedDtm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRevokedDtm() {
        return revokedDtm;
    }

    /**
     * Sets the value of the revokedDtm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRevokedDtm(XMLGregorianCalendar value) {
        this.revokedDtm = value;
    }

    /**
     * Gets the value of the revokedReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevokedReason() {
        return revokedReason;
    }

    /**
     * Sets the value of the revokedReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevokedReason(String value) {
        this.revokedReason = value;
    }

    /**
     * Gets the value of the cert property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getCert() {
        return cert;
    }

    /**
     * Sets the value of the cert property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setCert(byte[] value) {
        this.cert = value;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setKey(byte[] value) {
        this.key = value;
    }

}