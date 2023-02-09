

docker run --rm -v C:/Users/miguel.mino/.kube:/root/.kube:ro  -v C:/Users/miguel.mino/Documents/GitHub/BMC322-BIOEN-MS-Enrolamiento:/root -ti line/kubectl-kustomize:latest

mvn clean install
docker build -t enrolamiento:2.14 .
kubectl delete deployment enrolamiento
kubectl apply -f k8s/configmap.yml
kubectl apply -f k8s/deployment.yml
kubectl get pod
kubectl logs enrolamiento-55c596478-jrb88

kubectl logs "$(kubectl get pods | grep enrolamiento | awk '{print $1}')"


https://github.com/kubernetes-sigs/kustomize/tree/master/examples/springboot


https://githubmacro.corp.globant.com/BancoMacro/BMC322-BIO-ConfigurationServer/trunk/development/conf/development/properties

