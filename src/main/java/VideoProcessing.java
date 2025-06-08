import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class VideoProcessing {

    /* Carrega a biblioteca nativa (via nu.pattern.OpenCV) assim que a classe é carregada na VM. */
    static {
        nu.pattern.OpenCV.loadLocally();
    }

    public static byte[][][] carregarVideo(String caminho) {

        VideoCapture captura = new VideoCapture(caminho);
        if (!captura.isOpened()) {
            System.out.println("Vídeo está sendo processado por outra aplicação");
        }
        
        //tamanho do frame
        int largura = (int) captura.get(Videoio.CAP_PROP_FRAME_WIDTH);
        int altura = (int) captura.get(Videoio.CAP_PROP_FRAME_HEIGHT);

        //não conhecço a quantidade dos frames (melhorar com outra lib) :(
        List<byte[][]> frames = new ArrayList<>();
           
        //matriz RGB mesmo preto e branco?? - uso na leitura do frame
        Mat matrizRGB = new Mat();
        
        //criando uma matriz temporária em escala de cinza
        Mat escalaCinza = new Mat(altura, largura, CvType.CV_8UC1); //1 única escala
        byte linha[] = new byte[largura];

        while (captura.read(matrizRGB)) {//leitura até o último frames
            
            //convertemos o frame atual para escala de cinza
            Imgproc.cvtColor(matrizRGB, escalaCinza, Imgproc.COLOR_BGR2GRAY);

            //criamos uma matriz para armazenar o valor de cada pixel (int estouro de memória)
            byte pixels[][] = new byte[altura][largura];
            for (int y = 0; y < altura; y++) {
                escalaCinza.get(y, 0, linha);
                for (int x = 0; x < largura; x++) {
                    pixels[y][x] = (byte)(linha[x] & 0xFF); //shift de correção - unsig
                }
            }
            frames.add(pixels);
        }
        captura.release();

        /* converte o array de frames em matriz 3D */
        byte cuboPixels[][][] = new byte[frames.size()][][];
        for (int i = 0; i < frames.size(); i++) {
            cuboPixels[i] = frames.get(i);
        }
        
        return cuboPixels;
    }


    public static void main(String[] args) {

        String caminhoVideo = "C:\\Users\\Estella1\\Documents\\MATERIAS IFMG\\Arquitetura de computadores\\TP2\\video.mp4";
        String caminhoGravar = "C:\\Users\\Estella1\\Documents\\MATERIAS IFMG\\Arquitetura de computadores\\TP2\\video2.mp4";
        double fps = 21.0; //isso deve mudar se for outro vídeo (avaliar metadados ???)

        System.out.println("Carregando o vídeo... " + caminhoVideo);
        byte pixels[][][] = carregarVideo(caminhoVideo);

        System.out.printf("Frames: %d   Resolução: %d x %d \n",
        pixels.length, pixels[0][0].length, pixels[0].length);

        System.out.println("processamento remove ruído 1");
        Filtros.removerSalPimenta(pixels);

        System.out.println("processamento remove ruído 2");
        Filtros.removerBorroestempo(pixels);

        System.out.println("Salvando...  " + caminhoGravar);
        Testes.gravarVideo(pixels, caminhoGravar, fps);
        System.out.println("Término do processamento");

/*        byte[][][] vetor = criarVetor3D(6,4,6);
            System.out.println("Farme 3");
            imprimirFrame(vetor,3);
            System.out.println("Farme 2");
            imprimirFrame(vetor,2);
            System.out.println("Farme 4");
            imprimirFrame(vetor,4);

            Filtros.removerBorroestempo(vetor);

            System.out.println("Frame 3 corrigido");
            imprimirFrame(vetor,3);*/









    }
}
