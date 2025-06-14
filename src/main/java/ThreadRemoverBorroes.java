import java.util.ArrayList;
import java.util.Collections;

public class ThreadRemoverBorroes extends Thread{
    byte[][][] videoPixels;
    int frameInicial;
    int frameFinal;
    int altura;
    int largura;

    public ThreadRemoverBorroes(byte[][][] videoPixels, int frameInicial, int frameFinal,
                                int altura, int largura) {
        this.videoPixels = videoPixels;
        this.frameInicial = frameInicial;
        this.frameFinal = frameFinal;
        this.altura = altura;
        this.largura = largura;
    }

    @Override
    public void run() {
        if (videoPixels == null || videoPixels.length < 3) {
            //o filtro precisa de pelo menos 3 frames para funcionar.
            return;
        }

        int totalFrames = videoPixels.length;
        int altura = videoPixels[0].length;
        int largura = videoPixels[0][0].length;

        //cria um buffer
        byte[][][] videoProcessado = new byte[totalFrames][altura][largura];

        for (int f = 0; f < totalFrames; f++) {
            for (int y = 0; y < altura; y++) {
                System.arraycopy(videoPixels[f][y], 0, videoProcessado[f][y], 0, largura);
            }
        }

        for (int f = 1; f < totalFrames - 1; f++) {
            for (int y = 1; y < altura - 1; y++) {
                for (int x = 1; x < largura - 1; x++) {

                    //coleta o mesmo pixel dos 3 frames vizinhos no tempo
                    ArrayList<Integer> pixelsTemporais = new ArrayList<>();


                    pixelsTemporais.add(converteByteInteiro(videoPixels[f - 1][y][x]));
                    pixelsTemporais.add(converteByteInteiro(videoPixels[f][y][x]));
                    pixelsTemporais.add(converteByteInteiro(videoPixels[f + 1][y][x]));
                    Collections.sort(pixelsTemporais);

                    int valorMediano = pixelsTemporais.get(1);

                    videoProcessado[f][y][x] = converteIntByte(valorMediano);
                }
            }
        }

        for (int f = 0; f < totalFrames; f++) {
            for (int y = 0; y < altura; y++) {
                System.arraycopy(videoProcessado[f][y], 0, videoPixels[f][y], 0, largura);
            }
        }
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

