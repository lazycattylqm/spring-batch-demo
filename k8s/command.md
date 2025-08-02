kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/job.yaml # 一次性任务
kubectl apply -f k8s/cronjob.yaml # 定时任务

minikube image load spring-batch-demo:latest
