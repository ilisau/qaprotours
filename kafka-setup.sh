kubectl create namespace kafka
kubectl apply -f https://strimzi.io/install/0.34.0?namespace=kafka -n kafka
kubectl apply -f https://strimzi.io/examples/0.34.0/kafka/kafka-persistent-single.yaml -n kafka

kubectl create sa kafka-serviceaccount
kubectl apply -f src/infra/kafka-clusterrolebinding.yml
