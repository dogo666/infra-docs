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
import javax.xml.bind.annotation.XmlType;


/**
 * The score for an individual sample as returned by a matcher.
 * 
 * <p>Java class for SampleScore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SampleScore"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TypeQualifier" type="{http://www.daon.com/ws/de}TypeQualifier"/&gt;
 *         &lt;element name="UsageQualifier" type="{http://www.daon.com/ws/de}UsageQualifier" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.daon.com/ws/de}AbstractScore" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SampleScore", propOrder = {
    "typeQualifier",
    "usageQualifier",
    "abstractScore"
})
public class SampleScore {

    @XmlElement(name = "TypeQualifier")
    protected int typeQualifier;
    @XmlElement(name = "UsageQualifier")
    protected Integer usageQualifier;
    @XmlElement(name = "AbstractScore", required = true)
    protected List<AbstractScore> abstractScore;

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
     * One or more scores for the sample as determined by the matcher.Gets the value of the abstractScore property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abstractScore property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbstractScore().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AbstractScore }
     * 
     * 
     */
    public List<AbstractScore> getAbstractScore() {
        if (abstractScore == null) {
            abstractScore = new ArrayList<AbstractScore>();
        }
        return this.abstractScore;
    }

}
