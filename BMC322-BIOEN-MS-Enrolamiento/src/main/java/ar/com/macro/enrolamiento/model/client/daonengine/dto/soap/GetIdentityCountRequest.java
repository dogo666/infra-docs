//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.enrolamiento.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.*;


/**
 * Count the number of Identities matching the
 * 							provided criteria.
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
 *         &lt;element name="IdentityUniqueNameLike" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SQLSearchCriteria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "identityUniqueNameLike",
    "sqlSearchCriteria"
})
@XmlRootElement(name = "GetIdentityCountRequest")
public class GetIdentityCountRequest
    extends AbstractRequest
{

    @XmlElement(name = "DomainIdentifier", required = true)
    protected DomainIdentifier domainIdentifier;
    @XmlElement(name = "IdentityUniqueNameLike")
    protected String identityUniqueNameLike;
    @XmlElement(name = "SQLSearchCriteria")
    protected String sqlSearchCriteria;

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
     * Gets the value of the identityUniqueNameLike property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentityUniqueNameLike() {
        return identityUniqueNameLike;
    }

    /**
     * Sets the value of the identityUniqueNameLike property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentityUniqueNameLike(String value) {
        this.identityUniqueNameLike = value;
    }

    /**
     * Gets the value of the sqlSearchCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSQLSearchCriteria() {
        return sqlSearchCriteria;
    }

    /**
     * Sets the value of the sqlSearchCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSQLSearchCriteria(String value) {
        this.sqlSearchCriteria = value;
    }

}
