package org.example.secretsharing

import org.example.secretsharing.Dealer.Share
import org.example.secretsharing.shamir.{ShamirSecretSharingScheme, ShamirShare}

case class Dealer(secretSharingProtocol: ShamirSecretSharingScheme) {

  def share(secretBytes: Array[Byte]): List[Share] = {
    secretSharingProtocol
      .share(BigInt(secretBytes))
      .map(shamirShare =>
        Share(shamirShare.index, shamirShare.value.toByteArray)
      )
  }

  def recover(shares: List[Share]): Array[Byte] = {
    secretSharingProtocol
      .secret(shares.map(share => ShamirShare(share.id, BigInt(share.value))))
      .toByteArray
  }
}

object Dealer {

  case class Share(id: Int, value: Array[Byte])

}
