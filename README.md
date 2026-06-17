# Arena System 

**Integrantes:**
| Aluno | Microservice | Banco | Responsabilidade |
|---|---|---|---|
| Leticia Saraiva | arena-service | MongoDB | Personagens, combate e partidas |
| Sarah Figueiredo | user-service | PostgreSQL | Cadastro, autenticação e pontuação |

---

## Descrição do Projeto

O Arena System é uma plataforma digital para gestão e simulação de partidas de um jogo físico competitivo chamado Arena. Cada jogador escolhe um personagem com atributos como vida, sanidade, mana e velocidade, e joga turnos estratégicos usando um moveset com 13 habilidades. A API gerencia o motor de combate, rola os dados internamente e aplica os resultados ao estado da partida.

---

## Arquitetura

O sistema é dividido em quatro projetos Spring Boot independentes:

```
Arena_System/
├── discovery-server      # Eureka Server: registro e descoberta de serviços
├── api-gateway           # Spring Cloud Gateway: ponto de entrada único
├── arena-service         # Gerencia personagens, combate e partidas (MongoDB)
└── user-service          # Gerencia usuários e pontuação (PostgreSQL)
```

### Fluxo de uma requisição

```
Cliente → API Gateway (8085) → Discovery Server (8761) → arena-service (9089)
                                                        → user-service
```

O API Gateway consulta o Discovery Server para resolver os endereços lógicos (`arena-service`, `user-service`) e roteia as requisições para a instância correta.

---

## Microsserviços

### arena-service (Leticia Saraiva)

Responsável por personagens, combate turno a turno e registro de partidas.

**Endpoints:**

| Método | Rota | Descrição |
|---|---|---|
| GET | /characters | Lista todos os personagens |
| GET | /characters/{id} | Ficha completa de um personagem |
| POST | /characters | Cadastra novo personagem |
| PATCH | /characters/{id} | Atualiza dados de um personagem |
| POST | /matches | Inicia uma nova partida |
| GET | /matches/{id} | Retorna o estado atual da partida |
| POST | /matches/{id}/turn | Executa um turno de combate |

**Mecânica de turno (POST /matches/{id}/turn):**
1. Cliente envia `{ attackerId, moveId }`
2. A API valida se é o turno do jogador e se o Move pertence ao personagem
3. A API rola internamente um dado de 20 lados
4. Se resultado >= activationValue do Move, o dano é aplicado ao oponente
5. Se resultado < activationValue, o turno passa para o oponente
6. Se a partida terminar, o `winnerId` é definido e um evento `MatchFinished` é publicado no Kafka

### user-service (Sarah Figueiredo)

Responsável por cadastro, autenticação e pontuação dos jogadores.

**Endpoints:**

| Método | Rota | Descrição |
|---|---|---|
| POST | /users/register | Cadastro de novo usuário |
| POST | /users/login | Autenticação e retorno de token JWT |
| GET | /users/{id} | Perfil público do usuário |
| GET | /users/{id}/score | Pontuação detalhada |
| POST | /users/{id}/score | Atualiza pontuação após partida |
| GET | /users/ranking | Ranking público por pontuação |

---

## Bancos de Dados

Cada microsserviço possui seu próprio banco. Nenhum serviço acessa diretamente os dados do outro.

| Serviço | Banco | Database | Justificativa |
|---|---|---|---|
| arena-service | MongoDB | arena_db | Documentos flexíveis para fichas e estado de combate |
| user-service | PostgreSQL | user_schema | Dados estruturados para usuários e pontuação |

---

## Discovery Server

Utiliza Spring Cloud Netflix Eureka. Ao iniciar, cada microsserviço se registra automaticamente no Discovery Server. O API Gateway usa os nomes lógicos `arena-service` e `user-service` para resolver as rotas sem precisar de IPs fixos.

**Acesso:** http://localhost:8761

---

## API Gateway

Utiliza Spring Cloud Gateway. É o único ponto de entrada externo do sistema.

