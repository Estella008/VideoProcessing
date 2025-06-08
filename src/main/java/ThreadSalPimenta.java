import java.util.ArrayList;

public class ThreadSalPimenta extends Thread{
    private byte[][][] videoPixels; // Referência ao vídeo completo
    private int frameInicio;        // Frame de início (inclusivo) para esta thread
    private int frameFinal;          // Frame de fim (exclusivo) para esta thread
    private int altura;
    private int largura;

    // Construtor para inicializar a thread com sua porção de trabalho
    public ThreadSalPimenta(byte[][][] pixels, int start, int end, int h, int w) {
        this.videoPixels = pixels;
        this.frameInicio = start;
        this.frameFinal = end;
        this.altura = h;
        this.largura = w;
    }
    @Override
    public void run(){
        for (int f = frameInicio; f < frameFinal; f++) {


            byte[][] quadroProcessado = videoPixels[f].clone();


            //pegando os pixels que estão no meio do frame
            for (int y = 0; y < altura ; y++) {
                for (int x = 0; x < largura ; x++) {

                    // coleta os vizinhos como se fosse uma matriz 3x3
                    ArrayList<Byte> pixelsVizinhos = new ArrayList<>();
                    for (int linha = Math.max(0, y - 1); linha <= Math.min(y + 1, altura - 1); linha++) {
                        for (int coluna = Math.max(0,x-1); coluna <= Math.min(x + 1, largura - 1);coluna++) {
                            pixelsVizinhos.add(videoPixels[f][linha][coluna]);
                        }
                    }

                    //USA A NOVA FUNÇÃO DE MÉDIA!
                    byte valorMedio = UltilitariosDeFiltros.media(pixelsVizinhos);

                    //aplica o resultado ao novo quadro
                    quadroProcessado[y][x] = valorMedio;
                }
            }
            videoPixels[f] = quadroProcessado.clone();
        }

    }
}
