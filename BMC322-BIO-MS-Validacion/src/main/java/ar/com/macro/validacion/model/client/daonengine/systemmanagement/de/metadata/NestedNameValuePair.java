//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.17 at 02:10:31 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.systemmanagement.de.metadata;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * NameValuePair requires at least one nested NameValuePair
 * 
 * <p>Java class for NestedNameValuePair complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NestedNameValuePair"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.daon.com/de/metadata}NameValuePair"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.daon.com/de/metadata}NameValuePair" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NestedNameValuePair", propOrder = {
    "nameValuePair"
})
public class NestedNameValuePair
    extends NameValuePair
{

    @XmlElementRef(name = "NameValuePair", namespace = "http://www.daon.com/de/metadata", type = JAXBElement.class)
    protected List<JAXBElement<? extends NameValuePair>> nameValuePair;

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
     * {@link JAXBElement }{@code <}{@link IntegerNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link DoubleNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link DateNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link NestedNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link DateTimeNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link StringNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link BinaryNameValuePair }{@code >}
     * {@link JAXBElement }{@code <}{@link BooleanNameValuePair }{@code >}
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

}