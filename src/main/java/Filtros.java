import java.util.ArrayList;

public class Filtros {
    //Filtro que remove os ruídos da imagem(pontos brancos e pretos aleatórios que aparecem na imagem)


    public static byte[][][] removerSalPimenta(byte[][][] pixels) {

        int qFrames = pixels.length;
        int altura;
        int largura;
        byte valorMedio;

        for (int f = 0; f < qFrames; f++) {
            byte[][] frameOriginalZeros = UltilitariosDeFiltros.preencheBordaFrameZeros(pixels, f);

            altura = frameOriginalZeros.length;
            largura = frameOriginalZeros[0].length;

            byte[][] quadroProcessado = new byte[altura - 2][largura - 2];


            //pegando os pixels que estão no meio do frame preenchido com zeros
            for (int y = 1; y < altura - 1; y++) {
                for (int x = 1; x < largura - 1; x++) {

                    // coleta os vizinhos como se fosse uma matriz 3x3
                    ArrayList<Byte> pixelsVizinhos = new ArrayList<>();
                    for (int j = -1; j <= 1; j++) {
                        for (int i = -1; i <= 1; i++) {
                            pixelsVizinhos.add(frameOriginalZeros[y + j][x + i]);
                        }
                    }

                    //USA A NOVA FUNÇÃO DE MÉDIA!
                    valorMedio = UltilitariosDeFiltros.media(pixelsVizinhos);

                    //aplica o resultado ao novo quadro
                    quadroProcessado[y - 1][x - 1] = valorMedio;
                }
            }
            pixels[f] = quadroProcessado;
        }
        return pixels;
    }
    public static byte[][][] removerBorroestempo(byte[][][] image) {
        //preenchendo a imagem com um frame de zeros no início e no fim
        byte[][][] imagemPreenchida= UltilitariosDeFiltros.preencheFrameZeros(image);
        int qFrames = imagemPreenchida.length;
        int altura = imagemPreenchida[0].length;
        int largura = imagemPreenchida[0][0].length;

        byte valorMedio;

       //pegando os frames apenas do meio
        for (int f = 1; f < qFrames-1; f++) {
            byte quadroProcessado[][]= new byte[altura][largura];
            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                   ArrayList<Byte> framesVizinhos = new ArrayList<>();
                   for (int i = -1; i <= 1; i++) {
                       framesVizinhos.add(imagemPreenchida[f+i][y][x]);

                   }
                   valorMedio = UltilitariosDeFiltros.media(framesVizinhos);
                   quadroProcessado[y][x] = valorMedio;
                }

            }
           image[f-1] = quadroProcessado;
        }

        return image;
    }

}
