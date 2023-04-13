kubectl label namespace default istio-injection=enabled
istioctl install

minikube addons enable ingress
kubectl apply -f src/infra/ingress.yaml