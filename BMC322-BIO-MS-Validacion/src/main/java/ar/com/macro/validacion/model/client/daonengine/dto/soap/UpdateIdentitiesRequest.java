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
 * Update or delete specific data of an existing
 * 							system
 * 							Identities.<p>
 * 
 * 							All Identity related data, with the exclusion of the
 * 							IdentityIdentifier and UniqueName, can be updated or
 * 							deleted. This includes core Identity data,
 * 							biographic data, biometric data and Group membership
 * 							data. <p>
 * 
 * 							This is an atomic operation - if an error occurs
 * 							updating any of the Identities specified then no
 * 							Identities will be updated. If there is no error
 * 							then all the Identities specified will be updated.
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
 *         &lt;element name="DomainUpdate" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DomainIdentifier" type="{http://www.daon.com/ws/de}DomainIdentifier"/&gt;
 *                   &lt;element name="IdentityIdentifier" type="{http://www.daon.com/ws/de}IdentityIdentifier" minOccurs="0"/&gt;
 *                   &lt;element name="TransformPolicyIdentifier" type="{http://www.daon.com/ws/de}PolicyIdentifier" minOccurs="0"/&gt;
 *                   &lt;element name="UpdateData" type="{http://www.daon.com/ws/de}Identity" minOccurs="0"/&gt;
 *                   &lt;element name="DeleteData" type="{http://www.daon.com/ws/de}IdentityDescription" minOccurs="0"/&gt;
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
    "domainUpdate"
})
@XmlRootElement(name = "UpdateIdentitiesRequest")
public class UpdateIdentitiesRequest
    extends AbstractRequest
{

    @XmlElement(name = "DomainUpdate", required = true)
    protected List<DomainUpdate> domainUpdate;

    /**
     * Gets the value of the domainUpdate property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domainUpdate property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomainUpdate().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DomainUpdate }
     *
     *
     */
    public List<DomainUpdate> getDomainUpdate() {
        if (domainUpdate == null) {
            domainUpdate = new ArrayList<DomainUpdate>();
        }
        return this.domainUpdate;
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
     *         &lt;element name="IdentityIdentifier" type="{http://www.daon.com/ws/de}IdentityIdentifier" minOccurs="0"/&gt;
     *         &lt;element name="TransformPolicyIdentifier" type="{http://www.daon.com/ws/de}PolicyIdentifier" minOccurs="0"/&gt;
     *         &lt;element name="UpdateData" type="{http://www.daon.com/ws/de}Identity" minOccurs="0"/&gt;
     *         &lt;element name="DeleteData" type="{http://www.daon.com/ws/de}IdentityDescription" minOccurs="0"/&gt;
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
        "identityIdentifier",
        "transformPolicyIdentifier",
        "updateData",
        "deleteData"
    })
    public static class DomainUpdate {

        @XmlElement(name = "DomainIdentifier", required = true)
        protected DomainIdentifier domainIdentifier;
        @XmlElement(name = "IdentityIdentifier")
        protected IdentityIdentifier identityIdentifier;
        @XmlElement(name = "TransformPolicyIdentifier")
        protected PolicyIdentifier transformPolicyIdentifier;
        @XmlElement(name = "UpdateData")
        protected Identity updateData;
        @XmlElement(name = "DeleteData")
        protected IdentityDescription deleteData;

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
         * Gets the value of the updateData property.
         * 
         * @return
         *     possible object is
         *     {@link Identity }
         *     
         */
        public Identity getUpdateData() {
            return updateData;
        }

        /**
         * Sets the value of the updateData property.
         * 
         * @param value
         *     allowed object is
         *     {@link Identity }
         *     
         */
        public void setUpdateData(Identity value) {
            this.updateData = value;
        }

        /**
         * Gets the value of the deleteData property.
         * 
         * @return
         *     possible object is
         *     {@link IdentityDescription }
         *     
         */
        public IdentityDescription getDeleteData() {
            return deleteData;
        }

        /**
         * Sets the value of the deleteData property.
         * 
         * @param value
         *     allowed object is
         *     {@link IdentityDescription }
         *     
         */
        public void setDeleteData(IdentityDescription value) {
            this.deleteData = value;
        }

    }

}
