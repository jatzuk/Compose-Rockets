package utils

import org.jetbrains.skija.Bitmap
import org.jetbrains.skija.ColorAlphaType
import org.jetbrains.skija.Image
import org.jetbrains.skija.ImageInfo
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ResourceLoader {

  private const val IMG_PATH = "/img/"

  private const val ROCKET_PATH = "${IMG_PATH}rocket.png"
  private const val TARGET_PATH = "${IMG_PATH}target.png"

  fun getRocketImage() = Image.makeFromBitmap(loadBitmap(getResourceUrl(ROCKET_PATH).path))

  fun getTargetImage() = Image.makeFromBitmap(loadBitmap(getResourceUrl(TARGET_PATH).path))

  private fun getResourceUrl(path: String) = this::class.java.getResource(path)!!

  private fun loadBitmap(path: String) = bitmapFromByteArray(File(path))

  private fun bitmapFromByteArray(imageFile: File): Bitmap {
    val image = ImageIO.read(imageFile)
    val pixels = getBytes(image)

    val bitmap = Bitmap()
    bitmap.allocPixels(ImageInfo.makeN32(image.width, image.height, ColorAlphaType.PREMUL))
    bitmap.installPixels(bitmap.imageInfo, pixels, image.width * 4L)

    return bitmap
  }

  private fun getBytes(image: BufferedImage): ByteArray {
    val (width, height) = image.width to image.height
    val buffer = IntArray(width * height)
    image.getRGB(0, 0, width, height, buffer, 0, width)
    val pixels = ByteArray(width * height * 4)

    var index = 0
    for (y in 0 until height) {
      for (x in 0 until width) {
        val pixel = buffer[y * width + x]
        pixels[index++] = ((pixel and 0xFF)).toByte()
        pixels[index++] = (((pixel shr 8) and 0xFF)).toByte()
        pixels[index++] = (((pixel shr 16) and 0xFF)).toByte()
        pixels[index++] = (((pixel shr 24) and 0xFF)).toByte()
      }
    }

    return pixels
  }
}
