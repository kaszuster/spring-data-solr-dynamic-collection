package de.dev86.app;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.convert.SolrConverter;
import org.springframework.data.solr.repository.config.AbstractSolrConfiguration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.SolrClientFactory;
import org.springframework.data.solr.server.support.HttpSolrClientFactory;

@Configuration
@EnableSolrRepositories(solrTemplateRef = "mySolrTemplate", basePackages = "de.dev86.app")
@ComponentScan
public class SolrConfig extends AbstractSolrConfiguration {

    @Value("${spring.data.solr.host}")
    private String solrURL;

    @Value("${spring.data.solr.user}")
    private String solrUser;

    @Value("${spring.data.solr.pass}")
    private String solrPass;

    @Value("${spring.data.solr.collection}")
    private String solrColl;

    @Bean
    public SolrClient solrClient() {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("followRedirects", false);
        params.set("allowCompression", false);
        params.set("httpBasicAuthUser", solrUser);
        params.set("httpBasicAuthPassword", solrPass);
        CloseableHttpClient httpClient = HttpClientUtil.createClient(params);
        return new Builder(solrURL).withHttpClient(httpClient).build();
    }

    @Bean
    public String collectionName() {
        return solrColl;
    }

    @Bean
    @ConditionalOnMissingBean(name = "solrTemplate")
    public SolrTemplate solrTemplate(@Qualifier("mySolrTemplate") SolrTemplate solrTemplate) {
        return solrTemplate;
    }

    @Bean("mySolrTemplate")
    public SolrTemplate mySolrTemplate(SolrClient solrClient, SolrConverter solrConverter) {
        // return new SolrTemplate(new HttpSolrClientFactory(solrClient), solrConverter); // OK
        return new SolrTemplate(new HttpSolrClientFactory(solrClient)); // NOT OK
    }

    @Override
    public SolrClientFactory solrClientFactory() {
        return new HttpSolrClientFactory(solrClient());
    }

}
