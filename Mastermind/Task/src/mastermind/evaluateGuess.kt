package mastermind

data class Evaluation(var rightPosition: Int, var wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val evaluate = Evaluation(0,0)
    evaluate.rightPosition = secret.
                                foldIndexed(0) { index, acc, element ->
                                    acc + (if (element == guess.get(index)) 1 else 0) }

    System.out.println(evaluate.rightPosition)


    var secretMap: HashMap<Char, Int> = HashMap()
    for (char in secret){
       if ( !secretMap.containsKey(char)){
           secretMap.put(char,0)
       }
        secretMap.get(char)?.let { secretMap.put(char, it.plus(1)) }
    }

    for (char in guess){
        secretMap.get(char)?.let {
            if (it > 0){
                secretMap.put(char, it.minus(1));
                evaluate.wrongPosition++
            }
        }
    }
    evaluate.wrongPosition = evaluate.wrongPosition - evaluate.rightPosition

    return evaluate
}
