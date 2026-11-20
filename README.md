# 🧠 Processamento de Vídeos Paralelo para Correção de Imagens

Este projeto foi desenvolvido como parte da disciplina de **Arquitetura e Organização de Computadores** no **Instituto Federal de Minas Gerais (IFMG - Campus Ouro Branco)**. O objetivo é aplicar conceitos de programação paralela para otimizar a correção de defeitos comuns em vídeos, como **ruído "Salt and Pepper"** e **borrões temporais**.

---

## 🎯 Objetivos do Projeto

- Revisar e aplicar conceitos de **programação paralela** (`java.lang.Thread`).
- Implementar **algoritmos de Processamento Digital de Imagens (PDI)** para remoção de ruídos e correção de borrões.
- Analisar o **ganho de desempenho** com múltiplos núcleos de processamento.
- Estudar o **impacto do número de threads** na eficiência da execução.

---

## 🌟 O Problema

**Seu José**, um morador de Ouro Branco, encontrou um vídeo antigo de família danificado pelo tempo. O vídeo apresenta:

- 🎞️ **Ruído "Salt & Pepper"**: Pontos pretos e brancos aleatórios (chuviscos).
- 🎞️ **Borrões Temporais**: Quadros embaçados que parecem "vultos" devido à degradação da fita.

O desafio é restaurar a qualidade visual do vídeo, otimizando o tempo de correção com o uso de múltiplos núcleos de CPU.

---

## ✨ Técnicas de Correção Implementadas

### Remoção de Ruído "Salt & Pepper"
- Filtro de **mediana espacial 3x3**.
- Preserva bordas e detalhes melhor que o filtro da média.
- Tratamento adaptativo das bordas com `Math.max` e `Math.min`.

### Correção de Borrões Temporais
- Filtro de **média temporal** entre 3 frames.
- Suaviza borrões entre quadros consecutivos.
- Lida com bordas temporais com lógica adaptativa.

### Considerações Importantes
- **Valores de pixels tratados como inteiros sem sinal (0-255)** com `& 0xFF`.
- **Operação in-place**: os filtros modificam o array original do vídeo.

---

## 🚀 Paralelização (Programação Concorrente)

- **Divisão de Trabalho**: cada `Thread` processa um subconjunto dos frames.
- **Gerenciamento Manual de Threads**: `start()` e `join()`.
- **Segurança de Dados**: evita condições de corrida:
  - Cada thread trabalha com **cópias locais** dos dados antes de gravar o resultado.
  - Escrita em posições distintas do array compartilhado.
- **Otimização de Desvios Condicionais**: uso de `Math.max` e `Math.min` minimiza impacto no desempenho em loops intensivos.

---

## 🧪 Testes e Análise de Desempenho

### Configurações Testadas:
- Solução **sequencial** (sem threads).
- Solução **paralela** com diferentes números de threads (2, 4, 8, 16, 32).

### Perguntas para Análise:
- Como o tempo varia conforme o número de threads?
- O que acontece quando o número de threads excede os núcleos físicos/lógicos?

---

## 🛠️ Tecnologias Utilizadas

- **Java 8+**
- **OpenCV** (`nu.pattern:opencv`) — Leitura/escrita de vídeos `.mp4`
- `java.lang.Thread` — Programação paralela

---

## 📦 Estrutura do Projeto

- `VideoProcessing.java`: `main`, leitura/escrita de vídeos, funções de debug.
- `Filtros.java`: implementação dos filtros paralelos.
- `UltilitariosDeFiltros.java`: funções auxiliares (`mediana`, `media`, etc.).
- `ThreadRemoverSalPimenta.java`: lógica paralela do filtro "Salt and Pepper".
- `ThreadRemoverBorroes.java`: lógica paralela do filtro temporal.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- JDK 8 ou superior
- OpenCV configurado:
  - Com Maven/Gradle: adicione `nu.pattern:opencv`.
  - Sem Maven: adicione os JARs do OpenCV ao classpath.

### Passos
1. Coloque um arquivo `.mp4` em `D:\Download` (ou ajuste o caminho no código).
2. O vídeo processado será salvo em `D:\Download\video2.mp4`.
3. Compile todos os arquivos `.java`.
4. Execute `VideoProcessing.java`.

---

### 👨‍💻 Desenvolvido por:  
Estella Moreira, Aquiles Ascar e Matheus Henrique Baêta

**Disciplina**: Arquitetura e Organização de Computadores  
**Professor**: Saulo Henrique Cabral Silva  
**IFMG - Campus Ouro Branco**

