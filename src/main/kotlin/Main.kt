import Game.Sample
import java.math.RoundingMode
import kotlin.math.sqrt

val atmPresure = 100100
val standard = 0.021513

fun main(args: Array<String>) {
    val s1 = Sample(6.22, 27.1, 0.126, 0.15)
    val result = (getConcentration(getExpenses(s1), s1) * s1.speed * s1.square).toBigDecimal().setScale(6, RoundingMode.UP).toDouble()
    println("Массовый выброс = $result")
}

private fun generateFilter() = (0.09 + (Math.random() / 100)).toBigDecimal().setScale(4, RoundingMode.UP).toDouble()

private fun getExpenses(sample: Sample): Double {
    var check = false
    var expenses: Double = 0.0
    while (!check) {
        for (x in 10 downTo 1) {
            expenses =
                (2.45 * 0.001 * x * x * sample.speed * (atmPresure - sample.presure) / (273 + sample.temp) * sqrt((273 + sample.temp) / (atmPresure - sample.presure))).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
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

private fun getConcentration(expenses: Double, sample: Sample): Double {
    var check = false
    val filter1 = generateFilter()
    var filter2 = (filter1 + Math.random()).toBigDecimal().setScale(4, RoundingMode.UP).toDouble()
    while (!false) {
        val concentration =
            (((filter2 - filter1) * 1000 * atmPresure * (273 + sample.temp)) / (expenses * sample.time * 273 * (atmPresure - sample.presure))).toBigDecimal()
                .setScale(6, RoundingMode.UP).toDouble()
        if (concentration > standard) {
            filter2 -= 0.0001
        } else {
            println("Filter = $filter1, Filter2 = $filter2")
            check = true
            return concentration
        }
    }
    return 0.0
}

