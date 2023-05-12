package com.solvd.qaprotours.web;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.solvd.qaprotours.service.property.ElasticsearchProperties;
import com.solvd.qaprotours.service.property.MinioProperties;
import io.minio.MinioClient;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.time.format.DateTimeFormatter;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final ElasticsearchProperties elasticsearchProperties;

    private final MinioProperties minioProperties;

    /**
     * Create a password encoder.
     *
     * @return password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Create a minio client.
     * @return minio client
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(),
                        minioProperties.getSecretKey())
                .build();
    }

    /**
     * Create a swagger open api.
     * @return open api
     */
    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().addList(securitySchemeName)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("QaproTours API")
                        .description("Tour service")
                        .version("v1"));
    }

    /**
     * Create a web client builder.
     * @return web client builder
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    /**
     * Create a xml object for producer.
     *
     * @return xml
     */
    @SneakyThrows
    @Bean(name = "producer")
    public XML producerXml() {
        return new XMLDocument(
                new File("src/main/resources/kafka/producer.xml")
        );
    }

    /**
     * Create a xml object for consumer.
     *
     * @return xml
     */
    @SneakyThrows
    @Bean(name = "consumer")
    public XML consumerXml() {
        return new XMLDocument(
                new File("src/main/resources/kafka/consumer.xml")
        );
    }

    /**
     * Creates Elasticsearch client.
     *
     * @return client
     */
    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = RestClient.builder(
                        new HttpHost(elasticsearchProperties.getHost(),
                                elasticsearchProperties.getPort()))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    }

}
