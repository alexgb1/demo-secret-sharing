package org.example.secretsharing

import org.scalacheck.Gen
import munit.ScalaCheckSuite
import org.example.secretsharing.shamir.ShamirSecretSharingScheme
import org.example.secretsharing.shamir.ShamirSecretSharingScheme.Config
import org.scalacheck.Prop._

import java.nio.charset.StandardCharsets

class DealerTest extends ScalaCheckSuite:

  val bitLen = 1024
  val numberOfParticipants = 7
  val threshold = 4

  def lessThanThreshold: Gen[Int] = Gen.choose(1, threshold - 1)

  def thresholdOrMore: Gen[Int] = Gen.choose(threshold, numberOfParticipants)

  test("share and recover the secret using the minimum required number of shares or more") {
    forAll(thresholdOrMore) { numberOfCollectedShares =>
      val secret = "This is quite the secret!"
      val secretBytes = secret.getBytes(StandardCharsets.UTF_8)
      val sharingScheme = ShamirSecretSharingScheme(Config(BigInt(secretBytes), threshold, numberOfParticipants, bitLen))
      val dealer = Dealer(sharingScheme)

      val shares = dealer.share(secretBytes)

      val rebuiltSecret = dealer.recover(shares.take(numberOfCollectedShares))
      val recoveredSecret = new String(rebuiltSecret, StandardCharsets.UTF_8)

      assertEquals(recoveredSecret, secret)
    }
  }


  test("share and fail to recover the secret if less than minimum shares are used") {
    forAll(lessThanThreshold) { numberOfCollectedShares =>
      val secret = "This is quite the secret!"
      val secretBytes = secret.getBytes(StandardCharsets.UTF_8)
      val sharingScheme = ShamirSecretSharingScheme(Config(BigInt(secretBytes), threshold, numberOfParticipants, bitLen))
      val dealer = Dealer(sharingScheme)

      val shares = dealer.share(secretBytes)

      val rebuiltSecret = dealer.recover(shares.take(numberOfCollectedShares))
      val recoveredSecret = new String(rebuiltSecret, StandardCharsets.UTF_8)

      assertNotEquals(recoveredSecret, secret)
    }
  }