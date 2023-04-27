# Qaprotours

After starting application you need to grant readonly authority for bucket to anonymous users in Minio client.

This application provides:

1) Resilience4j Circuit Breaker

We use Circuit Breaker to wrap function calls and return default data instead
of throwing exceptions if supplier service is unavailable. This approach helps to avoid falling of request.
After some requests are fallen, the status of breaker is changed and custom handling is used until supplier can't
respond to requests.

You can change Circuit Breaker configuration in ```resilience4j.circuitbreaker``` property.

2) Kafka

We use Kafka here for sending messages to mail client instead of blocking rest approach.

### Arguments from .env file

- ```HOST``` - 'postgres' for PostreSQL
- ```POSTGRES_USERNAME``` - PostgreSQL user
- ```POSTGRES_PASSWORD``` - PostgreSQL password
- ```POSTGRES_DB``` - database and schema name, prefered 'qaprotours'

- ```KAFKA_HOST``` - host with port for Kafka bootstrap server


- ```MINIO_BUCKET``` - bucket name for images, 'tours' default
- ```MINIO_URL``` - preferred url to access Minio client
- ```MINIO_ACCESS_KEY``` - Minio access key
- ```MINIO_SECRET_KEY``` - Minio secret key

To run application you need to install [Helm](https://helm.sh/docs/intro/install/) and run
```console
 sh kafka-setup.sh 
 sh run.sh
 sh istio-setup.sh
 sh runner-setup.sh
 ```

It will ask permission to install ```Istio``` to your cluster, so you need to grant it.
After this command were executed, your cluster will contain required Istio configs and application will be available
through ```localhost```

### Required applications to correct work

1) [User client](https://github.com/ilisau/user-service)
2) [Mail client](https://github.com/ilisau/mail-service)

### CI/CD

This application uses inside-cluster runner and rollout restart deployment `qaprotours`, it needs to be configured
in `runner-setup.sh` and place your [Personal Access Token](https://github.com/settings/tokens) instead of `###YOUR_PERSONAL_ACCESS_TOKEN###`

Jenkins is also included in this setup, so you can access Jenkins from `jenkins.localhost` after executing `minikube tunnel`