//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.14 at 09:18:33 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.identification.dto.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.daon.com/ws/de}AbstractRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DomainIdentifier" type="{http://www.daon.com/ws/de}DomainIdentifier"/&gt;
 *         &lt;element name="RequestTrackingUID" type="{http://www.daon.com/ws/de}RequestTrackingUID"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "domainIdentifier",
    "requestTrackingUID"
})
@XmlRootElement(name = "GetVerificationAdjudicationRequest")
public class GetVerificationAdjudicationRequest
    extends AbstractRequest
{

    @XmlElement(name = "DomainIdentifier", required = true)
    protected DomainIdentifier domainIdentifier;
    @XmlElement(name = "RequestTrackingUID", required = true)
    protected String requestTrackingUID;

    /**
     * Gets the value of the domainIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link DomainIdentifier }
     *     
     */
    public DomainIdentifier getDomainIdentifier() {
        return domainIdentifier;
    }

    /**
     * Sets the value of the domainIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainIdentifier }
     *     
     */
    public void setDomainIdentifier(DomainIdentifier value) {
        this.domainIdentifier = value;
    }

    /**
     * Gets the value of the requestTrackingUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestTrackingUID() {
        return requestTrackingUID;
    }

    /**
     * Sets the value of the requestTrackingUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestTrackingUID(String value) {
        this.requestTrackingUID = value;
    }

}
