import org.junit.Test

import org.junit.Assert.*

class MainKtTest {

    @Test
    fun getCommission_vk() {
        val cardType = "VkPay"
        val previousPay = 0
        val payAmount = 10

        val res = getCommission(
            cardType = cardType,
            previousPay = previousPay,
            payAmount = payAmount)

        assertEquals(0, res)
    }

    @Test
    fun getCommission_visaMin() {
        val cardType = "Visa"
        val previousPay = 0
        val payAmount = 10_00

        val res = getCommission(
            cardType = cardType,
            previousPay = previousPay,
            payAmount = payAmount)

        assertEquals(35_00, res)
    }

    @Test
    fun getCommission_visa() {
        val cardType = "Visa"
        val previousPay = 0
        val payAmount = 10000_00

        val res = getCommission(
            cardType = cardType,
            previousPay = previousPay,
            payAmount = payAmount)

        assertEquals(75_00, res)
    }


    @Test
    fun getCommission_mc() {
        val cardType = "MasterCard"
        val previousPay = 0
        val payAmount = 350_00

        val res = getCommission(
            cardType = cardType,
            previousPay = previousPay,
            payAmount = payAmount)

        assertEquals(0, res)
    }

    @Test
    fun getMasterCardZeroCommission() {
        val previousPay = 300_00
        val payAmount = 10_00

        val res = getMasterCardCommission(
            previousPay = previousPay,
            payAmount = payAmount
        )
        assertEquals(0, res)
    }

    @Test
    fun getMasterCardFullCommission() {
        val previousPay = 0
        val payAmount = 100_000_00

        val res = getMasterCardCommission(
            previousPay = previousPay,
            payAmount = payAmount
        )
        assertEquals(620_00, res)
    }


    @Test
    fun fitLimit_vk_Month() {
        val cardType = "VK Pay"
        val previousPay = 50_000_00
        val payAmount = 10_00

        val res = fitLimit(
            cardType,
            previousPay,
            payAmount
        )
        assertEquals(false, res)
    }

    @Test
    fun fitLimit_vkOneTime() {
        val cardType = "VK Pay"
        val previousPay = 1_00
        val payAmount = 20_000_00
        val res = fitLimit(
            cardType,
            previousPay,
            payAmount
        )
        assertEquals(false, res)
    }

    @Test
    fun fitLimit_othersMonth() {
        val cardType = "MasterCard"
        val previousPay = 100_00
        val payAmount = 200_000_00

        val res = fitLimit(
            cardType,
            previousPay,
            payAmount
        )
        assertEquals(false, res)
    }

    @Test
    fun fitLimit_othersOneTime() {
        val cardType = "MasterCard"
        val previousPay = 1000_000_00
        val payAmount = 100_00

        val res = fitLimit(
            cardType,
            previousPay,
            payAmount
        )
        assertEquals(false, res)
    }

    @Test
    fun fitLimit_othersDayAndMonth() {
        val cardType = "Maestro"
        val previousPay = 100_00
        val payAmount = 100_00

        val res = fitLimit(
            cardType,
            previousPay,
            payAmount
        )
        assertEquals(false, res)
    }
}