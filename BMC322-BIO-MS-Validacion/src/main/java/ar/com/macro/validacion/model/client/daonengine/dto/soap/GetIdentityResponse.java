//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.validacion.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.*;


/**
 * Return an Identity including associated
 * 							biometric
 * 							and biographic data.
 * 						
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.daon.com/ws/de}AbstractDaonEngineResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ResponseData" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Identity" type="{http://www.daon.com/ws/de}Identity"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "responseData"
})
@XmlRootElement(name = "GetIdentityResponse")
public class GetIdentityResponse
    extends AbstractDaonEngineResponse
{

    @XmlElement(name = "ResponseData")
    protected ResponseData responseData;

    /**
     * Gets the value of the responseData property.
     *
     * @return
     *     possible object is
     *     {@link ResponseData }
     *
     */
    public ResponseData getResponseData() {
        return responseData;
    }

    /**
     * Sets the value of the responseData property.
     *
     * @param value
     *     allowed object is
     *     {@link ResponseData }
     *
     */
    public void setResponseData(ResponseData value) {
        this.responseData = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Identity" type="{http://www.daon.com/ws/de}Identity"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "identity"
    })
    public static class ResponseData {

        @XmlElement(name = "Identity", required = true)
        protected Identity identity;

        /**
         * Gets the value of the identity property.
         * 
         * @return
         *     possible object is
         *     {@link Identity }
         *     
         */
        public Identity getIdentity() {
            return identity;
        }

        /**
         * Sets the value of the identity property.
         * 
         * @param value
         *     allowed object is
         *     {@link Identity }
         *     
         */
        public void setIdentity(Identity value) {
            this.identity = value;
        }

    }

}
