cd src/infra || exit
kubectl apply -f qaprotours-service-account.yml

kubectl apply -f minio-secrets.yml
kubectl apply -f pg-secrets.yml
kubectl apply -f qaprotours-configmap.yml
kubectl apply -f grafana-secrets.yml
kubectl apply -f logstash-configmap.yml

kubectl apply -f qaprotours-service.yml
kubectl apply -f minio-service.yml
kubectl apply -f pg-service.yml
kubectl apply -f elastic-service.yml
kubectl apply -f kibana-service.yml
kubectl apply -f grafana-service.yml
kubectl apply -f prometheus-service.yml
kubectl apply -f logstash-service.yml

kubectl apply -f qaprotours-stateful.yml
kubectl apply -f pg-stateful.yml
kubectl apply -f minio-stateful.yml
kubectl apply -f elastic-stateful.yml
kubectl apply -f kibana-deployment.yml
kubectl apply -f grafana-stateful.yml
kubectl apply -f prometheus-stateful.yml
kubectl apply -f logstash-stateful.yml