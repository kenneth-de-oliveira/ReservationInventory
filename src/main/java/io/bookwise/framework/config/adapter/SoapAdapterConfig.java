package io.bookwise.framework.config.adapter;

import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jaxb.JAXBContextFactory;
import feign.soap.SOAPDecoder;
import feign.soap.SOAPEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up SOAP adapter.
 */
@Configuration
public class SoapAdapterConfig {

    /**
     * Singleton instance of JAXBContextFactory configured with UTF-8 encoding.
     */
    private static final JAXBContextFactory JAXB_CONTEXT_FACTORY = new JAXBContextFactory.Builder()
            .withMarshallerJAXBEncoding("UTF-8")
            .build();

    /**
     * Provides a SOAP encoder bean.
     *
     * @return a SOAPEncoder instance
     */
    @Bean
    public Encoder soapEncoder() {
        return new SOAPEncoder(JAXB_CONTEXT_FACTORY);
    }

    /**
     * Provides a SOAP decoder bean.
     *
     * @return a SOAPDecoder instance
     */
    @Bean
    public Decoder soapDecoder() {
        return new SOAPDecoder(JAXB_CONTEXT_FACTORY);
    }

    /**
     * Provides a request interceptor to set the Content-Type header to text/xml.
     *
     * @return a RequestInterceptor instance
     */
    @Bean
    public RequestInterceptor contentTypeRequestInterceptor() {
        return template -> template.header("Content-Type", "text/xml;charset=utf-8");
    }

}