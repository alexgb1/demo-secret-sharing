package org.example.secretsharing.shamir

import org.example.polynomial.Polynomial

import scala.annotation.tailrec
import scala.util.Random

case class ShamirSecretSharingScheme(
                                      polynomial: Polynomial,
                                      prime: BigInt,
                                      total: Int
                                    ) {

  def share(secret: BigInt): List[ShamirShare] = {
    val fullPolynomial =
      Polynomial(coefficients = secret +: polynomial.coefficients)
    (1 to total)
      .map(index => ShamirShare(index, fullPolynomial.compute(index)))
      .toList
  }

  def secret(shares: List[ShamirShare]): BigInt = {
    val indexes = shares.map(_.index)
    shares
      .foldLeft(BigInt(0)) { case (accumulator, share) =>
        accumulator + computeKi(indexes, share.index) * share.value
      }
      .mod(prime)
  }

  private def computeKi(
                         indexes: List[Int],
                         ithIndex: Int
                       ): BigInt = {
    indexes.filter(_ != ithIndex).foldLeft(BigInt(1)) {
      case (accumulator, jthIndex) =>
        accumulator * (BigInt(jthIndex) * BigInt(jthIndex - ithIndex).modInverse(prime))
    }
  }
}

object ShamirSecretSharingScheme {

  case class Config(secret: BigInt, min: Int, total: Int, bigLen: Int)

  val random = new Random()

  def apply(config: Config): ShamirSecretSharingScheme = {
    val p = ShamirSecretSharingScheme.choosePrime(config.secret, config.bigLen)
    val coefficients = (
      (1 until config.min)
        .map(_ => BigInt.probablePrime(p.bitLength - 2, random))
      )
      .toList

    new ShamirSecretSharingScheme(Polynomial(coefficients), p, config.total)
  }

  @tailrec
  private def choosePrime(secret: BigInt, bitLength: Int): BigInt = {
    val prime = BigInt.probablePrime(bitLength, random)
    if (prime <= secret) {
      choosePrime(secret, bitLength + 1)
    } else {
      prime
    }
  }
}
