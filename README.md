Processamento de Vídeos Paralelo para Correção de Imagens
Este projeto foi desenvolvido como parte da disciplina de Arquitetura e Organização de Computadores no Instituto Federal de Minas Gerais (IFMG - Campus Ouro Branco). O objetivo é aplicar conceitos de programação paralela para otimizar a correção de defeitos comuns em vídeos, como ruído "Salt and Pepper" e borrões temporais.

🎯 Objetivos do Projeto
Revisar e aplicar conceitos de programação paralela (utilizando java.lang.Thread).
Implementar algoritmos de processamento digital de imagens (PDI) para remoção de ruídos e correção de borrões.
Analisar o ganho de desempenho ao utilizar múltiplos núcleos de processamento.
Estudar o impacto do número de threads na eficiência da execução.
🌟 O Problema (Caso de Uso do Seu José)
Seu José, um morador de Ouro Branco, encontrou um vídeo antigo de família danificado pelo tempo. O vídeo apresenta:

Ruído "Salt & Pepper": Pontos pretos e brancos aleatórios (chuviscos).
Borrões Temporais: Quadros embaçados que parecem "vultos" devido à degradação da fita.
O desafio é restaurar a qualidade visual do vídeo, otimizando o tempo de correção através do uso de múltiplos núcleos de CPU.

✨ Técnicas de Correção Implementadas
O projeto implementa dois filtros principais para a correção dos defeitos:

Remoção de Ruído "Salt & Pepper":

Utiliza um filtro de mediana espacial (3x3). A mediana é escolhida por sua superioridade em remover ruído "Salt & Pepper" ao preservar melhor as bordas e detalhes da imagem em comparação com a média.
Lida com as bordas da imagem adaptando o kernel, usando Math.max e Math.min para garantir o acesso a vizinhos válidos.
Correção de Borrões Temporais:

Utiliza um filtro de média temporal (3 frames). A média é aplicada no eixo temporal para suavizar os borrões entre os quadros.
Lida com as bordas temporais (primeiro e último frame do vídeo) adaptando o kernel, usando Math.max e Math.min.
Considerações Importantes dos Filtros:
Tratamento de Pixels: Valores de pixels (byte) são tratados como inteiros sem sinal (0-255) durante os cálculos (valor & 0xFF), com arredondamento e clamping (saturação em 0-255) no resultado final.
Operação In-Place: Ambos os filtros operam "no lugar", ou seja, modificam o array de vídeo original que é passado como parâmetro.
🚀 Paralelização (Programação Concorrente)
Para otimizar o tempo de execução, a correção do vídeo é realizada em paralelo, utilizando múltiplas threads.

Divisão de Trabalho: O vídeo é dividido em segmentos de frames. Cada java.lang.Thread é responsável por processar um subconjunto específico desses frames de forma independente.
Gerenciamento de Threads: As threads são criadas, iniciadas (.start()) e aguardadas (.join()) manualmente para garantir que todo o processamento seja concluído antes de prosseguir.
Segurança de Dados: As funções de filtro são projetadas para evitar "condições de corrida" e "efeito de varredura":
Cada thread trabalha em uma cópia independente do frame atual (para o filtro espacial) ou do vídeo completo (para o filtro temporal) antes de gravar o resultado de volta.
Os resultados são escritos em posições distintas do array de vídeo compartilhado.
Otimização de ifs: A lógica de Math.max e Math.min foi escolhida para lidar com as bordas, minimizando o impacto de desvios condicionais na performance da CPU em loops intensivos, o que é crucial para o desempenho paralelo.
🧪 Testes e Análise de Desempenho
Após a implementação das correções, é necessário realizar testes comparando o tempo de execução em diferentes configurações:

Solução Sequencial: Sem o uso de threads.
Solução Paralela: Utilizando threads com diferentes números de núcleos lógicos (2, 4, 8, 16, 32). O número de núcleos lógicos disponíveis no computador pode ser obtido via Runtime.getRuntime().availableProcessors().
Os resultados devem ser organizados em uma tabela e analisados criticamente, respondendo a perguntas como:

Como o tempo de execução varia conforme aumentamos o número de threads?
Qual o comportamento do tempo de execução quando o número de threads excede a quantidade de núcleos físicos/lógicos instalados na máquina?
🛠️ Tecnologias Utilizadas
Java 8+: Linguagem de programação.
OpenCV (via nu.pattern.OpenCV): Biblioteca para manipulação de vídeo (leitura e escrita de arquivos .mp4).
Conceitos de java.lang.Thread: Para programação paralela.
📦 Estrutura do Projeto
O projeto é organizado nas seguintes classes principais:

VideoProcessing.java: Contém o método main, funções para carregar/gravar vídeos, e métodos auxiliares para depuração (como imprimirFrame).
Filtros.java: Contém as implementações dos filtros removerSalPimenta e removerBorroesTempo, que gerenciam as threads.
UltilitariosDeFiltros.java: Contém métodos utilitários estáticos, como media e mediana (para cálculo de pixels), e outras funções auxiliares genéricas.
RemoverSalPimentaThread.java: Classe que estende Thread, encapsulando a lógica paralela do filtro "Salt and Pepper" para um segmento de frames.
RemoverBorroesTempoThread.java: Classe que estende Thread, encapsulando a lógica paralela do filtro de borrões temporais para um segmento de frames.
⚙️ Como Executar o Projeto
Pré-requisitos:
Java Development Kit (JDK) 8 ou superior instalado.
Configurar a biblioteca OpenCV no seu projeto. Se estiver usando Maven/Gradle, adicione a dependência nu.pattern:opencv (verifique a versão compatível). Caso contrário, adicione os JARs do OpenCV ao seu classpath.
Preparação:
Coloque um arquivo de vídeo .mp4 na pasta D:\Download (ou ajuste o caminho no código VideoProcessing.java).
O código está configurado para salvar o vídeo processado em D:\Download\video2.mp4.
Compilação e Execução:
Compile todos os arquivos .java do projeto.
Execute a classe VideoProcessing.java.
📜 Documentação
A documentação do trabalho (a ser entregue em PDF) deve conter:

Introdução: Descrição do problema e visão geral do funcionamento do programa.
Implementação: Detalhamento da estrutura de dados, funções, procedimentos e decisões de projeto (especialmente sobre tratamento de bordas e paralelização).
Conclusão: Comentários gerais, dificuldades encontradas e aprendizados.
Bibliografia: Fontes e sites consultados.
Desenvolvido por: Aquiles Ascar, Estella Moreira e Matheus Henrique Baêta
Disciplina: Arquitetura e Organização de Computadores
Professor: Saulo Henrique Cabral Silva
IFMG - Campus Ouro Branco
