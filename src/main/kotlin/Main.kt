import java.math.RoundingMode
import kotlin.math.sqrt

val atmPresure = 100100 //Pa
val standard = 0.001513 //mg/m3
fun main(args: Array<String>) {
    val s1 = Sample(6.67, 20.1, 0.126, 0.15)
    val result = getResult(getExpenses(s1), s1)
    println("Норматив = $standard, Массовый выброс = $result")
}

private fun getExpenses(sample: Sample): Double {
    var check = false
    var expenses: Double = 0.0
    while (!check) {
        for (x in 10 downTo 1) {
            expenses =
                (2.45 * 0.001 * x * x * sample.speed * (atmPresure - sample.presure) / (273 + sample.temp) * sqrt((273 + sample.temp) / (atmPresure - sample.presure))).toBigDecimal()
                    .setScale(1, RoundingMode.UP).toDouble()
            if (expenses < 20) {
                println("Наконечник = $x")
                check = true
                break
            }
        }
    }
    println("Расход = $expenses")
    return expenses
}

private fun getResult(expenses: Double, sample: Sample): Double {
    var check = false
    val filter1 = round(0.09 + (Math.random() / 100))
    var filter2 = round(filter1 + Math.random())
    while (!false) {
        val concentration =
            round(((filter2 - filter1) * 1000 * atmPresure * (273 + sample.temp)) / (expenses * sample.time * 273 * (atmPresure - sample.presure)))
        val result = concentration * sample.speed * sample.square
        if (result > standard) {
            filter2 = round(filter2 - 0.0001)
        } else {
            println("Filter = $filter1, Filter2 = $filter2, Концентрация = $concentration")
            check = true
            return result
        }
    }
    return 0.0
}

private fun round(value: Double): Double {
    return value.toBigDecimal().setScale(4, RoundingMode.HALF_UP).toDouble()
}
