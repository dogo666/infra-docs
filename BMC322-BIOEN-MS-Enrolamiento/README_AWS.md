
## Instalar

- aws cli
- kubectl

## En Consola AWS

Creo cueanta en aws

Creo el cluster desde la consola

Tengo que generar un Rol nuevo en iam

## Configure 

aws configure

aws eks --region us-east-1 update-kubeconfig --name cluster-poc-kubernetes

aws eks --region us-east-1 update-kubeconfig --name eks-cluster-sg-my-cluster

## Configuro kubectl
kubectl get pods --kubeconfig ./.kube/config

kubectl create serviceaccount spark
kubectl create clusterrolebinding spark-role --clusterrole=edit --serviceaccount=default:spark --namespace=default

## Preparar Register docker login



aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 426994308472.dkr.ecr.us-east-1.amazonaws.com
docker build -t biometria/enrolamiento .
docker tag biometria/enrolamiento:latest 426994308472.dkr.ecr.us-east-1.amazonaws.com/biometria/enrolamiento:0.0.2
docker push 426994308472.dkr.ecr.us-east-1.amazonaws.com/biometria/enrolamiento:0.0.2

## Referencia para automatizar deploy

https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2-docker.html

## Conectar la Base de datos

https://dev.to/bensooraj/accessing-amazon-rds-from-aws-eks-2pc3


https://aws.amazon.com/es/getting-started/hands-on/deploy-kubernetes-app-amazon-eks/

Crear el cluster
eksctl create cluster --name my-cluster --region us-east-1 --fargate


## Docker imagen 
aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 847516806498.dkr.ecr.us-west-2.amazonaws.com
docker build -t enrolamiento .

docker build -t webapp .

docker tag webapp:latest 847516806498.dkr.ecr.us-west-2.amazonaws.com/webapp:latest
docker push 847516806498.dkr.ecr.us-west-2.amazonaws.com/webapp:latest

## Create clusterrolebinding rolebinding

kubectl create clusterrolebinding service-reader-pod --clusterrole=service-reader  --serviceaccount=default:default

kubectl create rolebinding default:service-discovery-client --clusterrole service-discovery-client --serviceaccount default:default


## Instalacion Mysql

docker tag mysql:5.6 426994308472.dkr.ecr.us-east-1.amazonaws.com/biometria/mysql:latest

docker push 426994308472.dkr.ecr.us-east-1.amazonaws.com/biometria/mysql:latest


Prueba DB
kubectl run -i --tty --rm debug --image=busybox --restart=Never -- sh
If you don't see a command prompt, try pressing enter.
/ # nc mysql-service 3306
^Cpunt!


## Restart POD
kubectl rollout restart deployment enrolamiento

## Escalar

kubectl scale deployment mydeploy --replicas=0

## Fix  User "system:serviceaccount:default:default" cannot list resource "endpoints" in API group "" at the cluster scope.

kubectl create -f k8s/lwdc-clusterrole.yaml
kubectl create -f k8s/lwdc-rolebinding.yaml
kubectl auth can-i list endpoints --all-namespaces --as system:serviceaccount:default:default

ref https://www.ibm.com/docs/en/cloud-app-management/2019.4.0?topic=topics-authorizing-data-collector-access-kubernetes-resources


##Load balancer 
https://docs.aws.amazon.com/eks/latest/userguide/aws-load-balancer-controller.html

aws iam create-policy --policy-name AWSLoadBalancerControllerIAMPolicy --policy-document file://iam_policy.json

eksctl create iamserviceaccount \
--cluster=my-cluster \
--namespace=kube-system \
--name=aws-load-balancer-controller \
--attach-policy-arn=arn:aws:iam::426994308472:policy/AWSLoadBalancerControllerIAMPolicy \
--override-existing-serviceaccounts \
--approve

eksctl create iamserviceaccount --cluster=my-cluster --namespace=kube-system --name=aws-load-balancer-controller --attach-policy-arn=arn:aws:iam::426994308472:policy/AWSLoadBalancerControllerIAMPolicy --override-existing-serviceaccounts --approve


aws iam create-policy --policy-name AWSLoadBalancerControllerAdditionalIAMPolicy --policy-document file://iam_policy_v1_to_v2_additional.json

aws iam attach-role-policy --role-name eksctl-my-cluster-addon-iamserviceaccount-ku-Role1-1D0S41577BTYG --policy-arn arn:aws:iam::426994308472:policy/AWSLoadBalancerControllerAdditionalIAMPolicy


helm upgrade -i aws-load-balancer-controller eks/aws-load-balancer-controller --set clusterName=my-cluster --set serviceAccount.create=false --set serviceAccount.name=aws-load-balancer-controller -n kube-system

## Cambio
kubectl config get-contexts
kubectl config use-context CONTEXT_NAME



## certificados
https://www.youtube.com/watch?v=JbQbwum196g