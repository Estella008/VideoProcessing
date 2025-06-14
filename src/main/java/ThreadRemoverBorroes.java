import java.util.ArrayList;

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
        for (int f = frameInicial; f < frameFinal ; f++) {
            byte quadroProcessado[][]= new byte[altura][largura];
            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    ArrayList<Byte> framesVizinhos = new ArrayList<>();
                    for (int i = f - 1; i <= f + 1; i++) {
                        framesVizinhos.add(videoPixels[i][y][x]);

                    }
                    byte valorMedio = UltilitariosDeFiltros.media(framesVizinhos);
                    quadroProcessado[y][x] = valorMedio;
                }

            }
            videoPixels[f] = quadroProcessado.clone();
        }

    }
}
