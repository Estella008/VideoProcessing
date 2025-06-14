import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Filtros {
    //Filtro que remove os ruídos da imagem(pontos brancos e pretos aleatórios que aparecem na imagem)


    public static byte[][][] removerSalPimenta(byte[][][] pixels) {

        int qFrames = pixels.length;
        int altura = pixels[0].length;
        int largura = pixels[0][0].length;
        byte valorMedio;

        int numThreads = Runtime.getRuntime().availableProcessors();

        //Garantindo que o número de threads é menor do que o de frames
        // (eu sendo muito otimista com o meu notebook kkkkkk)
        if (numThreads > qFrames) {
            numThreads = qFrames;
        }
        List<ThreadRemoverSalPimenta> threads = new ArrayList<>();

        int framesPorThread = qFrames / numThreads;
        int frameInicialThread = 0;

        for (int i = 0; i < numThreads; i++) {
            int frameFinalThread = frameInicialThread + framesPorThread;
            ThreadRemoverSalPimenta thread = new ThreadRemoverSalPimenta(pixels, frameInicialThread,
                    frameFinalThread, altura, largura);
            threads.add(thread);
            frameInicialThread = frameFinalThread;
        }
        for (ThreadRemoverSalPimenta thread : threads) {
            thread.start(); // Isso faz com que o método run() da thread comece a ser executado
        }
        for (ThreadRemoverSalPimenta thread : threads) {
            try {
                thread.join(); // Espera a thread atual da lista terminar
            } catch (InterruptedException e) {

                Thread.currentThread().interrupt(); // Re-interrompe a thread atual
                System.err.println("A thread foi interrompida enquanto aguardava: " + e.getMessage());

            }
        }


        return pixels;
    }

    public static byte[][][] removerBorroestempo(byte[][][] image) {
        //iltro precisa de pelo menos 3 frames para funcionar
        if (image == null || image.length < 3) {
            System.err.println("Filtro temporal precisa de pelo menos 3 frames para funcionar.");
            return image;
        }

        int totalFrames = image.length;
        int altura = image[0].length;
        int largura = image[0][0].length;

        //matriz nova pra armazenar
        byte[][][] videoProcessado = new byte[totalFrames][altura][largura];

        for (int f = 0; f < totalFrames; f++) {
            for (int y = 0; y < altura; y++) {
                System.arraycopy(image[f][y], 0, videoProcessado[f][y], 0, largura);
            }
        }
        //aplica o filtro ignorando as bordas
        for (int f = 1; f < totalFrames - 1; f++) {
            for (int y = 1; y < altura - 1; y++) {
                for (int x = 1; x < largura - 1; x++) {

                    ArrayList<Integer> pixelsTemporais = new ArrayList<>();
                    pixelsTemporais.add(converteByteInteiro(image[f - 1][y][x]));
                    pixelsTemporais.add(converteByteInteiro(image[f][y][x]));
                    pixelsTemporais.add(converteByteInteiro(image[f + 1][y][x]));

                    Collections.sort(pixelsTemporais);
                    int valorMediano = pixelsTemporais.get(1);

                    videoProcessado[f][y][x] = converteIntByte(valorMediano);
                }
            }
        }

        return videoProcessado;
    }
    private static int converteByteInteiro(byte b) {
        return b & 0xFF;
    }

    private static byte converteIntByte(int valor) {
        if (valor > 255) valor = 255;
        else if (valor < 0) valor = 0;
        return (byte) valor;
    }

}

