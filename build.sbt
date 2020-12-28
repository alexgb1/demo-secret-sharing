lazy val root = project
  .in(file("."))
  .settings(
    name := "demo-secret-sharing",
    version := "0.0.1",
    scalaVersion := "3.0.0-M3",
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.20",
      "org.scalameta" %% "munit-scalacheck" % "0.7.20"
    ).map(_ % Test)
  )
