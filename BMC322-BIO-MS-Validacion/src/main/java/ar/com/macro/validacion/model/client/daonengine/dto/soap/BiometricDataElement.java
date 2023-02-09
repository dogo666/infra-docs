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
 * Contains Biometric data and associated meta-data
 * 						elements. <p>
 * 
 * 						CreatedDtm, ApplicationUserIdentifier,
 * 						ApplicationIdentifier are only populated in Response
 * 						messages
 * 					
 * 
 * <p>Java class for BiometricDataElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BiometricDataElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TypeQualifier" type="{http://www.daon.com/ws/de}TypeQualifier"/&gt;
 *         &lt;element name="UsageQualifier" type="{http://www.daon.com/ws/de}UsageQualifier"/&gt;
 *         &lt;element name="CapturedDtm" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDtm" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationUserIdentifier" type="{http://www.daon.com/ws/de}ApplicationUserIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationIdentifier" type="{http://www.daon.com/ws/de}ApplicationIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="Data"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;base64Binary"&gt;
 *                 &lt;attribute name="externalRef" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MetaData" type="{http://www.daon.com/de/metadata}MetaData" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BiometricDataElement", propOrder = {
    "typeQualifier",
    "usageQualifier",
    "capturedDtm",
    "createdDtm",
    "applicationUserIdentifier",
    "applicationIdentifier",
    "data",
    "metaData"
})
public class BiometricDataElement {

    @XmlElement(name = "TypeQualifier")
    protected int typeQualifier;
    @XmlElement(name = "UsageQualifier")
    protected int usageQualifier;
    @XmlElement(name = "CapturedDtm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar capturedDtm;
    @XmlElement(name = "CreatedDtm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDtm;
    @XmlElement(name = "ApplicationUserIdentifier")
    protected String applicationUserIdentifier;
    @XmlElement(name = "ApplicationIdentifier")
    protected String applicationIdentifier;
    @XmlElement(name = "Data", required = true)
    protected Data data;
    @XmlElement(name = "MetaData")
    protected MetaData metaData;

    /**
     * Gets the value of the typeQualifier property.
     *
     */
    public int getTypeQualifier() {
        return typeQualifier;
    }

    /**
     * Sets the value of the typeQualifier property.
     *
     */
    public void setTypeQualifier(int value) {
        this.typeQualifier = value;
    }

    /**
     * Gets the value of the usageQualifier property.
     *
     */
    public int getUsageQualifier() {
        return usageQualifier;
    }

    /**
     * Sets the value of the usageQualifier property.
     *
     */
    public void setUsageQualifier(int value) {
        this.usageQualifier = value;
    }

    /**
     * Gets the value of the capturedDtm property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getCapturedDtm() {
        return capturedDtm;
    }

    /**
     * Sets the value of the capturedDtm property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setCapturedDtm(XMLGregorianCalendar value) {
        this.capturedDtm = value;
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
     * Gets the value of the applicationUserIdentifier property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getApplicationUserIdentifier() {
        return applicationUserIdentifier;
    }

    /**
     * Sets the value of the applicationUserIdentifier property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setApplicationUserIdentifier(String value) {
        this.applicationUserIdentifier = value;
    }

    /**
     * Gets the value of the applicationIdentifier property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getApplicationIdentifier() {
        return applicationIdentifier;
    }

    /**
     * Sets the value of the applicationIdentifier property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setApplicationIdentifier(String value) {
        this.applicationIdentifier = value;
    }

    /**
     * Gets the value of the data property.
     *
     * @return
     *     possible object is
     *     {@link Data }
     *
     */
    public Data getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     *
     * @param value
     *     allowed object is
     *     {@link Data }
     *
     */
    public void setData(Data value) {
        this.data = value;
    }

    /**
     * Gets the value of the metaData property.
     * 
     * @return
     *     possible object is
     *     {@link MetaData }
     *     
     */
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaData }
     *     
     */
    public void setMetaData(MetaData value) {
        this.metaData = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;base64Binary"&gt;
     *       &lt;attribute name="externalRef" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Data {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "externalRef")
        protected String externalRef;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the externalRef property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExternalRef() {
            return externalRef;
        }

        /**
         * Sets the value of the externalRef property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExternalRef(String value) {
            this.externalRef = value;
        }

    }

}