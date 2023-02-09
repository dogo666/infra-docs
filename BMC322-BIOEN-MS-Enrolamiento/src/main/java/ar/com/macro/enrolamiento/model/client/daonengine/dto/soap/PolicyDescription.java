//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.enrolamiento.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.*;


/**
 * A lightweight description of a Policy.
 * 					
 * 
 * <p>Java class for PolicyDescription complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PolicyDescription"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PolicyIdentifier" type="{http://www.daon.com/ws/de}PolicyIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="PolicyDetails" type="{http://www.daon.com/ws/de}PolicyDetails"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicyDescription", propOrder = {
    "policyIdentifier",
    "policyDetails"
})
@XmlSeeAlso({
    Policy.class
})
public class PolicyDescription {

    @XmlElement(name = "PolicyIdentifier")
    protected PolicyIdentifier policyIdentifier;
    @XmlElement(name = "PolicyDetails", required = true)
    protected PolicyDetails policyDetails;

    /**
     * Gets the value of the policyIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyIdentifier }
     *     
     */
    public PolicyIdentifier getPolicyIdentifier() {
        return policyIdentifier;
    }

    /**
     * Sets the value of the policyIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyIdentifier }
     *     
     */
    public void setPolicyIdentifier(PolicyIdentifier value) {
        this.policyIdentifier = value;
    }

    /**
     * Gets the value of the policyDetails property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyDetails }
     *     
     */
    public PolicyDetails getPolicyDetails() {
        return policyDetails;
    }

    /**
     * Sets the value of the policyDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyDetails }
     *     
     */
    public void setPolicyDetails(PolicyDetails value) {
        this.policyDetails = value;
    }

}
