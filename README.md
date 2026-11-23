# O Sistema que Esquecia Tudo — ReservaLab

## Situação-problema

O **ReservaLab** é um sistema web usado pelo laboratório de uma instituição de ensino para organizar a reserva de equipamentos (câmera, projetor, microfone, notebook e tablet).

A aplicação permite consultar o catálogo, reservar um equipamento para uma data e um turno, acompanhar agendamentos e cancelar reservas. Tudo parece funcionar... **até o servidor ser desligado**. Ao reiniciar, todas as reservas desaparecem.

Sua equipe foi contratada para corrigir esse problema: substituir o armazenamento temporário por um **banco de dados SQLite integrado ao back-end Java com JDBC**, preservando a interface e as regras de negócio.

**O objetivo não é recriar o site nem reescrever a aplicação inteira.** O desafio está em fazer a aplicação web armazenar, consultar e alterar dados reais em um banco relacional.

## O que o projeto já oferece

- Interface completa em **HTML, CSS e JavaScript**.
- Servidor **Java 17+** com endpoints HTTP e validações de entrada.
- Catálogo de cinco equipamentos.
- Cadastro, listagem e cancelamento de reservas em memória.
- Scripts de execução sem Maven e preparação automática do driver SQLite JDBC (primeira execução com internet).
- Interface `RepositorioReservas` e classe `RepositorioSqlite` com pontos de implementação.

A execução inicial usa `RepositorioMemoria`, portanto a perda de dados após reiniciar **é intencional**.

## Preparação e execução

Você precisa somente do **JDK 17 ou superior** (Java e `javac`). **Não é necessário instalar Maven.**

Clone o repositório publicado pelo professor (não há branches especiais).

- **Windows:** execute `executar.bat` (ou `./executar.bat` no terminal apropriado).
- **Linux/macOS:** execute `./executar.sh` (ou `sh executar.sh`).

Os scripts compilam o Java e iniciam o servidor. Na primeira execução, **se houver internet**, tentam obter automaticamente as bibliotecas do SQLite JDBC diretamente do Maven Central, colocando-as em `lib/`. Após esse primeiro download, funcionam offline. **Este ZIP não contém os JARs**; a primeira execução com SQLite precisa obtê-los pela internet, ou você deve recebê-los do professor na pasta `lib/`.

**Sem internet?** A versão inicial em memória continua executando sem driver; para usar o `RepositorioSqlite`, o driver precisa estar presente. O professor pode disponibilizar a pasta `lib/` com os arquivos JAR para eliminar também o download inicial. Outra opção é executar `preparar_jdbc.bat` ou `./preparar_jdbc.sh` em uma conexão com internet.

Se o download automático falhar, o script informará o link oficial de cada JAR. Não é necessário instalar nada globalmente.

Depois, abra **http://localhost:8777** no navegador.

A porta pode ser alterada com a variável de ambiente `PORT`. O banco da solução ficará em `data/reservalab.db`, que **não deve ser enviado para o GitHub**.

**Demonstração do problema:** crie uma reserva, encerre o servidor (`Ctrl+C`), inicie-o novamente e verifique o histórico.

## Desafios obrigatórios

### 1 — Modelar e criar o banco de dados

Crie as tabelas `equipamentos` e `reservas` em SQLite. A tabela de reservas deve guardar: identificador, equipamento, responsável, data, turno e status. Defina chave primária e vínculo entre a reserva e o equipamento.

A criação do banco deve acontecer de forma automática na inicialização, quando ele ainda não existir. O catálogo deve ser preenchido apenas na primeira execução, sem duplicar equipamentos em reinicializações posteriores.

### 2 — Consultar equipamentos do banco

Implemente `listarEquipamentos()` em `RepositorioSqlite`, utilizando JDBC. O catálogo exibido no navegador deve passar a vir de um `SELECT`, e não de uma lista fixa em memória.

### 3 — Salvar e listar reservas

Implemente `criar()` e `listarReservas()` com instruções SQL e `PreparedStatement` quando houver parâmetros externos. Cada nova reserva deve gerar um identificador e aparecer no histórico mesmo após reiniciar o servidor.

### 4 — Cancelar sem apagar o histórico

Implemente `cancelar()`: o status de uma reserva ativa deve mudar para `CANCELADA`. Não exclua fisicamente esse registro, pois o sistema precisa preservar o histórico de agendamentos.

### 5 — Garantir as regras de negócio

- O mesmo equipamento **não pode ter duas reservas ativas para a mesma data e turno**.
- Uma reserva cancelada **libera novamente** aquele equipamento no período correspondente.
- Uma reserva não pode referenciar um equipamento inexistente.
- O sistema deve continuar rejeitando nomes, datas e turnos inválidos.
- As consultas e alterações devem utilizar parâmetros adequados, sem concatenar entradas do usuário no SQL.

### 6 — Integrar ao sistema já existente

Depois de concluir a classe `RepositorioSqlite`, altere a inicialização em `App.java` para utilizar o novo repositório. O front-end não deve perder nenhuma funcionalidade.

## Como saber se está pronto?

1. Crie uma reserva pelo navegador e verifique que aparece no histórico.
2. Reinicie o servidor: **a reserva deve continuar lá**.
3. Tente reservar o mesmo equipamento, dia e turno: o sistema deve rejeitar a duplicação.
4. Cancele a primeira reserva e tente reservar novamente o mesmo período: agora deve funcionar.
5. Reinicie o servidor de novo: tanto a reserva cancelada quanto a nova reserva ativa devem permanecer no histórico.
6. Confirme que reiniciar a aplicação não duplica os cinco equipamentos iniciais.

## Entrega

Entregue o código-fonte com a implementação JDBC, as instruções para executar e **um registro curto da verificação**, informando quais testes manuais você realizou e o que observou antes e depois da reinicialização.

O arquivo `.db`, a pasta `build/` e outras saídas geradas localmente não devem fazer parte da entrega. Se o projeto for enviado como repositório, a pasta `lib/` pode incluir apenas os JARs originais das dependências (com as licenças indicadas).

## Critérios de avaliação

Serão observados: persistência real dos dados, integração Java/SQLite via JDBC, operações de cadastro/consulta/cancelamento, respeito às regras de negócio, organização do código e evidências de funcionamento.

**Não há necessidade de apresentação formal.** A conclusão das funcionalidades obrigatórias é suficiente para demonstrar o objetivo da atividade; recursos extras são opcionais e não substituem os requisitos.

## Mapa do projeto

```text
src/main/java/br/edu/reservalab/
  App.java                   # Servidor HTTP e validação das requisições
  RepositorioReservas.java   # Contrato de acesso aos dados
  RepositorioMemoria.java    # Implementação atual (perde os dados)
  RepositorioSqlite.java     # Aqui fica a maior parte do desafio
  Equipamento.java          # Modelo
  Reserva.java              # Modelo
  RegraException.java       # Erros de negócio
src/main/resources/web/
  index.html                 # Interface pronta
  app.js                     # Integração HTTP
  styles.css                 # Visual da aplicação
executar.bat / executar.sh  # Compilam e iniciam sem Maven
preparar_jdbc.bat / .sh      # Obtêm os JARs na primeira execução
ferramentas/                 # Código Java do preparador
lib/                         # Bibliotecas JDBC após o download
LICENCAS_E_DEPENDENCIAS.md   # Créditos e licenças
.gitignore                   # Evita enviar banco e arquivos gerados
```

**Atenção:** o projeto é um exercício educacional e não implementa autenticação de usuários. Execute-o apenas no ambiente local de desenvolvimento.
