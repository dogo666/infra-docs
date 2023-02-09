//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.14 at 09:18:33 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.identification.dto.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * MetaDataItem - container for name value pairs
 * 
 * <p>Java class for MetaDataItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MetaDataItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.daon.com/de/metadata}NameValuePair" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="subtype" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetaDataItem", propOrder = {
    "nameValuePair"
})
public class MetaDataItem {

    @XmlElementRef(name = "NameValuePair", namespace = "http://www.daon.com/de/metadata", type = JAXBElement.class)
    protected List<JAXBElement<? extends NameValuePair>> nameValuePair;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "subtype")
    protected String subtype;

    /**
     * Gets the value of the nameValuePair property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nameValuePair property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameValuePair().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link DoubleNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link IntegerNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link DateNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link BinaryNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link NestedNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link BooleanNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link StringNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link DateTimeNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link NameValuePair }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends NameValuePair>> getNameValuePair() {
        if (nameValuePair == null) {
            nameValuePair = new ArrayList<JAXBElement<? extends NameValuePair>>();
        }
        return this.nameValuePair;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the subtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * Sets the value of the subtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubtype(String value) {
        this.subtype = value;
    }

}
