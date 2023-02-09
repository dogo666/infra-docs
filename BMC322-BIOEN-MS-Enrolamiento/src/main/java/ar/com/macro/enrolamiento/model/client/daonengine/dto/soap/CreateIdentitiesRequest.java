//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.enrolamiento.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Create a multiple Identities with the given
 * 							Identity
 * 							data.<p>
 * 
 * 							This is an atomic operation - if an error occurs
 * 							creating any of the Identities specified then no
 * 							Identities will be created. If there is no error
 * 							then all the Identities specified will be created an
 * 							IdentityIdentifier for each identity will be
 * 							returned in the same order as the identities in the
 * 							request.
 * 						
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
 *         &lt;element name="DomainCreate" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DomainIdentifier" type="{http://www.daon.com/ws/de}DomainIdentifier"/&gt;
 *                   &lt;element name="TransformPolicyIdentifier" type="{http://www.daon.com/ws/de}PolicyIdentifier" minOccurs="0"/&gt;
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
    "domainCreate"
})
@XmlRootElement(name = "CreateIdentitiesRequest")
public class CreateIdentitiesRequest
    extends AbstractRequest
{

    @XmlElement(name = "DomainCreate", required = true)
    protected List<DomainCreate> domainCreate;

    /**
     * Gets the value of the domainCreate property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domainCreate property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomainCreate().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DomainCreate }
     *
     *
     */
    public List<DomainCreate> getDomainCreate() {
        if (domainCreate == null) {
            domainCreate = new ArrayList<DomainCreate>();
        }
        return this.domainCreate;
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
     *         &lt;element name="DomainIdentifier" type="{http://www.daon.com/ws/de}DomainIdentifier"/&gt;
     *         &lt;element name="TransformPolicyIdentifier" type="{http://www.daon.com/ws/de}PolicyIdentifier" minOccurs="0"/&gt;
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
        "domainIdentifier",
        "transformPolicyIdentifier",
        "identity"
    })
    public static class DomainCreate {

        @XmlElement(name = "DomainIdentifier", required = true)
        protected DomainIdentifier domainIdentifier;
        @XmlElement(name = "TransformPolicyIdentifier")
        protected PolicyIdentifier transformPolicyIdentifier;
        @XmlElement(name = "Identity", required = true)
        protected Identity identity;

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
         * Gets the value of the transformPolicyIdentifier property.
         * 
         * @return
         *     possible object is
         *     {@link PolicyIdentifier }
         *     
         */
        public PolicyIdentifier getTransformPolicyIdentifier() {
            return transformPolicyIdentifier;
        }

        /**
         * Sets the value of the transformPolicyIdentifier property.
         * 
         * @param value
         *     allowed object is
         *     {@link PolicyIdentifier }
         *     
         */
        public void setTransformPolicyIdentifier(PolicyIdentifier value) {
            this.transformPolicyIdentifier = value;
        }

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
