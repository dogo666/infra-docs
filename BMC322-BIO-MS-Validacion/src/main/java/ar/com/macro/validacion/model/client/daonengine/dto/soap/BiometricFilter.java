//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.validacion.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.*;


/**
 * Biometric filter - can match specific biometrics
 * 						using a
 * 						type and usage qualifier combination. Can also match a
 * 						group of biometrics by specifying the type qualifier and
 * 						no usage qualifier (e.g. to match all finger prints).
 * 					
 * 
 * <p>Java class for BiometricFilter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BiometricFilter"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TypeQualifier" type="{http://www.daon.com/ws/de}TypeQualifier"/&gt;
 *         &lt;element name="UsageQualifier" type="{http://www.daon.com/ws/de}UsageQualifier" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="fetchExternal" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BiometricFilter", propOrder = {
    "typeQualifier",
    "usageQualifier"
})
public class BiometricFilter {

    @XmlElement(name = "TypeQualifier")
    protected int typeQualifier;
    @XmlElement(name = "UsageQualifier")
    protected Integer usageQualifier;
    @XmlAttribute(name = "fetchExternal")
    protected Boolean fetchExternal;

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
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUsageQualifier() {
        return usageQualifier;
    }

    /**
     * Sets the value of the usageQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUsageQualifier(Integer value) {
        this.usageQualifier = value;
    }

    /**
     * Gets the value of the fetchExternal property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFetchExternal() {
        return fetchExternal;
    }

    /**
     * Sets the value of the fetchExternal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFetchExternal(Boolean value) {
        this.fetchExternal = value;
    }

}
