//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.validacion.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Filter used to exclude biographic data.
 * 					
 * 
 * <p>Java class for ExclusiveBiographicDataFilter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExclusiveBiographicDataFilter"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.daon.com/ws/de}BiographicDataFilter"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BiographicFilter" type="{http://www.daon.com/ws/de}BiographicFilter" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExclusiveBiographicDataFilter", propOrder = {
    "biographicFilter"
})
public class ExclusiveBiographicDataFilter
    extends BiographicDataFilter
{

    @XmlElement(name = "BiographicFilter", required = true)
    protected List<BiographicFilter> biographicFilter;

    /**
     * Gets the value of the biographicFilter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the biographicFilter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBiographicFilter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BiographicFilter }
     * 
     * 
     */
    public List<BiographicFilter> getBiographicFilter() {
        if (biographicFilter == null) {
            biographicFilter = new ArrayList<BiographicFilter>();
        }
        return this.biographicFilter;
    }

}
