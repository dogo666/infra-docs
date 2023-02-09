//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.14 at 09:18:33 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.identification.dto.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnSetOrder.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReturnSetOrder"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ASC"/&gt;
 *     &lt;enumeration value="DESC"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReturnSetOrder")
@XmlEnum
public enum ReturnSetOrder {


    /**
     * Denotes ascending order.
     * 
     */
    ASC,

    /**
     * Denotes descending order.
     * 
     */
    DESC;

    public String value() {
        return name();
    }

    public static ReturnSetOrder fromValue(String v) {
        return valueOf(v);
    }

}
