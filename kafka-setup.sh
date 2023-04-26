kubectl create namespace kafka
kubectl apply -f https://strimzi.io/install/latest?namespace=kafka -n kafka
kubectl apply -f https://strimzi.io/examples/latest/kafka/kafka-persistent-single.yaml -n kafka

kubectl create sa kafka-serviceaccount
kubectl apply -f src/infra/kafka-clusterrolebinding.yml
