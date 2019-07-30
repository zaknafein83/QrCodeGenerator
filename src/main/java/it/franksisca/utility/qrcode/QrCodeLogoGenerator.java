package it.franksisca.utility.qrcode;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeLogoGenerator {
	private static final String OUTPUT = "C:\\Users\\Francesco Sisca\\Desktop\\QR.png";
	private static final String LOGO_PATH = "C:\\Users\\Francesco Sisca\\Desktop\\logo.png";
	private static final int Y = 250;
	private static final int X = 250;
	private static final String WEB_URL = "http://www.dstech.it/";

	public static void main(String[] args) {
		Map hints = new HashMap();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			bitMatrix = writer.encode(WEB_URL, BarcodeFormat.QR_CODE, X, Y, hints);
			MatrixToImageConfig config = new MatrixToImageConfig(0xFF227CE2, MatrixToImageConfig.WHITE);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
			File file = new File(LOGO_PATH);
			BufferedImage logoImage = ImageIO.read(file);
			BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) combined.getGraphics();
			g.drawImage(qrImage, 0, 0, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			BufferedImage scaled = getScaledInstance(logoImage, 50, 50, RenderingHints.VALUE_INTERPOLATION_BILINEAR,
					true);
			g.drawImage(scaled, 100, 100, null);
			ImageIO.write(combined, "png", new File(OUTPUT));
			System.out.println("done");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint,
			boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			w = img.getWidth();
			h = img.getHeight();
		} else {
			w = targetWidth;
			h = targetHeight;
		}
		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}
			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}
			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();
			ret = tmp;
		} while (w != targetWidth || h != targetHeight);
		return ret;
	}

}
