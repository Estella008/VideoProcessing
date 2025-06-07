import java.util.ArrayList;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
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

    public static void gravarVideo(byte pixels[][][],
            String caminho,
            double fps) {

        int qFrames = pixels.length;
        int altura = pixels[0].length;
        int largura = pixels[0][0].length;

        int fourcc = VideoWriter.fourcc('a', 'v', 'c', '1');   // identificação codec .mp4
        VideoWriter escritor = new VideoWriter(
                caminho, fourcc, fps, new Size(largura, altura), true);

        if (!escritor.isOpened()) {
            System.err.println("Erro ao gravar vídeo no caminho sugerido");
        }

        Mat matrizRgb = new Mat(altura, largura, CvType.CV_8UC3); //voltamos a operar no RGB (limitação da lib)
        
        byte linha[] = new byte[largura * 3];                // BGR intercalado

        for (int f = 0; f < qFrames; f++) {
            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    byte g = (byte) pixels[f][y][x];
                    int i = x * 3;
                    linha[i] = linha[i + 1] = linha[i + 2] = g;     // cinza → B,G,R
                }
                matrizRgb.put(y, 0, linha);
            }
            escritor.write(matrizRgb);
        }
        escritor.release(); //limpando o buffer 
    }
    public static void exibirCamada(byte[][] vetor) {



        for (int i = 0; i < vetor.length; i++) {
            for (int j = 0; j < vetor[0].length; j++) {
                System.out.print(vetor[i][j] + "\t");
            }
            System.out.println();
        }
    }
    public static byte[][][] criarVetor3D(int camadas, int linhas, int colunas) {
        byte[][][] vetor = new byte[camadas][linhas][colunas];

        byte valor = 0;
        for (int i = 0; i < camadas; i++) {
            for (int j = 0; j < linhas; j++) {
                for (int k = 0; k < colunas; k++) {
                    vetor[i][j][k] = valor++;
                    // Para evitar overflow do byte (vai de -128 a 127)
                    if (valor == 128) valor = -128;
                }
            }
        }

        return vetor;
    }
    public static void imprimirFrame(byte[][][] videoPixels, int frameIndex) {
        // Verifica se o vetor de pixels é nulo ou vazio
        if (videoPixels == null || videoPixels.length == 0) {
            System.out.println("O vetor de vídeo está vazio ou é nulo.");
            return;
        }

        // Verifica se o índice do frame é válido
        if (frameIndex < 0 || frameIndex >= videoPixels.length) {
            System.out.println("Índice de frame inválido: " + frameIndex + ". O vídeo possui " + videoPixels.length + " frames.");
            return;
        }

        byte[][] frame = videoPixels[frameIndex];
        int altura = frame.length;
        int largura = frame[0].length;

        System.out.println("\n--- Imprimindo Frame " + frameIndex + " (Dimensões: " + altura + "x" + largura + ") ---");

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                // Converte o byte para int sem sinal (0-255) antes de imprimir
                // Isso garante que valores como -1 sejam mostrados como 255, etc.
                int pixelValue = frame[y][x] & 0xFF;
                System.out.printf("%4d ", pixelValue); // Formata para ocupar 4 espaços, para alinhamento
            }
            System.out.println(); // Quebra de linha após cada linha de pixels
        }
        System.out.println("-----------------------------------------\n");
    }



        public static void main(String[] args) {

/*        String caminhoVideo = "D:\\Download\\video.mp4";
        String caminhoGravar = "D:\\Download\\video2.mp4";
        double fps = 21.0; //isso deve mudar se for outro vídeo (avaliar metadados ???)

        System.out.println("Carregando o vídeo... " + caminhoVideo);
        byte pixels[][][] = carregarVideo(caminhoVideo);

        System.out.printf("Frames: %d   Resolução: %d x %d \n",
                pixels.length, pixels[0][0].length, pixels[0].length);

        System.out.println("processamento remove ruído 1");
        //removerSalPimenta(pixels); //voce deve implementar esta funcao

        System.out.println("processamento remove ruído 2");
        //removerBorroesTempo(pixels); //voce deve implementar esta funcao

        System.out.println("Salvando...  " + caminhoGravar);
        gravarVideo(pixels, caminhoGravar, fps);
        System.out.println("Término do processamento");*/

        byte[][][] vetor = criarVetor3D(3,4,6);
            imprimirFrame(vetor,2);
       byte[][][] vetor2 = UltilitariosDeFiltros.preencheFrameZeros(vetor);
            imprimirFrame(vetor2,0);
       imprimirFrame(vetor2,3);



//            System.out.println("\n--- Imprimindo Frames ---");
//         imprimirFrame(vetor2,2);






    }
}
