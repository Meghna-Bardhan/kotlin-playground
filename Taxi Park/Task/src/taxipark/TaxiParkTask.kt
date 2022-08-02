package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    this.allDrivers.filter { driver ->
        driver !in this.trips.map { trip -> trip.driver }
    }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    this.allPassengers.filter { passenger ->
        this.trips.count { trip -> passenger in trip.passengers } >= minTrips
    }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    this.allPassengers.filter { passenger ->
        this.trips.count { trip -> passenger in trip.passengers && driver == trip.driver } > 1
    }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    this.allPassengers.filter { passenger ->
        this.trips.count { trip -> passenger in trip.passengers && trip.discount != null } >
            this.trips.count { trip -> passenger in trip.passengers && trip.discount == null }
    }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    if (this.trips.isEmpty()) return null

    val countTripsForDuration: MutableMap<IntRange, Int> = mutableMapOf()
    val maxDuration = this.trips.map { trip -> trip.duration }.max() ?: 0
    for (i in 0..maxDuration step 10) {
        val durationRange = IntRange(i, i + 9)
        countTripsForDuration[durationRange] = this.trips.count { trip -> trip.duration in durationRange }
    }
    return countTripsForDuration.maxBy { (durationRange, numberOfTrips) -> numberOfTrips }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {

    val totalIncome = this.trips.map { trip -> trip.cost }.sum()

    val totalIncomePerDriver =
    this.trips.map { it.driver to it.cost }.groupBy { it.first }.mapValues { driverIncome -> driverIncome.value.sumByDouble { eachTripCost -> eachTripCost.second } }

    val totalDriver = this.allDrivers.count()

    if ( totalIncome == 0.0 || totalIncomePerDriver.values.sortedDescending().take((totalDriver*0.2).toInt()).sum() < totalIncome * 0.8) {
        return false
    }
    return true

}