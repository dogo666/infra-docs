//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.07.08 at 01:37:27 PM PET 
//


package ar.com.macro.validacion.model.client.renaper.dto.soap;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for generarTransaccionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generarTransaccionResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="tcn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generarTransaccionResponse", propOrder = {

})
@XmlRootElement(name = "generarTransaccionResponse")
public class GenerarTransaccionResponse {

    @XmlElement(name = "tcn", required = true)
    protected String tcn;

    /**
     * Gets the value of the tcn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTcn() {
        return tcn;
    }

    /**
     * Sets the value of the tcn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTcn(String value) {
        this.tcn = value;
    }

}