| Rota Externa | Microsserviço | Descrição |
|---|---|---|
| /api/characters/** | arena-service | Operações com personagens |
| /api/matches/** | arena-service | Partidas e turnos de combate |
| /api/users/** | user-service | Usuários e rankings |

**Acesso:** http://localhost:8085

---

## Estratégia de Resiliência

Implementada no `arena-service` com Resilience4j, protegendo a chamada ao `user-service` para atualização de pontuação após partida.

**Retry:** 3 tentativas com intervalo de 3 segundos em caso de `ResourceAccessException`.

**Circuit Breaker:**
- Janela deslizante de 5 chamadas
- Abre com 50% de falhas (mínimo 2 chamadas)
- Permanece aberto por 15 segundos
- Permite 2 chamadas no estado half-open

**Fallback:** se o `user-service` estiver indisponível, a partida é registrada normalmente com `scoreUpdated = false`, sem interromper o fluxo principal.

---

## Kafka — Comunicação Assíncrona

O `arena-service` publica um evento de domínio ao final de cada partida.

**Evento:** `MatchFinished`  
**Tópico:** `match-finished`  
**Produtor:** arena-service  
**Consumidor:** user-service  

**Estrutura do evento:**
```json
{
  "matchId": "6a3067c19ec3bbfdc77a2218",
  "winnerId": "123456789",
  "loserId": "987654321"
}
```

**Justificativa do uso do Kafka:** A atualização de pontuação no `user-service` não precisa acontecer de forma síncrona durante o fluxo de combate. Usando um evento assíncrono, o `arena-service` não precisa aguardar a resposta do `user-service`, reduzindo o acoplamento entre os serviços e evitando falhas em cascata.

**Acesso ao Kafka UI:** http://localhost:8086

---

## Cache

O `arena-service` utiliza Caffeine Cache para o catálogo de personagens.

- `GET /characters` — resultado cacheado com chave `'all'`
- `GET /characters/{id}` — resultado cacheado por `charId`
- `POST /characters` — invalida o cache da listagem completa
- `PATCH /characters/{id}` — invalida o cache do personagem e da listagem completa

O cache evita consultas repetidas ao MongoDB para dados que mudam com pouca frequência, reduzindo a latência de resposta.

---

## Observabilidade

### Métricas

Expostas via Spring Boot Actuator e Micrometer, coletadas pelo Prometheus e visualizadas no Grafana.

**Endpoint de métricas:** http://localhost:9089/actuator/prometheus  
**Prometheus:** http://localhost:9090  
**Grafana:** http://localhost:3000 (dashboard id: 19004)

**Métricas disponíveis:**
- Quantidade de requisições por endpoint e status HTTP
- Tempo médio de resposta por endpoint
- Estado do Circuit Breaker do user-service
- Estatísticas de memória JVM (heap e non-heap)
- Uso de CPU
- Estatísticas de logs (INFO e ERROR) via Logback

### Logs

Os logs são estruturados em três destinos:

**Console:** todos os logs da aplicação com correlationId  
**Arquivo (`logs/requests.log`):** apenas logs de requisições dos endpoints de partidas, turnos e personagens, e logs do producer Kafka  
**Logstash:** todos os logs enviados em formato JSON via TCP, enriquecidos com campos `service`, `ambiente` e `stack`

**Exemplos de logs importantes:**

```
# Requisição recebida
17:12:05 [9c0425ef] INFO REQUEST_LOGGER - GET /characters | buscando todos os personagens

# Cache miss — busca no banco (aparece apenas na primeira chamada)
17:12:05 [9c0425ef] INFO CharacterService - Cache miss | buscando todos os personagens no banco

# Cache hit — nas chamadas seguintes o log do service não aparece, provando que o cache atuou

# Evento Kafka publicado
17:12:58 [de507dcc] INFO MatchEventProducer - Publicando evento no Kafka = topic: match-finished | matchId: 6a31... | winnerId: 123456789 | loserId: 987654321
17:12:58 [de507dcc] INFO MatchEventProducer - Evento publicado com sucesso no tópico: match-finished | matchId: 6a31...
```

### Correlação entre serviços

Cada requisição HTTP recebe um `correlationId` único gerado pelo `CorrelationIdFilter`. Este ID é injetado automaticamente em todos os logs da requisição via MDC (Mapped Diagnostic Context) do SLF4J.

O mesmo `correlationId` aparece no log do controller, no log do service e no log do producer Kafka, permitindo rastrear uma operação completa de ponta a ponta.

**Exemplo de correlação:**
```
[9c0425ef] GET /characters | buscando todos os personagens       ← controller
[9c0425ef] Cache miss | buscando todos os personagens no banco   ← service
```

No Logstash, o campo `correlationId` aparece como campo estruturado em cada evento JSON.

---

## Infraestrutura Docker

Todos os serviços de suporte sobem via Docker Compose:

| Container | Imagem | Porta | Descrição |
|---|---|---|---|
| arena-mongodb | mongo:8 | 27017 | Banco do arena-service |
| arena-kafka | apache/kafka:4.1.0 | 9092 | Broker Kafka |
| arena-kafka-ui | provectuslabs/kafka-ui | 8086 | Interface visual do Kafka |
| arena-prometheus | prom/prometheus | 9090 | Coleta de métricas |
| arena-grafana | grafana/grafana | 3000 | Visualização de métricas |
| arena-logstash | docker.elastic.co/logstash/logstash:8.13.0 | 5001 | Recebe e enriquece logs |

---

## Como executar

1. Subir a infraestrutura Docker:
```bash
docker-compose up -d
```

2. Iniciar os serviços na ordem:
```
1. discovery-server
2. api-gateway
3. arena-service
4. user-service
```

3. Verificar registro no Eureka: http://localhost:8761

4. Testar via API Gateway:
```bash
# Criar personagem
POST http://localhost:8085/api/characters

# Iniciar partida
POST http://localhost:8085/api/matches
{
  "player1Id": "id1",
  "player2Id": "id2",
  "char1Id": "<id_do_personagem_1>",
  "char2Id": "<id_do_personagem_2>"
}

# Jogar turno
POST http://localhost:8085/api/matches/{id}/turn
{
  "attackerId": "id1",
  "moveId": "Unibeam"
}
```

---

## Repositório

https://github.com/Leticia-SS/Arena_System.git
