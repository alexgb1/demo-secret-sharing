# demo-secret-sharing
This is an implementation of the Shamir's secret sharing scheme in Scala 3

# Dealer test

To run the dealer test (share a secret and reconstruct the secret in several configurations)

Run dealer test: ``` sbt "testOnly *.DealerTest"```

# Improvement

The sharing scheme is using polynomials in finite fields, this implementation makes use of [an improved way of evaluating polynomials](https://agbuzneanu.com/blog/faster-polynomial-evaluation/)

# How to use

Given your secret `s` as bytes:

1. Initiate the sharing scheme for your dealer:
```scala
val sharingScheme = ShamirSecretSharingScheme(
  Config(
    BigInt(s), // Your secret
    threshold, // The minium number of shares required to recover the secret
    numberOfParticipants, // Total number of participants/shares
    bitLen // The bit length of the prime number used to define the finite field (should be larger than the size of the secret)
  )
)

val dealer = Dealer(sharingScheme)
```
2. Share your secret with your trusted parties:
```scala
val shares = dealer.share(s)
```
3. Later on, reconstruct your secret, given at least `threshold` shares:
```scala
val recoveredSecret = dealer.recover(colectedShares)
```