package sklearn.covariance

import breeze.linalg.{DenseMatrix, DenseVector}
import org.scalatest.{FlatSpec, Matchers}

import sklearn.util.Csv.loadCsv

class GaussianMultivariateEstimatorSpec extends FlatSpec with Matchers {
  "Gaussion AD" should "detect outliers" in {
    val features: Seq[Seq[Double]] = loadCsv("multivar_anomaly_X.csv", {
      case one :: two :: three :: four :: five :: six :: seven :: eight :: nine :: ten :: eleven :: Nil =>
        Seq(one.toDouble, two.toDouble, three.toDouble, four.toDouble, five.toDouble, six.toDouble, seven.toDouble, eight.toDouble, nine.toDouble, ten.toDouble, eleven.toDouble)
    })

    val labels: Seq[Seq[Double]] = loadCsv("multivar_anomaly_y.csv", {
      case first :: Nil => Seq(first.toDouble)
    })

    val X = DenseMatrix(features: _*)
    val y = DenseVector(labels.flatten: _*)

    val prediction: DenseVector[Double] = new GaussianMultivariateEstimator().fit(X, Some(y)).predict(X)

    prediction.data.head should be("7.433929464694808E-18".toDouble)
  }
}
