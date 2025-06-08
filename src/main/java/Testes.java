import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

import java.util.Random;

public class Testes {

    public static void gravarVideo(byte pixels[][][],
                                   String caminho,
                                   double fps) {

        int qFrames = pixels.length;
        int altura = pixels[0].length;
        int largura = pixels[0][0].length;

        int fourcc = VideoWriter.fourcc('a', 'v', 'c', '1');   // identificação codec .mp4
        VideoWriter escritor = new VideoWriter(
                caminho, fourcc, fps, new Size(largura, altura), true);

        if (!escritor.isOpened()) {
            System.err.println("Erro ao gravar vídeo no caminho sugerido");
        }

        Mat matrizRgb = new Mat(altura, largura, CvType.CV_8UC3); //voltamos a operar no RGB (limitação da lib)

        byte linha[] = new byte[largura * 3];                // BGR intercalado

        for (int f = 0; f < qFrames; f++) {
            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    byte g = (byte) pixels[f][y][x];
                    int i = x * 3;
                    linha[i] = linha[i + 1] = linha[i + 2] = g;     // cinza → B,G,R
                }
                matrizRgb.put(y, 0, linha);
            }
            escritor.write(matrizRgb);
        }
        escritor.release(); //limpando o buffer
    }

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
       byte[][][] vetor= Filtros.removerBorroestempo(vetor3d);
        imprimirFrame(vetor, 0);
        imprimirFrame(vetor, 1);
    }

}
