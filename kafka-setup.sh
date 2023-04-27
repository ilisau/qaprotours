kubectl create namespace kafka
helm repo add strimzi https://strimzi.io/charts/
helm install strimzi-kafka strimzi/strimzi-kafka-operator --version 0.34.0 --namespace kafka
kubectl apply -f src/infra/kafka-persistent.yml -n kafka

kubectl create sa kafka-serviceaccount
kubectl apply -f src/infra/kafka-clusterrolebinding.yml