fun main() {
    val cardType = "VkPay"
    val previousPay = 0
    val payAmount: Int = 10000 * 100

    if (!fitLimit(cardType, previousPay, payAmount)){
        println("Операция не может быть осуществлена. Достигнут лимит")
        return
    }

    println("Комиссия за перевод составит: ${getCommision(cardType, previousPay, payAmount) / 100}")
}

fun getCommision(cardType: String = "VkPay", previousPay: Int, payAmount: Int): Int {
    return when {
        cardType == "Visa" || cardType == "МИР" -> getVisaCommission(payAmount)
        cardType == "MasterCard" || cardType == "Maestro" -> getMasterCardCommission(previousPay, payAmount)
        else -> 0
    }
}

fun getVisaCommission(payAmount: Int): Int {
    val minCommission = 35*100
    val commissionPercent = 0.75

    val res = (payAmount * commissionPercent / 100).toInt()
    return when {
        res < minCommission -> minCommission
        else -> res
    }
}

fun getMasterCardCommission(previousPay: Int, payAmount: Int): Int {
    val freeCommissionAmount = 75000 * 100
    val fixCommission = 20 * 100
    return when {
        previousPay + payAmount <= freeCommissionAmount -> 0
        else -> (payAmount * 0.006).toInt() + fixCommission
    }
}

fun fitLimit(cardType: String, previousPay: Int, payAmount: Int): Boolean {
    val maxDayAmount = 150000 * 100
    val maxMonthAmount = 600000 * 100
    val maxVkDayAmount = 15000 * 100
    val maxVkMonthAmount = 40000 * 100
    return when (cardType) {
        "VK Pay" -> payAmount <= maxVkDayAmount && maxVkMonthAmount >= previousPay + payAmount
        else -> payAmount <= maxDayAmount && maxMonthAmount >= previousPay + payAmount
    }
}