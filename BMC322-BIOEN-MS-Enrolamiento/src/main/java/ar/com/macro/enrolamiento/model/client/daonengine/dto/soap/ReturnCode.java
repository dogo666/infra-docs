//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.30 at 12:06:50 PM COT 
//


package ar.com.macro.enrolamiento.model.client.daonengine.dto.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnCodeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReturnCodeEnum"&gt;
 *   &lt;restriction base="{http://www.daon.com/ws/de}ReturnCode"&gt;
 *     &lt;enumeration value="0"/&gt;
 *     &lt;enumeration value="200"/&gt;
 *     &lt;enumeration value="201"/&gt;
 *     &lt;enumeration value="202"/&gt;
 *     &lt;enumeration value="203"/&gt;
 *     &lt;enumeration value="204"/&gt;
 *     &lt;enumeration value="205"/&gt;
 *     &lt;enumeration value="206"/&gt;
 *     &lt;enumeration value="207"/&gt;
 *     &lt;enumeration value="208"/&gt;
 *     &lt;enumeration value="209"/&gt;
 *     &lt;enumeration value="210"/&gt;
 *     &lt;enumeration value="211"/&gt;
 *     &lt;enumeration value="212"/&gt;
 *     &lt;enumeration value="213"/&gt;
 *     &lt;enumeration value="220"/&gt;
 *     &lt;enumeration value="221"/&gt;
 *     &lt;enumeration value="222"/&gt;
 *     &lt;enumeration value="223"/&gt;
 *     &lt;enumeration value="224"/&gt;
 *     &lt;enumeration value="225"/&gt;
 *     &lt;enumeration value="230"/&gt;
 *     &lt;enumeration value="231"/&gt;
 *     &lt;enumeration value="232"/&gt;
 *     &lt;enumeration value="233"/&gt;
 *     &lt;enumeration value="240"/&gt;
 *     &lt;enumeration value="241"/&gt;
 *     &lt;enumeration value="242"/&gt;
 *     &lt;enumeration value="243"/&gt;
 *     &lt;enumeration value="244"/&gt;
 *     &lt;enumeration value="245"/&gt;
 *     &lt;enumeration value="246"/&gt;
 *     &lt;enumeration value="247"/&gt;
 *     &lt;enumeration value="248"/&gt;
 *     &lt;enumeration value="250"/&gt;
 *     &lt;enumeration value="251"/&gt;
 *     &lt;enumeration value="252"/&gt;
 *     &lt;enumeration value="253"/&gt;
 *     &lt;enumeration value="254"/&gt;
 *     &lt;enumeration value="255"/&gt;
 *     &lt;enumeration value="256"/&gt;
 *     &lt;enumeration value="257"/&gt;
 *     &lt;enumeration value="258"/&gt;
 *     &lt;enumeration value="259"/&gt;
 *     &lt;enumeration value="260"/&gt;
 *     &lt;enumeration value="261"/&gt;
 *     &lt;enumeration value="262"/&gt;
 *     &lt;enumeration value="263"/&gt;
 *     &lt;enumeration value="264"/&gt;
 *     &lt;enumeration value="265"/&gt;
 *     &lt;enumeration value="266"/&gt;
 *     &lt;enumeration value="267"/&gt;
 *     &lt;enumeration value="268"/&gt;
 *     &lt;enumeration value="269"/&gt;
 *     &lt;enumeration value="272"/&gt;
 *     &lt;enumeration value="273"/&gt;
 *     &lt;enumeration value="274"/&gt;
 *     &lt;enumeration value="275"/&gt;
 *     &lt;enumeration value="276"/&gt;
 *     &lt;enumeration value="277"/&gt;
 *     &lt;enumeration value="280"/&gt;
 *     &lt;enumeration value="281"/&gt;
 *     &lt;enumeration value="282"/&gt;
 *     &lt;enumeration value="283"/&gt;
 *     &lt;enumeration value="284"/&gt;
 *     &lt;enumeration value="290"/&gt;
 *     &lt;enumeration value="291"/&gt;
 *     &lt;enumeration value="292"/&gt;
 *     &lt;enumeration value="300"/&gt;
 *     &lt;enumeration value="301"/&gt;
 *     &lt;enumeration value="321"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ReturnCodeEnum")
