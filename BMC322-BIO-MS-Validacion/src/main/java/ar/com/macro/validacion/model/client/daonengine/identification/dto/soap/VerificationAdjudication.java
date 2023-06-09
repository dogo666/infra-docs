//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.14 at 09:18:33 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.identification.dto.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerificationAdjudication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VerificationAdjudication"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OverallDecision" type="{http://www.daon.com/ws/de}VerificationAdjudicationDecision"/&gt;
 *         &lt;element name="SampleDecision" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{http://www.daon.com/ws/de}VerificationAdjudicationDecision"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="TypeQualifier" type="{http://www.daon.com/ws/de}TypeQualifier"/&gt;
 *                   &lt;element name="UsageQualifier" type="{http://www.daon.com/ws/de}UsageQualifier" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificationAdjudication", propOrder = {
    "overallDecision",
    "sampleDecision"
})
public class VerificationAdjudication {

    @XmlElement(name = "OverallDecision", required = true)
    protected VerificationAdjudicationDecision overallDecision;
    @XmlElement(name = "SampleDecision")
    protected List<VerificationAdjudication.SampleDecision> sampleDecision;

    /**
     * Gets the value of the overallDecision property.
     * 
     * @return
     *     possible object is
     *     {@link VerificationAdjudicationDecision }
     *     
     */
    public VerificationAdjudicationDecision getOverallDecision() {
        return overallDecision;
    }

    /**
     * Sets the value of the overallDecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationAdjudicationDecision }
     *     
     */
    public void setOverallDecision(VerificationAdjudicationDecision value) {
        this.overallDecision = value;
    }

    /**
     * Gets the value of the sampleDecision property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sampleDecision property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSampleDecision().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VerificationAdjudication.SampleDecision }
     * 
     * 
     */
    public List<VerificationAdjudication.SampleDecision> getSampleDecision() {
        if (sampleDecision == null) {
            sampleDecision = new ArrayList<VerificationAdjudication.SampleDecision>();
        }
        return this.sampleDecision;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{http://www.daon.com/ws/de}VerificationAdjudicationDecision"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="TypeQualifier" type="{http://www.daon.com/ws/de}TypeQualifier"/&gt;
     *         &lt;element name="UsageQualifier" type="{http://www.daon.com/ws/de}UsageQualifier" minOccurs="0"/&gt;
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
        "typeQualifier",
        "usageQualifier"
    })
    public static class SampleDecision
        extends VerificationAdjudicationDecision
    {

        @XmlElement(name = "TypeQualifier")
        protected int typeQualifier;
        @XmlElement(name = "UsageQualifier")
        protected Integer usageQualifier;

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

    }

}
