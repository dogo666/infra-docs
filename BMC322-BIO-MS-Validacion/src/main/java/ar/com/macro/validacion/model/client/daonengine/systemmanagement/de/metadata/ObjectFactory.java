//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.17 at 02:10:31 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.systemmanagement.de.metadata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.daon.de.metadata package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Binary_QNAME = new QName("http://www.daon.com/de/metadata", "Binary");
    private final static QName _NameValuePair_QNAME = new QName("http://www.daon.com/de/metadata", "NameValuePair");
    private final static QName _Boolean_QNAME = new QName("http://www.daon.com/de/metadata", "Boolean");
    private final static QName _DataTime_QNAME = new QName("http://www.daon.com/de/metadata", "DataTime");
    private final static QName _Date_QNAME = new QName("http://www.daon.com/de/metadata", "Date");
    private final static QName _Double_QNAME = new QName("http://www.daon.com/de/metadata", "Double");
    private final static QName _Integer_QNAME = new QName("http://www.daon.com/de/metadata", "Integer");
    private final static QName _MetaData_QNAME = new QName("http://www.daon.com/de/metadata", "MetaData");
    private final static QName _MetaDataItem_QNAME = new QName("http://www.daon.com/de/metadata", "MetaDataItem");
    private final static QName _Nested_QNAME = new QName("http://www.daon.com/de/metadata", "Nested");
    private final static QName _OptionalNestedNameValuePair_QNAME = new QName("http://www.daon.com/de/metadata", "OptionalNestedNameValuePair");
    private final static QName _String_QNAME = new QName("http://www.daon.com/de/metadata", "String");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.daon.de.metadata
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BinaryNameValuePair }
     * 
     */
    public BinaryNameValuePair createBinaryNameValuePair() {
        return new BinaryNameValuePair();
    }

    /**
     * Create an instance of {@link BooleanNameValuePair }
     * 
     */
    public BooleanNameValuePair createBooleanNameValuePair() {
        return new BooleanNameValuePair();
    }

    /**
     * Create an instance of {@link DateTimeNameValuePair }
     * 
     */
    public DateTimeNameValuePair createDateTimeNameValuePair() {
        return new DateTimeNameValuePair();
    }

    /**
     * Create an instance of {@link DateNameValuePair }
     * 
     */
    public DateNameValuePair createDateNameValuePair() {
        return new DateNameValuePair();
    }

    /**
     * Create an instance of {@link DoubleNameValuePair }
     * 
     */
    public DoubleNameValuePair createDoubleNameValuePair() {
        return new DoubleNameValuePair();
    }

    /**
     * Create an instance of {@link IntegerNameValuePair }
     * 
     */
    public IntegerNameValuePair createIntegerNameValuePair() {
        return new IntegerNameValuePair();
    }

    /**
     * Create an instance of {@link MetaData }
     * 
     */
    public MetaData createMetaData() {
        return new MetaData();
    }

    /**
     * Create an instance of {@link MetaDataItem }
     * 
     */
    public MetaDataItem createMetaDataItem() {
        return new MetaDataItem();
    }

    /**
     * Create an instance of {@link NestedNameValuePair }
     * 
     */
    public NestedNameValuePair createNestedNameValuePair() {
        return new NestedNameValuePair();
    }

    /**
     * Create an instance of {@link OptionalNestedNameValuePair }
     * 
     */
    public OptionalNestedNameValuePair createOptionalNestedNameValuePair() {
        return new OptionalNestedNameValuePair();
    }

    /**
     * Create an instance of {@link StringNameValuePair }
     * 
     */
    public StringNameValuePair createStringNameValuePair() {
        return new StringNameValuePair();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BinaryNameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BinaryNameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "Binary", substitutionHeadNamespace = "http://www.daon.com/de/metadata", substitutionHeadName = "NameValuePair")
    public JAXBElement<BinaryNameValuePair> createBinary(BinaryNameValuePair value) {
        return new JAXBElement<BinaryNameValuePair>(_Binary_QNAME, BinaryNameValuePair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "NameValuePair")
    public JAXBElement<NameValuePair> createNameValuePair(NameValuePair value) {
        return new JAXBElement<NameValuePair>(_NameValuePair_QNAME, NameValuePair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BooleanNameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BooleanNameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "Boolean", substitutionHeadNamespace = "http://www.daon.com/de/metadata", substitutionHeadName = "NameValuePair")
    public JAXBElement<BooleanNameValuePair> createBoolean(BooleanNameValuePair value) {
        return new JAXBElement<BooleanNameValuePair>(_Boolean_QNAME, BooleanNameValuePair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeNameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateTimeNameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "DataTime", substitutionHeadNamespace = "http://www.daon.com/de/metadata", substitutionHeadName = "NameValuePair")
    public JAXBElement<DateTimeNameValuePair> createDataTime(DateTimeNameValuePair value) {
        return new JAXBElement<DateTimeNameValuePair>(_DataTime_QNAME, DateTimeNameValuePair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateNameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateNameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "Date", substitutionHeadNamespace = "http://www.daon.com/de/metadata", substitutionHeadName = "NameValuePair")
    public JAXBElement<DateNameValuePair> createDate(DateNameValuePair value) {
        return new JAXBElement<DateNameValuePair>(_Date_QNAME, DateNameValuePair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoubleNameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DoubleNameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "Double", substitutionHeadNamespace = "http://www.daon.com/de/metadata", substitutionHeadName = "NameValuePair")
    public JAXBElement<DoubleNameValuePair> createDouble(DoubleNameValuePair value) {
        return new JAXBElement<DoubleNameValuePair>(_Double_QNAME, DoubleNameValuePair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntegerNameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IntegerNameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "Integer", substitutionHeadNamespace = "http://www.daon.com/de/metadata", substitutionHeadName = "NameValuePair")
    public JAXBElement<IntegerNameValuePair> createInteger(IntegerNameValuePair value) {
        return new JAXBElement<IntegerNameValuePair>(_Integer_QNAME, IntegerNameValuePair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetaData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetaData }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "MetaData")
    public JAXBElement<MetaData> createMetaData(MetaData value) {
        return new JAXBElement<MetaData>(_MetaData_QNAME, MetaData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetaDataItem }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MetaDataItem }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "MetaDataItem")
    public JAXBElement<MetaDataItem> createMetaDataItem(MetaDataItem value) {
        return new JAXBElement<MetaDataItem>(_MetaDataItem_QNAME, MetaDataItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NestedNameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NestedNameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "Nested", substitutionHeadNamespace = "http://www.daon.com/de/metadata", substitutionHeadName = "NameValuePair")
    public JAXBElement<NestedNameValuePair> createNested(NestedNameValuePair value) {
        return new JAXBElement<NestedNameValuePair>(_Nested_QNAME, NestedNameValuePair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OptionalNestedNameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OptionalNestedNameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "OptionalNestedNameValuePair")
    public JAXBElement<OptionalNestedNameValuePair> createOptionalNestedNameValuePair(OptionalNestedNameValuePair value) {
        return new JAXBElement<OptionalNestedNameValuePair>(_OptionalNestedNameValuePair_QNAME, OptionalNestedNameValuePair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringNameValuePair }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringNameValuePair }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.daon.com/de/metadata", name = "String", substitutionHeadNamespace = "http://www.daon.com/de/metadata", substitutionHeadName = "NameValuePair")
    public JAXBElement<StringNameValuePair> createString(StringNameValuePair value) {
        return new JAXBElement<StringNameValuePair>(_String_QNAME, StringNameValuePair.class, null, value);
    }

}