@XmlEnum(Integer.class)
public enum ReturnCode {


    /**
     * Success
     * 
     */
    @XmlEnumValue("0")
    SUCCESS(0),

    /**
     * System Error
     * 
     */
    @XmlEnumValue("200")
    SYSTEM_ERROR(200),

    /**
     * Uncategorized Error
     * 
     */
    @XmlEnumValue("201")
    UNCATEGORIZED_ERROR(201),

    /**
     * Resource Error
     * 
     */
    @XmlEnumValue("202")
    RESOURCE_ERROR(202),

    /**
     * Non Existent Snap In
     * 
     */
    @XmlEnumValue("203")
    NON_EXISTENT_SNAP_IN(203),

    /**
     * Insufficient Privileges
     * 
     */
    @XmlEnumValue("204")
    INSUFFICIENT_PRIVILEGES(204),

    /**
     * Invalid Request
     * 
     */
    @XmlEnumValue("205")
    INVALID_REQUEST(205),

    /**
     * Unsupported Operation
     * 
     */
    @XmlEnumValue("206")
    UNSUPPORTED_OPERATION(206),

    /**
     * Invalid Query Syntax
     * 
     */
    @XmlEnumValue("207")
    INVALID_QUERY_SYNTAX(207),

    /**
     * Application Identifier Not Specified
     * 							
     * 
     */
    @XmlEnumValue("208")
    APPLICATION_IDENTIFIER_NOT_SPECIFIED(208),

    /**
     * Application User Not Specified
     * 							
     * 
     */
    @XmlEnumValue("209")
    APPLICATION_USER_NOT_SPECIFIED(209),

    /**
     * Invalid Custom Parameter
     * 
     */
    @XmlEnumValue("210")
    INVALID_CUSTOM_PARAMETER(210),

    /**
     * Existent Transaction Identifier
     * 							
     * 
     */
    @XmlEnumValue("211")
    EXISTENT_TRANSACTION_IDENTIFIER(211),

    /**
     * Non Existent Transaction Identifier
     * 							
     * 
     */
    @XmlEnumValue("212")
    NON_EXISTENT_TRANSACTION_IDENTIFIER(212),

    /**
     * Invalid Candidate List Size
     * 							
     * 
     */
    @XmlEnumValue("213")
    INVALID_CANDIDATE_LIST_SIZE(213),

    /**
     * Invalid Identity
     * 
     */
    @XmlEnumValue("220")
    INVALID_IDENTITY(220),

    /**
     * Invalid Identity Identifier
     * 							
     * 
     */
    @XmlEnumValue("221")
    INVALID_IDENTITY_IDENTIFIER(221),

    /**
     * Non Existent Identity
     * 
     */
    @XmlEnumValue("222")
    NON_EXISTENT_IDENTITY(222),

    /**
     * Existent Identity
     * 
     */
    @XmlEnumValue("223")
    EXISTENT_IDENTITY(223),

    /**
     * Identity Blocked
     * 
     */
    @XmlEnumValue("224")
    IDENTITY_BLOCKED(224),

    /**
     * Identity Incompatible With Domain
     * 							
     * 
     */
    @XmlEnumValue("225")
    IDENTITY_INCOMPATIBLE_WITH_DOMAIN(225),

    /**
     * Invalid Domain Identifier
     * 
     */
    @XmlEnumValue("230")
    INVALID_DOMAIN_IDENTIFIER(230),

    /**
     * Existent Domain
     * 
     */
    @XmlEnumValue("231")
    EXISTENT_DOMAIN(231),

    /**
     * Non Existent Domain
     * 
     */
    @XmlEnumValue("232")
    NON_EXISTENT_DOMAIN(232),

    /**
     * Type Qualifier Not Supported By Domain
     * 							
     * 
     */
    @XmlEnumValue("233")
    TYPE_QUALIFIER_NOT_SUPPORTED_BY_DOMAIN(233),

