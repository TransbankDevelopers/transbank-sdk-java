
package com.transbank.webpayserver.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.transbank.webpayserver.webservices package. 
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

    private final static QName _Authorize_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "authorize");
    private final static QName _AuthorizeResponse_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "authorizeResponse");
    private final static QName _CodeReverseOneClick_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "codeReverseOneClick");
    private final static QName _CodeReverseOneClickResponse_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "codeReverseOneClickResponse");
    private final static QName _FinishInscription_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "finishInscription");
    private final static QName _FinishInscriptionResponse_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "finishInscriptionResponse");
    private final static QName _InitInscription_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "initInscription");
    private final static QName _InitInscriptionResponse_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "initInscriptionResponse");
    private final static QName _RemoveUser_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "removeUser");
    private final static QName _RemoveUserResponse_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "removeUserResponse");
    private final static QName _Reverse_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "reverse");
    private final static QName _ReverseResponse_QNAME = new QName("http://webservices.webpayserver.transbank.com/", "reverseResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.transbank.webpayserver.webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Authorize }
     * 
     */
    public Authorize createAuthorize() {
        return new Authorize();
    }

    /**
     * Create an instance of {@link AuthorizeResponse }
     * 
     */
    public AuthorizeResponse createAuthorizeResponse() {
        return new AuthorizeResponse();
    }

    /**
     * Create an instance of {@link CodeReverseOneClick }
     * 
     */
    public CodeReverseOneClick createCodeReverseOneClick() {
        return new CodeReverseOneClick();
    }

    /**
     * Create an instance of {@link CodeReverseOneClickResponse }
     * 
     */
    public CodeReverseOneClickResponse createCodeReverseOneClickResponse() {
        return new CodeReverseOneClickResponse();
    }

    /**
     * Create an instance of {@link FinishInscription }
     * 
     */
    public FinishInscription createFinishInscription() {
        return new FinishInscription();
    }

    /**
     * Create an instance of {@link FinishInscriptionResponse }
     * 
     */
    public FinishInscriptionResponse createFinishInscriptionResponse() {
        return new FinishInscriptionResponse();
    }

    /**
     * Create an instance of {@link InitInscription }
     * 
     */
    public InitInscription createInitInscription() {
        return new InitInscription();
    }

    /**
     * Create an instance of {@link InitInscriptionResponse }
     * 
     */
    public InitInscriptionResponse createInitInscriptionResponse() {
        return new InitInscriptionResponse();
    }

    /**
     * Create an instance of {@link RemoveUser }
     * 
     */
    public RemoveUser createRemoveUser() {
        return new RemoveUser();
    }

    /**
     * Create an instance of {@link RemoveUserResponse }
     * 
     */
    public RemoveUserResponse createRemoveUserResponse() {
        return new RemoveUserResponse();
    }

    /**
     * Create an instance of {@link Reverse }
     * 
     */
    public Reverse createReverse() {
        return new Reverse();
    }

    /**
     * Create an instance of {@link ReverseResponse }
     * 
     */
    public ReverseResponse createReverseResponse() {
        return new ReverseResponse();
    }

    /**
     * Create an instance of {@link OneClickRemoveUserInput }
     * 
     */
    public OneClickRemoveUserInput createOneClickRemoveUserInput() {
        return new OneClickRemoveUserInput();
    }

    /**
     * Create an instance of {@link BaseBean }
     * 
     */
    public BaseBean createBaseBean() {
        return new BaseBean();
    }

    /**
     * Create an instance of {@link OneClickInscriptionInput }
     * 
     */
    public OneClickInscriptionInput createOneClickInscriptionInput() {
        return new OneClickInscriptionInput();
    }

    /**
     * Create an instance of {@link OneClickInscriptionOutput }
     * 
     */
    public OneClickInscriptionOutput createOneClickInscriptionOutput() {
        return new OneClickInscriptionOutput();
    }

    /**
     * Create an instance of {@link OneClickFinishInscriptionInput }
     * 
     */
    public OneClickFinishInscriptionInput createOneClickFinishInscriptionInput() {
        return new OneClickFinishInscriptionInput();
    }

    /**
     * Create an instance of {@link OneClickFinishInscriptionOutput }
     * 
     */
    public OneClickFinishInscriptionOutput createOneClickFinishInscriptionOutput() {
        return new OneClickFinishInscriptionOutput();
    }

    /**
     * Create an instance of {@link OneClickReverseInput }
     * 
     */
    public OneClickReverseInput createOneClickReverseInput() {
        return new OneClickReverseInput();
    }

    /**
     * Create an instance of {@link OneClickReverseOutput }
     * 
     */
    public OneClickReverseOutput createOneClickReverseOutput() {
        return new OneClickReverseOutput();
    }

    /**
     * Create an instance of {@link OneClickPayInput }
     * 
     */
    public OneClickPayInput createOneClickPayInput() {
        return new OneClickPayInput();
    }

    /**
     * Create an instance of {@link OneClickPayOutput }
     * 
     */
    public OneClickPayOutput createOneClickPayOutput() {
        return new OneClickPayOutput();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Authorize }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "authorize")
    public JAXBElement<Authorize> createAuthorize(Authorize value) {
        return new JAXBElement<Authorize>(_Authorize_QNAME, Authorize.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorizeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "authorizeResponse")
    public JAXBElement<AuthorizeResponse> createAuthorizeResponse(AuthorizeResponse value) {
        return new JAXBElement<AuthorizeResponse>(_AuthorizeResponse_QNAME, AuthorizeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReverseOneClick }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "codeReverseOneClick")
    public JAXBElement<CodeReverseOneClick> createCodeReverseOneClick(CodeReverseOneClick value) {
        return new JAXBElement<CodeReverseOneClick>(_CodeReverseOneClick_QNAME, CodeReverseOneClick.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReverseOneClickResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "codeReverseOneClickResponse")
    public JAXBElement<CodeReverseOneClickResponse> createCodeReverseOneClickResponse(CodeReverseOneClickResponse value) {
        return new JAXBElement<CodeReverseOneClickResponse>(_CodeReverseOneClickResponse_QNAME, CodeReverseOneClickResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FinishInscription }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "finishInscription")
    public JAXBElement<FinishInscription> createFinishInscription(FinishInscription value) {
        return new JAXBElement<FinishInscription>(_FinishInscription_QNAME, FinishInscription.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FinishInscriptionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "finishInscriptionResponse")
    public JAXBElement<FinishInscriptionResponse> createFinishInscriptionResponse(FinishInscriptionResponse value) {
        return new JAXBElement<FinishInscriptionResponse>(_FinishInscriptionResponse_QNAME, FinishInscriptionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitInscription }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "initInscription")
    public JAXBElement<InitInscription> createInitInscription(InitInscription value) {
        return new JAXBElement<InitInscription>(_InitInscription_QNAME, InitInscription.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitInscriptionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "initInscriptionResponse")
    public JAXBElement<InitInscriptionResponse> createInitInscriptionResponse(InitInscriptionResponse value) {
        return new JAXBElement<InitInscriptionResponse>(_InitInscriptionResponse_QNAME, InitInscriptionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "removeUser")
    public JAXBElement<RemoveUser> createRemoveUser(RemoveUser value) {
        return new JAXBElement<RemoveUser>(_RemoveUser_QNAME, RemoveUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "removeUserResponse")
    public JAXBElement<RemoveUserResponse> createRemoveUserResponse(RemoveUserResponse value) {
        return new JAXBElement<RemoveUserResponse>(_RemoveUserResponse_QNAME, RemoveUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reverse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "reverse")
    public JAXBElement<Reverse> createReverse(Reverse value) {
        return new JAXBElement<Reverse>(_Reverse_QNAME, Reverse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReverseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.webpayserver.transbank.com/", name = "reverseResponse")
    public JAXBElement<ReverseResponse> createReverseResponse(ReverseResponse value) {
        return new JAXBElement<ReverseResponse>(_ReverseResponse_QNAME, ReverseResponse.class, null, value);
    }

}
