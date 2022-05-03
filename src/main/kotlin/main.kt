fun main() {
    val cardType = "VkPay"
    val previousPay = 0
    val payAmount: Int = 10000 * 100

    if (!fitLimit(cardType, previousPay, payAmount)){
        println("Операция не может быть осуществлена. Достигнут лимит")
        return
    }

    println("Комиссия за перевод составит: ${getCommission(cardType, previousPay, payAmount) / 100}")
}

fun getCommission(cardType: String, previousPay: Int, payAmount: Int): Int {
    return when (cardType) {
        "Visa", "МИР" -> getVisaCommission(payAmount)
        "MasterCard", "Maestro" -> getMasterCardCommission(previousPay, payAmount)
        else -> 0
    }
}

fun getVisaCommission(payAmount: Int): Int {
    val minCommission = 35_00
    val commissionPercent = 0.0075

    val res = (payAmount * commissionPercent).toInt()
    return when {
        res < minCommission -> minCommission
        else -> res
    }
}

fun getMasterCardCommission(previousPay: Int, payAmount: Int): Int {
    val freeCommissionAmount = 75000_00
    val fixCommission = 20_00
    return when {
        previousPay + payAmount <= freeCommissionAmount -> 0
        else -> (payAmount * 0.006).toInt() + fixCommission
    }
}

fun fitLimit(cardType: String, previousPay: Int, payAmount: Int): Boolean {
    val maxDayAmount = 150000_00
    val maxMonthAmount = 600000_00
    val maxVkDayAmount = 15000_00
    val maxVkMonthAmount = 40000_00
    return when (cardType) {
        "VK Pay" -> payAmount <= maxVkDayAmount && maxVkMonthAmount >= previousPay + payAmount
        else -> payAmount <= maxDayAmount && maxMonthAmount >= previousPay + payAmount
    }
}