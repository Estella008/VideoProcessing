import java.util.Random;

public class Testes {

    public static void exibirCamada(byte[][] vetor) {



        for (int i = 0; i < vetor.length; i++) {
            for (int j = 0; j < vetor[0].length; j++) {
                System.out.print(vetor[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static byte[][][] criarVetor3D(int camadas, int linhas, int colunas) {
        Random random = new Random();
        byte[][][] vetor = new byte[camadas][linhas][colunas];

        for (int i = 0; i < camadas; i++) {
            for (int j = 0; j < linhas; j++) {
                for (int k = 0; k < colunas; k++) {
                   ;
                    vetor[i][j][k] = (byte) random.nextInt(256); // 0 a 255
                    vetor[i][j][k] += Byte.MIN_VALUE; // ajusta para -128 a 127
                }
            }
        }

        return vetor;
    }

    public static void imprimirFrame(byte[][][] videoPixels, int frameIndex) {
        // Verifica se o vetor de pixels é nulo ou vazio
        if (videoPixels == null || videoPixels.length == 0) {
            System.out.println("O vetor de vídeo está vazio ou é nulo.");
            return;
        }

        // Verifica se o índice do frame é válido
        if (frameIndex < 0 || frameIndex >= videoPixels.length) {
            System.out.println("Índice de frame inválido: " + frameIndex + ". O vídeo possui " + videoPixels.length + " frames.");
            return;
        }

        byte[][] frame = videoPixels[frameIndex];
        int altura = frame.length;
        int largura = frame[0].length;

        System.out.println("\n--- Imprimindo Frame " + frameIndex + " (Dimensões: " + altura + "x" + largura + ") ---");

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                // Converte o byte para int sem sinal (0-255) antes de imprimir
                // Isso garante que valores como -1 sejam mostrados como 255, etc.
                int pixelValue = frame[y][x] & 0xFF;
                System.out.printf("%4d ", pixelValue); // Formata para ocupar 4 espaços, para alinhamento
            }
            System.out.println(); // Quebra de linha após cada linha de pixels
        }
        System.out.println("-----------------------------------------\n");
    }
    public static  void main(String[] args) {
        byte vetor3d[][][] = criarVetor3D(6,4,7);
        imprimirFrame(vetor3d, 0);
        imprimirFrame(vetor3d, 1);
        imprimirFrame(vetor3d, 2);
       byte[][][] vetor= Filtros.removerSalPimenta(vetor3d);
        imprimirFrame(vetor, 0);
        imprimirFrame(vetor, 1);
    }

}
