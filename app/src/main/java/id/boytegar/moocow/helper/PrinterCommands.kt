package id.boytegar.moocow.helper

import kotlin.experimental.and

object PrinterCommands {

    val HT: Byte = 0x9
    val LF: Byte = 0x0A
    val CR: Byte = 0x0D
    val ESC: Byte = 0x1B
    val DLE: Byte = 0x10
    val GS: Byte = 0x1D
    val FS: Byte = 0x1C
    val STX: Byte = 0x02
    val US: Byte = 0x1F
    val CAN: Byte = 0x18
    val CLR: Byte = 0x0C
    val EOT: Byte = 0x04

    val INIT = byteArrayOf(27, 64)
    var FEED_LINE = byteArrayOf(10)

    var SELECT_FONT_A = byteArrayOf(20, 33, 0)

    var SET_BAR_CODE_HEIGHT = byteArrayOf(29, 104, 100)
    var PRINT_BAR_CODE_1 = byteArrayOf(29, 107, 2)
    var SEND_NULL_BYTE = byteArrayOf(0x00)

    var SELECT_PRINT_SHEET = byteArrayOf(0x1B, 0x63, 0x30, 0x02)
    var FEED_PAPER_AND_CUT = byteArrayOf(0x1D, 0x56, 66, 0x00)

    var SELECT_CYRILLIC_CHARACTER_CODE_TABLE = byteArrayOf(0x1B, 0x74, 0x11)

    var SELECT_BIT_IMAGE_MODE = byteArrayOf(0x1B, 0x2A, 33, -128, 0)
    var SET_LINE_SPACING_24 = byteArrayOf(0x1B, 0x33, 24)
    var SET_LINE_SPACING_30 = byteArrayOf(0x1B, 0x33, 30)

    var TRANSMIT_DLE_PRINTER_STATUS = byteArrayOf(0x10, 0x04, 0x01)
    var TRANSMIT_DLE_OFFLINE_PRINTER_STATUS = byteArrayOf(0x10, 0x04, 0x02)
    var TRANSMIT_DLE_ERROR_STATUS = byteArrayOf(0x10, 0x04, 0x03)
    var TRANSMIT_DLE_ROLL_PAPER_SENSOR_STATUS = byteArrayOf(0x10, 0x04, 0x04)

    val ESC_FONT_COLOR_DEFAULT = byteArrayOf(0x1B, 'r'.toByte(), 0x00)
    val FS_FONT_ALIGN = byteArrayOf(0x1C, 0x21, 1, 0x1B, 0x21, 1)
    val ESC_ALIGN_LEFT = byteArrayOf(0x1b, 'a'.toByte(), 0x00)
    val ESC_ALIGN_RIGHT = byteArrayOf(0x1b, 'a'.toByte(), 0x02)
    val ESC_ALIGN_CENTER = byteArrayOf(0x1b, 'a'.toByte(), 0x01)
    val ESC_CANCEL_BOLD = byteArrayOf(0x1B, 0x45, 0)


    /** */
    val ESC_HORIZONTAL_CENTERS = byteArrayOf(0x1B, 0x44, 20, 28, 0)
    val ESC_CANCLE_HORIZONTAL_CENTERS = byteArrayOf(0x1B, 0x44, 0)
    /** */

    val ESC_ENTER = byteArrayOf(0x1B, 0x4A, 0x40)
    val PRINTE_TEST = byteArrayOf(0x1D, 0x28, 0x41)

    fun byteToHex(b: Byte): String {
        // Returns hex String representation of byte b
        val hexDigit = charArrayOf(
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f'
        )

        val array = charArrayOf(hexDigit[b.toInt() shr 4 and 0x0f], hexDigit[b.toInt() and 0x0f])
        return String(array)
    }

    fun charToHex(c: Char): String {
        // Returns hex String representation of char c
        val hi = c.toInt().ushr(8).toByte()
        val lo = (c.toInt() and 0xff).toByte()
        return byteToHex(hi) + byteToHex(lo)
    }
}