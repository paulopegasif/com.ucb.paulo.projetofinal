
# 🔗 Projeto Final - Programação Concorrente em Java

Este projeto implementa um sistema distribuído em Java utilizando **Programação Concorrente com Sockets e Threads**, que simula a busca por artigos em dois servidores diferentes com comunicação intermediada por um servidor principal.

---

## 🧠 Objetivo

- Realizar buscas concorrentes em dois servidores (B e C) a partir de um cliente conectado ao servidor A.
- Contabilizar **quantos artigos** correspondem ao termo de busca e **quantas vezes o termo aparece** nos textos.
- Retornar os resultados encontrados e salvar localmente em `resultados.txt`.

---

## 🧱 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com.ucb.paulo.projetofinal/
│   │       ├── client/            # Código do Cliente
│   │       ├── servers/           # Servidores A, B e C
│   │       ├── model/             # Classe Article
│   │       ├── util/              # JsonUtils e algoritmo KMP
│   │       └── Main.java          # Inicializador com Threads
│   └── resources/
│       ├── dados_servidor_b.json  # Arquivo JSON do ServerB
│       └── dados_servidor_c.json  # Arquivo JSON do ServerC
```

---

## 🚀 Execução

### ✅ Etapa 1: Compilar o projeto
> Use o Maven ou o IntelliJ com suporte a Java 17+.

### ✅ Etapa 2: Rodar a aplicação
Você pode iniciar tudo de forma automática com:

```bash
Run Main.java
```

Ou manualmente:

1. Rodar `ServerB.java` (porta 5001)
2. Rodar `ServerC.java` (porta 5002)
3. Rodar `ServerA.java` (porta 5050)
4. Rodar `Client.java` (conecta-se ao ServerA)

---

## 🧪 Como funciona

1. O usuário digita um termo no `Client`.
2. O `ServerA` repassa esse termo para os servidores `B` e `C`.
3. Cada servidor:
   - Carrega seu JSON local;
   - Aplica uma busca com `KMP`;
   - Retorna os artigos encontrados;
   - Conta **quantas vezes o termo aparece nos textos**.
4. O `ServerA` recebe, soma e repassa para o `Client`.
5. O `Client` salva tudo no arquivo `resultados.txt`.

---

## 📊 Exemplo de Saída

```
[Server A] Cliente enviou: study
[Server B] Termo recebido:s: study
[Server B] Ocorrências do termo "study": 13981

[Server C] Termo recebido: study
[Server C] Ocorrências do termo "study": 14639

[Server A] Ocorrências totais do termo: 28620

[Client] Resultados salvos em resultados.txt

```

---

## 🧰 Tecnologias e Recursos

- Java 17
- Maven
- Sockets TCP/IP
- Threads para execução simultânea
- Manipulação de JSON com `org.json`
- Algoritmo KMP (Knuth-Morris-Pratt)
- Arquivos `.json` com simulação de base de dados científica

---

## 👤 Autor

Paulo Henrique Pereira Silva  
Curso de Ciência da Computação  
Universidade Católica de Brasília (UCB)  
Disciplina: Programação Concorrente – Prof. João Robson Santos Martins

---

## 📎 Licença

Uso acadêmico. Disponível para fins de aprendizado, estudo e extensão de funcionalidades.
