# Qaprotours
After starting application you need to grant readonly authority for bucket to anonymous users in Minio client.

This application provides:
- Eureka Service Registry with [Eureka server](https://github.com/ilisau/eureka-server)
- Resilience4j Circuit Breaker

### Arguments from .env file
- HOST - 'postgres' for PostreSQL
- POSTGRES_USERNAME - PostgreSQL user
- POSTGRES_PASSWORD - PostgreSQL password
- POSTGRES_DB - database and schema name, prefered 'qaprotours'


- SPRING_MAIL_HOST - mail host, 'smtp.gmail.com' for example
- SPRING_MAIL_PORT - mail port, '587' for example
- SPRING_MAIL_USERNAME - mail address
- SPRING_MAIL_PASSWORD - generated password, not actual account password


- MINIO_BUCKET - bucket name for images, 'tours' default
- MINIO_URL - preferred url to access Minio client
- MINIO_ACCESS_KEY - Minio access key
- MINIO_SECRET_KEY - Minio secret key