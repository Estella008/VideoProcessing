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

    public static byte mediana(ArrayList<Byte> pixelsValidos) {
        ArrayList <Integer> pixelsValidosInt = new ArrayList<>();

        // converte para inteiro os pixels
        for (Byte pixel : pixelsValidos) {
            pixelsValidosInt.add(pixel & 0xFF);
        }
        // ordena os array de pixels validos
        pixelsValidosInt.sort(null);

        int size = pixelsValidos.size();
        int mediana;

        if(size % 2 == 0){
            int meio1 = pixelsValidosInt.get((size / 2 ) - 1);
            int meio2 = pixelsValidosInt.get(size / 2);

            mediana =(meio1 + meio2) / 2;
        } else {
            mediana = pixelsValidosInt.get(size / 2);
        }

        return (byte) mediana;
    }
}
