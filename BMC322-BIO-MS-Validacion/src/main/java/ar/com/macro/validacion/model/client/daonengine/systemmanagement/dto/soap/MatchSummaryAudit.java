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
import javax.xml.bind.annotation.XmlType;


/**
 * Summary Audit Identity Type.
 * 
 * <p>Java class for MatchSummaryAudit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MatchSummaryAudit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AuditOrder" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="IdentityIdentifier" type="{http://www.daon.com/ws/de}IdentityIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="Score" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="TypeQualifier" type="{http://www.daon.com/ws/de}TypeQualifier"/&gt;
 *         &lt;element name="UsageQualifier" type="{http://www.daon.com/ws/de}UsageQualifier" minOccurs="0"/&gt;
 *         &lt;element name="PolicyIdentifier" type="{http://www.daon.com/ws/de}PolicyIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="RawScore" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="MatcherIdentifier" type="{http://www.daon.com/ws/de}MatcherIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="ExternalMatcherIdentifier" type="{http://www.daon.com/ws/de}ExternalMatcherIdentifier" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MatchSummaryAudit", propOrder = {
    "auditOrder",
    "identityIdentifier",
    "score",
    "typeQualifier",
    "usageQualifier",
    "policyIdentifier",
    "rawScore",
    "matcherIdentifier",
    "externalMatcherIdentifier"
})
public class MatchSummaryAudit {

    @XmlElement(name = "AuditOrder")
    protected Integer auditOrder;
    @XmlElement(name = "IdentityIdentifier")
    protected IdentityIdentifier identityIdentifier;
    @XmlElement(name = "Score")
    protected double score;
    @XmlElement(name = "TypeQualifier")
    protected int typeQualifier;
    @XmlElement(name = "UsageQualifier")
    protected Integer usageQualifier;
    @XmlElement(name = "PolicyIdentifier")
    protected PolicyIdentifier policyIdentifier;
    @XmlElement(name = "RawScore")
    protected Double rawScore;
    @XmlElement(name = "MatcherIdentifier")
    protected MatcherIdentifier matcherIdentifier;
    @XmlElement(name = "ExternalMatcherIdentifier")
    protected ExternalMatcherIdentifier externalMatcherIdentifier;

    /**
     * Gets the value of the auditOrder property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAuditOrder() {
        return auditOrder;
    }

    /**
     * Sets the value of the auditOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAuditOrder(Integer value) {
        this.auditOrder = value;
    }

    /**
     * Gets the value of the identityIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityIdentifier }
     *     
     */
    public IdentityIdentifier getIdentityIdentifier() {
        return identityIdentifier;
    }

    /**
     * Sets the value of the identityIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityIdentifier }
     *     
     */
    public void setIdentityIdentifier(IdentityIdentifier value) {
        this.identityIdentifier = value;
    }

    /**
     * Gets the value of the score property.
     * 
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     */
    public void setScore(double value) {
        this.score = value;
    }

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
     * Gets the value of the rawScore property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRawScore() {
        return rawScore;
    }

    /**
     * Sets the value of the rawScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRawScore(Double value) {
        this.rawScore = value;
    }

    /**
     * Gets the value of the matcherIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link MatcherIdentifier }
     *     
     */
    public MatcherIdentifier getMatcherIdentifier() {
        return matcherIdentifier;
    }

    /**
     * Sets the value of the matcherIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatcherIdentifier }
     *     
     */
    public void setMatcherIdentifier(MatcherIdentifier value) {
        this.matcherIdentifier = value;
    }

    /**
     * Gets the value of the externalMatcherIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalMatcherIdentifier }
     *     
     */
    public ExternalMatcherIdentifier getExternalMatcherIdentifier() {
        return externalMatcherIdentifier;
    }

    /**
     * Sets the value of the externalMatcherIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalMatcherIdentifier }
     *     
     */
    public void setExternalMatcherIdentifier(ExternalMatcherIdentifier value) {
        this.externalMatcherIdentifier = value;
    }

}
