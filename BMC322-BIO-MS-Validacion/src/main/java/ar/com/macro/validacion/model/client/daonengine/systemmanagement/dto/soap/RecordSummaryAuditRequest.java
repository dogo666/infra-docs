//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.17 at 02:10:31 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Add custom audit data to the summary audit trail for a specified customer using the custom fields in the
 * 					GeneralAPIParameters: customLong1, customString1 and customString2.
 * 
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
 *         &lt;element name="SummaryAudit" type="{http://www.daon.com/ws/de}SummaryAudit"/&gt;
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
    "summaryAudit"
})
@XmlRootElement(name = "RecordSummaryAuditRequest")
public class RecordSummaryAuditRequest
    extends AbstractRequest
{

    @XmlElement(name = "DomainIdentifier", required = true)
    protected DomainIdentifier domainIdentifier;
    @XmlElement(name = "SummaryAudit", required = true)
    protected SummaryAudit summaryAudit;

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
     * Gets the value of the summaryAudit property.
     * 
     * @return
     *     possible object is
     *     {@link SummaryAudit }
     *     
     */
    public SummaryAudit getSummaryAudit() {
        return summaryAudit;
    }

    /**
     * Sets the value of the summaryAudit property.
     * 
     * @param value
     *     allowed object is
     *     {@link SummaryAudit }
     *     
     */
    public void setSummaryAudit(SummaryAudit value) {
        this.summaryAudit = value;
    }

}
