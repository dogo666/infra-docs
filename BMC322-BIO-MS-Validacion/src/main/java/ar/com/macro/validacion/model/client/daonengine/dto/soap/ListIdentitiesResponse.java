//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.validacion.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Return a list the Identities matching the
 * 							request
 * 							criteria. <p>
 * 
 * 							If a BiographicDataFilter was specified in the
 * 							request then only core data and the matching custom
 * 							biographic data will be returned for each Identity.
 * 							<p>
 * 
 * 							If no BiographicDataFilter was specified in the
 * 							request then only core data associated with the
 * 							given Identity will be returned.
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
 *                   &lt;element name="Identity" type="{http://www.daon.com/ws/de}Identity" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="CompleteListReturned" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                   &lt;element name="CompleteListSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
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
@XmlRootElement(name = "ListIdentitiesResponse")
public class ListIdentitiesResponse
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
     *         &lt;element name="Identity" type="{http://www.daon.com/ws/de}Identity" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="CompleteListReturned" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *         &lt;element name="CompleteListSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
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
        "identity",
        "completeListReturned",
        "completeListSize"
    })
    public static class ResponseData {

        @XmlElement(name = "Identity")
        protected List<Identity> identity;
        @XmlElement(name = "CompleteListReturned")
        protected boolean completeListReturned;
        @XmlElement(name = "CompleteListSize")
        protected Integer completeListSize;

        /**
         * Gets the value of the identity property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the identity property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIdentity().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Identity }
         * 
         * 
         */
        public List<Identity> getIdentity() {
            if (identity == null) {
                identity = new ArrayList<Identity>();
            }
            return this.identity;
        }

        /**
         * Gets the value of the completeListReturned property.
         * 
         */
        public boolean isCompleteListReturned() {
            return completeListReturned;
        }

        /**
         * Sets the value of the completeListReturned property.
         * 
         */
        public void setCompleteListReturned(boolean value) {
            this.completeListReturned = value;
        }

        /**
         * Gets the value of the completeListSize property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getCompleteListSize() {
            return completeListSize;
        }

        /**
         * Sets the value of the completeListSize property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCompleteListSize(Integer value) {
            this.completeListSize = value;
        }

    }

}
