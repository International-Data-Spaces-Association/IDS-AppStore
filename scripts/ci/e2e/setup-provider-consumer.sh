#!/usr/bin/env bash
set -eo pipefail

. ./scripts/ci/libraries/init.sh

echo "Setup provider and consumer"

# Consumer setup
helm install consumer charts/dataspace-connector --set env.config.SPRING_APPLICATION_NAME="Consumer Connector" 2>&1 > /dev/null

# Provider setup
sed -i "s/^appVersion:.*$/appVersion: ci/" charts/dataspace-connector/Chart.yaml
helm install provider charts/dataspace-connector --set env.config.SPRING_APPLICATION_NAME="Producer Connector" 2>&1 > /dev/null

echo "Waiting for readiness"
kubectl rollout status deployments/provider-dataspace-connector --timeout=360s 2>&1 > /dev/null
kubectl rollout status deployments/consumer-dataspace-connector --timeout=60s  2>&1 > /dev/null

# Make sure the deployments are really ready and the rollout did not just timeout
kubectl wait --for=condition=available deployments/provider-dataspace-connector --timeout=1s 2>&1 > /dev/null
kubectl wait --for=condition=available deployments/consumer-dataspace-connector --timeout=1s 2>&1 > /dev/null

echo "Exposing services to localhost"
export PROVIDER_POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=dataspace-connector,app.kubernetes.io/instance=provider" -o jsonpath="{.items[0].metadata.name}")
export PROVIDER_CONTAINER_PORT=$(kubectl get pod --namespace default $PROVIDER_POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")
export CONSUMER_POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=dataspace-connector,app.kubernetes.io/instance=consumer" -o jsonpath="{.items[0].metadata.name}")
export CONSUMER_CONTAINER_PORT=$(kubectl get pod --namespace default $CONSUMER_POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")

kubectl port-forward $PROVIDER_POD_NAME 8080:$PROVIDER_CONTAINER_PORT 2>&1 > /dev/null &
kubectl port-forward $CONSUMER_POD_NAME 8081:$CONSUMER_CONTAINER_PORT 2>&1 > /dev/null &

echo "$LINE_BREAK_DASH"
while read INPUT; do
    export CURRENT_TEST_SCRIPT=$INPUT
    test::run_test_script
done <./scripts/ci/e2e/active-tests.txt

test::evaluate_test_runs

echo "$LINE_BREAK_DASH"
echo "Cleanup"
helm uninstall provider 2>&1 > /dev/null
helm uninstall consumer 2>&1 > /dev/null

if [ $TEST_FAILURES -gt 0 ]; then
    exit 1
fi
