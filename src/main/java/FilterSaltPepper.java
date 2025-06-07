import java.util.ArrayList;

public class FilterSaltPepper {
    //Filtro que remove os ruídos da imagem(pontos brancos e pretos aleatórios que aparecem na imagem)
    public static byte[][][] removerSalPimenta(byte[][][] image) {
        return null;
    }
    //Filtro que melhora as partes que estão embaçadas ou arrastadas


    private static int media(ArrayList<Byte> numeros) {//nao tenho certeza se precisa ser um array de entrada
        if(numeros == null|| numeros.isEmpty()) {
            return 0;
        }
        int soma = 0;
        for(Byte numero : numeros) {
           soma += (int)numero;
        }
        int media = soma/numeros.size();
        return media;

    }
    public static byte [][] preencheFrameZeros(byte [][][] image, int frame) {
        //o tamnho da imagem é 720x960 e 24 frames
        //criando uma imagem auxiliar
        int linhas = image[0].length;
        int colunas = image[0][0].length;
        byte [][] frameZeros = new byte[linhas+2][colunas+2];

        for(int i = 1 ; i < frameZeros.length -1 ; i++) {
            for(int j = 1 ; j < frameZeros[0].length -1; j++) {
               frameZeros[i][j] = image[frame][i-1][j-1];

            }
        }

        return frameZeros;
    }



    public static void aplicarFiltroDeMedia(byte[][][] pixels) {

    int qFrames = 0;
    int altura = 0;
    int largura = 0;

        for (int f = 0; f < qFrames; f++) {
            byte[][] quadroOriginal = pixels[f];
            byte[][] quadroProcessado = new byte[altura][largura];

        for (int y = 1; y < altura - 1; y++) {
            for (int x = 1; x < largura - 1; x++) {

                // coleta os vizinhos, reaproveitei da mediano, acho que é a mesma coisa
                ArrayList<Integer> vizinhos = new ArrayList<>();
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        vizinhos.add(converteByteInteiro(quadroOriginal[y + j][x + i]));
                    }
                }

                //USA A NOVA FUNÇÃO DE MÉDIA!
                int valorMedio = media(vizinhos);

                //aplica o resultado ao novo quadro
                quadroProcessado[y][x] = converteIntByte(valorMedio);
            }
        }
        pixels[f] = quadroProcessado;
    }
}
}
