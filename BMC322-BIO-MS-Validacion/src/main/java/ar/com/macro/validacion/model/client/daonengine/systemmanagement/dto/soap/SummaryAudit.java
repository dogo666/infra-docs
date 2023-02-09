//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.17 at 02:10:31 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Summary Audit Type
 * 
 * <p>Java class for SummaryAudit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SummaryAudit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SummaryAuditIdentifier" type="{http://www.daon.com/ws/de}SummaryAuditIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="IdentityIdentifier" type="{http://www.daon.com/ws/de}IdentityIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="API" type="{http://www.daon.com/ws/de}API"/&gt;
 *         &lt;element name="ApplicationAuditId" type="{http://www.daon.com/ws/de}ApplicationAuditId" minOccurs="0"/&gt;
 *         &lt;element name="ReturnCode" type="{http://www.daon.com/ws/de}ReturnCode"/&gt;
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedDtm" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="Score" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationIdentifier" type="{http://www.daon.com/ws/de}ApplicationIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationUserIdentifier" type="{http://www.daon.com/ws/de}ApplicationUserIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="CustomParameters" type="{http://www.daon.com/ws/de}CustomParameters" minOccurs="0"/&gt;
 *         &lt;element name="RequestCorrelationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MatchSummaryAudit" type="{http://www.daon.com/ws/de}MatchSummaryAudit" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummaryAudit", propOrder = {
    "summaryAuditIdentifier",
    "identityIdentifier",
    "api",
    "applicationAuditId",
    "returnCode",
    "message",
    "createdDtm",
    "score",
    "applicationIdentifier",
    "applicationUserIdentifier",
    "customParameters",
    "requestCorrelationId",
    "matchSummaryAudit"
})
public class SummaryAudit {

    @XmlElement(name = "SummaryAuditIdentifier")
    protected SummaryAuditIdentifier summaryAuditIdentifier;
    @XmlElement(name = "IdentityIdentifier")
    protected IdentityIdentifier identityIdentifier;
    @XmlElement(name = "API", required = true)
    protected API api;
    @XmlElement(name = "ApplicationAuditId")
    protected Long applicationAuditId;
    @XmlElement(name = "ReturnCode")
    protected int returnCode;
    @XmlElement(name = "Message", required = true)
    protected String message;
    @XmlElement(name = "CreatedDtm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDtm;
    @XmlElement(name = "Score")
    protected Double score;
    @XmlElement(name = "ApplicationIdentifier")
    protected String applicationIdentifier;
    @XmlElement(name = "ApplicationUserIdentifier")
    protected String applicationUserIdentifier;
    @XmlElement(name = "CustomParameters")
    protected CustomParameters customParameters;
    @XmlElement(name = "RequestCorrelationId")
    protected String requestCorrelationId;
    @XmlElement(name = "MatchSummaryAudit")
    protected List<MatchSummaryAudit> matchSummaryAudit;

    /**
     * Gets the value of the summaryAuditIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link SummaryAuditIdentifier }
     *     
     */
    public SummaryAuditIdentifier getSummaryAuditIdentifier() {
        return summaryAuditIdentifier;
    }

    /**
     * Sets the value of the summaryAuditIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link SummaryAuditIdentifier }
     *     
     */
    public void setSummaryAuditIdentifier(SummaryAuditIdentifier value) {
        this.summaryAuditIdentifier = value;
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
     * Gets the value of the api property.
     * 
     * @return
     *     possible object is
     *     {@link API }
     *     
     */
    public API getAPI() {
        return api;
    }

    /**
     * Sets the value of the api property.
     * 
     * @param value
     *     allowed object is
     *     {@link API }
     *     
     */
    public void setAPI(API value) {
        this.api = value;
    }

    /**
     * Gets the value of the applicationAuditId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getApplicationAuditId() {
        return applicationAuditId;
    }

    /**
     * Sets the value of the applicationAuditId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setApplicationAuditId(Long value) {
        this.applicationAuditId = value;
    }

    /**
     * Gets the value of the returnCode property.
     * 
     */
    public int getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     * 
     */
    public void setReturnCode(int value) {
        this.returnCode = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
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
     * Gets the value of the score property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setScore(Double value) {
        this.score = value;
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
     * Gets the value of the customParameters property.
     * 
     * @return
     *     possible object is
     *     {@link CustomParameters }
     *     
     */
    public CustomParameters getCustomParameters() {
        return customParameters;
    }

    /**
     * Sets the value of the customParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomParameters }
     *     
     */
    public void setCustomParameters(CustomParameters value) {
        this.customParameters = value;
    }

    /**
     * Gets the value of the requestCorrelationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestCorrelationId() {
        return requestCorrelationId;
    }

    /**
     * Sets the value of the requestCorrelationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestCorrelationId(String value) {
        this.requestCorrelationId = value;
    }

    /**
     * Gets the value of the matchSummaryAudit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the matchSummaryAudit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMatchSummaryAudit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MatchSummaryAudit }
     * 
     * 
     */
    public List<MatchSummaryAudit> getMatchSummaryAudit() {
        if (matchSummaryAudit == null) {
            matchSummaryAudit = new ArrayList<MatchSummaryAudit>();
        }
        return this.matchSummaryAudit;
    }

}