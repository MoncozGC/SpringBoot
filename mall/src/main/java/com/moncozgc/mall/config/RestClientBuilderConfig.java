package com.moncozgc.mall.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class RestClientBuilderConfig {
    private List<String> urls;
    private String username;
    private String password;
    private Duration connectionTimeout = Duration.ofSeconds(1);
    private Duration socketTimeout = Duration.ofSeconds(30);
    private int maxConnTotal = 300;
    private int maxConnPerRoute = 100;

    @Bean
    public RestClientBuilder restClientBuilder() {
        if (getUrls() == null || getUrls().isEmpty()) {
            throw new IllegalArgumentException("Empty elasticsearch hostList.");
        }

        HttpHost[] hosts = getUrls().stream().map(this::createHttpHost).toArray(HttpHost[]::new);

        RestClientBuilder builder = RestClient.builder(hosts);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(getUsername(), getPassword()));

        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setKeepAliveStrategy((httpResponse, httpContext) -> TimeUnit.MINUTES.toMillis(3));
            httpClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setSoKeepAlive(true).build());
            httpClientBuilder.setMaxConnTotal(maxConnTotal);
            httpClientBuilder.setMaxConnPerRoute(maxConnPerRoute);
            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        });

        return builder;
    }

    private HttpHost createHttpHost(String uri) {
        try {
            return createHttpHost(URI.create(uri));
        } catch (IllegalArgumentException ex) {
            return HttpHost.create(uri);
        }
    }

    private HttpHost createHttpHost(URI uri) {
        if (!StringUtils.hasLength(uri.getUserInfo())) {
            return HttpHost.create(uri.toString());
        }
        try {
            return HttpHost.create(new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), uri.getPath(),
                    uri.getQuery(), uri.getFragment()).toString());
        } catch (URISyntaxException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
