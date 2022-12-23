package rationals

import java.math.BigInteger

class Rational(
    val numerator: BigInteger,
    val denominator: BigInteger
) : Comparable<Rational> {

    operator fun plus(rational: Rational): Rational =
        (numerator.times(rational.denominator)
            .plus(rational.numerator.times(denominator))).divBy(rational.denominator.times(denominator))

    operator fun minus(rational: Rational): Rational = (numerator.times(rational.denominator)
        .minus(rational.numerator.times(denominator))).divBy(rational.denominator.times(denominator))

    operator fun times(rational: Rational): Rational =
        numerator.times(rational.numerator).divBy(rational.denominator.times(denominator))

    operator fun div(rational: Rational): Rational =
        numerator.times(rational.denominator).divBy(denominator.times(rational.numerator))

    operator fun unaryMinus(): Rational = Rational(numerator.negate(), denominator)

    private fun hcf(number1: BigInteger, number2: BigInteger): BigInteger =
        if (number2 != 0.toBigInteger()) hcf(number2, number1 % number2) else number1

    private fun simplify(rational: Rational): Rational {
        val hcf = hcf(rational.numerator, rational.denominator).abs()
        return Rational(rational.numerator.div(hcf), rational.denominator.div(hcf))
    }

    private fun formatRational(rational: Rational): String =
        rational.numerator.toString() + "/" + rational.denominator.toString()

    override fun compareTo(rational: Rational): Int =
        numerator.times(rational.denominator).compareTo(rational.numerator.times(denominator))

    override fun toString(): String {
        return when {
            denominator == 1.toBigInteger() || numerator.rem(denominator) == 0.toBigInteger() -> numerator.div(
                denominator
            ).toString()
            else -> {
                val rational = simplify(this)

                if (rational.denominator < 0.toBigInteger() || (rational.numerator < 0.toBigInteger() && rational.denominator < 0.toBigInteger())) {
                    formatRational(Rational(rational.numerator.negate(), rational.denominator.negate()))
                } else {
                    formatRational(Rational(rational.numerator, rational.denominator))
                }
            }
        }
    }

    override fun equals(rational: Any?): Boolean {
        if (this === rational) return true
        rational as Rational
        val thisNumber = simplify(this)
        val otherNumber = simplify(rational)
        return thisNumber.numerator.toDouble()
            .div(thisNumber.denominator.toDouble()) == (otherNumber.numerator.toDouble()
            .div(otherNumber.denominator.toDouble()))
    }
}

infix fun Int.divBy(r2: Int): Rational = Rational(toBigInteger(), r2.toBigInteger())

infix fun Long.divBy(r2: Long): Rational = Rational(toBigInteger(), r2.toBigInteger())

infix fun BigInteger.divBy(r2: BigInteger): Rational = Rational(this, r2)

fun String.toRational(): Rational {
    val number = split("/")
    return when (number.size) {
        1 -> Rational(number[0].toBigInteger(), 1.toBigInteger())
        else -> Rational(number[0].toBigInteger(), number[1].toBigInteger())
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
}