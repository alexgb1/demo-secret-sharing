package org.example.polynomial

import scala.annotation.tailrec


case class Polynomial(coefficients: List[BigInt]) {

  /**
   * Compute using Horner's method https://en.wikipedia.org/wiki/Horner%27s_method
   *
   * */
  def compute(variable: BigInt): BigInt = {
    @tailrec
    def go(temp: BigInt, coef: List[BigInt]): BigInt = {
      coef match {
        case Nil => temp
        case head :: tail =>
          go(head + variable * temp, tail)
      }
    }

    go(0, coefficients.reverse)
  }


  def classicCompute(variable: BigInt): BigInt = {
    coefficients.zipWithIndex.foldLeft(BigInt(0)) { case (accumulator, (coef, i)) =>
      accumulator + coef * variable.pow(i)
    }
  }
}
