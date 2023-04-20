cd src/infra || exit
kubectl create namespace devops-tools

kubectl apply -f jenkins-service-account.yml
kubectl apply -f jenkins-volume.yml
kubectl apply -f jenkins-deployment.yml
kubectl apply -f docker-deployment.yml
kubectl apply -f docker-service.yml
kubectl apply -f jenkins-service.yml
kubectl apply -f jenkins-ingress.yaml