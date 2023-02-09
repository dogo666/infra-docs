//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.14 at 09:18:33 PM ART 
//


package ar.com.macro.validacion.model.client.daonengine.identification.dto.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Result for Verification performed with supplied BiometricData, against reference
 *                     BiometricData held by the system for the ClaimedIdentity.
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.daon.com/ws/de}AbstractDaonEngineResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ResponseData" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CandidateResults" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ReferenceIdentityIdentifier" type="{http://www.daon.com/ws/de}IdentityIdentifier" minOccurs="0"/&gt;
 *                             &lt;choice&gt;
 *                               &lt;element name="CandidateMatchFailure"&gt;
 *                                 &lt;complexType&gt;
 *                                   &lt;complexContent&gt;
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                       &lt;sequence&gt;
 *                                         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                                         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;/sequence&gt;
 *                                     &lt;/restriction&gt;
 *                                   &lt;/complexContent&gt;
 *                                 &lt;/complexType&gt;
 *                               &lt;/element&gt;
 *                               &lt;element name="CandidateResult"&gt;
 *                                 &lt;complexType&gt;
 *                                   &lt;complexContent&gt;
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                       &lt;sequence&gt;
 *                                         &lt;element name="VerifyResult" type="{http://www.daon.com/ws/de}VerifyResult"/&gt;
 *                                         &lt;element name="VerifyScore" minOccurs="0"&gt;
 *                                           &lt;complexType&gt;
 *                                             &lt;complexContent&gt;
 *                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                 &lt;sequence&gt;
 *                                                   &lt;element ref="{http://www.daon.com/ws/de}AbstractScore" maxOccurs="unbounded"/&gt;
 *                                                 &lt;/sequence&gt;
 *                                               &lt;/restriction&gt;
 *                                             &lt;/complexContent&gt;
 *                                           &lt;/complexType&gt;
 *                                         &lt;/element&gt;
 *                                         &lt;element name="SampleScore" type="{http://www.daon.com/ws/de}SampleScore" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                       &lt;/sequence&gt;
 *                                     &lt;/restriction&gt;
 *                                   &lt;/complexContent&gt;
 *                                 &lt;/complexType&gt;
 *                               &lt;/element&gt;
 *                             &lt;/choice&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
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
    "responseData"
})
@XmlRootElement(name = "VerifiesResponse")
public class VerifiesResponse
    extends AbstractDaonEngineResponse
{

    @XmlElement(name = "ResponseData")
    protected VerifiesResponse.ResponseData responseData;

    /**
     * Gets the value of the responseData property.
     * 
     * @return
     *     possible object is
     *     {@link VerifiesResponse.ResponseData }
     *     
     */
    public VerifiesResponse.ResponseData getResponseData() {
        return responseData;
    }

    /**
     * Sets the value of the responseData property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerifiesResponse.ResponseData }
     *     
     */
    public void setResponseData(VerifiesResponse.ResponseData value) {
        this.responseData = value;
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
     *         &lt;element name="CandidateResults" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ReferenceIdentityIdentifier" type="{http://www.daon.com/ws/de}IdentityIdentifier" minOccurs="0"/&gt;
     *                   &lt;choice&gt;
     *                     &lt;element name="CandidateMatchFailure"&gt;
     *                       &lt;complexType&gt;
     *                         &lt;complexContent&gt;
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                             &lt;sequence&gt;
     *                               &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                               &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;/sequence&gt;
     *                           &lt;/restriction&gt;
     *                         &lt;/complexContent&gt;
     *                       &lt;/complexType&gt;
     *                     &lt;/element&gt;
     *                     &lt;element name="CandidateResult"&gt;
     *                       &lt;complexType&gt;
     *                         &lt;complexContent&gt;
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                             &lt;sequence&gt;
     *                               &lt;element name="VerifyResult" type="{http://www.daon.com/ws/de}VerifyResult"/&gt;
     *                               &lt;element name="VerifyScore" minOccurs="0"&gt;
     *                                 &lt;complexType&gt;
     *                                   &lt;complexContent&gt;
     *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                       &lt;sequence&gt;
     *                                         &lt;element ref="{http://www.daon.com/ws/de}AbstractScore" maxOccurs="unbounded"/&gt;
     *                                       &lt;/sequence&gt;
     *                                     &lt;/restriction&gt;
     *                                   &lt;/complexContent&gt;
     *                                 &lt;/complexType&gt;
     *                               &lt;/element&gt;
     *                               &lt;element name="SampleScore" type="{http://www.daon.com/ws/de}SampleScore" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                             &lt;/sequence&gt;
     *                           &lt;/restriction&gt;
     *                         &lt;/complexContent&gt;
     *                       &lt;/complexType&gt;
     *                     &lt;/element&gt;
     *                   &lt;/choice&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "candidateResults"
    })
    public static class ResponseData {

        @XmlElement(name = "CandidateResults", required = true)
        protected List<VerifiesResponse.ResponseData.CandidateResults> candidateResults;

        /**
         * Gets the value of the candidateResults property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the candidateResults property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCandidateResults().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VerifiesResponse.ResponseData.CandidateResults }
         * 
         * 
         */
        public List<VerifiesResponse.ResponseData.CandidateResults> getCandidateResults() {
            if (candidateResults == null) {
                candidateResults = new ArrayList<VerifiesResponse.ResponseData.CandidateResults>();
            }
            return this.candidateResults;
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
         *         &lt;element name="ReferenceIdentityIdentifier" type="{http://www.daon.com/ws/de}IdentityIdentifier" minOccurs="0"/&gt;
         *         &lt;choice&gt;
         *           &lt;element name="CandidateMatchFailure"&gt;
         *             &lt;complexType&gt;
         *               &lt;complexContent&gt;
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                   &lt;sequence&gt;
         *                     &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *                     &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;/sequence&gt;
         *                 &lt;/restriction&gt;
         *               &lt;/complexContent&gt;
         *             &lt;/complexType&gt;
         *           &lt;/element&gt;
         *           &lt;element name="CandidateResult"&gt;
         *             &lt;complexType&gt;
         *               &lt;complexContent&gt;
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                   &lt;sequence&gt;
         *                     &lt;element name="VerifyResult" type="{http://www.daon.com/ws/de}VerifyResult"/&gt;
         *                     &lt;element name="VerifyScore" minOccurs="0"&gt;
         *                       &lt;complexType&gt;
         *                         &lt;complexContent&gt;
         *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                             &lt;sequence&gt;
         *                               &lt;element ref="{http://www.daon.com/ws/de}AbstractScore" maxOccurs="unbounded"/&gt;
         *                             &lt;/sequence&gt;
         *                           &lt;/restriction&gt;
         *                         &lt;/complexContent&gt;
         *                       &lt;/complexType&gt;
         *                     &lt;/element&gt;
         *                     &lt;element name="SampleScore" type="{http://www.daon.com/ws/de}SampleScore" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                   &lt;/sequence&gt;
         *                 &lt;/restriction&gt;
         *               &lt;/complexContent&gt;
         *             &lt;/complexType&gt;
         *           &lt;/element&gt;
         *         &lt;/choice&gt;
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
            "referenceIdentityIdentifier",
            "candidateMatchFailure",
            "candidateResult"
        })
        public static class CandidateResults {

            @XmlElement(name = "ReferenceIdentityIdentifier")
            protected IdentityIdentifier referenceIdentityIdentifier;
            @XmlElement(name = "CandidateMatchFailure")
            protected VerifiesResponse.ResponseData.CandidateResults.CandidateMatchFailure candidateMatchFailure;
            @XmlElement(name = "CandidateResult")
            protected VerifiesResponse.ResponseData.CandidateResults.CandidateResult candidateResult;

            /**
             * Gets the value of the referenceIdentityIdentifier property.
             * 
             * @return
             *     possible object is
             *     {@link IdentityIdentifier }
             *     
             */
            public IdentityIdentifier getReferenceIdentityIdentifier() {
                return referenceIdentityIdentifier;
            }

            /**
             * Sets the value of the referenceIdentityIdentifier property.
             * 
             * @param value
             *     allowed object is
             *     {@link IdentityIdentifier }
             *     
             */
            public void setReferenceIdentityIdentifier(IdentityIdentifier value) {
                this.referenceIdentityIdentifier = value;
            }

            /**
             * Gets the value of the candidateMatchFailure property.
             * 
             * @return
             *     possible object is
             *     {@link VerifiesResponse.ResponseData.CandidateResults.CandidateMatchFailure }
             *     
             */
            public VerifiesResponse.ResponseData.CandidateResults.CandidateMatchFailure getCandidateMatchFailure() {
                return candidateMatchFailure;
            }

            /**
             * Sets the value of the candidateMatchFailure property.
             * 
             * @param value
             *     allowed object is
             *     {@link VerifiesResponse.ResponseData.CandidateResults.CandidateMatchFailure }
             *     
             */
            public void setCandidateMatchFailure(VerifiesResponse.ResponseData.CandidateResults.CandidateMatchFailure value) {
                this.candidateMatchFailure = value;
            }

            /**
             * Gets the value of the candidateResult property.
             * 
             * @return
             *     possible object is
             *     {@link VerifiesResponse.ResponseData.CandidateResults.CandidateResult }
             *     
             */
            public VerifiesResponse.ResponseData.CandidateResults.CandidateResult getCandidateResult() {
                return candidateResult;
            }

            /**
             * Sets the value of the candidateResult property.
             * 
             * @param value
             *     allowed object is
             *     {@link VerifiesResponse.ResponseData.CandidateResults.CandidateResult }
             *     
             */
            public void setCandidateResult(VerifiesResponse.ResponseData.CandidateResults.CandidateResult value) {
                this.candidateResult = value;
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
             *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
             *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "errorCode",
                "errorMessage"
            })
            public static class CandidateMatchFailure {

                @XmlElement(name = "ErrorCode")
                protected int errorCode;
                @XmlElement(name = "ErrorMessage", required = true)
                protected String errorMessage;

                /**
                 * Gets the value of the errorCode property.
                 * 
                 */
                public int getErrorCode() {
                    return errorCode;
                }

                /**
                 * Sets the value of the errorCode property.
                 * 
                 */
                public void setErrorCode(int value) {
                    this.errorCode = value;
                }

                /**
                 * Gets the value of the errorMessage property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getErrorMessage() {
                    return errorMessage;
                }

                /**
                 * Sets the value of the errorMessage property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setErrorMessage(String value) {
                    this.errorMessage = value;
                }

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
             *         &lt;element name="VerifyResult" type="{http://www.daon.com/ws/de}VerifyResult"/&gt;
             *         &lt;element name="VerifyScore" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element ref="{http://www.daon.com/ws/de}AbstractScore" maxOccurs="unbounded"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="SampleScore" type="{http://www.daon.com/ws/de}SampleScore" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                "verifyResult",
                "verifyScore",
                "sampleScore"
            })
            public static class CandidateResult {

                @XmlElement(name = "VerifyResult", required = true)
                @XmlSchemaType(name = "string")
                protected VerifyResult verifyResult;
                @XmlElement(name = "VerifyScore")
                protected VerifiesResponse.ResponseData.CandidateResults.CandidateResult.VerifyScore verifyScore;
                @XmlElement(name = "SampleScore")
                protected List<SampleScore> sampleScore;

                /**
                 * Gets the value of the verifyResult property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link VerifyResult }
                 *     
                 */
                public VerifyResult getVerifyResult() {
                    return verifyResult;
                }

                /**
                 * Sets the value of the verifyResult property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link VerifyResult }
                 *     
                 */
                public void setVerifyResult(VerifyResult value) {
                    this.verifyResult = value;
                }

                /**
                 * Gets the value of the verifyScore property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link VerifiesResponse.ResponseData.CandidateResults.CandidateResult.VerifyScore }
                 *     
                 */
                public VerifiesResponse.ResponseData.CandidateResults.CandidateResult.VerifyScore getVerifyScore() {
                    return verifyScore;
                }

                /**
                 * Sets the value of the verifyScore property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link VerifiesResponse.ResponseData.CandidateResults.CandidateResult.VerifyScore }
                 *     
                 */
                public void setVerifyScore(VerifiesResponse.ResponseData.CandidateResults.CandidateResult.VerifyScore value) {
                    this.verifyScore = value;
                }

                /**
                 * Gets the value of the sampleScore property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the sampleScore property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getSampleScore().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link SampleScore }
                 * 
                 * 
                 */
                public List<SampleScore> getSampleScore() {
                    if (sampleScore == null) {
                        sampleScore = new ArrayList<SampleScore>();
                    }
                    return this.sampleScore;
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
                 *         &lt;element ref="{http://www.daon.com/ws/de}AbstractScore" maxOccurs="unbounded"/&gt;
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
                    "abstractScore"
                })
                public static class VerifyScore {

                    @XmlElement(name = "AbstractScore", required = true)
                    protected List<AbstractScore> abstractScore;

                    /**
                     * One or more Scores denoting the overall result
                     *                                                                                         for the verification as determined by the matcher.Gets the value of the abstractScore property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the abstractScore property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getAbstractScore().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link AbstractScore }
                     * 
                     * 
                     */
                    public List<AbstractScore> getAbstractScore() {
                        if (abstractScore == null) {
                            abstractScore = new ArrayList<AbstractScore>();
                        }
                        return this.abstractScore;
                    }

                }

            }

        }

    }

}