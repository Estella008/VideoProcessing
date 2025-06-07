import java.util.ArrayList;

public class FilterSaltPepper {
    //Filtro que remove os ruídos da imagem(pontos brancos e pretos aleatórios que aparecem na imagem)


    private static byte media(ArrayList<Byte> numeros) {//nao tenho certeza se precisa ser um array de entrada
        if (numeros == null || numeros.isEmpty()) {
            return 0;
        }
        double soma = 0;
        for (Byte numero : numeros) {
            soma += numero & 0xFF;
        }
        //media arredondada para nao ter perdas
        long media = Math.round(soma / numeros.size());
        //garanatindo que a media está entre 0 e 255 pra nao estourar os bytes
        byte valorMedio;
        if (media < 0) {
            valorMedio = 0;
        }else if(media>255){
            valorMedio = (byte) 255;
        }else{
            valorMedio=(byte) media;
        }
        return valorMedio;

    }

    //preenche as bosrdas de cada frame com zeros
    private static byte[][] preencheFrameZeros(byte[][][] image, int frame) {
        //o tamnho da imagem é 720x960 e 24 frames
        //criando uma imagem auxiliar
        int linhas = image[0].length;
        int colunas = image[0][0].length;
        byte[][] frameZeros = new byte[linhas + 2][colunas + 2];

        for (int i = 1; i < frameZeros.length - 1; i++) {
            for (int j = 1; j < frameZeros[0].length - 1; j++) {
                frameZeros[i][j] = image[frame][i - 1][j - 1];

            }
        }

        return frameZeros;
    }


    public static byte[][][] removerSalPimenta(byte[][][] pixels) {

        int qFrames = pixels.length;
        int altura;
        int largura;

        for (int f = 0; f < qFrames; f++) {
            byte[][] frameOriginalZeros = preencheFrameZeros(pixels, f);

            altura = frameOriginalZeros.length;
            largura = frameOriginalZeros[0].length;

            byte[][] quadroProcessado = new byte[altura - 2][largura - 2];


            //pegando os pixels que estão no meio do frame preenchido com zeros
            for (int y = 1; y < altura - 1; y++) {
                for (int x = 1; x < largura - 1; x++) {

                    // coleta os vizinhos como se fosse uma matriz 3x3
                    ArrayList<Byte> vizinhos = new ArrayList<>();
                    for (int j = -1; j <= 1; j++) {
                        for (int i = -1; i <= 1; i++) {
                            vizinhos.add(frameOriginalZeros[y + j][x + i]);
                        }
                    }

                    //USA A NOVA FUNÇÃO DE MÉDIA!
                    byte valorMedio = media(vizinhos);

                    //aplica o resultado ao novo quadro
                    quadroProcessado[y - 1][x - 1] = valorMedio;
                }
            }
            pixels[f] = quadroProcessado;
        }
        return pixels;
    }
}
