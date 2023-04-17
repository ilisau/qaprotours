kubectl label namespace default istio-injection=enabled
istioctl install

kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.17/samples/addons/prometheus.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.17/samples/addons/grafana.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.17/samples/addons/kiali.yaml

minikube addons enable ingress
kubectl apply -f src/infra/ingress.yaml
kubectl apply -f src/infra/jenkins-ingress.yaml
kubectl apply -f src/infra/istio-security.yml

minikube tunnel