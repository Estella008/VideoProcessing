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




}
