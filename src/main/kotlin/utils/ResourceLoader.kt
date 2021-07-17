package utils

import org.jetbrains.skija.Bitmap
import org.jetbrains.skija.ColorAlphaType
import org.jetbrains.skija.Image
import org.jetbrains.skija.ImageInfo
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ResourceLoader {

    private const val ROCKET_PATH = "/img/rocket.png"

    fun getRocketImage() = Image.makeFromBitmap(loadBitmap(getResourceUrl().path))

    private fun getResourceUrl() = this::class.java.getResource(ROCKET_PATH)!!

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
