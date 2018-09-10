package sklearn.covariance

import breeze.linalg.{DenseMatrix, DenseVector}
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random
import sklearn.util.Csv.loadCsv

class GaussianMultivariateEstimatorSpec extends FlatSpec with Matchers {

  "Gaussion AD" should "detect outlier" in {
    Random.setSeed(42)

    val features: Seq[Seq[Double]] = loadCsv("anomaly_X.csv", {
      case first :: second :: Nil => Seq(first.toDouble, second.toDouble)
    })

    val labels: Seq[Seq[Double]] = loadCsv("anomaly_y.csv", {
      case first :: Nil => Seq(first.toDouble)
    })

    val X = DenseMatrix(features: _*)
    val y = DenseVector(labels.flatten: _*)

    println(X.data.toSeq)
    println(y.data.toSeq)

    val prediction: DenseVector[Double] = new GaussianMultivariateEstimator().fit(X, Some(y)).predict(X)
  }
}
