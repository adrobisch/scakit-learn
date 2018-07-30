package sklearn.covariance

import breeze.linalg.{DenseMatrix, DenseVector}
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

class GaussianMultivariateEstimatorSpec extends FlatSpec with Matchers {
  "Gaussion AD" should "detect outlier" in {
    Random.setSeed(42)

    val featureOne: Seq[Double] = (1 to 1000).map(_ => Random.nextGaussian()) ++ Seq(10.0)
    val featureTwo: Seq[Double] = (1 to 1000).map(_ => Random.nextGaussian()) ++ Seq(10.0)

    println(featureOne)

    val X = DenseMatrix(
      featureOne,
      featureTwo
    )

    val y: DenseVector[Double] = DenseVector(Seq.fill(1000)(0.0) ++ Seq(1.0): _*)
    println(y)

    new GaussianMultivariateEstimator().fit(X, Some(y)).predict(X)
  }
}
