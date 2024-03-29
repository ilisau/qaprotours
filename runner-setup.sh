kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.11.0/cert-manager.yaml

export GITHUB_TOKEN=###YOUR_PERSONAL_ACCESS_TOKEN###
kubectl create ns actions-runner-system
kubectl apply -f src/infra/runner-serviceaccount.yml
kubectl apply -f src/infra/runner-clusterrolebinding.yml
kubectl apply -f src/infra/runner-deployment.yml
kubectl create secret generic controller-manager -n actions-runner-system --from-literal=github_token=${GITHUB_TOKEN}

helm repo add actions-runner-controller https://actions-runner-controller.github.io/actions-runner-controller
helm repo update
helm upgrade --install \
--namespace actions-runner-system \
--create-namespace \
--wait actions-runner-controller \
actions-runner-controller/actions-runner-controller \
--set syncPeriod=1m
/
