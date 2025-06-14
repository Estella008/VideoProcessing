import java.util.ArrayList;

public class ThreadRemoverSalPimenta extends Thread{
    private byte[][][] videoPixels; // Referência ao vídeo completo
    private int frameInicio;        // Frame de início (inclusivo) para esta thread
    private int frameFinal;          // Frame de fim (exclusivo) para esta thread
    private int altura;
    private int largura;

    // Construtor para inicializar a thread com sua porção de trabalho
    public ThreadRemoverSalPimenta(byte[][][] videoPixels, int start, int end, int altura, int largura) {
        this.videoPixels = videoPixels;
        this.frameInicio = start;
        this.frameFinal = end;
        this.altura = altura;
        this.largura = largura;
    }
    @Override
    public void run() {
        for (int f = frameInicio; f < frameFinal; f++) {

            byte[][] quadroOriginal = videoPixels[f];
            byte[][] quadroProcessado = new byte[altura][largura];
            for (int i = 0; i < altura; i++) {
                //system.arraycopy é uma forma eficiente de copiar arrays 1D
                System.arraycopy(quadroOriginal[i], 0, quadroProcessado[i], 0, largura);
            }

            //ignorando as bordas
            for (int y = 1; y < altura - 1; y++) {
                for (int x = 1; x < largura - 1; x++) {

                    ArrayList<Byte> pixelsVizinhos = new ArrayList<>();
                    for (int linha = y - 1; linha <= y + 1; linha++) {
                        for (int coluna = x - 1; coluna <= x + 1; coluna++) {
                            pixelsVizinhos.add(quadroOriginal[linha][coluna]);
                        }
                    }

                    //calcula a mediana
                    byte valorProcessado = UltilitariosDeFiltros.mediana(pixelsVizinhos);
                    quadroProcessado[y][x] = valorProcessado;
                }
            }

            videoPixels[f] = quadroProcessado;
        }
    }
}
