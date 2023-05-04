import java.math.RoundingMode
import kotlin.math.sqrt

val atmPresure = 100100 //Pa
val standard = 0.006051 //mg/m3
fun main() {
    val s1 = Sample(9.22, 20.1, 0.126, -0.15)
    val result = getResult(getExpenses(s1), s1)
    println("Норматив = $standard, Массовый выброс = $result")
}

private fun getExpenses(sample: Sample): Double {
    var expenses = 0.0
    for (x in 10 downTo 1) {
        expenses =
            roundOne(
                2.45 * 0.001 * x * x * sample.speed * (atmPresure - sample.presure) /
                        (273 + sample.temp) * sqrt((273 + sample.temp) / (atmPresure - sample.presure))
            )
        if (expenses < 20) {
            println("Наконечник = $x")
            break
        }
    }
    println("Расход = $expenses")
    return expenses
}

private fun getResult(expenses: Double, sample: Sample): Double {
    val filter1 = roundFour(0.09 + (Math.random() / 100))
    var filter2 = roundFour(filter1 + Math.random())
    var result = 0.0
    while (filter2 > filter1) {
        val concentration =
            roundFour(((filter2 - filter1) * 1000 * atmPresure * (273 + sample.temp)) / (expenses * sample.time * 273 * (atmPresure - sample.presure)))
        result = concentration * sample.speed * sample.square
        if (result > standard) {
            filter2 = roundFour(filter2 - Math.random() / 1000)
        } else {
            println("Filter = $filter1, Filter2 = $filter2, Концентрация = $concentration")
            return roundSix(result)
        }
    }
    return result
}

private fun roundFour(value: Double): Double {
    return value.toBigDecimal().setScale(4, RoundingMode.HALF_UP).toDouble()
}
private fun roundOne(value: Double): Double {
    return value.toBigDecimal().setScale(1, RoundingMode.HALF_UP).toDouble()
}
private fun roundSix(value: Double): Double {
    return value.toBigDecimal().setScale(6, RoundingMode.HALF_UP).toDouble()
}