//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.validacion.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Filter used to exclude biometric data.
 * 					
 * 
 * <p>Java class for ExclusiveBiometricDataFilter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExclusiveBiometricDataFilter"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.daon.com/ws/de}BiometricDataFilter"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BiometricFilter" type="{http://www.daon.com/ws/de}BiometricFilter" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="fetchExternal" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExclusiveBiometricDataFilter", propOrder = {
    "biometricFilter"
})
public class ExclusiveBiometricDataFilter
    extends BiometricDataFilter
{

    @XmlElement(name = "BiometricFilter", required = true)
    protected List<BiometricFilter> biometricFilter;
    @XmlAttribute(name = "fetchExternal")
    protected Boolean fetchExternal;

    /**
     * Gets the value of the biometricFilter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the biometricFilter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBiometricFilter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BiometricFilter }
     * 
     * 
     */
    public List<BiometricFilter> getBiometricFilter() {
        if (biometricFilter == null) {
            biometricFilter = new ArrayList<BiometricFilter>();
        }
        return this.biometricFilter;
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
