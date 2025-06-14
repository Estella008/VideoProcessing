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
        //preenchendo a imagem com um frame de zeros no início e no fim

        int qFrames = image.length;
        int altura = image[0].length;
        int largura = image[0][0].length;



        //Obtendo o número de nucleos do computador
        int numThreads = Runtime.getRuntime().availableProcessors();
        if (numThreads > qFrames) {
            numThreads = qFrames;
        }
        //Calulando quantos frames por thread
        int framesPorThread = (qFrames - 2) / numThreads;

        ArrayList<ThreadRemoverBorroes> threads = new ArrayList<>();
        int frameInicialThread = 1;
        for (int i = 0; i < numThreads; i++) {
            int frameFinalThread = frameInicialThread + framesPorThread;
            ThreadRemoverBorroes thread = new ThreadRemoverBorroes(image, frameInicialThread, frameFinalThread,
                    altura, largura);
            //Armazenando a tread
            threads.add(thread);
            //Atualizando o início
            frameInicialThread = frameFinalThread;
        }

        //Excutando cada thread
        for (ThreadRemoverBorroes thread : threads) {
            thread.start();
        }
        for (ThreadRemoverBorroes thread : threads) {
            try {
                thread.join(); // Espera a thread atual da lista terminar
            } catch (InterruptedException e) {

                Thread.currentThread().interrupt(); // Re-interrompe a thread atual
                System.err.println("A thread foi interrompida enquanto aguardava: " + e.getMessage());

            }



        }

        return image;

    }
}