    /**
     * Invalid Policy
     * 
     */
    @XmlEnumValue("240")
    INVALID_POLICY(240),

    /**
     * Existent Policy
     * 
     */
    @XmlEnumValue("241")
    EXISTENT_POLICY(241),

    /**
     * Non Existent Policy
     * 
     */
    @XmlEnumValue("242")
    NON_EXISTENT_POLICY(242),

    /**
     * Type/Usage Qualifier Combination Not Supported
     * 								By Policy
     * 
     */
    @XmlEnumValue("243")
    TYPE_USAGE_NOT_SUPPORTED_BY_POLICY(243),

    /**
     * Invalid Policy Identifier
     * 
     */
    @XmlEnumValue("244")
    INVALID_POLICY_IDENTIFIER(244),

    /**
     * Policy Incompatible With Domain
     * 							
     * 
     */
    @XmlEnumValue("245")
    POLICY_INCOMPATIBLE_WITH_DOMAIN(245),

    /**
     * Policy Incompatible With Group
     * 							
     * 
     */
    @XmlEnumValue("246")
    POLICY_INCOMPATIBLE_WITH_GROUP(246),

    /**
     * Invalid Policy Type
     * 
     */
    @XmlEnumValue("247")
    INVALID_POLICY_TYPE(247),

    /**
     * Error deleting Policy
     * 
     */
    @XmlEnumValue("248")
    ERROR_DELETING_POLICY(248),

    /**
     * Invalid Group Identifier
     * 
     */
    @XmlEnumValue("250")
    INVALID_GROUP_IDENTIFIER(250),

    /**
     * Existent Group
     * 
     */
    @XmlEnumValue("251")
    EXISTENT_GROUP(251),

    /**
     * Non Existent Group
     * 
     */
    @XmlEnumValue("252")
    NON_EXISTENT_GROUP(252),

    /**
     * Non Existent Group Membership
     * 							
     * 
     */
    @XmlEnumValue("253")
    NON_EXISTENT_GROUP_MEMBERSHIP(253),

    /**
     * Existent User Alias
     * 
     */
    @XmlEnumValue("254")
    EXISTENT_USER_ALIAS(254),

    /**
     * Non Existent User Alias
     * 
     */
    @XmlEnumValue("255")
    NON_EXISTENT_USER_ALIAS(255),

    /**
     * Group Not Specified
     * 
     */
    @XmlEnumValue("256")
    GROUP_NOT_SPECIFIED(256),

    /**
     * Identity Not Registered For Group
     * 							
     * 
     */
    @XmlEnumValue("257")
    IDENTITY_NOT_REGISTERED_FOR_GROUP(257),

    /**
     * Group Blocked
     * 
     */
    @XmlEnumValue("258")
    GROUP_BLOCKED(258),

    /**
     * Group Incompatible With Domain
     * 							
     * 
     */
    @XmlEnumValue("259")
    GROUP_INCOMPATIBLE_WITH_DOMAIN(259),

    /**
     * Invalid Biometric Data
     * 
     */
    @XmlEnumValue("260")
    INVALID_BIOMETRIC_DATA(260),

    /**
     * Existent Biometric Data
     * 
     */
    @XmlEnumValue("261")
    EXISTENT_BIOMETRIC_DATA(261),

    /**
     * Non Existent Biometric Data
     * 							
     * 
     */
    @XmlEnumValue("262")
    NON_EXISTENT_BIOMETRIC_DATA(262),

    /**
     * Incomplete Biometric Data Set
     * 							
     * 
     */
    @XmlEnumValue("263")
    INCOMPLETE_BIOMETRIC_DATA_SET(263),

    /**
     * Unsupported Biometric Data Format
     * 							
     * 
     */
    @XmlEnumValue("264")
    UNSUPPORTED_BIOMETRIC_DATA_FORMAT(264),

    /**
     * Unsupported Usage Qualifier
     * 							
     * 
     */
    @XmlEnumValue("265")
    UNSUPPORTED_USAGE_QUALIFIER(265),

