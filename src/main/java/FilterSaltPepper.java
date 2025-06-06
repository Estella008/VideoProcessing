import java.util.ArrayList;

public class FilterSaltPepper {
    //Filtro que remove os ruídos da imagem(pontos brancos e pretos aleatórios que aparecem na imagem)
    public static byte[][][] removerSalPimenta(byte[][][] image) {
        return null;
    }
    //Filtro que melhora as partes que estão embaçadas ou arrastadas
    public static byte[][][] removerBorroestempo(byte[][][] image) {
        return null;
    }
    private static int converteByteInteiro(byte b) {
        return b & 0xFF; //aplica uma "máscara" que efetivamente descarta qualquer informação de sinal negativo
        // quando o byte é promovido para int, resultando no valor numérico correto (200).
    };
    private static byte converteIntByte(int valor) {
        //limite superior
        if (valor > 255) {
            valor = 255;
        }
        //limite inferior
        else if (valor < 0) {
            valor = 0;
        }

        //agora faz a conversão
        return (byte) valor;
    }

        private static int media(ArrayList<Integer> numeros) {
            if (numeros == null || numeros.isEmpty()) {
                return 0;
            }

            //a soma de todos os números
            long soma = 0;
            for (int numero : numeros) {
                soma += numero;
            }
            //a média
            int quantidade = numeros.size();
            double mediaExata = (double) soma / quantidade;

            //arredondar para o inteiro mais próximo e converter para int
            return (int) Math.round(mediaExata);
    }
    private static byte [][] preencheFrameZeros(byte [][][] image, int frame) {
        return null;
    }

    public static void aplicarFiltroDeMedia(byte[][][] pixels) {

    int qFrames = 0;
    int altura = 0;
    int largura = 0;

        for (int f = 0; f < qFrames; f++) {
            byte[][] quadroOriginal = pixels[f];
            byte[][] quadroProcessado = new byte[altura][largura];

        for (int y = 1; y < altura - 1; y++) {
            for (int x = 1; x < largura - 1; x++) {

                // coleta os vizinhos, reaproveitei da mediano, acho que é a mesma coisa
                ArrayList<Integer> vizinhos = new ArrayList<>();
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        vizinhos.add(converteByteInteiro(quadroOriginal[y + j][x + i]));
                    }
                }

                //USA A NOVA FUNÇÃO DE MÉDIA!
                int valorMedio = media(vizinhos);

                //aplica o resultado ao novo quadro
                quadroProcessado[y][x] = converteIntByte(valorMedio);
            }
        }
        pixels[f] = quadroProcessado;
    }
}
}
