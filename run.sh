cd src/infra || exit
kubectl apply -f minio-secrets.yml
kubectl apply -f pg-secrets.yml
kubectl apply -f qaprotours-configmap.yml
kubectl apply -f kafka-configmap.yml
kubectl apply -f zookeeper-configmap.yml

kubectl apply -f qaprotours-service.yml
kubectl apply -f minio-service.yml
kubectl apply -f pg-service.yml
kubectl apply -f kafka-service.yml
kubectl apply -f zookeeper-service.yml

kubectl apply -f qaprotours-deployment.yml
kubectl apply -f pg-stateful.yml
kubectl apply -f minio-stateful.yml
kubectl apply -f kafka-deployment.yml
kubectl apply -f zookeeper-stateful.yml

istioctl install

kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.17/samples/addons/prometheus.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.17/samples/addons/grafana.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.17/samples/addons/kiali.yaml