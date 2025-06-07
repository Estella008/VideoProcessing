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


}
