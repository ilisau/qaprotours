cd src/infra || exit
kubectl apply -f minio-secrets.yml
kubectl apply -f pg-secrets.yml
kubectl apply -f qaprotours-secrets.yml
kubectl apply -f qaprotours-configmap.yml

kubectl apply -f qaprotours-service.yml
kubectl apply -f minio-service.yml
kubectl apply -f pg-service.yml

kubectl apply -f qaprotours-deployment.yml
kubectl apply -f pg-stateful.yml
kubectl apply -f minio-stateful.yml