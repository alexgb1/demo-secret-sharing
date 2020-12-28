package org.example.polynomial

import munit.FunSuite


class PolynomialTest extends FunSuite {

  test("compute polynomial") {
    val polynomial = Polynomial(List(1234, 166, 94))

    assertEquals(polynomial.compute(0).toInt, 1234)
    assertEquals(polynomial.compute(1).toInt, 1494)
    assertEquals(polynomial.compute(2).toInt, 1942)
  }
  
}
