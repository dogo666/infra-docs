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
 * An element containing a summary description of the data
 *             	associated with an Identity. This data includes the
 *             	Identity's core data, biographics, biometrics and Group
 *             	membership details.
 * 
 *             	The IdentityDescription provides a lightweight
 *             	representation of an Identity - it provides details of
 *             	they types of biographics and biometrics an Identity
 *             	contains without returning the actual data.
 * 
 * <p>Java class for IdentityDescription complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentityDescription"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdentityIdentifier" type="{http://www.daon.com/ws/de}IdentityIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="CoreData" type="{http://www.daon.com/ws/de}CoreData" minOccurs="0"/&gt;
 *         &lt;element name="BiographicDataInfo" type="{http://www.daon.com/ws/de}BiographicDataInfo" minOccurs="0"/&gt;
 *         &lt;element name="BiometricDataInfo" type="{http://www.daon.com/ws/de}BiometricItemInfo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="GroupMembership" type="{http://www.daon.com/ws/de}GroupMembership" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentityDescription", propOrder = {
    "identityIdentifier",
    "coreData",
    "biographicDataInfo",
    "biometricDataInfo",
    "groupMembership"
})
public class IdentityDescription {

    @XmlElement(name = "IdentityIdentifier")
    protected IdentityIdentifier identityIdentifier;
    @XmlElement(name = "CoreData")
    protected CoreData coreData;
    @XmlElement(name = "BiographicDataInfo")
    protected BiographicDataInfo biographicDataInfo;
    @XmlElement(name = "BiometricDataInfo")
    protected List<BiometricItemInfo> biometricDataInfo;
    @XmlElement(name = "GroupMembership")
    protected List<GroupMembership> groupMembership;

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
     * Gets the value of the coreData property.
     * 
     * @return
     *     possible object is
     *     {@link CoreData }
     *     
     */
    public CoreData getCoreData() {
        return coreData;
    }

    /**
     * Sets the value of the coreData property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoreData }
     *     
     */
    public void setCoreData(CoreData value) {
        this.coreData = value;
    }

    /**
     * Gets the value of the biographicDataInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BiographicDataInfo }
     *     
     */
    public BiographicDataInfo getBiographicDataInfo() {
        return biographicDataInfo;
    }

    /**
     * Sets the value of the biographicDataInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BiographicDataInfo }
     *     
     */
    public void setBiographicDataInfo(BiographicDataInfo value) {
        this.biographicDataInfo = value;
    }

    /**
     * Gets the value of the biometricDataInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the biometricDataInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBiometricDataInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BiometricItemInfo }
     * 
     * 
     */
    public List<BiometricItemInfo> getBiometricDataInfo() {
        if (biometricDataInfo == null) {
            biometricDataInfo = new ArrayList<BiometricItemInfo>();
        }
        return this.biometricDataInfo;
    }

    /**
     * Gets the value of the groupMembership property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupMembership property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupMembership().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GroupMembership }
     * 
     * 
     */
    public List<GroupMembership> getGroupMembership() {
        if (groupMembership == null) {
            groupMembership = new ArrayList<GroupMembership>();
        }
        return this.groupMembership;
    }

}