    /**
     * Non Existent Usage Qualifier
     * 							
     * 
     */
    @XmlEnumValue("266")
    NON_EXISTENT_USAGE_QUALIFIER(266),

    /**
     * Unsupported Type Qualifier
     * 
     */
    @XmlEnumValue("267")
    UNSUPPORTED_TYPE_QUALIFIER(267),

    /**
     * Non Existent Type Qualifier
     * 							
     * 
     */
    @XmlEnumValue("268")
    NON_EXISTENT_TYPE_QUALIFIER(268),

    /**
     * Type Qualifier Not Supported For Operation
     * 							
     * 
     */
    @XmlEnumValue("269")
    TYPE_QUALIFIER_NOT_SUPPORTED_FOR_OPERATION(269),

    /**
     * Error transforming biometric data
     * 							
     * 
     */
    @XmlEnumValue("272")
    BIOMETRIC_DATA_TRANSFORM_ERROR(272),

    /**
     * Incompatible Biometric Data Set
     * 							
     * 
     */
    @XmlEnumValue("273")
    INCOMPATIBLE_BIOMETRIC_DATA_SET(273),

    /**
     * Type/Usage Qualifier Combination Not
     * 								Compatible
     * 
     */
    @XmlEnumValue("274")
    INCOMPATIBLE_TYPE_USAGE_QUALIFIERS(274),

    /**
     * Usage Qualifier Not Supported For Operation
     * 							
     * 
     */
    @XmlEnumValue("275")
    USAGE_QUALIFIER_NOT_SUPPORTED_FOR_OPERATION(275),

    /**
     * Error executing transform policy
     * 							
     * 
     */
    @XmlEnumValue("276")
    TRANSFORM_POLICY_EXECUTION_ERROR(276),

    /**
     * Error parsing transform policy
     * 							
     * 
     */
    @XmlEnumValue("277")
    TRANSFORM_POLICY_SYNTAX_ERROR(277),

    /**
     * Invalid Biographic Data
     * 
     */
    @XmlEnumValue("280")
    INVALID_BIOGRAPHIC_DATA(280),

    /**
     * Existent Biographic Data
     * 
     */
    @XmlEnumValue("281")
    EXISTENT_BIOGRAPHIC_DATA(281),

    /**
     * Non Existent Biographic Data
     * 							
     * 
     */
    @XmlEnumValue("282")
    NON_EXISTENT_BIOGRAPHIC_DATA(282),

    /**
     * Biographic Data Element Not Defined
     * 							
     * 
     */
    @XmlEnumValue("283")
    UNDEFINED_BIOGRAPHIC_DATA_ELEMENT(283),

    /**
     * Biographic Data Element Not Supported For
     * 								Operation
     * 
     */
    @XmlEnumValue("284")
    BIOGRAPHIC_DATA_ELEMENT_NOT_SUPPORTED_FOR_OPERATION(284),

    /**
     * Invalid Key Identifier
     * 
     */
    @XmlEnumValue("290")
    INVALID_KEY_IDENTIFIER(290),

    /**
     * Non Existent Key
     * 
     */
    @XmlEnumValue("291")
    NON_EXISTENT_KEY(291),

    /**
     * Invalid Private Information Nonce
     * 							
     * 
     */
    @XmlEnumValue("292")
    INVALID_PRIVATE_INFORMATION_NONCE(292),

    /**
     * Invalid NIST Transaction
     * 
     */
    @XmlEnumValue("300")
    INVALID_NIST_TRANSACTION(300),

    /**
     * Error Processing NIST Transaction
     * 							
     * 
     */
    @XmlEnumValue("301")
    ERROR_PROCESSING_NIST_TRANSACTION(301),

    /**
     * Non Existent Adjudication Record
     * 							
     * 
     */
    @XmlEnumValue("321")
    NON_EXISTENT_ADJUDICATION_RECORD(321);
    private final int value;

    ReturnCode(int v) {
        value = v;
    }

    public int value() {
        return value;
    }

    public static ReturnCode fromValue(int v) {
        for (ReturnCode c: ReturnCode.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
