#!/bin/bash

echo "Iniciando teste de concorrência com duas transferências simultâneas..."

# Requisição 1 (em background)
curl --request POST \
  --url http://localhost:8080/transferencias \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.0.0' \
  --data '{
    "origemId": 1,
    "destinoId": 2,
    "valor": 100
}' &
PID1=$!

# Requisição 2 (em background)
curl --request POST \
  --url http://localhost:8080/transferencias \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.0.0' \
  --data '{
    "origemId": 1,
    "destinoId": 2,
    "valor": 100
}' &
PID2=$!

# Aguardar as duas finalizarem
wait $PID1
wait $PID2

echo "Teste finalizado."
