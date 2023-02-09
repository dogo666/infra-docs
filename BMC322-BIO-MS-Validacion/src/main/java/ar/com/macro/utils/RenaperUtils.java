package ar.com.macro.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import org.jnbis.api.Jnbis;
import org.jnbis.api.handler.FileHandler;

public class RenaperUtils {

    private static final int[] MASKS = new int[]{255, 255, 255};

    private RenaperUtils() {
      super();
    }

    public static String imagenBase64To512x512(String imagenBase64) throws IOException{
        String base64BPM512="";
        try {
            BufferedImage originalImage = originalImage(imagenBase64 );
            BufferedImage combined = combinar512x512(originalImage );
            base64BPM512 = imageToBase64(toBMP(combined));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return base64BPM512;
    }

    public static BufferedImage originalImage(String base64Img ) throws IOException {
        org.jnbis.api.model.Bitmap bitmap = Jnbis.wsq()
                .decode(DatatypeConverter.parseBase64Binary(base64Img))
                .asBitmap();
        InputStream bufferedImage = new FileHandler(convert(bitmap,  "BMP")).asInputStream();
        return ImageIO.read(bufferedImage);
    }

    public static BufferedImage combinar512x512(BufferedImage originalImage) throws IOException{
        BufferedImage recorteH512 = wsd2pngH512(originalImage);
        BufferedImage imagen512x512 = imagen512x512();
        BufferedImage combined = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
        Graphics g = combined.getGraphics();
        g.drawImage(imagen512x512, 0, 0, null);
        g.drawImage(recorteH512, 80, 0, null);
        return combined;
    }

    public static BufferedImage toBMP(BufferedImage inputImage) throws IOException {
        BufferedImage newBufferedImage = new BufferedImage(inputImage.getWidth(),  inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(inputImage, 0, 0, Color.WHITE, null);
        return newBufferedImage;
    }

    public static BufferedImage wsd2pngH512(BufferedImage originalImage)throws IOException{
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage recorte = originalImage.getSubimage(0, 16, 352, 496);
        return recorte;
    }

    public static BufferedImage imagen512x512() throws IOException {
        BufferedImage overlay = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
        Graphics2D ig2 = overlay.createGraphics();
        ig2.setBackground(Color.WHITE);
        ig2.clearRect(0, 0, 512, 512);
        return overlay;
    }

    public static byte[] convert(org.jnbis.api.model.Bitmap bitmap, String format) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        DataBuffer buffer = new DataBufferByte(bitmap.getPixels(), bitmap.getLength());
        WritableRaster writableRaster = Raster.createPackedRaster(buffer, width, height, width, MASKS, null);
        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        bufferedImage.setData(writableRaster);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Throwable var9 = null;
            byte[] var10;
            try {
                ImageIO.write(bufferedImage, format, outputStream);
                outputStream.close();
                var10 = outputStream.toByteArray();
            } catch (Throwable var20) {
                var9 = var20;
                throw var20;
            } finally {
                if (outputStream != null) {
                    if (var9 != null) {
                        try {
                            outputStream.close();
                        } catch (Throwable var19) {
                            var9.addSuppressed(var19);
                        }
                    } else {
                        outputStream.close();
                    }
                }
            }
            return var10;
        } catch (IOException var22) {
            throw new RuntimeException(var22);
        }
    }

    public static String imageToBase64(BufferedImage image) throws IOException {
        
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ImageIO.write(image,"wsq",baos);
        baos.flush();
        byte[] outputImage = baos.toByteArray();
        return DatatypeConverter.printBase64Binary(outputImage);
    }
}