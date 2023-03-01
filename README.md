# Qaprotours

After starting application you need to grant readonly authority for bucket to anonymous users in Minio client.

This application provides:

1) Eureka Service Registry with [Eureka server](https://github.com/ilisau/eureka-server)

We use Service Registry to avoid direct URLs of services via passing requests
to [Eureka server](https://github.com/ilisau/eureka-server) instead.
Services register themselves on server, so server knows addresses of all services.
When we need to call any service we need only know service name, and server
passes request to services itself, so we don't need to know their addresses.

You can change server address in ```eureka.client.server-url.defaultZone``` property.

2) Resilience4j Circuit Breaker

We use Circuit Breaker to wrap function calls and return default data instead
of throwing exceptions if supplier service is unavailable. This approach helps to avoid falling of request.
After some requests are fallen, the status of breaker is changed and custom handling is used until supplier can't
respond to requests.

You can change Circuit Breaker configuration in ```resilience4j.circuitbreaker``` property.

### Arguments from .env file

- ```HOST``` - 'postgres' for PostreSQL
- ```POSTGRES_USERNAME``` - PostgreSQL user
- ```POSTGRES_PASSWORD``` - PostgreSQL password
- ```POSTGRES_DB``` - database and schema name, prefered 'qaprotours'


- ```SPRING_MAIL_HOST``` - mail host, 'smtp.gmail.com' for example
- ```SPRING_MAIL_PORT``` - mail port, '587' for example
- ```SPRING_MAIL_USERNAME``` - mail address
- ```SPRING_MAIL_PASSWORD``` - generated password, not actual account password


- ```MINIO_BUCKET``` - bucket name for images, 'tours' default
- ```MINIO_URL``` - preferred url to access Minio client
- ```MINIO_ACCESS_KEY``` - Minio access key
- ```MINIO_SECRET_KEY``` - Minio secret key