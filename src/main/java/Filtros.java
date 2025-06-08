import java.util.ArrayList;

public class Filtros {
    //Filtro que remove os ruídos da imagem(pontos brancos e pretos aleatórios que aparecem na imagem)


    public static byte[][][] removerSalPimenta(byte[][][] pixels) {

        int qFrames = pixels.length;
        int altura = pixels[0].length;
        int largura = pixels[0][0].length;
        byte valorMedio;


        for (int f = 0; f < qFrames; f++) {


            byte[][] quadroProcessado = pixels[f].clone();


            //pegando os pixels que estão no meio do frame
            for (int y = 1; y < altura -1 ; y++) {
                for (int x = 1; x < largura -1; x++) {

                    // coleta os vizinhos como se fosse uma matriz 3x3
                    ArrayList<Byte> pixelsVizinhos = new ArrayList<>();
                    for (int j = -1; j <= 1; j++) {
                        for (int i = -1; i <= 1; i++) {
                            pixelsVizinhos.add(pixels[f][y + j][x + i]);
                        }
                    }

                    //USA A NOVA FUNÇÃO DE MÉDIA!
                    valorMedio = UltilitariosDeFiltros.media(pixelsVizinhos);

                    //aplica o resultado ao novo quadro
                    quadroProcessado[y][x] = valorMedio;
                }
            }
            pixels[f] = quadroProcessado.clone();
        }
        return pixels;
    }
    public static byte[][][] removerBorroestempo(byte[][][] image) {
        //preenchendo a imagem com um frame de zeros no início e no fim

        int qFrames = image.length;
        int altura = image[0].length;
        int largura = image[0][0].length;

        byte valorMedio;

       //pegando os frames apenas do meio
        for (int f = 1; f < qFrames -1; f++) {
            byte quadroProcessado[][]= new byte[altura][largura];
            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                   ArrayList<Byte> framesVizinhos = new ArrayList<>();
                   for (int i = -1; i <= 1; i++) {
                       framesVizinhos.add(image[f+i][y][x]);

                   }
                   valorMedio = UltilitariosDeFiltros.media(framesVizinhos);
                   quadroProcessado[y][x] = valorMedio;
                }

            }
           image[f] = quadroProcessado.clone();
        }

        return image;
    }

}
