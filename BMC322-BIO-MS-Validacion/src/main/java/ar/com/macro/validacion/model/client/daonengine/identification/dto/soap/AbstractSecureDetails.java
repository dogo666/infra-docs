//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.14 at 09:18:33 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.identification.dto.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Base type for Secure Details.
 * 
 * <p>Java class for AbstractSecureDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractSecureDetails"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EncryptingKeyIdentifier" type="{http://www.daon.com/ws/de}KeyIdentifier"/&gt;
 *         &lt;element name="SessionKeyType" type="{http://www.daon.com/ws/de}SymmetricKeyType"/&gt;
 *         &lt;element name="EncryptedSessionKey" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="CompressionAlgorithm" type="{http://www.daon.com/ws/de}CompressionAlgorithm"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractSecureDetails", propOrder = {
    "encryptingKeyIdentifier",
    "sessionKeyType",
    "encryptedSessionKey",
    "compressionAlgorithm"
})
@XmlSeeAlso({
    SecureEnvelope.class
})
public abstract class AbstractSecureDetails {

    @XmlElement(name = "EncryptingKeyIdentifier", required = true)
    protected KeyIdentifier encryptingKeyIdentifier;
    @XmlElement(name = "SessionKeyType", required = true)
    @XmlSchemaType(name = "string")
    protected SymmetricKeyType sessionKeyType;
    @XmlElement(name = "EncryptedSessionKey", required = true)
    protected byte[] encryptedSessionKey;
    @XmlElement(name = "CompressionAlgorithm", required = true)
    @XmlSchemaType(name = "string")
    protected CompressionAlgorithm compressionAlgorithm;

    /**
     * Gets the value of the encryptingKeyIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link KeyIdentifier }
     *     
     */
    public KeyIdentifier getEncryptingKeyIdentifier() {
        return encryptingKeyIdentifier;
    }

    /**
     * Sets the value of the encryptingKeyIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyIdentifier }
     *     
     */
    public void setEncryptingKeyIdentifier(KeyIdentifier value) {
        this.encryptingKeyIdentifier = value;
    }

    /**
     * Gets the value of the sessionKeyType property.
     * 
     * @return
     *     possible object is
     *     {@link SymmetricKeyType }
     *     
     */
    public SymmetricKeyType getSessionKeyType() {
        return sessionKeyType;
    }

    /**
     * Sets the value of the sessionKeyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SymmetricKeyType }
     *     
     */
    public void setSessionKeyType(SymmetricKeyType value) {
        this.sessionKeyType = value;
    }

    /**
     * Gets the value of the encryptedSessionKey property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEncryptedSessionKey() {
        return encryptedSessionKey;
    }

    /**
     * Sets the value of the encryptedSessionKey property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEncryptedSessionKey(byte[] value) {
        this.encryptedSessionKey = value;
    }

    /**
     * Gets the value of the compressionAlgorithm property.
     * 
     * @return
     *     possible object is
     *     {@link CompressionAlgorithm }
     *     
     */
    public CompressionAlgorithm getCompressionAlgorithm() {
        return compressionAlgorithm;
    }

    /**
     * Sets the value of the compressionAlgorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompressionAlgorithm }
     *     
     */
    public void setCompressionAlgorithm(CompressionAlgorithm value) {
        this.compressionAlgorithm = value;
    }

}
