import java.util.ArrayList;

public class UltilitariosDeFiltros {
    public static byte[][][] removerBorroestempo(byte[][][] image) {
        return null;
    }

    public static byte media(ArrayList<Byte> numeros) {//nao tenho certeza se precisa ser um array de entrada
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
    public static byte[][] preencheBordaFrameZeros(byte[][][] image, int frame) {
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
    public static byte[][][] preencheFrameZeros(byte[][][] image) {
        int frames = image.length;
        int linhas = image[0].length;
        int colunas = image[0][0].length;
        byte imagePreenchida[][][]= new byte[frames+2][linhas][colunas];

        for (int i = 0; i < frames; i++) {
            imagePreenchida[i+1]=image[i];
        }

        return imagePreenchida;

    }
}
