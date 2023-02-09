//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.enrolamiento.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Filter biographic and biometric Identity Data
 * 					
 * 
 * <p>Java class for IdentityDataFilter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentityDataFilter"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BiographicDataFilter" type="{http://www.daon.com/ws/de}BiographicDataFilter" minOccurs="0"/&gt;
 *         &lt;element name="BiometricDataFilter" type="{http://www.daon.com/ws/de}BiometricDataFilter" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentityDataFilter", propOrder = {
    "biographicDataFilter",
    "biometricDataFilter"
})
public class IdentityDataFilter {

    @XmlElement(name = "BiographicDataFilter")
    protected BiographicDataFilter biographicDataFilter;
    @XmlElement(name = "BiometricDataFilter")
    protected BiometricDataFilter biometricDataFilter;

    /**
     * Gets the value of the biographicDataFilter property.
     * 
     * @return
     *     possible object is
     *     {@link BiographicDataFilter }
     *     
     */
    public BiographicDataFilter getBiographicDataFilter() {
        return biographicDataFilter;
    }

    /**
     * Sets the value of the biographicDataFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link BiographicDataFilter }
     *     
     */
    public void setBiographicDataFilter(BiographicDataFilter value) {
        this.biographicDataFilter = value;
    }

    /**
     * Gets the value of the biometricDataFilter property.
     * 
     * @return
     *     possible object is
     *     {@link BiometricDataFilter }
     *     
     */
    public BiometricDataFilter getBiometricDataFilter() {
        return biometricDataFilter;
    }

    /**
     * Sets the value of the biometricDataFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link BiometricDataFilter }
     *     
     */
    public void setBiometricDataFilter(BiometricDataFilter value) {
        this.biometricDataFilter = value;
    }

}
