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
 * <p>Java class for ReturnSetCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnSetCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="StartOffset" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumberToReturn" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnSetCriteria", propOrder = {
    "startOffset",
    "numberToReturn"
})
public class ReturnSetCriteria {

    @XmlElement(name = "StartOffset")
    protected int startOffset;
    @XmlElement(name = "NumberToReturn")
    protected int numberToReturn;

    /**
     * Gets the value of the startOffset property.
     * 
     */
    public int getStartOffset() {
        return startOffset;
    }

    /**
     * Sets the value of the startOffset property.
     * 
     */
    public void setStartOffset(int value) {
        this.startOffset = value;
    }

    /**
     * Gets the value of the numberToReturn property.
     * 
     */
    public int getNumberToReturn() {
        return numberToReturn;
    }

    /**
     * Sets the value of the numberToReturn property.
     * 
     */
    public void setNumberToReturn(int value) {
        this.numberToReturn = value;
    }

}
