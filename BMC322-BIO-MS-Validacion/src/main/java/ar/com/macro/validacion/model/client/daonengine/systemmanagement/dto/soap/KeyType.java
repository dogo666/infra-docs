//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.17 at 02:10:31 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KeyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="KeyType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="TRIPLE_DES_192"/&gt;
 *     &lt;enumeration value="AES_128"/&gt;
 *     &lt;enumeration value="AES_192"/&gt;
 *     &lt;enumeration value="AES_256"/&gt;
 *     &lt;enumeration value="RSA_512"/&gt;
 *     &lt;enumeration value="RSA_1024"/&gt;
 *     &lt;enumeration value="RSA_2048"/&gt;
 *     &lt;enumeration value="DSA_512"/&gt;
 *     &lt;enumeration value="DSA_1024"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "KeyType")
@XmlEnum
public enum KeyType {

    TRIPLE_DES_192,
    AES_128,
    AES_192,
    AES_256,
    RSA_512,
    RSA_1024,
    RSA_2048,
    DSA_512,
    DSA_1024;

    public String value() {
        return name();
    }

    public static KeyType fromValue(String v) {
        return valueOf(v);
    }

}